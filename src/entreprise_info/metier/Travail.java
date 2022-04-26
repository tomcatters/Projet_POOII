package entreprise_info.metier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Travail {
    protected Employe emp;
    protected int pourcentage;
    protected Date dateEngag;
    protected List<Employe> lEmploye = new ArrayList<>();

    public Travail(Employe id_employe, int pourcentage, Date dateEngag) {
        this.emp = id_employe;
        this.pourcentage = pourcentage;
        this.dateEngag = dateEngag;
    }

    public Employe getEmp() {
        return emp;
    }

    public void setEmp(Employe emp) {
        this.emp = emp;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Date getDateEngag() {
        return dateEngag;
    }

    public void setDateEngag(Date dateEngag) {
        this.dateEngag = dateEngag;
    }

    public List<Employe> getlEmploye() {
        return lEmploye;
    }

    public void setlEmploye(List<Employe> lEmploye) {
        this.lEmploye = lEmploye;
    }

    @Override
    public String toString() {
        return "Travail{" +
                "id_employe=" + emp +
                ", pourcentage=" + pourcentage +
                ", dateEngag=" + dateEngag +
                '}';
    }
}
