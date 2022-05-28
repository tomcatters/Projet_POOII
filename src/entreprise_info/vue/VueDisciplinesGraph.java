package entreprise_info.vue;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Projet;

import javax.swing.*;
import java.util.List;

public class VueDisciplinesGraph extends VueCommuneGraph implements VueDisciplinesInterface{
    @Override
    public void affLobj(List lobj) {
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }

    @Override
    public Disciplines create() {
        JTextField tfnom = new JTextField();
        JTextField tfdesc = new JTextField();
        Object[] message = {
                "nom ", tfnom,
                "description ", tfdesc
        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouvelle disciplines", JOptionPane.DEFAULT_OPTION);
        String nom = tfnom.getText().toString();
        String description = tfdesc.getText().toString();
        Disciplines d = new Disciplines(nom,description);
        return d;
    }

    @Override
    public void display(Disciplines obj) {
        displayMsg(obj.toString());
        if (!obj.getlProjet().isEmpty()){
            String rep;
            do {
                rep = getMsg("Afficher les projet (o/n)");
            }while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")){
                int i=0;
                StringBuffer sb= new StringBuffer(200);
                for (Projet p : obj.getlProjet()) sb.append((++i)+"."+p+"\n");
                displayMsg(sb.toString()) ;
            }
        }
    }

    @Override
    public Disciplines update(Disciplines obj) {
        do {
            int ch = Integer.parseInt(getMsg("1.changer la description\n2.frin"));

            switch (ch){
                case 1:
                    String desc = getMsg("nouvelle description: ");
                    obj.setDescription(desc);
                    return obj;
                case 2:
                    return obj;
                default:
                    displayMsg("choix invalide");
            }

        }while (true);
    }

    @Override
    public Integer read() {
        String ns = getMsg("numéro de discipline : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    @Override
    public void affAll(List<Disciplines> lobj) {
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }
}
