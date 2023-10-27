import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                  addItemDialog.dispose();
                  String title=titleTextField.getText();
                  String author=authorTextField.getText();
                  int year=Integer.parseInt(publicationTextField.getText());
                  Book book=new Book(title,0,author,year);
                  addRow(book);
                  //check if all cells are full and save data
               }
            });



         }
      });

      JButton b2 = new JButton("Edit Item");
      JButton b3 = new JButton("Delete Item");
      JButton b4 =new JButton("Popularity Count");

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

   public void addRow(@NotNull Book book){
      String[] strings=new String[3];
      strings[0]=book.getTitle();
      strings[1]=book.getAuthor();
      strings[2]= String.valueOf(book.getYear());

      booksTableModel.addRow(strings);

   }
}
