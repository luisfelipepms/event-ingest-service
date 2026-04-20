package com.felipesilva.event_ingest_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipesilva.event_ingest_service.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return repository.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException("Usuário não encontrado: " + username)
        );
    }
}
