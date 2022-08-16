package com.kalsym.whatsapp.service.provider.facebookcloud;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {
    private String button;
    private List<Section> sections;
    private List<Button> buttons;
}
