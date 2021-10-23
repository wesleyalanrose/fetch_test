package com.rewards.fetch.service.objects;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Simple "transaction" storage object. Extends BalanceObject since they need similar data.
 * 
 * @author Wesley Rose
 */
public class TransactionObject extends BalanceObject {
    
    @JsonProperty public Timestamp timestamp;
    @JsonProperty public int pointsAvailable;

    public TransactionObject(String payer, int points, Timestamp timestamp) {
        super(payer, points);
        this.timestamp = timestamp;
        this.pointsAvailable = points;
    }

    public TransactionObject() {

    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }


    @Override
    public String toString() {
        return "{ payer=" + payer + " points=" + points + 
            " timestamp='" + timestamp + "'" +
            ", pointsAvailable=" + pointsAvailable + 
            "}";
    }

}
