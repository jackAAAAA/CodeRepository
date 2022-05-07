package Singleton.MemorialWriting.M0901;

public class test_0901 {
    public static void main(String[] args) {
//    by Static_InnerClass
        Static id1 = Static.getInstance();
        System.out.println(id1.getId());

//        by enum_id
        id id2 = id.INSTANCE;
        System.out.println(id2.getID());

//        by enum_string
        string str = string.INSTANCE;
        System.out.println(str.getName());
        str.setName("test!");
        System.out.println(str.getName());
    }
}
