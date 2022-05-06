package entreprise_info.vue;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Competence;

import java.util.List;

public class VueEmploye extends VueCommune implements VueEmployeInterface{

    public Employe create(){
        int matricule = Integer.parseInt(getMsg("n° matricule: "));
        String nom = getMsg("nom: ");
        String prenom = getMsg("prenom: ");
        String tel = getMsg("n° telephone: ");
        String mail = getMsg("adresse mail: ");
        Employe empNew = new Employe(matricule,nom,prenom,tel,mail);
        return empNew;
    }

    public Employe update(Employe emp) {
        do {
            int ch = Integer.parseInt(getMsg("1.changement de téléphone\n2.fin"));
            switch (ch){
                case 1:
                    String ntel = getMsg("nouveau numéro de téléphone :");
                    emp.setTel(ntel); //tester doublon!!
                    break;
                case 2:
                    return emp;
                default:
                    displayMsg("choix invalide");
            }
        }while (true);
    }

    public void display(Employe emp){
        displayMsg(emp.toString());
        if (!emp.listeDisciplinesEtNiveau().isEmpty()) {
            String rep;
            do {
                rep = getMsg("Afficher ses disciplines (o/n) ");
            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                for (Competence cpt : emp.listeDisciplinesEtNiveau()) {
                    displayMsg(cpt.toString());
                }
            }
        }
    }

    public Integer read(){
        String ns = getMsg("numéro d'employé : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    public int choixNiveau(){
        int c;
        do {
            c = Integer.parseInt(getMsg("choix de niveau(1,2 ou 3): "));//
            return c;
        }while (c<1 && c>3);
    }

    @Override
    public void affAll(List<Employe> lEmp) {
        int i =0;
        for(Employe emp:lEmp){
            displayMsg((++i)+"."+emp.toString());
        }
    }

    @Override
    public void affLobj(List lobj) {
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }
}
