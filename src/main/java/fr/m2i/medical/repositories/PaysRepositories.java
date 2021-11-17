package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.PaysEntity;
import fr.m2i.medical.entities.VilleEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaysRepositories extends CrudRepository<PaysEntity, String> {

}
