package com.rewards.fetch.service.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rewards.fetch.service.objects.BalanceObject;
import com.rewards.fetch.service.objects.TransactionObject;
import com.rewards.fetch.service.objects.UserObject;
import com.rewards.fetch.service.util.Pair;

@Path("/fetch_test")
@Produces(MediaType.APPLICATION_JSON)
public class DataController extends Controller{
    
    /**
     * Dummy user account to store data in memory;
     */
    private UserObject user;

    public DataController() {
        user = new UserObject("wrose@fetchrewards.com");
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPoints(List<TransactionObject> transactions) {

        for (TransactionObject transaction : transactions) {
            if (transaction.points > 0) {
                user.addPoints(transaction);
            } else if (transaction.points < 0) {
                user.spendPoints(transaction.points, transaction.payer);
            }
        }

        return Response.ok().build();
    }

    @GET
    @Path("/spend")
    @Produces(MediaType.APPLICATION_JSON)
    public Response spendPoints(@QueryParam("points") int points) {
        List<BalanceObject> balances = user.spendPoints(points, new ArrayList<BalanceObject>());

        for (BalanceObject balance : balances) {
            BalanceObject bo = balances.stream().filter(b -> b.payer.equals(balance.payer)).reduce((a, b) -> b).orElse(null);
            bo.points += balance.points;
        }

        return Response.ok(balances).build();
    }

    @GET
    @Path("/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalance() {
        List<Pair<String, Integer>> balances = new ArrayList<Pair<String, Integer>>();

        for (BalanceObject balance : user.balances) {
            balances.add(new Pair<String,Integer>(balance.payer, balance.points));
        }
        return Response.ok(balances).build();
    }
}
