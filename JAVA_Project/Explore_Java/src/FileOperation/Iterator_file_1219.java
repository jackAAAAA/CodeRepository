package FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Iterator_file_1219 {
    private static int num0 = 0, num1 = 0;

    public static void main(String[] args) {
        Iterator_file_1219 holder = new Iterator_file_1219();
        List<String> list = new ArrayList<>() {{
            add("D:\\CodeRepository\\JAVA_Project");}};
        long start = System.currentTimeMillis();
//        Recursion
        for (String e : list) {
            File file = new File(e);
            holder.sr(file);
        }
        System.out.println("-------上递归，下迭代---------");
//        Iterator
        for (String e : list) {
            File file = new File(e);
            holder.si(file);
        }
        long end = System.currentTimeMillis();
        System.out.println("递归 & 迭代遍历文件的总耗时：" + (end - start));
        System.out.println("递归遍历的文件总数：" + num0);
        System.out.println("迭代遍历的文件总数：" + num1);
    }

    private void sr(File file) {
        if (!file.canRead()) {
            return;
        }
        if (file.isDirectory()) {
            String[] files = file.list();
            if (files != null) {
                for (String e : files) {
                    sr(new File(file, e));
                }
            }
        } else {
            if (file.getName().endsWith("txt")) {
                System.out.println(file);
            }
            ++num0;
        }
    }

    private void si(File file) {
        List<File> temp_list = new ArrayList<>();
        sort_file(temp_list, file);
        while (!temp_list.isEmpty()) {
            file = temp_list.remove(0);
            sort_file(temp_list, file);
        }
    }

    private void sort_file(List<File> list, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File e : files) {
                if (e.isDirectory()) {
                    list.add(e);
                } else {
                    if (e.getName().endsWith("txt")) {
                        System.out.println(e);
                    }
                    ++num1;
                }
            }
        }
    }
}
