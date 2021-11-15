package fr.m2i.medical.api;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {
    PatientService ps;

    public PatientAPIController(PatientService ps) {
        this.ps = ps;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Iterable<PatientEntity> getAll() { return ps.findAll(); }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PatientEntity> getPatient(@PathVariable("id") int id) {
        try{
            PatientEntity p = ps.findById(id);
            return ResponseEntity.ok(p);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Patient introuvable" );
        }
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<PatientEntity> add(@RequestBody PatientEntity p){
        try{
            ps.savePatient(p);

            // création de l'url d'accès au nouvel objet => http://localhost:8080/api/ville/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( p.getId() ).toUri();

            return ResponseEntity.created( uri ).body(p);

        }catch ( InvalidObjectException e ){
            //return ResponseEntity.badRequest().build();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody PatientEntity p){
        try{
            ps.updatePatient(id, p);

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Patient introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        try {
            ps.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient introuvable");
        }
    }

}
