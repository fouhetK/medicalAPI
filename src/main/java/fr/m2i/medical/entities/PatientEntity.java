package fr.m2i.medical.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "patient", schema = "medical_jpa", catalog = "")
public class PatientEntity {
    private int id;
    private String adresse;
    private Date datenaissance;
    private String nom;
    private String prenom;
    private PaysEntity paysByPaysCode;
    private VilleEntity villeByVilleId;

    public PatientEntity() {
    }

    public PatientEntity(int id, String adresse, Date datenaissance, String nom, String prenom, PaysEntity paysByPaysCode, VilleEntity villeByVilleId) {
        this.id = id;
        this.adresse = adresse;
        this.datenaissance = datenaissance;
        this.nom = nom;
        this.prenom = prenom;
        this.paysByPaysCode = paysByPaysCode;
        this.villeByVilleId = villeByVilleId;
    }

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
    @Column(name = "adresse")
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "datenaissance")
    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEntity that = (PatientEntity) o;
        return id == that.id && Objects.equals(adresse, that.adresse) && Objects.equals(datenaissance, that.datenaissance) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adresse, datenaissance, nom, prenom);
    }

    @ManyToOne
    @JoinColumn(name = "pays_code", referencedColumnName = "code")
    public PaysEntity getPaysByPaysCode() {
        return paysByPaysCode;
    }

    public void setPaysByPaysCode(PaysEntity paysByPaysCode) {
        this.paysByPaysCode = paysByPaysCode;
    }

    @ManyToOne
    @JoinColumn(name = "ville_id", referencedColumnName = "id")
    public VilleEntity getVilleByVilleId() {
        return villeByVilleId;
    }

    public void setVilleByVilleId(VilleEntity villeByVilleId) {
        this.villeByVilleId = villeByVilleId;
    }
}
