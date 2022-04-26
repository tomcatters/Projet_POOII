package entreprise_info.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Competence {
    protected Disciplines id_Discipline;
    protected int niveau;
    protected List<Disciplines> lDisc = new ArrayList<>();

    public Competence(Disciplines id_Discipline, int niveau) {
        this.id_Discipline = id_Discipline;
        this.niveau = niveau;
    }

    public Disciplines getId_Discipline() {
        return id_Discipline;
    }

    public void setId_Discipline(Disciplines id_Discipline) {
        this.id_Discipline = id_Discipline;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public List<Disciplines> getlDisc() {
        return lDisc;
    }

    public void setlDisc(List<Disciplines> lDisc) {
        this.lDisc = lDisc;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "id_Discipline=" + id_Discipline +
                ", niveau=" + niveau +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competence that = (Competence) o;
        return Objects.equals(id_Discipline, that.id_Discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Discipline);
    }
}
