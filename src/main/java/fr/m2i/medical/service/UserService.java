package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.repositories.PatientRepositories;
import fr.m2i.medical.repositories.UserRepositories;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepositories ur;

    public UserService(UserRepositories ur){
        this.ur = ur;
    }

    public Iterable<UserEntity> findAll() {
        return ur.findAll();
    }

    public UserEntity findById(int id) {
        return ur.findById(id).get();
    }

    public void delete(int p) {
    }

    public void deleteById(int id) {
        ur.deleteById(id);
    }
}
