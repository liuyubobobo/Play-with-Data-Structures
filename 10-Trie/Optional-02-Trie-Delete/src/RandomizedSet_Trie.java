/// Leetcode 380. Insert Delete GetRandom O(1)
/// https://leetcode.com/problems/insert-delete-getrandom-o1/description/
///
/// 使用380号问题测试我们的代码
/// 该版本代码用于测试非递归实现的TrieMap

import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Random;

public class RandomizedSet_Trie {

    private class TrieMap {

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

        public TrieMap(){
            root = new Node();
            size = 0;
        }

        // 获得Trie中存储的单词数量
        public int getSize(){
            return size;
        }

        // 向Trie中添加一个新的单词word
        public void add(String word, int val){

            Node cur = root;
            for(int i = 0 ; i < word.length() ; i ++){
                char c = word.charAt(i);
                if(cur.next.get(c) == null)
                    cur.next.put(c, new Node());
                cur = cur.next.get(c);
            }

            if(!cur.isWord){
                cur.isWord = true;
                size ++;
            }
            cur.val = val;
        }

        // 查询单词word是否在Trie中
        public boolean contains(String word){

            Node cur = root;
            for(int i = 0 ; i < word.length() ; i ++){
                char c = word.charAt(i);
                if(cur.next.get(c) == null)
                    return false;
                cur = cur.next.get(c);
            }
            return cur.isWord;
        }

        // 查询单词word所对应的值
        public int get(String word){

            Node cur = root;
            for(int i = 0 ; i < word.length() ; i ++){
                char c = word.charAt(i);
                if(cur.next.get(c) == null)
                    throw new RuntimeException("Can not get");
                cur = cur.next.get(c);
            }
            return cur.val;
        }

        // 删除word, 返回是否删除成功
        public boolean remove(String word){

            Stack<Node> stack = new Stack<>();
            stack.push(root);
            for(int i = 0; i < word.length(); i ++){
                if(!stack.peek().next.containsKey(word.charAt(i)))
                    return false;
                stack.push(stack.peek().next.get(word.charAt(i)));
            }

            if(!stack.peek().isWord)
                return false;

            // 将该单词结尾isWord置空
            stack.peek().isWord = false;
            size --;

            // 如果单词最后一个字母的节点的next非空，
            // 说明trie中还存储了其他以该单词为前缀的单词，直接返回
            if(stack.peek().next.size() > 0)
                return true;
            else
                stack.pop();

            // 自底向上删除
            for(int i = word.length() - 1; i >= 0; i --){
                stack.peek().next.remove(word.charAt(i));
                // 如果上一个节点的isWord为true，或者是其他单词的前缀，则直接返回
                if(stack.peek().isWord || stack.peek().next.size() > 0)
                    return true;
            }
            return true;
        }
    }

    TrieMap map;
    ArrayList<Integer> nums;

    /** Initialize your data structure here. */
    public RandomizedSet_Trie() {
        map = new TrieMap();
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
