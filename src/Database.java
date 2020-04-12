import java.beans.Customizer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Database {

    private List<Record> recordList;
    private List<User> userList;
    private List<User> customerList;
    private List<Order> orderList;


    public Database() {
        this.userList = new ArrayList<>();
        this.recordList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.orderList = new ArrayList<>();

        // Test för admin
        PurchaseHistory purchaseHistory1 = new PurchaseHistory();
        PurchaseHistory purchaseHistory2 = new PurchaseHistory();
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);
        order1.addNewProductToOrder(new Record(1,"Toto","Old is new","toto-1.jpg", 149),3000);
        order2.addNewProductToOrder(new Record(2,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209),2);
        order2.addNewProductToOrder(new Record(3,"Streaplers","En gång till","streaplers-1.jpg",79),10);
        order3.addNewProductToOrder(new Record(123,"John Hiatt", "Ottawa", "johnhiatt-1.jpg", 199), 8);
        purchaseHistory1.addOrderToHistory(order1);
        purchaseHistory1.addOrderToHistory(order2);
        purchaseHistory2.addOrderToHistory(order3);
        User user1 = new User("mrtest", "Test", "Testsson", "lösen", Role.CUSTOMER, 1);
        user1.setPurchaseHistory(purchaseHistory1);
        User user2 = new User("jacob1", "Jacob", "Andersson", "password", Role.CUSTOMER, 2);
        User user3 = new User("sandra85", "Sandra", "Berg", "mittlösen", Role.CUSTOMER, 3);
        User user4 = new User("Larssa2000", "Lars", "Lerin", "Junior123", Role.PREMIUM, 4);
        user2.setPurchaseHistory(purchaseHistory2);
        user3.setPurchaseHistory(new PurchaseHistory());
        user4.setPurchaseHistory(new PurchaseHistory());

        this.userList.add(user1);
        this.userList.add(user2);
        this.userList.add(user3);
        this.userList.add(user4);
        this.userList.add(new User("HockeyDanne", "Daniel", "Sedin", "qwerty", Role.ADMIN, 5));

        this.recordList.add(new Record(4,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(5,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(6," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(7,"Streaplers","En gång till","streaplers-1.jpg",79));
        this.recordList.add(new Record(8,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(9,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(10," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(11,"Streaplers","En gång till","streaplers-1.jpg",79));
        this.recordList.add(new Record(12,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(13,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(14," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(15,"Streaplers","En gång till","streaplers-1.jpg",79));
        this.recordList.add(new Record(16,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(17,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(18," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(19,"Streaplers","En gång till","streaplers-1.jpg",79));
        this.recordList.add(new Record(20,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(21,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(22," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(23,"Streaplers","En gång till","streaplers-1.jpg",79));
        this.recordList.add(new Record(24,"Toto","Old is new","toto-1.jpg", 149));
        this.recordList.add(new Record(25,"Excalibur","Live In Brocéliande","excalibur-1.jpg", 209));
        this.recordList.add(new Record(26," John Hiatt","Ottawa","johnhiatt-1.jpg", 199));
        this.recordList.add(new Record(27,"Streaplers","En gång till","streaplers-1.jpg",79));

        this.orderList.add(order1);
        this.orderList.add(order2);
        this.orderList.add(order3);
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getCustomerList() {
        customerList = this.userList.parallelStream()
                .filter(user -> user.getRole().equals(Role.PREMIUM) || user.getRole().equals(Role.CUSTOMER))
                .collect(Collectors.toList());
        return customerList;
    }

    public List<Order> orderListByCustomerId(int id) {
        List<User> chosenCustomer = customerList.parallelStream().filter(customer -> customer.getId() == id).collect(Collectors.toList());
        return chosenCustomer.get(0).getPurchaseHistory().getOrders();
    }

    public List<OrderInfo> itemListByOrderId(int id) {
        List<Order> chosenOrder = orderList.parallelStream().filter(order -> order.getId() == id).collect(Collectors.toList());
        return chosenOrder.get(0).getItems();
    }

}
