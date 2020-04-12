import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private int id;
    private List<OrderInfo> items = new ArrayList<>();
    private int orderTotal;
    private int itemsQuantity;

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderInfo> getItems() {
        return items;
    }

    public void setItems(List<OrderInfo> items) {
        this.items = items;
    }

    public void addNewProductToOrder(Record record, int quantity) {
        items.add(new OrderInfo(record, quantity));
    }


    /**
     * Metoden kalkylerar en beställnings total summa
     * @return: summan för en beställning
     */
    public int getOrderTotal() {
        return items.parallelStream().mapToInt(item -> item.getQuantity() * item.getRecord().getPrice()).sum();
    }

    /**
     * Metoden räknar ut antalet produkter i en order
     * @return: antalet produkter i en order
     */
    public int getItemsQuantity() {
        return items.parallelStream().mapToInt(item -> item.getQuantity()).sum();
    }
}
