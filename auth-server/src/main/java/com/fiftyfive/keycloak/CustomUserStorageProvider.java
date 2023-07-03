package com.fiftyfive.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CustomUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        UserQueryProvider {
    private KeycloakSession session;
    private ComponentModel model;
    private UsersApiService usersApiService;
    protected HashMap<String, UserModel> loadedUsers = new HashMap<>();

    public CustomUserStorageProvider(KeycloakSession session, ComponentModel model,
                                     UsersApiService usersApiService) {

        this.session = session;
        this.model = model;
        this.usersApiService = usersApiService;
    }

    @Override
    public void close() {
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {

        String username = StorageId.externalId(id);
        return getUserByUsername(realm, username);
    }

    private UserModel createAdapter(RealmModel realm, User user) {

        return new UserAdapter(session, realm, model, user);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realmModel, String username) {

        UserModel adapter = loadedUsers.get(username);
        if (adapter == null) {
            User user = usersApiService.getUserDetails(username);
            adapter = createAdapter(realmModel, user);
        }
        return adapter;
    }

    @Override
    public int getUsersCount(RealmModel realm) {

        List<User> userList = usersApiService.getAllUsers();
        for (User user : userList) {
            loadedUsers.put(user.getUsername(), createAdapter(realm, user));
        }
        return userList.size();
    }

    @Override
    public UserModel getUserByEmail(RealmModel realmModel, String email) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realmModel, String username,
                                                 Integer integer, Integer integer1) {
        return searchForUserStream(realmModel, username);
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search) {

        List<User> userList = usersApiService.getAllUsers();
        userList.forEach(user -> loadedUsers.put(user.getUsername(), createAdapter(realm, user)));
        return userList.stream()
                .filter(user -> search.equalsIgnoreCase("*") || user.getUsername().contains(search))
                .map(user -> createAdapter(realm, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realmModel, Map<String, String> map,
                                                 Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realmModel, GroupModel groupModel,
                                                   Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realmModel, String username, String email) {
        return null;
    }
}
