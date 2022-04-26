package entreprise_info;

import myconnections.DBConnection;

import java.sql.*;
import java.util.Scanner;

import entreprise_info.metier.*;

public class GestionEntrepriseInfo {

    private Connection dbConnect;
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        GestionEntrepriseInfo g = new GestionEntrepriseInfo();
        g.Gestion();
    }

    public void Gestion(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("connexion établie");

        do {
            System.out.println("1.ajout\n2.recherche\n3.modification\n4.suppression\n5.fin");
            System.out.println("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    public void ajout(){
        System.out.print("matricule :");
        String matricule = sc.nextLine();
        System.out.print("nom :");
        String nom = sc.nextLine();
        System.out.print("prénom :");
        String prenom = sc.nextLine();
        System.out.print("tel :");
        String tel = sc.nextLine();
        System.out.print("mail :");
        String mail = sc.nextLine();
        String query = "insert into API_EMPLOYE(matricule,nom,prenom,tel,mail) values(?,?,?,?,?)";
        String query2 = "SELECT id_employe FROM API_EMPLOYE WHERE nom = ? AND prenom = ? AND tel = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm.setString(1,matricule);
            pstm.setString(2,nom);
            pstm.setString(3,prenom);
            pstm.setString(4,tel);
            pstm.setString(5,mail);
            int n = pstm.executeUpdate();
            System.out.println(n+" ligne insérée");
            if (n==1){
                pstm2.setString(1,nom);
                pstm2.setString(2,prenom);
                pstm2.setString(3,tel);
                ResultSet rs = pstm2.executeQuery();
                if(rs.next()){
                    int id_employe= rs.getInt(1);
                    System.out.println("id employe = "+id_employe);
                }
                else System.out.println("record introuvable");
                rs.close();
            }
        }catch (SQLException e) {
            System.out.println("erreur sql :"+e);
        }
    }

    public void recherche(){
        System.out.println("id du client recherché ");
        int idrech = sc.nextInt();
        String query = "SELECT * FROM API_EMPLOYE WHERE id_employe = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                String matricule = rs.getString(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                String mail = rs.getString(6);
                System.out.printf("%d %s %s %s %s %s\n", idrech,matricule,nom,prenom,tel,mail);
            }else {
                System.out.println("record introuvable");
            }
            rs.close();
        }catch (SQLException e){
            System.out.println("erreur sql :"+e);
        }
    }

    public void modification(){
        System.out.println("id du client recherché ");
        int idrech = sc.nextInt();
        sc.skip("\n");
        System.out.println("nouveau téléphone ");
        String tel = sc.nextLine();
        String query = "UPDATE API_EMPLOYE SET TEL=? WHERE ID_EMPLOYE= ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1,tel);
            pstm.setInt(2,idrech);
            int n = pstm.executeUpdate();
            if (n!=0){
                System.out.println(n+ "ligne mise à jour");
            }else{
                System.out.println("record introuvable");
            }
        }catch (SQLException e){
            System.out.println("erreur sql :"+e);
        }
    }

    public void suppression(){
        System.out.println("id du client recherché ");
        int idrech = sc.nextInt();
        String query = "DELETE FROM API_EMPLOYE WHERE ID_EMPLOYE = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1,idrech);
            int n = pstm.executeUpdate();
            if (n!=0){
                System.out.println(n+ "ligne supprimée");
            }else {
                System.out.println("record introuvable");
            }
        }catch (SQLException e){
            System.out.println("erreur sql :"+e);
        }
    }
}
