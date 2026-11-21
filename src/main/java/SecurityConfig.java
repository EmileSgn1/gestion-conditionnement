import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

/** Identité store sans authentification de démonstration permissive. */
@ApplicationScoped
public class SecurityConfig implements IdentityStore {
    @Override
    public CredentialValidationResult validate(Credential credential) {
        // LoginBean vérifie les identifiants contre les utilisateurs actifs en base.
        // Ne jamais accorder de rôles à une identité non authentifiée.
        return CredentialValidationResult.INVALID_RESULT;
    }
}
