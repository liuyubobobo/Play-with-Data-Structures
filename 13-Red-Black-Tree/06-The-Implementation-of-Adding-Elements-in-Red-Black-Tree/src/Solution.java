import java.util.ArrayList;

public class Solution {

    private class RBTree<K extends Comparable<K>, V> {

        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private class Node{
            public K key;
            public V value;
            public Node left, right;
            public boolean color;

            public Node(K key, V value){
                this.key = key;
                this.value = value;
                left = null;
                right = null;
                color = RED;
            }
        }

        private Node root;
        private int size;

        public RBTree(){
            root = null;
            size = 0;
        }

        public int getSize(){
            return size;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        // 判断节点node的颜色
        private boolean isRed(Node node){
            if(node == null)
                return BLACK;
            return node.color;
        }

        //   node                     x
        //  /   \     左旋转         /  \
        // T1   x   --------->   node   T3
        //     / \              /   \
        //    T2 T3            T1   T2
        private Node leftRotate(Node node){

            Node x = node.right;

            // 左旋转
            node.right = x.left;
            x.left = node;

            x.color = node.color;
            node.color = RED;

            return x;
        }

        //     node                   x
        //    /   \     右旋转       /  \
        //   x    T2   ------->   y   node
        //  / \                       /  \
        // y  T1                     T1  T2
        private Node rightRotate(Node node){

            Node x = node.left;

            // 右旋转
            node.left = x.right;
            x.right = node;

            x.color = node.color;
            node.color = RED;

            return x;
        }

        // 颜色翻转
        private void flipColors(Node node){

            node.color = RED;
            node.left.color = BLACK;
            node.right.color = BLACK;
        }

        // 向二分搜索树中添加新的元素(key, value)
        public void add(K key, V value){
            root = add(root, key, value);
            root.color = BLACK; // 最终根节点为黑色节点
        }

        // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
        // 返回插入新节点后二分搜索树的根
        private Node add(Node node, K key, V value){

            if(node == null){
                size ++;
                return new Node(key, value); // 默认插入红色节点
            }

            if(key.compareTo(node.key) < 0)
                node.left = add(node.left, key, value);
            else if(key.compareTo(node.key) > 0)
                node.right = add(node.right, key, value);
            else // key.compareTo(node.key) == 0
                node.value = value;

            if (isRed(node.right) && !isRed(node.left))
                node = leftRotate(node);

            if (isRed(node.left) && isRed(node.left.left))
                node = rightRotate(node);

            if (isRed(node.left) && isRed(node.right))
                flipColors(node);

            return node;
        }

        // 返回以node为根节点的二分搜索树中，key所在的节点
        private Node getNode(Node node, K key){

            if(node == null)
                return null;

            if(key.equals(node.key))
                return node;
            else if(key.compareTo(node.key) < 0)
                return getNode(node.left, key);
            else // if(key.compareTo(node.key) > 0)
                return getNode(node.right, key);
        }

        public boolean contains(K key){
            return getNode(root, key) != null;
        }

        public V get(K key){

            Node node = getNode(root, key);
            return node == null ? null : node.value;
        }

        public void set(K key, V newValue){
            Node node = getNode(root, key);
            if(node == null)
                throw new IllegalArgumentException(key + " doesn't exist!");

            node.value = newValue;
        }

        // 从二分搜索树中删除键为key的节点
        public V remove(K key){
            throw new UnsupportedOperationException("No remove in RBTree!");
        }
    }

    public int uniqueMorseRepresentations(String[] words) {

        String[] codes = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        RBTree<String, Object> set = new RBTree<>();
        for(String word: words){
            StringBuilder res = new StringBuilder();
            for(int i = 0 ; i < word.length() ; i ++)
                res.append(codes[word.charAt(i) - 'a']);

            set.add(res.toString(), null);
        }

        return set.getSize();
    }
}
