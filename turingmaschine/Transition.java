/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingmaschine;

/**
 *
 * @author Dirk Rusche <dirk@rusche.me>
 */
class Transition {
    public final String state;
    public final char letter;
    public final String targetstate;
    public final char newletter;
    public final Direction direction;
    
    public final boolean isBlock;
    public final boolean newletter_block;

    public Transition(String state, char letter, String targetstate, char newletter, Direction direction) {
        this.state = state;
        this.letter = letter;
        this.targetstate = targetstate;
        this.newletter = newletter;
        this.direction = direction;
        
        this.isBlock = false;
        this.newletter_block = false;
    }

    public Transition(String state, String targetstate, char newletter, Direction direction) {
        this.state = state;
        this.targetstate = targetstate;
        this.newletter = newletter;
        this.direction = direction;
        
        this.isBlock = true;
        this.letter = 'B';
        this.newletter_block = false;
    }

    public Transition(String state, String targetstate, Direction direction) {
        this.state = state;
        this.targetstate = targetstate;
        this.direction = direction;
        
        this.letter = '-';
        this.newletter = '-';
        this.newletter_block = true;
        this.isBlock = true;
    }
    
    public String toString() {
        return "state " + state + ", letter " + letter + ", targetstate " + targetstate + ", newletter " + newletter + ", direction " + direction + ", isBlock " + isBlock + ",  newletter_block " + newletter_block;
    }
    
    
}
