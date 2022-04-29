package entreprise_info.vue;

import entreprise_info.metier.Disciplines;

import java.util.List;

public interface VueDisciplinesInterface extends VueInterface<Disciplines,Integer>{
    void affLobj(List lobj);
}
