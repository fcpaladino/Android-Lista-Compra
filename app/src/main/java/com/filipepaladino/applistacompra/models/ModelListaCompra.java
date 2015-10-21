package com.filipepaladino.applistacompra.models;

/**
 * Created by Filipe Paladino on 21/10/2015.
 */
public class ModelListaCompra {

    private long id;
    private String nome;
    private Double valor;
    private Integer ativo;


    public ModelListaCompra() {}

    public ModelListaCompra(long id, String nome, Double valor, Integer ativo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ativo = ativo;
    }

    public ModelListaCompra(String nome, Double valor, Integer ativo) {
        this.nome = nome;
        this.valor = valor;
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}
