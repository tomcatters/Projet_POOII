package entreprise_info.modele;

import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelProjet implements DAOProjet{

    private List<Projet> lProjet = new ArrayList<>();

    public Projet create(Projet projetNew){
        if (lProjet.contains(projetNew)){
            return null;
        }
        lProjet.add(projetNew);
        Disciplines dTmp = projetNew.getId_DisciplineBase();
        dTmp.getlProjet().add(projetNew);
        return projetNew;
    }

    public Projet update(Projet projetRech){
        Projet pjt = read(projetRech);
        if (pjt == null){
            return null;
        }
        pjt.setId_Projet(pjt.getId_Projet());
        pjt.setCout(pjt.getCout());
        pjt.setId_DisciplineBase(pjt.getId_DisciplineBase());
        pjt.setTitre(pjt.getTitre());
        pjt.setDateDebut(pjt.getDateDebut());
        pjt.setDateFin(pjt.getDateFin());
        return pjt;
    }

    public boolean delete(Projet projetRech){
        Projet pjt = read(projetRech);
        if (pjt!=null){
            Disciplines dTmp = projetRech.getId_DisciplineBase();
            dTmp.getlProjet().remove(projetRech);
            lProjet.remove(pjt);
            return true;
        }else {
            return false;
        }
    }

    public Projet read(Projet projetRech){
        int idRech = projetRech.getId_Projet();
        for (Projet pjt:lProjet) {
            if (pjt.getId_Projet()==idRech){
                return pjt;
            }
        }
        return null;
    }

    public boolean addEmploye(Projet p,Employe e, int pour, Date d){
        return p.addEmploye(e,pour,d);
    }

    public boolean modifEmploye(Projet p,Employe e, int pourcentage){
        return p.modifEmploye(e,pourcentage);
    }

    public boolean supEmploye(Projet p,Employe e){
        return p.supEmploye(e);
    }

    public List<Projet> readAll(){
        return lProjet;
    }

    public List<Travail> listeEmployesDisciplineBase(Projet pjt,int niv){
        return pjt.listeEmployesDisciplineBase(niv);
    }

    public List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt){
        return pjt.listeEmployesEtPourcentageEtDate();
    }

    public int totPour(Projet p){return p.totalPourcentage();}
}
