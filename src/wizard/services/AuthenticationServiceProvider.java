package wizard.services;

import wizard.models.User;


public interface AuthenticationServiceProvider {
    boolean login(String u, String p);
    User getLoggedInUser();
}