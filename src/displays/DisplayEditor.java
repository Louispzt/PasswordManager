package displays;

import buttons.Button;
import buttons.ButtonEnum;
import utils.Id;
import utils.IntFilter;
import utils.Item;
import utils.SaveFile;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.PlainDocument;

/* ListDemo.java requires no other files. */
public class DisplayEditor extends Display
        implements ListSelectionListener{
    private final JCheckBox asciionly;
    private JList<Item> list;
    private DefaultListModel<Item> listView;
    private DefaultComboBoxModel<Id> choiceModel;

    private SaveFile saveFile;

    private JComboBox<Id> choiceBox;
    private JButton edit_bt;
    private JButton new_bt;
    private JButton remove_bt;
    private JTextField new_app;
    private JTextField new_log;
    private JTextField new_pwlength;

    public DisplayEditor(SaveFile saveFile) throws IOException {
        this.saveFile = saveFile;
        edit_bt = new JButton("Edit");
        edit_bt.addActionListener(new EditListener());
        new_bt = new JButton("New");
        new_bt.addActionListener(new NewListener());
        remove_bt = new JButton("Remove");
        remove_bt.addActionListener(new RemoveListener());
        listView = new DefaultListModel<>();
        listView.addAll(saveFile.items.values());
        asciionly = new JCheckBox();

        new_app = new JTextField(16);
        new_log = new JTextField(16);
        new_pwlength = new JTextField(5);

        PlainDocument doc = (PlainDocument) new_pwlength.getDocument();
        doc.setDocumentFilter(new IntFilter());

        choiceModel = new DefaultComboBoxModel<>();
        choiceBox = new JComboBox<>(choiceModel);
        choiceBox.setPrototypeDisplayValue(new Id("to be sure it fits everything : 0".split(" : ")));
        choiceBox.setMaximumSize(choiceBox.getPreferredSize());
        choiceBox.addActionListener(e -> {
            Id id = (Id) choiceBox.getSelectedItem();
            if (id == null)
                return;
            new_log.setText(id.getId());
            new_pwlength.setText(String.valueOf(id.getLength()));
            asciionly.setSelected(id.getAscii());
        });


        //Create the list and put it in a scroll pane.
        list = new JList<>(listView);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Create a panel that uses BoxLayout.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 5, 0, 5); //margin
        gbc.gridwidth = 1;
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.WEST;
        buttonPane.add(new JLabel("Login :"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(choiceBox, gbc);

        gbc.insets = new Insets(20, 5, 0, 5); //margin
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        buttonPane.add(new JLabel("App :"), gbc);
        gbc.gridx++;
        buttonPane.add(new_app, gbc);

        gbc.insets = new Insets(0, 5, 0, 5); //margin
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        buttonPane.add(new JLabel("Login :"), gbc);
        gbc.gridx++;
        buttonPane.add(new_log, gbc);

        gbc.insets = new Insets(0, 5, 20, 5); //margin
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        buttonPane.add(new JLabel("Max Length :"), gbc);

        GridBagConstraints newgb = new GridBagConstraints();
        newgb.insets = new Insets(0, 5, 20, 5); //margin
        newgb.gridx = 0;
        newgb.gridwidth = 1;
        newgb.anchor = GridBagConstraints.WEST;
        newgb.gridx++;
        newgb.anchor = GridBagConstraints.EAST;
        buttonPane.add(new JLabel("ASCII only :"), newgb);

        gbc.gridx++;
        buttonPane.add(new_pwlength, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx++;
        gbc.insets = new Insets(0, -5, 20, 1); //margin
        buttonPane.add(asciionly, gbc);



        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.insets = new Insets(0, 5, 0, 5); //margin
        gb.gridwidth = 1;

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        gb.gridx = 0;
        gb.gridwidth = 1;
        gb.anchor = GridBagConstraints.EAST;
        gb.insets = new Insets(5, 5, 5, 5); //margin
        buttons.add(new_bt, gb);
        gb.gridx++;
        gb.anchor = GridBagConstraints.CENTER;
        buttons.add(edit_bt, gb);
        gb.gridx++;
        gb.anchor = GridBagConstraints.WEST;
        buttons.add(remove_bt, gb);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(buttons, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 5, 5, 5); //margin
        buttonPane.add(new Button("Close", ButtonEnum.CLOSE), gbc);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.EAST);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() != -1) {
                reload();
                new_app.setText(list.getSelectedValue().toString());
                choiceModel.removeAllElements();
                choiceModel.addAll(list.getSelectedValue().getIds().values());
            }
        }
    }

    @Override
    public void reload() {
        new_app.setText("");
        new_log.setText("");
        new_pwlength.setText("");
        asciionly.setSelected(false);
    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Item newItem = list.getSelectedValue();
            int itemIndex = list.getSelectedIndex();
            if (newItem != null){
                Id newId = (Id) choiceBox.getSelectedItem();
                int idIndex = choiceBox.getSelectedIndex();
                if (newId != null){
                    newId.setAll(new_log.getText(), new_pwlength.getText(), asciionly.isSelected());
                    choiceBox.setSelectedIndex(idIndex);
                }
                newItem.setName(new_app.getText());
                listView.set(itemIndex, newItem);
            }
            try {
                saveFile.save();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class NewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Item item = saveFile.items.get(new_app.getText());
            if (item != null){
                Id id = item.getIds().get(new_log.getText());
                if (id == null){
                    Id newid = new Id(new_log.getText(), new_pwlength.getText(), asciionly.isSelected());
                    item.getIds().put(new_log.getText(), newid);
                    choiceModel.addElement(newid);
                }
                else{
                    id.setAll(new_log.getText(), new_pwlength.getText(), asciionly.isSelected());
                }
            }
            else{
                if (new_app.getText().equals("") || new_app.getText().matches("[ ]+") || new_log.getText().equals("") || new_log.getText().matches("[ ]+"))
                    return;
                Item newitem = new Item(new_app.getText(), new_log.getText(), new_pwlength.getText(), asciionly.isSelected());
                saveFile.items.put(new_app.getText(), newitem);
                listView.add(listView.size(), newitem);
            }
            try {
                saveFile.save();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class RemoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Item item = list.getSelectedValue();
            int itemIndex = list.getSelectedIndex();
            if (item != null){
                Id id = (Id) choiceBox.getSelectedItem();
                int idIndex = choiceBox.getSelectedIndex();
                if (id != null){
                    item.getIds().remove(id.toString());
                    choiceModel.removeElementAt(idIndex);
                }
                else{
                    reload();
                    saveFile.items.remove(item.toString());
                    listView.remove(itemIndex);
                }
            }
            try {
                saveFile.save();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}