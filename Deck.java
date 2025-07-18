import java.util.ArrayList;
public class Deck
{
    private final String[] suitArray =
            {
                    "Clubs",
                    "Diamonds",
                    "Hearts",
                    "Spades"
            };
    private final String[] rankArray =
            {
                    "Ace",
                    "Two",
                    "Three",
                    "Four",
                    "Five",
                    "Six",
                    "Seven",
                    "Eight",
                    "Nine",
                    "Ten",
                    "Jack",
                    "Queen",
                    "King"};
    private ArrayList<Card> deck;
    private ArrayList<Card> discardPile;
    //getters/constructors
    public Deck()
    {
        this.deck = new ArrayList<Card>();
        this.discardPile = new ArrayList<Card>();
        for (String s : suitArray)
        {
            for (int j = 0; j < rankArray.length; j++)
            {
                Card c = new Card(s, rankArray[j], j+6);
                this.deck.add(c);
            }
        }
    }

    public ArrayList<Card> getDiscardPile()
    {
        return discardPile;
    }

    public ArrayList<Card> getDeckArray()
    {
        return deck;
    }
    /*
    purpose: creates discard pile
    input: n/a
    output: n/a
    assumptions: a deck exists
     */
    public void initalizeDiscardPile()
    {
        this.discardPile.add(this.deck.remove(0));
    }
}