// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import java.util.Arrays;
import org.bukkit.entity.Player;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.DisplaySlot;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.ChatColor;
import java.util.List;

public class Board
{
    private static int count;
    private static final List<ChatColor> COLORS;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private String title;
    private final List<Line> lines;
    
    public Board(final String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.lines = new ArrayList<Line>();
        (this.objective = this.scoreboard.registerNewObjective("scorelib" + Board.count++, "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        this.setTitle(title);
        for (int i = 0; i < Board.COLORS.size(); ++i) {
            final ChatColor color = Board.COLORS.get(i);
            final Team team = this.scoreboard.registerNewTeam("line" + i);
            this.lines.add(i, new Line(color.toString(), team, i));
        }
    }
    
    public void setLines(final List<String> lines) {
        int start = lines.size();
        for (final String line : lines) {
            this.setLine(start--, line);
        }
    }
    
    public void setLine(final int line, final String value) {
        final Line boardLine = this.getLine(line);
        Validate.notNull((Object)boardLine, "The index should be between 0 and 21");
        this.updateLine(boardLine, value);
        if (!boardLine.isSet()) {
            this.objective.getScore(boardLine.getEntry()).setScore(line);
            boardLine.setSet(true);
        }
    }
    
    private void updateLine(final Line boardLine, String value) {
        value = ChatColor.translateAlternateColorCodes('&', value);
        if (value.length() <= 16) {
            boardLine.getTeam().setPrefix(value);
            return;
        }
        final String prefix = value.substring(0, 16);
        String suffix = value.substring(16);
        final String lastColor = ChatColor.getLastColors(prefix);
        if (!boardLine.getFixColor().equals(lastColor)) {
            this.removeLine(boardLine);
            boardLine.fixColor(lastColor);
        }
        if (suffix.length() > 16) {
            suffix = suffix.substring(0, 16);
        }
        boardLine.getTeam().setPrefix(prefix);
        boardLine.getTeam().setSuffix(suffix);
    }
    
    public void removeLine(final int line) {
        final Line boardLine = this.getLine(line);
        Validate.notNull((Object)boardLine, "The index should be between 0 and 21");
        this.removeLine(boardLine);
    }
    
    private void removeLine(final Line line) {
        this.scoreboard.resetScores(line.getEntry());
        line.setSet(false);
    }
    
    private Line getLine(final int index) {
        return this.lines.stream().filter(line -> line.getLine() == index).findFirst().orElse(null);
    }
    
    public void show(final Player player) {
        player.setScoreboard(this.scoreboard);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
        this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    static {
        Board.count = 0;
        COLORS = Arrays.asList(ChatColor.values());
    }
}
