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
import Entities.Stocks;
import Entities.Categories;
import Entities.Unites;
import Entities.Alertes;
import java.util.ArrayList;
import java.util.List;
import Entities.Conditionnements;
import Entities.OperationsConditionnement;
import Entities.ArticleConditionnements;
import Entities.Articles;
import Entities.MouvementsStock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

/**
 *
 * @author ADMIN
 */
public class ArticlesJpaController implements Serializable {

    public ArticlesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articles articles) throws RollbackFailureException, Exception {
        if (articles.getAlertesList() == null) {
            articles.setAlertesList(new ArrayList<Alertes>());
        }
        if (articles.getConditionnementsList() == null) {
            articles.setConditionnementsList(new ArrayList<Conditionnements>());
        }
        if (articles.getConditionnementsList1() == null) {
            articles.setConditionnementsList1(new ArrayList<Conditionnements>());
        }
        if (articles.getOperationsConditionnementList() == null) {
            articles.setOperationsConditionnementList(new ArrayList<OperationsConditionnement>());
        }
        if (articles.getOperationsConditionnementList1() == null) {
            articles.setOperationsConditionnementList1(new ArrayList<OperationsConditionnement>());
        }
        if (articles.getArticleConditionnementsList() == null) {
            articles.setArticleConditionnementsList(new ArrayList<ArticleConditionnements>());
        }
        if (articles.getMouvementsStockList() == null) {
            articles.setMouvementsStockList(new ArrayList<MouvementsStock>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Stocks stocks = articles.getStocks();
            if (stocks != null) {
                stocks = em.getReference(stocks.getClass(), stocks.getId());
                articles.setStocks(stocks);
            }
            Categories categorieId = articles.getCategorieId();
            if (categorieId != null) {
                categorieId = em.getReference(categorieId.getClass(), categorieId.getId());
                articles.setCategorieId(categorieId);
            }
            Unites uniteId = articles.getUniteId();
            if (uniteId != null) {
                uniteId = em.getReference(uniteId.getClass(), uniteId.getId());
                articles.setUniteId(uniteId);
            }
            List<Alertes> attachedAlertesList = new ArrayList<Alertes>();
            for (Alertes alertesListAlertesToAttach : articles.getAlertesList()) {
                alertesListAlertesToAttach = em.getReference(alertesListAlertesToAttach.getClass(), alertesListAlertesToAttach.getId());
                attachedAlertesList.add(alertesListAlertesToAttach);
            }
            articles.setAlertesList(attachedAlertesList);
            List<Conditionnements> attachedConditionnementsList = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsListConditionnementsToAttach : articles.getConditionnementsList()) {
                conditionnementsListConditionnementsToAttach = em.getReference(conditionnementsListConditionnementsToAttach.getClass(), conditionnementsListConditionnementsToAttach.getId());
                attachedConditionnementsList.add(conditionnementsListConditionnementsToAttach);
            }
            articles.setConditionnementsList(attachedConditionnementsList);
            List<Conditionnements> attachedConditionnementsList1 = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsList1ConditionnementsToAttach : articles.getConditionnementsList1()) {
                conditionnementsList1ConditionnementsToAttach = em.getReference(conditionnementsList1ConditionnementsToAttach.getClass(), conditionnementsList1ConditionnementsToAttach.getId());
                attachedConditionnementsList1.add(conditionnementsList1ConditionnementsToAttach);
            }
            articles.setConditionnementsList1(attachedConditionnementsList1);
            List<OperationsConditionnement> attachedOperationsConditionnementList = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnementToAttach : articles.getOperationsConditionnementList()) {
                operationsConditionnementListOperationsConditionnementToAttach = em.getReference(operationsConditionnementListOperationsConditionnementToAttach.getClass(), operationsConditionnementListOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementList.add(operationsConditionnementListOperationsConditionnementToAttach);
            }
            articles.setOperationsConditionnementList(attachedOperationsConditionnementList);
            List<OperationsConditionnement> attachedOperationsConditionnementList1 = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementList1OperationsConditionnementToAttach : articles.getOperationsConditionnementList1()) {
                operationsConditionnementList1OperationsConditionnementToAttach = em.getReference(operationsConditionnementList1OperationsConditionnementToAttach.getClass(), operationsConditionnementList1OperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementList1.add(operationsConditionnementList1OperationsConditionnementToAttach);
            }
            articles.setOperationsConditionnementList1(attachedOperationsConditionnementList1);
            List<ArticleConditionnements> attachedArticleConditionnementsList = new ArrayList<ArticleConditionnements>();
            for (ArticleConditionnements articleConditionnementsListArticleConditionnementsToAttach : articles.getArticleConditionnementsList()) {
                articleConditionnementsListArticleConditionnementsToAttach = em.getReference(articleConditionnementsListArticleConditionnementsToAttach.getClass(), articleConditionnementsListArticleConditionnementsToAttach.getId());
                attachedArticleConditionnementsList.add(articleConditionnementsListArticleConditionnementsToAttach);
            }
            articles.setArticleConditionnementsList(attachedArticleConditionnementsList);
            List<MouvementsStock> attachedMouvementsStockList = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListMouvementsStockToAttach : articles.getMouvementsStockList()) {
                mouvementsStockListMouvementsStockToAttach = em.getReference(mouvementsStockListMouvementsStockToAttach.getClass(), mouvementsStockListMouvementsStockToAttach.getId());
                attachedMouvementsStockList.add(mouvementsStockListMouvementsStockToAttach);
            }
            articles.setMouvementsStockList(attachedMouvementsStockList);
            em.persist(articles);
            if (stocks != null) {
                Articles oldArticleIdOfStocks = stocks.getArticleId();
                if (oldArticleIdOfStocks != null) {
                    oldArticleIdOfStocks.setStocks(null);
                    oldArticleIdOfStocks = em.merge(oldArticleIdOfStocks);
                }
                stocks.setArticleId(articles);
                stocks = em.merge(stocks);
            }
            if (categorieId != null) {
                categorieId.getArticlesList().add(articles);
                categorieId = em.merge(categorieId);
            }
            if (uniteId != null) {
                uniteId.getArticlesList().add(articles);
                uniteId = em.merge(uniteId);
            }
            for (Alertes alertesListAlertes : articles.getAlertesList()) {
                Articles oldArticleIdOfAlertesListAlertes = alertesListAlertes.getArticleId();
                alertesListAlertes.setArticleId(articles);
                alertesListAlertes = em.merge(alertesListAlertes);
                if (oldArticleIdOfAlertesListAlertes != null) {
                    oldArticleIdOfAlertesListAlertes.getAlertesList().remove(alertesListAlertes);
                    oldArticleIdOfAlertesListAlertes = em.merge(oldArticleIdOfAlertesListAlertes);
                }
            }
            for (Conditionnements conditionnementsListConditionnements : articles.getConditionnementsList()) {
                Articles oldArticleIdOfConditionnementsListConditionnements = conditionnementsListConditionnements.getArticleId();
                conditionnementsListConditionnements.setArticleId(articles);
                conditionnementsListConditionnements = em.merge(conditionnementsListConditionnements);
                if (oldArticleIdOfConditionnementsListConditionnements != null) {
                    oldArticleIdOfConditionnementsListConditionnements.getConditionnementsList().remove(conditionnementsListConditionnements);
                    oldArticleIdOfConditionnementsListConditionnements = em.merge(oldArticleIdOfConditionnementsListConditionnements);
                }
            }
            for (Conditionnements conditionnementsList1Conditionnements : articles.getConditionnementsList1()) {
                Articles oldArticleSourceIdOfConditionnementsList1Conditionnements = conditionnementsList1Conditionnements.getArticleSourceId();
                conditionnementsList1Conditionnements.setArticleSourceId(articles);
                conditionnementsList1Conditionnements = em.merge(conditionnementsList1Conditionnements);
                if (oldArticleSourceIdOfConditionnementsList1Conditionnements != null) {
                    oldArticleSourceIdOfConditionnementsList1Conditionnements.getConditionnementsList1().remove(conditionnementsList1Conditionnements);
                    oldArticleSourceIdOfConditionnementsList1Conditionnements = em.merge(oldArticleSourceIdOfConditionnementsList1Conditionnements);
                }
            }
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : articles.getOperationsConditionnementList()) {
                Articles oldArticleProduitIdOfOperationsConditionnementListOperationsConditionnement = operationsConditionnementListOperationsConditionnement.getArticleProduitId();
                operationsConditionnementListOperationsConditionnement.setArticleProduitId(articles);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
                if (oldArticleProduitIdOfOperationsConditionnementListOperationsConditionnement != null) {
                    oldArticleProduitIdOfOperationsConditionnementListOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListOperationsConditionnement);
                    oldArticleProduitIdOfOperationsConditionnementListOperationsConditionnement = em.merge(oldArticleProduitIdOfOperationsConditionnementListOperationsConditionnement);
                }
            }
            for (OperationsConditionnement operationsConditionnementList1OperationsConditionnement : articles.getOperationsConditionnementList1()) {
                Articles oldArticleSourceIdOfOperationsConditionnementList1OperationsConditionnement = operationsConditionnementList1OperationsConditionnement.getArticleSourceId();
                operationsConditionnementList1OperationsConditionnement.setArticleSourceId(articles);
                operationsConditionnementList1OperationsConditionnement = em.merge(operationsConditionnementList1OperationsConditionnement);
                if (oldArticleSourceIdOfOperationsConditionnementList1OperationsConditionnement != null) {
                    oldArticleSourceIdOfOperationsConditionnementList1OperationsConditionnement.getOperationsConditionnementList1().remove(operationsConditionnementList1OperationsConditionnement);
                    oldArticleSourceIdOfOperationsConditionnementList1OperationsConditionnement = em.merge(oldArticleSourceIdOfOperationsConditionnementList1OperationsConditionnement);
                }
            }
            for (ArticleConditionnements articleConditionnementsListArticleConditionnements : articles.getArticleConditionnementsList()) {
                Articles oldArticleIdOfArticleConditionnementsListArticleConditionnements = articleConditionnementsListArticleConditionnements.getArticleId();
                articleConditionnementsListArticleConditionnements.setArticleId(articles);
                articleConditionnementsListArticleConditionnements = em.merge(articleConditionnementsListArticleConditionnements);
                if (oldArticleIdOfArticleConditionnementsListArticleConditionnements != null) {
                    oldArticleIdOfArticleConditionnementsListArticleConditionnements.getArticleConditionnementsList().remove(articleConditionnementsListArticleConditionnements);
                    oldArticleIdOfArticleConditionnementsListArticleConditionnements = em.merge(oldArticleIdOfArticleConditionnementsListArticleConditionnements);
                }
            }
            for (MouvementsStock mouvementsStockListMouvementsStock : articles.getMouvementsStockList()) {
                Articles oldArticleIdOfMouvementsStockListMouvementsStock = mouvementsStockListMouvementsStock.getArticleId();
                mouvementsStockListMouvementsStock.setArticleId(articles);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
                if (oldArticleIdOfMouvementsStockListMouvementsStock != null) {
                    oldArticleIdOfMouvementsStockListMouvementsStock.getMouvementsStockList().remove(mouvementsStockListMouvementsStock);
                    oldArticleIdOfMouvementsStockListMouvementsStock = em.merge(oldArticleIdOfMouvementsStockListMouvementsStock);
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

    public void edit(Articles articles) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Articles persistentArticles = em.find(Articles.class, articles.getId());
            Stocks stocksOld = persistentArticles.getStocks();
            Stocks stocksNew = articles.getStocks();
            Categories categorieIdOld = persistentArticles.getCategorieId();
            Categories categorieIdNew = articles.getCategorieId();
            Unites uniteIdOld = persistentArticles.getUniteId();
            Unites uniteIdNew = articles.getUniteId();
            List<Alertes> alertesListOld = persistentArticles.getAlertesList();
            List<Alertes> alertesListNew = articles.getAlertesList();
            List<Conditionnements> conditionnementsListOld = persistentArticles.getConditionnementsList();
            List<Conditionnements> conditionnementsListNew = articles.getConditionnementsList();
            List<Conditionnements> conditionnementsList1Old = persistentArticles.getConditionnementsList1();
            List<Conditionnements> conditionnementsList1New = articles.getConditionnementsList1();
            List<OperationsConditionnement> operationsConditionnementListOld = persistentArticles.getOperationsConditionnementList();
            List<OperationsConditionnement> operationsConditionnementListNew = articles.getOperationsConditionnementList();
            List<OperationsConditionnement> operationsConditionnementList1Old = persistentArticles.getOperationsConditionnementList1();
            List<OperationsConditionnement> operationsConditionnementList1New = articles.getOperationsConditionnementList1();
            List<ArticleConditionnements> articleConditionnementsListOld = persistentArticles.getArticleConditionnementsList();
            List<ArticleConditionnements> articleConditionnementsListNew = articles.getArticleConditionnementsList();
            List<MouvementsStock> mouvementsStockListOld = persistentArticles.getMouvementsStockList();
            List<MouvementsStock> mouvementsStockListNew = articles.getMouvementsStockList();
            List<String> illegalOrphanMessages = null;
            for (ArticleConditionnements articleConditionnementsListOldArticleConditionnements : articleConditionnementsListOld) {
                if (!articleConditionnementsListNew.contains(articleConditionnementsListOldArticleConditionnements)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArticleConditionnements " + articleConditionnementsListOldArticleConditionnements + " since its articleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (stocksNew != null) {
                stocksNew = em.getReference(stocksNew.getClass(), stocksNew.getId());
                articles.setStocks(stocksNew);
            }
            if (categorieIdNew != null) {
                categorieIdNew = em.getReference(categorieIdNew.getClass(), categorieIdNew.getId());
                articles.setCategorieId(categorieIdNew);
            }
            if (uniteIdNew != null) {
                uniteIdNew = em.getReference(uniteIdNew.getClass(), uniteIdNew.getId());
                articles.setUniteId(uniteIdNew);
            }
            List<Alertes> attachedAlertesListNew = new ArrayList<Alertes>();
            for (Alertes alertesListNewAlertesToAttach : alertesListNew) {
                alertesListNewAlertesToAttach = em.getReference(alertesListNewAlertesToAttach.getClass(), alertesListNewAlertesToAttach.getId());
                attachedAlertesListNew.add(alertesListNewAlertesToAttach);
            }
            alertesListNew = attachedAlertesListNew;
            articles.setAlertesList(alertesListNew);
            List<Conditionnements> attachedConditionnementsListNew = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsListNewConditionnementsToAttach : conditionnementsListNew) {
                conditionnementsListNewConditionnementsToAttach = em.getReference(conditionnementsListNewConditionnementsToAttach.getClass(), conditionnementsListNewConditionnementsToAttach.getId());
                attachedConditionnementsListNew.add(conditionnementsListNewConditionnementsToAttach);
            }
            conditionnementsListNew = attachedConditionnementsListNew;
            articles.setConditionnementsList(conditionnementsListNew);
            List<Conditionnements> attachedConditionnementsList1New = new ArrayList<Conditionnements>();
            for (Conditionnements conditionnementsList1NewConditionnementsToAttach : conditionnementsList1New) {
                conditionnementsList1NewConditionnementsToAttach = em.getReference(conditionnementsList1NewConditionnementsToAttach.getClass(), conditionnementsList1NewConditionnementsToAttach.getId());
                attachedConditionnementsList1New.add(conditionnementsList1NewConditionnementsToAttach);
            }
            conditionnementsList1New = attachedConditionnementsList1New;
            articles.setConditionnementsList1(conditionnementsList1New);
            List<OperationsConditionnement> attachedOperationsConditionnementListNew = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnementToAttach : operationsConditionnementListNew) {
                operationsConditionnementListNewOperationsConditionnementToAttach = em.getReference(operationsConditionnementListNewOperationsConditionnementToAttach.getClass(), operationsConditionnementListNewOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementListNew.add(operationsConditionnementListNewOperationsConditionnementToAttach);
            }
            operationsConditionnementListNew = attachedOperationsConditionnementListNew;
            articles.setOperationsConditionnementList(operationsConditionnementListNew);
            List<OperationsConditionnement> attachedOperationsConditionnementList1New = new ArrayList<OperationsConditionnement>();
            for (OperationsConditionnement operationsConditionnementList1NewOperationsConditionnementToAttach : operationsConditionnementList1New) {
                operationsConditionnementList1NewOperationsConditionnementToAttach = em.getReference(operationsConditionnementList1NewOperationsConditionnementToAttach.getClass(), operationsConditionnementList1NewOperationsConditionnementToAttach.getId());
                attachedOperationsConditionnementList1New.add(operationsConditionnementList1NewOperationsConditionnementToAttach);
            }
            operationsConditionnementList1New = attachedOperationsConditionnementList1New;
            articles.setOperationsConditionnementList1(operationsConditionnementList1New);
            List<ArticleConditionnements> attachedArticleConditionnementsListNew = new ArrayList<ArticleConditionnements>();
            for (ArticleConditionnements articleConditionnementsListNewArticleConditionnementsToAttach : articleConditionnementsListNew) {
                articleConditionnementsListNewArticleConditionnementsToAttach = em.getReference(articleConditionnementsListNewArticleConditionnementsToAttach.getClass(), articleConditionnementsListNewArticleConditionnementsToAttach.getId());
                attachedArticleConditionnementsListNew.add(articleConditionnementsListNewArticleConditionnementsToAttach);
            }
            articleConditionnementsListNew = attachedArticleConditionnementsListNew;
            articles.setArticleConditionnementsList(articleConditionnementsListNew);
            List<MouvementsStock> attachedMouvementsStockListNew = new ArrayList<MouvementsStock>();
            for (MouvementsStock mouvementsStockListNewMouvementsStockToAttach : mouvementsStockListNew) {
                mouvementsStockListNewMouvementsStockToAttach = em.getReference(mouvementsStockListNewMouvementsStockToAttach.getClass(), mouvementsStockListNewMouvementsStockToAttach.getId());
                attachedMouvementsStockListNew.add(mouvementsStockListNewMouvementsStockToAttach);
            }
            mouvementsStockListNew = attachedMouvementsStockListNew;
            articles.setMouvementsStockList(mouvementsStockListNew);
            articles = em.merge(articles);
            if (stocksOld != null && !stocksOld.equals(stocksNew)) {
                stocksOld.setArticleId(null);
                stocksOld = em.merge(stocksOld);
            }
            if (stocksNew != null && !stocksNew.equals(stocksOld)) {
                Articles oldArticleIdOfStocks = stocksNew.getArticleId();
                if (oldArticleIdOfStocks != null) {
                    oldArticleIdOfStocks.setStocks(null);
                    oldArticleIdOfStocks = em.merge(oldArticleIdOfStocks);
                }
                stocksNew.setArticleId(articles);
                stocksNew = em.merge(stocksNew);
            }
            if (categorieIdOld != null && !categorieIdOld.equals(categorieIdNew)) {
                categorieIdOld.getArticlesList().remove(articles);
                categorieIdOld = em.merge(categorieIdOld);
            }
            if (categorieIdNew != null && !categorieIdNew.equals(categorieIdOld)) {
                categorieIdNew.getArticlesList().add(articles);
                categorieIdNew = em.merge(categorieIdNew);
            }
            if (uniteIdOld != null && !uniteIdOld.equals(uniteIdNew)) {
                uniteIdOld.getArticlesList().remove(articles);
                uniteIdOld = em.merge(uniteIdOld);
            }
            if (uniteIdNew != null && !uniteIdNew.equals(uniteIdOld)) {
                uniteIdNew.getArticlesList().add(articles);
                uniteIdNew = em.merge(uniteIdNew);
            }
            for (Alertes alertesListOldAlertes : alertesListOld) {
                if (!alertesListNew.contains(alertesListOldAlertes)) {
                    alertesListOldAlertes.setArticleId(null);
                    alertesListOldAlertes = em.merge(alertesListOldAlertes);
                }
            }
            for (Alertes alertesListNewAlertes : alertesListNew) {
                if (!alertesListOld.contains(alertesListNewAlertes)) {
                    Articles oldArticleIdOfAlertesListNewAlertes = alertesListNewAlertes.getArticleId();
                    alertesListNewAlertes.setArticleId(articles);
                    alertesListNewAlertes = em.merge(alertesListNewAlertes);
                    if (oldArticleIdOfAlertesListNewAlertes != null && !oldArticleIdOfAlertesListNewAlertes.equals(articles)) {
                        oldArticleIdOfAlertesListNewAlertes.getAlertesList().remove(alertesListNewAlertes);
                        oldArticleIdOfAlertesListNewAlertes = em.merge(oldArticleIdOfAlertesListNewAlertes);
                    }
                }
            }
            for (Conditionnements conditionnementsListOldConditionnements : conditionnementsListOld) {
                if (!conditionnementsListNew.contains(conditionnementsListOldConditionnements)) {
                    conditionnementsListOldConditionnements.setArticleId(null);
                    conditionnementsListOldConditionnements = em.merge(conditionnementsListOldConditionnements);
                }
            }
            for (Conditionnements conditionnementsListNewConditionnements : conditionnementsListNew) {
                if (!conditionnementsListOld.contains(conditionnementsListNewConditionnements)) {
                    Articles oldArticleIdOfConditionnementsListNewConditionnements = conditionnementsListNewConditionnements.getArticleId();
                    conditionnementsListNewConditionnements.setArticleId(articles);
                    conditionnementsListNewConditionnements = em.merge(conditionnementsListNewConditionnements);
                    if (oldArticleIdOfConditionnementsListNewConditionnements != null && !oldArticleIdOfConditionnementsListNewConditionnements.equals(articles)) {
                        oldArticleIdOfConditionnementsListNewConditionnements.getConditionnementsList().remove(conditionnementsListNewConditionnements);
                        oldArticleIdOfConditionnementsListNewConditionnements = em.merge(oldArticleIdOfConditionnementsListNewConditionnements);
                    }
                }
            }
            for (Conditionnements conditionnementsList1OldConditionnements : conditionnementsList1Old) {
                if (!conditionnementsList1New.contains(conditionnementsList1OldConditionnements)) {
                    conditionnementsList1OldConditionnements.setArticleSourceId(null);
                    conditionnementsList1OldConditionnements = em.merge(conditionnementsList1OldConditionnements);
                }
            }
            for (Conditionnements conditionnementsList1NewConditionnements : conditionnementsList1New) {
                if (!conditionnementsList1Old.contains(conditionnementsList1NewConditionnements)) {
                    Articles oldArticleSourceIdOfConditionnementsList1NewConditionnements = conditionnementsList1NewConditionnements.getArticleSourceId();
                    conditionnementsList1NewConditionnements.setArticleSourceId(articles);
                    conditionnementsList1NewConditionnements = em.merge(conditionnementsList1NewConditionnements);
                    if (oldArticleSourceIdOfConditionnementsList1NewConditionnements != null && !oldArticleSourceIdOfConditionnementsList1NewConditionnements.equals(articles)) {
                        oldArticleSourceIdOfConditionnementsList1NewConditionnements.getConditionnementsList1().remove(conditionnementsList1NewConditionnements);
                        oldArticleSourceIdOfConditionnementsList1NewConditionnements = em.merge(oldArticleSourceIdOfConditionnementsList1NewConditionnements);
                    }
                }
            }
            for (OperationsConditionnement operationsConditionnementListOldOperationsConditionnement : operationsConditionnementListOld) {
                if (!operationsConditionnementListNew.contains(operationsConditionnementListOldOperationsConditionnement)) {
                    operationsConditionnementListOldOperationsConditionnement.setArticleProduitId(null);
                    operationsConditionnementListOldOperationsConditionnement = em.merge(operationsConditionnementListOldOperationsConditionnement);
                }
            }
            for (OperationsConditionnement operationsConditionnementListNewOperationsConditionnement : operationsConditionnementListNew) {
                if (!operationsConditionnementListOld.contains(operationsConditionnementListNewOperationsConditionnement)) {
                    Articles oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement = operationsConditionnementListNewOperationsConditionnement.getArticleProduitId();
                    operationsConditionnementListNewOperationsConditionnement.setArticleProduitId(articles);
                    operationsConditionnementListNewOperationsConditionnement = em.merge(operationsConditionnementListNewOperationsConditionnement);
                    if (oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement != null && !oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement.equals(articles)) {
                        oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement.getOperationsConditionnementList().remove(operationsConditionnementListNewOperationsConditionnement);
                        oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement = em.merge(oldArticleProduitIdOfOperationsConditionnementListNewOperationsConditionnement);
                    }
                }
            }
            for (OperationsConditionnement operationsConditionnementList1OldOperationsConditionnement : operationsConditionnementList1Old) {
                if (!operationsConditionnementList1New.contains(operationsConditionnementList1OldOperationsConditionnement)) {
                    operationsConditionnementList1OldOperationsConditionnement.setArticleSourceId(null);
                    operationsConditionnementList1OldOperationsConditionnement = em.merge(operationsConditionnementList1OldOperationsConditionnement);
                }
            }
            for (OperationsConditionnement operationsConditionnementList1NewOperationsConditionnement : operationsConditionnementList1New) {
                if (!operationsConditionnementList1Old.contains(operationsConditionnementList1NewOperationsConditionnement)) {
                    Articles oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement = operationsConditionnementList1NewOperationsConditionnement.getArticleSourceId();
                    operationsConditionnementList1NewOperationsConditionnement.setArticleSourceId(articles);
                    operationsConditionnementList1NewOperationsConditionnement = em.merge(operationsConditionnementList1NewOperationsConditionnement);
                    if (oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement != null && !oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement.equals(articles)) {
                        oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement.getOperationsConditionnementList1().remove(operationsConditionnementList1NewOperationsConditionnement);
                        oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement = em.merge(oldArticleSourceIdOfOperationsConditionnementList1NewOperationsConditionnement);
                    }
                }
            }
            for (ArticleConditionnements articleConditionnementsListNewArticleConditionnements : articleConditionnementsListNew) {
                if (!articleConditionnementsListOld.contains(articleConditionnementsListNewArticleConditionnements)) {
                    Articles oldArticleIdOfArticleConditionnementsListNewArticleConditionnements = articleConditionnementsListNewArticleConditionnements.getArticleId();
                    articleConditionnementsListNewArticleConditionnements.setArticleId(articles);
                    articleConditionnementsListNewArticleConditionnements = em.merge(articleConditionnementsListNewArticleConditionnements);
                    if (oldArticleIdOfArticleConditionnementsListNewArticleConditionnements != null && !oldArticleIdOfArticleConditionnementsListNewArticleConditionnements.equals(articles)) {
                        oldArticleIdOfArticleConditionnementsListNewArticleConditionnements.getArticleConditionnementsList().remove(articleConditionnementsListNewArticleConditionnements);
                        oldArticleIdOfArticleConditionnementsListNewArticleConditionnements = em.merge(oldArticleIdOfArticleConditionnementsListNewArticleConditionnements);
                    }
                }
            }
            for (MouvementsStock mouvementsStockListOldMouvementsStock : mouvementsStockListOld) {
                if (!mouvementsStockListNew.contains(mouvementsStockListOldMouvementsStock)) {
                    mouvementsStockListOldMouvementsStock.setArticleId(null);
                    mouvementsStockListOldMouvementsStock = em.merge(mouvementsStockListOldMouvementsStock);
                }
            }
            for (MouvementsStock mouvementsStockListNewMouvementsStock : mouvementsStockListNew) {
                if (!mouvementsStockListOld.contains(mouvementsStockListNewMouvementsStock)) {
                    Articles oldArticleIdOfMouvementsStockListNewMouvementsStock = mouvementsStockListNewMouvementsStock.getArticleId();
                    mouvementsStockListNewMouvementsStock.setArticleId(articles);
                    mouvementsStockListNewMouvementsStock = em.merge(mouvementsStockListNewMouvementsStock);
                    if (oldArticleIdOfMouvementsStockListNewMouvementsStock != null && !oldArticleIdOfMouvementsStockListNewMouvementsStock.equals(articles)) {
                        oldArticleIdOfMouvementsStockListNewMouvementsStock.getMouvementsStockList().remove(mouvementsStockListNewMouvementsStock);
                        oldArticleIdOfMouvementsStockListNewMouvementsStock = em.merge(oldArticleIdOfMouvementsStockListNewMouvementsStock);
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
                Integer id = articles.getId();
                if (findArticles(id) == null) {
                    throw new NonexistentEntityException("The articles with id " + id + " no longer exists.");
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
            Articles articles;
            try {
                articles = em.getReference(Articles.class, id);
                articles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArticleConditionnements> articleConditionnementsListOrphanCheck = articles.getArticleConditionnementsList();
            for (ArticleConditionnements articleConditionnementsListOrphanCheckArticleConditionnements : articleConditionnementsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articles (" + articles + ") cannot be destroyed since the ArticleConditionnements " + articleConditionnementsListOrphanCheckArticleConditionnements + " in its articleConditionnementsList field has a non-nullable articleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Stocks stocks = articles.getStocks();
            if (stocks != null) {
                stocks.setArticleId(null);
                stocks = em.merge(stocks);
            }
            Categories categorieId = articles.getCategorieId();
            if (categorieId != null) {
                categorieId.getArticlesList().remove(articles);
                categorieId = em.merge(categorieId);
            }
            Unites uniteId = articles.getUniteId();
            if (uniteId != null) {
                uniteId.getArticlesList().remove(articles);
                uniteId = em.merge(uniteId);
            }
            List<Alertes> alertesList = articles.getAlertesList();
            for (Alertes alertesListAlertes : alertesList) {
                alertesListAlertes.setArticleId(null);
                alertesListAlertes = em.merge(alertesListAlertes);
            }
            List<Conditionnements> conditionnementsList = articles.getConditionnementsList();
            for (Conditionnements conditionnementsListConditionnements : conditionnementsList) {
                conditionnementsListConditionnements.setArticleId(null);
                conditionnementsListConditionnements = em.merge(conditionnementsListConditionnements);
            }
            List<Conditionnements> conditionnementsList1 = articles.getConditionnementsList1();
            for (Conditionnements conditionnementsList1Conditionnements : conditionnementsList1) {
                conditionnementsList1Conditionnements.setArticleSourceId(null);
                conditionnementsList1Conditionnements = em.merge(conditionnementsList1Conditionnements);
            }
            List<OperationsConditionnement> operationsConditionnementList = articles.getOperationsConditionnementList();
            for (OperationsConditionnement operationsConditionnementListOperationsConditionnement : operationsConditionnementList) {
                operationsConditionnementListOperationsConditionnement.setArticleProduitId(null);
                operationsConditionnementListOperationsConditionnement = em.merge(operationsConditionnementListOperationsConditionnement);
            }
            List<OperationsConditionnement> operationsConditionnementList1 = articles.getOperationsConditionnementList1();
            for (OperationsConditionnement operationsConditionnementList1OperationsConditionnement : operationsConditionnementList1) {
                operationsConditionnementList1OperationsConditionnement.setArticleSourceId(null);
                operationsConditionnementList1OperationsConditionnement = em.merge(operationsConditionnementList1OperationsConditionnement);
            }
            List<MouvementsStock> mouvementsStockList = articles.getMouvementsStockList();
            for (MouvementsStock mouvementsStockListMouvementsStock : mouvementsStockList) {
                mouvementsStockListMouvementsStock.setArticleId(null);
                mouvementsStockListMouvementsStock = em.merge(mouvementsStockListMouvementsStock);
            }
            em.remove(articles);
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

    public List<Articles> findArticlesEntities() {
        return findArticlesEntities(true, -1, -1);
    }

    public List<Articles> findArticlesEntities(int maxResults, int firstResult) {
        return findArticlesEntities(false, maxResults, firstResult);
    }

    private List<Articles> findArticlesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articles.class));
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

    public Articles findArticles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articles.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticlesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articles> rt = cq.from(Articles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
