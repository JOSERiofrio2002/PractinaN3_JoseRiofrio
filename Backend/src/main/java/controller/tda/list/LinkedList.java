package controller.tda.list;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;

    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    protected void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            this.header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            this.header = help;
            this.last = help;
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
        }
        this.size++;
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return header.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public void update(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("La lista está vacía");
        }
        
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites");
        }
        Node<E> help = getNode(index);
        help.setInfo(info);
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public int getLength() {
        return this.size.intValue();
    }

    protected void removeLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            Node<E> nodo_last = getNode((getLength() - 2));
            nodo_last.setNext(null);
            this.last = nodo_last;
            this.size--;
        }
    }

    public void removeFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            Node<E> help = this.header;
            Node<E> nextHeader = help.getNext();
            this.header = nextHeader;
            this.size--;
        }
    }

    public void remove(int index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia, no puede eliminar elementos");
        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + index);
        } else if (index == 0) {
            removeFirst();
        } else if (index == (this.size - 1)) {
            removeLast();
        } else {
            Node<E> nodoDeath = getNode(index);
            Node<E> previousNode = getNode(index - 1);
            previousNode.setNext(nodoDeath.getNext());
            this.size--;
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia, no puede eliminar elementos");
        } else if (post < 0 || post >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + post);
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (this.size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = actually.getInfo();
            Node<E> next = actually.getNext();
            preview.setNext(next);
            size--;
            return element;
        }
    }

    public boolean remove(E element) {
        if (isEmpty()) return false;
        
        if (header.getInfo().equals(element)) {
            header = header.getNext();
            size--;
            return true;
        }
        
        Node<E> current = header;
        while (current.getNext() != null) {
            if (current.getNext().getInfo().equals(element)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // Sorting methods
    private Boolean compare(Object a, Object b, Integer type) {
        if (a == null || b == null) return false;
        
        if (a instanceof Number && b instanceof Number) {
            Number aNum = (Number) a;
            Number bNum = (Number) b;
            return (type == 1) ? aNum.doubleValue() > bNum.doubleValue() : aNum.doubleValue() < bNum.doubleValue();
        } else if (a instanceof String && b instanceof String) {
            String aStr = (String) a;
            String bStr = (String) b;
            return (type == 1) ? aStr.compareTo(bStr) > 0 : aStr.compareTo(bStr) < 0;
        }
        return false;
    }

    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        if (a == null || b == null) return false;
        Object valueA = exist_attribute(a, attribute);
        Object valueB = exist_attribute(b, attribute);
        return compare(valueA, valueB, type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().equals(attribute)) {
                method = aux;
                break;
            }
        }
        
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().equals(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        
        if (method != null) {
            return method.invoke(a);
        }
        throw new Exception("Atributo no econtrado: " + attribute);
    }

  
    public LinkedList<E> order(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            for (int i = 1; i < lista.length; i++) {
                E aux = lista[i];
                int j = i - 1;
                while (j >= 0 && compare(lista[j], aux, orderType)) {
                    lista[j + 1] = lista[j];
                    j--;
                }
                lista[j + 1] = aux;
            }
            this.toList(lista);
        }
        return this;
    }

    public LinkedList<E> order(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            for (int i = 1; i < lista.length; i++) {
                E aux = lista[i];
                int j = i - 1;
                while (j >= 0 && atrribute_compare(attribute, lista[j], aux, type)) {
                    lista[j + 1] = lista[j];
                    j--;
                }
                lista[j + 1] = aux;
            }
            this.toList(lista);
        }
        return this;
    }

    // Búsqueda Secuencial
    public static int busquedaSecuencial(int[] array, int objetivo) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == objetivo) {
                return i; 
            }
        }
        return -1; 
    }

    // Búsqueda Binaria
    public static int busquedaBinaria(int[] array, int objetivo) {
        int inicio = 0, fin = array.length - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (array[medio] == objetivo) {
                return medio; 
            } else if (array[medio] < objetivo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1; 
    }
 
    // QuickSort implementation
    public LinkedList<E> quicksort(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            quicksortHelper(lista, 0, lista.length - 1, orderType);
            this.toList(lista);
        }
        return this;
    }

    private void quicksortHelper(E[] lista, int low, int high, int orderType) throws Exception {
        if (low < high) {
            int pi = partition(lista, low, high, orderType);
            quicksortHelper(lista, low, pi - 1, orderType);
            quicksortHelper(lista, pi + 1, high, orderType);
        }
    }

    private int partition(E[] lista, int low, int high, int orderType) throws Exception {
        E pivot = lista[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (compare(lista[j], pivot, orderType)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        
        E temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;
        
        return i + 1;
    }

    private int partitionAttribute(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
        E pivot = lista[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (atrribute_compare(attribute, lista[j], pivot, type)) {
                i++;
                E temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        
        E temp = lista[i + 1];
        lista[i + 1] = lista[high];
        lista[high] = temp;
        
        return i + 1;
    }
    
    public LinkedList<E> quicksort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            quicksortHelperAttribute(lista, 0, lista.length - 1, attribute, type);
            this.toList(lista);
        }
        return this;
    }
    
    private void quicksortHelperAttribute(E[] lista, int low, int high, String attribute, Integer type) throws Exception {
        if (low < high) {
            int pi = partitionAttribute(lista, low, high, attribute, type);
            quicksortHelperAttribute(lista, low, pi - 1, attribute, type);
            quicksortHelperAttribute(lista, pi + 1, high, attribute, type);
        }
    }
    
    public LinkedList<E> mergesort(int orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = mergesortHelper(lista, orderType);
            this.toList(lista);
        }
        return this;
    }
    
    private E[] mergesortHelper(E[] lista, int orderType) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }
    
        int middle = lista.length / 2;
        E[] left = Arrays.copyOfRange(lista, 0, middle);
        E[] right = Arrays.copyOfRange(lista, middle, lista.length);
    
        left = mergesortHelper(left, orderType);
        right = mergesortHelper(right, orderType);
    
        return merge(left, right, orderType);
    }
    
    private E[] merge(E[] left, E[] right, int orderType) throws Exception {
        E[] result = (E[]) new Object[left.length + right.length];
        int i = 0, j = 0, k = 0;
    
        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], orderType)) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
    
        while (i < left.length) {
            result[k++] = left[i++];
        }
    
        while (j < right.length) {
            result[k++] = right[j++];
        }
    
        return result;
    }
    
    public LinkedList<E> mergesort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            lista = mergesortHelperAttribute(lista, attribute, type);
            this.toList(lista);
        }
        return this;
    }
    
    private E[] mergesortHelperAttribute(E[] lista, String attribute, Integer type) throws Exception {
        if (lista.length <= 1) {
            return lista;
        }
    
        int middle = lista.length / 2;
        E[] left = Arrays.copyOfRange(lista, 0, middle);
        E[] right = Arrays.copyOfRange(lista, middle, lista.length);
    
        left = mergesortHelperAttribute(left, attribute, type);
        right = mergesortHelperAttribute(right, attribute, type);
    
        return mergeAttribute(left, right, attribute, type);
    }
    
    private E[] mergeAttribute(E[] left, E[] right, String attribute, Integer type) throws Exception {
        E[] result = (E[]) new Object[left.length + right.length];
        int i = 0, j = 0, k = 0;
    
        while (i < left.length && j < right.length) {
            if (atrribute_compare(attribute, left[i], right[j], type)) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
    
        while (i < left.length) {
            result[k++] = left[i++];
        }
    
        while (j < right.length) {
            result[k++] = right[j++];
        }
    
        return result;
    }
    
    public LinkedList<E> shellSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            int n = lista.length;
    
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E temp = lista[i];
                    int j;
    
                    for (j = i; j >= gap && atrribute_compare(attribute, lista[j - gap], temp, type); j -= gap) {
                        lista[j] = lista[j - gap];
                    }
                    lista[j] = temp;
                }
            }
    
            this.toList(lista);
        }
        return this;
    }
    
    public LinkedList<E> shellsort(String attribute, Integer orderType) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            reset();
            int n = lista.length;
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E temp = lista[i];
                    int j;
                    for (j = i; j >= gap && compare(lista[j - gap], temp, orderType); j -= gap) {
                        lista[j] = lista[j - gap];
                    }
                    lista[j] = temp;
                }
            }
            this.toList(lista);
        }
        return this;
    }

    public void clear() {

            Object head = null; 
            size = 0;   
        
    }

}