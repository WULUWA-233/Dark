package entity;

public class Dish {
    private int dishId;
    private String dishName;
    private double price;
    private int categoryId;

    public Dish() {}

    public Dish(int dishId, String dishName, double price, int categoryId) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.categoryId = categoryId;
    }
    public int getDishId() { return dishId; }
    public String getDishName() { return dishName; }
    public double getPrice() { return price; }
    public int getCategoryId() { return categoryId; }
    public void setDishId(int dishId) { this.dishId = dishId; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public void setPrice(double price) { this.price = price; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}