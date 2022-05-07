package MultiThread.Liaoxuefeng;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * CompletableFuture_1
 * 直接查询价格
 */
//public class P16_Main {
//    public static void main(String[] args) throws Exception {
//        // 创建异步执行任务:
//        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(P16_Main::fetchPrice);
//        // 如果执行成功:
//        cf.thenAccept((result) -> {
//            System.out.println("price: " + result);
//        });
//        // 如果执行异常:
//        cf.exceptionally((e) -> {
//            e.printStackTrace();
//            return null;
//        });
//        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
//        Thread.sleep(200);
//    }
//
//    static Double fetchPrice() {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//        }
//        if (Math.random() < 0.3) {
//            throw new RuntimeException("fetch price failed!");
//        }
//        return 5 + Math.random() * 20;
////        System.out.println("fetched!");
////        return 123.345;
//    }
//}

/**
 * CompletableFuture_2
 * 串行查询：根据证券名称查询股票代码，再根据股票代码查询价格。
 */
//public class P16_Main {
//    public static void main(String[] args) throws Exception {
//        // 第一个任务:
//        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
//            return queryCode("中国石油");
//        });
//        // cfQuery成功后继续执行下一个任务:
//        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
//            return fetchPrice(code);
//        });
//        // cfFetch成功后打印结果:
//        cfFetch.thenAccept((result) -> {
//            System.out.println("price: " + result);
//        });
//        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
//        Thread.sleep(2000);
//    }
//
//    static String queryCode(String name) {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//        }
//        return "601857";
//    }
//
//    static Double fetchPrice(String code) {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//        }
//        return 5 + Math.random() * 20;
//    }
//}

/**
 * CompletableFuture_3
 * 并行查询：同时查询两家公司的股票代码，倘若任何一家查询成功；就继续查询价格
 * CompletableFuture.anyOf()返回结果为一个Object；而CompletableFuture.anyOf()返回结果为一个Void(空)
 */
public class P16_Main {
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);
//        CompletableFuture<Void> cfQuery = CompletableFuture.allOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice(String.valueOf(code), "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice(String.valueOf(code), "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);
//        CompletableFuture<Void> cfFetch = CompletableFuture.allOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
