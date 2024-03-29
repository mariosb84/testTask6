package com.example.itemservice.service;

import com.example.itemservice.domain.model.User;
import com.example.itemservice.domain.dto.UserDto;
import com.example.itemservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static com.example.itemservice.domain.model.Role.ROLE_USER;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class UserServiceData implements UserService, UserDetailsService {

    private final BCryptPasswordEncoder encoder;

    private final UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> add(User user) {
        user.setRoles(List.of(ROLE_USER));
      User result = userRepository.save(user);
        if (Optional.of(result).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public boolean update(User user) {
        userRepository.save(user);
        return userRepository.findById(user.getId()).isPresent();

    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean delete(User user) {
        userRepository.delete(user);
        return userRepository.findById(user.getId()).isEmpty();
    }

    @Override
    public boolean updatePatch(UserDto userDto) {
        var person = userRepository.findById(userDto.getId());
        if (person.isPresent()) {
            User result = person.get();
            result.setPassword(encoder.encode(userDto.getPassword()));
             this.add(result);
             return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
    }

}
