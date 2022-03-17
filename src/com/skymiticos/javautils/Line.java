// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import org.bukkit.scoreboard.Team;

class Line
{
    private final String defaultEntry;
    private String fixColor;
    private Team team;
    private final int line;
    private boolean isSet;
    
    Line(final String entry, final Team team, final int line) {
        this.fixColor = "";
        this.isSet = false;
        this.team = team;
        this.line = line;
        this.defaultEntry = entry;
        this.updateEntry();
    }
    
    private void updateEntry() {
        if (this.defaultEntry != null) {
            this.team.removeEntry(this.getEntry());
        }
        this.team.addEntry(this.getEntry());
    }
    
    void fixColor(final String color) {
        this.fixColor = color;
        this.updateEntry();
    }
    
    String getFixColor() {
        return this.fixColor;
    }
    
    String getEntry() {
        return this.defaultEntry + this.fixColor;
    }
    
    Team getTeam() {
        return this.team;
    }
    
    int getLine() {
        return this.line;
    }
    
    boolean isSet() {
        return this.isSet;
    }
    
    void setSet(final boolean set) {
        this.isSet = set;
    }
}
