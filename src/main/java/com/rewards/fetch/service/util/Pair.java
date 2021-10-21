package com.rewards.fetch.service.util;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pair<F, S> {

    @JsonProperty public F first;
    @JsonProperty public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return this.first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return this.second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "<" + first + ", " + second + ">";
    }
}