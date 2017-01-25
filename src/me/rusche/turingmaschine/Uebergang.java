/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.rusche.turingmaschine;

import java.io.Serializable;

/**
 *
 * @author Dirk Rusche <dirk@rusche.me>
 */
public class Uebergang implements Serializable {
    private final String zustand;
    private final String zielzustand;
    private final Richtung richtung;
    
    private Character buchstabe = null;
    private Character buchstabe_neu = null;

    public Uebergang(String zustand, String zielzustand, Richtung richtung) {
	this.zustand = zustand;
	this.zielzustand = zielzustand;
	this.richtung = richtung;
    }
    
    public Uebergang(String zustand, Character buchstabe, String zielzustand, Character buchstabe_neu, Richtung richtung) {
	this(zustand, zielzustand, richtung);
	this.buchstabe = buchstabe;
	this.buchstabe_neu = buchstabe_neu;
    }
    
    
    public String toString() {
        return "state " + getZustand() + ", letter " + getBuchstabe() + ", targetstate " + getZielzustand() + ", newletter " + getBuchstabe_neu() + ", direction " + getRichtung();
    }
    
    public Character getBuchstabe() {
	return buchstabe;
    }
    public Character getBuchstabe_neu() {
	return buchstabe_neu;
    }

    public String getZustand() {
	return zustand;
    }

    public String getZielzustand() {
	return zielzustand;
    }

    public Richtung getRichtung() {
	return richtung;
    }
    
}
