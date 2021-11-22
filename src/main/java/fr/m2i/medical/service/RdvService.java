package fr.m2i.medical.service;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.repositories.RdvRepositories;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.NoSuchElementException;

@Service
public class RdvService {
    private RdvRepositories rr;
    private PatientService ps;

    public RdvService(RdvRepositories rr){
        this.rr = rr;
    }

    private void checkRdv(RdvEntity v) throws InvalidObjectException {

    }

    public Iterable<RdvEntity> findAll() {
        return rr.findAll();
    }

    public Page<RdvEntity> findAllByPage(int page, String search, String datesearch) {
        Pageable p = PageRequest.of(page - 1, 5);

        if (search != null && datesearch != null)
            return rr.findAllByPatient_NomContainsAndDateheure_Date(p, search, Date.valueOf(datesearch));
        else if (search != null && datesearch == null){
            return rr.findAllByPatient_NomContains(p, search);
        }
        else if (search == null && datesearch != null){
            return rr.findAllByDateheure(p, Date.valueOf(datesearch));
        }
        else
            return rr.findAll(p);
    }

    public RdvEntity findById(int id) {
        return rr.findById(id).get();
    }

    public void deleteById(int id) {
        rr.deleteById(id);
    }

    public void saveRdv(RdvEntity rdv){
        rr.save(rdv);
    }

    public void updateRdv(int id, RdvEntity r) throws InvalidObjectException, NoSuchElementException {
        checkRdv(r);
        try {
            RdvEntity rt = findById(id);

            rr.save(rt);

        } catch (NoSuchElementException e) {
            throw e;
        }
    }
}
