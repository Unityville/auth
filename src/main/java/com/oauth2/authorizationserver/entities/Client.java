package com.oauth2.authorizationserver.entities;

import jakarta.persistence.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

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
        clientEntity.setRedirectUri(client.getRedirectUris().stream().findAny().orElseThrow());
        clientEntity.setScope(client.getScopes().stream().findAny().orElseThrow());
        clientEntity.setAuthMethod(client.getClientAuthenticationMethods().stream().findAny().map(ClientAuthenticationMethod::getValue).orElseThrow());
        clientEntity.setGrantType(client.getAuthorizationGrantTypes().stream().findAny().orElseThrow().getValue());
        return clientEntity;
    }

    public static RegisteredClient from (Client client){
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .scope(client.getScope())
                .redirectUri(client.getRedirectUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(5))
                        .refreshTokenTimeToLive(Duration.ofHours(2))
                        .build())
                .build();
    }

    private Long getId() {
        return id;
    }

    private String getClientId() {
        return clientId;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    private String getSecret() {
        return secret;
    }

    private void setSecret(String secret) {
        this.secret = secret;
    }

    private String getRedirectUri() {
        return redirectUri;
    }

    private void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    private String getScope() {
        return scope;
    }

    private void setScope(String scope) {
        this.scope = scope;
    }

    private String getAuthMethod() {
        return authMethod;
    }

    private void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    private void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
