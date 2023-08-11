//package Sevices;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DB {//DAO - DATA ACCESS OBJECT
//
//    public static Connection connecta(){
//        String user = "root";
//        String pass = "";
//        String url = "jdbc:mysql://localhost:3306/medicalLaboratory";
//        Connection conn = null;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            conn = DriverManager.getConnection(url,user,pass);
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("Erro ao abrir o driver JDBC");
//        } catch (SQLException e) {
//            System.out.println("Erro ao conectar ao banco!");
//            System.err.println(e);
//        }
//
//        return conn;
//    }
//
//    public static void desconecta(Connection conn){
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("Erro ao desconectar do banco");
//            System.out.println(e);
//        }
//    }
//}

package Sevices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static Connection conexao;
    public static Connection connecta(){
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/medicalLaboratory";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Adicione uma variável de classe para armazenar a conexão
            Connection conexao = DriverManager.getConnection(url, user, pass);
            return conexao;

        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao abrir o driver JDBC");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco!");
            System.err.println(e);
        }

        return null; // Retorna null se a conexão não pôde ser estabelecida
    }

    public static void desconecta(Connection conexao){
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao desconectar do banco");
            System.out.println(e);
        }
    }

    public static boolean isConnected() {
        return conexao != null;
    }
}

