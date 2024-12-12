
public class InventoryItem {
    private String brand;
    private int size;
    private int quantity;

    public InventoryItem(String brand, int size, int quantity) {
        this.brand = brand;
        this.size = size;
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public int getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }
}
