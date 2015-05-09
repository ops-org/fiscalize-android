package br.net.ops.fiscalize.vo;


import br.net.ops.fiscalize.utils.Utilidade;

public class Partido {

    private Integer partidoId;
    private String sigla;
    private String nome;

    public String getUrlImagem() {
        return Utilidade.IMG_PARTIDO_URL + sigla + Utilidade.IMG_PARTIDO_EXT;
    }

    public String toString() {
        return String.valueOf(nome) + " - " + String.valueOf(sigla);
    }

    public Integer getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Integer partidoId) {
        this.partidoId = partidoId;
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
