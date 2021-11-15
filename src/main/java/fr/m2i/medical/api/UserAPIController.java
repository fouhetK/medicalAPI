package fr.m2i.medical.api;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {
    UserService ps;

    public UserAPIController(UserService ps){
        this.ps = ps;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Iterable<UserEntity> getAll(){
        return ps.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public UserEntity getPatient(@PathVariable("id") int id) {
        return ps.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable("id") int id) {
        ps.deleteById(id);
    }
}
