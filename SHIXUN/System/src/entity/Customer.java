package entity;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String gender;

    public Customer() {}

    public Customer(int customerId, String name, String phone, String gender) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
}