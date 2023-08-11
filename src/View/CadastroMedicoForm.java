package View;
import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroMedicoForm extends JFrame {
    private JTextField nomeField;
    private JTextField especialidadeField;
    private JTextField telefoneField;

    public CadastroMedicoForm() {
        setTitle("Cadastro de Médico");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(5, 2));

        container.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        container.add(nomeField);

        container.add(new JLabel("Especialidade:"));
        especialidadeField = new JTextField();
        container.add(especialidadeField);

        container.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        container.add(telefoneField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarMedico();
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

    private void cadastrarMedico() {
        String nome = nomeField.getText();
        String especialidade = especialidadeField.getText();
        String telefone = telefoneField.getText();

        Connection conexao = DB.connecta();
        String sql = "INSERT INTO Medicos (Nome, Especialidade, Telefone) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, especialidade);
            preparedStatement.setString(3, telefone);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!");

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar médico.");
        }
    }

    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroMedicoForm();
            }
        });
    }
}
