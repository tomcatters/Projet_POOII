package entreprise_info.vue;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VueProjetGraph extends VueCommuneGraph implements VueProjetInterface{

    private Scanner sc = new Scanner(System.in);

    @Override
    public Projet create() {
        return null;
    }

    @Override
    public void display(Projet obj) {
        displayMsg(obj.toString());
        if (!obj.listeEmployesEtPourcentageEtDate().isEmpty()){
            String rep;
            do{
                rep = getMsg("Afficher les travaillers (o/n)");
            }while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));
            if (rep.equalsIgnoreCase("o")){
                int i=0;
                StringBuffer sb= new StringBuffer(200);
                for (Travail t : obj.listeEmployesEtPourcentageEtDate()) sb.append((++i)+"."+t+"\n");
                    displayMsg(sb.toString());
            }
        }
    }

    @Override
    public Projet update(Projet obj) {
        do {
            int ch = Integer.parseInt(getMsg("1.changement de date\n2.changement du cout\n3.fin"));
            switch (ch){
                case 1:
                    displayMsg("modifier la date de fin: ");
                    Date dateFin = initDate();
                    obj.setDateFin(dateFin);
                    break;
                case 2:
                    int cout = Integer.parseInt(getMsg("Modifier le cout: "));
                    obj.setCout(cout);
                    break;
                case 3:
                    return obj;
                default:
                    displayMsg("choix invalide");
            }
        }while (true);
    }

    @Override
    public Integer read() {
        String ns = getMsg("numéro de projet : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    @Override
    public void affAll(List<Projet> lobj) {
        int i =0;
        for(Projet p:lobj){
            displayMsg((++i)+"."+p.toString());
        }
    }

    @Override
    public void affLobj(List lobj) {
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }

    @Override
    public Date initDate() {
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
}
