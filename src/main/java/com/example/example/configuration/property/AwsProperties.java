package com.example.example.configuration.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private AwsCredentials cred;

    private AwsBucket bucket;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AwsCredentials {
        private String host;
        private String region;
        private String accessToken;
        private String secretKey;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AwsBucket {
        private String name;
        private String publicUrl;
    }
}

