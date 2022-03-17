// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

public class Group
{
    private String tag;
    private String ordem;
    private String permissao;
    private boolean padrao;
    
    public Group(final String tag, final String ordem, final String permissao, final boolean padrao) {
        this.tag = tag;
        this.permissao = permissao;
        this.padrao = padrao;
        this.ordem = ordem;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public String getPermissao() {
        return this.permissao;
    }
    
    public void setPermissao(final String permissao) {
        this.permissao = permissao;
    }
    
    public boolean isPadrao() {
        return this.padrao;
    }
    
    public void setPadrao(final boolean padrao) {
        this.padrao = padrao;
    }
    
    public String getOrdem() {
        return this.ordem;
    }
    
    public void setOrdem(final String ordem) {
        this.ordem = ordem;
    }
}
