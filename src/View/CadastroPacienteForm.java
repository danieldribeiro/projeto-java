package View;
import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroPacienteForm extends JFrame {
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField generoField;
    private JTextField telefoneField;
    private JTextField enderecoField;

    public CadastroPacienteForm() {
        setTitle("Cadastro de Paciente");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(8, 2));

        container.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        container.add(nomeField);

        container.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
        dataNascimentoField = new JTextField();
        container.add(dataNascimentoField);

        container.add(new JLabel("Gênero:"));
        generoField = new JTextField();
        container.add(generoField);

        container.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        container.add(telefoneField);

        container.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        container.add(enderecoField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPaciente();
            }
        });

        JButton voltarButton = new JButton("Voltar à Tela Principal");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retornarTelaPrincipal();
            }
        });

        container.add(new JLabel());
        container.add(cadastrarButton);
        container.add(new JLabel());
        container.add(voltarButton);

        setVisible(true);
    }

    private void cadastrarPaciente() {
        String nome = nomeField.getText();
        String dataNascimentoStr = dataNascimentoField.getText();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
        String genero = generoField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        Connection conexao = DB.connecta();
        try {
            String sql = "INSERT INTO Pacientes (Nome, DataNascimento, Genero, Telefone, Endereco) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setObject(2, dataNascimento);
            preparedStatement.setString(3, genero);
            preparedStatement.setString(4, telefone);
            preparedStatement.setString(5, endereco);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar paciente.");
        } finally {
            if(conexao != null) {
                DB.desconecta(conexao);
            }
        }
        }

    private void retornarTelaPrincipal() {
//        TelaPrincipal telaPrincipal = new TelaPrincipal();
//        telaPrincipal.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroPacienteForm();
            }
        });
    }
}
