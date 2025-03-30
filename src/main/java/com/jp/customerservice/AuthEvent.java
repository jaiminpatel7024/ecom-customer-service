package com.jp.customerservice;

public class AuthEvent {

    private String type;
    private String principal;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Analytic{" +
                "type='" + type + '\'' +
                ", principal='" + principal + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}