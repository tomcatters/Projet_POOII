package entreprise_info.presenter;

import entreprise_info.metier.Disciplines;
import entreprise_info.modele.DAODisciplines;
import entreprise_info.vue.VueDisciplinesInterface;

import java.util.Scanner;

public class PesenterDisciplines {

    private DAODisciplines mdD;

    private VueDisciplinesInterface vueD;

    private Scanner sc = new Scanner(System.in);

    public PesenterDisciplines(DAODisciplines mdD, VueDisciplinesInterface vueD) {
        this.mdD = mdD;
        this.vueD = vueD;
    }

    public void Gestion(){
        do {
            System.out.println("1.ajout\n2.recherche\n3.modification\n4.suppression\n5.fin");
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    protected void ajout(){
        Disciplines discNew = vueD.create();
        discNew = mdD.create(discNew);
        if (discNew==null){
            vueD.displayMsg("erreur lors de la création de l'employé - doublon");
            return;
        }

        vueD.displayMsg("Employé crée");
        vueD.display(discNew);
    }

    protected Disciplines recherche(){
        int nRech = vueD.read();
        Disciplines disc = new Disciplines(nRech,null,null);
        disc = mdD.read(disc);
        if (disc == null){
            vueD.displayMsg("Employé introuvable");
            return null;
        }
        vueD.display(disc);
        return disc;
    }

    protected void modification(){
        Disciplines disc = recherche();
        if (disc != null) {
            disc =  vueD.update(disc);
            mdD.update(disc);
        }
    }

    protected void suppression(){
        Disciplines disc = recherche();
        if (disc != null){
            String rep;
            do {
                rep = vueD.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if( mdD.delete(disc))vueD.displayMsg("Discipline supprimé");
                else vueD.displayMsg("Discipline non supprimé");
            }
        }
    }
}
