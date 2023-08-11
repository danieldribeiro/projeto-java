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
import java.time.LocalDateTime;

public class AgendamentoExameForm extends JFrame {
    private JComboBox<String> pacienteComboBox;
    private JComboBox<String> medicoComboBox;
    private JComboBox<String> exameComboBox;
    private JTextField dataHoraField;

    public AgendamentoExameForm() {
        setTitle("Agendamento de Exame");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(6, 2));

        container.add(new JLabel("Paciente:"));
        pacienteComboBox = new JComboBox<>();
        preencherComboBoxPacientes();
        container.add(pacienteComboBox);

        container.add(new JLabel("Médico:"));
        medicoComboBox = new JComboBox<>();
        preencherComboBoxMedicos();
        container.add(medicoComboBox);

        container.add(new JLabel("Exame:"));
        exameComboBox = new JComboBox<>();
        preencherComboBoxExames();
        container.add(exameComboBox);

        container.add(new JLabel("Data e Hora (YYYY-MM-DDTHH:MM):"));
        dataHoraField = new JTextField();
        container.add(dataHoraField);

        JButton agendarButton = new JButton("Agendar");
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarExame();
            }
        });

        JButton voltarButton = new JButton("Voltar à Tela Principal");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retornarTelaPrincipal();
            }
        });

        container.add(new JLabel());
        container.add(agendarButton);
        container.add(new JLabel());
        container.add(voltarButton);

        setVisible(true);
    }

    private void preencherComboBoxPacientes() {
        Connection conexao = DB.connecta();

        if (conexao != null) {
            try {
                String sql = "SELECT Nome FROM Pacientes";
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomePaciente = resultSet.getString("Nome");
                    pacienteComboBox.addItem(nomePaciente);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conexao);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void preencherComboBoxMedicos() {
        Connection conexao = DB.connecta();

        if (conexao != null) {
            try {
                String sql = "SELECT Nome FROM Medicos";
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomeMedico = resultSet.getString("Nome");
                    medicoComboBox.addItem(nomeMedico);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conexao);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void preencherComboBoxExames() {
        Connection conexao = DB.connecta();

        if (conexao != null) {
            try {
                String sql = "SELECT NomeExame FROM Exames";
                PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nomeExame = resultSet.getString("NomeExame");
                    exameComboBox.addItem(nomeExame);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DB.desconecta(conexao);
            }
        } else {
            System.out.println("Não foi possível obter a conexão com o banco de dados.");
        }
    }

    private void agendarExame() {
        String paciente = (String) pacienteComboBox.getSelectedItem();
        String medico = (String) medicoComboBox.getSelectedItem();
        String exame = (String) exameComboBox.getSelectedItem();
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraField.getText());

        if (verificarConflitoHorarios(medico, dataHora)) {
            JOptionPane.showMessageDialog(this, "Conflito de horários! O médico já possui um agendamento para esse horário.");
            return;
        }

        if (!verificarIntervaloMinimo(medico, dataHora)) {
            JOptionPane.showMessageDialog(this, "Intervalo mínimo de 15 minutos entre agendamentos consecutivos para o mesmo médico.");
            return;
        }

        Connection conexao = DB.connecta();
        String sql = "INSERT INTO Agendamentos (PacienteID, MedicoID, ExameID, DataHoraAgendamento) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdPaciente(paciente));
            preparedStatement.setInt(2, obterIdMedico(medico));
            preparedStatement.setInt(3, obterIdExame(exame));
            preparedStatement.setObject(4, dataHora);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Exame agendado com sucesso!");

            // Atualiza a coluna Realizado no exame
            atualizarExameRealizado(obterIdExame(exame));

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao agendar exame.");
        }
    }


//    private void agendarExame() {
//        String paciente = (String) pacienteComboBox.getSelectedItem();
//        String medico = (String) medicoComboBox.getSelectedItem();
//        String exame = (String) exameComboBox.getSelectedItem();
//        LocalDateTime dataHora = LocalDateTime.parse(dataHoraField.getText());
//
//        if (verificarConflitoHorarios(medico, dataHora)) {
//            JOptionPane.showMessageDialog(this, "Conflito de horários! O médico já possui um agendamento para esse horário.");
//            return;
//        }
//
//        if (!verificarIntervaloMinimo(medico, dataHora)) {
//            JOptionPane.showMessageDialog(this, "Intervalo mínimo de 15 minutos entre agendamentos consecutivos para o mesmo médico.");
//            return;
//        }
//
//        Connection conexao = DB.connecta();
//        String sql = "INSERT INTO Agendamentos (PacienteID, MedicoID, ExameID, DataHoraAgendamento) VALUES (?, ?, ?, ?)";

//        try {
//            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
//            preparedStatement.setInt(1, obterIdPaciente(paciente));
//            preparedStatement.setInt(2, obterIdMedico(medico));
//            preparedStatement.setInt(3, obterIdExame(exame));
//            preparedStatement.setObject(4, dataHora);
//
//            atualizarExameRealizado(obterIdExame(exame));
//
//            preparedStatement.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Exame agendado com sucesso!");
//
//            DB.desconecta(conexao);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Erro ao agendar exame.");
//        }

//    }

    private boolean verificarConflitoHorarios(String medico, LocalDateTime dataHora) {
        Connection conexao = DB.connecta();
        String sql = "SELECT COUNT(*) FROM Agendamentos WHERE MedicoID = ? AND DataHoraAgendamento = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdMedico(medico));
            preparedStatement.setObject(2, dataHora);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            DB.desconecta(conexao);

            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean verificarIntervaloMinimo(String medico, LocalDateTime dataHora) {
        Connection conexao = DB.connecta();
        String sql = "SELECT MAX(DataHoraAgendamento) FROM Agendamentos WHERE MedicoID = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, obterIdMedico(medico));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LocalDateTime ultimaDataHora = resultSet.getTimestamp(1).toLocalDateTime();
                LocalDateTime minimaDataHora = ultimaDataHora.plusMinutes(15);
                return dataHora.isAfter(minimaDataHora);
            } else {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DB.desconecta(conexao);
        }
    }

    private int obterIdPaciente(String nomePaciente) {
        Connection conexao = DB.connecta();
        String sql = "SELECT ID FROM Pacientes WHERE Nome = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nomePaciente);

            ResultSet resultSet = preparedStatement.executeQuery();

            int pacienteId = -1;

            if (resultSet.next()) {
                pacienteId = resultSet.getInt("ID");
            }

            DB.desconecta(conexao);

            return pacienteId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int obterIdMedico(String nomeMedico) {
        Connection conexao = DB.connecta();
        String sql = "SELECT ID FROM Medicos WHERE Nome = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nomeMedico);

            ResultSet resultSet = preparedStatement.executeQuery();

            int medicoId = -1;

            if (resultSet.next()) {
                medicoId = resultSet.getInt("ID");
            }

            DB.desconecta(conexao);

            return medicoId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int obterIdExame(String nomeExame) {
        Connection conexao = DB.connecta();
        String sql = "SELECT ID FROM Exames WHERE NomeExame = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nomeExame);

            ResultSet resultSet = preparedStatement.executeQuery();

            int exameId = -1;

            if (resultSet.next()) {
                exameId = resultSet.getInt("ID");
            }

            DB.desconecta(conexao);

            return exameId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

//    private int atualizarExameRealizado(int exameId) {
//        Connection conexao = DB.connecta();
//        String sql = "UPDATE Exames SET Realizado = Realizado + 1 WHERE ID = ?";
//
//        try {
//            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
//            preparedStatement.setInt(1, exameId);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DB.desconecta(conexao);
//        }
//        return exameId;
//    }

    private void atualizarExameRealizado(int exameId) {
        Connection conexao = DB.connecta();
        String sql = "UPDATE Exames SET Realizado = Realizado + 1 WHERE ID = ?";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, exameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.desconecta(conexao);
        }
    }



    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AgendamentoExameForm();
            }
        });
    }
}
