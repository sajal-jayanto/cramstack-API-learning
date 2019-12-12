package com.cramstack.cramstackAPIlearning.Payloads;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserPayload {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String designation;

    private boolean enabled;

    @NotBlank
    @Length(min = 6)
    private String password;

    public UserPayload() {
    }

    public UserPayload(String name, String email, String designation, boolean enabled, String password) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.enabled = enabled;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
