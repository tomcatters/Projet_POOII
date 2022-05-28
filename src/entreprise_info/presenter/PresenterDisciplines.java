package entreprise_info.presenter;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.modele.DAODisciplines;
import entreprise_info.vue.VueDisciplinesInterface;

import java.util.List;
import java.util.Scanner;

public class PresenterDisciplines {

    private DAODisciplines mdD;

    private VueDisciplinesInterface vueD;

    private Scanner sc = new Scanner(System.in);

    public PresenterDisciplines(DAODisciplines mdD, VueDisciplinesInterface vueD) {
        this.mdD = mdD;
        this.vueD = vueD;
    }

    public void Gestion(){
        do {
            int ch = vueD.menu(new String[]{"ajout","recherche","modification","suppression","fin"});
            ///sc.skip("\n");
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
                    return;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    protected void ajout(){
        Disciplines discNew = vueD.create();
        discNew = mdD.create(discNew);
        if (discNew==null){
            vueD.displayMsg("erreur lors de la création de la discipline - doublon");
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
            vueD.displayMsg("Disciplines introuvable");
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

    protected Disciplines choixDiscipline(Employe emp){
        vueD.affLobj(mdD.readAll());
        int s = mdD.readAll().size();
        do {
            int choix = Integer.parseInt(vueD.getMsg("choix id disciplines: "));
            Disciplines d = new Disciplines(choix,null,null);
            d = mdD.read(d);
            if (d!=null){
                return d;
            }else {
                vueD.displayMsg("choix incorrect");
            }
        }while (true);
    }

    protected void ajoutProjet(Projet p){

    }
}
