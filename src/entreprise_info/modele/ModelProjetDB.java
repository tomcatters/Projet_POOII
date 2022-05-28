package entreprise_info.modele;

import entreprise_info.metier.*;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ModelProjetDB implements DAOProjet{

    protected Connection dbConnect;

    public ModelProjetDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Projet create(Projet objNew) {
        String req1 = "insert into api_projet(titre,datedebut,datefin,cout,id_disciplines) values (?,?,?,?,?)";
        String req2 = "select id_projet from api_projet where titre = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req1);){
            pstm.setString(1,objNew.getTitre());
            long mlsDbtDate = objNew.getDateDebut().getTime();
            java.sql.Date dateDbtSql = new java.sql.Date(mlsDbtDate);
            pstm.setDate(2,dateDbtSql);
            long mlsFinDate = objNew.getDateFin().getTime();
            java.sql.Date dateFinSql = new java.sql.Date(mlsFinDate);
            pstm.setDate(3,dateFinSql);
            pstm.setInt(4,objNew.getCout());
            pstm.setInt(5,objNew.getId_DisciplineBase().getId_Discipline());

            int n = pstm.executeUpdate();
            if (n==0){
                return null;
            }
            try(PreparedStatement pstm2 = dbConnect.prepareStatement(req2)){
                pstm2.setString(1,objNew.getTitre());

                ResultSet rs = pstm2.executeQuery();
                if(rs.next()){
                    int idPjt = rs.getInt(1);
                    objNew.setId_Projet(idPjt);
                    objNew.setlTrav(new ArrayList<>());
                    //return objNew;
                }else {
                    throw new Exception("aucun projet trouvé");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return objNew;
    }

    @Override
    public Projet update(Projet objRech) {
        String req = "update api_projet set datefin = ?, cout = ? where id_projet = ?";
        try(PreparedStatement pstm = dbConnect.prepareCall(req)){
            long mlsFinDate = objRech.getDateFin().getTime();
            java.sql.Date dateFinSql = new java.sql.Date(mlsFinDate);
            pstm.setDate(1,dateFinSql);
            pstm.setInt(2,objRech.getCout());
            pstm.setInt(3,objRech.getId_Projet());

            int n = pstm.executeUpdate();
            if (n==0){
                throw new Exception("aucun projet mis à jour");
            }else {
                return read(objRech);
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean delete(Projet objRech) {
        String req = "delete from api_projet where id_projet = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(1,objRech.getId_Projet());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Projet read(Projet objRech) {
        String req = "select * from api_projet join API_DISCIPLINES AD on AD.ID_DISCIPLINES = API_PROJET.ID_DISCIPLINES where id_projet = ?";
        //String req2 = "select * from api_disciplines";
        Projet p = null;
        Disciplines d = null;
        try(PreparedStatement pstm = dbConnect.prepareCall(req)){
            pstm.setInt(1,objRech.getId_Projet());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                int idProjet = rs.getInt("ID_PROJET");
                String titre = rs.getString("TITRE");
                java.sql.Date sqlDateDebut = rs.getDate("DATEDEBUT");
                java.util.Date dateDebut = new java.util.Date(sqlDateDebut.getTime());
                java.sql.Date sqlDateFin = rs.getDate("DATEFIN");
                java.util.Date dateFin = new java.util.Date(sqlDateFin.getTime());
                int cout = rs.getInt("COUT");
                int id_disc = rs.getInt("ID_DISCIPLINES");
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                d = new Disciplines(id_disc,nom,desc);
                p = new Projet(idProjet,titre,dateDebut,dateFin,cout,d);
            }
            return p;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Projet> readAll() {
        String req = "select * from api_projet join API_DISCIPLINES AD on AD.ID_DISCIPLINES = API_PROJET.ID_DISCIPLINES";
        List<Projet> lProj = null;
        Projet p = null;
        Disciplines d = null;
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)){
            ResultSet rs = pstm.executeQuery();
            do {
                int idProjet = rs.getInt("ID_PROJET");
                String titre = rs.getString("TITRE");
                java.sql.Date sqlDateDebut = rs.getDate("DATEDEBUT");
                java.util.Date dateDebut = new java.util.Date(sqlDateDebut.getTime());
                java.sql.Date sqlDateFin = rs.getDate("DATEFIN");
                java.util.Date dateFin = new java.util.Date(sqlDateFin.getTime());
                int cout = rs.getInt("COUT");
                int id_disc = rs.getInt("AD.ID_DISCIPLINES");
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                d = new Disciplines(id_disc,nom,desc);
                p = new Projet(idProjet,titre,dateDebut,dateFin,cout,d);
                lProj.add(p);
            }while (rs.next());
            if(lProj.isEmpty()){
                return null;
            }else return lProj;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Travail> listeEmployesDisciplineBase(Projet pjt, int niv) {
        String req = "select * from PROJ_EMP_DISCBASE_VIEW where id_projet = ? and DDB = ? and NIVEAU = ?";
        List<Travail> lTrav = new ArrayList<>();
        Travail t = null;
        Employe emp = null;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(1,pjt.getId_Projet());
            pstm.setInt(2,pjt.getId_DisciplineBase().getId_Discipline());
            pstm.setInt(3,niv);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                int id_emp = rs.getInt("IDEMPLOYE");
                int mat = rs.getInt("MATRICULE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String  tel = rs.getString("TELNUM");
                String mail = rs.getString("MAIL");

                int pour = rs.getInt("POURCENTAGE");
                java.sql.Date sqlDate = rs.getDate("DATE_ENGAGEMENT");
                java.util.Date dateEng = new java.util.Date(sqlDate.getTime());
                List<Competence> lComp = new ArrayList<>();
                emp = new Employe(id_emp,mat,nom,prenom,tel,mail,lComp);
                t = new Travail(emp,pour,dateEng);
                lTrav.add(t);
            }
        }catch (Exception e){
            return null;
        }
        return lTrav;
    }

    @Override
    public List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt) {
        String req="select * from PROJ_EMP_POUR_DENG_DISC_VIEW where id_projet = ?";
        List<Travail> lTrav = new ArrayList<>();
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1,pjt.getId_Projet());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_EMPLOYE");
                int mat = rs.getInt("MATRICULE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String  tel = rs.getString("TEL");
                String mail = rs.getString("MAIL");
                int pour = rs.getInt("POURCENTAGE");
                java.sql.Date sqlDate = rs.getDate("DATE_ENGAGEMENT");
                java.util.Date dateEng = new java.util.Date(sqlDate.getTime());
                List<Competence> lComp = new ArrayList<>();
                Employe emp = new Employe(id,mat,nom,prenom,tel,mail,lComp);
                Travail t = new Travail(emp,pour,dateEng);
                lTrav.add(t);
            }
        }catch (Exception e){
            return null;
        }
        return lTrav;
    }

    @Override
    public int totPour(Projet p) {
        String req = "select * from PROJ_TITRE_SUMPOUR_VIEW where ID_PROJET = ?";
        int totPour;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,p.getId_Projet());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                totPour=rs.getInt(3);
                return totPour;
            }else {
                throw new Exception("aucun projet trouvé");
            }
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public boolean addEmploye(Projet p,Employe e,int pour,Date d) {
        /*String req = "insert into api_travail (id_employe,id_projet,pourcentage,dateengag) values (?,?,?,?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,emp.getId_employe());
            pstm.setInt(2,p.getId_Projet());
            pstm.setInt(3,pourcentage);
            long mlsDate = dateEngag.getTime();
            java.sql.Date dateEg = new java.sql.Date(mlsDate);
            pstm.setDate(4,dateEg);

            int n = pstm.executeUpdate();
            if (n==0){
                System.out.println("hello");
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("true");
        return true;*/
        String req = "insert into api_travail (id_employe,id_projet,pourcentage,dateengag) values (?,?,?,?)";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(1,e.getId_employe());
            pstm.setInt(2,p.getId_Projet());
            pstm.setInt(3,pour);
            long mlsDate = d.getTime();
            java.sql.Date dateEg = new java.sql.Date(mlsDate);
            pstm.setDate(4,dateEg);

            int n = pstm.executeUpdate();
            if (n==0){
                return false;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean modifEmploye(Projet p, Employe emp, int pourcentage) {
        String req = "update api_travail set pourcentage = ? where id_employe = ? and id_projet = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,emp.getId_employe());
            pstm.setInt(2,p.getId_Projet());

            int n = pstm.executeUpdate();
            if (n==0){
                return false;
            }
            else return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean supEmploye(Projet p, Employe emp) {
        String req = "delete from api_travail where id_employe = ? and id_projet = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,emp.getId_employe());
            pstm.setInt(2,p.getId_Projet());

            int n = pstm.executeUpdate();
            if (n==0){
                return false;
            }
            else return true;
        }catch (Exception e){
            return false;
        }
    }
}
