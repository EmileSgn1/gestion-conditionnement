/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.Articles;
import Entities.Conditionnements;
import Entities.Utilisateurs;
import Entities.MouvementsStock;
import Entities.OperationsConditionnement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class OperationsConditionnementJpaController implements Serializable {

    public OperationsConditionnementJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OperationsConditionnement operationsConditionnement) throws RollbackFailureException, Exception {
        if (operationsConditionnement.getMouvementsStockList() == null) {
            operationsConditionnement.setMouvementsStockList(new ArrayList<MouvementsStock>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleProduitId = operationsConditionnement.getArticleProduitId();
            if (articleProduitId != null) {
                articleProduitId = em.getReference(articleProduitId.getClass(), articleProduitId.getId());
                operationsConditionnement.setArticleProduitId(articleProduitId);
            }
            Articles articleSourceId = operationsConditionnement.getArticleSourceId();
            if (articleSourceId != null) {
                articleSourceId = em.getReference(articleSourceId.getClass(), articleSourceId.getId());
                operationsConditionnement.setArticleSourceId(articleSourceId);
            }
            Conditionnements conditionnementId = operationsConditionnement.getConditionnementId();
            if (conditionnementId != null) {
                conditionnementId = em.getReference(conditionnementId.getClass(), conditionnementId.getId());
                operationsConditionnement.setConditionnementId(conditionnementId);
            }
            Utilisateurs utilisateurId = operationsConditionnement.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId = em.getReference(utilisateurId.getClass(), utilisateurId.getId());
                operationsConditionnement.setUtilisateurId(utilisateurId);
            }
            List<MouvementsStock> attachedMouvementsStockList = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListMouvementsStockToAttach : operationsConditionnement.getMouvementsStockList()) {
                mouvementsStockListMouvementsStockToAttach = em.getReference(mouvementsStockListMouvementsStockToAttach.getClass(), mouvementsStockListMouvementsStockToAttach.getId());
                attachedMouvementsStockList.add(mouvementsStockListMouvementsStockToAttach);
            }
            operationsConditionnement.setMouvementsStockList(attachedMouvementsStockList);
            em.persist(operationsConditionnement);
            if (articleProduitId != null) {
                articleProduitId.getOperationsConditionnementList().add(operationsConditionnement);
                articleProduitId = em.merge(articleProduitId);
            }
            if (articleSourceId != null) {
                articleSourceId.getOperationsConditionnementList().add(operationsConditionnement);
                articleSourceId = em.merge(articleSourceId);
            }
            if (conditionnementId != null) {
                conditionnementId.getOperationsConditionnementList().add(operationsConditionnement);
                conditionnementId = em.merge(conditionnementId);
            }
            if (utilisateurId != null) {
                utilisateurId.getOperationsConditionnementList().add(operationsConditionnement);
                utilisateurId = em.merge(utilisateurId);
            }
            for (MouvementsStock mouvementsStockListMouvementsStock : operationsConditionnement.getMouvementsStockList()) {
                OperationsConditionnement oldOperationIdOfMouvementsStockListMouvementsStock = mouvementsStockListMouvementsStock.getOperationId();
                mouvementsStockListMouvementsStock.setOperationId(operationsConditionnement);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
                if (oldOperationIdOfMouvementsStockListMouvementsStock != null) {
                    oldOperationIdOfMouvementsStockListMouvementsStock.getMouvementsStockList().remove(mouvementsStockListMouvementsStock);
                    oldOperationIdOfMouvementsStockListMouvementsStock = em.merge(oldOperationIdOfMouvementsStockListMouvementsStock);
                }
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

    public void edit(OperationsConditionnement operationsConditionnement) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OperationsConditionnement persistentOperationsConditionnement = em.find(OperationsConditionnement.class, operationsConditionnement.getId());
            Articles articleProduitIdOld = persistentOperationsConditionnement.getArticleProduitId();
            Articles articleProduitIdNew = operationsConditionnement.getArticleProduitId();
            Articles articleSourceIdOld = persistentOperationsConditionnement.getArticleSourceId();
            Articles articleSourceIdNew = operationsConditionnement.getArticleSourceId();
            Conditionnements conditionnementIdOld = persistentOperationsConditionnement.getConditionnementId();
            Conditionnements conditionnementIdNew = operationsConditionnement.getConditionnementId();
            Utilisateurs utilisateurIdOld = persistentOperationsConditionnement.getUtilisateurId();
            Utilisateurs utilisateurIdNew = operationsConditionnement.getUtilisateurId();
            List<MouvementsStock> mouvementsStockListOld = persistentOperationsConditionnement.getMouvementsStockList();
            List<MouvementsStock> mouvementsStockListNew = operationsConditionnement.getMouvementsStockList();
            if (articleProduitIdNew != null) {
                articleProduitIdNew = em.getReference(articleProduitIdNew.getClass(), articleProduitIdNew.getId());
                operationsConditionnement.setArticleProduitId(articleProduitIdNew);
            }
            if (articleSourceIdNew != null) {
                articleSourceIdNew = em.getReference(articleSourceIdNew.getClass(), articleSourceIdNew.getId());
                operationsConditionnement.setArticleSourceId(articleSourceIdNew);
            }
            if (conditionnementIdNew != null) {
                conditionnementIdNew = em.getReference(conditionnementIdNew.getClass(), conditionnementIdNew.getId());
                operationsConditionnement.setConditionnementId(conditionnementIdNew);
            }
            if (utilisateurIdNew != null) {
                utilisateurIdNew = em.getReference(utilisateurIdNew.getClass(), utilisateurIdNew.getId());
                operationsConditionnement.setUtilisateurId(utilisateurIdNew);
            }
            List<MouvementsStock> attachedMouvementsStockListNew = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListNewMouvementsStockToAttach : mouvementsStockListNew) {
                mouvementsStockListNewMouvementsStockToAttach = em.getReference(mouvementsStockListNewMouvementsStockToAttach.getClass(), mouvementsStockListNewMouvementsStockToAttach.getId());
                attachedMouvementsStockListNew.add(mouvementsStockListNewMouvementsStockToAttach);
            }
            mouvementsStockListNew = attachedMouvementsStockListNew;
            operationsConditionnement.setMouvementsStockList(mouvementsStockListNew);
            operationsConditionnement = em.merge(operationsConditionnement);
            if (articleProduitIdOld != null && !articleProduitIdOld.equals(articleProduitIdNew)) {
                articleProduitIdOld.getOperationsConditionnementList().remove(operationsConditionnement);
                articleProduitIdOld = em.merge(articleProduitIdOld);
            }
            if (articleProduitIdNew != null && !articleProduitIdNew.equals(articleProduitIdOld)) {
                articleProduitIdNew.getOperationsConditionnementList().add(operationsConditionnement);
                articleProduitIdNew = em.merge(articleProduitIdNew);
            }
            if (articleSourceIdOld != null && !articleSourceIdOld.equals(articleSourceIdNew)) {
                articleSourceIdOld.getOperationsConditionnementList().remove(operationsConditionnement);
                articleSourceIdOld = em.merge(articleSourceIdOld);
            }
            if (articleSourceIdNew != null && !articleSourceIdNew.equals(articleSourceIdOld)) {
                articleSourceIdNew.getOperationsConditionnementList().add(operationsConditionnement);
                articleSourceIdNew = em.merge(articleSourceIdNew);
            }
            if (conditionnementIdOld != null && !conditionnementIdOld.equals(conditionnementIdNew)) {
                conditionnementIdOld.getOperationsConditionnementList().remove(operationsConditionnement);
                conditionnementIdOld = em.merge(conditionnementIdOld);
            }
            if (conditionnementIdNew != null && !conditionnementIdNew.equals(conditionnementIdOld)) {
                conditionnementIdNew.getOperationsConditionnementList().add(operationsConditionnement);
                conditionnementIdNew = em.merge(conditionnementIdNew);
            }
            if (utilisateurIdOld != null && !utilisateurIdOld.equals(utilisateurIdNew)) {
                utilisateurIdOld.getOperationsConditionnementList().remove(operationsConditionnement);
                utilisateurIdOld = em.merge(utilisateurIdOld);
            }
            if (utilisateurIdNew != null && !utilisateurIdNew.equals(utilisateurIdOld)) {
                utilisateurIdNew.getOperationsConditionnementList().add(operationsConditionnement);
                utilisateurIdNew = em.merge(utilisateurIdNew);
            }
            for (MouvementsStock mouvementsStockListOldMouvementsStock : mouvementsStockListOld) {
                if (!mouvementsStockListNew.contains(mouvementsStockListOldMouvementsStock)) {
                    mouvementsStockListOldMouvementsStock.setOperationId(null);
                    mouvementsStockListOldMouvementsStock = em.merge(mouvementsStockListOldMouvementsStock);
                }
            }
            for (MouvementsStock mouvementsStockListNewMouvementsStock : mouvementsStockListNew) {
                if (!mouvementsStockListOld.contains(mouvementsStockListNewMouvementsStock)) {
                    OperationsConditionnement oldOperationIdOfMouvementsStockListNewMouvementsStock = mouvementsStockListNewMouvementsStock.getOperationId();
                    mouvementsStockListNewMouvementsStock.setOperationId(operationsConditionnement);
                    mouvementsStockListNewMouvementsStock = em.merge(mouvementsStockListNewMouvementsStock);
                    if (oldOperationIdOfMouvementsStockListNewMouvementsStock != null && !oldOperationIdOfMouvementsStockListNewMouvementsStock.equals(operationsConditionnement)) {
                        oldOperationIdOfMouvementsStockListNewMouvementsStock.getMouvementsStockList().remove(mouvementsStockListNewMouvementsStock);
                        oldOperationIdOfMouvementsStockListNewMouvementsStock = em.merge(oldOperationIdOfMouvementsStockListNewMouvementsStock);
                    }
                }
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
                Integer id = operationsConditionnement.getId();
                if (findOperationsConditionnement(id) == null) {
                    throw new NonexistentEntityException("The operationsConditionnement with id " + id + " no longer exists.");
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
            OperationsConditionnement operationsConditionnement;
            try {
                operationsConditionnement = em.getReference(OperationsConditionnement.class, id);
                operationsConditionnement.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operationsConditionnement with id " + id + " no longer exists.", enfe);
            }
            Articles articleProduitId = operationsConditionnement.getArticleProduitId();
            if (articleProduitId != null) {
                articleProduitId.getOperationsConditionnementList().remove(operationsConditionnement);
                articleProduitId = em.merge(articleProduitId);
            }
            Articles articleSourceId = operationsConditionnement.getArticleSourceId();
            if (articleSourceId != null) {
                articleSourceId.getOperationsConditionnementList().remove(operationsConditionnement);
                articleSourceId = em.merge(articleSourceId);
            }
            Conditionnements conditionnementId = operationsConditionnement.getConditionnementId();
            if (conditionnementId != null) {
                conditionnementId.getOperationsConditionnementList().remove(operationsConditionnement);
                conditionnementId = em.merge(conditionnementId);
            }
            Utilisateurs utilisateurId = operationsConditionnement.getUtilisateurId();
            if (utilisateurId != null) {
                utilisateurId.getOperationsConditionnementList().remove(operationsConditionnement);
                utilisateurId = em.merge(utilisateurId);
            }
            List<MouvementsStock> mouvementsStockList = operationsConditionnement.getMouvementsStockList();
            for (MouvementsStock mouvementsStockListMouvementsStock : mouvementsStockList) {
                mouvementsStockListMouvementsStock.setOperationId(null);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
            }
            em.remove(operationsConditionnement);
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

    public List<OperationsConditionnement> findOperationsConditionnementEntities() {
        return findOperationsConditionnementEntities(true, -1, -1);
    }

    public List<OperationsConditionnement> findOperationsConditionnementEntities(int maxResults, int firstResult) {
        return findOperationsConditionnementEntities(false, maxResults, firstResult);
    }

    private List<OperationsConditionnement> findOperationsConditionnementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OperationsConditionnement.class));
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

    public OperationsConditionnement findOperationsConditionnement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OperationsConditionnement.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperationsConditionnementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OperationsConditionnement> rt = cq.from(OperationsConditionnement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
