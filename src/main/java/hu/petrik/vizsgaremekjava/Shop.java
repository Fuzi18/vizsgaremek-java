package hu.petrik.vizsgaremekjava;

public class Shop {
    private int id;
    private String type;
    private String team;
    private String size;
    private String color;
    private int price;
    private int quantity;

    public Shop(int id, String type, String team, String size, String color, int price, int quantity) {
        this.id = id;
        this.type = type;
        this.team = team;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }
    public Shop( String type, String team, String size, String color, int price, int quantity) {
        this.type = type;
        this.team = team;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", team='" + team + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
