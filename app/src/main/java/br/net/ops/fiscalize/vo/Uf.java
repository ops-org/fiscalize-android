package br.net.ops.fiscalize.vo;


public class Uf {

    private Integer ufId;
    private String sigla;
    private String nome;

    public String toString() {
        return String.valueOf(nome) + " - " + String.valueOf(sigla);
    }

    public Integer getUfId() {
        return ufId;
    }

    public void setUfId(Integer ufId) {
        this.ufId = ufId;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
