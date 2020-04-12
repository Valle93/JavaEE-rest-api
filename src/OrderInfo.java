import java.io.Serializable;

public class OrderInfo implements Serializable {

    private int id;
    private Record record;
    private int quantity;
    private int totalPrice;

    public OrderInfo(Record record, int quantity) {
        this.record = record;
        this.quantity = quantity;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Metoden räknar ut summan av antalet av en specifik produkt
     * @return: summa
     */
    public int getTotalPrice() {
        return quantity * record.getPrice();
    }
}
