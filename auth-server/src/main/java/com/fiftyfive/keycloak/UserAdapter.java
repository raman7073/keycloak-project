package com.fiftyfive.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class UserAdapter extends AbstractUserAdapterFederatedStorage {
    private User user;


    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {

        super(session, realm, model);
        this.user = user;
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {

        user.setUsername(username);
    }

    @Override
    public String getFirstName() {

        return user.getFirstName();
    }

    @Override
    public String getLastName() {

        return user.getLastName();
    }

    @Override
    public String getEmail() {

        return user.getEmail();
    }
}
