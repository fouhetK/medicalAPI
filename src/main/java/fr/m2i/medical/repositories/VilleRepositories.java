package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

public interface VilleRepositories extends CrudRepository<VilleEntity, Integer> {

    Iterable<VilleEntity> findByPaysByPaysCode(String code);

}
