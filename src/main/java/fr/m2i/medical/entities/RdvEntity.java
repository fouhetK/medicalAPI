package fr.m2i.medical.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rdv", schema = "medical_jpa", catalog = "")
public class RdvEntity {
    private int id;
    private Timestamp dateheure;
    private Integer duree;
    private String note;
    private String type;
    private PatientEntity patient;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateheure")
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public Timestamp getDateheure() {
        return dateheure;
    }

    public void setDateheure(Timestamp dateheure) {
        this.dateheure = dateheure;
    }

    @Basic
    @Column(name = "duree")
    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdvEntity rdvEntity = (RdvEntity) o;
        return id == rdvEntity.id && Objects.equals(dateheure, rdvEntity.dateheure) && Objects.equals(duree, rdvEntity.duree) && Objects.equals(note, rdvEntity.note) && Objects.equals(type, rdvEntity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateheure, duree, note, type);
    }

    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
