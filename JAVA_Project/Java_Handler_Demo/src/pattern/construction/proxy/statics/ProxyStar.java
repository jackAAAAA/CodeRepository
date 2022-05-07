package pattern.construction.proxy.statics;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.statics
 * 文件名:    ProxyStar
 * 创建时间:  2018/1/8 on 16:37
 * 描述:     TODO 明星代理对象：经纪人
 *
 * @author zjb
 */
public class ProxyStar implements Star {

    /**
     * 真实对象的引用（明星）
     */
    private Star star;

    /**
     * 通过构造器给真实角色赋值
     *
     * @param star
     */
    public ProxyStar(Star star) {
        this.star = star;
    }


    @Override
    public void signContract() {
        System.out.println("经纪人.签合同()");
    }


    @Override
    public void sing() {
        //真实对象的操作（明星唱歌）
        star.sing();
    }

    @Override
    public void collectMoney() {
        System.out.println("经纪人.收尾款()");
    }


}