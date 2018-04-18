/// Leetcode 350. Intersection of Two Arrays II
/// https://leetcode.com/problems/intersection-of-two-arrays-ii/description/

import java.util.ArrayList;

public class LinkedListMapSolution350 {

    private interface Map<K, V> {

        void add(K key, V value);
        boolean contains(K key);
        V get(K key);
        void set(K key, V newValue);
        V remove(K key);
        int getSize();
        boolean isEmpty();
    }

    private class LinkedListMap<K, V> implements Map<K, V> {

        private class Node{
            public K key;
            public V value;
            public Node next;

            public Node(K key, V value, Node next){
                this.key = key;
                this.value = value;
                this.next = next;
            }

            public Node(K key, V value){
                this(key, value, null);
            }

            public Node(){
                this(null, null, null);
            }

            @Override
            public String toString(){
                return key.toString() + " : " + value.toString();
            }
        }

        private Node dummyHead;
        private int size;

        public LinkedListMap(){
            dummyHead = new Node();
            size = 0;
        }

        @Override
        public int getSize(){
            return size;
        }

        @Override
        public boolean isEmpty(){
            return size == 0;
        }

        private Node getNode(K key){
            Node cur = dummyHead.next;
            while(cur != null){
                if(cur.key.equals(key))
                    return cur;
                cur = cur.next;
            }
            return null;
        }

        @Override
        public boolean contains(K key){
            return getNode(key) != null;
        }

        @Override
        public V get(K key){
            Node node = getNode(key);
            return node == null ? null : node.value;
        }

        @Override
        public void add(K key, V value){
            Node node = getNode(key);
            if(node == null){
                dummyHead.next = new Node(key, value, dummyHead.next);
                size ++;
            }
            else
                node.value = value;
        }

        @Override
        public void set(K key, V newValue){
            Node node = getNode(key);
            if(node == null)
                throw new IllegalArgumentException(key + " doesn't exist!");

            node.value = newValue;
        }

        @Override
        public V remove(K key){

            Node prev = dummyHead;
            while(prev.next != null){
                if(prev.next.key.equals(key))
                    break;
                prev = prev.next;
            }

            if(prev.next != null){
                Node delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;
                size --;
                return delNode.value;
            }

            return null;
        }
    }

    public int[] intersect(int[] nums1, int[] nums2) {

        LinkedListMap<Integer, Integer> map = new LinkedListMap<>();
        for(int num: nums1){
            if(!map.contains(num))
                map.add(num, 1);
            else
                map.set(num, map.get(num) + 1);
        }

        ArrayList<Integer> res = new ArrayList<>();
        for(int num: nums2){
            if(map.contains(num)){
                res.add(num);
                map.set(num, map.get(num) - 1);
                if(map.get(num) == 0)
                    map.remove(num);
            }
        }

        int[] ret = new int[res.size()];
        for(int i = 0 ; i < res.size() ; i ++)
            ret[i] = res.get(i);

        return ret;
    }
}
