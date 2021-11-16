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
    private String email;
    private String telephone;
    private String nom;
    private String prenom;
    private PaysEntity paysCode;
    private VilleEntity villeId;

    public PatientEntity() {
    }

    public PatientEntity(int id, String nom, String prenom, Date dateNaissance, String email, String telephone, String adresse, VilleEntity ville, PaysEntity pays) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.villeId = ville;
        this.paysCode = pays;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        return id == that.id && Objects.equals(adresse, that.adresse) && Objects.equals(datenaissance, that.datenaissance) && Objects.equals(email, that.email) && Objects.equals(telephone, that.telephone) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adresse, datenaissance, email, telephone, nom, prenom);
    }

    @OneToOne
    @JoinColumn(name = "pays_code", referencedColumnName = "code")
    public PaysEntity getPaysCode() {
        return paysCode;
    }

    public void setPaysCode(PaysEntity paysCode) {
        this.paysCode = paysCode;
    }

    @OneToOne
    @JoinColumn(name = "ville_id", referencedColumnName = "id")
    public VilleEntity getVilleId() {
        return villeId;
    }

    public void setVilleId(VilleEntity villeId) {
        this.villeId = villeId;
    }
}
