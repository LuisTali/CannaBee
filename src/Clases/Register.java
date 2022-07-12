package Clases;

import UserRelated.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JDialog{
    private JPanel RegisterPanel;
    private JTextField mailField;
    private JTextField passwordField;
    private JButton RegisterButton;
    private JButton cancelButton;
    private JLabel NameField;
    private JLabel PasswordField;
    private JLabel MailField;
    private JTextField nameField;
    private JCheckBox adminCheck;
    Connect con = new Connect();

    public Register(JFrame parent) {
        super(parent);
        setTitle("Register");
        setContentPane(RegisterPanel);
        setResizable(false);
        setMinimumSize(new Dimension(500,600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            registerButtonLogic();
            dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    public void registerButtonLogic(){
        User usuario = null;
        if (!nameField.getText().isEmpty() && !mailField.getText().isEmpty() && passwordField.getText() != null){
            String nombre = nameField.getText();
            String mail = mailField.getText();
            String password = passwordField.getText();
            boolean isAdmin = false;
            if (adminCheck.isSelected()) isAdmin = true;
            usuario = con.crearUser(nombre,mail,password,isAdmin);
            con.añadirUser(usuario);
        } else JOptionPane.showMessageDialog(null,"ERROR");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
