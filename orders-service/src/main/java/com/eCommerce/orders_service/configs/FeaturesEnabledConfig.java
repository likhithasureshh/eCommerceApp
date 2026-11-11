package com.eCommerce.orders_service.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@RefreshScope
public class FeaturesEnabledConfig {
    @Value("${user.featureEnabled}")
    private boolean featureEnabled;
}
