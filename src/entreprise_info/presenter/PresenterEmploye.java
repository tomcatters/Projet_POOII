package entreprise_info.presenter;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.modele.DAOEmploye;
import entreprise_info.vue.VueEmployeInterface;

import java.util.List;
import java.util.Scanner;


public class PresenterEmploye {

    private DAOEmploye mdE;
    private VueEmployeInterface vueE;

    private PesenterDisciplines pD;

    private Scanner sc = new Scanner(System.in);

    public PresenterEmploye(DAOEmploye mdE, VueEmployeInterface vueE) {
        this.mdE = mdE;
        this.vueE = vueE;
    }

    public void setpD(PesenterDisciplines pD) {
        this.pD = pD;
    }

    public void Gestion(){
        do {
            int ch = vueE.menu(new String[]{"1.ajout", "2.recherche","3.modification","n4.suppression","5.gestion des disciplines","6.affichage des disciplines","7.fin"});
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
                    gestDisc();
                    break;
                case 6:
                    affDisc();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    vueE.displayMsg("choix invalide recommencez ");
            }
        } while (true);
    }

    private void gestDisc(){

        Employe emp = recherche();

        int n;

        vueE.affLobj(emp.listeDisciplinesEtNiveau());
        int choix = Integer.parseInt(vueE.getMsg("choix : "));

        Disciplines d = emp.listeDisciplinesEtNiveau().get(choix).getId_Discipline();

        if (emp != null) {
            do {
                boolean check=false;
                int ch = vueE.menu(new String[]{"1.ajout discipline", "2.modification discipline", "3.suppression discipline","4.fin"});
                switch (ch) {
                    case 1:
                        addDiscipline(emp,d);
                        break;
                    case 2:
                        modifDiscipline(emp,d);
                        break;
                    case 3:
                        suppDiscipline(emp,d);
                        break;
                    case 4:
                        return;
                    default:
                        vueE.displayMsg("choix invalide recommencez ");
                }
                if(check==false) {
                    vueE.displayMsg("une erreur s'est produite");
                    continue;
                }
                if(emp.listeDisciplinesEtNiveau().isEmpty()) vueE.displayMsg("aucun élément à afficher");
                else vueE.affLobj(emp.listeDisciplinesEtNiveau());
            }

            while (true) ;
        }
    }

    protected void ajout(){
        Employe empNew = vueE.create();
        empNew = mdE.create(empNew);
        if (empNew==null){
            vueE.displayMsg("erreur lors de la création de l'employé - doublon");
            return;
        }

        vueE.displayMsg("Employé crée");
        vueE.display(empNew);

    }

    protected Employe recherche(){
        int nRech = vueE.read();
        Employe emp = new Employe(nRech,0,null,null,null,null);
        emp = mdE.read(emp);
        if (emp == null){
            vueE.displayMsg("Employé introuvable");
            return null;
        }
        vueE.display(emp);
        return emp;
    }

    protected void modification(){
        Employe empRech = recherche();
        if (empRech != null) {
            empRech =  vueE.update(empRech);
            mdE.update(empRech);
        }
    }

    protected void suppression(){
        Employe empRech = recherche();
        if (empRech != null){
            String rep;
            do {
                rep = vueE.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if( mdE.delete(empRech))vueE.displayMsg("Employé supprimé");
                else vueE.displayMsg("Employé non supprimé");
            }
        }
    }

    protected void addDiscipline(Employe emp,Disciplines d){
        int n=choixNiveau();
        mdE.addDiscipline(emp,d,n);
    }

    protected void modifDiscipline(Employe emp,Disciplines d){
        int n=choixNiveau();
        mdE.modifDiscipline(emp,d,n);
    }

    protected void suppDiscipline(Employe emp, Disciplines d){
        mdE.suppDiscipline(emp,d);
    }

    private int choixNiveau(){
        int c;
        do {
            c = Integer.parseInt(vueE.getMsg("choix de niveau: "));
            return c;
        }while (c>=1 && c<=3);
    }

    private void affDisc(){
        Employe emp = recherche();
        vueE.display(emp);
    }

    protected void affAll(){
        vueE.affAll(mdE.readAll());
    }
}
