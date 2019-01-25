package tj.ebm.commons.ServiceInterface;

import java.io.Serializable;
import java.util.List;

public interface BaseCrudService<D, I extends Serializable> {

    D findById(Long id);

    D save(D dto);

    Boolean deleteFromDb(Long id);

    List<D> getAll();

}
