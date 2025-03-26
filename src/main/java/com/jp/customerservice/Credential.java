package com.jp.customerservice;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    private String username;
    private String password;
    private String email;
    private String role;

    @Override
    public String toString() {
        return "Credential{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
