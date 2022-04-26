package entreprise_info.modele;

import entreprise_info.metier.Competence;
import entreprise_info.metier.Disciplines;
import entreprise_info.metier.Employe;

import java.util.ArrayList;
import java.util.List;

public class ModeleEmploye implements DAOEmploye{

    private List<Employe> lEmploye = new ArrayList<>();

    public Employe create(Employe employeNew){
        if(lEmploye.contains(employeNew)){
            return null;
        }
        lEmploye.add(employeNew);
        return employeNew;
    }

    public Employe update(Employe employeRech){
        Employe emp = read(employeRech);
        if (emp == null){
            return null;
        }
        emp.setId_employe(emp.getId_employe());
        emp.setMatricule(emp.getMatricule());
        emp.setNom(emp.getNom());
        emp.setPrenom(emp.getPrenom());
        emp.setTel(emp.getTel());
        emp.setMail(emp.getMail());
        return emp;
    }

    public boolean delete(Employe employeRech){
        Employe emp = read(employeRech);
        if (emp!=null){
            lEmploye.remove(emp);
            return true;
        }else {
            return false;
        }
    }

    public Employe read(Employe employeRech){
        int idRech = employeRech.getId_employe();
        for (Employe emp:lEmploye) {
            if (emp.getId_employe()==idRech){
                return emp;
            }
        }
        return null;
    }

    public List<Employe> readAll(){
        return lEmploye;
    }

    public List<Competence> listeDisciplinesEtNiveau(Employe emp) {
        return emp.listeDisciplinesEtNiveau();
    }
}
