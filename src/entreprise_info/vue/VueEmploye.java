package entreprise_info.vue;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Competence;

import java.util.ArrayList;
import java.util.List;

public class VueEmploye extends VueCommune implements VueEmployeInterface{

    public Employe create(){
        int matricule = Integer.parseInt(verifierEntree("\\d*","n° matricule: "));
        String nom = verifierEntree("[A-Z][a-z]*","nom: ");
        String prenom = verifierEntree("[A-Z][a-z]*","prenom: ");
        String tel = verifierEntree("\\d{10}","n° telephone: ");
        String mail = verifierEntree(".*","adresse mail: ");
        Employe empNew = new Employe(matricule,nom,prenom,tel,mail,new ArrayList<>());
        return empNew;
    }

    public Employe update(Employe emp) {
        do {
            int ch = Integer.parseInt(verifierEntree("\\d","1.changement de téléphone\n2.fin"));
            switch (ch){
                case 1:
                    String ntel = verifierEntree("\\d{10}","nouveau numéro de téléphone :");
                    emp.setTel(ntel);
                    return emp;
                case 2:
                    return emp;
                default:
                    displayMsg("choix invalide");
            }
        }while (true);
    }

    public void display(Employe emp){
        displayMsg(emp.toString());
        if (!emp.getlComp().isEmpty()) {
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
        int n = Integer.parseInt(verifierEntree("\\d*","numéro d'employé : "));
        return n;
    }

    public int choixNiveau(){
        int c;
        c = Integer.parseInt(verifierEntree("1|2|3","choix de niveau(1,2 ou 3): "));
        return c;
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
