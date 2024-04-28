package assignment1;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class AdminUser extends NormalUser {

    public AdminUser(String config_path, String data_path) {
        super(config_path, data_path);
    }

    public void set_new_rate(String from, String to, double amount) {
        File file = new File(data_path);
        Calendar date = Calendar.getInstance();
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(from + "," + to + "," + Double.toString(amount) + "," + date.get(Calendar.DATE) + ","
                    + date.get(Calendar.MONTH) + "," + date.get(Calendar.YEAR) + "," + date.get(Calendar.HOUR_OF_DAY)
                    + "," + date.get(Calendar.MINUTE) + "," + date.get(Calendar.SECOND) + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set_popular_currencies(String[] Currencies) {
        File file = new File(config_path);
        try {
            FileWriter fw = new FileWriter(file);
            for (String Currency : Currencies) {
                fw.write(Currency + "\n");
            }
            fw.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
