/// 208. Implement Trie (Prefix Tree)
/// https://leetcode.com/problems/implement-trie-prefix-tree/description/

import java.util.TreeMap;

public class Trie208 {

    private class Node{

        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }

    private Node root;

    public Trie208(){
        root = new Node();
    }

    // 向Trie中添加一个新的单词word
    public void insert(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }
        cur.isWord = true;
    }

    // 查询单词word是否在Trie中
    public boolean search(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    // 查询是否在Trie中有单词以prefix为前缀
    public boolean startsWith(String isPrefix){

        Node cur = root;
        for(int i = 0 ; i < isPrefix.length() ; i ++){
            char c = isPrefix.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }

        return true;
    }
}
