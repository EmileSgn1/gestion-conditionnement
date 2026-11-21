/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.PastOrPresent;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "alertes")
@NamedQueries({
    @NamedQuery(name = "Alertes.findAll", query = "SELECT a FROM Alertes a"),
    @NamedQuery(name = "Alertes.findById", query = "SELECT a FROM Alertes a WHERE a.id = :id"),
    @NamedQuery(name = "Alertes.findByTypeAlerte", query = "SELECT a FROM Alertes a WHERE a.typeAlerte = :typeAlerte"),
    @NamedQuery(name = "Alertes.findByMessage", query = "SELECT a FROM Alertes a WHERE a.message = :message"),
    @NamedQuery(name = "Alertes.findByDateAlerte", query = "SELECT a FROM Alertes a WHERE a.dateAlerte = :dateAlerte"),
    @NamedQuery(name = "Alertes.findByNiveau", query = "SELECT a FROM Alertes a WHERE a.niveau = :niveau"),
    @NamedQuery(name = "Alertes.findByLu", query = "SELECT a FROM Alertes a WHERE a.lu = :lu"),
    @NamedQuery(name = "Alertes.findByActif", query = "SELECT a FROM Alertes a WHERE a.actif = :actif"),
    @NamedQuery(name = "Alertes.findBySourceKey", query = "SELECT a FROM Alertes a WHERE a.sourceKey = :sourceKey"),
    @NamedQuery(name = "Alertes.findByRetiree", query = "SELECT a FROM Alertes a WHERE a.retiree = :retiree")})
public class Alertes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "type_alerte")
    private String typeAlerte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_alerte")
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date dateAlerte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "niveau")
    private String niveau;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lu")
    private boolean lu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @Size(max = 120)
    @Column(name = "source_key")
    private String sourceKey;
    @Basic(optional = false)
    @NotNull
    @Column(name = "retiree")
    private boolean retiree;
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleId;

    public Alertes() {
    }

    public Alertes(Long id) {
        this.id = id;
    }

    public Alertes(Long id, String typeAlerte, String message, Date dateAlerte, String niveau, boolean lu, boolean actif, boolean retiree) {
        this.id = id;
        this.typeAlerte = typeAlerte;
        this.message = message;
        this.dateAlerte = dateAlerte;
        this.niveau = niveau;
        this.lu = lu;
        this.actif = actif;
        this.retiree = retiree;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAlerte() {
        return typeAlerte;
    }

    public void setTypeAlerte(String typeAlerte) {
        this.typeAlerte = typeAlerte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateAlerte() {
        return dateAlerte;
    }

    public void setDateAlerte(Date dateAlerte) {
        this.dateAlerte = dateAlerte;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public boolean getLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public boolean getRetiree() {
        return retiree;
    }

    public void setRetiree(boolean retiree) {
        this.retiree = retiree;
    }

    public Articles getArticleId() {
        return articleId;
    }

    public void setArticleId(Articles articleId) {
        this.articleId = articleId;
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
        if (!(object instanceof Alertes)) {
            return false;
        }
        Alertes other = (Alertes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(typeAlerte) + " : " + String.valueOf(message);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateAlerte = now;
    }
}



