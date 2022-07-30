package Clases;

import Clases.Cepa;
import Clases.Connect;
import Clases.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGenBank extends JDialog {

    private JPanel registerGenBankPane;
    private JTextField nombreField;
    private JTextField thcField;
    private JTextField rasafield;
    private JTextField creadorField;
    private JTextField comentariosField;
    private JButton registerButton;
    private JButton cancelButton;
    private JTextField stockField;

    private MainMenu mm; //Para poder asociarlo con el Frame Creador.

    Connect con = new Connect();

    public RegisterGenBank(MainMenu parent) { //Puedo enviar un JFrame parent o enviarle un MainMenu parent.
        this.mm = parent; //Lo asocia.
        setTitle("Registrar Cepa");
        setContentPane(registerGenBankPane);
        setMinimumSize(new Dimension(400,400));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerGenButtonLogic();
            }
        });
    }

    public void registerGenButtonLogic(){
        if (!nombreField.getText().isEmpty() & !thcField.getText().isEmpty() & !rasafield.getText().isEmpty() & !creadorField.getText().isEmpty() & !comentariosField.getText().isEmpty()){
            Cepa aux = new Cepa(nombreField.getText(), Double.parseDouble(thcField.getText()), rasafield.getText(),comentariosField.getText(),creadorField.getText(),Integer.parseInt(stockField.getText()));
            con.registrarCepas(aux);
            Integer auxId = con.consultarIdGen(aux.getNombre());
            System.out.println("\nID Auxiliar: " + auxId);
            mm.getCbSyst().agregarCepaBanco(auxId,aux);
            mm.listarGensBancos();
            dispose();
            JOptionPane.showMessageDialog(null,"Genetica Registrada Correctamente");
        }else JOptionPane.showMessageDialog(null,"Ingrese los datos correctamente");
    }

}
