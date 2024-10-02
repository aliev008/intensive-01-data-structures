package org.example;

import org.example.structures.CustomHashMap;

public class Main {
    public static void main(String[] args) {
        CustomHashMap map = new CustomHashMap();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(4, "Four");
        map.put(5, "Five");

        System.out.println(map.get(1)); // Вывод: One
        System.out.println(map.get(3)); // Вывод: Three
        System.out.println(map.get(10)); // Вывод: null
        System.out.println("Size: " + map.size()); // Вывод: Size: 5
    }
}