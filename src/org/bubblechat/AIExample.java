package org.bubblechat;

import java.util.List;
import java.util.Properties;

import org.bubblechat.model.GameThread;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * This is an example of how the StanfordCoreNLP library can be used to take sentences and determine sentiment or what
 * a sentence is about. 
 * 
 * In this example use only the first 100 comments because of memory and cpu limitations. This example takes a little bit of time
 * to compute.
 * 
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class AIExample {
	
    static String doctext = "";
    
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment");
        
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(properties);
        
        GameThread gt = GameThreadLoader.loadGameThread("GameThreadCleaned.json");
		gt.comments.stream().limit(100).forEach(n -> {
			n.text = n.text + ". "; //make sure they're sentences
			//System.out.println(n.text);
			doctext += n.text;
		});

        CoreDocument cd = new CoreDocument(doctext);
        stanfordCoreNLP.annotate(cd);
        
        List<CoreSentence> sentences = cd.sentences();
        for(CoreSentence sentence : sentences) {
            System.out.println(sentence.sentiment() + "        " + sentence);
           // System.out.println(sentence.lemmas() + "        " + sentence);
        }
    }


}
