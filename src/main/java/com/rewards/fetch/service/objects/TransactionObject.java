package com.rewards.fetch.service.objects;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class TransactionObject extends BalanceObject {
    
    @JsonProperty public Date timestamp;
    @JsonProperty public int pointsAvailable = 0;

    public TransactionObject(String payer, int points, Date timestamp) {
        super(payer, points);
        this.timestamp = timestamp;
    }
}
