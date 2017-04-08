package com.example.listview;

import java.util.Comparator;

/**
 * Created by taylo on 4/8/2017.
 */

public class ComparatorModel implements Comparator<BikeData> {
        @Override
        public int compare(BikeData c1, BikeData c2) {
            return c1.getModel().compareTo(c2.getModel());
        }
}
