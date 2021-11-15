package fr.m2i.medical.api;

import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.VilleService;
import org.springframework.web.bind.annotation.*;

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
    public VilleEntity getPatient(@PathVariable("id") int id) {
        return vs.findById(id);
    }

    /*
    @GetMapping(value = "/{code}", produces = "application/json")
    public Iterable<VilleEntity> getPatient(@PathVariable("code") String code_pays) {
        return vs.findByPaysByPaysCode(code_pays);
    }
    */

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        vs.deleteById(id);
    }
}
