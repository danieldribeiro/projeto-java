package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Tela Principal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new GridLayout(10, 1));

        JButton cadastrarPacienteButton = new JButton("Cadastrar Paciente");
        cadastrarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroPacienteForm();
            }
        });
        container.add(cadastrarPacienteButton);

        JButton cadastrarMedicoButton = new JButton("Cadastrar Médico");
        cadastrarMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroMedicoForm();
            }
        });
        container.add(cadastrarMedicoButton);

        JButton cadastrarExameButton = new JButton("Cadastrar Exame");
        cadastrarExameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroExameForm();
            }
        });
        container.add(cadastrarExameButton);

        JButton agendarExameButton = new JButton("Agendar Exame");
        agendarExameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgendamentoExameForm();
            }
        });
        container.add(agendarExameButton);

        JButton consultaTodosPacientesButton = new JButton("Consultar Todos os Pacientes");
        consultaTodosPacientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaPacientesForm();
            }
        });
        container.add(consultaTodosPacientesButton);

        JButton consultaAniversariantesButton = new JButton("Consultar Pacientes Aniversariantes do Mês");
        consultaAniversariantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PacientesAniversariantesForm();
            }
        });
        container.add(consultaAniversariantesButton);

        JButton consultaExamesPorTipoButton = new JButton("Consultar Exames por Tipo");
        consultaExamesPorTipoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesPorTipoForm();
            }
        });
        container.add(consultaExamesPorTipoButton);

        JButton consultaExamesAgendadosButton = new JButton("Consultar Exames Agendados");
        consultaExamesAgendadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesAgendadosForm();
            }
        });
        container.add(consultaExamesAgendadosButton);

        JButton consultaExamesRealizadosButton = new JButton("Consultar Exames Realizados");
        consultaExamesRealizadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesRealizadosForm();
            }
        });
        container.add(consultaExamesRealizadosButton);

        JButton consultaExamesPorPacienteButton = new JButton("Consultar Exames por Paciente");
        consultaExamesPorPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultaExamesPorPacienteForm();
            }
        });
        container.add(consultaExamesPorPacienteButton);

        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal();
            }
        });
    }
}

