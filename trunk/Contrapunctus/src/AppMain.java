import org.jfugue.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AppMain {
	public static void main(String[] args){
		playTommysPentatonicRandomness();
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
