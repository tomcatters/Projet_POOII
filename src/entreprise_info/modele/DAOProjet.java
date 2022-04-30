package entreprise_info.modele;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.util.List;

public interface DAOProjet extends DAO<Projet>{
    List<Employe> listeEmployesDisciplineBase(Projet pjt,int niv);
    List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt);
    int totPour(Projet p);
}
