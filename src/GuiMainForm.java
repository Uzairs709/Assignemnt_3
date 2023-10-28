import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiMainForm {
   JFrame jFrame;
   static DefaultTableModel booksTableModel;
   static JTable booksShelf;
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
      b2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Integer selectedField=booksShelf.getSelectedRow();
            if(selectedField!=null){
            editItem(selectedField);
            }
            else {
               JOptionPane.showMessageDialog(jFrame, "Please Select the row to edit.", "Row Not Selected", JOptionPane.ERROR_MESSAGE);

            }
         }
      });

      JButton b3 = new JButton("Delete Item");
      b3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            deleteItem();
         }
      });

      JButton b4 =new JButton("Popularity Count");
      b4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ArrayList<Integer> popularityCounts=Main.getAllPopularityCount();
            ArrayList<String> bookNames=Main.getBookNames();
            showBarGraph(popularityCounts,bookNames);
         }
      });

      JButton b5 =new JButton("Update File");
      b5.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Main.saveDataToFile();
               JOptionPane.showMessageDialog(jFrame, "Data Updated in file", "Update done", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
               JOptionPane.showMessageDialog(jFrame, "Data Updated in File Failed", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
         }
      });

      JPanel bottomPanel = new JPanel(new FlowLayout());

      bottomPanel.setBackground(new Color(0x807B7B));
      bottomPanel.add(b1);
      bottomPanel.add(b2);
      bottomPanel.add(b3);
      bottomPanel.add(b4);
      bottomPanel.add(b5);

      jFrame.add(bottomPanel, BorderLayout.SOUTH);

      String[] columnName={"Title", "Author", "Publication Year", "Read Item"};
      booksTableModel=new DefaultTableModel(columnName,0){
         @Override
         public boolean isCellEditable(int row, int column)
         {
            return column == 3;
         }
      };
      booksShelf=new JTable(booksTableModel);
      booksShelf.setShowGrid(true);
      JScrollPane scrollPaneTable=new JScrollPane(booksShelf);
      booksShelf.setSize(700,30);
      jFrame.add(scrollPaneTable,BorderLayout.NORTH);


      booksShelf.addMouseMotionListener(new MouseAdapter() {
         @Override
         public void mouseMoved(MouseEvent e) {
            int row = booksShelf.rowAtPoint(e.getPoint());
            if (row >= 0) {
               booksShelf.getSelectionModel().setSelectionInterval(row, row);
               booksShelf.setSelectionBackground(Color.pink);
            } else {
               booksShelf.getSelectionModel().clearSelection();
            }
         }
      });

      booksShelf.getColumnModel().getColumn(3).setCellRenderer(new RenderButtonForTable());
      booksShelf.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorForTable(new JTextField()));

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
///////////////////////////////////////////////////////////////////////////////////
      for(int i=0;i<booksTableModel.getRowCount();i++){
         for (int j=0;j<3;j++){

         }
      }

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
               Integer year= Integer.valueOf(publicationTextField.getText());
               Book book=new Book(title,0,author,year);
               addRow(book);
               Main.addBookToList(book);
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

   private void deleteItem(){
      Dialog deleteItemDialog=new Dialog(jFrame);
      deleteItemDialog.setVisible(true);
      deleteItemDialog.setSize(500,150);
      deleteItemDialog.setLayout(new BorderLayout());

      JLabel title=new JLabel("Title:");
      JTextField titleTextField=new JTextField(20);
      JPanel detailPanel=new JPanel(new GridLayout(1,2));

      detailPanel.add(title);
      detailPanel.add(titleTextField);

      deleteItemDialog.add(detailPanel,BorderLayout.NORTH);


      JButton deleteButton=new JButton("Delete");
      deleteItemDialog.add(deleteButton,BorderLayout.SOUTH);

      deleteButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(!titleTextField.getText().isEmpty()) {
               String bookToRemove = titleTextField.getText();
               for (int i = 0; i < booksTableModel.getRowCount(); i++) {
                  if(booksTableModel.getValueAt(i,0).equals(bookToRemove)){
                     booksTableModel.removeRow(i);
                     Main.removeBook(i);
                  }
               }
               deleteItemDialog.dispose();
            }
            else {
               JOptionPane.showMessageDialog(deleteItemDialog, "Please enter Title of Book in the text field.", "Empty Text Field", JOptionPane.ERROR_MESSAGE);
            }
         }
      });


      deleteItemDialog.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            deleteItemDialog.dispose();
         }
      });
   }

   private void editItem(int selectedField){
      Dialog editItem= new Dialog(jFrame);
      editItem.setVisible(true);
      editItem.setSize(500,150);
      editItem.setLayout(new BorderLayout());


      JButton edit=new JButton("Edit");

      JLabel title=new JLabel("Title:");
      JTextField titleTextField=new JTextField(20);

      JLabel author=new JLabel("Author:");
      JTextField authorTextField=new JTextField(20);

      JLabel publicationYear=new JLabel("Publication Year:");
      JTextField publicationTextField=new JTextField(20);

      JPanel panel=new JPanel(new GridLayout(4,3));
      panel.add(title);
      panel.add(titleTextField);

      panel.add(author);
      panel.add(authorTextField);

      panel.add(publicationYear);
      panel.add(publicationTextField);

      editItem.add(panel,BorderLayout.NORTH);
      editItem.add(edit,BorderLayout.SOUTH);

      edit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(titleTextField.getText().isEmpty()||authorTextField.getText().isEmpty()||publicationTextField.getText().isEmpty()){
               JOptionPane.showMessageDialog(editItem, "Please enter all values for the text field.", "Empty Text Field", JOptionPane.ERROR_MESSAGE);
            }else{
               booksTableModel.setValueAt(titleTextField.getText(),selectedField,0);
               booksTableModel.setValueAt(authorTextField.getText(),selectedField,1);
               booksTableModel.setValueAt(publicationTextField.getText(),selectedField,2);
               Main.editBook(selectedField,titleTextField.getText(),authorTextField.getText(), Integer.parseInt(publicationTextField.getText()));
               editItem.dispose();
            }
         }
      });
   }

}
class RenderButtonForTable extends JButton implements TableCellRenderer
{
    public RenderButtonForTable()
    {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("read");
        return this;
    }
}

class ButtonEditorForTable extends DefaultCellEditor
{
    private final JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditorForTable(JTextField textField) {
        super(textField);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) ->
        {

           JFrame readFrame=new JFrame();
           int selectedRow = GuiMainForm.booksShelf.convertRowIndexToModel(GuiMainForm.booksShelf.getEditingRow());
           String selectedItemTitle = GuiMainForm.booksTableModel.getValueAt(selectedRow, 0).toString();

           JFrame readingFrame = new JFrame(selectedItemTitle);
           readingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           JTextArea textArea = new JTextArea(20, 40);
           textArea.setWrapStyleWord(true);
           textArea.setLineWrap(true);
           JScrollPane scrollPane = new JScrollPane(textArea);
           String text=selectedItemTitle+"\n\n"+"Artificial intelligence (AI) is a branch of computer science that deals with the creation of intelligent agents, which are systems that can reason, learn, and act autonomously. AI research has been highly successful in developing effective techniques for solving a wide range of problems, from game playing to medical diagnosis.\n" +
                   "\n" +
                   "One of the most important goals of AI research is to create machines that can understand and reason like humans. This is a challenging task, as human intelligence is incredibly complex and multifaceted. However, AI researchers have made significant progress in developing new algorithms and techniques for machine learning and natural language processing.\n" +
                   "\n" +
                   "Machine learning is a type of AI that allows computers to learn without being explicitly programmed. Instead, machines are trained on data sets and learn to identify patterns and make predictions. This approach has been used to develop highly successful AI systems for a variety of tasks, such as image recognition, speech recognition, and machine translation.\n" +
                   "\n" +
                   "Natural language processing (NLP) is a field of AI that deals with the interaction between computers and human language. NLP systems can understand and generate human language, which is essential for many AI applications, such as chatbots, voice assistants, and machine translation.\n" +
                   "\n" +
                   "AI is already having a major impact on our world. It is used in a wide range of industries, including healthcare, finance, transportation, and manufacturing. AI is also being used to develop new products and services, such as self-driving cars and virtual assistants.\n" +
                   "\n" +
                   "As AI continues to develop, it is likely to have an even greater impact on our lives. AI systems could be used to solve some of the world's most pressing problems, such as climate change and disease. AI could also lead to new forms of art, entertainment, and social interaction.\n" +
                   "\n" +
                   "Here are some specific examples of how AI is being used today:\n" +
                   "\n" +
                   "In healthcare, AI is being used to develop new diagnostic tools, treatment plans, and drug therapies. AI is also being used to create virtual assistants that can help patients manage their health.\n" +
                   "In finance, AI is being used to detect fraud, manage risk, and make investment decisions. AI is also being used to develop new financial products and services.\n" +
                   "In transportation, AI is being used to develop self-driving cars, trucks, and buses. AI is also being used to optimize traffic flow and reduce congestion.\n" +
                   "In manufacturing, AI is being used to automate tasks, improve quality control, and predict demand. AI is also being used to develop new products and manufacturing processes.\n" +
                   "AI is a rapidly developing field with the potential to revolutionize many aspects of our lives. It is important to stay informed about the latest developments in AI and to think about the potential implications of this technology for society.\n" +
                   "\n" +
                   "Here are some of the potential challenges and benefits of AI:\n" +
                   "\n" +
                   "Challenges:\n" +
                   "\n" +
                   "Bias: AI systems are trained on data sets, which can reflect the biases present in the real world. This can lead to AI systems that are biased against certain groups of people.\n" +
                   "Safety: AI systems need to be designed and tested carefully to ensure that they are safe to use. For example, AI systems that control self-driving cars need to be able to handle unexpected situations safely.\n" +
                   "Control: It is important to ensure that AI systems are used in a responsible and ethical manner. This means developing guidelines and regulations for the development and use of AI.\n" +
                   "Benefits:\n" +
                   "\n" +
                   "Efficiency: AI systems can automate tasks and improve efficiency in a variety of industries. This can lead to lower costs and increased productivity.\n" +
                   "Accuracy: AI systems can perform tasks with greater accuracy than humans. This can lead to improved outcomes in healthcare, finance, and other industries.\n" +
                   "Creativity: AI systems can generate new ideas and solutions that humans may not have thought of. This can lead to new products, services, and works of art.\n" +
                   "AI is a powerful technology with the potential to both benefit and harm society. It is important to be aware of the challenges and benefits of AI so that we can use this technology in a responsible and ethical manner.\n";

           textArea.append(text);
           readingFrame.add(scrollPane);

           readingFrame.addWindowListener(new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent e) {
                 int confirm = JOptionPane.showConfirmDialog(readingFrame, "Are you sure you want to stop reading this item?", "Confirmation", JOptionPane.YES_NO_OPTION);
                 if (confirm == JOptionPane.YES_OPTION) {
                    //LibraryManagementSystem.main(null);
                    readingFrame.dispose();
                 }
                 else {
                    readingFrame.setDefaultCloseOperation(readingFrame.DO_NOTHING_ON_CLOSE);
                 }
              }
           });

           readingFrame.pack();
           readingFrame.setVisible(true);

        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected)
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }
    @Override
    public Object getCellEditorValue() {
        if (isPushed)
        {

        }
        isPushed = false;
        return label;
    }
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}