package entreprise_info.vue;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Employe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VueEmployeGraph extends VueCommuneGraph implements VueEmployeInterface{
    @Override
    public void affLobj(List lobj) {
        int i =0;
        for(Object o:lobj){
            displayMsg((++i)+"."+o.toString());
        }
    }

    @Override
    public int choixNiveau() {
        int c;
        c = Integer.parseInt(verifierEntree("1|2|3","choix de niveau(1,2 ou 3): "));
        return c;
    }

    @Override
    public Employe create() {
        JTextField tfmatricule = new JTextField();
        JTextField tfnom = new JTextField();
        JTextField tfprenom = new JTextField();
        JTextField tfntel = new JTextField();
        JTextField tfmail = new JTextField();
        Object[] message = {
                "n° matricule: ", tfmatricule,
                "nom: ", tfnom,
                "prenom: ", tfprenom,
                "n° telephone: ", tfntel,
                "adresse mail: ", tfmail
        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouveau employe", JOptionPane.DEFAULT_OPTION);
        int matricule = Integer.parseInt(tfmatricule.getText().toString());
        String nom = tfnom.getText().toString();
        String prenom = tfprenom.getText().toString();
        String tel = tfntel.getText().toString();
        String mail = tfmail.getText().toString();
        Employe empNew = new Employe(matricule,nom,prenom,tel,mail,new ArrayList<>());
        return empNew;
    }

    @Override
    public void display(Employe obj) {
        displayMsg(obj.toString());
        if (!obj.getlComp().isEmpty()){
            String rep;
            do {
                rep = getMsg("Afficher les disciplines (o/n)");
            }while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));
            if (rep.equalsIgnoreCase("o")) {
                int i=0;
                StringBuffer sb= new StringBuffer(200);
                for (Competence cpt : obj.listeDisciplinesEtNiveau())  sb.append((++i)+"."+cpt+"\n");
                    displayMsg(sb.toString());
            }
        }
    }

    @Override
    public Employe update(Employe obj) {
        do {
            int ch = Integer.parseInt(getMsg("1.changement de téléphone\n2.fin"));
            switch (ch){
                case 1:
                    String ntel = getMsg("nouveau numéro de téléphone :");
                    obj.setTel(ntel);
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
        String ns = getMsg("numéro d'employé : ");
        int n = Integer.parseInt(ns);
        return n;
    }

    @Override
    public void affAll(List<Employe> lobj) {
        int i =0;
        StringBuffer sb= new StringBuffer(200);

        for(Employe emp:lobj) sb.append((++i)+"."+emp+"\n");
            displayMsg(sb.toString());
    }
}
