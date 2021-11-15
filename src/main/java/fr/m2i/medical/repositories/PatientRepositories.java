package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PatientEntity;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepositories extends CrudRepository<PatientEntity, Integer> {

}
