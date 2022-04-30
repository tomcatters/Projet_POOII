package entreprise_info.vue;

import entreprise_info.metier.Projet;

import java.util.List;

public class VueProjet extends VueCommune implements VueProjetInterface{
    public Projet create(){

    }

    public Projet update(Projet p){
        return p;
    }

    public void display(Projet p){

    }

    public Integer read(){
        String ns = getMsg("numéro de projet : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    public void affLobj(List lobj){

    }

    public void affAll(List<Projet> lEmp){

    }
}
