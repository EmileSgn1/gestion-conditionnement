/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "types_conditionnement")
@NamedQueries({
    @NamedQuery(name = "TypesConditionnement.findAll", query = "SELECT t FROM TypesConditionnement t"),
    @NamedQuery(name = "TypesConditionnement.findById", query = "SELECT t FROM TypesConditionnement t WHERE t.id = :id"),
    @NamedQuery(name = "TypesConditionnement.findByLibelle", query = "SELECT t FROM TypesConditionnement t WHERE t.libelle = :libelle"),
    @NamedQuery(name = "TypesConditionnement.findByCoefficientConversion", query = "SELECT t FROM TypesConditionnement t WHERE t.coefficientConversion = :coefficientConversion"),
    @NamedQuery(name = "TypesConditionnement.findByNom", query = "SELECT t FROM TypesConditionnement t WHERE t.nom = :nom"),
    @NamedQuery(name = "TypesConditionnement.findByDescription", query = "SELECT t FROM TypesConditionnement t WHERE t.description = :description"),
    @NamedQuery(name = "TypesConditionnement.findByActif", query = "SELECT t FROM TypesConditionnement t WHERE t.actif = :actif")})
public class TypesConditionnement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "libelle")
    private String libelle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "coefficient_conversion")
    private BigDecimal coefficientConversion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nom")
    private String nom;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeConditionnementId")
    private List<ConditionnementsCatalogue> conditionnementsCatalogueList;
    @OneToMany(mappedBy = "typeConditionnementId")
    private List<Conditionnements> conditionnementsList;

    public TypesConditionnement() {
    }

    public TypesConditionnement(Integer id) {
        this.id = id;
    }

    public TypesConditionnement(Integer id, String libelle, BigDecimal coefficientConversion, String nom, boolean actif) {
        this.id = id;
        this.libelle = libelle;
        this.coefficientConversion = coefficientConversion;
        this.nom = nom;
        this.actif = actif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getCoefficientConversion() {
        return coefficientConversion;
    }

    public void setCoefficientConversion(BigDecimal coefficientConversion) {
        this.coefficientConversion = coefficientConversion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<ConditionnementsCatalogue> getConditionnementsCatalogueList() {
        return conditionnementsCatalogueList;
    }

    public void setConditionnementsCatalogueList(List<ConditionnementsCatalogue> conditionnementsCatalogueList) {
        this.conditionnementsCatalogueList = conditionnementsCatalogueList;
    }

    public List<Conditionnements> getConditionnementsList() {
        return conditionnementsList;
    }

    public void setConditionnementsList(List<Conditionnements> conditionnementsList) {
        this.conditionnementsList = conditionnementsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypesConditionnement)) {
            return false;
        }
        TypesConditionnement other = (TypesConditionnement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(libelle == null ? nom : libelle);
    }
    
}
