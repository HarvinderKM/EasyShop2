package org.yearup.data;


import org.yearup.models.Profile;

import java.security.Principal;

public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getById(int userId);

    void update(int userId, Profile profile);
}
