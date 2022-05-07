package com.hencoder.pluginable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    TO DO SUM
//    Android创建文件
//    在指定的缓存目录中创建一个后缀为.apk文件对象，还需.write()函数来生成真实的文件
    File apk = new File(getCacheDir() + "/new_plugin.apk");
//    File apk1 = new File("/data/user/0/com.hencoder.pluginable/plugin-test.apk");
//    File apk = new File(getCacheDir() + "/Create_test.apk");
//    File apk = new File(getCacheDir() + "/new_new_test.apk");
//    wrong——System.err: java.io.FileNotFoundException: ./new_new_test.apk: open failed: EROFS (Read-only file system)
//    File apk = new File("./new_new_test.apk");
//    try (Source source = Okio.source(getAssets().open("apk/plugin-test.apk"));
//         BufferedSink sink = Okio.buffer(Okio.sink(apk))) {
//      sink.writeAll(source);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    right
//    wrong
//    File apk = new File("./app/src/main/assets/Create_test.apk");
    try (Source source = Okio.source(getAssets().open("apk/plugin-test.apk"));
         BufferedSink sink = Okio.buffer(Okio.sink(apk))) {
      sink.writeAll(source);
    } catch (IOException e) {
      e.printStackTrace();
    }
    DexClassLoader classLoader = new DexClassLoader(apk.getPath(), getCacheDir().getPath(), null, null);
    try {
      Class utilsClass = classLoader.loadClass("com.hencoder.plugin.Utils");
      Constructor utilsConstructor = utilsClass.getDeclaredConstructors()[0];
      utilsConstructor.setAccessible(true);
      Object utils = utilsConstructor.newInstance();
      Method shoutMethod = utilsClass.getDeclaredMethod("shout");
      shoutMethod.setAccessible(true);
      shoutMethod.invoke(utils);
      Intent intent = new Intent();
      intent.setClassName("com.hencoder.plugin", "com.hencoder.plugin.MainActivity");
      startActivity(intent);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}