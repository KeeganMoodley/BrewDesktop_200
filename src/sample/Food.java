package sample;

import java.io.Serializable;


/**
 * Created by s214079694 on 2017/07/01.
 */
public class Food implements Serializable {
    private int quantity, quantityAvailable, id, type, prepTime;
    private double price, length, width, height, volume = 0;
    private String title, nutrition, dietary;
    private boolean halaal;
    private byte[] image;

    public Food(int id, int type, byte[] image, double price, String title, String nutrition, String dietary, boolean halaal, int quantityAvailable, double length, double width, double height, double volume, int prepTime) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.halaal = halaal;
        quantity = 0;
        this.quantityAvailable = quantityAvailable;
        this.nutrition = nutrition;
        this.dietary = dietary;
        this.id = id;
        this.type = type;
        this.length = length;
        this.width = width;
        this.height = height;
        this.volume = volume;
        this.prepTime = prepTime;
    }

    public Food(int quantity, int id, String title, byte[] image) {
        this.quantity = quantity;
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return title + "\t" + "x" + quantity;
    }

    public double getSize() {
        if (volume == 0)
            return length * width;
        return volume;
    }
}

