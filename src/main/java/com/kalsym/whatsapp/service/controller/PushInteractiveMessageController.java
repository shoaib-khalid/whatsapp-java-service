package com.kalsym.whatsapp.service.controller;

import com.kalsym.whatsapp.service.model.WhatsappInteractiveMessage;
import com.kalsym.whatsapp.service.model.WhatsappNotificationMessage;
import com.kalsym.whatsapp.service.WhatsappWrapperServiceApplication;
import com.kalsym.whatsapp.service.utils.HttpResponse;
import com.kalsym.whatsapp.service.utils.Logger;
import com.kalsym.whatsapp.service.provider.facebookcloud.FacebookCloud;
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
@RequestMapping(path = "/interactive")
public class PushInteractiveMessageController {
    
    //https://graph.facebook.com/v15.0/{phone-number-ID}/message_templates.
    
    @Value("${whatsapp.push.url.60125063299:https://graph.facebook.com/v13.0/101552592607485/messages}")
    private String whatsappPushUrl;
    
    @Value("${whatsapp.push.token.60125063299:Bearer EAAFqZCjx0n6IBALKZCGohhKmZCQ0M5vNr4Gfk2wiZC55xehGQkaUlyHZBP0G6q3jiguZCvvfpbayN8cvcXjlxonHlzhdFLJmeTRmvy0yndLB7CZCVxWL4AwUlTU0GbkXnA72ZA7TXPfRxv8DddhBVswMQZBnwpLBhlDqf9yZCq6Ug6rOKdHCY8vaMYZA1tteD8GKr0IolRgSGOECQZDZD}")
    private String whatsappPushToken;
    
    @Value("${whatsapp.push.url.60356300997:https://graph.facebook.com/v13.0/104564742449784/messages}")
    private String whatsappPushUrlDineIn;
    
    @Value("${whatsapp.push.token.60356300997:Bearer EAASHDrLZARfcBANifrFeuY77EETqNTKOmfIthFLJuNtRHy3hU2ZAL7OJSZAlojaXDZBtkaUiddZAZAG08qPugBZCCTjbsAT6X2ZBkLLA2thYEF7dFbsNB0ZBlZAhYk0yZAsZCG6szSliMvDOXkk9nGDigNQstViCydgj9QABh0eAVVXRXe6X23DCpoSr2ztldPchpoV79rzC2CbzD4K5xUDjXypT}")
    private String whatsappPushTokenDineIn;
    
    @PostMapping(path = {"/push"}, name = "push-interactive-message-post")   
    public ResponseEntity<HttpResponse> pushMessage(HttpServletRequest request,
            @Valid @RequestBody WhatsappInteractiveMessage messageBody,
            @RequestParam(required = false) String senderMsisdn) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, messageBody: ", messageBody.toString());
        
        String url = whatsappPushUrl;
        String token = whatsappPushToken;
        if (senderMsisdn.equals("60356300997")) {
            url = whatsappPushUrlDineIn;
            token = whatsappPushTokenDineIn;
        } 
        
        try {
            HttpResult result = FacebookCloud.sendInteractiveMessage(url, token, messageBody);            
            if (result.resultCode==0) {
                if (result.httpResponseCode==200) {
                    response.setSuccessStatus(HttpStatus.OK);
                } else {
                    response.setMessage("Whatsapp API return error");
                    return ResponseEntity.status(result.httpResponseCode).body(response);                    
                }
            }
        } catch (Exception exp) {
            Logger.application.error(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Error sending message : ", exp);
            response.setMessage(exp.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Send message completed");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    @PostMapping(path = {"/notification"}, name = "push-notification-message-post")   
    public ResponseEntity<HttpResponse> pushNotificationMessage(HttpServletRequest request,
            @Valid @RequestBody WhatsappNotificationMessage messageBody,
            @RequestParam(required = false) String senderMsisdn) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, messageBody: ", messageBody.toString());
        
        String url = whatsappPushUrl;
        String token = whatsappPushToken;
        if (senderMsisdn.equals("60356300997")) {
            url = whatsappPushUrlDineIn;
            token = whatsappPushTokenDineIn;
        } 
        
        try {
            HttpResult result = FacebookCloud.sendNotificationMessage(url, token, messageBody);            
            if (result.resultCode==0) {
                if (result.httpResponseCode==200) {
                    response.setSuccessStatus(HttpStatus.OK);
                } else {
                    response.setMessage("Whatsapp API return error");
                    return ResponseEntity.status(result.httpResponseCode).body(response);                    
                }
            }
        } catch (Exception exp) {
            Logger.application.error(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Error sending message : ", exp);
            response.setMessage(exp.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Send message completed");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
}
