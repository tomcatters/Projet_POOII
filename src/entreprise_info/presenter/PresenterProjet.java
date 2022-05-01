package entreprise_info.presenter;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.modele.DAOProjet;
import entreprise_info.vue.VueProjetInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PresenterProjet {

    private DAOProjet mdP;

    private VueProjetInterface vueP;

    private PresenterEmploye pE;

    private PesenterDisciplines pD;

    private Scanner sc = new Scanner(System.in);

    public PresenterProjet(DAOProjet mdP, VueProjetInterface vueP) {
        this.mdP = mdP;
        this.vueP = vueP;
    }

    public void setpE(PresenterEmploye pE) {
        this.pE = pE;
    }

    public void setpD(PesenterDisciplines pD) {
        this.pD = pD;
    }

    public PesenterDisciplines getpD() {
        return pD;
    }

    public void gestion() {
        do {
            int ch = vueP.menu(new String[]{"1.ajout","2.recherche","3.modification","4.suppression","5.pourcentage total","7.gestion des equipes","6.fin"});
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }

    private void gestEqp(){
        Projet p = recherche();

        if (p!=null){
            int ch = vueP.menu(new String[]{"1.ajout d'un employé", "2.modification d'un employé", "3.suppression d'un employé","4.fin"});
            do {
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
        Employe emp = pE.recherche();
        int pour = Integer.parseInt(vueP.getMsg("Pourcentage: "));
        Date d = vueP.initDate();
        mdP.addEmploye(p,emp,pour,d);
    }

    protected void modifEmploye(Projet p){
        Employe emp = pE.recherche();
        int pour = Integer.parseInt(vueP.getMsg("Pourcentage: "));
        mdP.modifEmploye(p,emp,pour);
    }

    protected void supEmploye(Projet p){
        Employe emp = pE.recherche();
        mdP.supEmploye(p,emp);
    }

    private void pourTot(){
        Projet p = recherche();
        if(p!=null){
            vueP.displayMsg("Pourcentage totale: "+mdP.totPour(p));
        }
    }

    protected void affAll(){
        vueP.affAll(mdP.readAll());
    }
}
