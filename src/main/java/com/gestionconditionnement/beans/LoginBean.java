package com.gestionconditionnement.beans;

import Entities.Utilisateurs;
import jakarta.enterprise.context.SessionScoped;
import jakarta.annotation.Resource;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoginBean.class.getName());

    private String email;
    private String password;
    private Utilisateurs utilisateurConnecte;

    @Resource
    private UserTransaction userTransaction;

    @PersistenceUnit(unitName = "my_persistence_unit")
    private EntityManagerFactory emf;

    public String seConnecter() {
        FacesContext faces = FacesContext.getCurrentInstance();
        String destination = null;

        try (EntityManager em = emf.createEntityManager()) {
            String emailSaisi = email == null ? "" : email.trim();

            Utilisateurs user = em.createQuery(
                    "SELECT u FROM Utilisateurs u WHERE LOWER(u.email) = LOWER(:email) AND u.actif = true",
                    Utilisateurs.class)
                    .setParameter("email", emailSaisi)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (user != null && com.gestionconditionnement.security.PasswordHasher.verify(password, user.getMotDePasse())) {
                if (!com.gestionconditionnement.security.PasswordHasher.isEncoded(user.getMotDePasse())) {
                    upgradeLegacyPassword(em, user, password);
                }
                String role = user.getRole() == null ? "" : user.getRole().trim().toUpperCase(Locale.ROOT);
                String page = switch (role) {
                    case "ADMIN", "ADMINISTRATEUR" -> "admin-dashboard.xhtml";
                    case "GESTIONNAIRE" -> "gestionnaire-dashboard.xhtml";
                    case "OPERATEUR", "OPÉRATEUR" -> "operateur-dashboard.xhtml";
                    case "CONSULTANT", "UTILISATEUR" -> "index.xhtml";
                    default -> null;
                };

                if (page != null) {
                    utilisateurConnecte = user;
                    HttpServletRequest request = (HttpServletRequest) faces.getExternalContext().getRequest();
                    request.changeSessionId();
                    destination = faces.getExternalContext().getRequestContextPath() + "/faces/" + page;
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Échec de la vérification des identifiants", ex);
        }

        password = null;

        if (destination != null) {
            try {
                faces.getExternalContext().redirect(destination);
                faces.responseComplete();
                return null;
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Impossible de rediriger après authentification", ex);
            }
        }

        utilisateurConnecte = null;
        faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Connexion refusée", "Vérifiez votre adresse e-mail, votre mot de passe et l’état de votre compte."));
        return null;
    }

    private void upgradeLegacyPassword(EntityManager em, Utilisateurs user, String rawPassword) {
        try {
            String encoded = com.gestionconditionnement.security.PasswordHasher.hash(rawPassword);
            userTransaction.begin();
            em.joinTransaction();
            int updated = em.createQuery("UPDATE Utilisateurs u SET u.motDePasse = :password WHERE u.id = :id")
                    .setParameter("password", encoded)
                    .setParameter("id", user.getId())
                    .executeUpdate();
            userTransaction.commit();
            if (updated == 1) user.setMotDePasse(encoded);
        } catch (Exception exception) {
            try {
                if (userTransaction != null) userTransaction.rollback();
            } catch (Exception rollbackException) {
                exception.addSuppressed(rollbackException);
            }
            LOGGER.log(Level.WARNING, "Impossible de migrer un mot de passe historique vers le stockage sécurisé", exception);
        }
    }

    public String seDeconnecter() {
        utilisateurConnecte = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateurs getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateurs utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }
}
