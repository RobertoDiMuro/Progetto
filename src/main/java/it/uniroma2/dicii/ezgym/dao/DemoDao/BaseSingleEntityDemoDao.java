package it.uniroma2.dicii.ezgym.dao.DemoDao;

public abstract class BaseSingleEntityDemoDao<T> {

    protected T entity;

    public boolean insert(T newEntity) {
        if (this.entity != null) {
            return false;
        }
        this.entity = newEntity;
        return true;
    }

    public T get(){
        return entity;
    }

    public void update(T newEntity) {
        this.entity = newEntity;
    }

   public boolean delete(T entity) {
        if(this.entity != null){
            this.entity = null;
            return true;
        }
        return false;
    }

    public boolean isPresent() {
        return this.entity != null;
    }
    
}
