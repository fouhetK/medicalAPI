package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepositories<T> extends CrudRepository<PatientEntity, Integer> {

    Iterable<PatientEntity> findAllByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(String nom, String prenom);

    Page<T> findAllByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(Pageable pageable, String nom, String prenom);

    Page<T> findAll(Pageable pageable);
}
