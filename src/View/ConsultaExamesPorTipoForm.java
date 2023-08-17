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

public class ConsultaExamesPorTipoForm extends JFrame {
    private JComboBox<String> tiposExameComboBox;
    private JTable examesPorTipoTable;

    public ConsultaExamesPorTipoForm() {
        setTitle("Consulta de Exames por Tipo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel comboBoxPanel = new JPanel();
        tiposExameComboBox = new JComboBox<>();
        preencherComboBoxTiposExame();
        comboBoxPanel.add(new JLabel("Selecione o Tipo de Exame:"));
        comboBoxPanel.add(tiposExameComboBox);
        container.add(comboBoxPanel, BorderLayout.NORTH);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID Agendamento");
        colunas.add("Paciente");
        colunas.add("Médico");
        colunas.add("Data e Hora");

        Vector<Vector<String>> dados = new Vector<>();

        tiposExameComboBox.addActionListener(e -> {
            dados.clear();
            String tipoExameSelecionado = (String) tiposExameComboBox.getSelectedItem();

            Connection conexao = DB.connecta();
            String sql = "SELECT Agendamentos.ID, Pacientes.Nome AS NomePaciente, Medicos.Nome AS NomeMedico, " +
                    "DataHoraAgendamento " +
                    "FROM Agendamentos " +
                    "INNER JOIN Pacientes ON Agendamentos.PacienteID = Pacientes.ID " +
                    "INNER JOIN Medicos ON Agendamentos.MedicoID = Medicos.ID " +
                    "INNER JOIN Exames ON Agendamentos.ExameID = Exames.ID " +
                    "WHERE Exames.TipoExame = ?";

            try {
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, tipoExameSelecionado);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Vector<String> linha = new Vector<>();
                    linha.add(resultSet.getString("ID"));
                    linha.add(resultSet.getString("NomePaciente"));
                    linha.add(resultSet.getString("NomeMedico"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dataHora = dateFormat.format(resultSet.getTimestamp("DataHoraAgendamento"));
                    linha.add(dataHora);

                    dados.add(linha);
                }

                DB.desconecta(conexao);
                examesPorTipoTable.setModel(new DefaultTableModel(dados, colunas));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        examesPorTipoTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(examesPorTipoTable);
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

    private void preencherComboBoxTiposExame() {
        Connection conexao = DB.connecta();
        String sql = "SELECT DISTINCT TipoExame FROM Exames";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tiposExameComboBox.addItem(resultSet.getString("TipoExame"));
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
        SwingUtilities.invokeLater(() -> new ConsultaExamesPorTipoForm());
    }
}