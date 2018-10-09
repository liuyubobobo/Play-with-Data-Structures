/// Leetcode 380. Insert Delete GetRandom O(1)
/// https://leetcode.com/problems/insert-delete-getrandom-o1/description/
///
/// 使用380号问题测试我们的代码
/// 该版本代码用于测试递归实现的TrieRMap

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class RandomizedSet_TrieR {

    private class TrieRMap {

        private class Node{

            public boolean isWord;
            public TreeMap<Character, Node> next;
            public int val;

            public Node(boolean isWord, int val){
                this.isWord = isWord;
                next = new TreeMap<>();
                this.val = val;
            }

            public Node(){
                this(false, -1);
            }
        }

        private Node root;
        private int size;

        public TrieRMap(){
            root = new Node();
            size = 0;
        }

        // 获得Trie中存储的单词数量
        public int getSize(){
            return size;
        }

        // 向Trie中添加一个新的单词word
        public void add(String word, int val){

            add(root, word, 0, val);
        }

        // 向以node为根的Trie中添加word[index...end), 递归算法
        private void add(Node node, String word, int index, int val){

            if(index == word.length()){
                if(!node.isWord){
                    node.isWord = true;
                    size ++;
                }
                node.val = val;
                return;
            }

            char c = word.charAt(index);
            if(node.next.get(c) == null)
                node.next.put(c, new Node());
            add(node.next.get(c), word, index + 1, val);
        }

        // 查询单词word是否在Trie中
        public boolean contains(String word){
            return contains(root, word, 0);
        }

        // 在以node为根的Trie中查询单词word[index...end)是否存在, 递归算法
        private boolean contains(Node node, String word, int index){

            if(index == word.length())
                return node.isWord;

            char c = word.charAt(index);
            if(node.next.get(c) == null)
                return false;

            return contains(node.next.get(c), word, index + 1);
        }

        // 查询单词word在Trie中所对应的值
        public int get(String word){
            return get(root, word, 0);
        }

        // 在以node为根的Trie中查询单词word[index...end)是否存在, 递归算法
        private int get(Node node, String word, int index){

            if(index == word.length())
                return node.val;

            char c = word.charAt(index);
            if(node.next.get(c) == null)
                throw new RuntimeException("Can not get");

            return get(node.next.get(c), word, index + 1);
        }

        // 删除word, 返回是否删除成功, 递归算法
        public boolean remove(String word){
            if(word.equals(""))
                return false;
            return remove(root, word, 0);
        }

        private boolean remove(Node node, String word, int index){

            if(index == word.length()){
                if(!node.isWord)
                    return false;
                node.isWord = false;
                size --;
                return true;
            }

            char c = word.charAt(index);
            if(!node.next.containsKey(c))
                return false;

            boolean ret = remove(node.next.get(c), word, index + 1);
            Node nextNode = node.next.get(c);
            if(!nextNode.isWord && nextNode.next.size() == 0)
                node.next.remove(word.charAt(index));
            return ret;
        }
    }

    TrieRMap map;
    ArrayList<Integer> nums;

    /** Initialize your data structure here. */
    public RandomizedSet_TrieR() {
        map = new TrieRMap();
        nums = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {

        String key = Integer.toString(val);
        if(map.contains(key))
            return false;

        nums.add(val);
        int index = nums.size() - 1;
        map.add(key, index);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {

        String key = Integer.toString(val);
        if(!map.contains(key))
            return false;

        int index = map.get(key);
        map.remove(key);

        int num = nums.get(nums.size() - 1);
        nums.remove(nums.size() - 1);

        if(num != val) {
            nums.set(index, num);
            map.add(Integer.toString(num), index);
        }

        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {

        Random random = new Random();
        int index = random.nextInt(nums.size());
        return nums.get(index);
    }

    public static void main(String[] args){

        RandomizedSet_TrieR rs = new RandomizedSet_TrieR();
        rs.insert(0);
        rs.remove(0);
        rs.insert(-1);
        rs.remove(0);
    }
}
