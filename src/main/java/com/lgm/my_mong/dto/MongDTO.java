package com.lgm.my_mong.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MongDTO {
    @JsonProperty("이름")
    private String name;

    @JsonProperty("수식어")
    private String modifier;

    @JsonProperty("스토리")
    private String story;
}
