package Exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class printStackTrace {
//    // Main Method
//    public static void main(String[] args)
//            throws Exception
//    {
//        try {
//
//            // create a array of Integers
//            int[] i = new int[3];
//
//            // try to add numbers to array
//            i[3] = 3;
//        }
//        catch (Throwable e) {
//
//            // print Stack Trace
//            e.printStackTrace(System.out);
//        }
//    }

    // Main Method
    public static void main(String[] args)
            throws Exception {
        try {

            // divide two numbers
            int a = 74, b = 0;

            int c = a / b;
        } catch (Throwable e) {

            // Using a StringWriter,
            // to convert trace into a String:
//            StringWriter sw = new StringWriter();
//
//            // create a PrintWriter
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//
//            String error = sw.toString();
//
//            System.out.print("Error:\n" + error);
//                System.out.println("Error:");
//                e.printStackTrace(System.out);
            e.printStackTrace();
        }
    }




}
