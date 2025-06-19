package entity;

public class Desk {
    private int deskId;
    private int areaId;
    private int capacity;
    private String status;

    public Desk() {}

    public Desk(int deskId, int areaId, int capacity, String status) {
        this.deskId = deskId;
        this.areaId = areaId;
        this.capacity = capacity;
        this.status = status;
    }
    public int getDeskId() { return deskId; }
    public int getAreaId() { return areaId; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }
    public void setDeskId(int deskId) { this.deskId = deskId; }
    public void setAreaId(int areaId) { this.areaId = areaId; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setStatus(String status) { this.status = status; }
}