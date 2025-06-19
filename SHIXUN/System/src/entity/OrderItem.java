package entity;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int dishId;
    private double dishPrice;
    private double totalAmount;

    public OrderItem() {}

    public OrderItem(int orderItemId, int orderId, int dishId, double dishPrice, double totalAmount) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.dishId = dishId;
        this.dishPrice = dishPrice;
        this.totalAmount = totalAmount;
    }
    public int getOrderItemId() { return orderItemId; }
    public int getOrderId() { return orderId; }
    public int getDishId() { return dishId; }
    public double getDishPrice() { return dishPrice; }
    public double getTotalAmount() { return totalAmount; }

    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setDishId(int dishId) { this.dishId = dishId; }
    public void setDishPrice(double dishPrice) { this.dishPrice = dishPrice; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}