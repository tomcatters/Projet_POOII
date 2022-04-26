package entreprise_info.metier;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Projet {
    protected int id_Projet,cout;
    protected Disciplines id_DisciplineBase;
    protected String titre;
    protected Date dateDebut, dateFin;
    protected List<Travail> lTrav = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public Projet(int id_Projet, int cout, Disciplines id_DisciplineBase, String titre, Date dateDebut, Date dateFin) {
        this.id_Projet = id_Projet;
        this.cout = cout;
        this.id_DisciplineBase = id_DisciplineBase;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public void addEmploye(Employe e, int pourcentage, Date dateEngag){
        Travail t;
        t = new Travail(e,pourcentage,dateEngag);
        lTrav.add(t);
    }

    public void modifEmploye(Employe e, int pourcentage){
        if (lTrav.contains(e)){
            lTrav.get(lTrav.indexOf(e)).setPourcentage(pourcentage);
        }
    }

    public void supEmploye(Employe e){
        int indexT;
        indexT = lTrav.indexOf(e);
        lTrav.remove(indexT);
    }

    public List<Employe> listeEmployesDisciplineBase(int niv){
        List<Competence> compList = new ArrayList<>();
        List <Employe> lEmpNiv = new ArrayList<>();

        for (Travail t:lTrav) {
            compList = t.getEmp().listeDisciplinesEtNiveau();
            for (Competence c: compList){
                if (c.getNiveau() >= niv && c.getId_Discipline().equals(id_DisciplineBase)){
                    lEmpNiv.add(t.getEmp());
                    break;
                }
            }
        }
        if (lEmpNiv.isEmpty()){
            return null;
        }
        return lEmpNiv;
    }

    public int totalPourcentage(){
        int totPour=0;
        for (Travail t:lTrav) {
            totPour+=t.getPourcentage();
            return totPour;
        }
        return totPour;
    }

    public List<Travail> listeEmployesEtPourcentageEtDate(){
        return lTrav;
    }

    public int getId_Projet() {
        return id_Projet;
    }

    public void setId_Projet(int id_Projet) {
        this.id_Projet = id_Projet;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public Disciplines getId_DisciplineBase() {
        return id_DisciplineBase;
    }

    public void setId_DisciplineBase(Disciplines id_DisciplineBase) {
        this.id_DisciplineBase = id_DisciplineBase;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id_Projet=" + id_Projet +
                ", cout=" + cout +
                ", id_DisciplineBase=" + id_DisciplineBase +
                ", titre='" + titre + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
