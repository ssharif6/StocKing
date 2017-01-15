/**
 * Created by shaheensharifian on 1/14/17.
 */
public class LinkModel {
    public String getSubreddit() {
        return subreddit;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getUps() {
        return ups;
    }

    public int getDowns() {
        return downs;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    private String subreddit;
    private String id;
    private int score;
    private int ups;
    private int downs;
    private String permalink_url;

    public LinkModel(String subreddit, String id, int score, int ups, int downs, String url) {
        this.subreddit = subreddit;
        this.id = id;
        this.score = score;
        this.ups = ups;
        this.downs = downs;
        this.permalink_url = url;
    }
}
