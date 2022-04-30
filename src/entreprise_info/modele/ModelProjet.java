package entreprise_info.modele;

import entreprise_info.metier.Employe;
import entreprise_info.metier.Projet;
import entreprise_info.metier.Travail;

import java.util.ArrayList;
import java.util.List;

public class ModelProjet implements DAOProjet{

    private List<Projet> lProjet = new ArrayList<>();

    public Projet create(Projet projetNew){
        if (lProjet.contains(projetNew)){
            return null;
        }
        lProjet.add(projetNew);
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

    public List<Projet> readAll(){
        return lProjet;
    }

    public List<Employe> listeEmployesDisciplineBase(Projet pjt,int niv){
        return pjt.listeEmployesDisciplineBase(niv);
    }

    public List<Travail> listeEmployesEtPourcentageEtDate(Projet pjt){
        return pjt.listeEmployesEtPourcentageEtDate();
    }

    public int totPour(Projet p){return p.totalPourcentage();}
}
