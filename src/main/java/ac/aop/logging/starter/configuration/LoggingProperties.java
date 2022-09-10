package ac.aop.logging.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {
    String controllers;
    String services;

    public String getControllers() {
        return controllers;
    }

    public String getServices() {
        return services;
    }

    public void setControllers(String controllers) {
        this.controllers = controllers;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
