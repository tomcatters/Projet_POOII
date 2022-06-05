package entreprise_info.presenter;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;
import entreprise_info.modele.DAOProjet;
import entreprise_info.vue.VueProjetInterface;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PresenterProjet {

    private DAOProjet mdP;

    private VueProjetInterface vueP;

    private PresenterEmploye pE;

    private PresenterDisciplines pD;

    private Scanner sc = new Scanner(System.in);

    public PresenterProjet(DAOProjet mdP, VueProjetInterface vueP) {
        this.mdP = mdP;
        this.vueP = vueP;
    }

    public void setpE(PresenterEmploye pE) {
        this.pE = pE;
    }

    public void setpD(PresenterDisciplines pD) {
        this.pD = pD;
    }

    public PresenterDisciplines getpD() {
        return pD;
    }

    public void gestion() {
        do {
            int ch = vueP.menu(new String[]{"ajout","recherche","modification","suppression","pourcentage total","gestion des equipes","option d'affichage","fin"});
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    pourTot();
                    break;
                case 6:
                    gestEqp();
                    break;
                case 7:
                    gestAff();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }

    private void gestEqp(){
        Projet p = recherche();

        if (p!=null){
            do {
                int ch = vueP.menu(new String[]{"ajout d'un employé", "modification d'un employé", "suppression d'un employé","fin"});
                switch (ch){
                    case 1:
                        addEmploye(p);
                        break;
                    case 2:
                        modifEmploye(p);
                        break;
                    case 3:
                        supEmploye(p);
                        break;
                    case 4:
                        return;
                    default:
                        vueP.displayMsg("choix invalide recommencez ");
                }
            }while (true);
        }
    }

    private void gestAff(){
        Projet p = recherche();

        if (p!=null){
            do {
                int ch = vueP.menu(new String[]{"Liste des employé d'un projet","Liste des employé ayant un niveau egal ou superieur a la disciplines de base d'un projet","fin"});
              switch (ch){
                  case 1:
                      affEmpPourDate(p);
                      break;
                  case 2:
                      affEmpDiscBase(p);
                      break;
                  case 3:return;
              }
            }while (true);
        }
    }

    protected void ajout(){
        Projet pNew = vueP.create();
        Disciplines d = pD.recherche();
        pNew.setId_DisciplineBase(d);
        pNew = mdP.create(pNew);
        if (pNew==null){
            vueP.displayMsg("erreur lors de la création du projet - doublon");
            return;
        }

        vueP.displayMsg("Projet crée");
        vueP.display(pNew);
    }

    protected Projet recherche(){
        int nRech = vueP.read();
        Projet p = new Projet(nRech,null,null,null,0,null);
        p = mdP.read(p);
        if (p == null){
            vueP.displayMsg("Projet introuvable");
            return null;
        }
        vueP.display(p);
        return p;
    }

    protected void modification(){
        Projet pRech = recherche();
        if (pRech != null) {
            pRech =  vueP.update(pRech);
            mdP.update(pRech);
        }
    }

    protected void suppression(){
        Projet pRech = recherche();
        if (pRech != null){
            String rep;
            do {
                rep = vueP.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if( mdP.delete(pRech))vueP.displayMsg("Projet supprimé");
                else vueP.displayMsg("Projet non supprimé");
            }
        }
    }

    protected void addEmploye(Projet p){
        boolean ck;
        Employe emp = pE.recherche();
        int pour = Integer.parseInt(vueP.getMsg("Pourcentage: "));
        vueP.displayMsg("Date d'engagement");
        Date d = vueP.initDate();
        ck = mdP.addEmploye(p,emp,pour,d);
        if (ck==true) {
            vueP.displayMsg("Empoyé ajouté");
        }else {
            vueP.displayMsg("Erreur");
        }
    }

    protected void modifEmploye(Projet p){
        boolean ck;
        Employe emp = pE.recherche();
        int pour = Integer.parseInt(vueP.getMsg("Pourcentage: "));
        ck=mdP.modifEmploye(p,emp,pour);
        if(ck==true){
            vueP.displayMsg("Empoyé modifié");
        }else {
            vueP.displayMsg("Erreur");
        }
    }

    protected void supEmploye(Projet p){
        boolean ck;
        Employe emp = pE.recherche();
        ck=mdP.supEmploye(p,emp);
        if(ck==true){
            vueP.displayMsg("Empoyé supprimé");
        }else {
            vueP.displayMsg("Erreur");
        }
    }

    protected void pourTot(){
        Projet p = recherche();
        if(p!=null){
            vueP.displayMsg("Pourcentage totale: "+mdP.totPour(p));
        }
    }

    protected void affEmpPourDate(Projet p){
        List<Travail> lTrEmpPourDt = mdP.listeEmployesEtPourcentageEtDate(p);
        vueP.affLobj(lTrEmpPourDt);
    }

    protected void affEmpDiscBase(Projet p){
        int niv = Integer.parseInt(vueP.getMsg("niveau"));
        List<Travail> lTrEmpDiscB = mdP.listeEmployesDisciplineBase(p,niv);
        vueP.affLobj(lTrEmpDiscB);
    }

    protected void affAll(){
        vueP.affAll(mdP.readAll());
    }
}
