package org.bubblechat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A game thread object.
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class GameThread {
	
	public List<GameComment> comments;
	
	public GameThread() {
		comments = new ArrayList<GameComment>();
	}
}
