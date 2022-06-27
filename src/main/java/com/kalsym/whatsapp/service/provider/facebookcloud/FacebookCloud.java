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
        
        Component[] componentList = new Component[3];
            
        if (requestBody.getTemplate().getParameters()!=null) {
            Parameter[] paramList = new Parameter[requestBody.getTemplate().getParameters().length];
            for (int i=0;i<requestBody.getTemplate().getParameters().length;i++) {
                String param = requestBody.getTemplate().getParameters()[i];
                Parameter parameter = new Parameter();
                parameter.setText(param);
                parameter.setType("text");
                paramList[i] = parameter;
            }                
            Component component = new Component();
            component.setType("body");
            component.setParameters(paramList);
            componentList[0] = component;
            template.setComponents(componentList);
        }
        
        if (requestBody.getTemplate().getParametersButton()!=null) {
            Parameter[] paramButtonList = new Parameter[requestBody.getTemplate().getParametersButton().length];
            for (int i=0;i<requestBody.getTemplate().getParametersButton().length;i++) {
                String param = requestBody.getTemplate().getParametersButton()[i];
                Parameter parameter = new Parameter();
                parameter.setText(param);
                parameter.setType("text");            
                paramButtonList[i] = parameter;
            }

            Component componentButton = new Component();
            componentButton.setType("button");
            componentButton.setSubType("url");
            componentButton.setIndex(0);
            componentButton.setParameters(paramButtonList);
            componentList[1] = componentButton;                    
        }
        
        if (requestBody.getTemplate().getParametersDocument()!=null) {
            Parameter[] paramList = new Parameter[1];
            String param = requestBody.getTemplate().getParametersDocument();
            Parameter parameter = new Parameter();                
            parameter.setType("document");
            Document document = new Document();
            document.setLink(param);
            document.setFilename("SaleInvoice.pdf");
            parameter.setDocument(document);
            paramList[0] = parameter;
                            
            Component component = new Component();
            component.setType("header");
            component.setParameters(paramList);
            componentList[2] = component;            
        }
        
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
