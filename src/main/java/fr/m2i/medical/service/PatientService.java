package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class PatientService {
    private PatientRepositories pr;
    private VilleService vs;

    public PatientService(PatientRepositories pr, VilleService vs){
        this.pr = pr;
        this.vs = vs;
    }

    private void checkPatient(PatientEntity p) throws InvalidObjectException {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (p.getNom().length() <= 2) {
            throw new InvalidObjectException("Nom du patient invalide");
        } else if (p.getPrenom().length() <= 2) {
            throw new InvalidObjectException("Prenom du patient invalide");
        } else if (!Pattern.compile(regexPattern).matcher(p.getEmail()).matches()){
            throw new InvalidObjectException("Email du patient invalide");
        } else if (p.getAdresse().length() <= 10){
            throw new InvalidObjectException("Adresse du patient invalide");
        }
        try {
            p.setVilleId(vs.findById(p.getVilleId().getId()));
        } catch (Exception e) {
            throw new InvalidObjectException("La ville n'est pas valide");
        }
        if (!p.getVilleId().getPaysByPaysCode().getCode().equals(p.getPaysCode().getCode())){
            throw new InvalidObjectException("La ville ne fait pas partie du pays choisi");
        }
    }


    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public Iterable<PatientEntity> findAll(String nom) {
        if (nom == null)
            return findAll();
        else
            return findAllByNomContainsOrPrenomContains(nom , nom);
    }

    public Page<PatientEntity> findAllByPage(int page, String nom) {
        Pageable p = PageRequest.of(page - 1, 5);

        if (nom == null)
            return pr.findAll(p);
        else
            return pr.findAllByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(p, nom , nom);
    }

    public Iterable<PatientEntity> findAllByNomContainsOrPrenomContains(String nom, String prenom){
        return pr.findAllByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(nom, prenom);
    }

    public PatientEntity findById(int id) {
        return pr.findById(id).get();
    }

    public void deleteById(int id) {
        pr.deleteById(id);
    }

    public void updatePatient(int id, PatientEntity p) throws InvalidObjectException, NoSuchElementException {
        checkPatient(p);
        try {
            PatientEntity pt = findById(id);

            pt.setNom(p.getNom());
            pt.setPrenom(p.getPrenom());
            pt.setDatenaissance(p.getDatenaissance());
            pt.setAdresse(p.getAdresse());
            pt.setEmail(p.getEmail());
            pt.setTelephone(p.getTelephone());
            pt.setVilleId(p.getVilleId());
            pt.setPaysCode(pt.getVilleId().getPaysByPaysCode());

            pr.save(pt);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public void savePatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }
}
