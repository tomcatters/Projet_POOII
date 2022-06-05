package entreprise_info.vue;

import javax.swing.*;
import java.util.Scanner;

public class VueCommuneGraph implements VueCommuneInterface{

    private Scanner sc = new Scanner(System.in);

    public int menu(String[] options){
        int ch;
        JTextField choiceField = new JTextField(5);
        StringBuilder sb = new StringBuilder(50);
        int i =0;
        for(String option : options) sb.append((++i)+"."+option+"\n");
        sb.append("choix:");
        do {
            ch = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
            if (ch >=1 && ch <= options.length);
            return ch;
        }while (true);
    }

    @Override
    public void displayMsg(String msg) {
        JOptionPane.showConfirmDialog(null,msg,"information", JOptionPane.DEFAULT_OPTION);
    }

    @Override
    public String getMsg(String invite) {
        return JOptionPane.showInputDialog(null,invite,"question",JOptionPane.DEFAULT_OPTION);
    }


    public boolean verifierRegex(String entree,String regex){
        return entree.matches(regex);
    }

    public String verifierEntree(String regex,String phrase){
        String choix;
        do {
            JTextField chx = new JTextField();

            Object message[] = {
                    phrase, chx
            };

            int option = JOptionPane.showConfirmDialog(null, message, "question", JOptionPane.DEFAULT_OPTION);
            choix = chx.getText().toString();
        }while (!verifierRegex(choix,regex));
        return choix;
    }
}
