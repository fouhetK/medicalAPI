package fr.m2i.medical.service;

import fr.m2i.medical.entities.PaysEntity;
import fr.m2i.medical.repositories.PaysRepositories;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class PaysService {
    private PaysRepositories pr;


    private void checkVille(PaysEntity v) throws InvalidObjectException {

        if (v.getNom().length() <= 2) {
            throw new InvalidObjectException("Nom de ville invalide");
        }

    }

    public PaysService(PaysRepositories pr) {
        this.pr = pr;
    }

    public Iterable<PaysEntity> findAll() {
        return pr.findAll();
    }

    public PaysEntity findByCode(String id) {
        return pr.findById(id).get();
    }

    public void saveVille(PaysEntity v) throws InvalidObjectException {
        checkVille(v);
        pr.save(v);
    }

    public void deleteByCode(String code){
        pr.deleteById(code);
    }

    public void updatePays(String id, PaysEntity p) throws InvalidObjectException, NoSuchElementException {
        checkVille(p);
        try {
            PaysEntity pt = findByCode(id);

            pt.setNom(p.getNom());

            pr.save(pt);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
