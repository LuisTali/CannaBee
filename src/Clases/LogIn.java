package Clases;

import UserRelated.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JDialog {
    private JPanel LogInPanel;
    private JTextField emailField;
    private JButton acceptButton;
    private JButton registerButton;
    private JButton cancelButton;
    private JPasswordField passwordField;

    public static User user = null;

    Connect con = new Connect();

    public LogIn(JFrame parent) {
        super(parent);
        setTitle("Log In");
        setContentPane(LogInPanel);
        setResizable(false);
        setMinimumSize(new Dimension(600,500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegister();
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = con.autentificar(emailField.getText(),passwordField.getPassword());
                if (user != null){
                    JOptionPane.showMessageDialog(null,"Inicio de Sesion Satisfactorio");
                    MainMenu.usuario = user; //Esto esta bien?
                    dispose();
                } else JOptionPane.showMessageDialog(null,"Error en Inicio de Sesion");
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

    public User getUser(){
        return user;
    }

    public void showRegister(){
        Register register = new Register(null);
    }
}
