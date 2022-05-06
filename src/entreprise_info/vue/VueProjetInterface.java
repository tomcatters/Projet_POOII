package entreprise_info.vue;

import entreprise_info.metier.Projet;

import java.util.Date;
import java.util.List;

public interface VueProjetInterface extends VueInterface<Projet,Integer>{
    void affLobj(List lobj);
    Date initDate();
}
