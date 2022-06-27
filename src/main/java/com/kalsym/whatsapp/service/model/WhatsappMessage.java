/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.whatsapp.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author taufik
 */

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class WhatsappMessage implements Serializable {    
    private String[] recipientIds;
    private String title;
    private String subTitle;
    private String url;
    private String urlType;
    private String menuItems;
    private Template template;    
    private String refId;
    private String referenceId;
    private Boolean guest;
    private String orderId;
    private String merchantToken;
}