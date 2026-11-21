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
@Table(name = "parametres_application")
@NamedQueries({
    @NamedQuery(name = "ParametresApplication.findAll", query = "SELECT p FROM ParametresApplication p"),
    @NamedQuery(name = "ParametresApplication.findById", query = "SELECT p FROM ParametresApplication p WHERE p.id = :id"),
    @NamedQuery(name = "ParametresApplication.findByCle", query = "SELECT p FROM ParametresApplication p WHERE p.cle = :cle"),
    @NamedQuery(name = "ParametresApplication.findByValeur", query = "SELECT p FROM ParametresApplication p WHERE p.valeur = :valeur"),
    @NamedQuery(name = "ParametresApplication.findByDescription", query = "SELECT p FROM ParametresApplication p WHERE p.description = :description"),
    @NamedQuery(name = "ParametresApplication.findByDateModification", query = "SELECT p FROM ParametresApplication p WHERE p.dateModification = :dateModification")})
public class ParametresApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cle")
    private String cle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "valeur")
    private String valeur;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_modification")
    @Temporal(TemporalType.TIMESTAMP)
    @PastOrPresent
    private Date dateModification;

    public ParametresApplication() {
    }

    public ParametresApplication(Long id) {
        this.id = id;
    }

    public ParametresApplication(Long id, String cle, String valeur, Date dateModification) {
        this.id = id;
        this.cle = cle;
        this.valeur = valeur;
        this.dateModification = dateModification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCle() {
        return cle;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
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
        if (!(object instanceof ParametresApplication)) {
            return false;
        }
        ParametresApplication other = (ParametresApplication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(cle);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateModification = now;
    }

    @PreUpdate
    public void actualiserDatesSysteme() {
        Date now = new Date();
        this.dateModification = now;
    }
}



