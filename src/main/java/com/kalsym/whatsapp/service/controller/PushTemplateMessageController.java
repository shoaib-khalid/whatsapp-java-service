package com.kalsym.whatsapp.service.controller;

import com.kalsym.whatsapp.service.model.WhatsappMessage;
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
@RequestMapping(path = "/templatemessage")
public class PushTemplateMessageController {
    
    @Value("${whatsapp.push.url.60125063299:https://graph.facebook.com/v17.0/101552592607485/messages}")
    private String whatsappPushUrl;
    
    @Value("${whatsapp.push.token.60125063299:Bearer EAAH0cEAnrjABOyNKyR0dNlwbSzsgSZBpZCRZBmppnE8gteTr27O8b6Ag2jYVprRJxSj4gzTJ4E81Vgp846ZBexMT4e3fZCZBK6L6ZBLSEaKZAkW8zvmdb49ANnqCtcs1nuSoRANj8101dav7RA5WtTuzvZAp2hB7sTjFZAJ5ZAIpwqdTZA2ODkQzcduChZA8tqQ0IIIW7}")
    private String whatsappPushToken;
    
    @Value("${whatsapp.push.url.60356300997:https://graph.facebook.com/v13.0/104564742449784/messages}")
    private String whatsappPushUrlDineIn;
    
    @Value("${whatsapp.push.token.60356300997:Bearer EAASHDrLZARfcBANifrFeuY77EETqNTKOmfIthFLJuNtRHy3hU2ZAL7OJSZAlojaXDZBtkaUiddZAZAG08qPugBZCCTjbsAT6X2ZBkLLA2thYEF7dFbsNB0ZBlZAhYk0yZAsZCG6szSliMvDOXkk9nGDigNQstViCydgj9QABh0eAVVXRXe6X23DCpoSr2ztldPchpoV79rzC2CbzD4K5xUDjXypT}")
    private String whatsappPushTokenDineIn;
    
    @Value("${whatsapp.push.url.92516120000:https://graph.facebook.com/v15.0/108910671852368/messages}")
    private String whatsappPushUrlEasyDukan;
    
    @Value("${whatsapp.push.token.92516120000:Bearer EAAG9yFXAfwABACu6stKw1YOpqMA1KJ8lVnLLl0SCdFI8vf4SxVa39x18tjIrxG5rrBgDaSjjI2bLZAW4mlv1XlOKk9Eakc2pGDZBi8YK00qZBCkUiSM8hdwiPsm54TW0C5nTZBZCxKgI2gtG2CSdMdDoxpCKFoN3gWHHvq65mcZAHSt4S7z2tt19w1fsX5AZAXFnSaBju0PZAAZDZD}")
    private String whatsappPushTokenEasyDukan;

    @Value("${whatsapp.push.url.601141218355:https://graph.facebook.com/v17.0/110598908806277/messages}")
    private String whatsappPushUrlEkedai;

    @Value("${whatsapp.push.token.601141218355:Bearer EAAH0cEAnrjABOyNKyR0dNlwbSzsgSZBpZCRZBmppnE8gteTr27O8b6Ag2jYVprRJxSj4gzTJ4E81Vgp846ZBexMT4e3fZCZBK6L6ZBLSEaKZAkW8zvmdb49ANnqCtcs1nuSoRANj8101dav7RA5WtTuzvZAp2hB7sTjFZAJ5ZAIpwqdTZA2ODkQzcduChZA8tqQ0IIIW7}")
    private String whatsappPushTokenEkedai;
    
    @PostMapping(path = {"/push"}, name = "push-template-message-post")   
    public ResponseEntity<HttpResponse> pushMessage(HttpServletRequest request,
            @Valid @RequestBody WhatsappMessage messageBody,
            @RequestParam(required = false) String senderMsisdn) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, messageBody: " + messageBody.toString());
        
        String url = whatsappPushUrlEkedai;
        String token = whatsappPushTokenEkedai;
//        if (senderMsisdn!=null && senderMsisdn.equals("60356300997")) {
//            url = whatsappPushUrlDineIn;
//            token = whatsappPushTokenDineIn;
//        }
//
//        if (messageBody.getTemplate().getName().startsWith("dinein")) {
//            url = whatsappPushUrlDineIn;
//            token = whatsappPushTokenDineIn;
//        }
//
//        if (messageBody.getTemplate().getName().startsWith("easydukan")) {
//            url = whatsappPushUrlEasyDukan;
//            token = whatsappPushTokenEasyDukan;
//        }
        
        try {
            HttpResult result = FacebookCloud.sendTemplateMessage(url, token, messageBody);            
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
