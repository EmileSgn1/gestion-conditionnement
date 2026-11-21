package com.gestionconditionnement.security;
import Entities.Utilisateurs;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

/** Enforces session and page-role access for JSF views. */
public class AccessPhaseListener implements PhaseListener, Serializable {
 private static final long serialVersionUID=1L;
 private static final Set<String> ADMIN=Set.of("ADMIN","ADMINISTRATEUR");
 @Override public PhaseId getPhaseId(){return PhaseId.RESTORE_VIEW;}
 @Override public void beforePhase(PhaseEvent e){}
 @Override public void afterPhase(PhaseEvent e){
  FacesContext f=e.getFacesContext(); if(f==null||f.getResponseComplete()||f.getViewRoot()==null)return;
  String v=f.getViewRoot().getViewId(); if(v==null||v.endsWith("/login.xhtml"))return;
  if(v!=null&&(v.startsWith("/view/parametresApplication/")||v.equals("/view/parametres/Create.xhtml"))){go(f,"/faces/view/parametres/List.xhtml");return;}
  if(v!=null&&v.startsWith("/view/roles/")){go(f,"/faces/view/utilisateurs/List.xhtml");return;}
  if(v!=null&&((v.startsWith("/view/historique/")||v.startsWith("/view/historiqueActions/"))&&(v.endsWith("/Create.xhtml")||v.endsWith("/Edit.xhtml")))){go(f,"/faces/view/"+v.substring(6).split("/",2)[0]+"/List.xhtml");return;}
  Object b=f.getApplication().getELResolver().getValue(f.getELContext(),null,"loginBean");
  Utilisateurs u=b instanceof com.gestionconditionnement.beans.LoginBean l?l.getUtilisateurConnecte():null;
  if(u==null){go(f,"/faces/login.xhtml");return;}
  String r=u.getRole()==null?"":u.getRole().trim().toUpperCase(Locale.ROOT);
  boolean a=ADMIN.contains(r),m=r.equals("GESTIONNAIRE"),o=Set.of("OPERATEUR","OPÉRATEUR").contains(r),c=Set.of("CONSULTANT","UTILISATEUR").contains(r),ok=true;
  if(v.endsWith("/admin-dashboard.xhtml"))ok=a;
  else if(v.endsWith("/gestionnaire-dashboard.xhtml"))ok=a||m;
  else if(v.endsWith("/operateur-dashboard.xhtml"))ok=a||o;
  else if(v.startsWith("/view/")){
   String module=v.substring(6).split("/",2)[0];
   if(Set.of("utilisateurs","roles","parametres","parametresApplication","historique","historiqueActions").contains(module))ok=a;
   else if(Set.of("articles","categories","conditionnements","conditionnementsCatalogue","compositionsConditionnements","unites").contains(module))ok=a||m;
   else if(Set.of("operationsConditionnement","mouvementsStock","ecartsStock","alertes","notifications","stocks","typesConditionnement","articleConditionnements").contains(module))ok=a||m||o||c;
   else ok=false;
   if(c&&(v.endsWith("/Create.xhtml")||v.endsWith("/Edit.xhtml")))ok=false;
  }
  if(!ok){f.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Accès refusé","Votre rôle ne permet pas d’ouvrir cette page."));go(f,"/faces/index.xhtml");}
 }
 private void go(FacesContext f,String p){try{f.getExternalContext().redirect(f.getExternalContext().getRequestContextPath()+p);f.responseComplete();}catch(IOException x){throw new IllegalStateException(x);}}
}


