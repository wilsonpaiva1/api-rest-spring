package br.com.wilson.paiva.apirestspring.service;

import br.com.wilson.paiva.apirestspring.controller.PersonController;
import br.com.wilson.paiva.apirestspring.data.vo.v1.PersonVO;
import br.com.wilson.paiva.apirestspring.data.vo.v2.PersonVOV2;
import br.com.wilson.paiva.apirestspring.exceptions.RequiredObjectlsNullException;
import br.com.wilson.paiva.apirestspring.exceptions.ResourceNotFoundException;
import br.com.wilson.paiva.apirestspring.mapper.DozerMapper;
import br.com.wilson.paiva.apirestspring.mapper.custom.PersonMapper;
import br.com.wilson.paiva.apirestspring.model.Person;
import br.com.wilson.paiva.apirestspring.repositories.PersonRepository;
import br.com.wilson.paiva.apirestspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name"+username +"");
        var user = repository.findByUserName(username);
        if ( user != null) {
            return user;
        } else{
            throw new UsernameNotFoundException("Username"+username+" not found!");
        }
    }
}
