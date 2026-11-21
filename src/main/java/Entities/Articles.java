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
import jakarta.persistence.OneToOne;
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
@Table(name = "articles")
@NamedQueries({
    @NamedQuery(name = "Articles.findAll", query = "SELECT a FROM Articles a"),
    @NamedQuery(name = "Articles.findById", query = "SELECT a FROM Articles a WHERE a.id = :id"),
    @NamedQuery(name = "Articles.findByReference", query = "SELECT a FROM Articles a WHERE a.reference = :reference"),
    @NamedQuery(name = "Articles.findByNom", query = "SELECT a FROM Articles a WHERE a.nom = :nom"),
    @NamedQuery(name = "Articles.findByDescription", query = "SELECT a FROM Articles a WHERE a.description = :description"),
    @NamedQuery(name = "Articles.findByStockMinimum", query = "SELECT a FROM Articles a WHERE a.stockMinimum = :stockMinimum"),
    @NamedQuery(name = "Articles.findByActif", query = "SELECT a FROM Articles a WHERE a.actif = :actif"),
    @NamedQuery(name = "Articles.findByDateCreation", query = "SELECT a FROM Articles a WHERE a.dateCreation = :dateCreation"),
    @NamedQuery(name = "Articles.findByDateModification", query = "SELECT a FROM Articles a WHERE a.dateModification = :dateModification"),
    @NamedQuery(name = "Articles.findByPrix", query = "SELECT a FROM Articles a WHERE a.prix = :prix"),
    @NamedQuery(name = "Articles.findByStockMin", query = "SELECT a FROM Articles a WHERE a.stockMin = :stockMin")})
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "reference")
    private String reference;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nom")
    private String nom;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stock_minimum")
    private BigDecimal stockMinimum;
    @Column(name = "actif")
    private Boolean actif;
    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateCreation;
    @Column(name = "date_modification")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateModification;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prix")
    private BigDecimal prix;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_min")
    private BigDecimal stockMin;
    @OneToMany(mappedBy = "articleId")
    private List<Alertes> alertesList;
    @OneToMany(mappedBy = "articleId")
    private List<Conditionnements> conditionnementsList;
    @OneToMany(mappedBy = "articleSourceId")
    private List<Conditionnements> conditionnementsList1;
    @OneToOne(mappedBy = "articleId")
    private Stocks stocks;
    @OneToMany(mappedBy = "articleProduitId")
    private List<OperationsConditionnement> operationsConditionnementList;
    @OneToMany(mappedBy = "articleSourceId")
    private List<OperationsConditionnement> operationsConditionnementList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
    private List<ArticleConditionnements> articleConditionnementsList;
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    @ManyToOne
    private Categories categorieId;
    @JoinColumn(name = "unite_id", referencedColumnName = "id")
    @ManyToOne
    private Unites uniteId;
    @OneToMany(mappedBy = "articleId")
    private List<MouvementsStock> mouvementsStockList;

    public Articles() {
    }

    public Articles(Integer id) {
        this.id = id;
    }

    public Articles(Integer id, String reference, String nom, BigDecimal prix, BigDecimal stockMin) {
        this.id = id;
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
        this.stockMin = stockMin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public BigDecimal getStockMinimum() {
        return stockMinimum;
    }

    public void setStockMinimum(BigDecimal stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public List<Alertes> getAlertesList() {
        return alertesList;
    }

    public void setAlertesList(List<Alertes> alertesList) {
        this.alertesList = alertesList;
    }

    public List<Conditionnements> getConditionnementsList() {
        return conditionnementsList;
    }

    public void setConditionnementsList(List<Conditionnements> conditionnementsList) {
        this.conditionnementsList = conditionnementsList;
    }

    public List<Conditionnements> getConditionnementsList1() {
        return conditionnementsList1;
    }

    public void setConditionnementsList1(List<Conditionnements> conditionnementsList1) {
        this.conditionnementsList1 = conditionnementsList1;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public List<OperationsConditionnement> getOperationsConditionnementList() {
        return operationsConditionnementList;
    }

    public void setOperationsConditionnementList(List<OperationsConditionnement> operationsConditionnementList) {
        this.operationsConditionnementList = operationsConditionnementList;
    }

    public List<OperationsConditionnement> getOperationsConditionnementList1() {
        return operationsConditionnementList1;
    }

    public void setOperationsConditionnementList1(List<OperationsConditionnement> operationsConditionnementList1) {
        this.operationsConditionnementList1 = operationsConditionnementList1;
    }

    public List<ArticleConditionnements> getArticleConditionnementsList() {
        return articleConditionnementsList;
    }

    public void setArticleConditionnementsList(List<ArticleConditionnements> articleConditionnementsList) {
        this.articleConditionnementsList = articleConditionnementsList;
    }

    public Categories getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Categories categorieId) {
        this.categorieId = categorieId;
    }

    public Unites getUniteId() {
        return uniteId;
    }

    public void setUniteId(Unites uniteId) {
        this.uniteId = uniteId;
    }

    public List<MouvementsStock> getMouvementsStockList() {
        return mouvementsStockList;
    }

    public void setMouvementsStockList(List<MouvementsStock> mouvementsStockList) {
        this.mouvementsStockList = mouvementsStockList;
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
        if (!(object instanceof Articles)) {
            return false;
        }
        Articles other = (Articles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(nom == null ? reference : nom);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateCreation = now;
        this.dateModification = now;
    }

    @PreUpdate
    public void actualiserDatesSysteme() {
        Date now = new Date();
        this.dateModification = now;
    }
}

