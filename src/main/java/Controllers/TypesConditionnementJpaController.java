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
import Entities.ConditionnementsCatalogue;
import java.util.ArrayList;
import java.util.List;
import Entities.Conditionnements;
import Entities.TypesConditionnement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

/**
 *
 * @author ADMIN
 */
public class TypesConditionnementJpaController implements Serializable {

    public TypesConditionnementJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TypesConditionnement typesConditionnement) throws RollbackFailureException, Exception {
        if (typesConditionnement.getConditionnementsCatalogueList() == null) {
            typesConditionnement.setConditionnementsCatalogueList(new ArrayList<ConditionnementsCatalogue>());
        }
        if (typesConditionnement.getConditionnementsList() == null) {
            typesConditionnement.setConditionnementsList(new ArrayList<Conditionnements>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ConditionnementsCatalogue> attachedConditionnementsCatalogueList = new ArrayList<ConditionnementsCatalogue>();
            for (ConditionnementsCatalogue conditionnementsCatalogueListConditionnementsCatalogueToAttach : typesConditionnement.getConditionnementsCatalogueList()) {
                conditionnementsCatalogueListConditionnementsCatalogueToAttach = em.getReference(conditionnementsCatalogueListConditionnementsCatalogueToAttach.getClass(), conditionnementsCatalogueListConditionnementsCatalogueToAttach.getId());
                attachedConditionnementsCatalogueList.add(conditionnementsCatalogueListConditionnementsCatalogueToAttach);
            }
            typesConditionnement.setConditionnementsCatalogueList(attachedConditionnementsCatalogueList);
            List<Conditionnements> attachedConditionnementsList = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsListConditionnementsToAttach : typesConditionnement.getConditionnementsList()) {
                conditionnementsListConditionnementsToAttach = em.getReference(conditionnementsListConditionnementsToAttach.getClass(), conditionnementsListConditionnementsToAttach.getId());
                attachedConditionnementsList.add(conditionnementsListConditionnementsToAttach);
            }
            typesConditionnement.setConditionnementsList(attachedConditionnementsList);
            em.persist(typesConditionnement);
            for (ConditionnementsCatalogue conditionnementsCatalogueListConditionnementsCatalogue : typesConditionnement.getConditionnementsCatalogueList()) {
                TypesConditionnement oldTypeConditionnementIdOfConditionnementsCatalogueListConditionnementsCatalogue = conditionnementsCatalogueListConditionnementsCatalogue.getTypeConditionnementId();
                conditionnementsCatalogueListConditionnementsCatalogue.setTypeConditionnementId(typesConditionnement);
                conditionnementsCatalogueListConditionnementsCatalogue = em.merge(conditionnementsCatalogueListConditionnementsCatalogue);
                if (oldTypeConditionnementIdOfConditionnementsCatalogueListConditionnementsCatalogue != null) {
                    oldTypeConditionnementIdOfConditionnementsCatalogueListConditionnementsCatalogue.getConditionnementsCatalogueList().remove(conditionnementsCatalogueListConditionnementsCatalogue);
                    oldTypeConditionnementIdOfConditionnementsCatalogueListConditionnementsCatalogue = em.merge(oldTypeConditionnementIdOfConditionnementsCatalogueListConditionnementsCatalogue);
                }
            }
            for (Conditionnements conditionnementsListConditionnements : typesConditionnement.getConditionnementsList()) {
                TypesConditionnement oldTypeConditionnementIdOfConditionnementsListConditionnements = conditionnementsListConditionnements.getTypeConditionnementId();
                conditionnementsListConditionnements.setTypeConditionnementId(typesConditionnement);
                conditionnementsListConditionnements = em.merge(conditionnementsListConditionnements);
                if (oldTypeConditionnementIdOfConditionnementsListConditionnements != null) {
                    oldTypeConditionnementIdOfConditionnementsListConditionnements.getConditionnementsList().remove(conditionnementsListConditionnements);
                    oldTypeConditionnementIdOfConditionnementsListConditionnements = em.merge(oldTypeConditionnementIdOfConditionnementsListConditionnements);
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

    public void edit(TypesConditionnement typesConditionnement) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TypesConditionnement persistentTypesConditionnement = em.find(TypesConditionnement.class, typesConditionnement.getId());
            List<ConditionnementsCatalogue> conditionnementsCatalogueListOld = persistentTypesConditionnement.getConditionnementsCatalogueList();
            List<ConditionnementsCatalogue> conditionnementsCatalogueListNew = typesConditionnement.getConditionnementsCatalogueList();
            List<Conditionnements> conditionnementsListOld = persistentTypesConditionnement.getConditionnementsList();
            List<Conditionnements> conditionnementsListNew = typesConditionnement.getConditionnementsList();
            List<String> illegalOrphanMessages = null;
            for (ConditionnementsCatalogue conditionnementsCatalogueListOldConditionnementsCatalogue : conditionnementsCatalogueListOld) {
                if (!conditionnementsCatalogueListNew.contains(conditionnementsCatalogueListOldConditionnementsCatalogue)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConditionnementsCatalogue " + conditionnementsCatalogueListOldConditionnementsCatalogue + " since its typeConditionnementId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ConditionnementsCatalogue> attachedConditionnementsCatalogueListNew = new ArrayList<ConditionnementsCatalogue>();
            for (ConditionnementsCatalogue conditionnementsCatalogueListNewConditionnementsCatalogueToAttach : conditionnementsCatalogueListNew) {
                conditionnementsCatalogueListNewConditionnementsCatalogueToAttach = em.getReference(conditionnementsCatalogueListNewConditionnementsCatalogueToAttach.getClass(), conditionnementsCatalogueListNewConditionnementsCatalogueToAttach.getId());
                attachedConditionnementsCatalogueListNew.add(conditionnementsCatalogueListNewConditionnementsCatalogueToAttach);
            }
            conditionnementsCatalogueListNew = attachedConditionnementsCatalogueListNew;
            typesConditionnement.setConditionnementsCatalogueList(conditionnementsCatalogueListNew);
            List<Conditionnements> attachedConditionnementsListNew = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsListNewConditionnementsToAttach : conditionnementsListNew) {
                conditionnementsListNewConditionnementsToAttach = em.getReference(conditionnementsListNewConditionnementsToAttach.getClass(), conditionnementsListNewConditionnementsToAttach.getId());
                attachedConditionnementsListNew.add(conditionnementsListNewConditionnementsToAttach);
            }
            conditionnementsListNew = attachedConditionnementsListNew;
            typesConditionnement.setConditionnementsList(conditionnementsListNew);
            typesConditionnement = em.merge(typesConditionnement);
            for (ConditionnementsCatalogue conditionnementsCatalogueListNewConditionnementsCatalogue : conditionnementsCatalogueListNew) {
                if (!conditionnementsCatalogueListOld.contains(conditionnementsCatalogueListNewConditionnementsCatalogue)) {
                    TypesConditionnement oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue = conditionnementsCatalogueListNewConditionnementsCatalogue.getTypeConditionnementId();
                    conditionnementsCatalogueListNewConditionnementsCatalogue.setTypeConditionnementId(typesConditionnement);
                    conditionnementsCatalogueListNewConditionnementsCatalogue = em.merge(conditionnementsCatalogueListNewConditionnementsCatalogue);
                    if (oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue != null && !oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue.equals(typesConditionnement)) {
                        oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue.getConditionnementsCatalogueList().remove(conditionnementsCatalogueListNewConditionnementsCatalogue);
                        oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue = em.merge(oldTypeConditionnementIdOfConditionnementsCatalogueListNewConditionnementsCatalogue);
                    }
                }
            }
            for (Conditionnements conditionnementsListOldConditionnements : conditionnementsListOld) {
                if (!conditionnementsListNew.contains(conditionnementsListOldConditionnements)) {
                    conditionnementsListOldConditionnements.setTypeConditionnementId(null);
                    conditionnementsListOldConditionnements = em.merge(conditionnementsListOldConditionnements);
                }
            }
            for (Conditionnements conditionnementsListNewConditionnements : conditionnementsListNew) {
                if (!conditionnementsListOld.contains(conditionnementsListNewConditionnements)) {
                    TypesConditionnement oldTypeConditionnementIdOfConditionnementsListNewConditionnements = conditionnementsListNewConditionnements.getTypeConditionnementId();
                    conditionnementsListNewConditionnements.setTypeConditionnementId(typesConditionnement);
                    conditionnementsListNewConditionnements = em.merge(conditionnementsListNewConditionnements);
                    if (oldTypeConditionnementIdOfConditionnementsListNewConditionnements != null && !oldTypeConditionnementIdOfConditionnementsListNewConditionnements.equals(typesConditionnement)) {
                        oldTypeConditionnementIdOfConditionnementsListNewConditionnements.getConditionnementsList().remove(conditionnementsListNewConditionnements);
                        oldTypeConditionnementIdOfConditionnementsListNewConditionnements = em.merge(oldTypeConditionnementIdOfConditionnementsListNewConditionnements);
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
                Integer id = typesConditionnement.getId();
                if (findTypesConditionnement(id) == null) {
                    throw new NonexistentEntityException("The typesConditionnement with id " + id + " no longer exists.");
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
            TypesConditionnement typesConditionnement;
            try {
                typesConditionnement = em.getReference(TypesConditionnement.class, id);
                typesConditionnement.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typesConditionnement with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ConditionnementsCatalogue> conditionnementsCatalogueListOrphanCheck = typesConditionnement.getConditionnementsCatalogueList();
            for (ConditionnementsCatalogue conditionnementsCatalogueListOrphanCheckConditionnementsCatalogue : conditionnementsCatalogueListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TypesConditionnement (" + typesConditionnement + ") cannot be destroyed since the ConditionnementsCatalogue " + conditionnementsCatalogueListOrphanCheckConditionnementsCatalogue + " in its conditionnementsCatalogueList field has a non-nullable typeConditionnementId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Conditionnements> conditionnementsList = typesConditionnement.getConditionnementsList();
            for (Conditionnements conditionnementsListConditionnements : conditionnementsList) {
                conditionnementsListConditionnements.setTypeConditionnementId(null);
                conditionnementsListConditionnements = em.merge(conditionnementsListConditionnements);
            }
            em.remove(typesConditionnement);
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

    public List<TypesConditionnement> findTypesConditionnementEntities() {
        return findTypesConditionnementEntities(true, -1, -1);
    }

    public List<TypesConditionnement> findTypesConditionnementEntities(int maxResults, int firstResult) {
        return findTypesConditionnementEntities(false, maxResults, firstResult);
    }

    private List<TypesConditionnement> findTypesConditionnementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TypesConditionnement.class));
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

    public TypesConditionnement findTypesConditionnement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TypesConditionnement.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypesConditionnementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TypesConditionnement> rt = cq.from(TypesConditionnement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
