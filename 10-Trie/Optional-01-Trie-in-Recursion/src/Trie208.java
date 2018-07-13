import java.util.TreeMap;

/// 使用Leetcode 208号问题测试我们实现的TrieR
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
        add(root, word, 0);
    }

    // 向以node为根的Trie中添加word[index...end), 递归算法
    private void add(Node node, String word, int index){

        if(index == word.length()){
            if(!node.isWord)
                node.isWord = true;
            return;
        }

        char c = word.charAt(index);
        if(node.next.get(c) == null)
            node.next.put(c, new Node());
        add(node.next.get(c), word, index + 1);
    }

    // 查询单词word是否在Trie中
    public boolean search(String word){
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

    // 查询是否在Trie中有单词以prefix为前缀
    public boolean startsWith(String prefix){
        return isPrefix(root, prefix, 0);
    }

    // 查询在以Node为根的Trie中是否有单词以prefix[index...end)为前缀, 递归算法
    private boolean isPrefix(Node node, String prefix, int index){

        if(index == prefix.length())
            return true;

        char c = prefix.charAt(index);
        if(node.next.get(c) == null)
            return false;

        return isPrefix(node.next.get(c), prefix, index + 1);
    }
}
