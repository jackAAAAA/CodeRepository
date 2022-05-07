package FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Iterator_File_1218 {
    private static int num0 = 0, num1 = 0;

    public static void main(String[] args) {
        List<String> list = new ArrayList<>() {{add("D:\\CodeRepository\\JAVA_Project");}};
//        Iterate
        Iterator_File_1218 iterator_file_1218 = new Iterator_File_1218();
        long start = System.currentTimeMillis();
        for (String e : list) {
            File file = new File(e);
            iterator_file_1218.si(file);
        }
        System.out.println("-----上迭代--分割线--下递归-----");
//        Recursion
        for (String e : list) {
            File file = new File(e);
            iterator_file_1218.sr(file);
        }
        long end = System.currentTimeMillis();
        System.out.println("迭代遍历文件总数：" + num0);
        System.out.println("递归遍历文件总数" + num1);
        System.out.println("总耗时：" + (end - start));
    }

    private void si(File file) {
        List<File> temp_list = new ArrayList<>();
        sort_File(temp_list, file);
        while (!temp_list.isEmpty()) {
            file = temp_list.remove(0);
            sort_File(temp_list, file);
        }
    }

    private void sort_File(List<File> temp_list, File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File e : files) {
                if (e.isDirectory()) {
                    temp_list.add(e);
                } else {
                    if (e.getName().endsWith("txt")) {
                        System.out.println(e);
                    }
                    ++num0;
                }
            }
        }
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
            ++num1;
        }
    }
}
