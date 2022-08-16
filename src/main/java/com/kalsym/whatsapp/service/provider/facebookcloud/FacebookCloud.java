/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.whatsapp.service.provider.facebookcloud;

import com.kalsym.whatsapp.service.WhatsappWrapperServiceApplication;
import com.kalsym.whatsapp.service.model.WhatsappMessage;
import com.kalsym.whatsapp.service.model.WhatsappInteractiveMessage;
import com.kalsym.whatsapp.service.model.WhatsappNotificationMessage;
import com.kalsym.whatsapp.service.model.ButtonParameter;
import com.kalsym.whatsapp.service.utils.Logger;
import com.kalsym.whatsapp.service.utils.HttpPostConn;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.kalsym.whatsapp.service.utils.HttpResult;

/**
 *
 * @author taufik
 */
public class FacebookCloud {
    
    public static HttpResult sendTemplateMessage(String url, String token, WhatsappMessage requestBody) {
        String logprefix = "FacebookCloud";
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Start sending message");
        String receiverMsisdn = requestBody.getRecipientIds()[0];
        if (receiverMsisdn.startsWith("01")) {
            receiverMsisdn = "6" + receiverMsisdn;
        } else if (receiverMsisdn.startsWith("0")) {
            receiverMsisdn = "92" + receiverMsisdn.substring(1);
        }
        FbRequest req = new FbRequest();
        req.setMessaging_product("whatsapp");
        req.setTo(receiverMsisdn);
        
        req.setType("template");
        
        Template template = new Template();
        template.setName(requestBody.getTemplate().getName());
        Language lang = new Language();
        lang.setCode("en");
        template.setLanguage(lang);
        
        List<Component> componentArrayList = new ArrayList<>();
        
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
            componentArrayList.add(component);                      
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
            componentButton.setSub_type("url");
            componentButton.setIndex(0);
            componentButton.setParameters(paramButtonList);
            componentArrayList.add(componentButton);                     
        }
        
        if (requestBody.getTemplate().getButtonParameters()!=null) {
            for (int i=0;i<requestBody.getTemplate().getButtonParameters().length;i++) {
                ButtonParameter buttonParam = requestBody.getTemplate().getButtonParameters()[i];
                Parameter[] paramList = new Parameter[buttonParam.getParameters().length];            
                for (int x=0;x<buttonParam.getParameters().length;x++) {
                    String param = buttonParam.getParameters()[x];
                    Parameter parameter = new Parameter();
                    parameter.setText(param);
                    parameter.setType("text");            
                    paramList[x] = parameter;
                }
            
                Component componentButton = new Component();
                componentButton.setType("button");
                componentButton.setSub_type(buttonParam.getSub_type());
                componentButton.setIndex(buttonParam.getIndex());
                componentButton.setParameters(paramList);
                componentArrayList.add(componentButton);                                                 
            }
        }
        
        if (requestBody.getTemplate().getParametersDocument()!=null) {
            Parameter[] paramList = new Parameter[1];
            String param = requestBody.getTemplate().getParametersDocument();
            Parameter parameter = new Parameter();                
            parameter.setType("document");
            Document document = new Document();
            document.setLink(param);
            if (requestBody.getTemplate().getParametersDocumentFileName()!=null) {
                document.setFilename(requestBody.getTemplate().getParametersDocumentFileName());
            } 
            parameter.setDocument(document);
            paramList[0] = parameter;
                            
            Component component = new Component();
            component.setType("header");
            component.setParameters(paramList);
            componentArrayList.add(component);            
        }
        
        int componentCount = componentArrayList.size();
        Component[] componentList = new Component[componentCount];
        for (int x=0;x<componentCount;x++) {
            componentList[x] = componentArrayList.get(x);            
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
        HttpResult result = HttpPostConn.SendHttpsRequest("POST", receiverMsisdn, url, httpHeader, jsonRequest, connectTimeout, waitTimeout);
        return result;
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
    
    
    
    public static HttpResult sendInteractiveMessage(String url, String token, WhatsappInteractiveMessage requestBody) {
        String logprefix = "FacebookCloud";
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Start sending message");
        
        FbInteractiveRequest req = new FbInteractiveRequest();
        req.setMessaging_product("whatsapp");
        req.setRecipient_type("individual");
        String receiverMsisdn = requestBody.getRecipientIds()[0];
        if (receiverMsisdn.startsWith("01")) {
            receiverMsisdn = "6" + receiverMsisdn;
        } else if (receiverMsisdn.startsWith("0")) {
            receiverMsisdn = "92" + receiverMsisdn.substring(1);
        }
        req.setTo(receiverMsisdn);
        req.setType("interactive");
        req.setInteractive(requestBody.getInteractive());
        
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(req);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Request Json:" + jsonRequest);

        int connectTimeout = 10000;
        int waitTimeout = 30000;
        HashMap httpHeader = new HashMap();
        httpHeader.put("Content-Type", "application/json");
        httpHeader.put("Authorization", token);
        HttpResult result = HttpPostConn.SendHttpsRequest("POST", receiverMsisdn, url, httpHeader, jsonRequest, connectTimeout, waitTimeout);
        return result;
    }
    
    
    public static HttpResult sendNotificationMessage(String url, String token, WhatsappNotificationMessage requestBody) {
        String logprefix = "FacebookCloud";
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Start sending message");
        
        FbNotificationRequest req = new FbNotificationRequest();
        req.setMessaging_product("whatsapp");
        req.setRecipient_type("individual");
        String receiverMsisdn = requestBody.getRecipientIds()[0];
        if (receiverMsisdn.startsWith("01")) {
            receiverMsisdn = "6" + receiverMsisdn;
        } else if (receiverMsisdn.startsWith("0")) {
            receiverMsisdn = "92" + receiverMsisdn.substring(1);
        }
        req.setTo(receiverMsisdn);        
        req.setText(new Text(requestBody.getText()));
        
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(req);
        Logger.application.info(Logger.pattern, WhatsappWrapperServiceApplication.VERSION, logprefix, "Request Json:" + jsonRequest);

        int connectTimeout = 10000;
        int waitTimeout = 30000;
        HashMap httpHeader = new HashMap();
        httpHeader.put("Content-Type", "application/json");
        httpHeader.put("Authorization", token);
        HttpResult result = HttpPostConn.SendHttpsRequest("POST", receiverMsisdn, url, httpHeader, jsonRequest, connectTimeout, waitTimeout);
        return result;
    }
}
