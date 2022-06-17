package com.kalsym.whatsapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author 7cu
 */
@SpringBootApplication
@EnableScheduling
public class WhatsappWrapperServiceApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger("application");

    static {
        System.setProperty("spring.jpa.hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        /**
         * To escape SQL reserved keywords
         */
        System.setProperty("hibernate.globally_quoted_identifiers", "true");
    }

    public static String VERSION;

    @Autowired
    private Environment env;

    public static void main(String... args) {
        logger.info("Starting whatsapp-wrapper-service...");
        SpringApplication.run(WhatsappWrapperServiceApplication.class, args);
    }

    @Value("${build.version:not-known}")
    String version;

    @Bean
    CommandLineRunner lookup(ApplicationContext context) {
        return args -> {
            VERSION = version;

            logger.info("[v{}][{}] {}", VERSION, "", "\n"
                    + "                            _                              _          \n"
                    + "                           | |                            (_)         \n"
                    + "  _ __ ___ _ __   ___  _ __| |_ ______ ___  ___ _ ____   ___  ___ ___ \n"
                    + " | '__/ _ \\ '_ \\ / _ \\| '__| __|______/ __|/ _ \\ '__\\ \\ / / |/ __/ _ \\\n"
                    + " | | |  __/ |_) | (_) | |  | |_       \\__ \\  __/ |   \\ V /| | (_|  __/\n"
                    + " |_|  \\___| .__/ \\___/|_|   \\__|      |___/\\___|_|    \\_/ |_|\\___\\___|\n"
                    + "          | |                                                         \n"
                    + "          |_|                                                         ");
        };
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
