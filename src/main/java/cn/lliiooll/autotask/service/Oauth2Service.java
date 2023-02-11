package cn.lliiooll.autotask.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Oauth2Service {

    @Value("${autotask.oauth2.github.clientid}")
    private  String clientId;
    @Value("${autotask.oauth2.github.clientsecret}")
    private  String clientSecret;
}
