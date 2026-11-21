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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "unites")
@NamedQueries({
    @NamedQuery(name = "Unites.findAll", query = "SELECT u FROM Unites u"),
    @NamedQuery(name = "Unites.findById", query = "SELECT u FROM Unites u WHERE u.id = :id"),
    @NamedQuery(name = "Unites.findByCode", query = "SELECT u FROM Unites u WHERE u.code = :code"),
    @NamedQuery(name = "Unites.findBySigle", query = "SELECT u FROM Unites u WHERE u.sigle = :sigle"),
    @NamedQuery(name = "Unites.findByLibelle", query = "SELECT u FROM Unites u WHERE u.libelle = :libelle"),
    @NamedQuery(name = "Unites.findByNom", query = "SELECT u FROM Unites u WHERE u.nom = :nom"),
    @NamedQuery(name = "Unites.findByActif", query = "SELECT u FROM Unites u WHERE u.actif = :actif")})
public class Unites implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "code")
    private String code;
    @Size(max = 20)
    @Column(name = "sigle")
    private String sigle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "libelle")
    private String libelle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actif")
    private boolean actif;
    @OneToMany(mappedBy = "uniteId")
    private List<Articles> articlesList;

    public Unites() {
    }

    public Unites(Integer id) {
        this.id = id;
    }

    public Unites(Integer id, String code, String libelle, String nom, boolean actif) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.nom = nom;
        this.actif = actif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Articles> articlesList) {
        this.articlesList = articlesList;
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
        if (!(object instanceof Unites)) {
            return false;
        }
        Unites other = (Unites) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(libelle == null ? nom : libelle);
    }
    
}
