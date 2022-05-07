package FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Iterator_File {

    public static int num0 = 0;
    public static int num1 = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Iterator_File holder = new Iterator_File();
//        List<String> list = new ArrayList<String>("D:\\CodeRepository\\JAVA_Project");
//        初始化动态数组的赋值标准写法
        List<String> list = new ArrayList<>() {{add("D:\\CodeRepository\\JAVA_Project");}};
//        String[] list = {"D:\\CodeRepository\\JAVA_Project"};

//        no_recursion
        for (String e : list) {
            File file = new File(e);
            holder.si(file);
        }

        System.out.println("--------------------");

//        recursion
        for (String e : list) {
            File file = new File(e);
            holder.sr1(file);
        }

        long end = System.currentTimeMillis();
        System.out.println("非递归 & 递归遍历文件的总耗时：" + (end - start));

        System.out.println("非递归遍历到的文件总数：" + num0);
        System.out.println("递归遍历到的文件总数：" + num1);
    }

    private void si(File file) {
        LinkedList<File> list = new LinkedList<>();
        file_sort(list, file);
        while (!list.isEmpty()) {
            File temp = list.removeFirst();
            file_sort(list, temp);
        }
    }

    private void file_sort(LinkedList<File> list, File temp) {
        File[] temp_files = temp.listFiles();
        if (temp_files != null) {
            for (File e : temp_files) {
                if (e.isDirectory()) {
                    list.add(e);
                } else {
                    if (e.getName().endsWith("txt")) {
                        System.out.println(e);
                    }
                    ++num0;
                }
            }
        }
    }

    //非递归
    public static void scanDirNoRecursion(File dir) {
//        文件目录列表：list
        LinkedList<File> list = new LinkedList<>();
//        File dir = new File(path);
//        listFiles()的含义是：返回指定文件夹（文件目录）中包含的子文件夹与文件的定长File[]
        File[] file = dir.listFiles();
//        System.out.println("指定的根目录中的文件总数：" + file == null ? 0 : file.length);
        if (file != null) {
            for (File item : file) {
//            目录与文件的区别：前者就是windows下的文件夹；后者指的是文件夹中包含的一个文件，比如以".txt"等结尾的文件
//            目录中可以包含子目录，也可以包含某个具体的文件；且一个文件目录只能包含这两种东西
                if (item.isDirectory()) {
                    list.add(item);
                } else {
                    if (item.getName().endsWith("txt")) {
                        System.out.println(item.getAbsolutePath());
                    }
                    num0++;
                }
            }
        }
//        输出子目录的数量
//        System.out.println("指定的根目录中的子目录数量_list：" + list.size());

        while (!list.isEmpty()) {
            File tmp = list.removeFirst();//首个目录；即指定的根目录中的首个文件目录
            file = tmp.listFiles();
            if (file == null)
                continue;
            for (File value : file) {
                if (value.isDirectory())
                    list.add(value);//目录则加入目录列表，关键；即如果是文件子目录则添加进文件目录列表
                else { //否则是某个具体的文件
                    if (value.getName().endsWith("txt")) {
                        System.out.println(value);
                    }
                    num0++;
                }
            }
        }
    }

    private void sr1(File file) {
        if (!file.canRead()) {
            return;
        }
        if (file.isDirectory()) {
            String[] files = file.list();
            if (files != null) {
                for (String e : files) {
                    sr1(new File(file, e));
                }
            }
        } else {
            if (file.getName().endsWith("txt")) {
                System.out.println(file);
            }
            ++num1;
        }
    }

    //递归
    public static void scanDirRecursion(File file) {
        if (!file.canRead()) {
            return;
        }
        if (file.isDirectory()) {
//                    file.list(): 返回的是文件目录file中包含的文件目录 & 文件的集合
            String[] files = file.list();
            if (files != null) {
                for (String s : files) {
//                            new File(file, files[i]): 表示的是将父文件目录的路径与其中所包含的文件的文件名拼接成该子文件的绝对路径
                    scanDirRecursion(new File(file, s));
                }
            }
        } else {
//                    以".txt"结尾的文件，则输出其完整的绝对路径 & 文件名称
            if (file.getName().endsWith("txt")) {
                System.out.println(file);
            }
            num1++;
        }
    }
}
