package Model;

import java.math.BigDecimal;

public class Exame {
    private int id;
    private String nomeExame;
    private String tipoExame;
    private BigDecimal preco;

    public Exame() {}

    public Exame(int id, String nomeExame, String tipoExame, BigDecimal preco) {
        this.id = id;
        this.nomeExame = nomeExame;
        this.tipoExame = tipoExame;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
