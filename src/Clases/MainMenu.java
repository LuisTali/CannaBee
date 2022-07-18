package Clases;

import UserRelated.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;

public class MainMenu extends JFrame {
    private JTabbedPane TabbedMenu;
    private JPanel MainMenuPane;
    private JPanel añadirPane;
    private JPanel bancosPane;
    private JButton logInButton;
    private JTextField UsuarioInfo;
    private JTextField nombreField;
    private JTextField thcField;
    private JLabel thcLabel;
    private JLabel nombreLabel;
    private JTextField razaField;
    private JLabel comentsLabel;
    private JTextArea commentsArea;
    private JButton agregarGen;
    private JTable gensTable;
    private JScrollPane GensScrollPane;
    private JPanel geneticasPane;
    private JTextField buscarField;
    private JTextField nombreEditField;
    private JLabel buscarLabel;
    private JTextField razaEditField;
    private JTextField thcEditField;
    private JLabel editarLabel;
    private JTextArea commentsEditArea;
    private JButton xButton;
    private JButton comprarButton;
    private JTextField textField1;
    private JTable table1;
    private JButton cargarBancoDeGeneticasButton;
    private User usuario = new User();
    CannaBeeSystem cbSyst = new CannaBeeSystem();

    public MainMenu() {
        super("MainMenu.exe");
        cbSyst.cepasReadFile(); //Lee el archivo Cepas.bin y carga la coleccion CepasUser.
        setContentPane(MainMenuPane);
        setMinimumSize(new Dimension(600, 550));
        setLocationRelativeTo(null); //Centra en el medio
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        commentsArea.setLineWrap(true); //Corta la palabra al escribir y llegar al final en el TextArea Comments.
        // if (usuario.getId() > 0)
        //    listarGensUser(); Al invocar el metodo desde el LogIn estas lineas quedan obsoletas.

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbSyst.cepasToFile(); //Para que si cancela, se guarden las geneticas del Usuario actual.
                LogIn logIn = new LogIn(null);
                dispose();
            }
        });

        agregarGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarGenButtonLogic();
                listarGensUser();
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbSyst.cepasToFile();
                dispose();
            }
        });

        logInButton.addMouseListener(new MouseAdapter() { //Al pasar mouse por encima del boton logIn hace esto.
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                logInButton.setBackground(new Color(246,246,255));
                logInButton.setForeground(new Color(0,0,0));
            }
        });
        logInButton.addMouseListener(new MouseAdapter() { //Al quitar el mouse de encima del boton logIn hace esto.
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logInButton.setBackground(new Color(0,0,0));
                logInButton.setForeground(new Color(246,246,255));
            }
        });
        TabbedMenu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (usuario.getId()<=0){
                    TabbedMenu.remove(geneticasPane);
                }
                if (!usuario.isAdmin()){
                    bancosPane.remove(cargarBancoDeGeneticasButton);
                }
            }
        });
    }

    public boolean checkUserGens(){
        return cbSyst.cepasUserIsEmpty(usuario.getId());
    }

    public void agregarGenButtonLogic() {  //Logica del boton Agregar Genetica.
        if (usuario.getId() >= 1) { //Corrobora si es un usuario logueado mediante ID.
            if (!nombreField.getText().isEmpty() && !thcField.getText().isEmpty() && !razaField.getText().isEmpty()) {
                String nombre = nombreField.getText();
                String raza = razaField.getText();
                double thc = Double.parseDouble(thcField.getText());
                Cepa aux = new Cepa(nombre, raza, thc);
                if (commentsArea != null) aux.setComentarios(commentsArea.getText());
                cbSyst.agregarCepaUser(usuario.getId(), aux);
            } else JOptionPane.showMessageDialog(null, "Verifique los campos obligatorios.");
        } else JOptionPane.showMessageDialog(null, "Funcion para usuarios logueados");

    }

    public void listarGensUser() { //Lista las geneticas del usuario en la Lista.
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Raza", "THC", "Comentarios"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = cbSyst.getCepasUserIterator(usuario.getId());
        while (entries.hasNext()){
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Cepa value = (Cepa) entry.getValue();
            model.addRow(new Object[]{key,value.getRaza(),value.getThc(),value.getComentarios()});
        }
        gensTable.setModel(model);
    }

    public void setText(String t) {
        UsuarioInfo.setText(t);
    }

    public String mostrarUser() {
        return usuario.toString();
    }

    public void setUser(User user) {
        usuario = user;
    }
}
