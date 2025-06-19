package entity;

import java.util.Date;

public class ConsumptionRecord {
    private int recordId;
    private int customerId;
    private String customerName;
    private int orderId;
    private double amount;
    private Date time;

    public ConsumptionRecord(int recordId, int customerId, String customerName, int orderId, double amount, Date time) {
        this.recordId = recordId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderId = orderId;
        this.amount = amount;
        this.time = time;
    }

    // getters and setters
    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getTime() { return time; }
    public void setTime(Date time) { this.time = time; }
}