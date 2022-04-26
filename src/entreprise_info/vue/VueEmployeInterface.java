package entreprise_info.vue;

import entreprise_info.metier.Employe;

import java.util.List;

public interface VueEmployeInterface extends VueInterface<Employe,Integer>{
    void affLobj(List lobj);
}
