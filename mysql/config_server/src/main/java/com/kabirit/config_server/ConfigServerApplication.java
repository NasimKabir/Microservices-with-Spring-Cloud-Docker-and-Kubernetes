package com.kabirit.config_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    @Autowired
    private Environment environment;
    private final Logger LOGGER = LoggerFactory.getLogger(ConfigServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }


    @Bean
    public String logConfigServerStartup() {
        try {
            String gitUri = environment.getProperty("spring.cloud.config.server.git.uri");
            String activeProfile = environment.getProperty("spring.profiles.active");
            String serverPort = environment.getProperty("server.port");

            LOGGER.info("\n" +
                            "===========================================================\n" +
                            "  Config Server is running!\n" +
                            "  Profile(s): {}\n" +
                            "  Port: {}\n" +
                            "  Git Repository: {}\n" +
                            "  Encryption Enabled: {}\n" +
                            "===========================================================",
                    activeProfile,
                    serverPort,
                    gitUri,
                    environment.containsProperty("encrypt.key")
            );

            if (gitUri == null) {
                LOGGER.error("Git URI configuration is missing!");
            }

            return "Config Server Startup Logger Initialized"; // Return a dummy string

        } catch (Exception e) {
            LOGGER.error("Failed to log startup information", e);
            return "Startup Logger Failed";
        }
    }
}
