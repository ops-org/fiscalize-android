package br.net.ops.fiscalize.vo;


import java.util.Date;

public class NotaFiscal {

    private int notaFiscalId;
    private String nomeParlamentar;
    private String emailParlamentar;
    private String urlFotoParlamentar;
    private String nomePartido;
    private String siglaPartido;
    private String urlFotoPartido;
    private String cota;
    private String UF;
    private Date dataEmissao;

    private String descricao;
    private String fornecedor;
    private int ano;
    private int mes;
    private int numeroDocumento;
    private String valor;
    private String valorGlosa;
    private String valorLiquido;

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    private String cpfCnpj;



    public int getNotaFiscalId() {
        return notaFiscalId;
    }

    public void setNotaFiscalId(int notaFiscalId) {
        this.notaFiscalId = notaFiscalId;
    }

    public String getNomeParlamentar() {
        return nomeParlamentar;
    }

    public void setNomeParlamentar(String nomeParlamentar) {
        this.nomeParlamentar = nomeParlamentar;
    }

    public String getEmailParlamentar() {
        return emailParlamentar;
    }

    public void setEmailParlamentar(String emailParlamentar) {
        this.emailParlamentar = emailParlamentar;
    }

    public String getUrlFotoParlamentar() {
        return urlFotoParlamentar;
    }

    public void setUrlFotoParlamentar(String urlFotoParlamentar) {
        this.urlFotoParlamentar = urlFotoParlamentar;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public String getNomePartido() {
        return nomePartido;
    }

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

    public String getUrlFotoPartido() {
        return urlFotoPartido;
    }

    public void setUrlFotoPartido(String urlFotoPartido) {
        this.urlFotoPartido = urlFotoPartido;
    }

    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorGlosa() {
        return valorGlosa;
    }

    public void setValorGlosa(String valorGlosa) {
        this.valorGlosa = valorGlosa;
    }

    public String getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(String valorLiquido) {
        this.valorLiquido = valorLiquido;
    }



}
