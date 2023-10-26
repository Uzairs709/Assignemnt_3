import javax.swing.*;
import java.awt.*;

public class GuiMainForm {
    JFrame jFrame;
    GuiMainForm(){
       jFrame=new JFrame("Library Management System");
       jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jFrame.setSize(700,600);
       jFrame.setVisible(true);
       jFrame.setLayout(new BorderLayout());
       jFrame.setResizable(false);

       Button b1=new Button("Add Item");
       Button b2=new Button("Edit Item");
       Button b3=new Button("Delete Item");

       JPanel bottomPanel =new JPanel(new FlowLayout());
       bottomPanel.add(b1);
       bottomPanel.add(b2);
       bottomPanel.add(b3);

       jFrame.add(bottomPanel,BorderLayout.SOUTH);
    }
}
