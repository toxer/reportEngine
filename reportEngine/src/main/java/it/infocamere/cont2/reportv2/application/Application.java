package it.infocamere.cont2.reportv2.application;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//Birt ha problemi con la configurazione di Mongo. Va eliminata per lo spring boot
@EnableAutoConfiguration(exclude=MongoAutoConfiguration.class)
@ImportResource("classpath:/spring/mvc-config.xml")
//non necessario in quanto presente nel file mvc-config.xml
//@ComponentScan({"it.infocamere.cont2.reportv2.controller","it.infocamere.cont2.reportv2.commons.birt"})


public class Application extends SpringBootServletInitializer {

    @Override	
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        
    	return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
    	//bug con Tomcat 8
    	  if (AuthConfigFactory.getFactory() == null) {
              AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
          }
        SpringApplication.run(Application.class, args);
    }

}