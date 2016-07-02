package br.net.ops.fiscalize.vo;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Partido partido = (Partido) o;

        return new EqualsBuilder()
                .append(partidoId, partido.partidoId)
                .append(sigla, partido.sigla)
                .append(nome, partido.nome)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(partidoId)
                .append(sigla)
                .append(nome)
                .toHashCode();
    }
}
