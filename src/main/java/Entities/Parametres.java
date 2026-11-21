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
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "parametres")
@NamedQueries({
    @NamedQuery(name = "Parametres.findAll", query = "SELECT p FROM Parametres p"),
    @NamedQuery(name = "Parametres.findById", query = "SELECT p FROM Parametres p WHERE p.id = :id"),
    @NamedQuery(name = "Parametres.findByCle", query = "SELECT p FROM Parametres p WHERE p.cle = :cle"),
    @NamedQuery(name = "Parametres.findByValeur", query = "SELECT p FROM Parametres p WHERE p.valeur = :valeur"),
    @NamedQuery(name = "Parametres.findByDescription", query = "SELECT p FROM Parametres p WHERE p.description = :description")})
public class Parametres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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

    public Parametres() {
    }

    public Parametres(Integer id) {
        this.id = id;
    }

    public Parametres(Integer id, String cle, String valeur) {
        this.id = id;
        this.cle = cle;
        this.valeur = valeur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametres)) {
            return false;
        }
        Parametres other = (Parametres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(cle);
    }
    
}
