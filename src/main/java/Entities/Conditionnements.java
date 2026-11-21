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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "conditionnements")
@NamedQueries({
    @NamedQuery(name = "Conditionnements.findAll", query = "SELECT c FROM Conditionnements c"),
    @NamedQuery(name = "Conditionnements.findById", query = "SELECT c FROM Conditionnements c WHERE c.id = :id"),
    @NamedQuery(name = "Conditionnements.findByQuantiteBase", query = "SELECT c FROM Conditionnements c WHERE c.quantiteBase = :quantiteBase"),
    @NamedQuery(name = "Conditionnements.findByCoefficient", query = "SELECT c FROM Conditionnements c WHERE c.coefficient = :coefficient"),
    @NamedQuery(name = "Conditionnements.findByActif", query = "SELECT c FROM Conditionnements c WHERE c.actif = :actif")})
public class Conditionnements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantite_base")
    private BigDecimal quantiteBase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "coefficient")
    private BigDecimal coefficient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleId;
    @JoinColumn(name = "article_source_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleSourceId;
    @JoinColumn(name = "type_conditionnement_id", referencedColumnName = "id")
    @ManyToOne
    private TypesConditionnement typeConditionnementId;
    @OneToMany(mappedBy = "conditionnementId")
    private List<OperationsConditionnement> operationsConditionnementList;

    public Conditionnements() {
    }

    public Conditionnements(Integer id) {
        this.id = id;
    }

    public Conditionnements(Integer id, BigDecimal coefficient, boolean actif) {
        this.id = id;
        this.coefficient = coefficient;
        this.actif = actif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getQuantiteBase() {
        return quantiteBase;
    }

    public void setQuantiteBase(BigDecimal quantiteBase) {
        this.quantiteBase = quantiteBase;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
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

    public Articles getArticleSourceId() {
        return articleSourceId;
    }

    public void setArticleSourceId(Articles articleSourceId) {
        this.articleSourceId = articleSourceId;
    }

    public TypesConditionnement getTypeConditionnementId() {
        return typeConditionnementId;
    }

    public void setTypeConditionnementId(TypesConditionnement typeConditionnementId) {
        this.typeConditionnementId = typeConditionnementId;
    }

    public List<OperationsConditionnement> getOperationsConditionnementList() {
        return operationsConditionnementList;
    }

    public void setOperationsConditionnementList(List<OperationsConditionnement> operationsConditionnementList) {
        this.operationsConditionnementList = operationsConditionnementList;
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
        if (!(object instanceof Conditionnements)) {
            return false;
        }
        Conditionnements other = (Conditionnements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(quantiteBase) + " — " + String.valueOf(typeConditionnementId);
    }
    
}
