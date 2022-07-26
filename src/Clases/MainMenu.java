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
    private JTextArea commentsEditArea;
    private JButton xButton;
    private JButton COMPRARButton;
    private JTextField cantidadField;
    private JTable gensBankTable;
    private JButton cargarBancoDeGeneticasButton;
    private JLabel cantComprarLabel;
    private JButton button1;
    private JButton EDITARButton;
    private JButton CONFIRMARButton;
    private JTextField stockField;
    private User usuario = new User();
    CannaBeeSystem cbSyst = new CannaBeeSystem();
    Connect con = new Connect();

    public MainMenu() {
        super("MainMenu.exe");
        cbSyst.cepasReadFile(); //Lee el archivo Cepas.bin y carga la coleccion CepasUser.
        cbSyst.cepasBanksReadSQL();
        llenarListas(); //Les da el formato a las listas y tambien las llena.
        setContentPane(MainMenuPane);
        setMinimumSize(new Dimension(650, 650));
        setLocationRelativeTo(null); //Centra en el medio
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Indica que sucede al cliquear el boton X.
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
                logInButton.setBackground(new Color(246, 246, 255));
                logInButton.setForeground(new Color(0, 0, 0));
            }
        });

        logInButton.addMouseListener(new MouseAdapter() { //Al quitar el mouse de encima del boton logIn hace esto.
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                logInButton.setBackground(new Color(0, 0, 0));
                logInButton.setForeground(new Color(246, 246, 255));
            }
        });

        TabbedMenu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (usuario.getId() <= 0) {
                    TabbedMenu.remove(geneticasPane);
                }
                if (!usuario.isAdmin()) {
                    bancosPane.remove(cargarBancoDeGeneticasButton);
                }
            }
        });

        EDITARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSelect = gensTable.getSelectedRow();
                if (filaSelect == -1) JOptionPane.showMessageDialog(null, "Elija una genetica a modificar!");
                else {
                    nombreEditField.setText((String) gensTable.getValueAt(filaSelect, 0));
                    razaEditField.setText((String) gensTable.getValueAt(filaSelect, 1));
                    double thcAux = (double) gensTable.getValueAt(filaSelect, 2);
                    thcEditField.setText(String.valueOf(thcAux));
                    commentsEditArea.setText((String) gensTable.getValueAt(filaSelect, 3));
                }
            }
        });

        CONFIRMARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int auxID = usuario.getId();
                String auxNombrePrim = (String) gensTable.getValueAt(gensTable.getSelectedRow(), 0);
                String auxNombre = nombreEditField.getText();
                double auxThc = Double.parseDouble(thcEditField.getText());
                String auxRaza = razaEditField.getText();
                String auxComments = commentsEditArea.getText();
                Cepa auxCepa = null;
                if (auxComments.equals(""))
                    auxCepa = new Cepa(auxNombre, auxRaza, auxThc);
                else auxCepa = new Cepa(auxNombre, auxRaza, auxThc, auxComments);
                if (auxNombre.equals(auxNombrePrim))  //Si usaria ID en las Cepas podria modificar nombre sin eliminarla del HashMap previamente.
                    cbSyst.agregarCepaUser(auxID, auxCepa);
                else {
                    cbSyst.eliminarCepa(auxID, auxNombrePrim);
                    cbSyst.agregarCepaUser(auxID, auxCepa);
                }
                listarGensUser();
            }
        });

        cargarBancoDeGeneticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarGenBancoButtonLogic();
            }
        });

        gensBankTable.addMouseListener(new MouseAdapter() { //Listener para que al seleccionar una Gen tire su Stock.
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int filaSelected = gensBankTable.getSelectedRow();
                if (filaSelected > -1) {
                    String auxNombre = (String) gensBankTable.getValueAt(filaSelected, 0);
                    stockField.setText(String.valueOf(con.consultarStockGen(auxNombre)));
                }
            }
        });

        COMPRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getId() > 0)
                comprarButtonLogic();
                else JOptionPane.showMessageDialog(null,"Funcion para usuarios logueados");
            }
        });
    }

    public boolean checkUserGens() {
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

    public void agregarGenBancoButtonLogic() {
        RegisterGenBank rBank = new RegisterGenBank(this); //Problema sucede al enviar como parametro 'this'.
        rBank.setVisible(true);
    }

    public void comprarButtonLogic() {
        int filaSelected = gensBankTable.getSelectedRow();
        if (filaSelected > -1) {
            String auxNombre = (String) gensBankTable.getValueAt(filaSelected,0);
            if (!cantidadField.getText().isEmpty()) {
                int stockActual = Integer.parseInt(stockField.getText());
                int auxStock = Integer.parseInt(cantidadField.getText());
                if (0 < auxStock && auxStock <= stockActual) {
                    con.actualizarStock(auxNombre,auxStock); //Actualiza el stock de la gen en MySQL.
                    cbSyst.cepasBanksReadSQL(); //Carga el hashmap de gensBanco desde MySQL.
                    stockField.setText(String.valueOf(cbSyst.getStockGen(auxNombre))); //Busca en CannaBeeSystem el stock de esa gen.
                    stockField.setText(String.valueOf(con.consultarStockGen(auxNombre))); //Actualiza el campo Stock.
                    listarGensBancos(); //Lista banco de geneticas.
                } else
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad valida para comprar entre 0 < x <= " + stockField.getText());
            } else
                JOptionPane.showMessageDialog(null, "Especifique cantidad a comprar");
        } else JOptionPane.showMessageDialog(null, "Elija una genetica de la tabla primero");
    }

    public void listarGensUser() { //Lista las geneticas del usuario en la Lista.
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Raza", "THC", "Comentarios"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (!(usuario.getId() <= 0)) { //Linea para comprobar si hay usuario iniciado agregada.
            Iterator entries = cbSyst.getCepasUserIterator(usuario.getId());
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Cepa value = (Cepa) entry.getValue();
                model.addRow(new Object[]{key, value.getRaza(), value.getThc(), value.getComentarios()});
            }
        } else System.out.println("Usuario no ingresado.");

        gensTable.setModel(model);
    }

    public void listarGensBancos() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Raza", "THC", "Comentarios", "Banco"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = cbSyst.getCepasBancosIterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key = (Integer) entry.getKey();
            Cepa valueC = (Cepa) entry.getValue();
            if (valueC.getStock() > 0)
            model.addRow(new Object[]{valueC.getNombre(), valueC.getRaza(), valueC.getThc(), valueC.getComentarios(), valueC.getBanco()});
        }
        gensBankTable.setModel(model);
    }

    public void llenarListas() {
        listarGensUser();
        listarGensBancos();
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

    public CannaBeeSystem getCbSyst() {
        return cbSyst;
    }
}
