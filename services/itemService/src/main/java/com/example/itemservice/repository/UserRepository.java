package com.example.itemservice.repository;

import com.example.itemservice.domain.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*import org.springframework.data.repository.CrudRepository;*/


import java.util.List;
import java.util.Optional;

@Repository
/*public interface UserRepository extends CrudRepository<User, Long> {*/
    public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    @NonNull
    List<User> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    List<User> findAllUsersByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    List<User> findAllUsersByUsernameContaining(String usernamePart);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
