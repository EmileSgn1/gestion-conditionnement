package Sessions.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.model.SelectItem;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JsfUtil {
    private static final Logger LOGGER = Logger.getLogger(JsfUtil.class.getName());
    private static final String SAFE_PERSISTENCE_ERROR = "L’opération n’a pas pu être enregistrée. Vérifiez les informations saisies et réessayez.";
    private static final String SAFE_VALIDATION_ERROR = "Veuillez vérifier les champs saisis avant de continuer.";

    private JsfUtil() { }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) items[i++] = new SelectItem(null, "Sélectionner…");
        for (Object entity : entities) items[i++] = new SelectItem(entity, entity.toString());
        return items;
    }

    /** Builds a menu whose values are scalar database identifiers instead of entity objects. */
    public static SelectItem[] getSelectItemsById(List<?> entities, boolean selectOne) {
        return getSelectItemsById(entities, selectOne, null);
    }

    public static SelectItem[] getSelectItemsById(List<?> entities, boolean selectOne, Class<?> targetType) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) items[i++] = new SelectItem(null, "Sélectionner…");
        try {
            for (Object entity : entities) {
                Method idGetter = entity.getClass().getMethod("getId");
                Object id = idGetter.invoke(entity);
                if (id != null && targetType == java.math.BigInteger.class) id = new java.math.BigInteger(id.toString());
                items[i++] = new SelectItem(id, entity.toString());
            }
        } catch (ReflectiveOperationException ex) {
            LOGGER.log(Level.SEVERE, "Impossible de construire la liste de références", ex);
            throw new IllegalStateException("Impossible de charger les options du formulaire.", ex);
        }
        return items;
    }

    /** Logs the underlying exception, while exposing only a safe, localized fallback to the client. */
    public static void addErrorMessage(Exception exception, String safeMessage) {
        LOGGER.log(Level.SEVERE, "Échec d’une opération métier", exception);
        addClientMessage(FacesMessage.SEVERITY_ERROR, isSafeMessage(safeMessage) ? safeMessage : SAFE_PERSISTENCE_ERROR);
    }

    /** Never renders arbitrary text because callers may accidentally pass exception details. */
    public static void addErrorMessage(String ignored) {
        addClientMessage(FacesMessage.SEVERITY_ERROR, SAFE_VALIDATION_ERROR);
    }

    public static void addErrorMessages(List<String> ignored) {
        addClientMessage(FacesMessage.SEVERITY_ERROR, SAFE_VALIDATION_ERROR);
    }

    public static void addSuccessMessage(String message) {
        addClientMessage(FacesMessage.SEVERITY_INFO, message);
    }

    private static boolean isSafeMessage(String message) {
        return message != null && !message.isBlank() && !message.matches("(?is).*(exception|constraint|sql|jdbc|org\\.|jakarta\\.|hibernate|eclipselink|wildfly|\n\\s*at ).*");
    }

    private static void addClientMessage(FacesMessage.Severity severity, String message) {
        FacesContext faces = FacesContext.getCurrentInstance();
        if (faces != null) faces.addMessage(null, new FacesMessage(severity, message, message));
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String parameter, Converter converter, UIComponent component) {
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, getRequestParameter(parameter));
    }
}
