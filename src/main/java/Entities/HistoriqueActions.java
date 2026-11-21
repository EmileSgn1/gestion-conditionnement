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
@Table(name = "historique_actions")
@NamedQueries({
    @NamedQuery(name = "HistoriqueActions.findAll", query = "SELECT h FROM HistoriqueActions h"),
    @NamedQuery(name = "HistoriqueActions.findById", query = "SELECT h FROM HistoriqueActions h WHERE h.id = :id"),
    @NamedQuery(name = "HistoriqueActions.findByUtilisateurId", query = "SELECT h FROM HistoriqueActions h WHERE h.utilisateurId = :utilisateurId"),
    @NamedQuery(name = "HistoriqueActions.findByAction", query = "SELECT h FROM HistoriqueActions h WHERE h.action = :action"),
    @NamedQuery(name = "HistoriqueActions.findByDetails", query = "SELECT h FROM HistoriqueActions h WHERE h.details = :details"),
    @NamedQuery(name = "HistoriqueActions.findByAdresseIp", query = "SELECT h FROM HistoriqueActions h WHERE h.adresseIp = :adresseIp"),
    @NamedQuery(name = "HistoriqueActions.findByDateAction", query = "SELECT h FROM HistoriqueActions h WHERE h.dateAction = :dateAction")})
public class HistoriqueActions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "utilisateur_id")
    private Integer utilisateurId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "action")
    private String action;
    @Size(max = 2147483647)
    @Column(name = "details")
    private String details;
    @Size(max = 50)
    @Column(name = "adresse_ip")
    private String adresseIp;
    @Column(name = "date_action")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateAction;

    public HistoriqueActions() {
    }

    public HistoriqueActions(Integer id) {
        this.id = id;
    }

    public HistoriqueActions(Integer id, String action) {
        this.id = id;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
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

    public String getAdresseIp() {
        return adresseIp;
    }

    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }

    public Date getDateAction() {
        return dateAction;
    }

    public void setDateAction(Date dateAction) {
        this.dateAction = dateAction;
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
        if (!(object instanceof HistoriqueActions)) {
            return false;
        }
        HistoriqueActions other = (HistoriqueActions) object;
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

