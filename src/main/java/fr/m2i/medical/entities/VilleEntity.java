package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ville", schema = "medical_jpa", catalog = "")
public class VilleEntity {
    private int id;
    private String codePostal;
    private String nom;
    private PaysEntity paysByPaysCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "codePostal")
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
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
        VilleEntity that = (VilleEntity) o;
        return id == that.id && Objects.equals(codePostal, that.codePostal) && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codePostal, nom);
    }

    @ManyToOne
    @JoinColumn(name = "pays_code", referencedColumnName = "code")
    public PaysEntity getPaysByPaysCode() {
        return paysByPaysCode;
    }

    public void setPaysByPaysCode(PaysEntity paysByPaysCode) {
        this.paysByPaysCode = paysByPaysCode;
    }
}
