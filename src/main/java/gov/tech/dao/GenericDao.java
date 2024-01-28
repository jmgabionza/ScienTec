package gov.tech.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
     void save(T domain);
     void update(T domain);
     T getById(ID id);
     List<T> getAll();
}
