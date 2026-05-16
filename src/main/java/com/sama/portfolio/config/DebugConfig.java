package com.sama.portfolio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.debug")
public class DebugConfig {

    /**
     * Enable/disable debug logging
     * Set to true in application.properties to enable debugging
     */
    private boolean enabled = false;

    /**
     * Debug logging level: info, debug, trace
     * info = basic operations
     * debug = detailed information
     * trace = very detailed trace
     */
    private String level = "info";

    /**
     * Check if we should log at debug level
     */
    public boolean isDebugLevel() {
        return enabled && ("debug".equalsIgnoreCase(level) || "trace".equalsIgnoreCase(level));
    }

    /**
     * Check if we should log at trace level
     */
    public boolean isTraceLevel() {
        return enabled && "trace".equalsIgnoreCase(level);
    }
}
