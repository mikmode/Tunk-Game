public class Card
{
    private int pointVal;
    private String rank;
    private String suit;
    //getters/constructor
    public Card(String suit, String rank, int pointValue)
    {
        this.suit = suit;
        this.rank = rank;
        if (pointValue < 10)
        {
            this.pointVal = pointValue + 1;
        }
        else
        {
            this.pointVal = 10;
        }
    }
    public String getRank()
    {
        return this.rank;
    }

    public String getSuit()
    {
        return this.suit;
    }

    public int getPointValue()
    {
        return this.pointVal;
    }
}