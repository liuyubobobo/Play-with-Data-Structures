/// Leetcode 350. Intersection of Two Arrays II
/// https://leetcode.com/problems/intersection-of-two-arrays-ii/description/
///
/// 课程中在这里暂时没有介绍这个问题
/// 该代码主要用于使用Leetcode上的问题测试我们的BSTMap类

import java.util.TreeMap;
import java.util.ArrayList;

public class Solution {

    private class HashTable<K, V> {

        private TreeMap<K, V>[] hashtable;
        private int size;
        private int M;

        public HashTable(int M){
            this.M = M;
            size = 0;
            hashtable = new TreeMap[M];
            for(int i = 0 ; i < M ; i ++)
                hashtable[i] = new TreeMap<>();
        }

        public HashTable(){
            this(97);
        }

        private int hash(K key){
            return (key.hashCode() & 0x7fffffff) % M;
        }

        public void add(K key, V value){
            TreeMap<K, V> map = hashtable[hash(key)];
            // if(!hashtable[hash(key)].containsKey(key)){
            if(!map.containsKey(key)){
                map.put(key, value);
                size ++;
            }
        }

        public V remove(K key){
            V ret = null;
            TreeMap<K, V> map = hashtable[hash(key)];
            if(map.containsKey(key)){
                ret = map.remove(key);
                size --;
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
