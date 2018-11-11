import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/// Leetcode 102. Binary Tree Level Order Traversal
/// https://leetcode.com/problems/binary-tree-level-order-traversal/description/
/// 二叉树的层序遍历
///
/// 二叉树的层序遍历是一个典型的可以借助队列解决的问题。
/// 该代码主要用于使用Leetcode上的问题测试我们的LoopQueue。
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

    private interface Queue<E> {

        int getSize();
        boolean isEmpty();
        void enqueue(E e);
        E dequeue();
        E getFront();
    }

    // 在这一版LoopQueue的实现中，我们将不浪费那1个空间：）
    public class LoopQueue<E> implements Queue<E> {

        private E[] data;
        private int front, tail;
        private int size;

        public LoopQueue(int capacity){
            data = (E[])new Object[capacity]; // 由于不浪费空间，所以data静态数组的大小是capacity
            // 而不是capacity + 1
            front = 0;
            tail = 0;
            size = 0;
        }

        public LoopQueue(){
            this(10);
        }

        public int getCapacity(){
            return data.length;
        }

        @Override
        public boolean isEmpty(){
            // 注意，我们不再使用front和tail之间的关系来判断队列是否为空，而直接使用size
            return size == 0;
        }

        @Override
        public int getSize(){
            return size;
        }

        @Override
        public void enqueue(E e){

            // 注意，我们不再使用front和tail之间的关系来判断队列是否为满，而直接使用size
            if(size == getCapacity())
                resize(getCapacity() * 2);

            data[tail] = e;
            tail = (tail + 1) % data.length;
            size ++;
        }

        @Override
        public E dequeue(){

            if(isEmpty())
                throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

            E ret = data[front];
            data[front] = null;
            front = (front + 1) % data.length;
            size --;
            if(size == getCapacity() / 4 && getCapacity() / 2 != 0)
                resize(getCapacity() / 2);
            return ret;
        }

        @Override
        public E getFront(){
            if(isEmpty())
                throw new IllegalArgumentException("Queue is empty.");
            return data[front];
        }

        private void resize(int newCapacity){

            E[] newData = (E[])new Object[newCapacity];
            for(int i = 0 ; i < size ; i ++)
                newData[i] = data[(i + front) % data.length];

            data = newData;
            front = 0;
            tail = size;
        }

        @Override
        public String toString(){

            StringBuilder res = new StringBuilder();
            res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
            res.append("front [");

            // 注意，我们的循环遍历打印队列的逻辑也有相应的更改 :-)
            for(int i = 0; i < size; i ++){
                res.append(data[(front + i) % data.length]);
                if((i + front + 1) % data.length != tail)
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
        LoopQueue<Pair<TreeNode, Integer>> queue = new LoopQueue<Pair<TreeNode, Integer>>();
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