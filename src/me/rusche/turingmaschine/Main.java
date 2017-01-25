/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.rusche.turingmaschine;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Dirk Rusche <dirk@rusche.me>
 */
public class Main {
    public static void main(String[] args) throws Exception {       
        HashSet<Uebergang> transitions = new HashSet<>();
        
        transitions.add(new Uebergang("q0", '0', "q0", '0', Richtung.r));
        transitions.add(new Uebergang("q0", '1', "q0", '1', Richtung.r));
        transitions.add(new Uebergang("q0", "q1", Richtung.l));
        
        transitions.add(new Uebergang("q1", '0', "q1", '0', Richtung.l));
        transitions.add(new Uebergang("q1", '1', "q2", '0', Richtung.l));
        
        transitions.add(new Uebergang("q2", '0', "q1", '1', Richtung.l));
        transitions.add(new Uebergang("q2", '1', "q2", '1', Richtung.l));
        transitions.add(new Uebergang("q2", null, "q3", '1', Richtung.s));
	
	
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	ObjectOutputStream so = new ObjectOutputStream(bo);
	so.writeObject(transitions);
	so.flush();
	System.out.println(Base64.getEncoder().encode(bo.toByteArray()));
	
        
        Set<Character> alphabet = new HashSet<>();
        alphabet.add('0');
        alphabet.add('1');
        
        Set<String> states = new HashSet<>();
        states.add("q0");
        states.add("q1");
        states.add("q2");
        states.add("q3");
        
        Set<Character> bandalphabet = new HashSet<>();
        bandalphabet.addAll(alphabet);
        
        Set<String> finalstate = new HashSet<>();
        finalstate.add("q3");
        
        String startstate = "q0";
        
        
        TuringMaschine tm = new TuringMaschine(alphabet, states, bandalphabet, transitions, startstate, finalstate);
	
	Scanner sc = new Scanner(System.in);
	
	System.out.print("Eingabe: ");
	while (sc.hasNext()) {
	    String eingabe = sc.next();
	    tm.run(eingabe);
	    System.out.print("Eingabe: ");
	}
    }
}
