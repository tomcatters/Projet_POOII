package entreprise_info.metier;

import java.util.*;

/**
 *
 * classe métier de gestion des employés
 *
 * @author williamcrawford
 * @version 0.1
 */

public class Employe {

    private static  int idEmp=0;
    protected int id_employe,matricule;
    protected String nom,prenom,tel,mail;
    protected List<Competence> lComp = new ArrayList();

    private Scanner sc = new Scanner(System.in);

    /**
     *
     * constructeur paramétré
     *
     * @param id_employe identifiant de l'employé
     * @param matricule n° matricule de l'employé
     * @param nom nom de l'employé
     * @param prenom prénom de l'employé
     * @param tel n° de téléphone de l'employé
     * @param mail adresse Email de l'employé
     */

    public Employe(int id_employe, int matricule, String nom, String prenom, String tel, String mail) {
        this.id_employe = id_employe;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
    }

    public Employe(int matricule, String nom, String prenom, String tel, String mail) {
        this.id_employe = idEmp++;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.mail = mail;
    }

    /**
     *
     *  ajout d'une discipline a un employé
     *
     * @param d Objet discipline
     * @param nivComp niveau de la discipline
     */

    public boolean addDiscipline(Disciplines d, int nivComp){
        Competence c;
        c = new Competence(d,nivComp);
        if(!lComp.contains(c)){
            lComp.add(c);
            return true;
        }
        return false;
    }

    /**
     *
     *  modification d'une discipline d'un employé
     *
     * @param d Objet discipline
     * @param nivComp niveau de la discipline
     */

    public boolean modifDiscipline(Disciplines d, int nivComp){
        Competence c;
        c = new Competence(d,nivComp);
        if (lComp.contains(c)){
            lComp.get(lComp.indexOf(c)).setNiveau(nivComp);
            return true;
        }
        return false;
    }

    /**
     *
     * suppression d'une discipline d'un employé
     *
     * @param d Objet discipline
     */

    public boolean suppDiscipline(Disciplines d){
        int indexComp;
        Competence c;
        c = new Competence(d,0);
        if (lComp.contains(c)){
            lComp.remove(c);
            return true;
        }
        return false;
    }

    /**
     *
     *  getter liste des compétence
     *
     * @return liste des compétence d'un employé
     */

    public List<Competence> listeDisciplinesEtNiveau(){
        if (lComp.isEmpty()){
            return null;
        }
        return lComp;
    }

    public List<Competence> getlComp() {
        return lComp;
    }

    public void setlComp(List<Competence> lComp) {
        this.lComp = lComp;
    }

    /**
     *
     *  getter identifiant d'un employé
     *
     * @return identifiant employé
     */

    public int getId_employe() {
        return id_employe;
    }

    /**
     *
     * setter identifiant d'un employé
     *
     * @param id_employe identifiant employé
     */

    public void setId_employe(int id_employe) {
        this.id_employe = id_employe;
    }

    /**
     *
     * getter matricule d'un employé
     *
     * @return matricule d'un employé
     */

    public int getMatricule() {
        return matricule;
    }

    /**
     *
     * setter matricule d'un employé
     *
     * @param matricule matricule d'un employé
     */

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    /**
     *
     * getter nom de l'employé
     *
     * @return nom de l'employé
     */

    public String getNom() {
        return nom;
    }

    /**
     *
     * setter nom de l'employé
     *
     * @param nom nom de l'employé
     */

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * getter prénom de l'employé
     *
     * @return prénom de l'employé
     */

    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * setter prénom de l'employé
     *
     * @param prenom prénom de l'employé
     */

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * getter n° de téléphone de l'employé
     *
     * @return n° de téléphone de l'employé
     */

    public String getTel() {
        return tel;
    }

    /**
     *
     * setter n° de téléphone de l'employé
     *
     * @param tel n° de téléphone de l'employé
     */

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     *
     * getter adresse Email de l'employé
     *
     * @return adresse Email de l'employé
     */

    public String getMail() {
        return mail;
    }

    /**
     * setter adresse Email de l'employé
     *
     *
     * @param mail adresse Email de l'employé
     */

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * méthode toString
     *
     * @return informations complètes
     */

    @Override
    public String toString() {
        return "Employe{" +
                "matricule=" + matricule +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
