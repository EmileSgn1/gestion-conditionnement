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
import jakarta.persistence.OneToMany;
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
@Table(name = "operations_conditionnement")
@NamedQueries({
    @NamedQuery(name = "OperationsConditionnement.findAll", query = "SELECT o FROM OperationsConditionnement o"),
    @NamedQuery(name = "OperationsConditionnement.findById", query = "SELECT o FROM OperationsConditionnement o WHERE o.id = :id"),
    @NamedQuery(name = "OperationsConditionnement.findByReference", query = "SELECT o FROM OperationsConditionnement o WHERE o.reference = :reference"),
    @NamedQuery(name = "OperationsConditionnement.findByQuantiteConsommee", query = "SELECT o FROM OperationsConditionnement o WHERE o.quantiteConsommee = :quantiteConsommee"),
    @NamedQuery(name = "OperationsConditionnement.findByQuantiteProduite", query = "SELECT o FROM OperationsConditionnement o WHERE o.quantiteProduite = :quantiteProduite"),
    @NamedQuery(name = "OperationsConditionnement.findByDateOperation", query = "SELECT o FROM OperationsConditionnement o WHERE o.dateOperation = :dateOperation"),
    @NamedQuery(name = "OperationsConditionnement.findByCommentaire", query = "SELECT o FROM OperationsConditionnement o WHERE o.commentaire = :commentaire"),
    @NamedQuery(name = "OperationsConditionnement.findByQuantiteConsomme", query = "SELECT o FROM OperationsConditionnement o WHERE o.quantiteConsomme = :quantiteConsomme"),
    @NamedQuery(name = "OperationsConditionnement.findByNombreConditionnements", query = "SELECT o FROM OperationsConditionnement o WHERE o.nombreConditionnements = :nombreConditionnements")})
public class OperationsConditionnement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "reference")
    private String reference;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_consommee")
    private BigDecimal quantiteConsommee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_produite")
    private BigDecimal quantiteProduite;
    @Column(name = "date_operation")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @PastOrPresent
    private Date dateOperation;
    @Size(max = 2147483647)
    @Column(name = "commentaire")
    private String commentaire;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_consomme")
    private BigDecimal quantiteConsomme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre_conditionnements")
    private BigDecimal nombreConditionnements;
    @JoinColumn(name = "article_produit_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleProduitId;
    @JoinColumn(name = "article_source_id", referencedColumnName = "id")
    @ManyToOne
    private Articles articleSourceId;
    @JoinColumn(name = "conditionnement_id", referencedColumnName = "id")
    @ManyToOne
    private Conditionnements conditionnementId;
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    @ManyToOne
    private Utilisateurs utilisateurId;
    @OneToMany(mappedBy = "operationId")
    private List<MouvementsStock> mouvementsStockList;

    public OperationsConditionnement() {
    }

    public OperationsConditionnement(Integer id) {
        this.id = id;
    }

    public OperationsConditionnement(Integer id, String reference, BigDecimal quantiteConsommee, BigDecimal quantiteProduite, BigDecimal quantiteConsomme, BigDecimal nombreConditionnements) {
        this.id = id;
        this.reference = reference;
        this.quantiteConsommee = quantiteConsommee;
        this.quantiteProduite = quantiteProduite;
        this.quantiteConsomme = quantiteConsomme;
        this.nombreConditionnements = nombreConditionnements;
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

    public BigDecimal getQuantiteConsommee() {
        return quantiteConsommee;
    }

    public void setQuantiteConsommee(BigDecimal quantiteConsommee) {
        this.quantiteConsommee = quantiteConsommee;
    }

    public BigDecimal getQuantiteProduite() {
        return quantiteProduite;
    }

    public void setQuantiteProduite(BigDecimal quantiteProduite) {
        this.quantiteProduite = quantiteProduite;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public BigDecimal getQuantiteConsomme() {
        return quantiteConsomme;
    }

    public void setQuantiteConsomme(BigDecimal quantiteConsomme) {
        this.quantiteConsomme = quantiteConsomme;
    }

    public BigDecimal getNombreConditionnements() {
        return nombreConditionnements;
    }

    public void setNombreConditionnements(BigDecimal nombreConditionnements) {
        this.nombreConditionnements = nombreConditionnements;
    }

    public Articles getArticleProduitId() {
        return articleProduitId;
    }

    public void setArticleProduitId(Articles articleProduitId) {
        this.articleProduitId = articleProduitId;
    }

    public Articles getArticleSourceId() {
        return articleSourceId;
    }

    public void setArticleSourceId(Articles articleSourceId) {
        this.articleSourceId = articleSourceId;
    }

    public Conditionnements getConditionnementId() {
        return conditionnementId;
    }

    public void setConditionnementId(Conditionnements conditionnementId) {
        this.conditionnementId = conditionnementId;
    }

    public Utilisateurs getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Utilisateurs utilisateurId) {
        this.utilisateurId = utilisateurId;
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
        if (!(object instanceof OperationsConditionnement)) {
            return false;
        }
        OperationsConditionnement other = (OperationsConditionnement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(reference == null ? "Opération " + id : reference);
    }
    

    @PrePersist
    public void initialiserDatesSysteme() {
        Date now = new Date();
        this.dateOperation = now;
    }
}

