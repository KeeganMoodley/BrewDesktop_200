package sample;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by s213463695 on 2016/07/23.
 */
public class ServerThread extends Thread {

    private Main main;
    private DataInputStream in;
    static ArrayList<Food> foods = new ArrayList<>();
    private static final String PRINT_OUT = "#PRINT_OUT";
    private static final String REMOVE = "#REMOVE_FROM_DESKTOP";
    private static final String ADD = "#ADD_TO_DESKTOP";
    private static final String HISTORY = "#HISTORY_STRING";
    private static final String SHUTDOWN = "#DEREGISTERED";

    @Override
    public void run() {
        try {
            boolean shutdown = false;
            // loop that communicates with server until shutting down
            while (!shutdown) {

                String command = in.readUTF();
                // do something depending on command
                switch (command) {
                    case PRINT_OUT:
                        //readInformation(); //Do not print now
                        break;
                    case REMOVE:
                        String id = in.readUTF();
                        String username = in.readUTF();
                        main.removeFromScreen(id, username);
                        break;
                    case ADD:
                        readAddition();
                        break;
                    case HISTORY:
                        String history = in.readUTF();
                        main.sendHistory(history);
                        break;
                    case SHUTDOWN:
                        shutdown = true;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: IOException - " + e.getMessage());
        }
    }

    public ServerThread(Main main, DataInputStream in) {
        this.main = main;
        this.in = in;
    }

    private void readAddition() throws IOException {
        String id = in.readUTF();
        String block = in.readUTF();
        String row = in.readUTF();
        String seat = in.readUTF();
        String user = in.readUTF();
        String quantity = in.readUTF();
        String total = in.readUTF();
        String time = in.readUTF();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            int foodID = in.readInt();
            int foodQuantity = in.readInt();
            byte[] image = new byte[in.readInt()];
            in.readFully(image);
            String title = in.readUTF();
            foods.add(new Food(foodQuantity, foodID, title, image));
        }
        main.insertOrder(id, user, block, row, seat, quantity, total, time, foods);
    }

    private void readInformation() throws IOException {
        ArrayList<String> outputStream = new ArrayList<>();

        int outputSize = Integer.parseInt(in.readUTF());
        String newOutput = "";
        for (int i = 0; i < outputSize; i++) {
            newOutput = in.readUTF();
            outputStream.add(newOutput);
        }
        main.printOut(outputStream);
    }
}
