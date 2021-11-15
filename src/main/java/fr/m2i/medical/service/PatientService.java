package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepositories;
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
        } else if (!Pattern.compile(regexPattern).matcher(p.getAdresse()).matches()){
            throw new InvalidObjectException("Adresse du patient invalide");
        } else if (p.getDatenaissance() == null){
            throw new InvalidObjectException("Date de naissance du patient invalide");
        }
        try {
            p.setVilleByVilleId(vs.findById(p.getVilleByVilleId().getId()));
        } catch (Exception e) {
            throw new InvalidObjectException("La ville n'est pas valide");
        }
        if (!p.getVilleByVilleId().getPaysByPaysCode().getCode().equals(p.getPaysByPaysCode().getCode())){
            throw new InvalidObjectException("La ville ne fait pas partie du pays choisi");
        }
    }


    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
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
            PatientEntity pt = pr.findById(id).get();
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public void savePatient(PatientEntity p) throws InvalidObjectException {
        checkPatient(p);
        pr.save(p);
    }
}
