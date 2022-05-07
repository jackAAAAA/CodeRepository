package pattern.construction.proxy.statics;

/**
 * 项目名:    Demo
 * 包名       pattern.construction.proxy.statics
 * 文件名:    Star
 * 创建时间:  2018/1/8 on 16:35
 * 描述:     TODO 抽象角色：提供代理角色和真实角色对外提供的公共方法
 *
 * @author zjb
 */
public interface Star {
    /**
     * 签合同
     */
    void signContract();

    /**
     * 收尾款
     */
    void collectMoney();

    /**
     * 唱歌
     */
    void sing();


}
