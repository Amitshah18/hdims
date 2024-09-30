package com.hdims.hdims.security;

import com.hdims.hdims.model.User;
import com.hdims.hdims.repository.UserRepository;
import com.hdims.hdims.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("User Not Found with email: " + email));
        
        return UserDetailsImpl.build(user);
    }
}
