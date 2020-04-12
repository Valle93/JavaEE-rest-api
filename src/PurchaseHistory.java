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

    public int getTotalSpent() {
        AtomicInteger totalSpent = new AtomicInteger();
        orders.forEach(order -> order.getItems().forEach(item ->
                totalSpent.addAndGet((item.getQuantity() * item.getRecord().getPrice()))));
        return totalSpent.get();
    }
}
