package com.ms.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRecordDto {
    @NotBlank
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
    public UserRecordDto() {}
    
    public UserRecordDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
