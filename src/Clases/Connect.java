package Clases;

import UserRelated.User;

import java.sql.*;

public class Connect {
    private static Connection con;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/cannabis";

    public Connection getConnection() {
        con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }

    public User crearUser(String nombre, String correo, String password, boolean esAdmin) {
        User usuario = new User(nombre, correo, password, esAdmin);
        return usuario;
    }

    public void añadirUser(User u) {
        Connection conn;
        String sql = "INSERT INTO usuarios (nombre, correo, password, esAdmin) VALUES (?,?,?,?)";
        PreparedStatement ps;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setBoolean(4, u.isAdmin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User autentificar(String mail, char[] password) {
        Connection conn;
        //El =? es para dejar la variable como incognita y luego asignarle un valor.
        String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
        PreparedStatement ps;
        ResultSet rs;
        User usuario = null;
        try {
            //Realiza la conexion con la base de datos MySQL.
            conn = getConnection();
            //Prepara el Statement para luego generar la consulta.
            ps = conn.prepareStatement(sql);
            //Setea el parametro 1 llamado correo y le asigna el enviado por parametro.
            ps.setString(1, mail);
            //Setea el parametro 2 llamado password y le asigna el enviado por parametro.
            ps.setString(2, String.valueOf(password));
            //Ejecuta la consulta.
            rs = ps.executeQuery();
            //Mientras siga habiendo filas con mail y password igual a los establecidos.
            while (rs.next()) {
                usuario = new User();
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setEmail(rs.getString("correo"));
                usuario.setPassword(rs.getString("password"));
                usuario.setAdmin(rs.getBoolean("esAdmin"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public void registrarCepas(){}//Escribir codigo para registrar Cepas.

    public HashMapGen<Integer, Cepa> consultarCepasBancos() {
        HashMapGen<Integer, Cepa> auxCepas = null;
        Cepa auxCepa = null;
        Connection con;
        String sql = "SELECT * FROM geneticas";
        PreparedStatement ps;
        ResultSet rs;
        try {
            auxCepas = new HashMapGen<>();
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                auxCepa = new Cepa();
                auxCepa.setId(rs.getInt(1));
                auxCepa.setNombre(rs.getString(2));
                auxCepa.setThc(rs.getDouble(3));
                auxCepa.setRaza(rs.getString(4));
                auxCepa.setBanco(rs.getString(5));
                auxCepa.setComentarios(rs.getString(6));
                auxCepas.añadir(auxCepa.getId(), auxCepa);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auxCepas;
    }

    public String returnIDKeys() {
        StringBuilder buffer = new StringBuilder();
        Connection con;
        String sql = "SELECT * FROM usuarios";
        PreparedStatement ps;
        ResultSet rs;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                buffer.append(rs.getInt(1));
                buffer.append(" ");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public void desconnection() {
        con = null;
    }
}
