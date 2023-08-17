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
import java.time.LocalDate;
import java.time.Month;
import java.util.Vector;

public class PacientesAniversariantesForm extends JFrame {
    private JTable aniversariantesTable;

    public PacientesAniversariantesForm() {
        setTitle("Pacientes Aniversariantes do Mês");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        String[] opcoesFiltro = {"Aniversariantes do Mês", "Aniversariantes do Dia"};
        JComboBox<String> filtroComboBox = new JComboBox<>(opcoesFiltro);
        filtroComboBox.addItemListener(e -> atualizarTabelaComFiltro(filtroComboBox.getSelectedItem().toString()));
        container.add(filtroComboBox, BorderLayout.NORTH);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID");
        colunas.add("Nome");
        colunas.add("Data de Nascimento");

        Vector<Vector<String>> dados = new Vector<>();

        Connection conexao = DB.connecta();
        String sql = "SELECT * FROM Pacientes WHERE MONTH(DataNascimento) = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, LocalDate.now().getMonthValue()); // Obtém o mês atual
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("Nome"));

                // Formata a data de nascimento no formato dd/MM/yyyy
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataNascimento = dateFormat.format(resultSet.getDate("DataNascimento"));
                linha.add(dataNascimento);

                dados.add(linha);
            }

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        aniversariantesTable = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(aniversariantesTable);
        container.add(scrollPane, BorderLayout.CENTER);

        JButton fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(e -> dispose()); // Fecha a janela de consulta
        container.add(fecharButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void atualizarTabelaComFiltro(String filtro) {
        DefaultTableModel tableModel = (DefaultTableModel) aniversariantesTable.getModel();
        tableModel.setRowCount(0); // Limpa os dados da tabela

        Connection conexao = DB.connecta();
        String sql = "";

        if (filtro.equals("Aniversariantes do Mês")) {
            sql = "SELECT * FROM Pacientes WHERE MONTH(DataNascimento) = ?";
        } else if (filtro.equals("Aniversariantes do Dia")) {
            sql = "SELECT * FROM Pacientes WHERE MONTH(DataNascimento) = ? AND DAY(DataNascimento) = ?";
        }

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            if (filtro.equals("Aniversariantes do Mês")) {
                preparedStatement.setInt(1, LocalDate.now().getMonthValue()); // Obtém o mês atual
            } else if (filtro.equals("Aniversariantes do Dia")) {
                preparedStatement.setInt(1, LocalDate.now().getMonthValue()); // Obtém o mês atual
                preparedStatement.setInt(2, LocalDate.now().getDayOfMonth()); // Obtém o dia atual
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vector<String> linha = new Vector<>();
                linha.add(resultSet.getString("ID"));
                linha.add(resultSet.getString("Nome"));

                // Formata a data de nascimento no formato dd/MM/yyyy
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataNascimento = dateFormat.format(resultSet.getDate("DataNascimento"));
                linha.add(dataNascimento);

                tableModel.addRow(linha);
            }

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PacientesAniversariantesForm());
    }
}
