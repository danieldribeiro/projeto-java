package View;

import Sevices.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

        // ...

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

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(e -> dispose()); // Fecha a janela de consulta
        container.add(fecharButton, BorderLayout.SOUTH);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaExamesPorPacienteForm());
    }
}

