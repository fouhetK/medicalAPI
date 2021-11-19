package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface UserRepositories extends CrudRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String name);

    public UserEntity findByUsernameOrEmail(String name, String email);

    Page<UserEntity> findAll(Pageable page);

    public Page<UserEntity> findAllByNameContainsOrUsernameContains(Pageable page, String name, String username);

}
