/// Leetcode 547. Friend Circles
/// https://leetcode.com/problems/friend-circles/description/
///
/// 课程中在这里暂时没有介绍这个问题
/// 该代码主要用于使用Leetcode上的问题测试我们的UF类
import java.util.TreeSet;

class Solution {

    private interface UF {
        int getSize();
        boolean isConnected(int p, int q);
        void unionElements(int p, int q);
    }

    // 我们的第二版Union-Find
    private class UnionFind2 implements UF {

        // 我们的第二版Union-Find, 使用一个数组构建一棵指向父节点的树
        // parent[i]表示第一个元素所指向的父节点
        private int[] parent;

        // 构造函数
        public UnionFind2(int size){

            parent = new int[size];

            // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
            for( int i = 0 ; i < size ; i ++ )
                parent[i] = i;
        }

        @Override
        public int getSize(){
            return parent.length;
        }

        // 查找过程, 查找元素p所对应的集合编号
        // O(h)复杂度, h为树的高度
        private int find(int p){
            if(p < 0 || p >= parent.length)
                throw new IllegalArgumentException("p is out of bound.");

            // 不断去查询自己的父亲节点, 直到到达根节点
            // 根节点的特点: parent[p] == p
            while(p != parent[p])
                p = parent[p];
            return p;
        }

        // 查看元素p和元素q是否所属一个集合
        // O(h)复杂度, h为树的高度
        @Override
        public boolean isConnected( int p , int q ){
            return find(p) == find(q);
        }

        // 合并元素p和元素q所属的集合
        // O(h)复杂度, h为树的高度
        @Override
        public void unionElements(int p, int q){

            int pRoot = find(p);
            int qRoot = find(q);

            if( pRoot == qRoot )
                return;

            parent[pRoot] = qRoot;
        }
    }

    public int findCircleNum(int[][] M) {

        int n = M.length;
        UnionFind2 uf = new UnionFind2(n);
        for(int i = 0 ; i < n ; i ++)
            for(int j = 0 ; j < i ; j ++)
                if(M[i][j] == 1)
                    uf.unionElements(i, j);

        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0 ; i < n ; i ++)
            set.add(uf.find(i));
        return set.size();
    }
}
