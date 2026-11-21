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
@Table(name = "historique")
@NamedQueries({
    @NamedQuery(name = "Historique.findAll", query = "SELECT h FROM Historique h"),
    @NamedQuery(name = "Historique.findById", query = "SELECT h FROM Historique h WHERE h.id = :id"),
    @NamedQuery(name = "Historique.findByAction", query = "SELECT h FROM Historique h WHERE h.action = :action"),
    @NamedQuery(name = "Historique.findByDetails", query = "SELECT h FROM Historique h WHERE h.details = :details"),
    @NamedQuery(name = "Historique.findByDateAction", query = "SELECT h FROM Historique h WHERE h.dateAction = :dateAction"),
    @NamedQuery(name = "Historique.findByEntite", query = "SELECT h FROM Historique h WHERE h.entite = :entite"),
    @NamedQuery(name = "Historique.findByEntiteId", query = "SELECT h FROM Historique h WHERE h.entiteId = :entiteId")})
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "action")
    private String action;
    @Size(max = 2147483647)
    @Column(name = "details")
    private String details;
    @Column(name = "date_action")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateAction;
    @Size(max = 100)
    @Column(name = "entite")
    private String entite;
    @Column(name = "entite_id")
    private Integer entiteId;
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    @ManyToOne
    private Utilisateurs utilisateurId;

    public Historique() {
    }

    public Historique(Integer id) {
        this.id = id;
    }

    public Historique(Integer id, String action) {
        this.id = id;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
    }

    public String getEntite() {
        return entite;
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }

    public Integer getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(Integer entiteId) {
        this.entiteId = entiteId;
    }

    public Utilisateurs getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Utilisateurs utilisateurId) {
        this.utilisateurId = utilisateurId;
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
        if (!(object instanceof Historique)) {
            return false;
        }
        Historique other = (Historique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(action);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateAction = now;
    }
}

