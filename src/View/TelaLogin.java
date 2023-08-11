package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TelaLogin extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;

    private Map<String, String> usuarios = new HashMap<>(); // Simulação de dados de usuários e senhas

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Simulação de dados de usuários e senhas
        usuarios.put("usuario1", "senha1");
        usuarios.put("usuario2", "senha2");

        Container container = getContentPane();
        container.setLayout(new GridLayout(3, 2));

        container.add(new JLabel("Usuário:"));
        usuarioField = new JTextField();
        container.add(usuarioField);

        container.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        container.add(senhaField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                char[] senhaChars = senhaField.getPassword();
                String senha = new String(senhaChars);

                if (autenticarUsuario(usuario, senha)) {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Login bem-sucedido!");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new TelaPrincipal();
                            }
                        });
                } else {
                    JOptionPane.showMessageDialog(TelaLogin.this, "Usuário ou senha incorretos.");
                    setVisible(true);
                }
            }
        });

        container.add(new JLabel());
        container.add(loginButton);

        setVisible(true);
    }

    private boolean autenticarUsuario(String usuario, String senha) {
        setVisible(false);
        return usuario.equals("admin") && senha.equals("123");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin();
            }
        });
    }
}

