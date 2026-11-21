/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import Entities.Historique;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.Utilisateurs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HistoriqueJpaController implements Serializable {

    public HistoriqueJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historique historique) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilisateurs utilisateurId = historique.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId = em.getReference(utilisateurId.getClass(), utilisateurId.getId());
                historique.setUtilisateurId(utilisateurId);
            }
            em.persist(historique);
            if (utilisateurId != null) {
                utilisateurId.getHistoriqueList().add(historique);
                utilisateurId = em.merge(utilisateurId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historique historique) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historique persistentHistorique = em.find(Historique.class, historique.getId());
            Utilisateurs utilisateurIdOld = persistentHistorique.getUtilisateurId();
            Utilisateurs utilisateurIdNew = historique.getUtilisateurId();
            if (utilisateurIdNew != null) {
                utilisateurIdNew = em.getReference(utilisateurIdNew.getClass(), utilisateurIdNew.getId());
                historique.setUtilisateurId(utilisateurIdNew);
            }
            historique = em.merge(historique);
            if (utilisateurIdOld != null && !utilisateurIdOld.equals(utilisateurIdNew)) {
                utilisateurIdOld.getHistoriqueList().remove(historique);
                utilisateurIdOld = em.merge(utilisateurIdOld);
            }
            if (utilisateurIdNew != null && !utilisateurIdNew.equals(utilisateurIdOld)) {
                utilisateurIdNew.getHistoriqueList().add(historique);
                utilisateurIdNew = em.merge(utilisateurIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historique.getId();
                if (findHistorique(id) == null) {
                    throw new NonexistentEntityException("The historique with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historique historique;
            try {
                historique = em.getReference(Historique.class, id);
                historique.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historique with id " + id + " no longer exists.", enfe);
            }
            Utilisateurs utilisateurId = historique.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId.getHistoriqueList().remove(historique);
                utilisateurId = em.merge(utilisateurId);
            }
            em.remove(historique);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historique> findHistoriqueEntities() {
        return findHistoriqueEntities(true, -1, -1);
    }

    public List<Historique> findHistoriqueEntities(int maxResults, int firstResult) {
        return findHistoriqueEntities(false, maxResults, firstResult);
    }

    private List<Historique> findHistoriqueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historique.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Historique findHistorique(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historique.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriqueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historique> rt = cq.from(Historique.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
