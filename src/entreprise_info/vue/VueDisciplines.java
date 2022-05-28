package entreprise_info.vue;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Projet;

import java.util.List;

public class VueDisciplines extends VueCommune implements VueDisciplinesInterface{
    public Disciplines create(){
        String nom = getMsg("nom: ");
        String description = getMsg("description: ");
        Disciplines d = new Disciplines(nom,description);
        return d;
    }

    public Disciplines update(Disciplines d){
        do {
            int ch = Integer.parseInt(getMsg("1.changer la description\n2.frin"));
            switch (ch){
                case 1:
                    String desc = getMsg("nouvelle description: ");
                    d.setDescription(desc);
                    return d;
                case 2:
                    return d;
                default:
                    displayMsg("choix invalide");
            }
        }while (true);
    }

    public void display(Disciplines d){
        displayMsg(d.toString());
        if (!d.getlProjet().isEmpty()){
            String rep;
            do {
                rep = getMsg("Afficher les projet (o/n)");
            }while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")){
                for (Projet p : d.getlProjet()){
                    displayMsg(p.toString());
                }
            }
        }
    }

    public Integer read(){
        String ns = getMsg("numéro de discipline : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    public void affLobj(List lobj){
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }

    public void affAll(List<Disciplines> lDisciplines){
        int i =0;
        for(Disciplines d:lDisciplines){
            displayMsg((++i)+"."+d.toString());
        }
    }
}
