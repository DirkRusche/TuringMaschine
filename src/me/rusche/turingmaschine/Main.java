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
        
        transitions.add(new Uebergang("q0", 'a', "q1", 'x', Richtung.s));
	transitions.add(new Uebergang("q0", 'b', "q0", 'b', Richtung.r));
	transitions.add(new Uebergang("q0", 'x', "q0", 'x', Richtung.r));
        transitions.add(new Uebergang("q0", "q4", Richtung.l));
        
        transitions.add(new Uebergang("q1", 'a', "q1", 'a', Richtung.l));
	transitions.add(new Uebergang("q1", 'b', "q1", 'b', Richtung.l));
	transitions.add(new Uebergang("q1", 'x', "q1", 'x', Richtung.l));
        transitions.add(new Uebergang("q1", "q2", Richtung.r));
	
	transitions.add(new Uebergang("q2", 'a', "q2", 'a', Richtung.r));
	transitions.add(new Uebergang("q2", 'b', "q3", 'x', Richtung.s));
	transitions.add(new Uebergang("q2", 'x', "q2", 'x', Richtung.r));
	
	transitions.add(new Uebergang("q3", 'a', "q3", 'x', Richtung.l));
	transitions.add(new Uebergang("q3", 'b', "q3", 'b', Richtung.l));
	transitions.add(new Uebergang("q3", 'x', "q3", 'x', Richtung.l));
        transitions.add(new Uebergang("q3", "q0", Richtung.r));
	
	transitions.add(new Uebergang("q4", 'x', "q4", 'x', Richtung.l));
        transitions.add(new Uebergang("q4", "qF", Richtung.r));
	
	
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
	states.add("q4");
	states.add("qF");
        
        Set<Character> bandalphabet = new HashSet<>();
        bandalphabet.addAll(alphabet);
        
        Set<String> finalstate = new HashSet<>();
        finalstate.add("qF");
        
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
