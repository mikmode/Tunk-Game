import java.util.ArrayList;

public class Model
{
    private int turn;

    private final int numPlayers = 2;

    private final int handSize = 7;
    private ArrayList<Player> players;

    private ArrayList<ArrayList<Card>> matchSets;


    private Deck d;

    // constructor/getter methods
    public Model()
    {
        this.turn = 0;
        this.d = new Deck();
        this.players = new ArrayList<Player>();
        this.matchSets = new ArrayList<>();
    }

    public ArrayList<ArrayList<Card>> getMatchSets()
    {
        return this.matchSets;
    }

    public int getTurn()
    {
        return turn;
    }

    public Deck getDeck()
    {
        return this.d;
    }

    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public void setNextTurn()
    {
        turn++;
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

    public int getHandSize()
    {
        return handSize;
    }
}