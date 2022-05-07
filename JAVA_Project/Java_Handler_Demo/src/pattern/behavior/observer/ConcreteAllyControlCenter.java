package pattern.behavior.observer;

/**
 * 项目名:    Demo
 * 包名       pattern.behavior.observer
 * 文件名:    ConcreteAllyControlCenter
 * 创建时间:  2018/1/8 on 17:33
 * 描述:     TODO
 *
 * @author zjb
 */
public class ConcreteAllyControlCenter extends AllyControlCenter {
    public ConcreteAllyControlCenter(String allyName) {
        System.out.println(allyName + "战队组建成功！");
        System.out.println("----------------------------");
        this.allyName = allyName;
    }

    /**
     * 实现通知方法
     *
     * @param name
     */
    @Override
    public void notifyObserver(String name) {
        System.out.println(this.allyName + "战队紧急通知，盟友" + name + "遭受敌人攻击！");
        //遍历观察者集合，调用每一个盟友（自己除外）的支援方法
        for (Object obs : players) {
            if (!((Observer) obs).getName().equalsIgnoreCase(name)) {
                ((Observer) obs).help();
            }
        }
    }
}