package entreprise_info.presenter;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.modele.DAOEmploye;
import entreprise_info.vue.VueEmployeInterface;

import java.util.Scanner;


public class PresenterEmploye {

    private DAOEmploye mdE;
    private VueEmployeInterface vueE;

    protected PresenterDisciplines pD;

    private Scanner sc = new Scanner(System.in);

    public PresenterEmploye(DAOEmploye mdE, VueEmployeInterface vueE) {
        this.mdE = mdE;
        this.vueE = vueE;
    }

    public void setpD(PresenterDisciplines pD) {
        this.pD = pD;
    }

    public void Gestion(){
        do {
            int ch = vueE.menu(new String[]{"ajout", "recherche","modification","suppression","gestion des disciplines","afficher tous","fin"});
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
                    affAll();
                    break;
                case 7:
                    return;
                    //System.exit(0);
                    //break;
                default:
                    vueE.displayMsg("choix invalide recommencez ");
            }
        } while (true);
    }

    private void gestDisc(){

        Employe emp = recherche();

        if (emp != null) {
            do {
                int ch = vueE.menu(new String[]{"ajout discipline", "modification discipline", "suppression discipline","affichage des disciplines","fin"});
                switch (ch) {
                    case 1:
                        addDiscipline(emp);
                        break;
                    case 2:
                        modifDiscipline(emp);
                        break;
                    case 3:
                        suppDiscipline(emp);
                        break;
                    case 4:
                        affDisc(emp);
                        break;
                    case 5:
                        return;
                    default:
                        vueE.displayMsg("choix invalide recommencez ");
                }
                /*if(check==false) {
                    vueE.displayMsg("une erreur s'est produite");
                    continue;
                }
                if(emp.listeDisciplinesEtNiveau().isEmpty()) vueE.displayMsg("aucun élément à afficher");
                else vueE.affLobj(emp.listeDisciplinesEtNiveau());*/
            } while (true) ;
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
        Employe emp = new Employe(nRech,0,null,null,null,null,null);
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

    protected boolean addDiscipline(Employe emp){
        boolean check;
        Disciplines d = pD.recherche();
        int n=vueE.choixNiveau();
        check=mdE.addDiscipline(emp,d,n);
        if (check==true){
            return true;
        }
        return false;
    }

    protected void modifDiscipline(Employe emp){
        Disciplines d = pD.choixDiscipline(emp);
        int n=vueE.choixNiveau();
        mdE.modifDiscipline(emp,d,n);
    }

    protected void suppDiscipline(Employe emp){
        Disciplines d = pD.choixDiscipline(emp);
        mdE.suppDiscipline(emp,d);
    }

    private void affDisc(Employe emp){
        vueE.display(emp);
    }

    protected void affAll(){
        vueE.affAll(mdE.readAll());
    }
}
