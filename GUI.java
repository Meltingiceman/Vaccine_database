/*
   Author(s): Jacob Jones 
              Kyler Chacon
              Austin Holland
              Shachi Shah
              
   Date: 04/19/2021
   Class: CSE 360, Monday
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;

//needed for general jfree chart stuff
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

//pie chart
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

//bar chart
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;

class GUI extends JFrame
{
   private ArrayList<Person> list;
   private JPanel leftPanel;
   private JPanel middlePanel;
   
   public final int MENU_BUTTON_HEIGHT = 30;
   public final int MENU_BUTTON_WIDTH = 138;
   
   public final Font nameFont = new Font("Default", Font.BOLD, 21);
   
   //just in case the GUI is started without being given a list
   public GUI() 
   {
     //calls the other constructor but passes null as a parameter
     this(null);
   }
   
   //starts the GUI up and loads up the about page by default
   public GUI(ArrayList<Person> l)
   {
      if(l == null) //if no list is given
         list = new ArrayList<Person>();
      else
         list = l;
      
      setSize(750, 600);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      setLayout(new BorderLayout());
      
      //setting up the left panel (buttons)
      setUpLeft();
      
      //default state is about
      about();
   }
   
   public void setUpLeft()
   {  
      leftPanel = new JPanel();
      leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
      
      //creating the buttons
      JButton about = new JButton("About");
      JButton load = new JButton("Load Data");
      JButton add = new JButton("Add Data");
      JButton save = new JButton("Save Data");
      JButton visualize = new JButton("Visualize Data");
      
      //adjusting the size of the buttons
      about.setPreferredSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      about.setMaximumSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      
      load.setPreferredSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      load.setMaximumSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      
      add.setPreferredSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      add.setMaximumSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      
      save.setPreferredSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      save.setMaximumSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      
      visualize.setPreferredSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      visualize.setMaximumSize(new Dimension(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT));
      
      //Setting up action listeners for each button
      about.addActionListener(new AboutButton());
      load.addActionListener(new LoadButton());
      add.addActionListener(new AddButton());
      save.addActionListener(new SaveButton());
      visualize.addActionListener(new VisualizeButton());
      
      //adding the buttons to the left panel
      leftPanel.add(about);
      leftPanel.add(load);
      leftPanel.add(add);
      leftPanel.add(save);
      leftPanel.add(visualize);
      leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      
      //adding the panel to the frame
      add(leftPanel, BorderLayout.LINE_START);
   }
   
   //helper method used to check if an existing id value is in the list
   public int listContains(int id)
   {
     int ix = -1;
     for(int i = 0; i < list.size() && ix == -1; i++)
     {
       if(list.get(i).getID() == id)
         ix = i;
     }

     return ix;
   }
   
   //displays the about page where team info is presented
   public void about()
   {
      if(middlePanel != null)
         remove(middlePanel);
      
      middlePanel = new JPanel();
      middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
      
      JLabel groupNum = new JLabel();
      
      //A label for each name (variable names are the memebers' initials)
      JLabel jj = new JLabel();
      JLabel kc = new JLabel();
      JLabel ah = new JLabel();
      JLabel ss = new JLabel();
      
      groupNum.setFont(nameFont);
      jj.setFont(nameFont);
      kc.setFont(nameFont);
      ah.setFont(nameFont);
      ss.setFont(nameFont);
      
      //group name and members
      groupNum.setText("Group Number: 37");
      jj.setText("Jacob Jones");
      kc.setText("Kyler Chacon");
      ah.setText("Austin Holland");
      ss.setText("Shachi Shah");
      
      //adding the group name labels to the middlePanel
      middlePanel.add(groupNum);
      middlePanel.add(jj);
      middlePanel.add(kc);
      middlePanel.add(ah);
      middlePanel.add(ss);
      
      //adding the middlePanel to the frame
      add(middlePanel, BorderLayout.CENTER);
      
      setVisible(true);
   }
   
   public void loadData(ActionEvent e)
   {
	   /*if we remove the middlePanel here the GUI becomes unresponsive.
        To resolve this we should remove the middlePanel after the user clicks the
        ok/select button when selecting a file.*/
      
	   JFileChooser fileChooser = new JFileChooser();
	   fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	   int result = fileChooser.showOpenDialog(this);
	   String path;
	   String line = "";
	   int count = 0;

     //if the user clicked the okay button   
	   if (result == JFileChooser.APPROVE_OPTION) 
	   {
	      File selectedFile = fileChooser.getSelectedFile();
	      path = selectedFile.getAbsolutePath();
	       
	      try 
		    {
          //read from the file
          BufferedReader br = new BufferedReader(new FileReader(path));
          
          while((line = br.readLine()) != null)
          {
            String[] values = line.split(",");
            String lines = Arrays.toString(values);
            
            for (int i = 0; i < values.length; i++)
            {
              Person p = new Person(Integer.parseInt(values[i++]),
                                    values[i++],
                                    values[i++],
                                    values[i++],
                                    values[i++],
                                    values[i++]);

              //check if data with the same id already exists
              int ix = listContains(p.getID());
              
              //if there is a repeat in ids then delete the old one and add the new one
              if(ix != -1)
                list.remove(ix);

              list.add(p);
            }
          }
        } 
		   catch (FileNotFoundException e1) 
		   {
			   e1.printStackTrace();
		   }
		   catch (IOException e1) 
		   {
			   e1.printStackTrace();
		   }
         
	      displayData();
	   }
   }
   
   public void addData()
   {
      remove(middlePanel);

      //create a new JPanel and set its' layout
      middlePanel = new JPanel();
      middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
      
      //create some panels with a label and a textfield
      AddPanel lastName = new AddPanel("Last Name: ");
      AddPanel firstName = new AddPanel("First Name: ");
      AddPanel id = new AddPanel("Patient ID: ");
      AddPanel vType = new AddPanel("Vaccine Type: ");
      AddPanel vDate = new AddPanel("Vaccine Date: ");
      AddPanel vLocation = new AddPanel("Vaccine Location: ");
      
      //create a confirm button
      JButton confirm = new JButton("Submit");
      
      //add the textfields to the panel
      middlePanel.add(lastName);
      middlePanel.add(firstName);
      middlePanel.add(id);
      middlePanel.add(vType);
      middlePanel.add(vDate);
      middlePanel.add(vLocation);
      
      //sets what the confirm button does when clicked.
      confirm.addActionListener(new ActionListener() //----defining action listener
         { 
            //This method is what is actually performed.
            public void actionPerformed(ActionEvent e)
            {
               Person person = new Person();
               
               //for some reason replit doesn't like these but still runs???
               String lName = lastName.getText();
               String fName = firstName.getText();
               String idRes = id.getText();
               String vTypeStr = vType.getText();
               String vDateStr = vDate.getText();
               String vLocationStr = vLocation.getText();
               
               //if any text field is empty we ask the user to enter the values again
               if(lName.isEmpty() || fName.isEmpty() || idRes.isEmpty() || 
                  vTypeStr.isEmpty() || vDateStr.isEmpty() ||vLocationStr.isEmpty() ){
                  
                  JOptionPane.showMessageDialog(null, "Please fill in all text fields.", "Error", JOptionPane.ERROR_MESSAGE);
                  
                  //exits the actionlistener
                  return;
               }
               
               boolean isNum = true;
               
               //verifying the id is numeric
               for(int i = 0; isNum && i < idRes.length(); i++)
               {
                  //if the char at i is not a number isNum will be set to false
                  isNum = (idRes.charAt(i) >= 48 && idRes.charAt(i) <= 57);
               }
               
               if(!isNum)
               {
                  JOptionPane.showMessageDialog(null, "Id Field should be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
               }
               
               //get the id value from the id text field
               int idVal = Integer.parseInt(idRes);
               
               //create a new person object
               person.setID(idVal);
               person.setLastName(lName);
               person.setFirstName(fName);
               person.setVaccineType(vTypeStr);
               person.setDate(vDateStr);
               person.setLocation(vLocationStr);
               
               //add the new person object to the list if the id is not in the list
               int ix = listContains(idVal);

               //if id is not in the list, then put in the list
               if(ix != -1)
               {
                 list.remove(ix);
               }

               list.add(person);
               //display all the data in list at the moment
               displayData();
            }
         }
      ); //----end of defining action listener

      //sets the size of the button and adds the button to the middlePanel
      confirm.setSize(80, 20);
      middlePanel.add(confirm);
      
      add(middlePanel);
      
      setVisible(true);
   }
   
   //This displays the data after some data is added
   //***this needs some cleaning up***
   public void displayData()
   {
      //remove what's currently there
      remove(middlePanel);
      
      //create labels for the chart
      Object[][] rowData = {};
	    Object[] columnNames = { "ID", "Last Name", "First Name", "Vaccine Type", "Vaccine Date", "Vaccine Location" };
      
      //create a tableList
      DefaultTableModel tableList;
      tableList = new DefaultTableModel(rowData, columnNames);
      
      //create a new JPanel
      middlePanel = new JPanel();
      middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
      
      //add items to the tableList to be displayed
      for(int i = 0; i < list.size(); i++)
      {  
         Person temp = list.get(i);
         tableList.addRow(new Object[] {temp.getID(), temp.getLastName(), temp.getFirstName(),temp.getVaccineType(),
                                        temp.getDate(), temp.getLocation()});
      }
      
      //Setting up the list
      JTable listTable = null;
      listTable = new JTable(tableList);
      listTable.setCellEditor(null);
      listTable.setBounds(37, 143, 397, 183);
      
      //putting the list into a scroll pane
      JScrollPane scroll = new JScrollPane(listTable);
      scroll.getVerticalScrollBar().setUnitIncrement(20);
      
      //adding the scroll panel onto the middlePanel
      middlePanel.add(scroll);
      
      //putting the middle panel onto the pane
      add(middlePanel, BorderLayout.CENTER);
      
      setVisible(true);
   }

   //saves data to a file called export.csv
   public File saveData() 
   {
	   File file = new File("export.csv");
	   try 
     { 
        file.createNewFile();
        FileWriter myWriter = new FileWriter(file);

        //writes the data
        for(int i = 0; i < list.size(); i++)
        {		    	
          Person temp = list.get(i);
          String s = String.valueOf(temp.getID());  
          myWriter.write(s);
          myWriter.write(",");
          myWriter.write(temp.getLastName());
          myWriter.write(",");
          myWriter.write(temp.getFirstName());
          myWriter.write(",");
          myWriter.write(temp.getVaccineType());
          myWriter.write(",");
          myWriter.write(temp.getDate());
          myWriter.write(",");
          myWriter.write(temp.getLocation());
          myWriter.write(" \n");
        }
        myWriter.close();
      
        //creates a message box telling the user that data has been saved
        JOptionPane.showMessageDialog(null, "Data has been saved to file export.csv in this program\'s directory."); 
      } 
      catch (IOException e) //an error has occured
      {
        JOptionPane.showMessageDialog(null, "There was a problem saving this program's data. Data has not been saved", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
      }
	    return file;
   }
   
   //creates a bar/pie chart to display data 
   public void visualize()
   {
      remove(middlePanel);
      
      middlePanel = new JPanel();
      middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
      

      DefaultCategoryDataset barChartData = new DefaultCategoryDataset(); //bar chart
      DefaultPieDataset dataset = new DefaultPieDataset( ); //pie chart
      
      //lists for the pie chart
      ArrayList<String> locations = new ArrayList<String>();
      ArrayList<Integer> locCount = new ArrayList<Integer>();
      
      //lists for the bar chart
      ArrayList<String> vaccines = new ArrayList<String>();
      ArrayList<Integer> vaccineCount = new ArrayList<Integer>();
      
      //creates pie/bar chart datasets for the GUI's to use
      for(int i = 0; i < list.size(); i++)
      {
         
         int ix = locations.indexOf( list.get(i).getLocation() );
         //if the location hasn't been put in the list yet, then add it and give it an initial count of 1
         if(ix == -1) 
         {
            locations.add(list.get(i).getLocation());
            locCount.add(1);
         }
         else //otherwise just add one to index ix
         {
            locCount.set(ix, locCount.get(ix) + 1);
         }
         
         //samething as above but just for the bar chart
         ix = vaccines.indexOf( list.get(i).getVaccineType() );
         if(ix == -1)
         {
            vaccines.add(list.get(i).getVaccineType());
            vaccineCount.add(1);
         }
         else
         {
            vaccineCount.set(ix, vaccineCount.get(ix) + 1);
         }

      }
      
      //create the pie and bar charts
      JFreeChart chart = ChartFactory.createPieChart("Locations",
                                                    dataset,
                                                    true,
                                                    true,
                                                    false);
                                                    
      JFreeChart barChart = ChartFactory.createBarChart("Vaccination Types",
                                                        "Vaccine type",
                                                        "Amount",
                                                        barChartData,
                                                        PlotOrientation.VERTICAL,
                                                        true, true, false);
      
      //add the new datasets to their respective graphs
      for(int i = 0; i < locations.size(); i++)
      {
         dataset.setValue(locations.get(i), new Double( locCount.get(i) ));
      }

      for(int i = 0; i < vaccines.size(); i++)
      {
         barChartData.addValue( vaccineCount.get(i), vaccines.get(i), "");
      } 
      
      //put the pie/bar chart onto a new JPanel
      JPanel temp = new JPanel();
      temp.add(new ChartPanel(barChart));
      temp.add(new ChartPanel(chart));
      temp.setMinimumSize(new Dimension(300, 1200));

      //Make that JPanel scrollable
      JScrollPane scroll = new JScrollPane(temp);
      scroll.getVerticalScrollBar().setUnitIncrement(20);
      scroll.setMinimumSize(new Dimension(300, 1200));
        
      //Add that scrollable panel onto the middlePanel
      middlePanel.add(scroll);
      add(middlePanel, BorderLayout.CENTER);
      setVisible(true);
   }
   
   //Below are action listeners for all the main buttons.
   //The name of the classes corespond to which button they belong to.
   private class AboutButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         about();
      }
   }
   
   private class LoadButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
        try 
        {
			    loadData(e);
		    } 
        catch (Exception e1) 
        {
			    e1.printStackTrace();
		    }
      }
   }
   
   private class AddButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         addData();
      }
   }
   
   private class SaveButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         saveData();
      }  
   }
   
   private class VisualizeButton implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {     
         visualize();
      }
   }
}

//A custom panel used when adding data about another patient
class AddPanel extends JPanel
{
   private JLabel label;
   private JTextField dataEntry;
   
   public AddPanel(String labelText)
   {
      label = new JLabel(labelText);
      dataEntry = new JTextField(20);
      
      add(label);
      add(dataEntry);
      
   }
   
   public String getText()
   {
      return dataEntry.getText();
   }
}

//A custom JPanel for use displaying patient info
class PatientPanel extends JPanel
{
   private JLabel lastName;
   private JLabel firstName;
   private JLabel id;
   private JLabel vLocation;
   private JLabel vDate;
   private JLabel vType;
   
   public PatientPanel(String lName, String fName, int _id, String vLoc,
                        String _vDate, String _vType) {
      lastName = new JLabel(lName);
      firstName = new JLabel(fName);
      id = new JLabel(Integer.toString(_id));
      vLocation = new JLabel(vLoc);
      vDate = new JLabel(_vDate);
      vType = new JLabel(_vType);
      
      add(lastName);
      //This is supposed to create some padding between the labels
      add(Box.createHorizontalStrut(10));
      
      add(firstName);
      add(Box.createHorizontalStrut(10));
      
      add(id);
      add(Box.createHorizontalStrut(10));
      
      add(vLocation);
      add(Box.createHorizontalStrut(10));
      
      add(vDate);
      add(Box.createHorizontalStrut(10));
      
      add(vType);
      add(Box.createHorizontalStrut(10));
   }
   
   public PatientPanel()
   {
      lastName = new JLabel("Last Name");
      firstName = new JLabel("First Name");
      id = new JLabel("ID");
      vLocation = new JLabel("Vaccine Location");
      vDate = new JLabel("Vaccine Date");
      vType = new JLabel("Vaccine Type");
      
      add(lastName);
      add(Box.createHorizontalStrut(10));
      
      add(firstName);
      add(Box.createHorizontalStrut(10));
      
      add(id);
      add(Box.createHorizontalStrut(10));
      
      add(vLocation);
      add(Box.createHorizontalStrut(10));
      
      add(vDate);
      add(Box.createHorizontalStrut(10));
      
      add(vType);
      add(Box.createHorizontalStrut(10));
   }
}

//driver for testing the GUI
class GUITester
{
   public static void main(String[] args)
   {
      ArrayList<Person> people = new ArrayList<Person>();

      //This starts the GUI
      GUI gui = new GUI(people);
   }
}