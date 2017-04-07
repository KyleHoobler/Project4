package com.example.listview;

import java.util.Comparator;

/**
 * Created by Kyle on 4/7/2017.
 */
public class ComparatorCompany implements Comparator<BikeData> {
    @Override
    public int compare(BikeData c1, BikeData c2) {
        return c1.getCompany().compareTo(c2.getCompany());
    }
}
