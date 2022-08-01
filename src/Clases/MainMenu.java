package Clases;

import UserRelated.IndoorConfig;
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
    private JButton buscarButton;
    private JButton editarButton;
    private JButton confirmarButton;
    private JTextField stockField;
    private JComboBox bancoBox;
    private JPanel miIndoorPane;
    private JLabel lucesLabel;
    private JLabel ventiladoresLabel;
    private JLabel indoorLabel;
    private JLabel macetasLabel;
    private JLabel coolerLaber;
    private JLabel lucesInfoLabel;
    private JLabel ventiladoresInfoLabel;
    private JLabel indoorInfoLabel;
    private JLabel macetasInfoLabel;
    private JLabel coolersInfoLabel;
    private JButton saveConfigButton;
    private JPanel indoorSetPanel;
    private User usuario = new User();
    CannaBeeSystem cbSyst = new CannaBeeSystem();
    Connect con = new Connect();

    public MainMenu() {
        super("MainMenu.exe");
        cbSyst.cepasReadFile(); //Lee el archivo Cepas.bin y carga la coleccion CepasUser.
        cbSyst.configsReadFile();
        cbSyst.cepasBanksReadSQL();
        listarBancoBox();
        llenarListas(); //Les da el formato a las listas y tambien las llena. Este metodo quedaria obsoleto al pedirle al LogIn que lo haga antes.
        //setearConfigIndoor(); //Carga los labels con la info del indoor.
        setContentPane(MainMenuPane);
        setMinimumSize(new Dimension(650, 650));
        setLocationRelativeTo(null); //Centra en el medio
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Indica que sucede al cliquear el boton X.
        commentsArea.setLineWrap(true); //Corta la palabra al escribir y llegar al final en el TextArea Comments.

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbSyst.cepasToFile(); //Para que si cancela, se guarden las geneticas del Usuario actual.
                cbSyst.configsToFile();
                LogIn logIn = new LogIn(null);
                dispose();
            }
        });

        agregarGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarGenButtonLogic();
                clearFields(); //Limpia los textfields.
                listarGensUser();
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbSyst.cepasToFile();
                cbSyst.configsToFile();
                dispose();
            }
        });

        editarButton.addActionListener(new ActionListener() {
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

        confirmarButton.addActionListener(new ActionListener() {
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
                clearFields();
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
                else JOptionPane.showMessageDialog(null, "Funcion para usuarios logueados");
            }
        });

        bancoBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(!bancoBox.getSelectedItem().equals(""));
                System.out.println(bancoBox.getSelectedItem().equals(""));
                if (!bancoBox.getSelectedItem().equals("")) {
                    String auxB = (String) bancoBox.getSelectedItem();
                    listarGensBancosPorBanco(auxB);
                }
            }
        });

        TabbedMenu.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                // if (usuario.getId() <= 0) {
                //JOptionPane.showMessageDialog(null,"Tabla de MisGeneticas solo para Usuarios logueados.");
                // }
                if (!usuario.isAdmin()) {
                    bancosPane.remove(cargarBancoDeGeneticasButton);
                }
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

        lucesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(usuario.getId());
                if (usuario.getId() > 0) { //Si hay usuario logueado permite el ingreso de Data
                    String aux = JOptionPane.showInternalInputDialog(MainMenuPane, "Ingrese el nombre del Panel o Foco y su potencia en Watts.");
                    if (aux != null) //Si no se cancelo el InputDialog.
                        lucesInfoLabel.setText(aux);
                } else JOptionPane.showMessageDialog(null, "Ingrese con un Usuario valido.");

            }
        });

        ventiladoresLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (usuario.getId() > 0) {
                    String aux = JOptionPane.showInternalInputDialog(MainMenuPane, "Ingrese nombre de su ventilador y potencia.");
                    if (aux != null)
                        ventiladoresInfoLabel.setText(aux);
                } else JOptionPane.showMessageDialog(null, "Ingrese con un Usuario valido.");
            }
        });
        indoorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (usuario.getId() > 0) {
                    String aux = JOptionPane.showInternalInputDialog(MainMenuPane, "Ingrese marca y medidas en CM de su carpa.");
                    if (aux != null)
                        indoorInfoLabel.setText(aux);
                } else JOptionPane.showMessageDialog(null, "Ingrese con un Usuario valido.");
            }
        });
        macetasLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (usuario.getId() > 0) {
                    String aux = JOptionPane.showInternalInputDialog(MainMenuPane, "Ingrese cantidad de macetas y sus litros.");
                    if (aux != null)
                        macetasInfoLabel.setText(aux);
                } else JOptionPane.showMessageDialog(null, "Ingrese con un Usuario valido.");
            }
        });
        coolerLaber.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (usuario.getId() > 0) {
                    String aux = JOptionPane.showInternalInputDialog(MainMenuPane, "Ingrse cantidad y pulgadas de sus coolers.");
                    if (aux != null)
                        coolersInfoLabel.setText(aux);
                } else JOptionPane.showMessageDialog(null, "Ingrese con un Usuario valido.");
            }
        });
        saveConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getId() > 0) {
                    String luz = lucesInfoLabel.getText();
                    String ventilador = ventiladoresInfoLabel.getText();
                    String indoor = indoorInfoLabel.getText();
                    String maceta = macetasInfoLabel.getText();
                    String cooler = coolersInfoLabel.getText();
                    cbSyst.agregarConfigIndoor(usuario.getId(), luz, ventilador, indoor, maceta, cooler);
                } else JOptionPane.showMessageDialog(null, "Funcion solo para Usuarios logueados.");
            }
        });

        geneticasPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (usuario.getId() <= 0) {
                    JOptionPane.showMessageDialog(null, "Tabla MisGeneticas solo para Usuarios logueados.");
                }
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
            String auxNombre = (String) gensBankTable.getValueAt(filaSelected, 0);
            if (!cantidadField.getText().isEmpty()) {
                int stockActual = Integer.parseInt(stockField.getText());
                int auxStock = Integer.parseInt(cantidadField.getText());
                if (0 < auxStock && auxStock <= stockActual) {
                    con.actualizarStock(auxNombre, auxStock); //Actualiza el stock de la gen en MySQL.
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

    public void setearConfigIndoor() {
        if (usuario.getId() > 0) {
            IndoorConfig iC = cbSyst.getIndoorConfig(usuario.getId());
            System.out.println(iC.toString());
            lucesInfoLabel.setText(iC.getLuz());
            ventiladoresInfoLabel.setText(iC.getVentilador());
            indoorInfoLabel.setText(iC.getIndoor());
            macetasInfoLabel.setText(iC.getMaceta());
            coolersInfoLabel.setText(iC.getCooler());
        }
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

    public void listarGensBancosPorBanco(String nombre) { //Lista la tabla en base al banco elegido.
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre", "Raza", "THC", "Comentarios", "Banco"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = cbSyst.getCepasPorBancoIterator(nombre);
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key = (Integer) entry.getKey();
            Cepa valueC = (Cepa) entry.getValue();
            if (valueC.getStock() > 0)
                model.addRow(new Object[]{valueC.getNombre(), valueC.getRaza(), valueC.getThc(), valueC.getComentarios(), valueC.getBanco()});
        }
        gensBankTable.setModel(model);
    }

    public void listarBancoBox() {
        bancoBox.removeAllItems();
        Iterator entries = cbSyst.getBancosIterator();
        while (entries.hasNext()) {
            String auxB = (String) entries.next();
            bancoBox.addItem(auxB);
        }
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

    public void clearFields() {
        nombreField.setText("");
        razaField.setText("");
        thcField.setText("");
        commentsArea.setText("");
        nombreEditField.setText("");
        razaEditField.setText("");
        thcEditField.setText("");
        commentsEditArea.setText("");
    }

    public CannaBeeSystem getCbSyst() {
        return cbSyst;
    }
}
