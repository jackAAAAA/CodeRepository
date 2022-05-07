package FileOperation;

import java.io.File;

public class Explorer_Func {
    public static void main(String[] args) {
        File f = new File("D:\\CodeRepository\\JAVA_Project\\test\\0421");
//        1.
//        mkdir与mkdirs的区别：前者无法在父目录不存在的情况之下来创建子目录；而后者即使遇到父目录不存在之时也可以指定的完整目录；
//        故创建目录推荐使用mkdirs()，而不是mkdir()。
//        f.mkdir();
        f.mkdirs();
//        2.
//        创建目录用f.mkdirs()；而创建文件用createNewFile()并包裹上try/catch
//        try {
//            f.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(f);
        System.out.println(f.isFile());
        System.out.println(f.isDirectory());
    }
}
