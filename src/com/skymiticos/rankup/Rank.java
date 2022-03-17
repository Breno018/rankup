// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

public class Rank
{
    private int id;
    private String nome;
    private String display;
    private double pontos;
    private double preco;
    
    public Rank(final int id, final String nome, final String display, final double pontos, final double preco) {
        this.id = id;
        this.nome = nome;
        this.display = display;
        this.pontos = pontos;
        this.preco = preco;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(final String nome) {
        this.nome = nome;
    }
    
    public String getDisplay() {
        return this.display;
    }
    
    public void setDisplay(final String display) {
        this.display = display;
    }
    
    public double getPontos() {
        return this.pontos;
    }
    
    public void setPontos(final double pontos) {
        this.pontos = pontos;
    }
    
    public double getPreco() {
        return this.preco;
    }
    
    public void setPreco(final double preco) {
        this.preco = preco;
    }
}
