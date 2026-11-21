/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.TypesConditionnement;
import Entities.ArticleConditionnements;
import Entities.ConditionnementsCatalogue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ConditionnementsCatalogueJpaController implements Serializable {

    public ConditionnementsCatalogueJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConditionnementsCatalogue conditionnementsCatalogue) throws RollbackFailureException, Exception {
        if (conditionnementsCatalogue.getArticleConditionnementsList() == null) {
            conditionnementsCatalogue.setArticleConditionnementsList(new ArrayList<ArticleConditionnements>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TypesConditionnement typeConditionnementId = conditionnementsCatalogue.getTypeConditionnementId();
            if (typeConditionnementId != null) {
                typeConditionnementId = em.getReference(typeConditionnementId.getClass(), typeConditionnementId.getId());
                conditionnementsCatalogue.setTypeConditionnementId(typeConditionnementId);
            }
            List<ArticleConditionnements> attachedArticleConditionnementsList = new ArrayList<ArticleConditionnements>();
            for (ArticleConditionnements articleConditionnementsListArticleConditionnementsToAttach : conditionnementsCatalogue.getArticleConditionnementsList()) {
                articleConditionnementsListArticleConditionnementsToAttach = em.getReference(articleConditionnementsListArticleConditionnementsToAttach.getClass(), articleConditionnementsListArticleConditionnementsToAttach.getId());
                attachedArticleConditionnementsList.add(articleConditionnementsListArticleConditionnementsToAttach);
            }
            conditionnementsCatalogue.setArticleConditionnementsList(attachedArticleConditionnementsList);
            em.persist(conditionnementsCatalogue);
            if (typeConditionnementId != null) {
                typeConditionnementId.getConditionnementsCatalogueList().add(conditionnementsCatalogue);
                typeConditionnementId = em.merge(typeConditionnementId);
            }
            for (ArticleConditionnements articleConditionnementsListArticleConditionnements : conditionnementsCatalogue.getArticleConditionnementsList()) {
                ConditionnementsCatalogue oldConditionnementIdOfArticleConditionnementsListArticleConditionnements = articleConditionnementsListArticleConditionnements.getConditionnementId();
                articleConditionnementsListArticleConditionnements.setConditionnementId(conditionnementsCatalogue);
                articleConditionnementsListArticleConditionnements = em.merge(articleConditionnementsListArticleConditionnements);
                if (oldConditionnementIdOfArticleConditionnementsListArticleConditionnements != null) {
                    oldConditionnementIdOfArticleConditionnementsListArticleConditionnements.getArticleConditionnementsList().remove(articleConditionnementsListArticleConditionnements);
                    oldConditionnementIdOfArticleConditionnementsListArticleConditionnements = em.merge(oldConditionnementIdOfArticleConditionnementsListArticleConditionnements);
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

    public void edit(ConditionnementsCatalogue conditionnementsCatalogue) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConditionnementsCatalogue persistentConditionnementsCatalogue = em.find(ConditionnementsCatalogue.class, conditionnementsCatalogue.getId());
            TypesConditionnement typeConditionnementIdOld = persistentConditionnementsCatalogue.getTypeConditionnementId();
            TypesConditionnement typeConditionnementIdNew = conditionnementsCatalogue.getTypeConditionnementId();
            List<ArticleConditionnements> articleConditionnementsListOld = persistentConditionnementsCatalogue.getArticleConditionnementsList();
            List<ArticleConditionnements> articleConditionnementsListNew = conditionnementsCatalogue.getArticleConditionnementsList();
            List<String> illegalOrphanMessages = null;
            for (ArticleConditionnements articleConditionnementsListOldArticleConditionnements : articleConditionnementsListOld) {
                if (!articleConditionnementsListNew.contains(articleConditionnementsListOldArticleConditionnements)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArticleConditionnements " + articleConditionnementsListOldArticleConditionnements + " since its conditionnementId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (typeConditionnementIdNew != null) {
                typeConditionnementIdNew = em.getReference(typeConditionnementIdNew.getClass(), typeConditionnementIdNew.getId());
                conditionnementsCatalogue.setTypeConditionnementId(typeConditionnementIdNew);
            }
            List<ArticleConditionnements> attachedArticleConditionnementsListNew = new ArrayList<ArticleConditionnements>();
            for (ArticleConditionnements articleConditionnementsListNewArticleConditionnementsToAttach : articleConditionnementsListNew) {
                articleConditionnementsListNewArticleConditionnementsToAttach = em.getReference(articleConditionnementsListNewArticleConditionnementsToAttach.getClass(), articleConditionnementsListNewArticleConditionnementsToAttach.getId());
                attachedArticleConditionnementsListNew.add(articleConditionnementsListNewArticleConditionnementsToAttach);
            }
            articleConditionnementsListNew = attachedArticleConditionnementsListNew;
            conditionnementsCatalogue.setArticleConditionnementsList(articleConditionnementsListNew);
            conditionnementsCatalogue = em.merge(conditionnementsCatalogue);
            if (typeConditionnementIdOld != null && !typeConditionnementIdOld.equals(typeConditionnementIdNew)) {
                typeConditionnementIdOld.getConditionnementsCatalogueList().remove(conditionnementsCatalogue);
                typeConditionnementIdOld = em.merge(typeConditionnementIdOld);
            }
            if (typeConditionnementIdNew != null && !typeConditionnementIdNew.equals(typeConditionnementIdOld)) {
                typeConditionnementIdNew.getConditionnementsCatalogueList().add(conditionnementsCatalogue);
                typeConditionnementIdNew = em.merge(typeConditionnementIdNew);
            }
            for (ArticleConditionnements articleConditionnementsListNewArticleConditionnements : articleConditionnementsListNew) {
                if (!articleConditionnementsListOld.contains(articleConditionnementsListNewArticleConditionnements)) {
                    ConditionnementsCatalogue oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements = articleConditionnementsListNewArticleConditionnements.getConditionnementId();
                    articleConditionnementsListNewArticleConditionnements.setConditionnementId(conditionnementsCatalogue);
                    articleConditionnementsListNewArticleConditionnements = em.merge(articleConditionnementsListNewArticleConditionnements);
                    if (oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements != null && !oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements.equals(conditionnementsCatalogue)) {
                        oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements.getArticleConditionnementsList().remove(articleConditionnementsListNewArticleConditionnements);
                        oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements = em.merge(oldConditionnementIdOfArticleConditionnementsListNewArticleConditionnements);
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
                Integer id = conditionnementsCatalogue.getId();
                if (findConditionnementsCatalogue(id) == null) {
                    throw new NonexistentEntityException("The conditionnementsCatalogue with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConditionnementsCatalogue conditionnementsCatalogue;
            try {
                conditionnementsCatalogue = em.getReference(ConditionnementsCatalogue.class, id);
                conditionnementsCatalogue.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conditionnementsCatalogue with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArticleConditionnements> articleConditionnementsListOrphanCheck = conditionnementsCatalogue.getArticleConditionnementsList();
            for (ArticleConditionnements articleConditionnementsListOrphanCheckArticleConditionnements : articleConditionnementsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConditionnementsCatalogue (" + conditionnementsCatalogue + ") cannot be destroyed since the ArticleConditionnements " + articleConditionnementsListOrphanCheckArticleConditionnements + " in its articleConditionnementsList field has a non-nullable conditionnementId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TypesConditionnement typeConditionnementId = conditionnementsCatalogue.getTypeConditionnementId();
            if (typeConditionnementId != null) {
                typeConditionnementId.getConditionnementsCatalogueList().remove(conditionnementsCatalogue);
                typeConditionnementId = em.merge(typeConditionnementId);
            }
            em.remove(conditionnementsCatalogue);
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

    public List<ConditionnementsCatalogue> findConditionnementsCatalogueEntities() {
        return findConditionnementsCatalogueEntities(true, -1, -1);
    }

    public List<ConditionnementsCatalogue> findConditionnementsCatalogueEntities(int maxResults, int firstResult) {
        return findConditionnementsCatalogueEntities(false, maxResults, firstResult);
    }

    private List<ConditionnementsCatalogue> findConditionnementsCatalogueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConditionnementsCatalogue.class));
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

    public ConditionnementsCatalogue findConditionnementsCatalogue(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConditionnementsCatalogue.class, id);
        } finally {
            em.close();
        }
    }

    public int getConditionnementsCatalogueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConditionnementsCatalogue> rt = cq.from(ConditionnementsCatalogue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
