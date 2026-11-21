package com.gestionconditionnement.beans;

import Entities.OperationsConditionnement;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("dashboardBean")
@RequestScoped
public class DashboardBean {
    private static final Logger LOG = Logger.getLogger(DashboardBean.class.getName());

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private Long totalArticles;
    private Long totalStocks;
    private Long totalOperations;
    private Long totalAlertes;
    private List<OperationsConditionnement> dernieresOperations;
    private boolean operationsIndisponibles;

    public Long getTotalArticles() {
        if (totalArticles == null) totalArticles = count("SELECT COUNT(a) FROM Articles a", "articles");
        return totalArticles;
    }

    public Long getTotalStocks() {
        if (totalStocks == null) totalStocks = count("SELECT COUNT(s) FROM Stocks s", "stocks");
        return totalStocks;
    }

    public Long getTotalOperations() {
        if (totalOperations == null) totalOperations = count("SELECT COUNT(o) FROM OperationsConditionnement o", "opérations");
        return totalOperations;
    }

    public Long getTotalAlertes() {
        if (totalAlertes == null) totalAlertes = count("SELECT COUNT(al) FROM Alertes al WHERE al.actif = true AND al.retiree = false", "alertes actives");
        return totalAlertes;
    }

    private Long count(String jpql, String libelle) {
        try {
            return em.createQuery(jpql, Long.class).getSingleResult();
        } catch (RuntimeException e) {
            LOG.log(Level.WARNING, "Impossible de charger le nombre de " + libelle, e);
            return null;
        }
    }

    public List<OperationsConditionnement> getDernieresOperations() {
        if (dernieresOperations == null && !operationsIndisponibles) {
            try {
                dernieresOperations = em.createQuery(
                        "SELECT o FROM OperationsConditionnement o ORDER BY o.dateOperation DESC, o.id DESC",
                        OperationsConditionnement.class)
                        .setMaxResults(5)
                        .getResultList();
            } catch (RuntimeException e) {
                LOG.log(Level.WARNING, "Impossible de charger les opérations récentes", e);
                operationsIndisponibles = true;
                dernieresOperations = Collections.emptyList();
            }
        }
        return dernieresOperations == null ? Collections.emptyList() : dernieresOperations;
    }

    public boolean isOperationsIndisponibles() {
        getDernieresOperations();
        return operationsIndisponibles;
    }
}

