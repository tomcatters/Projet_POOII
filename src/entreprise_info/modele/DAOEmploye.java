package entreprise_info.modele;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Employe;

import java.util.List;

public interface DAOEmploye extends DAO<Employe>{
    List<Competence> listeDisciplinesEtNiveau(Employe emp);
}
