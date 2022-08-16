package com.kalsym.whatsapp.service.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kalsym.whatsapp.service.model.WhatsappMessage;
import com.kalsym.whatsapp.service.WhatsappWrapperServiceApplication;
import com.kalsym.whatsapp.service.utils.HttpResponse;
import com.kalsym.whatsapp.service.utils.Logger;
import com.kalsym.whatsapp.service.provider.facebookcloud.FacebookCloud;
import com.kalsym.whatsapp.service.utils.DateTimeUtil;
import com.kalsym.whatsapp.service.utils.HttpPostConn;
import com.kalsym.whatsapp.service.utils.HttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping(path = "/webhook")
public class WebhookController {
    
    @Value("${route.incoming.default:https://www.kalsym.com/whatsapp/webhook.php}")
    private String defaultRouteUrl;
    
    @Value("${route.incoming.staging.url:https://api.symplified.it/order-service/v1/whatsapp/receive}")
    private String stagingOrderServiceUrl;
    
    @Value("${route.incoming.production.url:https://api.symplified.biz/order-service/v1/whatsapp/receive}")
    private String productionOrderServiceUrl;
    
    @PostMapping(path = {"/receive"}, name = "webhook-post")
    public ResponseEntity<HttpResponse> webhook(HttpServletRequest request, @RequestBody String json) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "callback-message-get, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Request Body:  " + json);
        
        JsonObject jsonResp = new Gson().fromJson(json, JsonObject.class);
        JsonObject entry = jsonResp.get("entry").getAsJsonArray().get(0).getAsJsonObject();
        JsonObject changes = entry.get("changes").getAsJsonArray().get(0).getAsJsonObject();
        
        //user input : {"from":"60133731869","id":"wamid.HBgLNjAxMzM3MzE4NjkVAgASGBQzRUIwQkI3Q0FCRjIwQjNEMjg4OQA=","timestamp":"1660023849","text":{"body":"hello"},"type":"text"}
        //user select from list : {"context":{"from":"60125063299","id":"wamid.HBgLNjAxMzM3MzE4NjkVAgARGBJGQkVGQURCODBDRDQ0OUQ4M0IA"},"from":"60133731869","id":"wamid.HBgLNjAxMzM3MzE4NjkVAgASGBQzRUIwMDAxOURCMDk1RDhGNjk4QwA=","timestamp":"1660024660","type":"interactive","interactive":{"type":"list_reply","list_reply":{"id":"b4b3fac1-f593-4dff-ad64-2ad532cf4724","title":"Brew Coffee"}}}
        
        JsonObject messages = null;
        try {
            messages = changes.get("value").getAsJsonObject().get("messages").getAsJsonArray().get(0).getAsJsonObject();
        } catch (Exception ex) {
            //not a message
            Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "callback-message-get, not a message");            
            JsonObject statuses = changes.get("value").getAsJsonObject().get("statuses").getAsJsonArray().get(0).getAsJsonObject();
            Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "callback-message-get, receive a status : "+statuses.toString());            
            response.setSuccessStatus(HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "callback-message-get, MessageBody: " + messages);
        
        JsonObject context = null;
        try {
            context = messages.get("context").getAsJsonObject();            
        } catch (Exception ex) {}
        
        WhatsappMessage messageBody = new WhatsappMessage();
        String phone = null;
        String userInput = null;
        String type = null;
        String replyTitle=null;
        String replyId=null;
        String url = defaultRouteUrl;
        
        if (context!=null) {
            //user reply
            phone = messages.get("from").getAsString();
            type = messages.get("type").getAsString();
            if (type.equals("interactive")) {
                JsonObject interactive = messages.get("interactive").getAsJsonObject();
                String interactiveType = interactive.get("type").getAsString();
                if (interactiveType.equals("list_reply")) {
                    JsonObject listReply = interactive.get("list_reply").getAsJsonObject();
                    replyId = listReply.get("id").getAsString();
                    replyTitle = listReply.get("title").getAsString();
                } else if (interactiveType.equals("button_reply")) {
                    JsonObject listReply = interactive.get("button_reply").getAsJsonObject();
                    replyId = listReply.get("id").getAsString();
                    replyTitle = listReply.get("title").getAsString();
                }
            } else if (type.equals("button")) {
                JsonObject button = messages.get("button").getAsJsonObject();
                replyId = button.get("payload").getAsString();
                replyTitle = button.get("text").getAsString();
            }
            if (replyId!=null) {
                if (replyId.startsWith("STG")) {
                    url = stagingOrderServiceUrl;
                } else if (replyId.startsWith("PROD")) {
                    url = productionOrderServiceUrl;
                }
            }
            Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Incoming message. Msisdn:"+phone+" UserReply: " + replyId+" -> "+replyTitle);        
        } else {
            //user input
            type = "input";
            phone = messages.get("from").getAsString();
            userInput = messages.get("text").getAsJsonObject().get("body").getAsString();
            Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Incoming message. Msisdn:"+phone+" UserInput:" + userInput);        
        }
        
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Route URL : "+url);        
        
        //route to external modules
        int connectTimeout = 10000;
        int waitTimeout = 30000;
        HashMap httpHeader = new HashMap();
        httpHeader.put("Authorization","Bearer accessToken");
        httpHeader.put("Content-Type","application/json");
        HttpResult result = HttpPostConn.SendHttpsRequest("POST", phone, url, httpHeader, json.toString(), connectTimeout, waitTimeout);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Route result : "+result.toString());        
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = {"/receive"}, name = "webhook-get")
    @ResponseBody
    public String verifyWebHook(HttpServletRequest request, 
            @RequestParam(required = false, name = "hub.mode") String mode, 
            @RequestParam(required = false, name = "hub.challenge") String challenge, 
            @RequestParam(required = false, name = "hub.verify_token") String token) throws Exception {
        String logprefix = request.getRequestURI() + " ";
       
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "URL:  " + request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "hub_mode: " + mode);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "hub_challenge : " + challenge);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "hub_verify_token : " + token);
        
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Send message completed");
        
        return challenge;
    }
    
}
