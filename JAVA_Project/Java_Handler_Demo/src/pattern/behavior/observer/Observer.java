package pattern.behavior.observer;

/**
 * 项目名:    Demo
 * 包名       pattern.behavior.observer
 * 文件名:    Observer
 * 创建时间:  2018/1/8 on 17:32
 * 描述:     TODO 抽象观察者
 *
 * @author zjb
 */
public interface Observer {
    public String getName();

    public void setName(String name);

    /**
     * 声明支援盟友方法
     */
    public void help();

    /**
     * 声明遭受攻击方法
     *
     * @param acc
     */
    public void beAttacked(AllyControlCenter acc);
}
