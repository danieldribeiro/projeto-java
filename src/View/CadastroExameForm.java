package View;

import Sevices.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroExameForm extends JFrame {
    private JTextField nomeExameField;
    private JTextField tipoExameField;
    private JTextField precoField;

    public CadastroExameForm() {
        setTitle("Cadastro de Exame");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(5, 2));

        container.add(new JLabel("Nome do Exame:"));
        nomeExameField = new JTextField();
        container.add(nomeExameField);

        container.add(new JLabel("Tipo de Exame:"));
        tipoExameField = new JTextField();
        container.add(tipoExameField);

        container.add(new JLabel("Preço:"));
        precoField = new JTextField();
        container.add(precoField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarExame();
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

    private void cadastrarExame() {
        String nomeExame = nomeExameField.getText();
        String tipoExame = tipoExameField.getText();
        BigDecimal preco = new BigDecimal(precoField.getText());

        Connection conexao = DB.connecta();
        String sql = "INSERT INTO Exames (NomeExame, TipoExame, Preco) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, nomeExame);
            preparedStatement.setString(2, tipoExame);
            preparedStatement.setBigDecimal(3, preco);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Exame cadastrado com sucesso!");

            DB.desconecta(conexao);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar exame.");
        }
    }

    private void retornarTelaPrincipal() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CadastroExameForm();
            }
        });
    }
}
