/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "article_conditionnements")
@NamedQueries({
    @NamedQuery(name = "ArticleConditionnements.findAll", query = "SELECT a FROM ArticleConditionnements a"),
    @NamedQuery(name = "ArticleConditionnements.findById", query = "SELECT a FROM ArticleConditionnements a WHERE a.id = :id"),
    @NamedQuery(name = "ArticleConditionnements.findByQuantiteParUnite", query = "SELECT a FROM ArticleConditionnements a WHERE a.quantiteParUnite = :quantiteParUnite"),
    @NamedQuery(name = "ArticleConditionnements.findByStockConditionne", query = "SELECT a FROM ArticleConditionnements a WHERE a.stockConditionne = :stockConditionne"),
    @NamedQuery(name = "ArticleConditionnements.findBySeuilAlerte", query = "SELECT a FROM ArticleConditionnements a WHERE a.seuilAlerte = :seuilAlerte"),
    @NamedQuery(name = "ArticleConditionnements.findByActif", query = "SELECT a FROM ArticleConditionnements a WHERE a.actif = :actif")})
public class ArticleConditionnements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_par_unite")
    private BigDecimal quantiteParUnite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_conditionne")
    private BigDecimal stockConditionne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seuil_alerte")
    private BigDecimal seuilAlerte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Articles articleId;
    @JoinColumn(name = "conditionnement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ConditionnementsCatalogue conditionnementId;

    public ArticleConditionnements() {
    }

    public ArticleConditionnements(Integer id) {
        this.id = id;
    }

    public ArticleConditionnements(Integer id, BigDecimal quantiteParUnite, BigDecimal stockConditionne, BigDecimal seuilAlerte, boolean actif) {
        this.id = id;
        this.quantiteParUnite = quantiteParUnite;
        this.stockConditionne = stockConditionne;
        this.seuilAlerte = seuilAlerte;
        this.actif = actif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getQuantiteParUnite() {
        return quantiteParUnite;
    }

    public void setQuantiteParUnite(BigDecimal quantiteParUnite) {
        this.quantiteParUnite = quantiteParUnite;
    }

    public BigDecimal getStockConditionne() {
        return stockConditionne;
    }

    public void setStockConditionne(BigDecimal stockConditionne) {
        this.stockConditionne = stockConditionne;
    }

    public BigDecimal getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(BigDecimal seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Articles getArticleId() {
        return articleId;
    }

    public void setArticleId(Articles articleId) {
        this.articleId = articleId;
    }

    public ConditionnementsCatalogue getConditionnementId() {
        return conditionnementId;
    }

    public void setConditionnementId(ConditionnementsCatalogue conditionnementId) {
        this.conditionnementId = conditionnementId;
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
        if (!(object instanceof ArticleConditionnements)) {
            return false;
        }
        ArticleConditionnements other = (ArticleConditionnements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Article " + articleId + " — conditionnement " + conditionnementId;
    }
    
}
