package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main extends Application {

    private static final String ip = "csdev.nmmu.ac.za";
    //private static final String ip = "10.112.49.25";

    private Controller controller;
    private Socket socket;
    private ServerThread listener;
    private DataInputStream in;
    private DataOutputStream out;
    private String dispatchName = "0";
    private String realName = "Dispatch 200";
    private static Boolean closed = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainRoot.fxml"));
        Scene scene = new Scene(root, 1200, 875);

        controller = new Controller(this);
        controller.connectToUI(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    closed = true;
                    out.writeUTF("#DEREGISTER");
                    out.writeUTF(dispatchName);
                    out.flush();
                    System.out.println("Dispatch successfully de-registered.");
                    System.out.println("Dispatch has been removed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        primaryStage.setTitle("Brew NMBS: " + realName);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Main() {
        if (connect()) {
            // start thread that receives messages from server
            listener = new ServerThread(this, in);
            listener.start();

        } else {
            // could not connect
            System.out.println("Could not establish connection, shutting down...");
        }
    }

    public boolean connect() {
        try {
            // attempt to connect to server
            socket = new Socket(ip, 8050);
            System.out.println("Socket initialized");

            // obtain streams
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Connection established...");
            out.writeUTF("#DISPATCH_REGISTRATION");
            out.writeUTF(dispatchName);
            System.out.println("Dispatch registered at server.");

        } catch (UnknownHostException e) {
            System.out.println("ERROR: Unknown Host - " + e.getMessage());
            return false;

        } catch (IOException e) {
            System.out.println("ERROR: IOException - " + e.getMessage());
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
        if (!closed) {
            new Main();
        }
    }

    public void removeFromScreen(String id, String username) {
        controller.removeIndividual(id, username);
    }

    public void insertOrder(String id, String user, String block, String row, String seat, String quantity, String total, String time, ArrayList<Food> foods) {
        controller.insertOrder(id, user, block, row, seat, quantity, total, time, foods);
    }

    public void fetchHistory(String date) {
        try {
            out.writeUTF("#FETCH_HISTORY");
            out.writeUTF(date);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendHistory(String history) {
        controller.processHistory(history);
    }

    public void printOut(ArrayList<String> outputStream) {
        controller.printOut(outputStream);
    }
}
