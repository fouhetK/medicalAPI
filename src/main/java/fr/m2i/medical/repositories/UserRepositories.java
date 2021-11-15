package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositories extends CrudRepository<UserEntity, Integer> {

}
