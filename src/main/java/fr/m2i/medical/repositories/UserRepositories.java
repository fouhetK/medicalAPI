package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositories extends CrudRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String name);

    public UserEntity findByUsernameOrEmail(String name, String email);

}
