package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepositories extends CrudRepository<PatientEntity, Integer> {

    Iterable<PatientEntity> findAllByNomLikeOrPrenomLike(String nom, String prenom);
    Iterable<PatientEntity> findAllByNomContainsOrPrenomContains(String nom, String prenom);
}
