import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Basket implements Serializable {

    private int[] prices;
    private String[] productsName;
    private int[] productsCount;

    private Basket() {

    }

    public Basket(int[] prices, String[] productsName) {
        this.prices = prices;
        this.productsCount = new int[prices.length];
        this.productsName = productsName;
    }

    public void setProductsCount(int[] productsCount) {
        this.productsCount = productsCount;
    }

    protected void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Корзина:");

        int sum = 0;

        for (int i = 0; i < productsCount.length; i++) {
            int allCount = productsCount[i];
            int priceSum = prices[i] * allCount;
            if (allCount > 0) {
                sum += priceSum;
                System.out.println(productsName[i] + " " + allCount + " " + priceSum);
            }
        }
        System.out.println("Всего: " + sum + " руб.");
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile);) {
            writer.println(productsName.length);

            String productsLine = Arrays.stream(productsName)
                    .collect(Collectors.joining(" "));
            writer.println(productsLine);

            for (int price : prices) {
                writer.print(price + " ");
            }
            writer.println();

            for (int count : productsCount) {
                writer.print(count + " ");
            }
            writer.println();
        }
    }

    public void saveBin(File binFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(binFile))) {
            out.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(binFile))) {
            return (Basket) in.readObject();
        }
    }

}
