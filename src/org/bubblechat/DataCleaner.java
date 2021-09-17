package org.bubblechat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bubblechat.model.GameThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Loads GameThread.json, cleans and formats text by removing punctuation and outputs GameThreadCleaned.json
 *
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class DataCleaner {

	    static GsonBuilder gsonBuilder = new GsonBuilder();
	    static Gson gson = gsonBuilder.setPrettyPrinting().create();
	    
		public static void main(String[] args) {
			GameThread gt = GameThreadLoader.loadGameThread("GameThread.json");
			gt.comments.forEach(n -> {
				n.text = n.text.replaceAll("[^a-zA-Z0-9\\s{2,}]", "");
				n.text = n.text.replaceAll("\\s{2,}", " ").trim();
				n.text = n.text.replace(",", "");
				//n.text = n.text.replaceAll(";$", "");
				n.text = n.text.toLowerCase();
				//System.out.println(n.created + " " + n.text);
			});
			
	        try (FileWriter writer = new FileWriter("GameThreadCleaned.json")) {
	            gson.toJson(gt, writer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       
		}
}