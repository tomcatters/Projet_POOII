package entreprise_info.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disciplines {

    private static int idDisc;

    protected int id_Discipline;
    protected String nom,description;

    protected List<Projet> lProjet = new ArrayList<>();

    public Disciplines(int id_Discipline, String nom, String description) {
        this.id_Discipline = id_Discipline;
        this.nom = nom;
        this.description = description;
    }

    public Disciplines(String nom, String description) {
        this.id_Discipline=idDisc++;
        this.nom = nom;
        this.description = description;
    }

    public int getId_Discipline() {
        return id_Discipline;
    }

    public void setId_Discipline(int id_Discipline) {
        this.id_Discipline = id_Discipline;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Disciplines{" +
                "id_Discipline=" + id_Discipline +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplines that = (Disciplines) o;
        return Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    public List<Projet> getlProjet() {
        return lProjet;
    }

    public void setlProjet(List<Projet> lProjet) {
        this.lProjet = lProjet;
    }


}
