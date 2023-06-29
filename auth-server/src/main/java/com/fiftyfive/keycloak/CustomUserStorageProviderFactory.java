package com.fiftyfive.keycloak;


import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;


public class CustomUserStorageProviderFactory implements
        UserStorageProviderFactory<CustomUserStorageProvider> {

    public static final String PROVIDER_NAME = "user-storage-provider";

    @Override
    public CustomUserStorageProvider create(KeycloakSession session, ComponentModel model) {

        return new CustomUserStorageProvider(session,
                model,
                new UsersApiService(session));
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
}

