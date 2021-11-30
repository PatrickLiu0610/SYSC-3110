import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class menu1 {
    private AddressBook book = new AddressBook();
    private JLabel l = new JLabel();

    public void work()
    {
        // create a frame
        JFrame f = new JFrame("Menu demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a menuBar
        JMenuBar mb = new JMenuBar();

        // create menu
        JMenu x = new JMenu("Display");
        JMenu a = new JMenu("Add");
        JMenu b = new JMenu("Remove");
        JMenu c = new JMenu("New Book");
        JMenu d = new JMenu("Save");
        JMenu e = new JMenu("Import");
        JMenu g = new JMenu("XMLsave");

        // create menuItems
        JMenuItem m1 = new JMenuItem(new AbstractAction("Display") {
            @Override
            public void actionPerformed(ActionEvent e) {

                // set the label to the menuItem that is selected
                l.setText("");
                String sText = ("<html>Display AddressBook <br/>" + listContent(book));
                l.setText(sText);
            }
        });

        JMenuItem m2 = new JMenuItem(new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                String address;
                String number;
                JFrame frame = new JFrame();
                Object result1 = JOptionPane.showInputDialog(frame, "Enter name:");
                name = (String)result1;
                Object result2 = JOptionPane.showInputDialog(frame, "Enter Address:");
                address = (String)result2;
                Object result3 = JOptionPane.showInputDialog(frame, "Enter Number:");
                number = (String)result3;

                book.addBuddy(new BuddyInfo(name, address, number));
            }
        });

        JMenuItem m3 = new JMenuItem(new AbstractAction("Remove") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                JFrame frame = new JFrame();
                Object result1 = JOptionPane.showInputDialog(frame, "Enter name:");
                name = (String)result1;
                book.removeBuddy(name);
            }
        });

        JMenuItem m4 = new JMenuItem(new AbstractAction("New Book") {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.clear();
            }
        });

        JMenuItem m5 = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fileName;
                JFrame frame = new JFrame();
                Object result = JOptionPane.showInputDialog(frame, "Enter file name:");
                fileName = (String)result;

                try {
                    File myObj = new File(fileName);
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException ae) {
                    System.out.println("An error occurred.");
                    ae.printStackTrace();
                }

                try {
                    FileWriter myWriter = new FileWriter(fileName);
                    myWriter.write(listContent(book));
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException ae) {
                    System.out.println("An error occurred.");
                    ae.printStackTrace();
                }
            }
        });

        JMenuItem m6 = new JMenuItem(new AbstractAction("Import") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName;
                JFrame frame = new JFrame();
                Object result = JOptionPane.showInputDialog(frame, "Enter file name:");
                fileName = (String)result;

                try {
                    File myObj = new File(fileName);
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        book.addBuddy(BuddyInfo.importString(data));
                    }
                    myReader.close();
                } catch (FileNotFoundException ae) {
                    System.out.println("An error occurred.");
                    ae.printStackTrace();
                }
            }
        });

        JMenuItem m7 = new JMenuItem(new AbstractAction("XMLsave") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // add menu items to menu
        x.add(m1);
        a.add(m2);
        b.add(m3);
        c.add(m4);
        d.add(m5);
        e.add(m6);

        // add menu to menu bar
        mb.add(x);
        mb.add(a);
        mb.add(b);
        mb.add(c);
        mb.add(d);
        mb.add(e);

        // add menuBar to frame
        f.setJMenuBar(mb);

        // add label
        f.add(l);

        // set the size of the frame
        f.setSize(500, 500);
        f.setVisible(true);
    }

    public String listContent(AddressBook book){
        String content = "";
        String eol = System.getProperty("line.separator");

        for (BuddyInfo num : book.getBuddyList()) {
            content += num.toString() + eol;
        }

        return content;
    }

    public static void main(String args[]) {
        menu1 menu = new menu1();
        menu.work();
    }
}