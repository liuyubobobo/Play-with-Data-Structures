import java.util.HashSet;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        int a = 42;
        System.out.println(((Integer)a).hashCode());

        double b = 3.1415926;
        System.out.println(((Double)b).hashCode());

        String c = "imooc";
        System.out.println(c.hashCode());

        Student student = new Student(3, 2, "Bobo", "Liu");
        System.out.println(student.hashCode());

        HashSet<Student> set = new HashSet<>();
        set.add(student);

        HashMap<Student, Integer> scores = new HashMap<>();
        scores.put(student, 100);

        // 测试：如果Student类没有hashCode
        Student student2 = new Student(3, 2, "Bobo", "Liu");
        System.out.println(student2.hashCode());
    }
}
