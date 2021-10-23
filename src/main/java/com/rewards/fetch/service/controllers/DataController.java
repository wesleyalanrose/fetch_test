package com.rewards.fetch.service.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

import org.apache.http.HttpStatus;

/**
 * API Controller. Holds all the endpoints.
 * 
 * @author Wesley Rose
 */
@Path("/fetch")
@Produces(MediaType.APPLICATION_JSON)
public class DataController extends Controller{
    
    /**
     * Dummy user account to store data in memory;
     */
    private UserObject user;

    public DataController() {
        user = new UserObject("wrose@fetchrewards.com");
    }

    /**
     * The "add" endpoint. Checks if points being added are negative and will remove them from an older transaction from that payer.
     * If that payer doesn't exist. Will create a new payer in the user.balances list and start from negative.
     *
     * @param transactions
     * @return
     */
    @POST
    @Path("/add_points")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPoints(List<TransactionObject> transactions) {
        System.out.println("Transaction adding");

        for (TransactionObject transaction : transactions) {
            System.out.println(transaction.toString());
            if (transaction.points > 0) {
                user.addPoints(transaction);
            } else if (transaction.points < 0) {
                Collections.sort(user.transactions, Comparator.comparing(TransactionObject::getTimestamp));
                user.spendPoints(transaction.points, transaction.payer);
            }
        }
        System.out.println("Finished transaction");
        return Response.ok().build();
    }

    /**
     * "Spend points" endpoint. First checks that the desired total being spent is available, then 
     * calls UserObject.spendPoints() with a fresh ArrayList to be filled with the payers and their totals.
     * @param points
     * @return
     */
    @GET
    @Path("/spend_points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response spendPoints(@QueryParam("points") int points) {
        System.out.println("Spending "+ points +" points");
        boolean checkSumPoints = user.checkPointsTotal(points);
        
        if (checkSumPoints) {   
            Collections.sort(user.transactions, Comparator.comparing(TransactionObject::getTimestamp));
            
            List<BalanceObject> balances = user.spendPoints(points, new ArrayList<BalanceObject>());

            for (BalanceObject balance : balances) {
                BalanceObject bo = user.balances.stream().filter(b -> b.payer.equals(balance.payer)).reduce((a, b) -> b).orElse(null);
                bo.points += balance.points;
            }

            return Response.ok(balances).build();
        } else {
            return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Not enough points available");
        }
    }

    /**
     * "Balance" endpoint. Builds a json from the user.balances list and returns. 
     */
    @GET
    @Path("/check_balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBalance() {
        System.out.println("Requesting balance");
        
        List<String> balances = new ArrayList<String>();

        for (BalanceObject balance : user.balances) {
            String json = "{'"+balance.payer + "': " + balance.points+"}";
            System.out.println(json);
            balances.add(json);
        }

        return Response.ok(balances, MediaType.APPLICATION_JSON).build();
    }
}
