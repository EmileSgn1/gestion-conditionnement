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
import Entities.Unites;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class UnitesJpaController implements Serializable {

    public UnitesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Unites unites) throws RollbackFailureException, Exception {
        if (unites.getArticlesList() == null) {
            unites.setArticlesList(new ArrayList<Articles>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Articles> attachedArticlesList = new ArrayList<Articles>();
            for (Articles articlesListArticlesToAttach : unites.getArticlesList()) {
                articlesListArticlesToAttach = em.getReference(articlesListArticlesToAttach.getClass(), articlesListArticlesToAttach.getId());
                attachedArticlesList.add(articlesListArticlesToAttach);
            }
            unites.setArticlesList(attachedArticlesList);
            em.persist(unites);
            for (Articles articlesListArticles : unites.getArticlesList()) {
                Unites oldUniteIdOfArticlesListArticles = articlesListArticles.getUniteId();
                articlesListArticles.setUniteId(unites);
                articlesListArticles = em.merge(articlesListArticles);
                if (oldUniteIdOfArticlesListArticles != null) {
                    oldUniteIdOfArticlesListArticles.getArticlesList().remove(articlesListArticles);
                    oldUniteIdOfArticlesListArticles = em.merge(oldUniteIdOfArticlesListArticles);
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

    public void edit(Unites unites) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unites persistentUnites = em.find(Unites.class, unites.getId());
            List<Articles> articlesListOld = persistentUnites.getArticlesList();
            List<Articles> articlesListNew = unites.getArticlesList();
            List<Articles> attachedArticlesListNew = new ArrayList<Articles>();
            for (Articles articlesListNewArticlesToAttach : articlesListNew) {
                articlesListNewArticlesToAttach = em.getReference(articlesListNewArticlesToAttach.getClass(), articlesListNewArticlesToAttach.getId());
                attachedArticlesListNew.add(articlesListNewArticlesToAttach);
            }
            articlesListNew = attachedArticlesListNew;
            unites.setArticlesList(articlesListNew);
            unites = em.merge(unites);
            for (Articles articlesListOldArticles : articlesListOld) {
                if (!articlesListNew.contains(articlesListOldArticles)) {
                    articlesListOldArticles.setUniteId(null);
                    articlesListOldArticles = em.merge(articlesListOldArticles);
                }
            }
            for (Articles articlesListNewArticles : articlesListNew) {
                if (!articlesListOld.contains(articlesListNewArticles)) {
                    Unites oldUniteIdOfArticlesListNewArticles = articlesListNewArticles.getUniteId();
                    articlesListNewArticles.setUniteId(unites);
                    articlesListNewArticles = em.merge(articlesListNewArticles);
                    if (oldUniteIdOfArticlesListNewArticles != null && !oldUniteIdOfArticlesListNewArticles.equals(unites)) {
                        oldUniteIdOfArticlesListNewArticles.getArticlesList().remove(articlesListNewArticles);
                        oldUniteIdOfArticlesListNewArticles = em.merge(oldUniteIdOfArticlesListNewArticles);
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
                Integer id = unites.getId();
                if (findUnites(id) == null) {
                    throw new NonexistentEntityException("The unites with id " + id + " no longer exists.");
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
            Unites unites;
            try {
                unites = em.getReference(Unites.class, id);
                unites.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unites with id " + id + " no longer exists.", enfe);
            }
            List<Articles> articlesList = unites.getArticlesList();
            for (Articles articlesListArticles : articlesList) {
                articlesListArticles.setUniteId(null);
                articlesListArticles = em.merge(articlesListArticles);
            }
            em.remove(unites);
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

    public List<Unites> findUnitesEntities() {
        return findUnitesEntities(true, -1, -1);
    }

    public List<Unites> findUnitesEntities(int maxResults, int firstResult) {
        return findUnitesEntities(false, maxResults, firstResult);
    }

    private List<Unites> findUnitesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Unites.class));
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

    public Unites findUnites(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Unites.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnitesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Unites> rt = cq.from(Unites.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
