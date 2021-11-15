package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pays", schema = "medical_jpa", catalog = "")
public class PaysEntity {
    private String code;
    private String nom;

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaysEntity that = (PaysEntity) o;
        return Objects.equals(code, that.code) && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, nom);
    }
}
