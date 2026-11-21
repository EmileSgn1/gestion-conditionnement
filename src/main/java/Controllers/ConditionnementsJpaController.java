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
import Entities.TypesConditionnement;
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
public class ConditionnementsJpaController implements Serializable {

    public ConditionnementsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conditionnements conditionnements) throws RollbackFailureException, Exception {
        if (conditionnements.getOperationsConditionnementList() == null) {
            conditionnements.setOperationsConditionnementList(new ArrayList<OperationsConditionnement>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleId = conditionnements.getArticleId();
            if (articleId != null) {
                articleId = em.getReference(articleId.getClass(), articleId.getId());
                conditionnements.setArticleId(articleId);
            }
            Articles articleSourceId = conditionnements.getArticleSourceId();
            if (articleSourceId != null) {
                articleSourceId = em.getReference(articleSourceId.getClass(), articleSourceId.getId());
                conditionnements.setArticleSourceId(articleSourceId);
            }
            TypesConditionnement typeConditionnementId = conditionnements.getTypeConditionnementId();
            if (typeConditionnementId != null) {
                typeConditionnementId = em.getReference(typeConditionnementId.getClass(), typeConditionnementId.getId());
                conditionnements.setTypeConditionnementId(typeConditionnementId);
            }
            List<OperationsConditionnement> attachedOperationsConditionnementList = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnementToAttach : conditionnements.getOperationsConditionnementList()) {
                operationsConditionnementListOperationsConditionnementToAttach = em.getReference(operationsConditionnementListOperationsConditionnementToAttach.getClass(), operationsConditionnementListOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementList.add(operationsConditionnementListOperationsConditionnementToAttach);
            }
            conditionnements.setOperationsConditionnementList(attachedOperationsConditionnementList);
            em.persist(conditionnements);
            if (articleId != null) {
                articleId.getConditionnementsList().add(conditionnements);
                articleId = em.merge(articleId);
            }
            if (articleSourceId != null) {
                articleSourceId.getConditionnementsList().add(conditionnements);
                articleSourceId = em.merge(articleSourceId);
            }
            if (typeConditionnementId != null) {
                typeConditionnementId.getConditionnementsList().add(conditionnements);
                typeConditionnementId = em.merge(typeConditionnementId);
            }
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : conditionnements.getOperationsConditionnementList()) {
                Conditionnements oldConditionnementIdOfOperationsConditionnementListOperationsConditionnement = operationsConditionnementListOperationsConditionnement.getConditionnementId();
                operationsConditionnementListOperationsConditionnement.setConditionnementId(conditionnements);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
                if (oldConditionnementIdOfOperationsConditionnementListOperationsConditionnement != null) {
                    oldConditionnementIdOfOperationsConditionnementListOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListOperationsConditionnement);
                    oldConditionnementIdOfOperationsConditionnementListOperationsConditionnement = em.merge(oldConditionnementIdOfOperationsConditionnementListOperationsConditionnement);
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

    public void edit(Conditionnements conditionnements) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conditionnements persistentConditionnements = em.find(Conditionnements.class, conditionnements.getId());
            Articles articleIdOld = persistentConditionnements.getArticleId();
            Articles articleIdNew = conditionnements.getArticleId();
            Articles articleSourceIdOld = persistentConditionnements.getArticleSourceId();
            Articles articleSourceIdNew = conditionnements.getArticleSourceId();
            TypesConditionnement typeConditionnementIdOld = persistentConditionnements.getTypeConditionnementId();
            TypesConditionnement typeConditionnementIdNew = conditionnements.getTypeConditionnementId();
            List<OperationsConditionnement> operationsConditionnementListOld = persistentConditionnements.getOperationsConditionnementList();
            List<OperationsConditionnement> operationsConditionnementListNew = conditionnements.getOperationsConditionnementList();
            if (articleIdNew != null) {
                articleIdNew = em.getReference(articleIdNew.getClass(), articleIdNew.getId());
                conditionnements.setArticleId(articleIdNew);
            }
            if (articleSourceIdNew != null) {
                articleSourceIdNew = em.getReference(articleSourceIdNew.getClass(), articleSourceIdNew.getId());
                conditionnements.setArticleSourceId(articleSourceIdNew);
            }
            if (typeConditionnementIdNew != null) {
                typeConditionnementIdNew = em.getReference(typeConditionnementIdNew.getClass(), typeConditionnementIdNew.getId());
                conditionnements.setTypeConditionnementId(typeConditionnementIdNew);
            }
            List<OperationsConditionnement> attachedOperationsConditionnementListNew = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnementToAttach : operationsConditionnementListNew) {
                operationsConditionnementListNewOperationsConditionnementToAttach = em.getReference(operationsConditionnementListNewOperationsConditionnementToAttach.getClass(), operationsConditionnementListNewOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementListNew.add(operationsConditionnementListNewOperationsConditionnementToAttach);
            }
            operationsConditionnementListNew = attachedOperationsConditionnementListNew;
            conditionnements.setOperationsConditionnementList(operationsConditionnementListNew);
            conditionnements = em.merge(conditionnements);
            if (articleIdOld != null && !articleIdOld.equals(articleIdNew)) {
                articleIdOld.getConditionnementsList().remove(conditionnements);
                articleIdOld = em.merge(articleIdOld);
            }
            if (articleIdNew != null && !articleIdNew.equals(articleIdOld)) {
                articleIdNew.getConditionnementsList().add(conditionnements);
                articleIdNew = em.merge(articleIdNew);
            }
            if (articleSourceIdOld != null && !articleSourceIdOld.equals(articleSourceIdNew)) {
                articleSourceIdOld.getConditionnementsList().remove(conditionnements);
                articleSourceIdOld = em.merge(articleSourceIdOld);
            }
            if (articleSourceIdNew != null && !articleSourceIdNew.equals(articleSourceIdOld)) {
                articleSourceIdNew.getConditionnementsList().add(conditionnements);
                articleSourceIdNew = em.merge(articleSourceIdNew);
            }
            if (typeConditionnementIdOld != null && !typeConditionnementIdOld.equals(typeConditionnementIdNew)) {
                typeConditionnementIdOld.getConditionnementsList().remove(conditionnements);
                typeConditionnementIdOld = em.merge(typeConditionnementIdOld);
            }
            if (typeConditionnementIdNew != null && !typeConditionnementIdNew.equals(typeConditionnementIdOld)) {
                typeConditionnementIdNew.getConditionnementsList().add(conditionnements);
                typeConditionnementIdNew = em.merge(typeConditionnementIdNew);
            }
            for (OperationsConditionnement operationsConditionnementListOldOperationsConditionnement : operationsConditionnementListOld) {
                if (!operationsConditionnementListNew.contains(operationsConditionnementListOldOperationsConditionnement)) {
                    operationsConditionnementListOldOperationsConditionnement.setConditionnementId(null);
                    operationsConditionnementListOldOperationsConditionnement = em.merge(operationsConditionnementListOldOperationsConditionnement);
                }
            }
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnement : operationsConditionnementListNew) {
                if (!operationsConditionnementListOld.contains(operationsConditionnementListNewOperationsConditionnement)) {
                    Conditionnements oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement = operationsConditionnementListNewOperationsConditionnement.getConditionnementId();
                    operationsConditionnementListNewOperationsConditionnement.setConditionnementId(conditionnements);
                    operationsConditionnementListNewOperationsConditionnement = em.merge(operationsConditionnementListNewOperationsConditionnement);
                    if (oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement != null && !oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement.equals(conditionnements)) {
                        oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListNewOperationsConditionnement);
                        oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement = em.merge(oldConditionnementIdOfOperationsConditionnementListNewOperationsConditionnement);
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
                Integer id = conditionnements.getId();
                if (findConditionnements(id) == null) {
                    throw new NonexistentEntityException("The conditionnements with id " + id + " no longer exists.");
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
            Conditionnements conditionnements;
            try {
                conditionnements = em.getReference(Conditionnements.class, id);
                conditionnements.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conditionnements with id " + id + " no longer exists.", enfe);
            }
            Articles articleId = conditionnements.getArticleId();
            if (articleId != null) {
                articleId.getConditionnementsList().remove(conditionnements);
                articleId = em.merge(articleId);
            }
            Articles articleSourceId = conditionnements.getArticleSourceId();
            if (articleSourceId != null) {
                articleSourceId.getConditionnementsList().remove(conditionnements);
                articleSourceId = em.merge(articleSourceId);
            }
            TypesConditionnement typeConditionnementId = conditionnements.getTypeConditionnementId();
            if (typeConditionnementId != null) {
                typeConditionnementId.getConditionnementsList().remove(conditionnements);
                typeConditionnementId = em.merge(typeConditionnementId);
            }
            List<OperationsConditionnement> operationsConditionnementList = conditionnements.getOperationsConditionnementList();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : operationsConditionnementList) {
                operationsConditionnementListOperationsConditionnement.setConditionnementId(null);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
            }
            em.remove(conditionnements);
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

    public List<Conditionnements> findConditionnementsEntities() {
        return findConditionnementsEntities(true, -1, -1);
    }

    public List<Conditionnements> findConditionnementsEntities(int maxResults, int firstResult) {
        return findConditionnementsEntities(false, maxResults, firstResult);
    }

    private List<Conditionnements> findConditionnementsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conditionnements.class));
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

    public Conditionnements findConditionnements(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conditionnements.class, id);
        } finally {
            em.close();
        }
    }

    public int getConditionnementsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conditionnements> rt = cq.from(Conditionnements.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
