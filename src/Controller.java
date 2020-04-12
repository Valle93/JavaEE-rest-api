import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class Controller implements Serializable {

    private Database database;
    private List<Record> recordList;
    private String pwd;
    private String username;
    private User currentUser;
    private String search;
    private List<CartItem> cartItems;
    private int cartLength;
    private List<Order> tempOrderList;
    private List<OrderInfo> tempItemList;

    public Controller() {
        this.database = new Database();
        this.recordList = database.getRecordList();
        this.cartItems = new ArrayList<>();
    }


    public List<Order> getTempOrderList() {
        return tempOrderList;
    }

    public void setTempOrderList(List<Order> tempOrderList) {
        this.tempOrderList = tempOrderList;
    }

    public List<OrderInfo> getTempItemList() {
        return tempItemList;
    }

    public void setTempItemList(List<OrderInfo> tempItemList) {
        this.tempItemList = tempItemList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getCartLength() {
        return cartLength;
    }

    public void setCartLength(int cartLength) {
        this.cartLength = cartLength;
    }

    public void search() {
        List<Record> filteredRecords = database.getRecordList().parallelStream().filter(str ->
                str.getArtist().toLowerCase().contains(search.toLowerCase()) ||
                        str.getTitle().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        recordList = filteredRecords;
        getRecordList();
    }

    public List<Record> getRecordList() {
        //List<Record> filteredRecords = database.getRecordList().stream().filter(str -> str.getArtist().contains(search) || str.getTitle().contains(search)).collect(Collectors.toList());
        // this.recordList = filteredRecords;
        return recordList;
        //return filteredRecords;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    private void clearInputField() {
        this.username = "";
        this.pwd = "";
    }

    public String validateUsernamePassword() {
        User user = LoginDAO.validate(username, pwd);
        if (user != null) {
            currentUser = user;
            clearInputField();
            return currentUser.getRole() == Role.ADMIN ? "adminUserOverview" : "shop";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Password",
                            "Please enter correct username and Password"));
            return "login";
        }

    }

    public String logout() {
        clearCart();
        return "login";
    }

    public String viewCustomerOrder(int id) {
        this.tempOrderList = database.orderListByCustomerId(id);
        return "adminOrderOverview";
    }

    public String viewItemsInOrder(int id) {
        this.tempItemList = database.itemListByOrderId(id);
        return "adminItemOverview";
    }

    public void addToCart(int id) {
        CartItem cartItem = getCartItemFromId(id);
        if (cartItem != null) {
            cartItem.setQty(cartItem.getQty() + 1);
        } else {
            for (Record record : recordList) {
                if (record.getId() == id) {
                    cartItems.add(new CartItem(record));
                }
            }
        }
        updateCartLength();
    }

    private void updateCartLength() {
        int count = 0;
        for (CartItem cartItem: cartItems) {
            count += cartItem.getQty();
        }
        this.cartLength = count;
    }

    private CartItem getCartItemFromId(int id) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getRecord().getId() == id) {
                return cartItem;
            }
        }
        return null;
    }

    public void clearCart() {
        this.cartItems.clear();
        this.cartLength = 0;
    }

    public String getCartIconStatus() {
        if(cartItems.size()>0) {
            return "visible";
        } else {
            return "hidden";
        }
    }

}