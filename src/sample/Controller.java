package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller {

    public Label captureDate;
    public Label totalOrders;
    public Label avgBeerDispatch;
    public Label avgBeerBlock;
    public Label avgBeerOrder;
    public Label maxBeerBlock;
    public Label maxBeerDispatch;
    public Label minBeerBlock;
    public Label minBeerDispatch;
    public Label grandTotal;
    public Label avgIncomeOrder;
    public Label avgIncomeBlock;
    public Label avgIncomeDispatch;
    public Label maxIncomeBlock;
    public Label maxIncomeDispatch;
    public Label minIncomeBlock;
    public Label minIncomeDispatch;
    public Label reportID;
    public Label reportDate;
    public Label avgIncomeDelivery;
    public Label avgBeerDelivery;

    private ArrayList<Order> orders = new ArrayList<>();
    private ObservableList<Order> obsOrders = FXCollections.observableArrayList(orders);
    private ArrayList<Food> list = new ArrayList<>();
    private ObservableList<Food> foodObservableList = FXCollections.observableArrayList(list);
    private ArrayList<Label> historyFractions;
    private Integer reportCount = 1;
    @FXML
    private Scene scene;
    @FXML
    private TableView mainTable;
    @FXML
    private DatePicker date_picker;
    @FXML
    private Button print;
    private VBox print_pane = null;
    private Main main;
    @FXML
    private ComboBox comboOrders;
    @FXML
    private FlowPane basket;
    @FXML
    private ListView itemsList;

    public Controller() {
    }

    public Controller(Main main) {
        this.main = main;
        obsOrders.setAll(FXCollections.observableArrayList(orders));
    }

    private void addFractions() {
        historyFractions = new ArrayList<>();
        historyFractions.add(captureDate);
        historyFractions.add(totalOrders);
        historyFractions.add(avgBeerDelivery);
        historyFractions.add(avgBeerOrder);
        historyFractions.add(avgBeerDispatch);
        historyFractions.add(avgBeerBlock);
        historyFractions.add(maxBeerBlock);
        historyFractions.add(maxBeerDispatch);
        historyFractions.add(minBeerBlock);
        historyFractions.add(minBeerDispatch);
        historyFractions.add(grandTotal);
        historyFractions.add(avgIncomeDelivery);
        historyFractions.add(avgIncomeOrder);
        historyFractions.add(avgIncomeDispatch);
        historyFractions.add(avgIncomeBlock);
        historyFractions.add(maxIncomeBlock);
        historyFractions.add(maxIncomeDispatch);
        historyFractions.add(minIncomeBlock);
        historyFractions.add(minIncomeDispatch);
    }

    public void connectToUI(Scene scene) {
        this.scene = scene;
        mainTable = (TableView) scene.lookup("#main_table");
        date_picker = (DatePicker) scene.lookup("#datepicker");
        print = (Button) scene.lookup("#print");
        comboOrders = (ComboBox) scene.lookup("#comboOrders");
        basket = (FlowPane) scene.lookup("#basket");
        itemsList = (ListView) scene.lookup("#itemsList");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                print_pane = (VBox) scene.lookup("#printPane");
                captureDate = (Label) scene.lookup("#captureDate");
                totalOrders = (Label) scene.lookup("#totalOrders");
                avgBeerDelivery = (Label) scene.lookup("#avgBeerDelivery");
                avgBeerDispatch = (Label) scene.lookup("#avgBeerDispatch");
                avgBeerBlock = (Label) scene.lookup("#avgBeerBlock");
                avgBeerOrder = (Label) scene.lookup("#avgBeerOrder");
                maxBeerBlock = (Label) scene.lookup("#maxBeerBlock");
                maxBeerDispatch = (Label) scene.lookup("#maxBeerDispatch");
                minBeerBlock = (Label) scene.lookup("#minBeerBlock");
                minBeerDispatch = (Label) scene.lookup("#minBeerDispatch");
                grandTotal = (Label) scene.lookup("#grandTotal");
                avgIncomeDelivery = (Label) scene.lookup("#avgIncomeDelivery");
                avgIncomeOrder = (Label) scene.lookup("#avgIncomeOrder");
                avgIncomeBlock = (Label) scene.lookup("#avgIncomeBlock");
                avgIncomeDispatch = (Label) scene.lookup("#avgIncomeDispatch");
                maxIncomeBlock = (Label) scene.lookup("#maxIncomeBlock");
                maxIncomeDispatch = (Label) scene.lookup("#maxIncomeDispatch");
                minIncomeBlock = (Label) scene.lookup("#minIncomeBlock");
                minIncomeDispatch = (Label) scene.lookup("#minIncomeDispatch");
                reportID = (Label) scene.lookup("#reportID");
                reportDate = (Label) scene.lookup("#reportDate");
                addFractions();
            }
        });

        print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                print(print_pane, true);
            }
        });

        date_picker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String date = String.valueOf(date_picker.getValue());
                main.fetchHistory(date);
            }
        });

        TableColumn orderIDCol = mainTable.getVisibleLeafColumn(0);
        orderIDCol.setCellValueFactory(new PropertyValueFactory("orderID"));

        TableColumn customerCol = mainTable.getVisibleLeafColumn(1);
        customerCol.setCellValueFactory(new PropertyValueFactory("customer"));

        TableColumn blockCol = mainTable.getVisibleLeafColumn(2);
        blockCol.setCellValueFactory(new PropertyValueFactory("block"));

        TableColumn rowCol = mainTable.getVisibleLeafColumn(3);
        rowCol.setCellValueFactory(new PropertyValueFactory("row"));

        TableColumn seatCol = mainTable.getVisibleLeafColumn(4);
        seatCol.setCellValueFactory(new PropertyValueFactory("seat"));

        TableColumn quantityCol = mainTable.getVisibleLeafColumn(5);
        quantityCol.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn costCol = mainTable.getVisibleLeafColumn(6);
        costCol.setCellValueFactory(new PropertyValueFactory("cost"));

        TableColumn timeCol = mainTable.getVisibleLeafColumn(7);
        timeCol.setCellValueFactory(new PropertyValueFactory("time"));

        mainTable.setItems(obsOrders);

        itemsList.setItems(foodObservableList);
    }

    public void removeIndividual(String id, String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Order o : obsOrders) {
                    if (o.getOrderID().equals(id) && o.getCustomer().equals(name)) {
                        obsOrders.remove(o);
                        break;
                    }
                }
                mainTable.refresh();
            }
        });
    }

    public void insertOrder(String id, String user, String block, String row, String seat, String quantity, String total, String time, ArrayList<Food> foods) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String formattedPrice = new DecimalFormat("#0.00").format(Double.parseDouble(total));
                Order newO = new Order(id, user, block, row, seat, quantity, formattedPrice, time);
                obsOrders.add(newO);
                mainTable.refresh();
                System.out.println("Food size:\t" + foods.size());
                for (Food food : foods) {
                    for (int i = 0; i < food.getQuantity(); i++) {
                        //Image image = new JavaFXImageConversion().getJavaFXImage(food.getImage(), 50, 50);
                        Image image1 = new Image(new ByteArrayInputStream(food.getImage()));
                        ImageView imageView = new ImageView(image1);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        basket.getChildren().add(imageView);
                    }
                    foodObservableList.add(food);
                }
                itemsList.refresh();
            }
        });
    }

    public void processHistory(String history) {
        String[] splitHistory = history.split("@");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date();
        String dateString = dateFormat.format(curDate);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (reportCount < 10) {
                    reportID.setText("#RID" + String.valueOf(reportCount) + "   ");
                } else {
                    reportID.setText("#RID" + String.valueOf(reportCount));
                }
                reportDate.setText("Date of Report:   " + dateString);
                int count = 0;
                String curS = "";
                Double curValue = 0.0;
                for (Label lb : historyFractions) {
                    if (count == 0) {
                        curS = "Date of Capture: ".concat(splitHistory[count]);
                    } else if (count <= 9) {
                        curValue = Double.parseDouble(splitHistory[count]);
                        curS = new DecimalFormat("##.##").format(curValue);
                    } else {
                        curValue = Double.parseDouble(splitHistory[count]);
                        curS = "R ".concat(new DecimalFormat("#0.00").format(curValue));
                    }
                    lb.setText(curS);
                    count++;
                }
            }
        });
    }

    public void printOut(ArrayList<String> outputStream) {
        VBox contentWindow = new VBox();
        contentWindow.setMinSize(300, 600);

        for (String s : outputStream) {
            Text newT = null;
            if (s.equals("@")) {
                newT = new Text();
            } else {
                newT = new Text(s);
            }
            contentWindow.getChildren().add(newT);
        }
        print(contentWindow, false);
    }

    private void print(Node node, boolean report) {
        PrinterJob job = PrinterJob.createPrinterJob();
        boolean positive = false;
        if (report) {
            Window size = node.getScene().getWindow();
            size.centerOnScreen();
            positive = job.showPrintDialog(size);
        }
        if (job != null && positive || !report) {
            if (report)
                reportCount++;
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

}
