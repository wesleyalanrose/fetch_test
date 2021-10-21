package com.rewards.fetch.service.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserObject {
    @JsonProperty public String userID;
    @JsonProperty public List<BalanceObject> balances;
    @JsonProperty public List<TransactionObject> transactions;

    public UserObject(String userID){
        this.transactions = new ArrayList<TransactionObject>();
        this.balances = new ArrayList<BalanceObject>();
    }

    /**
     * Adds a transaction record, and updates the balances List. Adds a record if a new payer is present.
     * @param transaction
     */
    public void addPoints(TransactionObject transaction) {
        this.transactions.add(transaction);
        
        Optional<BalanceObject> bo = balances.stream().filter(b -> b.payer.equals(transaction.payer)).reduce((a, b) -> b);
        if (bo.isPresent()) {
            BalanceObject boReal = bo.get();
            boReal.addPoints(transaction.points);
        } else {
            balances.add(new BalanceObject(transaction.payer, transaction.points));
        }
    }

    /**
     * "Spends" points if a transaction gives a negative total from the "add transaction" route
     * 
     * @param pointTotal
     * @param payer
     */
    public void spendPoints(int pointTotal, String payer) {
        Optional<BalanceObject> bo = balances.stream().filter(b -> b.payer.equals(payer)).reduce((a, b) -> b);
        if (bo.isPresent()) {
            BalanceObject boReal = bo.get();
            boReal.subtractPoints(pointTotal);
        } else {
            balances.add(new BalanceObject(payer, pointTotal));
        }
    }
    
    public ArrayList<BalanceObject> spendPoints(int pointTotal, ArrayList<BalanceObject> list) {
        
        do {
            BalanceObject localB = new BalanceObject(transactions.get(0).payer, transactions.get(0).pointsAvailable);
            Optional<BalanceObject> bo = list.stream().filter(b -> b.payer.equals(localB.payer)).reduce((a, b) -> b);
            BalanceObject boReal;

            if (bo.isPresent()) {
                boReal = bo.get();
            } else {
                list.add(new BalanceObject(localB.payer));
                boReal = list.get(list.size()-1);
            }

            if (pointTotal > localB.points) {
                pointTotal -= localB.points;
                transactions.remove(0);
                list = spendPoints(pointTotal, list);
            } else if (pointTotal <= localB.points) {
                pointTotal = 0;
                
                boReal.points -= localB.points;

                if (pointTotal == localB.points) {
                    transactions.remove(0);
                } else {
                    transactions.get(0).pointsAvailable -= pointTotal;
                }
            } 
        } while (pointTotal > 0);

        return list;
    }
}
