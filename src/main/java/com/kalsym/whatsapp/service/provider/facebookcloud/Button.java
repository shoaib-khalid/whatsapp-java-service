package com.kalsym.whatsapp.service.provider.facebookcloud;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Button {
    private String type;
    private Reply reply;
    
    public Button() {
        this.type="reply";
    }
    
     public Button(Reply reply) {
        this.type="reply";
        this.reply=reply;
    }
}
