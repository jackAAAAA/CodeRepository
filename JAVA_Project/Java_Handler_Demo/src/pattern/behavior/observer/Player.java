package pattern.behavior.observer;

/**
 * 项目名:    Demo
 * 包名       pattern.behavior.observer
 * 文件名:    Player
 * 创建时间:  2018/1/8 on 17:32
 * 描述:     TODO 具体观察者
 *
 * @author zjb
 */
public class Player implements Observer {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 支援盟友方法的实现
     */
    @Override
    public void help() {
        System.out.println("坚持住，" + this.name + "来救你！");
    }

    /**
     * 遭受攻击方法的实现，当遭受攻击时将调用战队控制中心类的通知方法notifyObserver()来通知盟友
     *
     * @param acc
     */
    @Override
    public void beAttacked(AllyControlCenter acc) {
        System.out.println(this.name + "被攻击！");
        acc.notifyObserver(name);
    }
}