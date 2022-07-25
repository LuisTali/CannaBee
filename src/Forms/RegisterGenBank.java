package Forms;

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

    private MainMenu mm; //Para poder asociarlo con el Frame Creador.

    Connect con = new Connect();

    public RegisterGenBank(MainMenu parent) {
        this.mm = parent; //Lo asocia.
        setTitle("Registrar Cepa");
        setContentPane(registerGenBankPane);
        setMinimumSize(new Dimension(350,350));
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
            Cepa aux = new Cepa(nombreField.getText(),rasafield.getText(),Double.parseDouble(thcField.getText()),comentariosField.getText(),creadorField.getText());
            con.registrarCepas(aux);
            mm.getCbSyst().agregarCepaBanco(creadorField.getText(),aux);
            dispose();
        }else JOptionPane.showMessageDialog(null,"Ingrese los datos correctamente");
    }

}
