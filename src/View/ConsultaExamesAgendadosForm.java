package View;

import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ConsultaExamesAgendadosForm extends JFrame {
    private JTable examesAgendadosTable;

    public ConsultaExamesAgendadosForm() {
        setTitle("Consulta de Exames Agendados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Paciente");
        colunas.add("Médico");
        colunas.add("Exame");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conexao = DB.connecta();
        String sql = "SELECT Agendamentos.ID, Pacientes.Nome AS NomePaciente, Medicos.Nome AS NomeMedico, " +
                "Exames.NomeExame AS NomeExame, DataHoraAgendamento " +
                "FROM Agendamentos " +
                "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("NomePaciente"));
                linha.add(resultSet.getString("NomeMedico"));
                linha.add(resultSet.getString("NomeExame"));

                // Formata a data e hora no formato dd/MM/yyyy HH:mm:ss
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                linha.add(dataHora);

                dados.add(linha);
            }

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        examesAgendadosTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesAgendadosTable);
        container.add(scrollPane, BorderLayout.CENTER);

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(e -> dispose()); // Fecha a janela de consulta
        container.add(fecharButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaExamesAgendadosForm());
    }
}

