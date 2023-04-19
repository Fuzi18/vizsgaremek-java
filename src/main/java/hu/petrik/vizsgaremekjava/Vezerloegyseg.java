package hu.petrik.vizsgaremekjava;

public class Vezerloegyseg {
    private int id;
    private String leiras;
    private String vezerloegysegkomponens;
    private int quantity;
    private int price;

    public Vezerloegyseg(int id, String leiras, String vezerloegysegkomponens, int quantity, int price) {
        this.id = id;
        this.leiras = leiras;
        this.vezerloegysegkomponens = vezerloegysegkomponens;
        this.quantity = quantity;
        this.price = price;
    }

    public Vezerloegyseg(String leiras, String vezerloegysegkomponens, int quantity, int price) {
        this.leiras = leiras;
        this.vezerloegysegkomponens = vezerloegysegkomponens;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getVezerloegysegkomponens() {
        return vezerloegysegkomponens;
    }

    public void setVezerloegysegkomponens(String vezerloegysegkomponens) {
        this.vezerloegysegkomponens = vezerloegysegkomponens;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Vezerloegyseg{" +
                "id=" + id +
                ", leiras='" + leiras + '\'' +
                ", vezerloegysegkomponens='" + vezerloegysegkomponens + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
