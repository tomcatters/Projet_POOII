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

    public Employe update(Employe emp){
        return emp;
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
