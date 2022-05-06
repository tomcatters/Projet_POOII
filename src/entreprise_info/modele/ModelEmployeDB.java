package entreprise_info.modele;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModelEmployeDB implements DAOEmploye{

    protected Connection dbConnect;

    public void ModelEmployeDB(){
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Employe create(Employe emp){
        String req1 = "insert into API_EMPLOYE(matricule,nom,prenom,tel,mail) values(?,?,?,?,?)";
        String req2 = "SELECT id_employe FROM API_EMPLOYE WHERE matricule = ? nom = ? AND prenom = ?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req1);
            PreparedStatement pstm2 = dbConnect.prepareStatement(req2)){
            pstm.setInt(1, emp.getMatricule());
            pstm.setString(2, emp.getNom());
            pstm.setString(3, emp.getPrenom());
            pstm.setString(4, emp.getTel());
            pstm.setString(5, emp.getMail());

            int n = pstm.executeUpdate();
            if (n==0){
                return null;
            }
            pstm2.setInt(1,emp.getMatricule());
            pstm2.setString(2,emp.getNom());
            pstm2.setString(3,emp.getPrenom());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()){
                int id_employe = rs.getInt(1);
                emp.setId_employe(id_employe);
                return emp;
            }else {
                throw new Exception("aucun client trouvé");
            }
        }catch (Exception e){
            return null;
        }
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
        List<Competence> compList = new ArrayList<>();
        compList = listeDisciplinesEtNiveau(emp);
        Employe empTemp = null;
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
                empTemp = new Employe(idemp,mat,nom,prenom,tel,mail);
                compList = listeDisciplinesEtNiveau(empTemp);
                empTemp.setlComp(compList);
            }
            return empTemp;
        }catch (Exception e){
            return null;
        }
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
                emp = new Employe(idemp,mat,nom,prenom,tel,mail);
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
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,emp.getId_employe());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                String req2 = "select * from api_disciplines where id_disciplines = ?";
                Disciplines d = null;
                int idemp = rs.getInt("IDEMPLOYE");
                int idDisc = rs.getInt("ID_DISCIPLINES");
                int niv = rs.getInt("NIVEAU");
                try(PreparedStatement pstm2 = dbConnect.prepareStatement(req2);){
                    pstm.setInt(1,idDisc);
                    ResultSet rs2 = pstm2.executeQuery();
                    if (rs2.next()){
                        int idDisc2 = rs.getInt(1);
                        String nomDisc = rs.getString(2);
                        String Desc = rs.getString(3);
                        d = new Disciplines(idDisc2,nomDisc,Desc);
                    }else {
                        throw new Exception("aucune discipline trouvé");
                    }
                }catch (Exception e){
                    return null;
                }
                Competence c = new Competence(d,niv);
                compList.add(c);
            }if (compList.isEmpty()){
                return null;
            }
            return compList;
        }catch (Exception e){
            return null;
        }
    }
}
