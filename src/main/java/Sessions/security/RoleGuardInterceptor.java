package Sessions.security;

import com.gestionconditionnement.beans.LoginBean;
import jakarta.annotation.Priority;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

@RoleGuarded
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class RoleGuardInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Set<String> ADMIN_ONLY = Set.of(
            "UtilisateursController", "RolesController", "ParametresController",
            "ParametresApplicationController", "HistoriqueController", "HistoriqueActionsController");
    private static final Set<String> OPERATOR_MUTATIONS = Set.of(
            "OperationsConditionnementController", "MouvementsStockController",
            "EcartsStockController", "AlertesController", "NotificationsController");
    private static final Set<String> AUDIT_READ_ONLY = Set.of(
            "HistoriqueController", "HistoriqueActionsController");

    @Inject
    private LoginBean loginBean;

    @AroundInvoke
    public Object authorize(InvocationContext invocation) throws Exception {
        String method = invocation.getMethod().getName();
        String role = loginBean.getUtilisateurConnecte() == null
                || loginBean.getUtilisateurConnecte().getRole() == null
                ? "" : loginBean.getUtilisateurConnecte().getRole().trim().toUpperCase(Locale.ROOT);
        String controller = invocation.getMethod().getDeclaringClass().getSimpleName();

        boolean admin = Set.of("ADMIN", "ADMINISTRATEUR").contains(role);
        boolean gestionnaire = role.equals("GESTIONNAIRE");
        boolean operateur = Set.of("OPERATEUR", "OPÉRATEUR").contains(role);
        boolean referenceUtilisateurPourOperation = controller.equals("UtilisateursController")
                && Set.of("getItemsAvailableSelectOne", "getItemsAvailableSelectOneById",
                        "getItemsAvailableSelectOneByIdBigInteger", "getConverter", "libellePourId").contains(method)
                && facesViewStartsWith("/view/operationsConditionnement/", "/view/mouvementsStock/",
                        "/view/ecartsStock/", "/view/notifications/");
        if (ADMIN_ONLY.contains(controller) && !admin && !referenceUtilisateurPourOperation) {
            return deny();
        }
        if (AUDIT_READ_ONLY.contains(controller) && isMutation(method)) {
            return deny();
        }
        if (!isMutation(method)) {
            return invocation.proceed();
        }
        boolean autorise = admin
                || (gestionnaire && !ADMIN_ONLY.contains(controller))
                || (operateur && OPERATOR_MUTATIONS.contains(controller));
        if (autorise) {
            return invocation.proceed();
        }

        return deny();
    }

    private boolean facesViewStartsWith(String... prefixes) {
        FacesContext faces = FacesContext.getCurrentInstance();
        String viewId = faces == null || faces.getViewRoot() == null ? "" : faces.getViewRoot().getViewId();
        for (String prefix : prefixes) {
            if (viewId.startsWith(prefix)) return true;
        }
        return false;
    }

    private Object deny() {
        FacesContext faces = FacesContext.getCurrentInstance();
        if (faces != null) {
            faces.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Action non autorisée", "Votre rôle ne permet pas d’effectuer cette modification."));
        }
        return null;
    }

    private boolean isMutation(String method) {
        return Set.of("create", "update", "destroy", "destroyAndView", "prepareCreate", "prepareEdit").contains(method);
    }
}
