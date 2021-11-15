package fr.m2i.medical.service;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.repositories.UserRepositories;
import fr.m2i.medical.repositories.VilleRepositories;
import org.springframework.stereotype.Service;

@Service
public class VilleService {
    private VilleRepositories vr;

    public VilleService(VilleRepositories vr){
        this.vr = vr;
    }

    public Iterable<VilleEntity> findAll() {
        return vr.findAll();
    }

    public VilleEntity findById(int id) {
        return vr.findById(id).get();
    }

    public Iterable<VilleEntity> findByPaysByPaysCode(String code){
        return vr.findByPaysByPaysCode(code);
    }

    public void deleteById(int id) {
        vr.deleteById(id);
    }
}
