package Model;

import java.time.LocalDateTime;

public class Agendamento {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private Exame exame;
    private LocalDateTime dataHoraAgendamento;

    public Agendamento() {}

    public Agendamento(int id, Paciente paciente, Medico medico, Exame exame, LocalDateTime dataHoraAgendamento) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.exame = exame;
        this.dataHoraAgendamento = dataHoraAgendamento;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public LocalDateTime getDataHoraAgendamento() {
        return dataHoraAgendamento;
    }

    public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
        this.dataHoraAgendamento = dataHoraAgendamento;
    }
}
