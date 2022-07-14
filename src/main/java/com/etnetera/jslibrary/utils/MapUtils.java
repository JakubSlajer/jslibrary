package com.etnetera.jslibrary.utils;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapUtils {

    /**
     * @param unsortMap unsorted map
     * @param order order to sort by
     * @return sorted map
     * @hidden origin - <a href="https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values">...</a>
     */
    public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
