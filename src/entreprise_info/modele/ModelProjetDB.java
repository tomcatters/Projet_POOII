package entreprise_info.modele;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.util.Date;
import java.util.List;

public class ModelProjetDB implements DAOProjet{
    @Override
    public Projet create(Projet objNew) {
        return null;
    }

    @Override
    public Projet update(Projet objRech) {
        return null;
    }

    @Override
    public boolean delete(Projet objRech) {
        return false;
    }

    @Override
    public Projet read(Projet objRech) {
        return null;
    }

    @Override
    public List<Projet> readAll() {
        return null;
    }

    @Override
    public List<Travail> listeEmployesDisciplineBase(Projet pjt, int niv) {
        return null;
    }

    @Override
    public List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt) {
        return null;
    }

    @Override
    public int totPour(Projet p) {
        return 0;
    }

    @Override
    public boolean addEmploye(Projet p, Employe e, int pourcentage, Date dateEngag) {
        return false;
    }

    @Override
    public boolean modifEmploye(Projet p, Employe e, int pourcentage) {
        return false;
    }

    @Override
    public boolean supEmploye(Projet p, Employe e) {
        return false;
    }
}
