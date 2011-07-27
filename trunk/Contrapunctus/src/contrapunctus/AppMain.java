package contrapunctus;
import org.jfugue.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static contrapunctus.Notes.*;
import static contrapunctus.Probability.*;
import static contrapunctus.Melody.*;

public class AppMain {
	
	public static void main(String[] args){
		
		Random rand = new Random();
		String[] ns = matchRhythm(getMelody(rand).toArray(new String[0]),rand);
		StringBuffer sb = new StringBuffer("Tempo[200] ");
		for(String note : ns) sb.append(note + " ");
		
		System.out.println(sb.toString());
		Player player = new Player();
		player.play(sb.toString());
	}
}
