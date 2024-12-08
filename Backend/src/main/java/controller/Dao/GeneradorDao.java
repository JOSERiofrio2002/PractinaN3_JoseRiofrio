package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Generador;

public class GeneradorDao extends AdapterDao<Generador> {
    private Generador generador = new Generador(); 
    private LinkedList listAll;
    
    public GeneradorDao(){
        super(Generador.class);
    }
    public Generador getGenerador(){ 
        if (generador == null) {
            generador = new Generador(); 
        }
        return this.generador; 
    }

    public void setGenerador(Generador generador){ 
        this.generador = generador; 
    }

    public LinkedList getlistAll(){  
        if (listAll == null) { 
            this.listAll = listAll(); 
        }
        return listAll; 
    }
    public Boolean save() throws Exception{ 
        Integer id = getlistAll().getSize()+1; 
        generador.setId(id); 
        this.persist(this.generador); 
        this.listAll = listAll(); 
        return true; 
    }

    public Boolean update() throws Exception{ 
        this.merge(getGenerador(), getGenerador().getId() -1); 
        this.listAll = listAll();  
        return true; 
    }

    public Boolean delete(int index) throws Exception { 
        this.supreme(index);
        this.listAll = listAll(); 
        return true; 
    }
    

}

