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
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "ecarts_stock")
@NamedQueries({
    @NamedQuery(name = "EcartsStock.findAll", query = "SELECT e FROM EcartsStock e"),
    @NamedQuery(name = "EcartsStock.findById", query = "SELECT e FROM EcartsStock e WHERE e.id = :id"),
    @NamedQuery(name = "EcartsStock.findByOperationId", query = "SELECT e FROM EcartsStock e WHERE e.operationId = :operationId"),
    @NamedQuery(name = "EcartsStock.findByQuantiteAttendue", query = "SELECT e FROM EcartsStock e WHERE e.quantiteAttendue = :quantiteAttendue"),
    @NamedQuery(name = "EcartsStock.findByQuantiteConstatee", query = "SELECT e FROM EcartsStock e WHERE e.quantiteConstatee = :quantiteConstatee"),
    @NamedQuery(name = "EcartsStock.findByDifference", query = "SELECT e FROM EcartsStock e WHERE e.difference = :difference"),
    @NamedQuery(name = "EcartsStock.findByJustification", query = "SELECT e FROM EcartsStock e WHERE e.justification = :justification"),
    @NamedQuery(name = "EcartsStock.findByStatut", query = "SELECT e FROM EcartsStock e WHERE e.statut = :statut"),
    @NamedQuery(name = "EcartsStock.findByValidePar", query = "SELECT e FROM EcartsStock e WHERE e.validePar = :validePar"),
    @NamedQuery(name = "EcartsStock.findByDateConstat", query = "SELECT e FROM EcartsStock e WHERE e.dateConstat = :dateConstat")})
public class EcartsStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "operation_id")
    private Integer operationId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_attendue")
    private BigDecimal quantiteAttendue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_constatee")
    private BigDecimal quantiteConstatee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "difference")
    private BigDecimal difference;
    @Size(max = 2147483647)
    @Column(name = "justification")
    private String justification;
    @Size(max = 30)
    @Column(name = "statut")
    private String statut;
    @Column(name = "valide_par")
    private Integer validePar;
    @Column(name = "date_constat")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateConstat;

    public EcartsStock() {
    }

    public EcartsStock(Integer id) {
        this.id = id;
    }

    public EcartsStock(Integer id, BigDecimal quantiteAttendue, BigDecimal quantiteConstatee, BigDecimal difference) {
        this.id = id;
        this.quantiteAttendue = quantiteAttendue;
        this.quantiteConstatee = quantiteConstatee;
        this.difference = difference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public BigDecimal getQuantiteAttendue() {
        return quantiteAttendue;
    }

    public void setQuantiteAttendue(BigDecimal quantiteAttendue) {
        this.quantiteAttendue = quantiteAttendue;
    }

    public BigDecimal getQuantiteConstatee() {
        return quantiteConstatee;
    }

    public void setQuantiteConstatee(BigDecimal quantiteConstatee) {
        this.quantiteConstatee = quantiteConstatee;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getValidePar() {
        return validePar;
    }

    public void setValidePar(Integer validePar) {
        this.validePar = validePar;
    }

    public Date getDateConstat() {
        return dateConstat;
    }

    public void setDateConstat(Date dateConstat) {
        this.dateConstat = dateConstat;
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
        if (!(object instanceof EcartsStock)) {
            return false;
        }
        EcartsStock other = (EcartsStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(statut) + " : " + String.valueOf(difference);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateConstat = now;
    }
}

