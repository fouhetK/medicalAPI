package fr.m2i.medical.repositories;

import fr.m2i.medical.entities.RdvEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.sql.Timestamp;

public interface RdvRepositories extends CrudRepository<RdvEntity, Integer> {

    @Query(value = "Select * from Rdv r inner join Patient p on r.patient=p.id WHERE p.nom LIKE %?1% AND DATE(r.dateheure) = ?2", nativeQuery = true)
    Page<RdvEntity> findAllByPatient_NomContainsAndDateheure_Date(Pageable pageable, String patient, Date time);

    @Query(value = "Select * from Rdv r WHERE DATE(r.dateheure) = ?1", nativeQuery = true)
    Page<RdvEntity> findAllByDateheure(Pageable pageable, Date date);

    Page<RdvEntity> findAllByPatient_NomContains(Pageable pageable, String patient);
    Page<RdvEntity> findAll(Pageable pageable);

}
