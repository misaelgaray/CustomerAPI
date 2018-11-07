package com.rest.resources;

import com.google.gson.Gson;
import model.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CustomerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/customer")
public class CustomerResource {

    private static final Logger log = LogManager.getLogger(CustomerResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(){
        Map<String,Object> response = new HashMap<>();
        try {
            CustomerService customerService = new CustomerService();
            List<Customer> customers = customerService.getAll();
            return Response.ok().entity(new Gson().toJson(customers))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET")
                    .build();
        }catch (SQLException e){
            response.put("error",e.getMessage());
            log.error("Error caused by ", e);
            return Response.serverError().entity(new Gson().toJson(response))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","POST").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id){
        Map<String,Object> response = new HashMap<>();
        try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.get(id);
            return Response.ok().entity(new Gson().toJson(customer))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET")
                    .build();
        }catch (Exception e){
            response.put("error",e.getMessage());
            log.error("Error caused by ", e);
            return Response.serverError().entity(new Gson().toJson(response))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","POST").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delteCustomer(@PathParam("id") Integer id){
        Map<String,Object> response = new HashMap<>();
        try {
            CustomerService customerService = new CustomerService();
            customerService.delete(id);
            response.put("result","success");
            return Response.ok().entity(new Gson().toJson(response))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","DELETE")
                    .build();
        }catch (Exception e){
            response.put("error",e.getMessage());
            log.error("Error caused by ", e);
            return Response.serverError().entity(new Gson().toJson(response))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","POST").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") Integer id, String data){
        Map<String,Object> response = new HashMap<>();
        try {

            if(data == null) {
                response.put("error", "No data sent");
                return Response.serverError().entity(new Gson().toJson(response))
                        .header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","PUT").build();
            }


            Customer customer = new Gson().fromJson(data,Customer.class);
            if(customer.getAge() == null || customer.getName() == null
                    || customer.getStatus() == null){
                response.put("error", "Required properties are empty");
                return Response.serverError().entity(new Gson().toJson(response))
                        .header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","PUT").build();
            }

            if(customer.getId() == null || !customer.getId().equals(id)){
                response.put("error","Not id sent");
                return Response.serverError().entity(new Gson().toJson(response))
                        .header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","PUT")
                        .build();
            }

            CustomerService customerService = new CustomerService();
            Customer editedCustomer = customerService.edit(customer);
            return Response.ok().entity(new Gson().toJson(editedCustomer))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","PUT")
                    .build();
        }catch (Exception e){
            response.put("error",e.getMessage());
            log.error("Error caused by ", e);
            return Response.serverError().entity(new Gson().toJson(response))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","PUT")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addCustomer(String data){
        Map<String, Object> result = new HashMap<>();

        try{

            if(data == null) {
                result.put("error", "No data sent");
                return Response.serverError().entity(new Gson().toJson(result))
                        .header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","POST").build();
            }

            Customer customer = new Gson().fromJson(data,Customer.class);
            if(customer.getAge() == null || customer.getName() == null
                    || customer.getStatus() == null){
                result.put("error", "Required properties are empty");
                return Response.serverError().entity(result).header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","POST").build();
            }

            CustomerService customerService = new CustomerService();

            if(customerService.getByName(customer.getName()) != null){
                result.put("error", "Customer already exist");
                return Response.serverError().entity(result).header("Access-Control-Allow-Origin","*")
                        .header("Access-Control-Allow-Methods","POST").build();
            }

            Customer newCustomer = customerService.add(customer);
            return Response.ok().entity(new Gson().toJson(newCustomer))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","POST")
                    .build();
        }catch (Exception e){
            log.error("Error caused by ", e);
            result.put("error", e.getMessage());
            return Response.serverError().entity(new Gson().toJson(result))
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","POST").build();
        }

    }

}
