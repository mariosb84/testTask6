package com.example.userservice.repository;

import com.example.userservice.domain.dto.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"userContacts", "userPhoto", "roles"})
    @NonNull
    List<User> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"userContacts", "userPhoto", "roles"})
    List<User> findAllUsersByUserName(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"userContacts", "userPhoto", "roles"})
    List<User> findAllUsersByUserNameContaining(String usernamePart);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"userContacts", "userPhoto", "roles"})
    Optional<User> findUserByUserName(String username);

    boolean existsByUserLastName(String userLastName);

    boolean existsByUserName(String userName);

    boolean existsByUserMiddleName(String userMiddleName);

}
