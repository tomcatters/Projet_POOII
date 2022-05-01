package entreprise_info.vue;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Projet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VueProjet extends VueCommune implements VueProjetInterface{

    private Scanner sc = new Scanner(System.in);
    public Projet create(){
        String titre = getMsg("titre: ");
        displayMsg("Date de Debut");
        Date dateDebut = initDate();
        displayMsg("Date de fin: ");
        Date dateFin = initDate();
        int cout = Integer.parseInt(getMsg("Cout: "));
        Projet p = new Projet(titre,dateDebut,dateFin,cout,null);
        return p;
    }

    public Projet update(Projet p){
        return p;
    }

    public void display(Projet p){
        /*displayMsg(emp.toString());
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
        }*/
    }

    public Integer read(){
        String ns = getMsg("numéro de projet : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    public Date initDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String str = sc.nextLine();
        Date d = null;
        try {
            d = sdf.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return d;
        //System.out.println(sdf.format(d));
    }

    public void affLobj(List lobj){
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }

    public void affAll(List<Projet> lProjet){
        int i =0;
        for(Projet p:lProjet){
            displayMsg((++i)+"."+p.toString());
        }
    }
}
