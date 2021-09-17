package org.bubblechat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Loads a reddit thread and proceeds to load all comments one by one and save them to Json.
 * This is single threaded and to prevent the reddit web server from giving us a 404 there is a 2000ms delay per comment.
 * Therefore the run time of this program is determined by the number of comments in the thread.
 * The output is the GameThread.json file.
 *
 * @version 0.0.1
 * @author Tyler Johnson
 *
 */
public class RedditRipper {
	private Document doc; 
	private String fetchstring;
	private String base = "https://www.reddit.com/r/nba/comments/npei0z/game_thread_philadelphia_76ers_4923_washington/.json";
	private String commenturl = "https://www.reddit.com/r/nba/comments/npei0z/game_thread_philadelphia_76ers_4923_washington/" + fetchstring + "/.json";
	private List<String> users = new ArrayList<String>();
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson = gsonBuilder.setPrettyPrinting().create();
    private boolean more = false;
    public List<String> morecomments;
    private GameThread gt;
    
	public RedditRipper() throws InterruptedException {
		gt = new GameThread();
		try {
			fetch(base);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s : users) {
			System.out.println(s);
		}
	}
	
	public void fetch(String url) throws InterruptedException, IOException {
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Response[] rep = gson.fromJson(doc.text(), Response[].class);
		Response comments = rep[1];	
		comments.data.children.forEach(n -> {
			System.out.println(n.data.created_utc + " " + n.data.body);
			register(new GameComment(n.data.created_utc, n.data.body));
		});
		//fetch more comments if full
		comments.data.children.forEach(n -> {
				if(n.kind.equalsIgnoreCase("more")) {
					System.out.println("found more");
					more = true;
					morecomments = n.data.children;
					System.out.println(n.data.children.size());
				}
			}
		);
		if(more) {
			for(String s : morecomments) {
				Thread.sleep(2000);
				fetchstring = s;
				updateURL();
				//System.out.println("String s is " + s);
				//System.out.println("Comment URL is " + commenturl);
				Document docu = null;
				try {
					docu = Jsoup.connect(commenturl).ignoreContentType(true).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				rep = gson.fromJson(docu.text(), Response[].class);
				Response commentt = rep[1];	
				commentt.data.children.forEach(n -> {
					System.out.println(n.data.created_utc + " " + n.data.body);
					register(new GameComment(n.data.created_utc, n.data.body));
				});
			}
		}
        try (FileWriter writer = new FileWriter("GameThread.json")) {
            gson.toJson(gt, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void register(GameComment gameComment) {
		gt.comments.add(gameComment);
	}

	void updateURL() {
		commenturl = "https://www.reddit.com/r/nba/comments/npei0z/game_thread_philadelphia_76ers_4923_washington/" + fetchstring + "/.json";
	}

	public static void main(String[] args) {
		try {
			new RedditRipper();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public class GameThread {
		public GameThread() {
			comments = new ArrayList<GameComment>();
		}
		public List<GameComment> comments;
	}
	
	public class GameComment{
		public GameComment(long created, String text) {
			this.created_utc = created;
			this.text = text;
		}
	    public long created_utc;
	    public String text;
	}

	public class Response {
		public String kind;
		public ResponseData data;
	}
	
	public static class ResponseData {
		public String modhash;
		public List<ResponseChildData> children;
	}

	public static class ResponseChildData {
		public String kind;
		public ChildData data;
	}

	public static class ChildData {
	    public String body;
	    public long created_utc;
	    public List<String> children;
	}

}
