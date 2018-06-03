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

    // 我们的第一版Union-Find
    private class UnionFind1 implements UF {

        private int[] id;    // 我们的第一版Union-Find本质就是一个数组

        public UnionFind1(int size) {

            id = new int[size];

            // 初始化, 每一个id[i]指向自己, 没有合并的元素
            for (int i = 0; i < size; i++)
                id[i] = i;
        }

        @Override
        public int getSize(){
            return id.length;
        }

        // 查找元素p所对应的集合编号
        // O(1)复杂度
        private int find(int p) {
            if(p < 0 || p >= id.length)
                throw new IllegalArgumentException("p is out of bound.");

            return id[p];
        }

        // 查看元素p和元素q是否所属一个集合
        // O(1)复杂度
        @Override
        public boolean isConnected(int p, int q) {
            return find(p) == find(q);
        }

        // 合并元素p和元素q所属的集合
        // O(n) 复杂度
        @Override
        public void unionElements(int p, int q) {

            int pID = find(p);
            int qID = find(q);

            if (pID == qID)
                return;

            // 合并过程需要遍历一遍所有元素, 将两个元素的所属集合编号合并
            for (int i = 0; i < id.length; i++)
                if (id[i] == pID)
                    id[i] = qID;
        }
    }

    public int findCircleNum(int[][] M) {

        int n = M.length;
        UnionFind1 uf = new UnionFind1(n);
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
