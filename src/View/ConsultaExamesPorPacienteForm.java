package View;

import Sevices.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

public class ConsultaExamesPorPacienteForm extends JFrame {
    private JComboBox<String> pacientesComboBox;
    private JTable examesPorPacienteTable;

    public ConsultaExamesPorPacienteForm() {
        setTitle("Consulta de Exames por Paciente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel comboBoxPanel = new JPanel();
        pacientesComboBox = new JComboBox<>();
        preencherComboBoxPacientes();
        comboBoxPanel.add(new JLabel("Selecione o Paciente:"));
        comboBoxPanel.add(pacientesComboBox);
        container.add(comboBoxPanel, BorderLayout.NORTH);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Médico");
        colunas.add("Exame");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        pacientesComboBox.addActionListener(e -> {
            dados.clear();
            String pacienteSelecionado = (String) pacientesComboBox.getSelectedItem();

            Connection conexao = DB.connecta();
            String sql = "SELECT Agendamentos.ID, Medicos.Nome AS NomeMedico, Exames.NomeExame AS NomeExame, " +
                    "Agendamentos.DataHoraAgendamento " +
                    "FROM Agendamentos " +
                    "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                    "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID " +
                    "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                    "WHERE Pacientes.Nome = ?";

            try {
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, pacienteSelecionado);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Vector<String> linha = new Vector<>();
                    linha.add(resultSet.getString("ID"));
                    linha.add(resultSet.getString("NomeMedico"));
                    linha.add(resultSet.getString("NomeExame"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                    linha.add(dataHora);

                    dados.add(linha);
                }

                DB.desconecta(conexao);
                examesPorPacienteTable.setModel(new DefaultTableModel(dados, colunas));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        examesPorPacienteTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesPorPacienteTable);
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

    private void preencherComboBoxPacientes() {
        Connection conexao = DB.connecta();
        String sql = "SELECT Nome FROM Pacientes";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pacientesComboBox.addItem(resultSet.getString("Nome"));
            }

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
        SwingUtilities.invokeLater(() -> new ConsultaExamesPorPacienteForm());
    }
}