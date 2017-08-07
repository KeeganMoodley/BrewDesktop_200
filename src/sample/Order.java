package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by s213463695 on 2016/07/23.
 */
public class Order {

    public StringProperty orderID=new SimpleStringProperty();
    public StringProperty customer=new SimpleStringProperty();
    public StringProperty block=new SimpleStringProperty();
    public StringProperty row=new SimpleStringProperty();
    public StringProperty seat=new SimpleStringProperty();
    public StringProperty quantity=new SimpleStringProperty();
    public StringProperty cost=new SimpleStringProperty();
    public StringProperty time=new SimpleStringProperty();

    public Order(String orderID, String customer, String block, String row, String seat, String quantity, String cost, String time) {
        this.orderID.set(orderID);
        this.customer.set(customer);
        this.block.set(block);
        this.row.set(row);
        this.seat.set(seat);
        this.quantity.set(quantity);
        this.cost.set(cost);
        this.time.set(time);
    }

    public String getOrderID() {
        return orderID.get();
    }

    public StringProperty orderIDProperty() {
        return orderID;
    }

    public String getCustomer() {
        return customer.get();
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public String getBlock() {
        return block.get();
    }

    public StringProperty blockProperty() {
        return block;
    }

    public String getRow() {
        return row.get();
    }

    public StringProperty rowProperty() {
        return row;
    }

    public String getSeat() {
        return seat.get();
    }

    public StringProperty seatProperty() {
        return seat;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }
}
