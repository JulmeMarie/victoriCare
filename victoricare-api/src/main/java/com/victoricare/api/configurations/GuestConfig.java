package com.victoricare.api.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.ERight;

import lombok.Data;

@Data
@Configuration
@Component
public class GuestConfig {
    @Value("${fmc.api.visitor.name}")
    private String visitorName;

    @Value("${fmc.api.visitor.id}")
    private Integer visitorId;

    @Value("${fmc.api.visitor.password}")
    private String visitorPassword;

    public User getUser() {
        return User.builder()
                .id(visitorId)
                .pseudo(visitorName)
                .rights(ERight.ANONYMOUS.name())
                .roles(ERight.ANONYMOUS.name())
                .build();
    }
}
