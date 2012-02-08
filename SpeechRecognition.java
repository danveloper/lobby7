import java.net.URI;
import java.net.URLEncoder;
import java.util.Locale;

import javax.speech.Central;
import javax.speech.EngineModeDesc;
import javax.speech.recognition.DictationGrammar;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.Result;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;

public class SpeechRecognition extends ResultAdapter {
	static Recognizer rec;
	
	public void resultAccepted(ResultEvent e) {
		Result r = (Result)(e.getSource());
		ResultToken tokens[] = r.getBestTokens();

		String command = "";
		for (int i = 0; i < tokens.length; i++)
			command = command + tokens[i].getSpokenText() + " ";
		
		try {
			command.replace("'", "");
			URI baseUrl = new URI("http://localhost:8080/s?action=add&text="+URLEncoder.encode(command, "ISO-8859-1"));
			baseUrl.toURL().openStream();
			System.out.println("added: " + command);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
				
	}
	
	public static void main(String args[]) {
		try {
			// Create a recognizer that supports English.
			rec = Central.createRecognizer(
							new EngineModeDesc(Locale.ENGLISH));

			// Start up the recognizer
			rec.allocate();
	 
			// Load the grammar from a file, and enable it
			//FileReader reader = new FileReader(args[0]);
			//RuleGrammar gram = rec.loadJSGF(reader);
			//gram.setEnabled(true);
	
			DictationGrammar dict = rec.getDictationGrammar("dictation");
			dict.setEnabled(true);
			
			
			// Add the listener to get results
			rec.addResultListener(new SpeechRecognition());
	
			// Commit the grammar
			rec.commitChanges();
	
			// Request focus and start listening
			rec.requestFocus();
			rec.resume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
