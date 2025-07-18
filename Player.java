import java.util.ArrayList;
public class Player
{
    private int totalPoints;
    private int handPoints;
    private ArrayList<Card> ignoreList;
    private ArrayList<Card> hand;
    private boolean playing;
    private boolean isHuman;
    private String name;
    private boolean hasQuit;
    //player constructor
    public Player()
    {
        this.totalPoints = 0;
        this.handPoints = 0;
        this.hand = new ArrayList<>();
        this.ignoreList = new ArrayList<Card>();
        this.playing = true;
        this.hasQuit = false;
        this.isHuman = false;
        this.name = "";
    }
    //getter/setter methods
    public ArrayList<Card> getHand()
    {
        return this.hand;
    }
    public void setPoints(int points)
    {
        this.totalPoints = this.totalPoints + points;
        this.handPoints = points;
    }
    public int getTotalPoints()
    {
        return this.totalPoints;
    }

    public int getHandPoints(){
        return this.handPoints;
    }
    public ArrayList<Card> getIgnoreList()
    {
        return ignoreList;
    }
    public void setHand(ArrayList<Card> hand)
    {
        this.hand = hand;
    }
    public void setPlaying(boolean playing)
    {
        this.playing = playing;
    }
    public boolean getPlaying()
    {
        return this.playing;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setHuman(boolean human)
    {
        isHuman = human;
    }
    public boolean getIsHuman()
    {
        return isHuman;
    }
    public String getName()
    {
        return name;
    }
    public void setHasQuit(boolean b)
    {
        hasQuit = b;
    }
    public boolean getHasQuit()
    {
        return hasQuit;
    }
}