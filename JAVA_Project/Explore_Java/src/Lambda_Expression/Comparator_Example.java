package Lambda_Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Comparator_Example {

    public static void main(String[] args) {
        List<Developer> listDevs = getDevelopers();

        System.out.println("Before Sort");
        for (Developer developer : listDevs) {
            System.out.println("name = " + developer.name + ", "
                    + "salary = " + developer.salary + ", " + "age = " + developer.age);
        }
//        sort by name with the normal function
//        Collections.sort(listDevs, new Comparator<Developer>() {
//            @Override
//            public int compare(Developer e1, Developer e2) {
//                return e1.getName().compareTo(e2.getName());
//            }
//        });
//        sort by name with lambda expressioin
//        listDevs.sort((Developer e1, Developer e2) -> e1.getName().compareTo(e2.getName()));

//        sort by salary with the normal function
//        Collections.sort(listDevs, new Comparator<Developer>() {
//           @Override
//           public int compare(Developer d2, Developer d1) {
//               return d1.getSalary() - d2.getSalary();
//           }
//        });
//      sort by salary with the lambda expression
//        listDevs.sort((Developer d1, Developer d2) -> d1.getSalary() - d2.getSalary());
        Comparator<Developer> salaryComparator = (Developer d1, Developer d2) -> d1.getSalary() - d2.getSalary();
//        listDevs.sort(salaryComparator);
        listDevs.sort(salaryComparator.reversed());

//        ort by age with the normal function
//        Collections.sort(listDevs, new Comparator<Developer>() {
//            @Override
//            public int compare(Developer o1, Developer o2) {
//                return o1.getAge() - o2.getAge();
//            }
//        });
        //sort by age with lambda expression
//        listDevs.sort((Developer v1, Developer v2) -> v2.getAge() - v1.getAge());

//        System.out.println("After Sort By age");
//        System.out.println("After Sort By name");
        System.out.println("After Sort By salary");

//        Normal output
//        for (Developer developer : listDevs) {
//            System.out.println("name = " + developer.name + ", "
//                    + "salary = " + developer.salary + ", " + "age = " + developer.age);
//        }
//        Output with lambda expression
        listDevs.forEach((Developer) -> System.out.println("name = " + Developer.name + ", "
                + "salary = " + Developer.salary + ", " + "age = " + Developer.age));
    }

    private static List<Developer> getDevelopers() {

        List<Developer> result = new ArrayList<Developer>();

        result.add(new Developer("mkyong", 70000, 33));
        result.add(new Developer("alvin", 80000, 20));
        result.add(new Developer("jason", 100000, 10));
        result.add(new Developer("iris", 170000, 55));

        return result;

    }

    private static class Developer {
        String name;
        int salary, age;
        public Developer(String name, int salary, int age) {
            this.name = name;
            this.salary = salary;
            this.age = age;
        }
        public String getName() {
            return this.name;
        }
        public int getSalary() {
            return this.salary;
        }
        public int getAge() {
            return this.age;
        }

    }
}
