package entreprise_info.modele;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;

import java.util.List;

public interface DAOEmploye extends DAO<Employe>{
    List<Competence> listeDisciplinesEtNiveau(Employe emp);
    boolean addDiscipline(Employe emp, Disciplines d,int niv);

    boolean modifDiscipline(Employe emp, Disciplines d,int niv);

    boolean suppDiscipline(Employe emp,Disciplines d);
}
