package contrapunctus;

import static contrapunctus.Notes.extractNoteBase;
import static contrapunctus.Notes.noteDistance;
import static contrapunctus.Notes.scaleNote;
import static contrapunctus.Probability.DOWN;
import static contrapunctus.Probability.cumSum;
import static contrapunctus.Probability.normalizeWeights;

import java.util.*;

// Methods that generate melodies of a random length.
public class Melody {

	// Generate a random melody without rhythm, starting and ending at C.
	static ArrayList<String> getMelody(){
		String n = "C5";
		ArrayList<String> ns = new ArrayList<String>();
		
		Random rand = new Random();
		
		int notesMade = 0;
		
		// Very rudimentary probabilistic chain approach
		while(true){
			
			// Weights array. Indices 1-8 are weights for notes upward and
			// 9-16 are weights for notes downward. Index 0 is same note.
			double[] weights = new double[17];
			weights[0] = 10;
			weights[1] = 30;
			weights[1+DOWN] = 30;
			weights[2] = 10;
			weights[2+DOWN] = 10;
			weights[3] = 8;
			weights[3+DOWN] = 8;
			weights[4] = 8;
			weights[4+DOWN] = 8;
			weights[7] = 4;
			weights[7+DOWN] = 4;
			
			// Avoid tritone with F-B
			if(extractNoteBase(n).equals("F")) weights[3] = 0;
			if(extractNoteBase(n).equals("B")) weights[3+DOWN] = 0;
			
			// Minor six possible when ascending
			if(extractNoteBase(n).equals("E") ||
				extractNoteBase(n).equals("A") ||
				extractNoteBase(n).equals("B")) weights[5] = 6;
			
			int distanceC = noteDistance("C5", n);
			double hookelaw = Math.pow(1.17604743, Math.abs(distanceC));
			
			if(distanceC > 0)
			for(int k=1; k<=8; k++){
				weights[k] /= (k*hookelaw / 2);
			}
			
			if(distanceC < 0)
			for(int k=1+DOWN; k<=8+DOWN; k++){
				weights[k] /= (k*hookelaw / 2);
			}
			
			normalizeWeights(weights);
			
			// Cumulative weights, for RNG
			double[] cum = cumSum(weights);
			
			String nn = n; // next note
			
			// Generate the next note based on the above probabilities
			boolean found_flag = false;
			double r = rand.nextDouble();
			if(r < cum[0]) {nn = n; found_flag = true;}
			for(int k=1; k<=8; k++){
				if(r < cum[k]){
					nn = scaleNote(n,k);
					found_flag = true;
					break;
				}
			}
			if(!found_flag)
			for(int k=1; k<=8; k++){
				if(r < cum[k+DOWN]){
					nn = scaleNote(n,-k);
					found_flag = true;
					break;
				}
			}
			
			n = nn;
			ns.add(nn);
			
			notesMade ++;
			
			// Resolve to a leading tone if we have enough notes and
			// we have a B or a D.
			if(notesMade > 15 && (n.equals("B4") || n.equals("D5"))) break;
		}
		ns.add("C5");
		return ns;
	}
	
}