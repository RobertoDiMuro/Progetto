package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseMultyEntityDemoDao<T, I> {
    
    protected final Map<I, T> demoMemory = new HashMap<>();

    public boolean insert(T entity, I id) {
        if (demoMemory.containsKey(id)) {
            return false;
        }
        demoMemory.put(id, entity);
        return true;
    }

    public T findBy(I id) {
        return demoMemory.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(demoMemory.values());
    }

    public void update(I id, T entity) {
        demoMemory.put(id, entity);
    }

    public boolean delete(I id) {
        if(demoMemory.containsKey(id)){
            demoMemory.remove(id);
            return true;
        }
        return false;
    }

    public void clear() {
        demoMemory.clear();
    }

    public boolean isEmpty() {
        return demoMemory.isEmpty();
    }
}
