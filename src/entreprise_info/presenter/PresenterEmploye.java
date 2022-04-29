package entreprise_info.presenter;

import entreprise_info.metier.Employe;
import entreprise_info.modele.DAOEmploye;
import entreprise_info.vue.VueEmployeInterface;
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
            System.out.println("1.ajout\n2.recherche\n3.modification\n4.suppression\n5.gestion des sisciplines\n6.fin");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    private void gestDisc(){
        System.out.println("1.ajoout discipline\n2.modification discipline\n3.suppression discipline\n4.fin");
        System.out.println("choix : ");
        int ch = sc.nextInt();
        sc.skip("\n");
        switch (ch){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("choix invalide recommencez ");
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

    protected void affAll(){
        vueE.affAll(mdE.readAll());
    }
}
