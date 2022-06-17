package com.kalsym.whatsapp.service.controller;

import com.kalsym.whatsapp.service.model.WhatsappMessage;
import com.kalsym.whatsapp.service.WhatsappWrapperServiceApplication;
import com.kalsym.whatsapp.service.utils.HttpResponse;
import com.kalsym.whatsapp.service.utils.Logger;
import com.kalsym.whatsapp.service.provider.facebookcloud.FacebookCloud;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
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
    
    @Value("${whatsapp.push.url:https://graph.facebook.com/v13.0/108744405189669/messages}")
    private String whatsappPushUrl;
    
    @Value("${whatsapp.push.token:Bearer EAAFqZCjx0n6IBALKZCGohhKmZCQ0M5vNr4Gfk2wiZC55xehGQkaUlyHZBP0G6q3jiguZCvvfpbayN8cvcXjlxonHlzhdFLJmeTRmvy0yndLB7CZCVxWL4AwUlTU0GbkXnA72ZA7TXPfRxv8DddhBVswMQZBnwpLBhlDqf9yZCq6Ug6rOKdHCY8vaMYZA1tteD8GKr0IolRgSGOECQZDZD}")
    private String whatsappPushToken;
    
    @PostMapping(path = {"/push"}, name = "push-template-message-post")   
    public ResponseEntity<HttpResponse> pushMessage(HttpServletRequest request,
            @Valid @RequestBody WhatsappMessage messageBody) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "push-template-message-post, messageBody: ", messageBody.toString());

        try {
            FacebookCloud.sendMessage(whatsappPushUrl, whatsappPushToken, messageBody);            
            response.setSuccessStatus(HttpStatus.CREATED);
        } catch (Exception exp) {
            Logger.application.error(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Error sending message : ", exp);
            response.setMessage(exp.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Send message completed");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
