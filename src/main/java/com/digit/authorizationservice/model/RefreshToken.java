package com.digit.authorizationservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "refresh_token")
public class RefreshToken {

    @Id
    private String id;
    private String value;
    private String accountId;
    private LocalDateTime expirationTime;
}
