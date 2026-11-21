/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import Entities.ArticleConditionnements;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Entities.Articles;
import Entities.ConditionnementsCatalogue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ArticleConditionnementsJpaController implements Serializable {

    public ArticleConditionnementsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArticleConditionnements articleConditionnements) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleId = articleConditionnements.getArticleId();
            if (articleId != null) {
                articleId = em.getReference(articleId.getClass(), articleId.getId());
                articleConditionnements.setArticleId(articleId);
            }
            ConditionnementsCatalogue conditionnementId = articleConditionnements.getConditionnementId();
            if (conditionnementId != null) {
                conditionnementId = em.getReference(conditionnementId.getClass(), conditionnementId.getId());
                articleConditionnements.setConditionnementId(conditionnementId);
            }
            em.persist(articleConditionnements);
            if (articleId != null) {
                articleId.getArticleConditionnementsList().add(articleConditionnements);
                articleId = em.merge(articleId);
            }
            if (conditionnementId != null) {
                conditionnementId.getArticleConditionnementsList().add(articleConditionnements);
                conditionnementId = em.merge(conditionnementId);
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

    public void edit(ArticleConditionnements articleConditionnements) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ArticleConditionnements persistentArticleConditionnements = em.find(ArticleConditionnements.class, articleConditionnements.getId());
            Articles articleIdOld = persistentArticleConditionnements.getArticleId();
            Articles articleIdNew = articleConditionnements.getArticleId();
            ConditionnementsCatalogue conditionnementIdOld = persistentArticleConditionnements.getConditionnementId();
            ConditionnementsCatalogue conditionnementIdNew = articleConditionnements.getConditionnementId();
            if (articleIdNew != null) {
                articleIdNew = em.getReference(articleIdNew.getClass(), articleIdNew.getId());
                articleConditionnements.setArticleId(articleIdNew);
            }
            if (conditionnementIdNew != null) {
                conditionnementIdNew = em.getReference(conditionnementIdNew.getClass(), conditionnementIdNew.getId());
                articleConditionnements.setConditionnementId(conditionnementIdNew);
            }
            articleConditionnements = em.merge(articleConditionnements);
            if (articleIdOld != null && !articleIdOld.equals(articleIdNew)) {
                articleIdOld.getArticleConditionnementsList().remove(articleConditionnements);
                articleIdOld = em.merge(articleIdOld);
            }
            if (articleIdNew != null && !articleIdNew.equals(articleIdOld)) {
                articleIdNew.getArticleConditionnementsList().add(articleConditionnements);
                articleIdNew = em.merge(articleIdNew);
            }
            if (conditionnementIdOld != null && !conditionnementIdOld.equals(conditionnementIdNew)) {
                conditionnementIdOld.getArticleConditionnementsList().remove(articleConditionnements);
                conditionnementIdOld = em.merge(conditionnementIdOld);
            }
            if (conditionnementIdNew != null && !conditionnementIdNew.equals(conditionnementIdOld)) {
                conditionnementIdNew.getArticleConditionnementsList().add(articleConditionnements);
                conditionnementIdNew = em.merge(conditionnementIdNew);
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
                Integer id = articleConditionnements.getId();
                if (findArticleConditionnements(id) == null) {
                    throw new NonexistentEntityException("The articleConditionnements with id " + id + " no longer exists.");
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
            ArticleConditionnements articleConditionnements;
            try {
                articleConditionnements = em.getReference(ArticleConditionnements.class, id);
                articleConditionnements.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articleConditionnements with id " + id + " no longer exists.", enfe);
            }
            Articles articleId = articleConditionnements.getArticleId();
            if (articleId != null) {
                articleId.getArticleConditionnementsList().remove(articleConditionnements);
                articleId = em.merge(articleId);
            }
            ConditionnementsCatalogue conditionnementId = articleConditionnements.getConditionnementId();
            if (conditionnementId != null) {
                conditionnementId.getArticleConditionnementsList().remove(articleConditionnements);
                conditionnementId = em.merge(conditionnementId);
            }
            em.remove(articleConditionnements);
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

    public List<ArticleConditionnements> findArticleConditionnementsEntities() {
        return findArticleConditionnementsEntities(true, -1, -1);
    }

    public List<ArticleConditionnements> findArticleConditionnementsEntities(int maxResults, int firstResult) {
        return findArticleConditionnementsEntities(false, maxResults, firstResult);
    }

    private List<ArticleConditionnements> findArticleConditionnementsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArticleConditionnements.class));
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

    public ArticleConditionnements findArticleConditionnements(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArticleConditionnements.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticleConditionnementsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArticleConditionnements> rt = cq.from(ArticleConditionnements.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
