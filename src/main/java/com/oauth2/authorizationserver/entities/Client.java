package com.oauth2.authorizationserver.entities;

import jakarta.persistence.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "secret")
    private String secret;
    @Column(name = "redirect_uri")
    private String redirectUri;
    @Column(name = "scope")
    private String scope;
    @Column(name = "auth_method")
    private String authMethod;
    @Column(name = "grant_type")
    private String grantType;

    public static Client from (RegisteredClient client){
        Client clientEntity = new Client();
        clientEntity.setClientId(client.getClientId());
        clientEntity.setSecret(client.getClientSecret());
        clientEntity.setRedirectUri(client.getRedirectUris().stream().findAny().get());
        clientEntity.setScope(client.getScopes().stream().findAny().get());
        clientEntity.setAuthMethod(client.getClientAuthenticationMethods().stream().findAny().map(ClientAuthenticationMethod::getValue).get()); //todo
        clientEntity.setGrantType(client.getAuthorizationGrantTypes().stream().findAny().get().getValue());
        return clientEntity;
    }

    public static RegisteredClient from (Client client){
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
                .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
                .build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
