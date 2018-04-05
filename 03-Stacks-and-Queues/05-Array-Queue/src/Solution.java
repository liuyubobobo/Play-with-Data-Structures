import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/// Leetcode 102. Binary Tree Level Order Traversal
/// https://leetcode.com/problems/binary-tree-level-order-traversal/description/
/// 二叉树的层序遍历
///
/// 二叉树的层序遍历是一个典型的可以借助队列解决的问题。
/// 该代码主要用于使用Leetcode上的问题测试我们的ArrayQueue。
/// 对于二叉树的层序遍历，这个课程后续会讲到。
/// 届时，同学们也可以再回头看这个代码。
/// 不过到时，大家应该已经学会自己编写二叉树的层序遍历啦：）

class Solution {

    /// Definition for a binary tree node.
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private class Array<E> {

        private E[] data;
        private int size;

        // 构造函数，传入数组的容量capacity构造Array
        public Array(int capacity){
            data = (E[])new Object[capacity];
            size = 0;
        }

        // 无参数的构造函数，默认数组的容量capacity=10
        public Array(){
            this(10);
        }

        // 获取数组的容量
        public int getCapacity(){
            return data.length;
        }

        // 获取数组中的元素个数
        public int getSize(){
            return size;
        }

        // 返回数组是否为空
        public boolean isEmpty(){
            return size == 0;
        }

        // 在index索引的位置插入一个新元素e
        public void add(int index, E e){

            if(index < 0 || index > size)
                throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");

            if(size == data.length)
                resize(2 * data.length);

            for(int i = size - 1; i >= index ; i --)
                data[i + 1] = data[i];

            data[index] = e;

            size ++;
        }

        // 向所有元素后添加一个新元素
        public void addLast(E e){
            add(size, e);
        }

        // 在所有元素前添加一个新元素
        public void addFirst(E e){
            add(0, e);
        }

        // 获取index索引位置的元素
        public E get(int index){
            if(index < 0 || index >= size)
                throw new IllegalArgumentException("Get failed. Index is illegal.");
            return data[index];
        }

        public E getLast(){
            return get(size - 1);
        }

        public E getFirst(){
            return get(0);
        }

        // 修改index索引位置的元素为e
        public void set(int index, E e){
            if(index < 0 || index >= size)
                throw new IllegalArgumentException("Set failed. Index is illegal.");
            data[index] = e;
        }

        // 查找数组中是否有元素e
        public boolean contains(E e){
            for(int i = 0 ; i < size ; i ++){
                if(data[i].equals(e))
                    return true;
            }
            return false;
        }

        // 查找数组中元素e所在的索引，如果不存在元素e，则返回-1
        public int find(E e){
            for(int i = 0 ; i < size ; i ++){
                if(data[i].equals(e))
                    return i;
            }
            return -1;
        }

        // 从数组中删除index位置的元素, 返回删除的元素
        public E remove(int index){
            if(index < 0 || index >= size)
                throw new IllegalArgumentException("Remove failed. Index is illegal.");

            E ret = data[index];
            for(int i = index + 1 ; i < size ; i ++)
                data[i - 1] = data[i];
            size --;
            data[size] = null; // loitering objects != memory leak

            if(size == data.length / 4 && data.length / 2 != 0)
                resize(data.length / 2);
            return ret;
        }

        // 从数组中删除第一个元素, 返回删除的元素
        public E removeFirst(){
            return remove(0);
        }

        // 从数组中删除最后一个元素, 返回删除的元素
        public E removeLast(){
            return remove(size - 1);
        }

        // 从数组中删除元素e
        public void removeElement(E e){
            int index = find(e);
            if(index != -1)
                remove(index);
        }

        @Override
        public String toString(){

            StringBuilder res = new StringBuilder();
            res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
            res.append('[');
            for(int i = 0 ; i < size ; i ++){
                res.append(data[i]);
                if(i != size - 1)
                    res.append(", ");
            }
            res.append(']');
            return res.toString();
        }

        // 将数组空间的容量变成newCapacity大小
        private void resize(int newCapacity){

            E[] newData = (E[])new Object[newCapacity];
            for(int i = 0 ; i < size ; i ++)
                newData[i] = data[i];
            data = newData;
        }
    }

    private interface Queue<E> {

        int getSize();
        boolean isEmpty();
        void enqueue(E e);
        E dequeue();
        E getFront();
    }

    private class ArrayQueue<E> implements Queue<E> {

        private Array<E> array;

        public ArrayQueue(int capacity){
            array = new Array<>(capacity);
        }

        public ArrayQueue(){
            array = new Array<>();
        }

        @Override
        public int getSize(){
            return array.getSize();
        }

        @Override
        public boolean isEmpty(){
            return array.isEmpty();
        }

        public int getCapacity(){
            return array.getCapacity();
        }

        @Override
        public void enqueue(E e){
            array.addLast(e);
        }

        @Override
        public E dequeue(){
            return array.removeFirst();
        }

        @Override
        public E getFront(){
            return array.getFirst();
        }

        @Override
        public String toString(){
            StringBuilder res = new StringBuilder();
            res.append("Queue: ");
            res.append("front [");
            for(int i = 0 ; i < array.getSize() ; i ++){
                res.append(array.get(i));
                if(i != array.getSize() - 1)
                    res.append(", ");
            }
            res.append("] tail");
            return res.toString();
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {

        ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
        if(root == null)
            return res;

        // 我们使用LinkedList来做为我们的先入先出的队列
        ArrayQueue<Pair<TreeNode, Integer>> queue = new ArrayQueue<Pair<TreeNode, Integer>>();
        queue.enqueue(new Pair<TreeNode, Integer>(root, 0));

        while(!queue.isEmpty()){

            Pair<TreeNode, Integer> front = queue.dequeue();
            TreeNode node = front.getKey();
            int level = front.getValue();

            if(level == res.size())
                res.add(new ArrayList<Integer>());
            assert level < res.size();

            res.get(level).add(node.val);
            if(node.left != null)
                queue.enqueue(new Pair<TreeNode, Integer>(node.left, level + 1));
            if(node.right != null)
                queue.enqueue(new Pair<TreeNode, Integer>(node.right, level + 1));
        }

        return res;
    }
}