package turingmaschine;

import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dirk Rusche <dirk@rusche.me>
 */
public class TuringMaschine {
    private Set<Character> alphabet;
    private Set<String> zustaende;
    private Set<Character> bandalphabet;
    private Set<Transition> uebergaenge;
    private String startzustand;
    private char block;
    private Set<String> endzustaende;
    
    public TuringMaschine(Set<Character> alphabet, Set<String> states, Set<Character> bandalphabet, Set<Transition> transition, String startstate, char block, Set<String> finalstate) throws Exception {
        this.alphabet = alphabet;
        this.zustaende = states;
        this.bandalphabet = bandalphabet;
        this.uebergaenge = transition;
        this.startzustand = startstate;
        this.block = block;
        this.endzustaende = finalstate;
        
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
    
    
    private void step(Map<Integer, Character> band, String zustand, int position) {
        this.print(band, zustand, position);
        String zustand_neu = null;
        int position_neu = position;
        
        System.out.println("zustand " + zustand + ", position " + position);
                
        
        for (Transition uebergang : uebergaenge) {
            if (!uebergang.state.equals(zustand)) {
                continue;
            }
            
            if (!band.containsKey(position)) {
                if (uebergang.isBlock == false) {
                    continue;
                }
            }
            else if (uebergang.letter != band.get(position)) {
                continue;
            }
            
            
            System.out.println(uebergang);
            
            if (uebergang.newletter_block == true) {
                band.remove(position);
            }
            else {
                band.put(position, uebergang.newletter);
            }
            zustand_neu = uebergang.targetstate;
            
            if (uebergang.direction == Direction.l) {
                position_neu = position - 1;
            }
            else if (uebergang.direction == Direction.r) {
                position_neu = position + 1;
            }
            
            break;
        }
        
        if (zustand_neu == null) {
            System.out.println("Kein Zustandsuebergang gefunden");
            
            if (endzustaende.contains(zustand)) {
                System.out.println("Zustand ist Endzustand");
            }
            
            print(band, zustand, position);
            System.out.println(band.toString());
            
            return;
        }

        
        step(band, zustand_neu, position_neu);
    }
    
    private void print(Map<Integer, Character> band, String zustand, int position) {
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

        System.out.print(block);
        System.out.println("");
    }
    
    public static void main(String[] args) throws Exception {
        char block = '\u25A0';
        
        Set<Transition> transitions = new HashSet<>();
        
        transitions.add(new Transition("q0", '0', "q0", '0', Direction.r));
        transitions.add(new Transition("q0", '1', "q0", '1', Direction.r));
        transitions.add(new Transition("q0", "q1", Direction.l));
        
        transitions.add(new Transition("q1", '0', "q1", '0', Direction.l));
        transitions.add(new Transition("q1", '1', "q2", '0', Direction.l));
        
        transitions.add(new Transition("q2", '0', "q1", '1', Direction.l));
        transitions.add(new Transition("q2", '1', "q2", '1', Direction.l));
        transitions.add(new Transition("q2", "q3", '1', Direction.s));
        
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
        bandalphabet.add(block);
        
        Set<String> finalstate = new HashSet<>();
        finalstate.add("q3");
        
        String startstate = "q0";
        
        
        TuringMaschine tm = new TuringMaschine(alphabet, states, bandalphabet, transitions, startstate, block, finalstate);
        tm.run("10001");
    }
     
}
