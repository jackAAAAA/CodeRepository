package handler;

/*
 * 项目名:    Demo
 * 包名       Looper
 * 文件名:    Message
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:10
 * 描述:     TODO
 */
public class Message {

    //发送的消息
    public Object obj;

    //目标Handler
    public Handler target;

    @Override
    public String toString() {
        return obj.toString();
    }
}
