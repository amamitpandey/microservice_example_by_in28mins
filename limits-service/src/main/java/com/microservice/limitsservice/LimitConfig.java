package com.microservice.limitsservice;

public class LimitConfig {
    private  int minimum;
    private  int maxmimum;

    public LimitConfig(int i, int i1) {
        this.minimum = i;
        this.maxmimum = i1;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaxmimum() {
        return maxmimum;
    }

    public void setMaxmimum(int maxmimum) {
        this.maxmimum = maxmimum;
    }


}
