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
@Table(name = "compositions_conditionnements")
@NamedQueries({
    @NamedQuery(name = "CompositionsConditionnements.findAll", query = "SELECT c FROM CompositionsConditionnements c"),
    @NamedQuery(name = "CompositionsConditionnements.findById", query = "SELECT c FROM CompositionsConditionnements c WHERE c.id = :id"),
    @NamedQuery(name = "CompositionsConditionnements.findByArticleId", query = "SELECT c FROM CompositionsConditionnements c WHERE c.articleId = :articleId"),
    @NamedQuery(name = "CompositionsConditionnements.findByConditionnementDepartId", query = "SELECT c FROM CompositionsConditionnements c WHERE c.conditionnementDepartId = :conditionnementDepartId"),
    @NamedQuery(name = "CompositionsConditionnements.findByConditionnementObtenuId", query = "SELECT c FROM CompositionsConditionnements c WHERE c.conditionnementObtenuId = :conditionnementObtenuId"),
    @NamedQuery(name = "CompositionsConditionnements.findByQuantiteTheorique", query = "SELECT c FROM CompositionsConditionnements c WHERE c.quantiteTheorique = :quantiteTheorique"),
    @NamedQuery(name = "CompositionsConditionnements.findByActif", query = "SELECT c FROM CompositionsConditionnements c WHERE c.actif = :actif")})
public class CompositionsConditionnements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "conditionnement_depart_id")
    private Integer conditionnementDepartId;
    @Column(name = "conditionnement_obtenu_id")
    private Integer conditionnementObtenuId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_theorique")
    private BigDecimal quantiteTheorique;
    @Column(name = "actif")
    private Boolean actif;

    public CompositionsConditionnements() {
    }

    public CompositionsConditionnements(Integer id) {
        this.id = id;
    }

    public CompositionsConditionnements(Integer id, BigDecimal quantiteTheorique) {
        this.id = id;
        this.quantiteTheorique = quantiteTheorique;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getConditionnementDepartId() {
        return conditionnementDepartId;
    }

    public void setConditionnementDepartId(Integer conditionnementDepartId) {
        this.conditionnementDepartId = conditionnementDepartId;
    }

    public Integer getConditionnementObtenuId() {
        return conditionnementObtenuId;
    }

    public void setConditionnementObtenuId(Integer conditionnementObtenuId) {
        this.conditionnementObtenuId = conditionnementObtenuId;
    }

    public BigDecimal getQuantiteTheorique() {
        return quantiteTheorique;
    }

    public void setQuantiteTheorique(BigDecimal quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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
        if (!(object instanceof CompositionsConditionnements)) {
            return false;
        }
        CompositionsConditionnements other = (CompositionsConditionnements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Composition de conditionnement";
    }
    
}
