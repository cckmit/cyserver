package com.cy.common.utils.sortandgroup;

import com.cy.system.PinYinUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by cha0res on 1/12/17.
 */
public class SortByPinyinUtils {
    public static Map<String, Object> sortAndGroupByPinyin(List<Map<String, String>> list){
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i<list.size(); i++){
            list.get(i).put("pinyin", PinYinUtils.getQuanPin(list.get(i).get("name")).toUpperCase());
        }
        ComparatorHashMap comparator=new ComparatorHashMap();
        Collections.sort(list, comparator);
        String[] charArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for(int i = 0; i < charArray.length; i++){
            if(list.size() > 0){
                List<Map<String, String>> listItem = new ArrayList<>();
                Boolean in = false;
                for(int j = 0; j < list.size(); j++){
                    String pinyin = list.get(j).get("pinyin");
                    if(charArray[i].equals(pinyin.substring(0,1))){
                        listItem.add(list.get(j));
                        list.remove(j);
                        in = true;
                    }
                }
                if(in){
                    map.put(charArray[i],listItem);
                }
            }
        }
        return map;
    }

    public static List<Map<String, String>> sortByPinyin(List<Map<String, String>> list){
        for(int i = 0; i<list.size(); i++){
            list.get(i).put("pinyin", PinYinUtils.getPinYin(list.get(i).get("name")).replaceAll("\\s*", "").toUpperCase());
        }
        ComparatorHashMap comparator=new ComparatorHashMap();
        Collections.sort(list, comparator);
        return list;
    }
}
