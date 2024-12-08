package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.Dao.servicies.FamiliaServicies;
import models.Familia;


@Path("myresource")
public class MyResource {
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, Object> mapa = new HashMap<>();
        String aux = "";
        
        try {
            FamiliaServicies fs = new FamiliaServicies();
            
            // Verificar lista vacía
            aux = "La lista de familias está vacía: " + fs.listAll().isEmpty();
            System.out.println(aux);
            
            // Obtener y ordenar familias
            controller.tda.list.LinkedList<Familia> familias = fs.listAll();
            System.out.println("Lista de familias: " + familias.toString());
            
            // Aplicar ordenamientos
            familias.mergesort("apellidoPaterno", 1);
            familias.quicksort("apellidoPaterno", 1);
            familias.shellsort("apellidoPaterno", 1);
            
            // Crear familia ejemplo 1
            fs.getFamilia().setApellidoPaterno("García");
            fs.getFamilia().setApellidoMaterno("López");
            fs.getFamilia().setCanton("Quito");
            fs.getFamilia().setIntegrantes(4);
            fs.getFamilia().setTieneGenerador(true);
            fs.save();

            // Crear familia ejemplo 2
            fs.getFamilia().setApellidoPaterno("Martínez");
            fs.getFamilia().setApellidoMaterno("Pérez");
            fs.getFamilia().setCanton("Guayaquil");
            fs.getFamilia().setIntegrantes(3);
            fs.getFamilia().setTieneGenerador(false);
            fs.save();

            aux = "La lista contiene " + familias.getSize() + " familias.";
            
            mapa.put("msg", "Ok");
            mapa.put("data", aux);
            
        } catch (Exception e) {
            e.printStackTrace();
            mapa.put("msg", "Error");
            mapa.put("error", e.getMessage() != null ? e.getMessage() : "Error desconocido");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        return Response.ok(mapa).build();
    } 
}
        
/* 
@GET
@Path("/benchmark")
@Produces(MediaType.APPLICATION_JSON)
public Response compareAlgorithms() {
    HashMap<String, Object> response = new HashMap<>();
    LinkedList<Integer> list = new LinkedList<>();
    
    System.out.println("\n=== BENCHMARK DE ALGORITMOS DE ORDENAMIENTO ===");
    System.out.println("Generando " + 10000 + " números aleatorios...\n");
    
    Random random = new Random();
    for (int i = 0; i < 10000; i++) {
        list.add(random.nextInt(10000));
    }
    
    Map<String, Long> tiempos = new HashMap<>();
    Integer[] originalArray = list.toArray();
    
    // Probar cada algoritmo y medir tiempo
    String[] algoritmos = {"Mergesort", "Quicksort", "Shellsort"};
    for (String algoritmo : algoritmos) {
        try {
            LinkedList<Integer> testList = new LinkedList<>();
            testList.toList(originalArray.clone());
            long startTime = System.nanoTime();
            
            switch (algoritmo) {
                case "Mergesort":
                    testList.mergesort("", 1);
                    break;
                case "Quicksort":
                    testList.quicksort("", 1);
                    break;
                case "Shellsort":
                    testList.shellsort("", 1);
                    break;
            }
            
            long endTime = System.nanoTime();
            long tiempoMs = (endTime - startTime) / 1_000_000;
            tiempos.put(algoritmo, tiempoMs);
            
            System.out.printf("%-10s: %5d ms\n", algoritmo, tiempoMs);
            
        } catch (Exception e) {
            tiempos.put(algoritmo, -1L);
            System.out.printf("%-10s: Error\n", algoritmo);
        }
    }
    
    System.out.println("\n=== FIN DEL BENCHMARK ===\n");
    
    List<Map<String, Object>> tabla = new ArrayList<>();
    for (Map.Entry<String, Long> entry : tiempos.entrySet()) {
        Map<String, Object> fila = new HashMap<>();
        fila.put("Algoritmo", entry.getKey());
        fila.put("Tiempo (ms)", entry.getValue());
        tabla.add(fila);
    }
    
    response.put("tamañoMuestra", 10000);
    response.put("resultados", tabla);
    
    return Response.ok(response).build();
}
   */ 
  
/* 
    @GET
    @Path("/busqueda")
    @Produces(MediaType.APPLICATION_JSON)
    public Response benchmarkBusqueda() {
        HashMap<String, Object> response = new HashMap<>();
        
         int[] sizes = {10000, 20000, 25000};
        Random random = new Random();

        System.out.printf("%-10s %-10s %-15s %-10s%n", "Size", "Algorithm", "Execution Time", "Found At");

        for (int size : sizes) {
            // Generar arreglo aleatorio
            int[] array = generarArreglo(size);
            int objetivo = array[random.nextInt(size)]; 

            // Medir búsqueda secuencial
            long tiempoInicio = System.nanoTime();
            int resultadoSecuencial = busquedaSecuencial(array, objetivo);
            long tiempoFin = System.nanoTime();
            System.out.printf("%-10d %-10s %-15.5f %-10d%n", size, "Secuencial",
                    (tiempoFin - tiempoInicio) / 1e6, resultadoSecuencial);

            // Medir búsqueda binaria
            Arrays.sort(array); // Ordenar el arreglo para búsqueda binaria
            tiempoInicio = System.nanoTime();
            int resultadoBinaria = busquedaBinaria(array, objetivo);
            tiempoFin = System.nanoTime();
            System.out.printf("%-10d %-10s %-15.5f %-10d%n", size, "Binaria",
                    (tiempoFin - tiempoInicio) / 1e6, resultadoBinaria);
        }
        return null;
    }
    

    // Generar un arreglo de tamaño n con valores aleatorios entre 1 y 100,000
    public static int[] generarArreglo(int n) {
        Random random = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(100000) + 1;
        }
        return array;
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
}
*/
