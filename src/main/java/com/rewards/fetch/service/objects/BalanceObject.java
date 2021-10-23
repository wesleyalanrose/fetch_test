package com.rewards.fetch.service.objects;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Simple object for holding a key-value pair of Payer and Points.
 * 
 * Can be used for output or simple storage.
 * 
 * @author Wesley Rose
 */
public class BalanceObject {
    @JsonProperty public String payer;
    @JsonProperty public int points = 0;

    public BalanceObject(String payer, int points) {
        this.payer = payer;
        this.points = points;
    }

    public BalanceObject(String payer) {
        this.payer = payer;
    }

    public BalanceObject() {

    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void subtractPoints(int points) {
        this.points -= points;
    }

    public String getPayer() {
        return this.payer;
    }


    @Override
    public String toString() {
        return "{" +
            " payer='" + payer + "'" +
            ", points='" + points + "'" +
            "}";
    }
}
