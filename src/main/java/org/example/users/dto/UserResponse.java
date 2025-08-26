package org.example.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
    private String token;
    @JsonProperty("isactive")
    private boolean isActive;
    private String name;
    private String email;
    private List<PhoneResponse> phones;

    public UserResponse() {}

    public UserResponse(String id, LocalDateTime created, LocalDateTime modified, LocalDateTime lastLogin,
                        String token, boolean isActive, String name, String email, List<PhoneResponse> phones) {
        this.id = id; this.created = created; this.modified = modified; this.lastLogin = lastLogin;
        this.token = token; this.isActive = isActive; this.name = name; this.email = email; this.phones = phones;
    }

    //Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<PhoneResponse> getPhones() { return phones; }
    public void setPhones(List<PhoneResponse> phones) { this.phones = phones; }
}