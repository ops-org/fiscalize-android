package br.net.ops.fiscalize.vo;


import java.util.Date;

import br.net.ops.fiscalize.utils.Utilidade;

public class Parlamentar {

    private Integer parlamentarId;
    private String nome;
    private String nomeCivil;
    private String email;
    private String profissao;
    private Integer ideCadastro;

    private Partido partido;

    public String getUrlImagem() {
        return Utilidade.IMG_PARLAMENTAR_URL + ideCadastro + Utilidade.IMG_PARLAMENTAR_EXT;
    }

    public String toString() {
        return String.valueOf(nome);
    }

    public Integer getParlamentarId() {
        return parlamentarId;
    }

    public void setParlamentarId(Integer parlamentarId) {
        this.parlamentarId = parlamentarId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Integer getIdeCadastro() {
        return ideCadastro;
    }

    public void setIdeCadastro(Integer ideCadastro) {
        this.ideCadastro = ideCadastro;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
