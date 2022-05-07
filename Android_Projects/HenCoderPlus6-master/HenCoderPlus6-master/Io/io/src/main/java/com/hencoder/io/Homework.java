package com.hencoder.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class Homework {
    public static void main(String[] args) {
//        Input
//        Input1();
//        Input2();
//        InputOkio();

//        output
//        Output1();
//        Output2();

//        copy
//        copyFile();
//        copyFileOkio();
        //    在根目录下创建testDir文件夹 & 创建此txt文件
//        new File("./io/testDir/JustForTest.txt");
//        try {
//            File file = new File("./io/JustForTest.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (file.exists()) {
//            System.out.println("test");
//        }
//            new FileInputStream("./io/testDir/JustForTest.txt");
//            new FileInputStream("./io/JustForTest.txt");
//        try (InputStream inputStream = new FileInputStream("./io/test_text.txt")) {
//            System.out.println((char) inputStream.read());
//            System.out.println((char) inputStream.read());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
//            FileOutputStream: 若该目录下的文件不存在，则在该目录创建真实的文件，但不会创建真实的目录
//            File: 若该目录下的文件不存在，不能创建真实的文件，仅当该文件存在时会创建该文件的对象；也不会创建真实的目录
            new FileOutputStream("./testdir/test_text.txt");
            new FileOutputStream("./app/test_text.txt");
            new File("./testdir/test_text.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

//    CopyFile by traditional I/O or Okio
    private static void copyFileOkio() {
        try (BufferedSource source = Okio.buffer(Okio.source(new File("./io/test_text.txt")));
//             Java创建文件某个路径下的文件TO DO SUM
             BufferedSink sink = Okio.buffer(Okio.sink(new File("./io/new_Okio_test_text.txt")))) {
            byte[] data = new byte[1024];
            int read = 0;
            /**
             * Removes up to {@code data.length} bytes from source and copies them into {@code data}. Returns
             * the number of bytes read, or -1 if this source is exhausted.
             */
            while ((read = source.read(data)) != -1) {
                sink.write(data, 0, read);
            }
            System.out.println(source.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile() {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream("./io/test_text.txt"));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("./io/new_test_text.txt"))) {
            byte[] data = new byte[1024];
            int read = 0;
//            1.如果一次读不完，分批次来读，并且后读的数据附在第一次读入的数据之后
//            2.而不是后面读的数据覆盖前面已读入的数据
//            3.outputStream.write(data, 0, read): 指的是将范围从data[0] -> data[0 + read]的数据写入outputStream
//            4.inputStream.read(data)：指的是将inputStream中的数据写入到data中/data数组读入inputStream中的数据
            while ((read = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //        output: Output data from memory

    private static void Output2() {
        try (OutputStream outputStream = new FileOutputStream("./io/test_text.txt");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream((outputStream))) {
            for (int i = 0; i < 1023; ++i) {
                bufferedOutputStream.write('c');
            }
            bufferedOutputStream.write('d');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void Output1() {
        try (OutputStream outputStream = new FileOutputStream("./io/test_text.txt")) {
            outputStream.write('a');
            outputStream.write('b');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        Input: Input data to memory
    private static void InputOkio() {
        try (BufferedSource source = Okio.buffer(Okio.source(new File("./io/test_text.txt")))) {
            System.out.println(source.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Input2() {
        try (InputStream inputStream = new FileInputStream("./io/test_text.txt");
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Input1() {
        try (InputStream inputStream = new FileInputStream("./io/test_text.txt")) {
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
