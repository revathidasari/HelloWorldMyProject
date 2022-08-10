package com.example.helloworldgrpc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyHashMap {

    HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
    Integer key;
    String value;

    public void accessingHashMap(String data) {
        System.out.println("accessingHashMap called");
        if (!hashMap.isEmpty()) {
            if (Objects.requireNonNull(hashMap.get(3)).equalsIgnoreCase(data)){
                System.out.println("Both are same");
            } else {
                System.out.println("Different values");
            }
        } else {
            System.out.println(" null");
        }
    }

    public String getDataAtPosition(int position) {
        return hashMap.get(position);
    }

    public HashMap<Integer, String> getMapData(){

        hashMap.put(1, "Dasari");
        hashMap.put(2, "Revathi");
        hashMap.put(3, "Reddy");

        System.out.println(hashMap);

        System.out.println(hashMap.get(2));
        for(Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            System.out.println(key+value);
            if (key.equals(3)){
                System.out.println(key+"->"+value);
            }
        }

        accessingHashMap("RevatHI");
        return hashMap;
    }

}
