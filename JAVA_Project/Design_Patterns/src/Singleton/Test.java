package Singleton;

//        Create a safe_thread
//        Create Singleton by static
//        Create Singleton by enum in first way
//        Create Singleton by enum in second way

import Singleton.MemorialWriting.M1002.EnumString;
import Singleton.MemorialWriting.M1002.EnumIDGenerator;
import Singleton.MemorialWriting.M1002.StaticInnerClass;

public class Test {
    public static void main(String[] args) {
//        Create a safe_thread
//        Create Singleton by static
        StaticInnerClass staticInnerClass = StaticInnerClass.getInstance();
        System.out.println(staticInnerClass.getId());
        System.out.println(staticInnerClass.getId());
        System.out.println(staticInnerClass.getId());
//        Create Singleton by enum in first way
        EnumIDGenerator id = EnumIDGenerator.INSTANCE;
        System.out.println(id.getID());
        System.out.println(id.getID());
        System.out.println(id.getID());
//        Create Singleton by enum in second way
        EnumString enumString = EnumString.INSTANCE;
        System.out.println(enumString.getName());
        enumString.setName("Modifying_1002");
        System.out.println(enumString.getName());
    }
}
