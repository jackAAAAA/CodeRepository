package pattern.construction.proxy.statics;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.statics
 * 文件名:    RealStar
 * 创建时间:  2018/1/8 on 16:35
 * 描述:     TODO 真实角色（明星艺人）：薛之谦
 *
 * @author zjb
 */
public class RealStar implements Star {

    @Override
    public void signContract() {
        System.out.println("薛之谦.签合同()");
    }

    @Override
    public void collectMoney() {
        System.out.println("薛之谦.收尾款()");
    }

    @Override
    public void sing() {
        //真实角色的操作：真正的业务逻辑
        System.out.println("薛之谦.sing()");
    }
}
