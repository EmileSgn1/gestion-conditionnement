package Sessions;

import Entities.Parametres;
import Sessions.util.JsfUtil;
import Sessions.util.PaginationHelper;
import Controllers.ParametresJpaController;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;
import jakarta.annotation.Resource;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.model.DataModel;
import jakarta.faces.model.ListDataModel;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;

@Sessions.security.RoleGuarded
@Named("parametresController")
@SessionScoped
public class ParametresController implements Serializable {

    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "my_persistence_unit")
    private EntityManagerFactory emf = null;

    private Parametres current;
    private DataModel items = null;
    private ParametresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ParametresController() {
    }

    public Parametres getSelected() {
        if (current == null) {
            current = new Parametres();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ParametresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new ParametresJpaController(utx, emf);
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getParametresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findParametresEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Parametres) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        warnStructuralChange();
        recreateModel();
        return "List";
    }

    public String create() {
        warnStructuralChange();
        return null;
    }
    public String prepareEdit() {
        current = (Parametres) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        if (!valeurValide()) {
            return null;
        }
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametresUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    private boolean valeurValide() {
        String cle = current == null || current.getCle() == null ? "" : current.getCle().trim();
        String valeur = current == null || current.getValeur() == null ? "" : current.getValeur().trim();
        String message = null;
        if (valeur.isEmpty()) {
            message = "Saisissez une valeur pour ce réglage.";
        } else if ("SEUIL_ALERTE_DEFAUT".equals(cle)) {
            try {
                if (new BigDecimal(valeur).signum() < 0) message = "Le seuil d’alerte doit être positif ou nul.";
            } catch (NumberFormatException exception) {
                message = "Saisissez un nombre valide pour le seuil d’alerte.";
            }
        } else if ("VALIDATION_AUTO_ECART".equals(cle)) {
            if (!"true".equalsIgnoreCase(valeur) && !"false".equalsIgnoreCase(valeur)) {
                message = "Choisissez Oui ou Non pour la validation automatique.";
            } else {
                current.setValeur(valeur.toLowerCase(Locale.ROOT));
            }
        }
        if (message == null) return true;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
        return false;
    }

    public String destroy() {
        warnStructuralChange();
        return "List";
    }

    public String destroyAndView() {
        warnStructuralChange();
        return "List";
    }

    private void warnStructuralChange() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Les clés de réglage sont gérées par l’application. Modifiez uniquement leur valeur.", ""));
        }
    }
    private void performDestroy() {
        try {
            getJpaController().destroy(current.getId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ParametresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getParametresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findParametresEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findParametresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findParametresEntities(), true);
    }

    public Converter getConverter() {
        return new ParametresControllerConverter();
    }

    @FacesConverter(forClass = Parametres.class)
    public static class ParametresControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametresController controller = (ParametresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametresController");
            return controller.getJpaController().findParametres(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof String) {
                return (String) object;
            }
            if (object instanceof Parametres) {
                Parametres o = (Parametres) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Parametres.class.getName());
            }
        }

    }

}


