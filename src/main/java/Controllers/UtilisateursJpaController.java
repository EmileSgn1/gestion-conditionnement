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
import Entities.OperationsConditionnement;
import java.util.ArrayList;
import java.util.List;
import Entities.Historique;
import Entities.MouvementsStock;
import Entities.Utilisateurs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

/**
 *
 * @author ADMIN
 */
public class UtilisateursJpaController implements Serializable {

    public UtilisateursJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilisateurs utilisateurs) throws RollbackFailureException, Exception {
        if (utilisateurs.getOperationsConditionnementList() == null) {
            utilisateurs.setOperationsConditionnementList(new ArrayList<OperationsConditionnement>());
        }
        if (utilisateurs.getHistoriqueList() == null) {
            utilisateurs.setHistoriqueList(new ArrayList<Historique>());
        }
        if (utilisateurs.getMouvementsStockList() == null) {
            utilisateurs.setMouvementsStockList(new ArrayList<MouvementsStock>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<OperationsConditionnement> attachedOperationsConditionnementList = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnementToAttach : utilisateurs.getOperationsConditionnementList()) {
                operationsConditionnementListOperationsConditionnementToAttach = em.getReference(operationsConditionnementListOperationsConditionnementToAttach.getClass(), operationsConditionnementListOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementList.add(operationsConditionnementListOperationsConditionnementToAttach);
            }
            utilisateurs.setOperationsConditionnementList(attachedOperationsConditionnementList);
            List<Historique> attachedHistoriqueList = new ArrayList<Historique>();
            for (Historique historiqueListHistoriqueToAttach : utilisateurs.getHistoriqueList()) {
                historiqueListHistoriqueToAttach = em.getReference(historiqueListHistoriqueToAttach.getClass(), historiqueListHistoriqueToAttach.getId());
                attachedHistoriqueList.add(historiqueListHistoriqueToAttach);
            }
            utilisateurs.setHistoriqueList(attachedHistoriqueList);
            List<MouvementsStock> attachedMouvementsStockList = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListMouvementsStockToAttach : utilisateurs.getMouvementsStockList()) {
                mouvementsStockListMouvementsStockToAttach = em.getReference(mouvementsStockListMouvementsStockToAttach.getClass(), mouvementsStockListMouvementsStockToAttach.getId());
                attachedMouvementsStockList.add(mouvementsStockListMouvementsStockToAttach);
            }
            utilisateurs.setMouvementsStockList(attachedMouvementsStockList);
            em.persist(utilisateurs);
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : utilisateurs.getOperationsConditionnementList()) {
                Utilisateurs oldUtilisateurIdOfOperationsConditionnementListOperationsConditionnement = operationsConditionnementListOperationsConditionnement.getUtilisateurId();
                operationsConditionnementListOperationsConditionnement.setUtilisateurId(utilisateurs);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
                if (oldUtilisateurIdOfOperationsConditionnementListOperationsConditionnement != null) {
                    oldUtilisateurIdOfOperationsConditionnementListOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListOperationsConditionnement);
                    oldUtilisateurIdOfOperationsConditionnementListOperationsConditionnement = em.merge(oldUtilisateurIdOfOperationsConditionnementListOperationsConditionnement);
                }
            }
            for (Historique historiqueListHistorique : utilisateurs.getHistoriqueList()) {
                Utilisateurs oldUtilisateurIdOfHistoriqueListHistorique = historiqueListHistorique.getUtilisateurId();
                historiqueListHistorique.setUtilisateurId(utilisateurs);
                historiqueListHistorique = em.merge(historiqueListHistorique);
                if (oldUtilisateurIdOfHistoriqueListHistorique != null) {
                    oldUtilisateurIdOfHistoriqueListHistorique.getHistoriqueList().remove(historiqueListHistorique);
                    oldUtilisateurIdOfHistoriqueListHistorique = em.merge(oldUtilisateurIdOfHistoriqueListHistorique);
                }
            }
            for (MouvementsStock mouvementsStockListMouvementsStock : utilisateurs.getMouvementsStockList()) {
                Utilisateurs oldUtilisateurIdOfMouvementsStockListMouvementsStock = mouvementsStockListMouvementsStock.getUtilisateurId();
                mouvementsStockListMouvementsStock.setUtilisateurId(utilisateurs);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
                if (oldUtilisateurIdOfMouvementsStockListMouvementsStock != null) {
                    oldUtilisateurIdOfMouvementsStockListMouvementsStock.getMouvementsStockList().remove(mouvementsStockListMouvementsStock);
                    oldUtilisateurIdOfMouvementsStockListMouvementsStock = em.merge(oldUtilisateurIdOfMouvementsStockListMouvementsStock);
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

    public void edit(Utilisateurs utilisateurs) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilisateurs persistentUtilisateurs = em.find(Utilisateurs.class, utilisateurs.getId());
            List<OperationsConditionnement> operationsConditionnementListOld = persistentUtilisateurs.getOperationsConditionnementList();
            List<OperationsConditionnement> operationsConditionnementListNew = utilisateurs.getOperationsConditionnementList();
            List<Historique> historiqueListOld = persistentUtilisateurs.getHistoriqueList();
            List<Historique> historiqueListNew = utilisateurs.getHistoriqueList();
            List<MouvementsStock> mouvementsStockListOld = persistentUtilisateurs.getMouvementsStockList();
            List<MouvementsStock> mouvementsStockListNew = utilisateurs.getMouvementsStockList();
            List<OperationsConditionnement> attachedOperationsConditionnementListNew = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnementToAttach : operationsConditionnementListNew) {
                operationsConditionnementListNewOperationsConditionnementToAttach = em.getReference(operationsConditionnementListNewOperationsConditionnementToAttach.getClass(), operationsConditionnementListNewOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementListNew.add(operationsConditionnementListNewOperationsConditionnementToAttach);
            }
            operationsConditionnementListNew = attachedOperationsConditionnementListNew;
            utilisateurs.setOperationsConditionnementList(operationsConditionnementListNew);
            List<Historique> attachedHistoriqueListNew = new ArrayList<Historique>();
            for (Historique historiqueListNewHistoriqueToAttach : historiqueListNew) {
                historiqueListNewHistoriqueToAttach = em.getReference(historiqueListNewHistoriqueToAttach.getClass(), historiqueListNewHistoriqueToAttach.getId());
                attachedHistoriqueListNew.add(historiqueListNewHistoriqueToAttach);
            }
            historiqueListNew = attachedHistoriqueListNew;
            utilisateurs.setHistoriqueList(historiqueListNew);
            List<MouvementsStock> attachedMouvementsStockListNew = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListNewMouvementsStockToAttach : mouvementsStockListNew) {
                mouvementsStockListNewMouvementsStockToAttach = em.getReference(mouvementsStockListNewMouvementsStockToAttach.getClass(), mouvementsStockListNewMouvementsStockToAttach.getId());
                attachedMouvementsStockListNew.add(mouvementsStockListNewMouvementsStockToAttach);
            }
            mouvementsStockListNew = attachedMouvementsStockListNew;
            utilisateurs.setMouvementsStockList(mouvementsStockListNew);
            utilisateurs = em.merge(utilisateurs);
            for (OperationsConditionnement operationsConditionnementListOldOperationsConditionnement : operationsConditionnementListOld) {
                if (!operationsConditionnementListNew.contains(operationsConditionnementListOldOperationsConditionnement)) {
                    operationsConditionnementListOldOperationsConditionnement.setUtilisateurId(null);
                    operationsConditionnementListOldOperationsConditionnement = em.merge(operationsConditionnementListOldOperationsConditionnement);
                }
            }
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnement : operationsConditionnementListNew) {
                if (!operationsConditionnementListOld.contains(operationsConditionnementListNewOperationsConditionnement)) {
                    Utilisateurs oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement = operationsConditionnementListNewOperationsConditionnement.getUtilisateurId();
                    operationsConditionnementListNewOperationsConditionnement.setUtilisateurId(utilisateurs);
                    operationsConditionnementListNewOperationsConditionnement = em.merge(operationsConditionnementListNewOperationsConditionnement);
                    if (oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement != null && !oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement.equals(utilisateurs)) {
                        oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListNewOperationsConditionnement);
                        oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement = em.merge(oldUtilisateurIdOfOperationsConditionnementListNewOperationsConditionnement);
                    }
                }
            }
            for (Historique historiqueListOldHistorique : historiqueListOld) {
                if (!historiqueListNew.contains(historiqueListOldHistorique)) {
                    historiqueListOldHistorique.setUtilisateurId(null);
                    historiqueListOldHistorique = em.merge(historiqueListOldHistorique);
                }
            }
            for (Historique historiqueListNewHistorique : historiqueListNew) {
                if (!historiqueListOld.contains(historiqueListNewHistorique)) {
                    Utilisateurs oldUtilisateurIdOfHistoriqueListNewHistorique = historiqueListNewHistorique.getUtilisateurId();
                    historiqueListNewHistorique.setUtilisateurId(utilisateurs);
                    historiqueListNewHistorique = em.merge(historiqueListNewHistorique);
                    if (oldUtilisateurIdOfHistoriqueListNewHistorique != null && !oldUtilisateurIdOfHistoriqueListNewHistorique.equals(utilisateurs)) {
                        oldUtilisateurIdOfHistoriqueListNewHistorique.getHistoriqueList().remove(historiqueListNewHistorique);
                        oldUtilisateurIdOfHistoriqueListNewHistorique = em.merge(oldUtilisateurIdOfHistoriqueListNewHistorique);
                    }
                }
            }
            for (MouvementsStock mouvementsStockListOldMouvementsStock : mouvementsStockListOld) {
                if (!mouvementsStockListNew.contains(mouvementsStockListOldMouvementsStock)) {
                    mouvementsStockListOldMouvementsStock.setUtilisateurId(null);
                    mouvementsStockListOldMouvementsStock = em.merge(mouvementsStockListOldMouvementsStock);
                }
            }
            for (MouvementsStock mouvementsStockListNewMouvementsStock : mouvementsStockListNew) {
                if (!mouvementsStockListOld.contains(mouvementsStockListNewMouvementsStock)) {
                    Utilisateurs oldUtilisateurIdOfMouvementsStockListNewMouvementsStock = mouvementsStockListNewMouvementsStock.getUtilisateurId();
                    mouvementsStockListNewMouvementsStock.setUtilisateurId(utilisateurs);
                    mouvementsStockListNewMouvementsStock = em.merge(mouvementsStockListNewMouvementsStock);
                    if (oldUtilisateurIdOfMouvementsStockListNewMouvementsStock != null && !oldUtilisateurIdOfMouvementsStockListNewMouvementsStock.equals(utilisateurs)) {
                        oldUtilisateurIdOfMouvementsStockListNewMouvementsStock.getMouvementsStockList().remove(mouvementsStockListNewMouvementsStock);
                        oldUtilisateurIdOfMouvementsStockListNewMouvementsStock = em.merge(oldUtilisateurIdOfMouvementsStockListNewMouvementsStock);
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
                Integer id = utilisateurs.getId();
                if (findUtilisateurs(id) == null) {
                    throw new NonexistentEntityException("The utilisateurs with id " + id + " no longer exists.");
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
            Utilisateurs utilisateurs;
            try {
                utilisateurs = em.getReference(Utilisateurs.class, id);
                utilisateurs.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilisateurs with id " + id + " no longer exists.", enfe);
            }
            List<OperationsConditionnement> operationsConditionnementList = utilisateurs.getOperationsConditionnementList();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : operationsConditionnementList) {
                operationsConditionnementListOperationsConditionnement.setUtilisateurId(null);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
            }
            List<Historique> historiqueList = utilisateurs.getHistoriqueList();
            for (Historique historiqueListHistorique : historiqueList) {
                historiqueListHistorique.setUtilisateurId(null);
                historiqueListHistorique = em.merge(historiqueListHistorique);
            }
            List<MouvementsStock> mouvementsStockList = utilisateurs.getMouvementsStockList();
            for (MouvementsStock mouvementsStockListMouvementsStock : mouvementsStockList) {
                mouvementsStockListMouvementsStock.setUtilisateurId(null);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
            }
            em.remove(utilisateurs);
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

    public List<Utilisateurs> findUtilisateursEntities() {
        return findUtilisateursEntities(true, -1, -1);
    }

    public List<Utilisateurs> findUtilisateursEntities(int maxResults, int firstResult) {
        return findUtilisateursEntities(false, maxResults, firstResult);
    }

    private List<Utilisateurs> findUtilisateursEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilisateurs.class));
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

    public Utilisateurs findUtilisateurs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilisateurs.class, id);
        } finally {
            em.close();
        }
    }

    public int getUtilisateursCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilisateurs> rt = cq.from(Utilisateurs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
