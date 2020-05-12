package net.kuper.tz.config;

import net.kuper.tz.core.properties.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
public class PropertiesConfig {

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "tz")
    public TzProperties tzProperties() {
        return new TzProperties();
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "tz.admin", ignoreUnknownFields = false)
    public AdminProperties adminProperties() {
        return new AdminProperties();
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "tz.storage", ignoreUnknownFields = false)
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "tz.build", ignoreUnknownFields = false)
    public BuildProperties buildProperties() {
        return new BuildProperties();
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = "tz.transport", ignoreUnknownFields = false)
    public TransportProperties transportProperties() {
        return new TransportProperties();
    }
}
