package entity;

public class Employee {
    private int empId;
    private String name;
    private String gender;
    private String position;
    private String address;
    private double salary;

    public Employee() {}

    public Employee(int empId, String name, String gender, String position, String address, double salary) {
        this.empId = empId;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.address = address;
        this.salary = salary;
    }
    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getPosition() { return position; }
    public String getAddress() { return address; }
    public double getSalary() { return salary; }
    public void setEmpId(int empId) { this.empId = empId; }
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPosition(String position) { this.position = position; }
    public void setAddress(String address) { this.address = address; }
    public void setSalary(double salary) { this.salary = salary; }
}