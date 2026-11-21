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
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "mouvements_stock")
@NamedQueries({
    @NamedQuery(name = "MouvementsStock.findAll", query = "SELECT m FROM MouvementsStock m"),
    @NamedQuery(name = "MouvementsStock.findById", query = "SELECT m FROM MouvementsStock m WHERE m.id = :id"),
    @NamedQuery(name = "MouvementsStock.findByTypeMouvement", query = "SELECT m FROM MouvementsStock m WHERE m.typeMouvement = :typeMouvement"),
    @NamedQuery(name = "MouvementsStock.findByQuantite", query = "SELECT m FROM MouvementsStock m WHERE m.quantite = :quantite"),
    @NamedQuery(name = "MouvementsStock.findByMotif", query = "SELECT m FROM MouvementsStock m WHERE m.motif = :motif"),
    @NamedQuery(name = "MouvementsStock.findByReference", query = "SELECT m FROM MouvementsStock m WHERE m.reference = :reference"),
    @NamedQuery(name = "MouvementsStock.findByDateMouvement", query = "SELECT m FROM MouvementsStock m WHERE m.dateMouvement = :dateMouvement")})
public class MouvementsStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type_mouvement")
    private String typeMouvement;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite")
    private BigDecimal quantite;
    @Size(max = 2147483647)
    @Column(name = "motif")
    private String motif;
    @Size(max = 100)
    @Column(name = "reference")
    private String reference;
    @Column(name = "date_mouvement")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateMouvement;
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleId;
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    @ManyToOne
    private OperationsConditionnement operationId;
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    @ManyToOne
    private Utilisateurs utilisateurId;

    public MouvementsStock() {
    }

    public MouvementsStock(Integer id) {
        this.id = id;
    }

    public MouvementsStock(Integer id, String typeMouvement, BigDecimal quantite) {
        this.id = id;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(Date dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Articles getArticleId() {
        return articleId;
    }

    public void setArticleId(Articles articleId) {
        this.articleId = articleId;
    }

    public OperationsConditionnement getOperationId() {
        return operationId;
    }

    public void setOperationId(OperationsConditionnement operationId) {
        this.operationId = operationId;
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
        if (!(object instanceof MouvementsStock)) {
            return false;
        }
        MouvementsStock other = (MouvementsStock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(reference == null ? typeMouvement : reference);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateMouvement = now;
    }
}

