package com.example.rest;

import java.util.HashMap;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.Dao.servicies.FamiliaServicies;
import models.Familia;
import models.HistorialTransacciones;
import models.Transacciones;


@Path("familia")
public class FamiliaApi {
    private HistorialTransacciones historialTransacciones = new HistorialTransacciones(100);

    // Método de lista general con opciones de filtrado y ordenamiento
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilias(
            @QueryParam("canton") String canton,
            @QueryParam("apellidoPaterno") String apellidoPaterno,
            @QueryParam("apellidoMaterno") String apellidoMaterno,
            @QueryParam("integrantes") Integer integrantes,
            @QueryParam("tieneGenerador") Boolean tieneGenerador,
            @QueryParam("orderBy") String orderBy,
            @QueryParam("orderDirection") String orderDirection) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();

        controller.tda.list.LinkedList<Familia> familias = fs.listAll();

        // Aplicar filtros
        if (canton != null) {
            familias = fs.buscarPorCanton(canton);
        }
        if (apellidoPaterno != null) {
            familias = fs.buscarPorApellidoPaterno(apellidoPaterno);
        }
        if (apellidoMaterno != null) {
            familias = fs.buscarPorApellidoMaterno(apellidoMaterno);
        }
        if (integrantes != null) {
            familias = fs.buscarPorIntegrantes(integrantes);
        }
        if (tieneGenerador != null) {
            familias = fs.buscarPorGenerador(tieneGenerador);
        }

        // Aplicar ordenamiento
        boolean ascendente = orderDirection == null || orderDirection.equalsIgnoreCase("asc");
        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "apellidopaterno":
                    familias = fs.ordenarPorApellidoPaterno(ascendente);
                    break;
                case "apellidomaterno":
                    familias = fs.ordenarPorApellidoMaterno(ascendente);
                    break;
                case "canton":
                    familias = fs.ordenarPorCanton(ascendente);
                    break;
                case "integrantes":
                    familias = fs.ordenarPorIntegrantes(ascendente);
                    break;
                case "generador":
                    familias = fs.ordenarPorGenerador(ascendente);
                    break;
            }
        }

        map.put("msg", "Ok");
        map.put("data", familias.toArray());
        map.put("total", familias.getSize());

        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        try {
            ps.setFamilia(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getFamilia());
        if (ps.getFamilia().getId() == null) {
            map.put("data", "No existe la familia con ese identificador");
           return Response.status(Status.BAD_REQUEST).entity(map).build();
        }


        return Response.ok(map).build();
    }
    
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        //todo
        //Validation

        HashMap res = new HashMap<>();

        try {

            FamiliaServicies ps = new FamiliaServicies();
            ps.getFamilia().setCanton(map.get("canton").toString());
            ps.getFamilia().setApellidoPaterno(map.get("apellidoPaterno").toString());
            ps.getFamilia().setApellidoMaterno(map.get("apellidoMaterno").toString());
            ps.getFamilia().setIntegrantes(Integer.parseInt(map.get("integrantes").toString()));
            ps.getFamilia().setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));

            ps.save();

            Transacciones transaccion = new Transacciones("GUARDAR FAMILIA Y GENERADOR", ps.getFamilia().getId()); // Guardamos la transacción
            historialTransacciones.agregarTransaccion(transaccion);

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
           
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        //todo
        //Validation

        HashMap res = new HashMap<>();

        try {

            FamiliaServicies ps = new FamiliaServicies();
            ps.setFamilia(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getFamilia().setCanton(map.get("canton").toString());
            ps.getFamilia().setApellidoPaterno(map.get("apellidoPaterno").toString());
            ps.getFamilia().setApellidoMaterno(map.get("apellidoMaterno").toString());
            ps.getFamilia().setIntegrantes(Integer.parseInt(map.get("integrantes").toString()));
            ps.getFamilia().setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));
    
            ps.update();

            Transacciones transaccion = new Transacciones("ACTUALIZAR FAMILIA Y GENERADOR", ps.getFamilia().getId()); // Guardamos la transacción
            historialTransacciones.agregarTransaccion(transaccion);

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
           
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    
    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        FamiliaServicies ps = new FamiliaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getFamilia());
        return Response.ok(map).build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFamilia(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
    
        try {
            FamiliaServicies fs = new FamiliaServicies();
            
            boolean familiaDeleted = fs.delete(id - 1);       // Intentamos eliminar la familia

            if (familiaDeleted) {
                res.put("message", "Familia y Generador eliminados exitosamente");
                
                Transacciones transaccion = new Transacciones("ELIMINAR FAMILIA, GENERADOR", id); // Guardamos la transacción
                historialTransacciones.agregarTransaccion(transaccion);

                return Response.ok(res).build();
            } else {
            
                res.put("message", "Familia no encontrada o no eliminada");     // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {
            
            res.put("message", "Error al intentar eliminar la familia"); // En caso de error, devolver una respuesta de error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/contadorGeneradores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response contarFamiliasConGenerador() {
        FamiliaServicies fs = new FamiliaServicies();
        int totalFamiliasConGenerador = fs.contarFamiliasConGenerador();

        HashMap<String, Object> response = new HashMap<>();
        response.put("msg", "Ok");
        response.put("familiasConGenerador", totalFamiliasConGenerador);
        
        return Response.ok(response).build();
    }
 // Métodos de Ordenamiento
@Path("/ordenar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ordenarFamilias(
        @QueryParam("by") String orderBy,
        @QueryParam("direction") String orderDirection
    ) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();
        
        boolean ascendente = orderDirection == null || orderDirection.equalsIgnoreCase("asc");
        controller.tda.list.LinkedList<Familia> familias;

        try {
            switch (orderBy.toLowerCase()) {
                case "apellidopaterno":
                    familias = fs.ordenarPorApellidoPaterno(ascendente);
                    break;
                case "apellidomaterno":
                    familias = fs.ordenarPorApellidoMaterno(ascendente);
                    break;
                case "canton":
                    familias = fs.ordenarPorCanton(ascendente);
                    break;
                case "integrantes":
                    familias = fs.ordenarPorIntegrantes(ascendente);
                    break;
                case "generador":
                    familias = fs.ordenarPorGenerador(ascendente);
                    break;
                default:
                    map.put("msg", "Error");
                    map.put("data", "Criterio de ordenamiento no válido");
                    return Response.status(Status.BAD_REQUEST).entity(map).build();
            }

            map.put("msg", "Ok");
            map.put("data", familias.toArray());
            map.put("total", familias.getSize());
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }

 // Métodos de Búsqueda
@Path("/buscar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarFamilias(
        @QueryParam("canton") String canton,
        @QueryParam("apellidoPaterno") String apellidoPaterno,
        @QueryParam("apellidoMaterno") String apellidoMaterno,
        @QueryParam("integrantes") Integer integrantes,
        @QueryParam("tieneGenerador") Boolean tieneGenerador
    ) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();
        
        controller.tda.list.LinkedList<Familia> resultados = fs.listAll();

        // Aplicar filtros
        if (canton != null) {
            resultados = fs.buscarPorCanton(canton);
        }
        if (apellidoPaterno != null) {
            resultados = fs.buscarPorApellidoPaterno(apellidoPaterno);
        }
        if (apellidoMaterno != null) {
            resultados = fs.buscarPorApellidoMaterno(apellidoMaterno);
        }
        if (integrantes != null) {
            resultados = fs.buscarPorIntegrantes(integrantes);
        }
        if (tieneGenerador != null) {
            resultados = fs.buscarPorGenerador(tieneGenerador);
        }

        map.put("msg", "Ok");
        map.put("data", resultados.toArray());
        map.put("total", resultados.getSize());

        return Response.ok(map).build();
    }

}
