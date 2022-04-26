package entreprise_info.modele;

import java.util.List;

public interface DAO<T> {
    T create(T objNew);
    T update(T objRech);
    boolean delete(T objRech);
    T read (T objRech);
    List<T> readAll();
}
