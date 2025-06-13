//package ru.hogwarts.school.configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Profile("dev")
//public class AppConfigOtherPort implements PortProvider {
//    @Value("${server.port}")
//    private Integer port;
//
//    @Override
//    public Integer getPort() {
//        return port;
//    }
//}