package contrapunctus;
import org.jfugue.*;
//This plays stuff with rhythm.
import java.util.*; //this imports java
import static contrapunctus.Melody.*;

public class AppMain { // this is a public class
	
	public static void main(String[] args) throws InterruptedException{
		
		// The random used throughout the program
		Random rand = new Random(1412);
		
		Object[] ns = matchRhythm(getMelody(rand).toArray(new String[0]),rand);
		String[] cp = matchCounterpoint((String[])ns[0], (Integer)ns[1], rand);
		StringBuffer sb_cp = new StringBuffer("V1 ");
		
		StringBuffer sb_ml = new StringBuffer("Tempo[400] V0 "); // this sets a tempo
		for(String note : (String[])ns[0]) sb_ml.append(note + " ");
		for(String note : cp) sb_cp.append(note + " ");
		sb_ml.append(sb_cp);
		
		System.out.println(sb_ml.toString()); // this is a string of notes
		Player player = new Player(); // this plays music.
		player.play(sb_ml.toString());
		//Thread.sleep(1000); //this calls it to sleep after a while.
		
	}
}
