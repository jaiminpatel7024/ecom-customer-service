package com.jp.customerservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private String tokenId;
    private String username;
    private String status;
    private Date generationTime;

    @Override
    public String toString() {
        return "Token{" +
                "tokenId='" + tokenId + '\'' +
                ", username='" + username + '\'' +
                ", status='" + status + '\'' +
                ", generationTime=" + generationTime +
                '}';
    }
}