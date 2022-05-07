package com.learning.javacodeexercise.ME1102;

import com.learning.javacodeexercise.ME1102.LittleSuperMarket.Person.Customer;
import com.learning.javacodeexercise.ME1102.LittleSuperMarket.supermarket.Merchandise;
import com.learning.javacodeexercise.ME1102.LittleSuperMarket.supermarket.StructureOfSupermarket;

import java.util.Scanner;

public class RunLittleSuperMarketAppMain {
    public static void main(String[] args) {

        StructureOfSupermarket structureOfSupermarket = new StructureOfSupermarket();
        structureOfSupermarket.address = "世纪大道666号";
        structureOfSupermarket.superMarketName = "有家超市";
        structureOfSupermarket.parkingCount = 200;
        structureOfSupermarket.merchandises = new Merchandise[100];
        structureOfSupermarket.merchandiseSold = new int[structureOfSupermarket.merchandises.length];

        Merchandise[] all = structureOfSupermarket.merchandises;

        for (int i = 0; i < all.length; i++) {
            Merchandise m = new Merchandise();
            m.count = 200;
            m.id = "ID" + i;
            m.name = "商品" + i;
            m.purchasePrice = Math.random() * 200;
            m.soldPrice = (1 + Math.random()) * 200;
            all[i] = m;
        }

        System.out.println("超市开门啦！");

        boolean open = true;
        Scanner scanner = new Scanner(System.in);

        while (open) {
            System.out.println("本店叫做" + structureOfSupermarket.superMarketName);
            System.out.println("本店地址" + structureOfSupermarket.address);
            System.out.println("共有停车位" + structureOfSupermarket.parkingCount + "个");
            System.out.println("今天的营业额为：" + structureOfSupermarket.incomingSum);
            System.out.println("共有商品：" + structureOfSupermarket.merchandises.length + "种");

            Customer customer = new Customer();
            customer.name = "顾客编号" + ((int) (Math.random() * 10000));
            customer.money = (1 + Math.random()) * 1000;
            customer.isDrivingCar = Math.random() > 0.5;

            if (customer.isDrivingCar) {
                if (structureOfSupermarket.parkingCount > 0) {
                    System.out.println("欢迎" + customer.name + "驾车而来。本店已经为您安排了车位，停车免费哦。车位编号为：" + structureOfSupermarket.parkingCount);
                    structureOfSupermarket.parkingCount--;
                }else {
                    System.out.println("不好意思，本店车位已满。欢迎您下次光临！");
                    continue;
                }
            }else {
                System.out.println("欢迎" + customer.name + "光临本店！");
            }

            double totalCost = 0;
            while (true) {
                System.out.println("本店提供" + all.length + "种商品，欢迎选购。请输入商品编号：");
                int index = scanner.nextInt();

                if (index < 0) {
                    break;
                }

                if (index >= all.length) {
                    System.out.println("本店没有这种商品，请叙述编号在0到" + (all.length - 1) + "之内的商品编号：");
                    continue;
                }

                Merchandise m = all[index];

                System.out.println("您选购的商品名字" + m.name + "。单价是" + m.soldPrice + "。请问您要购买多少个？");
                int numToBuy = scanner.nextInt();

                if (numToBuy <= 0) {
                    System.out.println("不买看看也好，欢迎继续挑选。");
                    continue;
                }

                if (numToBuy > m.count) {
                    System.out.println("本店此商品库存没有这么多，欢迎继续挑选。");
                    continue;
                }

                if (numToBuy * m.soldPrice + totalCost > customer.money) {
                    System.out.println("您带的钱不够，欢迎继续挑选。");
                    continue;
                }

                totalCost += numToBuy * m.soldPrice;

                m.count -= numToBuy;
                structureOfSupermarket.merchandiseSold[index] += numToBuy;
            }

            customer.money -= totalCost;

            if (customer.isDrivingCar) {
                structureOfSupermarket.parkingCount++;
            }

            System.out.println("顾客" + customer.name + "共消费了" + totalCost);

            structureOfSupermarket.incomingSum += totalCost;

            System.out.println("还继续营业吗？");
            open = scanner.nextBoolean();

            System.out.println("超市关门了！");
            System.out.println("今天总的营业额为：" + structureOfSupermarket.incomingSum + "，营业情况如下：");

            for (int i = 0; i < structureOfSupermarket.merchandiseSold.length; i++) {
                Merchandise m = all[i];
                int numSold = structureOfSupermarket.merchandiseSold[i];
                if (numSold > 0) {
                    double incoming = m.soldPrice * numSold;
                    double netIncoming = (m.soldPrice - m.purchasePrice) * numSold;
                    System.out.println(m.name + "售出了" + numSold + "个。销售额为：" +
                            incoming + "。净利润为：" + netIncoming);
                }

            }

        }

    }
}
