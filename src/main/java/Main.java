import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int[] prices = {40, 200, 60};
        String[] productsName = {"Хлеб", "Масло", "Щоколадка"};

//        int[] productsCount = new int[3];
        Basket basket = new Basket(prices, productsName);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите товар и количество или введите end:");
            String input = scanner.nextLine();

            if ("end".equals(input)) {
                break;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);

//            productsCount[productNumber] += productCount;
            basket.addToCart(productNumber, productCount);
            basket.saveTxt(new File("basket.txt"));
        }

        Basket basket2 = Basket.loadFromTxtFile(new File("basket.txt"));

        basket.printCart();
    }
}
