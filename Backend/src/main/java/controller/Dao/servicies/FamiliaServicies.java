package controller.Dao.servicies;
import java.util.function.Predicate;

import controller.Dao.FamiliaDao;
import controller.tda.list.LinkedList;
import models.Familia;

public class FamiliaServicies {
    private FamiliaDao obj;

    public FamiliaServicies() {
        obj = new FamiliaDao();
    }

    // Métodos CRUD con validaciones
    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Familia> listAll() {
        return obj.getlistAll();
    }

    public Familia getFamilia() {
        return obj.getFamilia();
    }

    public void setFamilia(Familia familia) {
        obj.setFamilia(familia);
    }

    public Familia get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Boolean delete(int index) throws Exception {
        return obj.delete(index);
    }

    // Métodos de búsqueda generalizados
    public LinkedList<Familia> buscar(Predicate<Familia> condicion) {
        return obj.buscar(condicion);
    }

    // Búsquedas específicas
    public LinkedList<Familia> buscarPorCanton(String canton) {
        return obj.buscarPorCanton(canton);
    }

    public LinkedList<Familia> buscarPorApellidoPaterno(String apellidoPaterno) {
        return obj.buscarPorApellidoPaterno(apellidoPaterno);
    }

    public LinkedList<Familia> buscarPorApellidoMaterno(String apellidoMaterno) {
        return obj.buscarPorApellidoMaterno(apellidoMaterno);
    }

    public LinkedList<Familia> buscarPorIntegrantes(int integrantes) {
        return obj.buscarPorIntegrantes(integrantes);
    }

    public LinkedList<Familia> buscarPorGenerador(boolean tieneGenerador) {
        return obj.buscarPorGenerador(tieneGenerador);
    }

    // Métodos de ordenamiento generalizados
    public LinkedList<Familia> ordenarPorApellidoPaterno(boolean ascendente) {
        return obj.ordenarPorApellidoPaterno(ascendente);
    }

    public LinkedList<Familia> ordenarPorApellidoMaterno(boolean ascendente) {
        return obj.ordenarPorApellidoMaterno(ascendente);
    }

    public LinkedList<Familia> ordenarPorCanton(boolean ascendente) {
        return obj.ordenarPorCanton(ascendente);
    }

    public LinkedList<Familia> ordenarPorIntegrantes(boolean ascendente) {
        return obj.ordenarPorIntegrantes(ascendente);
    }

    public LinkedList<Familia> ordenarPorGenerador(boolean ascendente) {
        return obj.ordenarPorGenerador(ascendente);
    }

    // Contadores
    public int contarFamiliasConGenerador() {
        return obj.contarFamiliasConGenerador();
    }
}
