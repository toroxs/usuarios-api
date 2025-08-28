package org.example.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneResponse {
    private String number;
    private String citycode;

    @JsonProperty("countrycode")
    private String countrycode;
}