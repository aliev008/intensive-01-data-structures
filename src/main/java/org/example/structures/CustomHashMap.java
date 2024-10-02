package org.example.structures;

import java.util.LinkedList;

public class CustomHashMap {
    private static final int INIT_CAPACITY = 16;
    private static final int THRESHOLD_BEFORE_TREE = 5;

    private LinkedList<Node>[] table;
    private int size = 0;

    public CustomHashMap() {
        table = new LinkedList[INIT_CAPACITY];
    }

    // Метод хеширования ключа
    private int hash(Integer key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % table.length);
    }

    // Метод для добавления элементов
    public void put(Integer key, String value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        // Поиск узла с таким же ключом
        for (Node node : table[index]) {
            if (node.key.equals(key)) {
                node.value = value; // Перезапись значения, если ключ уже есть
                return;
            }
        }

        // Добавление нового узла
        Node newNode = new Node(key, value);
        table[index].add(newNode);
        size++;

        // Проверка на перестройку в дерево, если в одном бакете слишком много элементов
        if (table[index].size() >= THRESHOLD_BEFORE_TREE) {
            makeTree(index);
        }
    }

    // Метод для получения значений элементов
    public String get(Integer key) {
        int index = hash(key);
        if (table[index] == null) return null;

        // Если элемент является деревом, то используем поиск в дереве
        if (isTreeNode(table[index].get(0))) {
            return searchInTree(table[index].get(0), key);
        }

        for (Node node : table[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // Перестройка списка в дерево
    private void makeTree(int index) {
        LinkedList<Node> bucket = table[index];
        if (bucket == null || bucket.isEmpty()) return;

        // Создаём корень дерева из первого элемента списка
        Node root = bucket.get(0);
        root.next = null; // Очищаем ссылки списка
        for (int i = 1; i < bucket.size(); i++) {
            Node node = bucket.get(i);
            node.next = null; // Очищаем ссылки списка
            insertIntoBST(root, node); // Вставляем узлы в дерево
        }

        // Преобразуем список в дерево, сохраняя корень дерева как единственный элемент
        table[index].clear();
        table[index].add(root);
    }

    // Вставка узла в бинарное дерево
    private void insertIntoBST(Node root, Node node) {
        if (node.key < root.key) {
            if (root.left == null) {
                root.left = node;
            } else {
                insertIntoBST(root.left, node);
            }
        } else {
            if (root.right == null) {
                root.right = node;
            } else {
                insertIntoBST(root.right, node);
            }
        }
    }

    // Поиск в бинарном дереве
    private String searchInTree(Node root, Integer key) {
        if (root == null) return null;
        if (root.key.equals(key)) return root.value;
        if (key < root.key) return searchInTree(root.left, key);
        return searchInTree(root.right, key);
    }

    // Проверка, является ли элемент узлом дерева
    private boolean isTreeNode(Node node) {
        return node.left != null || node.right != null;
    }

    // Метод для получения размера таблицы
    public int size() {
        return size;
    }

    class Node {
        Integer key;
        String value;
        Node next; // Для списка
        Node left, right; // Для дерева

        public Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
