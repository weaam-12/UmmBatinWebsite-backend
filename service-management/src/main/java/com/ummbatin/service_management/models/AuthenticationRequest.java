// src/main/java/com/ummbatin/service_management/models/AuthenticationRequest.java
package com.ummbatin.service_management.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
