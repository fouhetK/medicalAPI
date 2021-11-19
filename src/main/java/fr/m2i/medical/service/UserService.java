package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.PatientRepositories;
import fr.m2i.medical.repositories.UserRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private UserRepositories ur;

    public UserService(UserRepositories ur){
        this.ur = ur;
    }

    private void checkUser(UserEntity v) throws InvalidObjectException {

        if (v.getName().length() <= 2) {
            throw new InvalidObjectException("Nom de l'utilisateur invalide");
        }

    }

    public Iterable<UserEntity> findAll() {
        return ur.findAll();
    }


    public Page<UserEntity> findAllByPage(int page, String nom) {
        Pageable p = PageRequest.of(page - 1, 5);

        if (nom == null)
            return ur.findAll(p);
        else
            return ur.findAllByNameContainsOrUsernameContains(p, nom , nom);
    }

    public UserEntity findById(int id) {
        return ur.findById(id).get();
    }

    public void deleteById(int id) {
        ur.deleteById(id);
    }

    public void saveUser(UserEntity user){
        ur.save(user);
    }

    public void updateUser(int id, UserEntity u) throws InvalidObjectException, NoSuchElementException {
        checkUser(u);
        try {
            UserEntity ut = findById(id);

            ut.setUsername(u.getUsername());
            ut.setEmail(u.getEmail());
            ut.setName(u.getName());
            ut.setPassword(u.getPassword());
            ut.setPhotouser(u.getPhotouser());
            ut.setRoles(u.getRoles());

            ur.save(ut);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
