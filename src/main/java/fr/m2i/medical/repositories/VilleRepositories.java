package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface VilleRepositories<T> extends CrudRepository<VilleEntity, Integer> {

    Iterable<VilleEntity> findByPaysByPaysCode(String code);

    Iterable<VilleEntity> findAllByNomContainsIgnoreCaseOrCodePostalContains(String nom, String code);

    Page<T> findAllByNomContainsIgnoreCaseOrCodePostalContainsIgnoreCase(Pageable pageable, String nom, String code);

    Page<T> findAll(Pageable pageable);

}
