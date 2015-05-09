package br.net.ops.fiscalize.vo;


public class Cota {

    private Integer cotaId;
    private String nome;

    public String toString() {
        return String.valueOf(nome);
    }

    public Integer getCotaId() {
        return cotaId;
    }

    public void setCotaId(Integer cotaId) {
        this.cotaId = cotaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
