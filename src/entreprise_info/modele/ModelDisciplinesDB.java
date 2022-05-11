package entreprise_info.modele;

import entreprise_info.metier.Disciplines;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModelDisciplinesDB implements DAODisciplines{

    protected Connection dbConnect;

    public ModelDisciplinesDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Disciplines create(Disciplines objNew) {
        String req1 = "insert into api_disciplines(nom,description) values (?,?)";
        String req2 = "select id_disciplines from api_disciplines where nom = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(req1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(req2)){
            pstm.setString(1,objNew.getNom());
            pstm.setString(2,objNew.getDescription());

            int n = pstm.executeUpdate();
            if (n==0){
                return null;
            }
            pstm2.setString(1,objNew.getNom());
            ResultSet rs = pstm2.executeQuery();
            if(rs.next()){
                int id_disc = rs.getInt("ID_DISCIPLINES");
                objNew.setId_Discipline(id_disc);
                return objNew;
            }else {
                throw new Exception("aucune discipline trouvée");
            }
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Disciplines update(Disciplines objRech) {
        String req = "update api_disciplines set nom=?,description=? where id_disciplines=?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setString(1,objRech.getNom());
            pstm.setString(2,objRech.getDescription());

            int n =pstm.executeUpdate();
            if (n==0){
                throw new Exception("aucune discipline mis à jour");
            }
            return read(objRech);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean delete(Disciplines objRech) {
        String req = "delete from api_disciplines where id_disciplines=?";
        try(PreparedStatement pstm = dbConnect.prepareStatement(req)){
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Disciplines read(Disciplines objRech) {
        String req = "select * from api_disciplines where id_disciplines=?";
        Disciplines d = null;
        try(PreparedStatement pstm = dbConnect.prepareStatement(req);){
            pstm.setInt(1,objRech.getId_Discipline());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                int id = rs.getInt("ID_DISCIPLINES");
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                d = new Disciplines(id,nom,desc);
            }
            return d;
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public List<Disciplines> readAll() {
        String req = "select * from api_disciplines";
        List<Disciplines> lDisc = new ArrayList<>();
        Disciplines d = null;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);){
            ResultSet rs = pstm.executeQuery();
            do {
                int idDisc = rs.getInt("ID_DISCIPLINES");
                String nom = rs.getString("NOM");
                String desc = rs.getString("DESCRIPTION");
                d = new Disciplines(idDisc,nom,desc);
                lDisc.add(d);
            }while (rs.next());
            if (lDisc.isEmpty()){
                return null;
            }
            return lDisc;
        }catch (Exception e){
            return null;
        }
    }
}
