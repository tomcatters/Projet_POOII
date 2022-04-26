package entreprise_info.modele;

import entreprise_info.metier.Disciplines;

import java.util.ArrayList;
import java.util.List;

public class ModeleDisciplines implements DAODisciplines{

    private List<Disciplines> lDisciplines = new ArrayList<>();

    public Disciplines create(Disciplines disciplinesNew){
        if (lDisciplines.contains(disciplinesNew)){
            return null;
        }
        lDisciplines.add(disciplinesNew);
        return disciplinesNew;
    }

    public Disciplines update(Disciplines disciplinesRech){
        Disciplines dsp = read(disciplinesRech);
        if (dsp == null){
            return null;
        }
        dsp.setId_Discipline(dsp.getId_Discipline());
        dsp.setNom(dsp.getNom());
        dsp.setDescription(dsp.getDescription());
        return dsp;
    }

    public boolean delete(Disciplines disciplinesRech){
        Disciplines dsp = read(disciplinesRech);
        if (dsp!=null){
            lDisciplines.remove(dsp);
            return true;
        }else {
            return false;
        }
    }

    public Disciplines read(Disciplines disciplinesRech){
        int idRech = disciplinesRech.getId_Discipline();
        for (Disciplines dsp:lDisciplines) {
            if (dsp.getId_Discipline() == idRech){
                return dsp;
            }
        }
        return null;
    }

    public List<Disciplines> readAll(){
        return lDisciplines;
    }
}
