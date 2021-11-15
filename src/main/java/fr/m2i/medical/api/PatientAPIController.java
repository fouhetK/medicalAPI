package fr.m2i.medical.api;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.repositories.PatientRepositories;
import fr.m2i.medical.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {
    PatientService ps;

    public PatientAPIController(PatientService ps){
        this.ps = ps;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Iterable<PatientEntity> getAll(){
        return ps.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public PatientEntity getPatient(@PathVariable("id") int id) {
        return ps.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        ps.deleteById(id);
    }
}
