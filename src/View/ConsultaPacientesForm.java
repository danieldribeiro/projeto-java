package View;

import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ConsultaPacientesForm extends JFrame {
    private JTable pacientesTable;

    public ConsultaPacientesForm() {
        setTitle("Consulta de Pacientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Vector<String> colunas = new Vector<>();
        colunas.add("ID");
        colunas.add("Nome");
        colunas.add("Data de Nascimento");
        colunas.add("Gênero");
        colunas.add("Telefone");
        colunas.add("Endereço");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conexao = DB.connecta();
        String sql = "SELECT * FROM Pacientes";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("Nome"));
                linha.add(resultSet.getString("DataNascimento"));
                linha.add(resultSet.getString("Genero"));
                linha.add(resultSet.getString("Telefone"));
                linha.add(resultSet.getString("Endereco"));
                dados.add(linha);
            }

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        pacientesTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(pacientesTable);
        container.add(scrollPane, BorderLayout.CENTER);

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela de consulta
            }
        });
        container.add(fecharButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPacientesForm();
            }
        });
    }
}
