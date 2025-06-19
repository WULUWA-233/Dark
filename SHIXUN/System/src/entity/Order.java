package entity;

import java.util.Date;

public class Order {
    private int orderId;
    private int deskId;
    private int customerId;
    private int empId;
    private Date startTime;
    private Date endTime;
    private double totalAmount;

    public Order() {}

    public Order(int orderId, int deskId, int customerId, int empId, Date startTime, Date endTime, double totalAmount) {
        this.orderId = orderId;
        this.deskId = deskId;
        this.customerId = customerId;
        this.empId = empId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalAmount = totalAmount;
    }
    public int getOrderId() { return orderId; }
    public int getDeskId() { return deskId; }
    public int getCustomerId() { return customerId; }
    public int getEmpId() { return empId; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public double getTotalAmount() { return totalAmount; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setDeskId(int deskId) { this.deskId = deskId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setEmpId(int empId) { this.empId = empId; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}