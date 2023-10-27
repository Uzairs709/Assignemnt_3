import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.ErrorManager;

//TODO: Make Table non edit table
public class GuiMainForm {
   JFrame jFrame;
   DefaultTableModel booksTableModel;
   JTable booksShelf;
   GuiMainForm() {
      jFrame = new JFrame("Library Management System");
      jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jFrame.setSize(700, 600);
      jFrame.setVisible(true);
      jFrame.setLayout(new BorderLayout());

      JButton b1 = new JButton("Add Item");
      b1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            addItems();
         }
      });

      JButton b2 = new JButton("Edit Item");
      JButton b3 = new JButton("Delete Item");
      JButton b4 =new JButton("Popularity Count");
      b4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ArrayList<Integer> popularityCounts=Main.getAllPopularityCount();
            ArrayList<String> bookNames=Main.getBookNames();
            showBarGraph(popularityCounts,bookNames);
         }
      });

      JPanel bottomPanel = new JPanel(new FlowLayout());

      bottomPanel.setBackground(new Color(0x807B7B));
      bottomPanel.add(b1);
      bottomPanel.add(b2);
      bottomPanel.add(b3);
      bottomPanel.add(b4);

      jFrame.add(bottomPanel, BorderLayout.SOUTH);

      String[] columnName={"Title", "Author", "Publication Year", "Read Item"};
      booksTableModel=new DefaultTableModel(columnName,0);

      booksShelf=new JTable(booksTableModel);
      booksShelf.setShowGrid(true);
      JScrollPane scrollPaneTable=new JScrollPane(booksShelf);
      booksShelf.setSize(700,30);
      jFrame.add(scrollPaneTable,BorderLayout.NORTH);
   }

   void showBarGraph(ArrayList<Integer> popularityCounts,ArrayList<String> bookNames){
      JFrame barGraph=new JFrame();
      barGraph.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      barGraph.setVisible(true);
      barGraph.setSize(700,600);

      JPanel graphPanel=new JPanel(){
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int barWidth = 40;
            int spacing = 20;
            int x = 60; // Adjust the initial x position for the first bar
            int maxData = popularityCounts.stream().max(Integer::compare).orElse(0);

            // Draw Y-axis labels and values
            g.setColor(Color.BLACK);
            g.drawLine(50, 30, 50, getHeight() - 30);
            for (int i = 0; i <= 10; i++) {
               int y = getHeight() - 30 - (i * (getHeight() - 60) / 10);
               g.drawString(Integer.toString(i * (maxData / 10)), 10, y);
               g.drawLine(45, y, 50, y);
            }

            // Set the origin of the bars to the bottom of the graph
            int yOrigin = getHeight() - 30;

            for (int i = 0; i < popularityCounts.size(); i++) {
               int barHeight = (int) (((double) popularityCounts.get(i) / maxData) * (getHeight() - 60));
               g.setColor(Color.PINK);
               g.fillRect(x, yOrigin - barHeight, barWidth, barHeight);
               g.setColor(Color.BLACK);
               g.drawRect(x, yOrigin - barHeight, barWidth, barHeight);

               // Rotate the X-axis labels by 90 degrees
               Graphics2D g2d = (Graphics2D) g.create();
               g2d.translate(x + barWidth / 2, yOrigin - 10);
               g2d.rotate(Math.toRadians(45));
               g2d.drawString(bookNames.get(i), 0, 0);
               g2d.dispose();
               x += barWidth + spacing;

            }
         }
      };

      barGraph.add(graphPanel);
   }

   public void addRow(@NotNull Book book){
      String[] strings=new String[3];
      strings[0]=book.getTitle();
      strings[1]=book.getAuthor();
      strings[2]= String.valueOf(book.getYear());

      booksTableModel.addRow(strings);

   }

   private void addItems(){
      Dialog addItemDialog= new Dialog(jFrame);
      addItemDialog.setVisible(true);
      addItemDialog.setSize(500,150);
      addItemDialog.setLayout(new BorderLayout());


      JButton done=new JButton("Done");

      JLabel title=new JLabel("Title:");
      JTextField titleTextField=new JTextField(20);

      JTextField authorTextField=new JTextField(20);
      JLabel author=new JLabel("Author:");

      JLabel publicationYear=new JLabel("Publication Year:");
      JTextField publicationTextField=new JTextField(20);

      JPanel panel=new JPanel(new GridLayout(4,3));
      panel.add(title);
      panel.add(titleTextField);

      panel.add(author);
      panel.add(authorTextField);

      panel.add(publicationYear);
      panel.add(publicationTextField);

      addItemDialog.add(panel,BorderLayout.NORTH);
      addItemDialog.add(done,BorderLayout.SOUTH);

      done.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            if(titleTextField.getText().isEmpty()||authorTextField.getText().isEmpty()||publicationTextField.getText().isEmpty()){
               JOptionPane.showMessageDialog(addItemDialog, "Please enter all values for the text field.", "Empty Text Field", JOptionPane.ERROR_MESSAGE);
            }else{
               String title=titleTextField.getText();
               String author= authorTextField.getText();
               Integer year= Integer.valueOf(publicationYear.getText());
               Book book=new Book(title,0,author,year);
               addRow(book);
               addItemDialog.dispose();
            }


         }
      });

      addItemDialog.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            addItemDialog.dispose();
         }
      });
   }
}
