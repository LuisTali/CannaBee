package Clases;

import UserRelated.User;

import java.sql.*;
import java.util.ArrayList;

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

    public void registrarCepas(Cepa c) {
        Connection con;
        String sql = "INSERT INTO geneticas (nombre,thc,rasa,bancoGen,comments,stock) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setDouble(2, c.getThc());
            ps.setString(3, c.getRaza());
            ps.setString(4, c.getBanco());
            ps.setString(5, c.getComentarios());
            ps.setInt(6, c.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//Escribir codigo para registrar Cepas.

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
                auxCepa.setStock(rs.getInt(7));
                auxCepas.añadir(auxCepa.getId(), auxCepa);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auxCepas;
    }

    public Integer consultarIdGen(String nombre) {
        Cepa auxC = null;
        Integer auxId = null;
        Connection con;
        PreparedStatement ps;
        String sql = "SELECT * FROM geneticas WHERE nombre=?";
        ResultSet rs;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                auxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auxId;
    }

    public Integer consultarStockGen(String nombre) {
        Integer auxStock = null;
        Connection con;
        PreparedStatement ps;
        String sql = "SELECT * FROM geneticas WHERE nombre=?";
        ResultSet rs;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                auxStock = rs.getInt(7);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auxStock;
    }

    public void actualizarStock(String nombre, Integer auxStock) { //Al momento de comprar, recibir el stock deseado y actualizarlo en MySQL.
        Connection con;
        String sql = "UPDATE geneticas SET stock=? WHERE nombre=?";
        PreparedStatement ps;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, consultarStockGen(nombre) - auxStock);
            ps.setString(2, nombre);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> returnBanksName() { //Consulta a MySQL los nombres de los bancos, mete uno solo de cada uno y lo retorna en un ArrayList.
        ArrayList<String> nombres = null;
        boolean flag = true;
        Connection con;
        PreparedStatement ps;
        String sql = "SELECT * FROM geneticas";
        ResultSet rs;
        try {
            nombres = new ArrayList<>();
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String auxB = rs.getString(5);
                while (flag) {
                    for (String nombre : nombres) {
                        if (auxB.equals(nombre)) flag = false;
                    }
                    if (flag) nombres.add(auxB);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombres;
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
