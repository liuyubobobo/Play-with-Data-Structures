import java.util.Random;

public class Main {

    private static double testUF(UF uf, int m){

        int size = uf.getSize();
        Random random = new Random();

        long startTime = System.nanoTime();


        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for(int i = 0 ; i < m ; i ++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        return time;
    }

    public static void main(String[] args) {

        int size = 100000;
        int m = 100000;
        double time;

        UnionFind1 uf1 = new UnionFind1(size);
        time = testUF(uf1, m);
        System.out.println("UnionFind1 : " + time + " s");

        UnionFind2 uf2 = new UnionFind2(size);
        time = testUF(uf2, m);
        System.out.println("UnionFind2 : " + time + " s");

        UnionFind3 uf3 = new UnionFind3(size);
        time = testUF(uf3, m);
        System.out.println("UnionFind3 : " + time + " s");

        UnionFind4 uf4 = new UnionFind4(size);
        time = testUF(uf4, m);
        System.out.println("UnionFind4 : " + time + " s");
    }
}
