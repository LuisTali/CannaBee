package Clases;

import UserRelated.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JTabbedPane TabbedMenu;
    private JPanel MainMenuPane;
    private JPanel añadirPane;
    private JPanel Bancos;
    private JButton logInButton;
    private JTextField UsuarioInfo;
    public static User usuario = new User();

    public MainMenu() {
        super("MainMenu.exe");
        setContentPane(MainMenuPane);
        setMinimumSize(new Dimension(750, 750));
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogIn logIn = new LogIn(null);
            }
        });
        setVisible(true);
    }

    public void setText(String t){
        UsuarioInfo.setText(t);
    }

    public String mostrarUser(){
        return usuario.toString();
    }

    public void setUser(User user) {
        usuario = user;
    }
}
