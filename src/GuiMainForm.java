import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
