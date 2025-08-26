package org.example.users.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.validation.constraints.NotBlank;


public class PhoneRequest {
    @NotBlank(message = "El número de teléfono es obligatorio")
    private String number;


    @NotBlank(message = "El código de ciudad es obligatorio")
    private String citycode;

    @JsonAlias({"countrycode"})
    @JsonProperty("contrycode")
    @NotBlank(message = "El código de país es obligatorio")
    private String countrycode;


    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getCitycode() { return citycode; }
    public void setCitycode(String citycode) { this.citycode = citycode; }
    public String getCountrycode() { return countrycode; }
    public void setCountrycode(String countrycode) { this.countrycode = countrycode; }
}