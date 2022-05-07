package pattern.construction.proxy.dynamic;

import pattern.construction.proxy.statics.RealStar;
import pattern.construction.proxy.statics.Star;

import java.lang.reflect.Proxy;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.dynamic
 * 文件名:    Test
 * 创建时间:  2018/1/8 on 17:25
 * 描述:     TODO
 *
 * @author zjb
 */
public class Test {
    public static void main(String[] args) {
        //真实角色
        Star realStar = new RealStar();
        //处理器
        StarHandler handler = new StarHandler(realStar);
        //代理类
        Star proxy = (Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Star.class}, handler);
        //调用代理类的唱歌方法：其实调用的是真实角色的唱歌方法
        proxy.sing();
    }
}
