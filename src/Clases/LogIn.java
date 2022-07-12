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
        setLocationRelativeTo(null); //Centra en el medio
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
                MainMenu mm = new MainMenu();
                user = con.autentificar(emailField.getText(),passwordField.getPassword());
                if (user != null){
                    JOptionPane.showMessageDialog(null,"Inicio de Sesion Satisfactorio");
                    mm.setUser(user);
                    mm.setText(user.toTextField());
                    mm.setVisible(true);
                    dispose();
                } else JOptionPane.showMessageDialog(null,"Error en Inicio de Sesion");
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mm = new MainMenu();
                if (user != null){
                    mm.setUser(user);
                    mm.setText(user.toTextField());
                }
                mm.setVisible(true);
                dispose();
            }
        });
        setVisible(true);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void showRegister(){
        Register register = new Register(null);
    }
}
