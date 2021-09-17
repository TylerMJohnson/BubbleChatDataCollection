package org.bubblechat;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.bubblechat.model.GameThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Used to load a Json file into the GameThread Object
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class GameThreadLoader {

    static GsonBuilder gsonBuilder = new GsonBuilder();
    static Gson gson = gsonBuilder.setPrettyPrinting().create();
    
    public static GameThread loadGameThread(String name){
    	GameThread gamethread = null;
        try (Reader reader = new FileReader(name)) {
            gamethread = gson.fromJson(reader, GameThread.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamethread;
    }
	
}