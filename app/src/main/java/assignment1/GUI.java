package assignment1;
import java.util.*;
// import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
// import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.*;


public class GUI implements ActionListener{



    private AdminUser admin = new AdminUser("Popular_currencies.csv","History.csv");
    private NormalUser user = new NormalUser("Popular_currencies.csv","History.csv");

    //history and stats xxx
    JFrame his_f= new JFrame();
    JPanel his_pane = new JPanel();

    JTextField his_in1= new JTextField(3);
    JTextField his_in2= new JTextField(3);
    JTextField his_in3= new JTextField(5);
    JTextField his_in4= new JTextField(3);
    JTextField his_in5= new JTextField(3);
    JTextField his_in6= new JTextField(5);
    JTextField his_in7= new JTextField(3);
    JTextField his_in8= new JTextField(3);

    //converter xxx
    JFrame con_f= new JFrame();
    JPanel con_pane = new JPanel();

    JTextField con_in1= new JTextField(3);
    JTextField con_in2= new JTextField(3);
    JTextField con_in3= new JTextField(6);
    JLabel con_result= new JLabel("");

    JTable pop_table;
    JTable stats_table;
    JTable his_table;

    //new_pop xxx
    JFrame pop_f= new JFrame();
    JPanel pop_pane = new JPanel();
    JTextField pop_in1= new JTextField(3);
    JTextField pop_in2= new JTextField(3);
    JTextField pop_in3= new JTextField(3);
    JTextField pop_in4= new JTextField(3);
    JTextField pop_pass= new JTextField(9);
    JLabel pop_result= new JLabel("");

    //new_rate
    JFrame rat_f= new JFrame();
    JPanel rat_pane = new JPanel();
    JTextField rat_in1= new JTextField(3);
    JTextField rat_in2= new JTextField(3);
    JTextField rat_in3= new JTextField(6);
    JTextField rat_pass= new JTextField(9);
    JLabel rat_result= new JLabel("");

    JFrame full_his=new JFrame();
    JScrollPane full_his_panel= new JScrollPane();


    boolean created_popular_currency_table=false;

    boolean created_stat_table=false;
    String[][] stats_data=new String[2][5];
    String[][] history_data=new String[30][1];
    String[][] pop_data;

    public GUI() {
        System.out.print("runn");
    }

    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("Converter")){
        if (!this.created_popular_currency_table){
          String[] header = {"","","","",""};
          this.pop_data=this.user.create_table();
          this.pop_table = new JTable(this.pop_data, header);
          this.pop_table.setBounds(600,600, 600, 600);
          this.con_pane.add(this.pop_table);
          created_popular_currency_table=true;
        }else{
          String[][] new_data=this.user.create_table();
          for(int i=0;i<this.pop_data.length;i++){
            for (int j=0;j<this.pop_data.length;j++){
              this.pop_data[i][j]=new_data[i][j];
            }
          }
          this.pop_table.revalidate();
        }
          this.con_f.setVisible(true);

      }else if (e.getActionCommand().equals("Convert")){
          this.con_result.setText(user.convert_currencies(this.con_in1.getText(),this.con_in2.getText(),Double.parseDouble(this.con_in3.getText()))+" "+this.con_in2.getText());

      }else if (e.getActionCommand().equals("Update rate")){
          this.rat_f.setVisible(false);
          if (this.rat_pass.getText().equals("squeakydoor")){
            admin.set_new_rate(this.rat_in1.getText(),this.rat_in2.getText(),Double.parseDouble(this.rat_in3.getText()));
            this.rat_result.setText("Success");
          }else{
            this.rat_result.setText("Wrong password, no changes were made");
          }
          this.rat_f.setVisible(true);

      }else if (e.getActionCommand().equals("(Admin) Update rate")){
          this.rat_f.setVisible(true);

      }else if (e.getActionCommand().equals("Update list")){
          this.pop_f.setVisible(false);
        if (this.pop_pass.getText().equals("squeakydoor")){
          String[] xyz= {this.pop_in1.getText(),this.pop_in2.getText(),this.pop_in3.getText(),this.pop_in4.getText()};
          admin.set_popular_currencies(xyz);
          this.pop_result.setText("Success");
        }else{
          this.pop_result.setText("Wrong password, no changes were made");
        }
        this.pop_f.setVisible(true);

      }else if (e.getActionCommand().equals("(Admin) Update popular")){
          this.pop_f.setVisible(true);
      }else if (e.getActionCommand().equals("History lookup")){
          this.his_f.setVisible(true);
      }else if (e.getActionCommand().equals("Search")){
          this.his_f.setVisible(false);

          String[] stat= user.get_summary(
            this.his_in7.getText(),
            this.his_in8.getText(),
            this.his_in1.getText()+"-"+this.his_in2.getText()+"-"+this.his_in3.getText(),
            this.his_in4.getText()+"-"+this.his_in5.getText()+"-"+this.his_in6.getText());
          System.out.println(Arrays.toString(stat));
          System.out.println(Arrays.toString(this.stats_data));
          ArrayList<String> data= user.get_interval_history_string_array(
            this.his_in7.getText(),
            this.his_in8.getText(),
            this.his_in1.getText()+"-"+this.his_in2.getText()+"-"+this.his_in3.getText(),
            this.his_in4.getText()+"-"+this.his_in5.getText()+"-"+this.his_in6.getText()
          );
          System.out.println(this.history_data);
          System.out.println(data);

          for (int i = 0; i < this.history_data.length; i++){
            if (i<data.size()){
              this.history_data[i][0]=data.get(i);
            }else{
              this.history_data[i][0]="";
            }
          }

          for (int i = 0; i < stat.length; i++) {
           this.stats_data[1][i]=stat[i];
         }

          if (!this.created_stat_table){
            this.stats_data[0][0]="Mean";
            this.stats_data[0][1]="Median";
            this.stats_data[0][2]="Max";
            this.stats_data[0][3]="Min";
            this.stats_data[0][4]="Standard Deviation";
            String[] header= {"","","","",""};
            this.stats_table = new JTable(this.stats_data,header);
            this.his_pane.add(this.stats_table);
            this.his_pane.revalidate();

            String[] hea= {""};
            this.his_table = new JTable(this.history_data,hea);
            this.his_table.setSize(800,700);
            this.full_his_panel.add(his_table);
            this.full_his_panel.revalidate();
            this.full_his.setVisible(true);

            this.created_stat_table=true;
          }else{
            this.stats_table.revalidate();
            this.full_his.setVisible(false);
            this.his_table.revalidate();
            this.full_his.setVisible(true);
          }
          this.his_f.setVisible(true);

      }

    }
    public void run(){
//setup main frame
            Frame welcome=new Frame("Currency App");

            welcome.setLayout(new GridLayout(2,2));
            welcome.setSize(600, 300);

// quit program if click x on main window
            welcome.addWindowListener(new WindowAdapter(){
              public void windowClosing(WindowEvent we){
                System.exit(0);}});

// setup event listener on buttons

            Button rate_history = new Button("History lookup");
            rate_history.addActionListener(this);
            Button conv = new Button("Converter");
            conv.addActionListener(this);
            Button rate_update = new Button("(Admin) Update rate");
            rate_update.addActionListener(this);
            Button popular_update = new Button("(Admin) Update popular");
            popular_update.addActionListener(this);
//adding button to the frame

            welcome.add(rate_history);
            welcome.add(conv);
            welcome.add(rate_update);
            welcome.add(popular_update);
//make the main frame visible on start
            welcome.setVisible(true);


//setup converter Frame

          this.con_f.setSize(700,200);
          this.con_f.setTitle("Converter and Popular exchange rates");
          this.con_pane.add(new JLabel("Currencies: "));
          this.con_pane.add(this.con_in1);
          this.con_pane.add(this.con_in2);
          this.con_pane.add(new JLabel("amount"));
          this.con_pane.add(this.con_in3);
          Button converter = new Button("Convert");
          converter.addActionListener(this);
          this.con_pane.add(converter);
          this.con_pane.add(this.con_result);
          this.con_f.add(this.con_pane);
          this.con_f.setVisible(false);

// history Frame
          this.his_f.setSize(700,200);
          this.his_f.setTitle("History lookup");
          this.his_pane.add(new JLabel("From "));
          this.his_pane.add(this.his_in1);
          this.his_pane.add(this.his_in2);
          this.his_pane.add(this.his_in3);
          this.his_pane.add(new JLabel(" to "));
          this.his_pane.add(this.his_in4);
          this.his_pane.add(this.his_in5);
          this.his_pane.add(this.his_in6);
          this.his_pane.add(new JLabel("currencies"));
          this.his_pane.add(this.his_in7);
          this.his_pane.add(this.his_in8);
          Button search= new Button("Search");
          search.addActionListener(this);
          this.his_pane.add(search);
          this.his_f.add(his_pane);
          this.his_f.setVisible(false);

// change rate Frame
          this.rat_f.setSize(700,200);
          this.rat_f.setTitle("Update exchange rate (Admin)");
          this.rat_pane.add(new JLabel("Currencies"));
          this.rat_pane.add(this.rat_in1);
          this.rat_pane.add(this.rat_in2);
          this.rat_pane.add(new JLabel("change rate to"));
          this.rat_pane.add(this.rat_in3);
          this.rat_pane.add(new JLabel(". Admin password: "));
          this.rat_pane.add(this.rat_pass);
          Button n = new Button("Update rate");
          n.addActionListener(this);
          this.rat_pane.add(n);

          this.rat_pane.add(this.rat_result);
          this.rat_f.add(rat_pane);
          this.rat_f.setVisible(false);

// change popular frame
          this.pop_f.setSize(700,200);
          this.pop_f.setTitle("Update popular exchange rate (Admin)");
          this.pop_pane.add(new JLabel("Four new popular currencies: "));
          this.pop_pane.add(this.pop_in1);
          this.pop_pane.add(this.pop_in2);
          this.pop_pane.add(this.pop_in3);
          this.pop_pane.add(this.pop_in4);
          this.pop_pane.add(new JLabel(". Admin password: "));
          this.pop_pane.add(this.pop_pass);
          Button s = new Button("Update list");
          s.addActionListener(this);
          this.pop_pane.add(s);
          this.pop_pane.add(this.pop_result);
          this.pop_f.add(pop_pane);
          this.pop_f.setVisible(false);
// history list Frame
          this.full_his.setSize(700,200);
          this.full_his.setTitle("History");
          this.full_his.add(this.full_his_panel);
          this.full_his.setVisible(false);
    }
  }
