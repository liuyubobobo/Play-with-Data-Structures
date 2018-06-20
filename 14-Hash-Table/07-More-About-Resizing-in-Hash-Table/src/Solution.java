/// Leetcode 350. Intersection of Two Arrays II
/// https://leetcode.com/problems/intersection-of-two-arrays-ii/description/
///
/// 课程中在这里暂时没有介绍这个问题
/// 该代码主要用于使用Leetcode上的问题测试我们的HashTable类

import java.util.TreeMap;
import java.util.ArrayList;

public class Solution {

    private class HashTable<K extends Comparable<K>, V> {

        private final int[] capacity
                = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
                49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
                12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

        private static final int upperTol = 10;
        private static final int lowerTol = 2;
        private int capacityIndex = 0;

        private TreeMap<K, V>[] hashtable;
        private int size;
        private int M;

        public HashTable(){
            this.M = capacity[capacityIndex];
            size = 0;
            hashtable = new TreeMap[M];
            for(int i = 0 ; i < M ; i ++)
                hashtable[i] = new TreeMap<>();
        }

        private int hash(K key){
            return (key.hashCode() & 0x7fffffff) % M;
        }

        public int getSize(){
            return size;
        }

        public void add(K key, V value){
            TreeMap<K, V> map = hashtable[hash(key)];
            if(map.containsKey(key))
                map.put(key, value);
            else{
                map.put(key, value);
                size ++;

                if(size >= upperTol * M && capacityIndex + 1 < capacity.length){
                    capacityIndex ++;
                    resize(capacity[capacityIndex]);
                }
            }
        }

        public V remove(K key){
            V ret = null;
            TreeMap<K, V> map = hashtable[hash(key)];
            if(map.containsKey(key)){
                ret = map.remove(key);
                size --;

                if(size < lowerTol * M && capacityIndex - 1 >= 0){
                    capacityIndex --;
                    resize(capacity[capacityIndex]);
                }
            }
            return ret;
        }

        public void set(K key, V value){
            TreeMap<K, V> map = hashtable[hash(key)];
            if(!map.containsKey(key))
                throw new IllegalArgumentException(key + " doesn't exist!");

            map.put(key, value);
        }

        public boolean contains(K key){
            return hashtable[hash(key)].containsKey(key);
        }

        public V get(K key){
            return hashtable[hash(key)].get(key);
        }

        private void resize(int newM){
            TreeMap<K, V>[] newHashTable = new TreeMap[newM];
            for(int i = 0 ; i < newM ; i ++)
                newHashTable[i] = new TreeMap<>();

            int oldM = M;
            this.M = newM;
            for(int i = 0 ; i < oldM ; i ++){
                TreeMap<K, V> map = hashtable[i];
                for(K key: map.keySet())
                    newHashTable[hash(key)].put(key, map.get(key));
            }

            this.hashtable = newHashTable;
        }
    }

    public int[] intersect(int[] nums1, int[] nums2) {

        HashTable<Integer, Integer> map = new HashTable<>();
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
