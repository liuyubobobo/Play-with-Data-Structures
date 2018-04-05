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

    private class LoopQueue<E> implements Queue<E> {

        private E[] data;
        private int front, tail;
        private int size;  // 有兴趣的同学，在完成这一章后，可以思考一下：
        // LoopQueue中不声明size，如何完成所有的逻辑？
        // 这个问题可能会比大家想象的要难一点点：）

        public LoopQueue(int capacity){
            data = (E[])new Object[capacity + 1];
            front = 0;
            tail = 0;
            size = 0;
        }

        public LoopQueue(){
            this(10);
        }

        public int getCapacity(){
            return data.length - 1;
        }

        @Override
        public boolean isEmpty(){
            return front == tail;
        }

        @Override
        public int getSize(){
            return size;
        }

        @Override
        public void enqueue(E e){

            if((tail + 1) % data.length == front)
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

            E[] newData = (E[])new Object[newCapacity + 1];
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
            for(int i = front ; i != tail ; i = (i + 1) % data.length){
                res.append(data[i]);
                if((i + 1) % data.length != tail)
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