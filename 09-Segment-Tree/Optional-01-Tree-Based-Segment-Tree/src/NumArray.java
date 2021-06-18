/// Tree-Based Segment Tree
/// Leetcode 307

import java.util.Arrays;

class NumArray {

    private class SegmentTree{

        private class Node{
            private int v, l, r;
            private Node left, right;
            private Node(int v, int l, int r){
                this.v = v;
                this.l = l;
                this.r = r;
                this.left = null;
                this.right = null;
            }
        }

        private int[] data;
        private Node root;

        public SegmentTree(int[] arr){

            data = Arrays.copyOf(arr, arr.length);
            root = buildSegmentTree(0, arr.length - 1);
        }

        private Node buildSegmentTree(int l, int r){

            if(l == r) return new Node(data[l], l, r);

            int mid = (l + r) / 2;
            Node leftnode = buildSegmentTree(l, mid);
            Node rightnode = buildSegmentTree(mid + 1, r);

            Node node = new Node(leftnode.v + rightnode.v, l, r);
            node.left = leftnode;
            node.right = rightnode;
            return node;
        }

        public int query(int l, int r){
            return query(root, l, r);
        }

        private int query(Node node, int l, int r){

            if(l == node.l && r == node.r) return node.v;

            int mid = (node.l + node.r) / 2;

            if(l >= mid + 1) return query(node.right, l, r);
            if(r <= mid) return query(node.left, l, r);

            return query(node.left, l, mid) + query(node.right, mid + 1, r);
        }

        public void set(int index, int value){

            data[index] = value;
            set(root, index, value);
        }

        private void set(Node node, int index, int value){

            if(node.l == node.r){
                node.v = value;
                return;
            }

            int mid = (node.l + node.r) / 2;
            if(index <= mid) set(node.left, index, value);
            else set(node.right, index, value);

            node.v = node.left.v + node.right.v;
        }
    }

    private SegmentTree tree;

    public NumArray(int[] nums) {
        tree = new SegmentTree(nums);
    }

    public void update(int index, int val) {
        tree.set(index, val);
    }

    public int sumRange(int left, int right) {
        return tree.query(left, right);
    }

    public static void main(String[] args) {

        int[] nums = new int[]{1, 3, 5};
        NumArray o = new NumArray(nums);

        System.out.println(o.sumRange(0, 2));
        // 9

        o.update(1, 2);
        System.out.println(o.sumRange(0, 2));
        // 8
    }
}