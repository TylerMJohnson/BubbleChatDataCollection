package org.bubblechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bubblechat.model.Edge;
import org.bubblechat.model.GameThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Converts data to a format that can easily be inserted into the JavaScript Front-End (bubblechat.html)
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class GraphGenerator {

	public static List<String> commons = new ArrayList<String>(Arrays.asList(" ", "the","of","and","a","to","in","is",
			"you","that","it","he","was","for","on","are","as","with","his","they","i",
			"at","be","this","have","from","or","one","had","by","word","but","not","what",
			"all","were","we","when","your","can","said","there","use","an","each","which",
			"she","do","how","their","if","will","up","other","about","out","many","then",
			"them","these","so","some","her","would","make","like","him","into","time","has",
			"look","two","more","write","go","see","number","no","way","could","people","my",
			"than","first","water","been","call","who","oil","its","now","find","long","down",
			"day","did","get","come","made","may","part", "just", "why", "thats", "gonna", "im",
			"should", "hes", "me", "know", "only", "got", "doing", "us", "gets", "want", "ive", "any", "let"));
		
	    static GsonBuilder gsonBuilder = new GsonBuilder();
	    static Gson gson = gsonBuilder.setPrettyPrinting().create();
	    
		public static void main(String[] args) {
			GameThread gt = GameThreadLoader.loadGameThread("GameThreadCleaned.json");
			/**
			gt.comments.forEach(n -> {
				n.text = n.text.replaceAll("[^a-zA-Z0-9\\s{2,}]", "");
				n.text = n.text.replaceAll("\\s{2,}", " ").trim();
				n.text = n.text.replace(",", "");
				//n.text = n.text.replaceAll(";$", "");
				n.text = n.text.toLowerCase();
				//System.out.println(n.created + " " + n.text);
			});
			/**
	        try (FileWriter writer = new FileWriter("GameThreadCleaned.json")) {
	            gson.toJson(gt, writer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        **/
			
			Map<String, Integer> counts = new HashMap<>();
			
			gt.comments.forEach(n -> {
				String splits[] = n.text.split(" ");
				for(int i = 0; i < splits.length; i++) {
					String s = splits[i];
						if(!commons.contains(s)) {
						if (!counts.containsKey(s)) {
						    counts.put(s, 1);
						} else {
						    counts.put(s, counts.get(s) + 1);
						}
					}
				}
			});
			
			//counts.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new
	       // )).forEach((s, integer) -> System.out.println(String.format("{%s, %s, %s},", '"' + "id" + '"' + ": " + '"' + s + '"', '"' + "group" + '"' + ": "+ 1, '"' + "size" + '"'  + ": " + integer)));
			
			/*
			Map<Edge, Integer> edges = new HashMap<>();
			gt.comments.forEach(n -> {
				String splits[] = n.text.split(" ");
				for(int i = 1; i < splits.length; i++) {
					String from = splits[i - 1];
					String to = splits[i];
					
					Edge temp = new Edge(from, to);
					//if(!commons.contains(s)) {
						if (!edges.containsKey(temp)) {
						    edges.put(temp, 1);
						} else {
						    edges.put(temp, edges.get(temp) + 1);
						}
					//}
				}
			});
			*/
			
			gt.comments.forEach(n -> {
				String splits[] = n.text.split(" ");
				for(int i = 0; i < splits.length; i++) {
					splits[i] = splits[i].trim();
				}
				//System.out.println(Arrays.toString(splits));
				for(int i = 0; i < splits.length; i++) {
					for(String common : commons) {
						if(splits[i].equalsIgnoreCase(common)) {
							splits[i] = "";
						}
					}
				}
				for(int i = 0; i < splits.length; i++) {
					splits[i] = splits[i].trim();
				}
				//System.out.println(Arrays.toString(splits));
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < splits.length; i++) {
					if(!splits[i].equals("")) {
						sb.append(splits[i] + " ");
					}
				}
				String temp = sb.toString();
				//System.out.println(temp);
				n.text = temp;
			});
			
			Map<Edge, Integer> edges = new HashMap<>();
			gt.comments.forEach(n -> {
				String splits[] = n.text.split(" ");
				for(int i = 1; i < splits.length; i++) {
					String from = splits[i - 1];
					String to = splits[i];

					Edge temp = new Edge(from, to);
					if (!edges.containsKey(temp)) {
						edges.put(temp, 1);
					} else {
						edges.put(temp, edges.get(temp) + 1);
					}
				}
			});
			
			
			for (Map.Entry<Edge, Integer> entry : edges.entrySet()) {
				System.out.println(String.format("{%s, %s, %s},", '"' + "source" + '"' + ": " + '"' + entry.getKey().from + '"', '"' + "target" + '"' + ": " + '"' + entry.getKey().to + '"', '"' + "value" + '"'  + ": " + entry.getValue()));
			}
			
			//edges.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(200).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new
			//        )).forEach((s, integer) -> System.out.println(String.format("{%s, %s, %s},", '"' + "source" + '"' + ": " + '"' + s + '"', '"' + "target" + '"' + ": "+ s, '"' + "value" + '"'  + ": " + integer)));
		}

}