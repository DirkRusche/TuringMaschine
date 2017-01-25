package me.rusche.turingmaschine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Dirk Rusche <dirk@rusche.me>
 */
public class TuringMaschine {
    private final Set<Character> alphabet = new HashSet<>();
    private final Set<String> zustaende = new HashSet<>();
    private final Set<Character> bandalphabet = new HashSet<>();
    private final Set<Uebergang> uebergaenge = new HashSet<>();
    private final String startzustand;
    private final char block = '\u25A1';
    private final Set<String> endzustaende = new HashSet<>();
    
    public TuringMaschine(Set<Character> alphabet, Set<String> states, Set<Character> bandalphabet, Set<Uebergang> transition, String startstate, Set<String> finalstate) throws Exception {
	this.bandalphabet.add(block);
	
        this.alphabet.addAll(alphabet);
        this.zustaende.addAll(states);
        this.bandalphabet.addAll(bandalphabet);
        this.uebergaenge.addAll(transition);
        this.startzustand = startstate;
        //this.block = block;
        this.endzustaende.addAll(finalstate);
        
        if (!bandalphabet.containsAll(alphabet)) {
            throw new Exception("Alphabet muss Teilmenge von Bandalphabet sein!");
        }
        if (!zustaende.contains(startzustand)) {
            throw new Exception("Startzustand muss Element von Zustaende sein!");
        }
        if (!zustaende.containsAll(endzustaende)) {
            throw new Exception("Endzustaende muss Teilmenge von Zustaende sein!");
        }
    }
    public void run(String eingabe) {
        TreeMap<Integer, Character> map = new TreeMap<>();
        
        for (int i = 0; i < eingabe.length(); i++) {
            map.put(i, eingabe.charAt(i));
        }
        
        step(map, startzustand, 0);       
    }
    
    
    private void step(TreeMap<Integer, Character> band, String zustand, int position) {
        this.print(band, zustand, position);
	
        String zustand_neu = null;
        int position_neu = position;
	
	Character zeichen = null;
	
	if (band.containsKey(position)) {
	    zeichen = band.get(position);
	}
        
        //System.out.println("zustand " + zustand + ", char " + zeichen);
                
        Uebergang uebergang = getUebergang(zustand, zeichen);
	
	//System.out.println(uebergang);
	
	if (uebergang == null) {
	    System.out.println("Kein Zustandsuebergang gefunden");
            
            if (endzustaende.contains(zustand)) {
                System.out.println("Zustand ist Endzustand");
            }
            
            print(band, zustand, position);
            System.out.println(Arrays.toString(band.values().toArray()));
            
            return;
	}
	
	Character buchstabe_neu = uebergang.getBuchstabe_neu();
            
	if (buchstabe_neu == null) {
	    band.remove(position);
	}
	else {
	    band.put(position, buchstabe_neu);
	}
	zustand_neu = uebergang.getZielzustand();

	if (uebergang.getRichtung() == Richtung.l) {
	    position_neu = position - 1;
	}
	else if (uebergang.getRichtung() == Richtung.r) {
	    position_neu = position + 1;
	}

        
        step(band, zustand_neu, position_neu);
    }
    
    private Uebergang getUebergang(String zustand, Character zeichen) {
	for (Uebergang uebergang : uebergaenge) {
            if (uebergang.getZustand().equals(zustand)) {
		if (zeichen == null && uebergang.getBuchstabe() == null) {
		    return uebergang;
		}
		if (zeichen != null && zeichen.equals(uebergang.getBuchstabe())) {
		    return uebergang;
		}
            }
        }
	return null;
    }
    
    private void print(TreeMap<Integer, Character> band, String zustand, int position) {
        System.out.print(block + " ");
        
        Set<Integer> keyset = band.keySet();
        Integer[] ints = keyset.toArray(new Integer[keyset.size()]);
        if (position < ints[0]) {
            System.out.print(zustand + " ");
            for (int i = position; i < ints[0]; i++) {
                System.out.print(block + " ");
            }
        }
        
        
        for (Map.Entry<Integer, Character> entry : band.entrySet()) {
            if (entry.getKey() == position) {
                System.out.print(zustand + " ");
            }
            System.out.print(entry.getValue() + " ");
        }
	
	if (position > ints[ints.length - 1]) {
            System.out.print(zustand + " ");
            for (int i = ints[ints.length - 1]; i < position - 1; i++) {
                System.out.print(block + " ");
            }
        }

        System.out.print(block);
        System.out.println("");
    }
}
