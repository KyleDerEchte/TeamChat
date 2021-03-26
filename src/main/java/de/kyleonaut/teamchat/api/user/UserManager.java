package de.kyleonaut.teamchat.api.user;

import de.kyleonaut.teamchat.Teamchat;

import java.util.UUID;

public class UserManager {
    private final Teamchat tc = Teamchat.getInstance();

    public void addUser(User user){
        tc.getOnlineTeamUsers().forEach(it -> {
            if (!it.getUniqueId().equals(user.getUniqueId())){
                tc.getOnlineTeamUsers().add(user);
            }
        });
    }

    public void removeUser(User user){tc.getOnlineTeamUsers().remove(user);}

    public void removeUser(UUID uniqueId){
        tc.getOnlineTeamUsers().forEach(user -> {
            if (user.getUniqueId().equals(uniqueId)){
                tc.getOnlineTeamUsers().remove(user);
            }
        });
    }

    public User findUser(UUID uniqueId){
        for (User onlineTeamUser : tc.getOnlineTeamUsers()) {
            if (onlineTeamUser.getUniqueId().equals(uniqueId)){
                return onlineTeamUser;
            }
        }
        return null;
    }
}
