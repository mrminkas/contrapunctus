package contrapunctus;
import org.jfugue.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static contrapunctus.Notes.*;
import static contrapunctus.Probability.*;

public class AppMain {
	
	public static void main(String[] args){
		
		//playTommysPentatonicRandomness();
		//playPentatonicRandomWalk();
		
		String n = "C5";
		StringBuffer sb = new StringBuffer("Tempo[160] " + n + " ");
		
		Random rand = new Random();
		
		// Very rudimentary probabilistic chain approach
		for(int i=1; i<=11; i++){
			
			// Weights array. Indices 1-8 are weights for notes upward and
			// 9-16 are weights for notes downward. Index 0 is same note.
			double[] weights = new double[17];
			weights[1] = 30;
			weights[1+DOWN] = 30;
			weights[2] = 10;
			weights[2+DOWN] = 10;
			weights[3] = 6;
			weights[3+DOWN] = 6;
			weights[4] = 5;
			weights[4+DOWN] = 5;
			weights[7] = 4;
			weights[7+DOWN] = 4;
			
			// Avoid tritone with F-B
			if(extractNoteBase(n).equals("F")) weights[3] = 0;
			if(extractNoteBase(n).equals("B")) weights[3+DOWN] = 0;
			
			// Minor six possible when ascending
			if(extractNoteBase(n).equals("E") ||
				extractNoteBase(n).equals("A") ||
				extractNoteBase(n).equals("B")) weights[5] = 6;
			
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
			sb.append(nn + " ");
		}
		sb.append("C5ww");
		
		System.out.println(sb.toString());
		Player player = new Player();
		player.play(sb.toString());
	}
	
	static void playPentatonicRandomWalk(){
		String[] notes = "E4 G4 A4 C D E G A C6 D6 E6".split(" ");
		//String[] notes = "G4 A4 B4 C D E F G A B C6 D6 E6".split(" ");
		
		Random rand = new Random();
		
		StringBuilder sb = new StringBuilder("Tempo[300] V0 ");
		int position = 3;
		for(int i=0; i<=130; i++){
			
			boolean r = rand.nextBoolean();
			if(r) position ++; else position --;
			
			if(position < 0) position += 2;
			if(position > 5) position -= 2;
			
			if(i==0) position = 3;
			if(i == 130 && position != 3) i -= 13;
			
			String nlen = "i";
			if(i % 13 == 0){
				nlen = "h";
			}
			if(i==130) nlen = "ww";
			
			sb.append(notes[position] + nlen + " ");
		}
		
		Player player = new Player();
		player.play(sb.toString());
	}
	
	static void playTommysPentatonicRandomness(){

		// Notes of the pentatonic scale starting from C
		String[] notes = "C D E G A C6".split(" ");
		String[] notes3u = "E F# G# B C#6 E6".split(" ");
		String[] notes3d = "Ab4 Bb4 C Eb F Ab".split(" ");
		String[] notes5u = "G A B D6 E6 G6".split(" ");
		String[] notes5d = "F4 G4 A4 C D F".split(" ");
		Random rand = new Random();
		
		StringBuilder sb = new StringBuilder("Tempo[250] V0 ");
		// Generate 130 random notes
		for(int i=0; i<=130; i++){
			
			// Which note are we using?
			int r1 = rand.nextInt(6);
			
			// What's the second note?
			int r2 = rand.nextInt(4);
			
			String nlen = "i";
			if(i % 13 == 0){
				nlen = "h";
			}
			
			String[] sndnote = null;
			if(r2 == 0) sndnote = notes3u;
			if(r2 == 1) sndnote = notes3d;
			if(r2 == 2) sndnote = notes5u;
			if(r2 == 3) sndnote = notes5d;
			
			sb.append(notes[r1] + nlen + "+" + sndnote[r1] + nlen + " ");
		}
		
		Player player = new Player();
		try {
			player.saveMidi(sb.toString(),new File("C:/Users/Bai/Desktop/a.mid"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
