package contrapunctus;
import org.jfugue.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static contrapunctus.Notes.*;

public class AppMain {
	public static void main(String[] args){
		//playTommysPentatonicRandomness();
		//playPentatonicRandomWalk();
		
		String n = "C3";
		StringBuffer sb = new StringBuffer("Tempo[200] ");
		for(int i=0; i<200; i++){
			sb.append(n + " ");
			n = scaleNote(n, 2);
		}
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
