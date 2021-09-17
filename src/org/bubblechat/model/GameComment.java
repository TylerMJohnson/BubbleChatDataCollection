package org.bubblechat.model;

/**
 * A game comment object.
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class GameComment{
    public long created;
    
    public String text;
    
	public GameComment(long created, String text) {
		this.created = created;
		this.text = text;
	}
}