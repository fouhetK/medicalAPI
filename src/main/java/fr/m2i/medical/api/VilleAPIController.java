package fr.m2i.medical.api;

import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.VilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ville")
public class VilleAPIController {
    VilleService vs;

    public VilleAPIController(VilleService vs){
        this.vs = vs;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Iterable<VilleEntity> getAll(){
        return vs.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<VilleEntity> getVille(@PathVariable("id") int id) {
        try{
            VilleEntity v = vs.findById(id);
            return ResponseEntity.ok(v);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Ville non trouvé" );
        }
    }

    /*
    @GetMapping(value = "/{code}", produces = "application/json")
    public Iterable<VilleEntity> getPatient(@PathVariable("code") String code_pays) {
        return vs.findByPaysByPaysCode(code_pays);
    }
    */

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<VilleEntity> add(@RequestBody VilleEntity v){
        try{
            vs.saveVille(v);

            // création de l'url d'accès au nouvel objet => http://localhost:8080/api/ville/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( v.getId() ).toUri();

            return ResponseEntity.created( uri ).body(v);

        }catch ( InvalidObjectException e ){
            //return ResponseEntity.badRequest().build();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable("id") int id, @RequestBody VilleEntity v){
        try{
        vs.updateVille(id, v);

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Ville introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteVille(@PathVariable("id") int id) {
        try{
            vs.deleteById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Ville introuvable" );
        }
    }
}
