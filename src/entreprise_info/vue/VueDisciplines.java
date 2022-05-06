package entreprise_info.vue;

import entreprise_info.metier.Disciplines;

import java.util.List;

public class VueDisciplines extends VueCommune implements VueDisciplinesInterface{
    public Disciplines create(){
        String nom = getMsg("nom: ");
        String description = getMsg("description: ");
        Disciplines d = new Disciplines(nom,description);
        return d;
    }

    public Disciplines update(Disciplines d){
        return d;
    }

    public void display(Disciplines d){

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
