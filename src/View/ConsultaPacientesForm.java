package View;

import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela de consulta
            }
        });
        buttonPanel.add(fecharButton);

        JButton exportarButton = new JButton("Exportar para TXT");
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarConsultaParaArquivo(dados);
            }
        });
        buttonPanel.add(exportarButton);

        container.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void exportarConsultaParaArquivo(Vector<Vector<String>> dados) {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (Vector<String> linha : dados) {
                    String linhaFormatada = String.join(", ", linha);
                    printWriter.println(linhaFormatada);
                }

                printWriter.close();
                fileWriter.close();

                JOptionPane.showMessageDialog(this, "Dados exportados para o arquivo com sucesso!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao exportar os dados para o arquivo.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPacientesForm();
            }
        });
    }
}