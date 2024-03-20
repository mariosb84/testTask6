package com.example.itemservice.service;

import com.example.itemservice.domain.Role;
import com.example.itemservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceData implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> add(Role role) {
        Role result = roleRepository.save(role);
        if (Optional.of(result).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public boolean update(Role role) {
        roleRepository.save(role);
        return roleRepository.findById(role.getId()).isPresent();
    }

    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public boolean delete(Role role) {
        return roleRepository.findById(role.getId()).isEmpty();
    }

}
