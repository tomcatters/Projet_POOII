package entreprise_info;

import entreprise_info.modele.*;
import entreprise_info.presenter.*;
import entreprise_info.vue.*;

import java.util.Scanner;

public class GestionEntreprise {

    private PresenterProjet pP;

    private PresenterEmploye pE;

    private PresenterDisciplines pD;

    public static void main(String[] args) {
        String  modeVue = args[0];
        String modeData= args[1];

        GestionEntreprise gE = new GestionEntreprise();
        gE.gestion(modeVue,modeData);
    }

    public void gestion(String modeVue,String modeData){
        VueDisciplinesInterface vueD;
        VueProjetInterface vueP;
        VueEmployeInterface vueE;
        VueCommuneInterface vueC;

        DAODisciplines mdD;
        DAOEmploye mdE;
        DAOProjet mdP;

        if(modeVue.equals("console")){
            vueC = new VueCommune();
            vueE = new VueEmploye();
            vueD = new VueDisciplines();
            vueP = new VueProjet();
        }else {
            vueC = new VueCommuneGraph();
            vueE = new VueEmployeGraph();
            vueD = new VueDisciplinesGraph();
            vueP = new VueProjetGraph();
        }

        if (modeData.equals("db")){
            mdD = new ModelDisciplinesDB();
            mdE = new ModelEmployeDB();
            mdP = new ModelProjetDB();
        }else {
            mdD = new ModeleDisciplines();
            mdE = new ModeleEmploye();
            mdP = new ModelProjet();
        }

        pP = new PresenterProjet(mdP,vueP);
        pE = new PresenterEmploye(mdE,vueE);
        pD = new PresenterDisciplines(mdD,vueD);

        pP.setpE(pE);
        pP.setpD(pD);
        pE.setpD(pD);

        do {
            int ch = vueC.menu(new String[]{"Employé","Disciplines","Projet","Fin"});
            System.out.println(ch);
            switch (ch){
                case 1:
                    pE.Gestion();
                    break;
                case 2:
                    pD.Gestion();
                    break;
                case 3:
                    pP.gestion();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("choix invalide");
            }
        }while (true);
    }

}
