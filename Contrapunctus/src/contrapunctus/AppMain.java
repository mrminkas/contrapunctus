package contrapunctus;
import org.jfugue.*;
//This plays stuff with rhythm.
import java.util.*; //this imports java
import static contrapunctus.Melody.*;

public class AppMain { // this is a public class
	
	public static void main(String[] args) throws InterruptedException{
		
		while(true){
			Random rand = new Random();
			int ins = rand.nextInt(80); // this makes a random instrument sound to use while playingt the music that was previously created by this program that creates music for the instrument to play
			ins = 0;
			String[] ns = matchRhythm(getMelody(rand).toArray(new String[0]),rand);
			StringBuffer sb = new StringBuffer("Tempo[150] I[" + ins + "] "); // this sets a tempo
			for(String note : ns) sb.append(note + " ");
			
			System.out.println(sb.toString()); // this is a string of notes
			Player player = new Player(); // this plays music.
			player.play(sb.toString());
			Thread.sleep(1000); //this calls it to sleep after a while.
		}
	}
}
