package com.rahul.JavaMail.Configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix ="spring.mail-receiver" )
@Data
@Component
public class MailProperties {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String attachmentStoragePath;

}
