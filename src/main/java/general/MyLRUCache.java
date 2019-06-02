package general;

import java.util.*;

public class MyLRUCache<K, V> {
    final int MAX = 5;
    int size = 0;

    Map<K, Node<K,V>> map = new HashMap<>();
    Node<K,V> head, tail;

    public void put(K key, V val){
        if (map.containsKey(key)){
            final Node<K, V> node = map.get(key);
            node.val = val;
            update(node);
        }

        Node<K,V> node = new Node(key,val);

        if (size < MAX){
            add(node);
        } else {
            removeTail();
            add(node);
        }

    }

    private void removeTail() {
        K key = tail.key;
        Node prev = tail.prev;
        prev.next = null;
        tail = prev;
        map.remove(key);

    }

    private void update(Node<K,V> node) {
        Node prev = node.prev;
        Node next = node.next;
        if (prev == null) return;
        prev.next = next;
        if (next != null) {
            next.prev = prev;
        }
        node.next = head;
        node.prev = null;
        head = node;
        if (tail == node){
            tail = prev;
        }
    }

    private void add(Node<K,V> node) {
        if (size==0){
            head = node;
            tail = node;
            map.put(node.key, node);
            size++;
            return;
        }
        node.next = head;
        head.prev = node;
        head = node;
        size++;
        map.put(node.key, node);
    }

    public V get(K key) {
        Node<K,V> ret = map.get(key);
        update(ret);
        return ret.val;
    }


    private boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        Node<K,V> ptr = head;
        StringBuilder sb = new StringBuilder();
        while (ptr != null){
            sb.append(ptr).append("->");
            ptr = ptr.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MyLRUCache cache = new MyLRUCache();
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "two");
        cache.put(4, "two");
        cache.put(5, "two");
        System.out.println(cache.get(1) == "one"? "OK": "Not OK");
        cache.put(6, "six");
        System.out.println(cache.contains(2) == false ? "OK": "Not OK");

    }

}

class Node<K,V> {
    K key;
    V val;
    Node<K,V> prev;
    Node<K,V> next;

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public String toString() {
        return "(" + key + "," + val+ ")";
    }
}
