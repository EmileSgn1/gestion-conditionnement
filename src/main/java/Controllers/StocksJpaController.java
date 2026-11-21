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
import Entities.Stocks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StocksJpaController implements Serializable {

    public StocksJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Stocks stocks) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles articleId = stocks.getArticleId();
            if (articleId != null) {
                articleId = em.getReference(articleId.getClass(), articleId.getId());
                stocks.setArticleId(articleId);
            }
            em.persist(stocks);
            if (articleId != null) {
                Stocks oldStocksOfArticleId = articleId.getStocks();
                if (oldStocksOfArticleId != null) {
                    oldStocksOfArticleId.setArticleId(null);
                    oldStocksOfArticleId = em.merge(oldStocksOfArticleId);
                }
                articleId.setStocks(stocks);
                articleId = em.merge(articleId);
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

    public void edit(Stocks stocks) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Stocks persistentStocks = em.find(Stocks.class, stocks.getId());
            Articles articleIdOld = persistentStocks.getArticleId();
            Articles articleIdNew = stocks.getArticleId();
            if (articleIdNew != null) {
                articleIdNew = em.getReference(articleIdNew.getClass(), articleIdNew.getId());
                stocks.setArticleId(articleIdNew);
            }
            stocks = em.merge(stocks);
            if (articleIdOld != null && !articleIdOld.equals(articleIdNew)) {
                articleIdOld.setStocks(null);
                articleIdOld = em.merge(articleIdOld);
            }
            if (articleIdNew != null && !articleIdNew.equals(articleIdOld)) {
                Stocks oldStocksOfArticleId = articleIdNew.getStocks();
                if (oldStocksOfArticleId != null) {
                    oldStocksOfArticleId.setArticleId(null);
                    oldStocksOfArticleId = em.merge(oldStocksOfArticleId);
                }
                articleIdNew.setStocks(stocks);
                articleIdNew = em.merge(articleIdNew);
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
                Integer id = stocks.getId();
                if (findStocks(id) == null) {
                    throw new NonexistentEntityException("The stocks with id " + id + " no longer exists.");
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
            Stocks stocks;
            try {
                stocks = em.getReference(Stocks.class, id);
                stocks.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stocks with id " + id + " no longer exists.", enfe);
            }
            Articles articleId = stocks.getArticleId();
            if (articleId != null) {
                articleId.setStocks(null);
                articleId = em.merge(articleId);
            }
            em.remove(stocks);
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

    public List<Stocks> findStocksEntities() {
        return findStocksEntities(true, -1, -1);
    }

    public List<Stocks> findStocksEntities(int maxResults, int firstResult) {
        return findStocksEntities(false, maxResults, firstResult);
    }

    private List<Stocks> findStocksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Stocks.class));
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

    public Stocks findStocks(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Stocks.class, id);
        } finally {
            em.close();
        }
    }

    public int getStocksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Stocks> rt = cq.from(Stocks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
