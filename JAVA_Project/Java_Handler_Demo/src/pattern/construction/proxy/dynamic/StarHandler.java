package pattern.construction.proxy.dynamic;

import pattern.construction.proxy.statics.Star;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.dynamic
 * 文件名:    StarHandler
 * 创建时间:  2018/1/8 on 17:23
 * 描述:     TODO
 *
 * @author zjb
 */
public class StarHandler implements InvocationHandler {
    /**
     * 真实角色
     */
    private Star realStar;

    /**
     * 通过构造器来初始化真实角色
     *
     * @param realStar
     */
    public StarHandler(Star realStar) {
        this.realStar = realStar;
    }

    /**
     * 所有的流程控制都在invoke方法中
     * proxy：代理类
     * method：正在调用的方法
     * args：方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        System.out.println("真实角色调用之前的处理.....");
        if (method.getName().equals("sing")) {
            //反射调用realStar的sing方法
            object = method.invoke(realStar, args);
        }
        System.out.println("真实角色调用之后的处理.....");
        return object;
    }

}
