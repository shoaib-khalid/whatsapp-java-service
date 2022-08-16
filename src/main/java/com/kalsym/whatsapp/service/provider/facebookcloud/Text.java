package com.kalsym.whatsapp.service.provider.facebookcloud;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Text {
    private String body;
    
    public Text(String body) {
        this.body = body;
    }
            
}
