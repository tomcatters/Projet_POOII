package entreprise_info.modele;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelEmployeDB implements DAOEmploye{

    protected Connection dbConnect;

    public ModelEmployeDB(){
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.exit(1);
            System.out.println("rip");
        }
        System.out.println("connexion établie");
    }

    @Override
    public Employe create(Employe emp){
        String req1 = "insert into API_EMPLOYE(matricule,nom,prenom,tel,mail) values(?,?,?,?,?)";
        String req2 = "SELECT id_employe FROM API_EMPLOYE WHERE matricule = ? AND nom = ? AND prenom = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req1);){
            pstm.setInt(1, emp.getMatricule());
            pstm.setString(2, emp.getNom());
            pstm.setString(3, emp.getPrenom());
            pstm.setString(4, emp.getTel());
            pstm.setString(5, emp.getMail());

            int n = pstm.executeUpdate();
            if (n==0){
                return null;
            }
            try (PreparedStatement pstm2 = dbConnect.prepareStatement(req2)){
                pstm2.setInt(1,emp.getMatricule());
                pstm2.setString(2,emp.getNom());
                pstm2.setString(3,emp.getPrenom());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()){
                    int id_employe = rs.getInt(1);
                    emp.setId_employe(id_employe);
                    emp.setlComp(new ArrayList<>());
                }else {
                    throw new Exception("aucun client trouvé");
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            //return null;
        }
        return emp;
    }

    @Override
    public Employe update(Employe emp){
        String req = "update api_employe set matricule=?,nom=?,prenom=?,tel=?,mail=? where id_employe= ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(6, emp.getId_employe());
            pstm.setInt(1, emp.getMatricule());
            pstm.setString(2, emp.getNom());
            pstm.setString(3, emp.getPrenom());
            pstm.setString(4, emp.getTel());
            pstm.setString(5, emp.getMail());
            int n =pstm.executeUpdate();
            if (n==0){
                throw new Exception("aucun client mis à jour");
            }
            return read(emp);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean delete(Employe employeRech){
        String req = "delete from api_employe where id_employe=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(1,employeRech.getId_employe());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean addDiscipline(Employe emp, Disciplines d, int niv){
        String req1 = "insert into api_competence (id_employe,id_disciplines,niveau) values (?,?,?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req1);){
            pstm.setInt(1,emp.getId_employe());
            pstm.setInt(2,d.getId_Discipline());
            pstm.setInt(3,niv);

            int n = pstm.executeUpdate();
            if (n==0){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean modifDiscipline(Employe emp,Disciplines d, int niv){
        String req = "update api_competence set niveau = ? where id_employe = ? and id_disciplines = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(2,emp.getId_employe());
            pstm.setInt(3,d.getId_Discipline());
            pstm.setInt(1,niv);

            int n = pstm.executeUpdate();;
            if(n==0){
                return false;
            }
            else {
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean suppDiscipline(Employe emp,Disciplines d){
        String req = "delete from api_competence where id_employe = ? and id_disciplines = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,emp.getId_employe());
            pstm.setInt(2,d.getId_Discipline());

            int n = pstm.executeUpdate();
            if(n == 0){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Employe read(Employe emp){
        String req = "select * from api_employe where id_employe = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)){
            pstm.setInt(1,emp.getId_employe());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                int idemp = rs.getInt(1);
                int mat = rs.getInt(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                String mail = rs.getString(6);
                emp = new Employe(idemp,mat,nom,prenom,tel,mail,new ArrayList<>());
                emp.setlComp(listeDisciplinesEtNiveau(emp));
            }
        }catch (Exception e){
            return null;
        }
        return emp;
    }

    @Override
    public List<Employe> readAll(){
        String req = "select * from api_employe";
        Employe emp = null;
        List<Employe> lEmp = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)){
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                int idemp = rs.getInt(1);
                int mat = rs.getInt(2);
                String nom = rs.getString(3);
                String prenom = rs.getString(4);
                String tel = rs.getString(5);
                String mail = rs.getString(6);
                emp = new Employe(idemp,mat,nom,prenom,tel,mail,new ArrayList<>());
                lEmp.add(emp);
            }
            if (lEmp.isEmpty()){
                return null;
            }
            return lEmp;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Competence> listeDisciplinesEtNiveau(Employe emp){
        String req = "select * from emp_disc_niv_view where idemploye = ?";
        List<Competence> compList = new ArrayList<>();
        Competence c = null;
        Disciplines d = null;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {
            pstm.setInt(1, emp.getId_employe());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id_disc = rs.getInt("ID_DISCIPLINES");
                String nom_disc = rs.getString("NOMDISCIPLINE");
                String desc = rs.getString("DESCRIPTION");
                int niv = rs.getInt("NIVEAU");
                d = new Disciplines(id_disc, nom_disc, desc);
                c = new Competence(d, niv);
                compList.add(c);
            }
        } catch (Exception e) {
            return null;
        }
        return compList;
    }
}