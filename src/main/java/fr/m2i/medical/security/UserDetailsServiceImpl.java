package fr.m2i.medical.security;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity user = userRepositories.findByUsernameOrEmail(s, s);

        if (user == null)
            throw new UsernameNotFoundException("User "+ s +" not found");
        else
            return new UserDetailsImpl(user);
    }
}
