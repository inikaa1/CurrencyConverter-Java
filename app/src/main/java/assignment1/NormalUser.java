package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class NormalUser {

   protected String config_path;
   protected String data_path;

   public NormalUser(String config_path, String data_path) {
      this.config_path = config_path;
      this.data_path = data_path;
   }

   public ArrayList<String[]> History(String from, String to) {
      ArrayList<String[]> HistoryList = new ArrayList<String[]>();
      try {
         Scanner scanner = new Scanner(new File(data_path));
         while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(",");
            if (temp[0].equals(from) && temp[1].equals(to)) {
                HistoryList.add(temp);
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      return HistoryList;
   }

   public ArrayList<Double> get_interval_history(String from, String to, String start_date_str, String end_date_str) {
      ArrayList<Double> HistoryList = new ArrayList<Double>();
      LocalDate start_date = null;
      LocalDate end_date = null;

      // instantiate SimpleDateFormat class
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      // parse given strings to date objects
      try {
         start_date = LocalDate.parse(start_date_str, formatter);
         end_date = LocalDate.parse(end_date_str, formatter);
      } catch (DateTimeParseException e) {
         e.printStackTrace();
      }

      try {
         Scanner scanner = new Scanner(new File(data_path));
         while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(",");
            LocalDate temp_date = LocalDate.of(Integer.parseInt(temp[5]), Integer.parseInt(temp[4]), Integer.parseInt(temp[3]));

            // if currencies match, select conversion rates between specified start and end dates
            if (temp[0].equals(from) && temp[1].equals(to)) {
            if (temp_date.isAfter(start_date) && temp_date.isBefore(end_date)) {
                  HistoryList.add(Double.parseDouble(temp[2]));
            }
            }
         }
         return HistoryList;
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      return HistoryList;
   }

   public ArrayList<String> get_interval_history_string_array(String from, String to, String start_date_str, String end_date_str) {
      ArrayList<String> HistoryList = new ArrayList<String>();
      LocalDate start_date = null;
      LocalDate end_date = null;

      // instantiate SimpleDateFormat class
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      // parse given strings to date objects
      try {
         start_date = LocalDate.parse(start_date_str, formatter);
         end_date = LocalDate.parse(end_date_str, formatter);
      } catch (DateTimeParseException e) {
         e.printStackTrace();
      }

      try {
         Scanner scanner = new Scanner(new File(data_path));
         while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(",");
            LocalDate temp_date = LocalDate.of(Integer.parseInt(temp[5]), Integer.parseInt(temp[4]), Integer.parseInt(temp[3]));

            // if currencies match, select conversion rates between specified start and end dates
            if (temp[0].equals(from) && temp[1].equals(to)) {
            if (temp_date.isAfter(start_date) && temp_date.isBefore(end_date)) {
                  HistoryList.add("Set at "+temp[2]+" on "+temp[3]+"/"+temp[4]+"/"+temp[5]);
            }
            }
         }
         return HistoryList;
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      return HistoryList;
   }

   public double get_exchange_rate(String from, String to) {
      if (this.History(from, to).size() != 0) {
         return (Double.parseDouble(this.History(from, to).get(this.History(from, to).size() - 1)[2]));
      }
      return -1.0;
   }

   public double convert_currencies(String from,String to,double amount) {
      return this.get_exchange_rate(from,to)*amount;
   }

   public String[] get_popular_currencies(){
      String[] Currencies= new String[4];
      try{
        Scanner scanner = new Scanner(new File(config_path));
        int i=0;
        while (scanner.hasNextLine()&&i<4) {
          Currencies[i]=scanner.nextLine();
          i++;}
      }catch (FileNotFoundException e) {
        e.printStackTrace();
    }
      return Currencies;
  }

   public boolean check_increase(String from, String to) {
      if(this.History(from, to).size() < 2) {
         return false;
      }else{
      return (Double.parseDouble(this.History(from, to).get(this.History(from, to).size() - 2)[2]) < Double.parseDouble(this.History(from, to).get(this.History(from, to).size() - 1)[2]));}
   }

   public boolean check_decrease(String from, String to) {
      if(this.History(from, to).size() < 2) {
         return false;
      }else{
      return (Double.parseDouble(this.History(from, to).get(this.History(from, to).size() - 2)[2]) > Double.parseDouble(this.History(from, to).get(this.History(from, to).size() - 1)[2]));}
   }

   public double get_mean(String from,String to, String start_date, String end_date) {
      ArrayList<Double> HistoryList = this.get_interval_history(from, to, start_date, end_date);
      double sum=0;

      for (Double conversion_rate: HistoryList){
         sum+=conversion_rate;
      }
      return (double)Math.round((sum/HistoryList.size())*10d)/10d;
   }

   public double get_std_dev(String from,String to, String start_date, String end_date) {
      ArrayList<Double> HistoryList = this.get_interval_history(from, to, start_date, end_date);
      double std_dev = 0.0;
      double difference = 0.0;
      double sum = 0.0;
      double mean = 0.0;

      // calculate mean
      for (Double conversion_rate: HistoryList){
        sum += conversion_rate;
      }
      mean = sum/HistoryList.size();

      // calculate standard deviation
      for (Double conversion_rate: HistoryList){
        difference = conversion_rate - mean;
        std_dev += difference * difference;
      }
      return (double)Math.round(Math.sqrt(std_dev/HistoryList.size())*1000d)/1000d;
   }

   public double get_median(String from, String to, String start_date, String end_date) {
    ArrayList<Double> HistoryList = this.get_interval_history(from, to, start_date, end_date);
    Collections.sort(HistoryList);

    // if odd number of values
    if (HistoryList.size() % 2 == 1) {
        return HistoryList.get((HistoryList.size() - 1) / 2);
    // if even number of values
    } else {
        Double num1 = HistoryList.get((HistoryList.size() / 2) - 1);
        Double num2 = HistoryList.get(HistoryList.size() / 2);
        return (num1 + num2) / 2.0;
    }
   }

   public double get_min_value(String from, String to, String start_date, String end_date) {
    ArrayList<Double> HistoryList = this.get_interval_history(from, to, start_date, end_date);
    Collections.sort(HistoryList);
    return HistoryList.get(0);
   }

   public double get_max_value(String from, String to, String start_date, String end_date) {
    ArrayList<Double> HistoryList = this.get_interval_history(from, to, start_date, end_date);
    Collections.sort(HistoryList);
    return HistoryList.get(HistoryList.size() - 1);
   }

   public String[] get_summary(String from, String to, String start_date, String end_date) {
    double average = get_mean(from, to, start_date, end_date);
    double median = get_median(from, to, start_date, end_date);
    double max = get_max_value(from, to, start_date, end_date);
    double min = get_min_value(from, to, start_date, end_date);
    double std_dev = get_std_dev(from, to, start_date, end_date);

    String[] summary =  {String.valueOf(average), String.valueOf(median), String.valueOf(max), String.valueOf(min), String.valueOf(std_dev)};

    return summary;
   }

   public String[][] create_table() {
      String[] curr = this.get_popular_currencies();

      String[][] table = new String[5][5];

      for (int i=1; i<table.length;i++){
        table[0][i]=curr[i-1];
        table[i][0]=curr[i-1];
      }


      for (int row = 1; row < table.length; row++) {
         for (int col = 1; col < table[row].length; col++) {
            if(row==col){
                table[row][col] = "-";
            }else if(this.check_increase(curr[row - 1], curr[col - 1])) {
                table[row][col] = Double.toString(this.get_exchange_rate(curr[row - 1], curr[col - 1])) + " I";

            }else if(this.check_decrease(curr[row - 1], curr[col - 1])) {
                table[row][col] = Double.toString(this.get_exchange_rate(curr[row - 1], curr[col - 1])) + " D";

            }else{
                table[row][col] = Double.toString(this.get_exchange_rate(curr[row - 1], curr[col - 1]));
            }
         }}
      table[0][0]="FROM / TO";

      return table;

   }


}
