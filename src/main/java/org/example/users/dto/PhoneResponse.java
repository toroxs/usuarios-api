package org.example.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneResponse {
    private String number;
    private String citycode;

    @JsonProperty("countrycode")
    private String countrycode;

    public PhoneResponse() {}
    public PhoneResponse(String number, String citycode, String countrycode) {
        this.number = number; this.citycode = citycode; this.countrycode = countrycode;
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getCitycode() { return citycode; }
    public void setCitycode(String citycode) { this.citycode = citycode; }
    public String getCountrycode() { return countrycode; }
    public void setCountrycode(String countrycode) { this.countrycode = countrycode; }
}