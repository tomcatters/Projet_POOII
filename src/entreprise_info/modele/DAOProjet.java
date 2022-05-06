package entreprise_info.modele;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.util.Date;
import java.util.List;

public interface DAOProjet extends DAO<Projet>{
    List<Travail> listeEmployesDisciplineBase(Projet pjt,int niv);
    List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt);
    int totPour(Projet p);

    boolean addEmploye(Projet p,Employe e, int pourcentage, Date dateEngag);

    boolean modifEmploye(Projet p,Employe e, int pourcentage);

    boolean supEmploye(Projet p,Employe e);
}
