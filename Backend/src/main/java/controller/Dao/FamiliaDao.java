package controller.Dao;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Familia;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia = new Familia();
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public Familia getFamilia() {
        if (familia == null) {
            familia = new Familia();
        }
        return this.familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public LinkedList<Familia> getlistAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

  
    public Boolean save() throws Exception {
        if (familia == null) {
            throw new IllegalArgumentException("No se puede guardar una familia nula");
        }
        Integer id = getlistAll().getSize() + 1;
        familia.setId(id);
        this.persist(this.familia);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        if (familia == null || familia.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar una familia nula o sin ID");
        }
        this.merge(getFamilia(), getFamilia().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int index) throws Exception {
        if (index < 0 || index >= getlistAll().getSize()) {
            throw new IndexOutOfBoundsException("Índice de familia inválido");
        }
        this.supreme(index);
        this.listAll = listAll();
        return true;
    }

    // Método de búsqueda genérico
    public LinkedList<Familia> buscar(Predicate<Familia> condicion) {
        LinkedList<Familia> resultados = new LinkedList<>();
        Familia[] familiasArray = getlistAll().toArray();

        for (Familia f : familiasArray) {
            if (condicion.test(f)) {
                resultados.add(f);
            }
        }

        return resultados;
    }

    // Método de ordenamiento genérico
    public LinkedList<Familia> ordenar(Comparator<Familia> comparador, boolean ascendente) {
        if (getlistAll().isEmpty()) {
            return getlistAll();
        }

        Familia[] lista = getlistAll().toArray();
        Arrays.sort(lista, ascendente ? comparador : comparador.reversed());

        listAll.clear();
        for (Familia familia : lista) {
            listAll.add(familia);
        }

        return listAll;
    }

    // Métodos de ordenamiento con comparadores predefinidos
    public LinkedList<Familia> ordenarPorApellidoPaterno(boolean ascendente) {
        return ordenar(
                Comparator.comparing(Familia::getApellidoPaterno, String.CASE_INSENSITIVE_ORDER),
                ascendente);
    }

    public LinkedList<Familia> ordenarPorApellidoMaterno(boolean ascendente) {
        return ordenar(
                Comparator.comparing(Familia::getApellidoMaterno, String.CASE_INSENSITIVE_ORDER),
                ascendente);
    }

    public LinkedList<Familia> ordenarPorCanton(boolean ascendente) {
        return ordenar(
                Comparator.comparing(Familia::getCanton, String.CASE_INSENSITIVE_ORDER),
                ascendente);
    }

    public LinkedList<Familia> ordenarPorIntegrantes(boolean ascendente) {
        return ordenar(Comparator.comparing(Familia::getIntegrantes), ascendente);
    }

    public LinkedList<Familia> ordenarPorGenerador(boolean ascendente) {
        return ordenar(Comparator.comparing(Familia::getTieneGenerador), ascendente);
    }

    // Búsquedas 
    public LinkedList<Familia> buscarPorCanton(String canton) {
        return buscar(f -> f.getCanton().equalsIgnoreCase(canton));
    }

    public LinkedList<Familia> buscarPorApellidoPaterno(String apellidoPaterno) {
        return buscar(f -> f.getApellidoPaterno().equalsIgnoreCase(apellidoPaterno));
    }

    public LinkedList<Familia> buscarPorApellidoMaterno(String apellidoMaterno) {
        return buscar(f -> f.getApellidoMaterno().equalsIgnoreCase(apellidoMaterno));
    }

    public LinkedList<Familia> buscarPorIntegrantes(int integrantes) {
        return buscar(f -> f.getIntegrantes() == integrantes);
    }

    public LinkedList<Familia> buscarPorGenerador(boolean tieneGenerador) {
        return buscar(f -> f.getTieneGenerador() == tieneGenerador);
    }

    // Contador de familias con condición
    public int contarFamilias(Predicate<Familia> condicion) {
        return (int) Arrays.stream(getlistAll().toArray())
                .filter(condicion)
                .count();
    }

    public int contarFamiliasConGenerador() {
        return contarFamilias(Familia::getTieneGenerador);
    }
}