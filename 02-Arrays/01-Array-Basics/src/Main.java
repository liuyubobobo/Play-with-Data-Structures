public class Main {

    public static void main(String[] args) {

        int[] arr = new int[10];
        for(int i = 0 ; i < 10 ; i ++)
            arr[i] = i;

//        for(int i = 0 ; i < 10 ; i ++)
//            System.out.println(arr[i]);

        for(int i = 0 ; i < arr.length ; i ++)
            System.out.println(arr[i]);

        arr[0] = 100;

        // 如何添加元素？
        // 如何删除元素？
    }
}
