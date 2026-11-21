/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "conditionnements_catalogue")
@NamedQueries({
    @NamedQuery(name = "ConditionnementsCatalogue.findAll", query = "SELECT c FROM ConditionnementsCatalogue c"),
    @NamedQuery(name = "ConditionnementsCatalogue.findById", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.id = :id"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByCode", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.code = :code"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByDesignation", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.designation = :designation"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByCapacite", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.capacite = :capacite"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByUniteCapacite", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.uniteCapacite = :uniteCapacite"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByLongueur", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.longueur = :longueur"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByLargeur", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.largeur = :largeur"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByHauteur", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.hauteur = :hauteur"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByPoids", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.poids = :poids"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByMateriau", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.materiau = :materiau"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByActif", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.actif = :actif"),
    @NamedQuery(name = "ConditionnementsCatalogue.findByDateCreation", query = "SELECT c FROM ConditionnementsCatalogue c WHERE c.dateCreation = :dateCreation")})
public class ConditionnementsCatalogue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "designation")
    private String designation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "capacite")
    private BigDecimal capacite;
    @Size(max = 30)
    @Column(name = "unite_capacite")
    private String uniteCapacite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longueur")
    private BigDecimal longueur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "largeur")
    private BigDecimal largeur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hauteur")
    private BigDecimal hauteur;
    @Column(name = "poids")
    private BigDecimal poids;
    @Size(max = 100)
    @Column(name = "materiau")
    private String materiau;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date dateCreation;
    @JoinColumn(name = "type_conditionnement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TypesConditionnement typeConditionnementId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conditionnementId")
    private List<ArticleConditionnements> articleConditionnementsList;

    public ConditionnementsCatalogue() {
    }

    public ConditionnementsCatalogue(Integer id) {
        this.id = id;
    }

    public ConditionnementsCatalogue(Integer id, String code, String designation, BigDecimal longueur, BigDecimal largeur, BigDecimal hauteur, boolean actif, Date dateCreation) {
        this.id = id;
        this.code = code;
        this.designation = designation;
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.actif = actif;
        this.dateCreation = dateCreation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getCapacite() {
        return capacite;
    }

    public void setCapacite(BigDecimal capacite) {
        this.capacite = capacite;
    }

    public String getUniteCapacite() {
        return uniteCapacite;
    }

    public void setUniteCapacite(String uniteCapacite) {
        this.uniteCapacite = uniteCapacite;
    }

    public BigDecimal getLongueur() {
        return longueur;
    }

    public void setLongueur(BigDecimal longueur) {
        this.longueur = longueur;
    }

    public BigDecimal getLargeur() {
        return largeur;
    }

    public void setLargeur(BigDecimal largeur) {
        this.largeur = largeur;
    }

    public BigDecimal getHauteur() {
        return hauteur;
    }

    public void setHauteur(BigDecimal hauteur) {
        this.hauteur = hauteur;
    }

    public BigDecimal getPoids() {
        return poids;
    }

    public void setPoids(BigDecimal poids) {
        this.poids = poids;
    }

    public String getMateriau() {
        return materiau;
    }

    public void setMateriau(String materiau) {
        this.materiau = materiau;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TypesConditionnement getTypeConditionnementId() {
        return typeConditionnementId;
    }

    public void setTypeConditionnementId(TypesConditionnement typeConditionnementId) {
        this.typeConditionnementId = typeConditionnementId;
    }

    public List<ArticleConditionnements> getArticleConditionnementsList() {
        return articleConditionnementsList;
    }

    public void setArticleConditionnementsList(List<ArticleConditionnements> articleConditionnementsList) {
        this.articleConditionnementsList = articleConditionnementsList;
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
        if (!(object instanceof ConditionnementsCatalogue)) {
            return false;
        }
        ConditionnementsCatalogue other = (ConditionnementsCatalogue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(designation == null ? code : designation);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateCreation = now;
    }
}



