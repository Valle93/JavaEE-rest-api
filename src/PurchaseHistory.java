import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PurchaseHistory implements Serializable {

    private List<Order> orders = new ArrayList<>();
    private int totalSpent;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrderToHistory(Order order) {
        orders.add(order);
    }

    /**
     * Metoden kalkylerar den totala summan som en kund har spenderat
     * @return: Totala summan som en kund har spenderat
     */
    public int getTotalSpent() {
        return orders.parallelStream().mapToInt(order -> order.getItems().parallelStream()
                .mapToInt(item -> item.getQuantity() * item.getRecord().getPrice()).sum()).sum();
    }
}
