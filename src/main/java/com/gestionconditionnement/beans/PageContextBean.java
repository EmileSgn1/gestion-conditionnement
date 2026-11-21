package com.gestionconditionnement.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.Map;

@Named("pageContextBean")
@RequestScoped
public class PageContextBean {
    private static final Map<String, String> MODULES = Map.ofEntries(
        Map.entry("alertes", "Alertes"), Map.entry("articleConditionnements", "Articles et conditionnements"),
        Map.entry("articles", "Articles"), Map.entry("categories", "Catégories"),
        Map.entry("compositionsConditionnements", "Compositions"), Map.entry("conditionnements", "Conditionnements"),
        Map.entry("conditionnementsCatalogue", "Catalogue des conditionnements"), Map.entry("ecartsStock", "Écarts de stock"),
        Map.entry("historique", "Historique"), Map.entry("historiqueActions", "Historique des actions"),
        Map.entry("mouvementsStock", "Mouvements de stock"), Map.entry("notifications", "Notifications"),
        Map.entry("operationsConditionnement", "Opérations de conditionnement"), Map.entry("parametres", "Paramètres"),
        Map.entry("parametresApplication", "Paramètres de l’application"), Map.entry("roles", "Rôles"),
        Map.entry("stocks", "Stocks"), Map.entry("typesConditionnement", "Types de conditionnement"),
        Map.entry("unites", "Unités"), Map.entry("utilisateurs", "Utilisateurs"));

    public String getBreadcrumb() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context == null || context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
        if (viewId.startsWith("/view/")) {
            String[] parts = viewId.substring(6).split("/");
            String module = MODULES.getOrDefault(parts[0], parts[0]);
            if (parts.length > 1) {
                String operation = switch (parts[1].replace(".xhtml", "")) {
                    case "Create" -> " / Nouveau";
                    case "Edit" -> " / Modifier";
                    case "View" -> " / Détail";
                    default -> "";
                };
                return module + operation;
            }
            return module;
        }
        if (viewId.endsWith("admin-dashboard.xhtml")) return "Tableau de bord";
        if (viewId.endsWith("gestionnaire-dashboard.xhtml")) return "Gestion des stocks";
        if (viewId.endsWith("operateur-dashboard.xhtml")) return "Espace opérateur";
        return "Accueil";
    }

    public boolean isCrudPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context == null || context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
        if (!viewId.startsWith("/view/")) return false;
        return !(viewId.startsWith("/view/operationsConditionnement/Create") || viewId.startsWith("/view/operationsConditionnement/Edit") || viewId.startsWith("/view/operationsConditionnement/List"));
    }

    public boolean isGlobalMessageAreaVisible() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context == null || context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
        return !viewId.startsWith("/view/") && !viewId.endsWith("/login.xhtml");
    }
    public String getPageDescription() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context == null || context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
        if (viewId.endsWith("/Create.xhtml")) return "Saisissez les informations puis enregistrez la fiche.";
        if (viewId.endsWith("/Edit.xhtml")) return "Mettez à jour les informations de cette fiche.";
        if (viewId.endsWith("/View.xhtml")) return "Consultez les informations de cette fiche.";
        return "Consultez et gérez les enregistrements de ce module.";
    }
    public String getPageHeading() { return getBreadcrumb().replace(" / ", " · "); }
    public boolean active(String prefix) {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context == null || context.getViewRoot() == null ? "" : context.getViewRoot().getViewId();
        return viewId.startsWith(prefix);
    }
}




