package displays;

import buttons.Button;
import buttons.ButtonEnum;
import utils.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class DisplayManager extends Display
        implements ListSelectionListener {
    private JList<Item> list;
    private DefaultListModel<Item> listView;
    private DefaultComboBoxModel<Id> choiceModel;

    private JComboBox<Id> choiceBox;
    private JButton copy_pw;
    private JButton copy_id;
    private Button edit_bt;
    private JPasswordField password;
    private JTextField pwmaxlength;
    private JLabel pwlength;

    public DisplayManager(RSA rsa) throws IOException {
        saveFile = new SaveFile(rsa);
        copy_id = new JButton("Copy");
        copy_id.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Id id = (Id) choiceBox.getSelectedItem();
                if (id != null)
                    copyToClipboard(id.toString());
            }
        });
        copy_id.setEnabled(false);
        password = new JPasswordField(16);
        password.setEnabled(false);

        copy_pw = new JButton("Copy");
        copy_pw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard(String.valueOf(password.getPassword()));
            }
        });
        copy_pw.setEnabled(false);

        edit_bt = new Button("Edit", ButtonEnum.OPENEDIT);

        pwmaxlength = new JTextField(5);
        pwmaxlength.setEnabled(false);

        listView = new DefaultListModel<>();
        listView.addAll(saveFile.items.values());

        choiceModel = new DefaultComboBoxModel<>();
        choiceBox = new JComboBox<>(choiceModel);
        choiceBox.setPrototypeDisplayValue(new Id("to be sure it fits everything : 15".split(" : ")));
        choiceBox.setMaximumSize(choiceBox.getPreferredSize());
        choiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Id id = (Id) choiceBox.getSelectedItem();
                if (id == null)
                    return;
                pwmaxlength.setText(String.valueOf(id.getLength()));
                password.setText(rsa.codeAndStrCB(list.getSelectedValue().getName() + "" + id.getId(), id.getLength(), id.getAscii()));
                pwmaxlength.setText(String.valueOf(id.getLength()));
                pwlength.setText("Length : " + String.valueOf(password.getPassword().length));
            }
        });


        //Create the list and put it in a scroll pane.
        list = new JList<>(listView);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 20, 5); //margin
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(new JLabel("<html><h1>Password Manager</h1></html>"), gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(1, 5, 1, 5); //margin
        buttonPane.add(new JLabel("Login :"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(choiceBox, gbc);
        gbc.gridx += 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPane.add(copy_id, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        buttonPane.add(new JLabel("Password :"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(password, gbc);
        gbc.gridx += 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPane.add(copy_pw, gbc);

        gbc.insets = new Insets(-5, 5, 0, 5); //margin
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        pwlength = new JLabel("Length :");
        pwlength.setFont(new Font("Length :", Font.PLAIN, 10));
        buttonPane.add(pwlength, gbc);

        gbc.insets = new Insets(1, 5, 20, 5); //margin
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPane.add(new JLabel("Max Length :"), gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPane.add(pwmaxlength, gbc);

        gbc.gridx=0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(edit_bt, gbc);

        gbc.gridx=0;
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5); //margin
        gbc.anchor = GridBagConstraints.EAST;
        buttonPane.add(new Button("Quit", ButtonEnum.QUIT), gbc);


        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(5);
        jPanel.setLayout(borderLayout);
        jPanel.add(listScrollPane, BorderLayout.CENTER);
        jPanel.add(edit_bt, BorderLayout.SOUTH);

        add(jPanel, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.EAST);
    }

    public void copyToClipboard(String str){
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                copy_id.setEnabled(false);
                copy_pw.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                copy_id.setEnabled(true);
                copy_pw.setEnabled(true);
                choiceModel.removeAllElements();
                choiceModel.addAll(list.getSelectedValue().getIds().values());
                choiceBox.setSelectedIndex(0);
            }
        }
    }


    @Override
    public void reload() {
        listView.removeAllElements();
        listView.addAll(saveFile.items.values());
        choiceBox.removeAllItems();
    }
}