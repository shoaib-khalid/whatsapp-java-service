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
public class Template implements Serializable { 
    private String[] parameters;
    private String[] parametersHeader;
    private String[] parametersButton; //old field, cannot remove to remain backward compatibility
    private ButtonParameter[] buttonParameters; //new field
    private String parametersDocument;
    private String parametersDocumentFileName;
    private String name;   
}