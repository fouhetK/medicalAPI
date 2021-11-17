package fr.m2i.medical.service;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepositories;
import fr.m2i.medical.repositories.VilleRepositories;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class VilleService {
    private VilleRepositories vr;


    private void checkVille(VilleEntity v) throws InvalidObjectException {

        if (v.getNom().length() <= 2) {
            throw new InvalidObjectException("Nom de ville invalide");
        }

    }

    public VilleService(VilleRepositories vr) {
        this.vr = vr;
    }


    public Iterable<VilleEntity> findAll() {
        return vr.findAll();
    }

    public Iterable<VilleEntity> findAll(String nom) {
        if (nom == null)
            return vr.findAll();
        else
            return  vr.findAllByNomContainsOrCodePostalContains(nom, nom);
    }

    public VilleEntity findById(int id) {
        return vr.findById(id).get();
    }

    public Iterable<VilleEntity> findByPaysByPaysCode(String code) {
        return vr.findByPaysByPaysCode(code);
    }

    public void saveVille(VilleEntity v) throws InvalidObjectException {
        checkVille(v);
        vr.save(v);
    }

    public void deleteById(int id) {
        vr.deleteById(id);
    }

    public void updateVille(int id, VilleEntity v) throws InvalidObjectException, NoSuchElementException {
        checkVille(v);
        try {
            VilleEntity vt = findById(id);

            vt.setPaysByPaysCode(v.getPaysByPaysCode());
            vt.setNom(v.getNom());
            vt.setCodePostal(v.getCodePostal());

            vr.save(vt);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
