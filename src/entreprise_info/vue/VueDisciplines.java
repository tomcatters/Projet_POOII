package entreprise_info.vue;

import entreprise_info.metier.Disciplines;

import java.util.List;

public class VueDisciplines extends VueCommune implements VueDisciplinesInterface{
    public Disciplines create(){

    }

    public Disciplines update(Disciplines d){
        return d;
    }

    public void display(Disciplines d){

    }

    public Integer read(){

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
