/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.whatsapp.service.provider.facebookcloud;

import com.kalsym.whatsapp.service.WhatsappWrapperServiceApplication;
import com.kalsym.whatsapp.service.model.WhatsappMessage;
import com.kalsym.whatsapp.service.utils.Logger;
import com.kalsym.whatsapp.service.utils.HttpPostConn;
import java.util.HashMap;
import com.google.gson.Gson;

/**
 *
 * @author taufik
 */
public class FacebookCloud {
    
    public static void sendMessage(String url, String token, WhatsappMessage requestBody) {
        String logprefix = "FacebookCloud";
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Start sending message");
        FbRequest req = new FbRequest();
        req.setMessaging_product("whatsapp");
        req.setTo(requestBody.getRecipientIds()[0]);
        req.setType("template");
        
        Template template = new Template();
        template.setName(requestBody.getTemplate().getName());
        Language lang = new Language();
        lang.setCode("en");
        template.setLanguage(lang);
        
        Parameter[] paramList = new Parameter[requestBody.getTemplate().getParameters().length];
        for (int i=0;i<requestBody.getTemplate().getParameters().length;i++) {
            String param = requestBody.getTemplate().getParameters()[i];
            Parameter parameter = new Parameter();
            parameter.setText(param);
            parameter.setType("text");
            paramList[i] = parameter;
        }
                
        Component[] componentList = new Component[1];
        Component component = new Component();
        component.setType("body");
        component.setParameters(paramList);
        componentList[0] = component;
        template.setComponents(componentList);
        req.setTemplate(template);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(req);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Request Json:"+jsonRequest);
        
        int connectTimeout = 10000;
        int waitTimeout = 30000;
        HashMap httpHeader = new HashMap();
        httpHeader.put("Authorization",token);
        httpHeader.put("Content-Type","application/json");
        HttpPostConn.SendHttpsRequest("POST", requestBody.getRecipientIds()[0], url, httpHeader, jsonRequest, connectTimeout, waitTimeout);
        
    }
    /*
    {
   "messaging_product":"whatsapp",
   "to":"60133731869",
   "type":"template",
   "template":{
      "name":"symplified_admin_alert",
      "language":{
         "code":"en"
      },
      "components":[
         {
            "type":"body",
            "parameters":[
               {
                  "type":"text",
                  "text":"CANNOT FIND DRIVER"
               },
               {
                  "type":"text",
                  "text":"Cinema Online"
               },
               {
                  "type":"text",
                  "text":"INV10001"
               },
               {
                  "type":"text",
                  "text":"2022-06-16 16:31:00"
               }
            ]
         }
      ]
   }
}
    */
}
