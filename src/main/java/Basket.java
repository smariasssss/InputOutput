import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;

public class Basket implements Serializable {
    private int[] prices;
    private String[] products;
    private int[] totalBasket;
    private int sum;
    private boolean[] isFilled;


    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.totalBasket = new int[products.length];
        this.isFilled = new boolean[products.length];
    }

    public Basket(int[] prices, String[] products, int[] totalBasket, boolean[] isFilled) {
        this.prices = prices;
        this.products = products;
        this.totalBasket = totalBasket;
        this.isFilled = isFilled;
    }

    public Basket(int[] prices, String[] products, int[] totalBasket, int sum, boolean[] isFilled) {
        this.prices = prices;
        this.products = products;
        this.totalBasket = totalBasket;
        this.sum = sum;
        this.isFilled = isFilled;
    }

    public Basket() {
    }


    public void addToCart(int productNum, int amount) {
        totalBasket[productNum] += amount;

        isFilled[productNum] = true;
    }

    public void printCart() {
        for (int i = 0; i < products.length; i++) {
            if (isFilled[i]) {
                System.out.println("You chose " + products[i] + ", " + totalBasket[i] + " items;" +
                        "price = " + prices[i] * totalBasket[i]);
                sum += prices[i] * totalBasket[i];
            }
        }
        System.out.println("Total price = " + sum);
    }

    public void saveTxt(File textFile) throws IOException {
        textFile = new File(textFile.toURI());
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i = 0; i < totalBasket.length; i++) {

                out.print(totalBasket[i] + " ");
            }
            out.println();
            for (int j = 0; j < totalBasket.length; j++) {
                out.print(products[j] + " ");
            }
            out.println();
            for (int t = 0; t < prices.length; t++) {
                out.print(prices[t] + " ");
            }
            out.println();
            for (int t = 0; t < isFilled.length; t++) {
                out.print(isFilled[t] + " ");
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException, ParseException, org.json.simple.parser.ParseException {

        JSONParser parser = new JSONParser();
        if (textFile.exists()) {
            ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            Object obj = parser.parse(new FileReader(textFile));
            String result = mapper.writeValueAsString(obj);
            return mapper.readValue(result, Basket.class);
        } else {
            return new Basket(null, null, null, null);
        }
    }
}
