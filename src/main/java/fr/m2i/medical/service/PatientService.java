package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepositories pr;

    public PatientService(PatientRepositories pr){
        this.pr = pr;
    }

    public Iterable<PatientEntity> findAll() {
        return pr.findAll();
    }

    public PatientEntity findById(int id) {
        return pr.findById(id).get();
    }

    public void delete(int p) {
    }

    public void deleteById(int id) {
        pr.deleteById(id);
    }
}
