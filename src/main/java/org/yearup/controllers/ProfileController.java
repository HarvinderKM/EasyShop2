package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }
    @GetMapping()
    @PreAuthorize("permitAll()")
    public Profile getProfileDao(Principal principal) {
        User user = userDao.getByUserName(principal.getName());
        int userId = user.getId();
        try
        {
            var profile = profileDao.getById(user.getId());

            if(profile == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return profile;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

        }}

        @PutMapping()
        @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        @CrossOrigin
        public void updateProfile(Principal principal, @RequestBody Profile profile)
        {
            User user = userDao.getByUserName(principal.getName());
            try
            {
                var profile2 = profileDao.getById(user.getId());

                if(profile2 == null)
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                profileDao.update(profile2.getUserId(), profile);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
            }
        }
}
