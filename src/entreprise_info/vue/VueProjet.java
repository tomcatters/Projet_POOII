package entreprise_info.vue;

import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VueProjet extends VueCommune implements VueProjetInterface{

    private Scanner sc = new Scanner(System.in);
    public Projet create(){
        String titre = verifierEntree("[A-Z][a-z]*","titre: ");
        displayMsg("Date de Debut: ");
        Date dateDebut = initDate();
        displayMsg("Date de fin: ");
        Date dateFin = initDate();
        int cout = Integer.parseInt(getMsg("Cout: "));
        Projet p = new Projet(titre,dateDebut,dateFin,cout,null);
        return p;
    }

    public Projet update(Projet p){
        do {
            int ch = Integer.parseInt(getMsg("1.changement de date\n2.changement du cout\n3.fin"));
            switch (ch){
                case 1:
                    displayMsg("modifier la date de fin: ");
                    Date dateFin = initDate();
                    p.setDateFin(dateFin);
                    break;
                case 2:
                    int cout = Integer.parseInt(verifierEntree("\\d*","Modifier le cout: "));
                    p.setCout(cout);
                    break;
                case 3:
                    return p;
                default:
                    displayMsg("choix invalide");
            }
        }while (true);
    }

    public void display(Projet p){
        displayMsg(p.toString());
        if (!p.listeEmployesEtPourcentageEtDate().isEmpty()){
            String rep;
            do{
                rep = getMsg("Afficher les travaillers (o/n)");
            }while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")){
                for (Travail t : p.listeEmployesEtPourcentageEtDate()){
                    displayMsg(t.toString());
                }
            }
        }
    }

    public Integer read(){
        int n = Integer.parseInt(verifierEntree("\\d*","numéro de projet : "));
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
