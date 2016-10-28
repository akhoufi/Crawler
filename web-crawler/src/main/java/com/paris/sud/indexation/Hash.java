package com.paris.sud.indexation;

import java.util.*;
/**
 * Created by Hadhami on 28/10/2016.
 */
public class Hash {

    public int c;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int hash(String url){
        Map<Integer, String> hashedStrings  = new HashMap<Integer, String>();
        int hc = url.hashCode();
        this.c=hc;
        hashedStrings.put(hc, url);
        return hc;
    }



}
