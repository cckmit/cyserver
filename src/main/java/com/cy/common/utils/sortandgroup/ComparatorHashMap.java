package com.cy.common.utils.sortandgroup;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by cha0res on 1/12/17.
 */
public class ComparatorHashMap implements Comparator {

    public int compare(Object arg0, Object arg1) {

        HashMap<String, Object> map = (HashMap<String, Object>)arg0;
        HashMap<String, Object> map2 = (HashMap<String, Object>)arg1;
        return (map.get("pinyin").toString()).compareTo(map2.get("pinyin").toString());
    }
}