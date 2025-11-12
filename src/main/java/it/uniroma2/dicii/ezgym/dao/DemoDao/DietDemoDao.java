package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DietDao;
import it.uniroma2.dicii.ezgym.domain.model.Diet;

public class DietDemoDao extends BaseMultyEntityDemoDao<Diet, String> implements DietDao {

    private static DietDemoDao instance;

    private DietDemoDao() {
    }

    public static synchronized DietDemoDao getInstance() {
        if (instance == null) {
            instance = new DietDemoDao();
        }
        return instance;
    }   
    
}
