import java.util.ArrayList;

public class View
{
    public void cardStringPrompt(Card c, int i)
    {
        System.out.println(i + ": " + c.getRank() + " " + "of" + " " + c.getSuit());
    }

    public void playerStartPrompt(Player p)
    {
        System.out.println("It is: " + p.getName() + "'s turn.");
    }

    public void matchSetsStringPrompt(int k, ArrayList<Card> matchSets)
    {
        for(int i = 0; i < matchSets.size(); i++)
        {
            Card c = matchSets.get(i);
            System.out.println("   " + k + ": " + c.getRank() + " " + "of" + " " + c.getSuit());
        }
    }
    /*
    purpose: displays the cards in a player's hand
    input: player object
    output: display messages
    assumptions: messages correspond to the card they're depicting
     */
    public void printHandCardsPrompt(Player p)
    {
        for(int i = 0; i < p.getHand().size(); i++)
        {
            Card c  = p.getHand().get(i);
            this.cardStringPrompt(c, i);
        }
    }
    public void printMatchSetcardsPrompt(Model m)
    {
        for(int i = 0; i < m.getMatchSets().size(); i++)
        {
            System.out.println(i + ":");
            this.matchSetsStringPrompt(i, m.getMatchSets().get(i));
        }
    }
    public void callTunkPrompt()
    {
        System.out.println("TUNK!");
    }

    public void startGamePrompt()
    {
        System.out.println("Let the games begin!");
    }

    public void displayCardsPrompt()
    {
        System.out.println("Which cards would you like to check?  Input your choices as a group of 3 or 4 integers, each separated with a space, on one line.  Type \"done\" if you are done :)");
    }

    public void startTunkTurnPrompt()
    {
        System.out.println("Tunk turn started");
    }

    public void discardCardPrompt(Card c)
    {
        System.out.println("Discarded: " + c.getRank() + " " + "of" + " " + c.getSuit());
    }

    public void continuePlayingPrompt()
    {
        System.out.println("Would you like to keep playing this game?  Please pick either \"y\" or \"n\"");
    }

    public void pickDiscardCardPrompt()
    {
        System.out.println("Select a card to discard:");
    }

    public void drawCardOrDrawDiscardPrompt()
    {
        System.out.println("Would you like to \"draw\" from the stockpile or \"pick\" from the discard pile? Type draw or pick.");
    }

    public void printDiscardPile(Model m)
    {
        int index = m.getDeck().getDiscardPile().size();
        Card c = m.getDeck().getDiscardPile().get(index-1);
        System.out.println(c.getRank() + " " + "of" + " " + c.getSuit() + "   <-- Current Top");

    }
    ///VVVVVVVVVVVVVVVV Maybe? something like this if you want the whole pile?
    /*
    public void printDiscardPile(Model m)
    {
        String retString = "";
        for(int i = 0; i < m.getDeck().getDiscardPile().size(); i++)
        {
            retString = retString + m.getDeck().getDiscardPile().get(i).;
            if(i != m.getDeck().getDiscardPile().size()){
                retString = retString + " | ";
            }
        }
        retString = retString + "<--";
        System.out.println(retString);
    }
*/
    public void soundsGoodGameOverPrompt()
    {
        System.out.println("Sounds good to me, pal.");
    }

    public void invalidInputPrompt()
    {
        System.out.println("Invalid Input");
    }

    public void wrongInputPrompt()
    {
        System.out.println("All right, buddy, you got two options.  So, what's it gonna be?  Huh?  Huh?  y?  or n?");
    }

    public void cardGroupingWorkedPrompt()
    {
        System.out.println("Card grouping worked");
    }

    public void okPrompt()
    {
        System.out.println("ok.");
    }

    public void startTurnPrompt(int turn)
    {
        System.out.println("start of turn" + " " + turn);
    }

    public void handPointTotalPrompt(Player p)
    {
        System.out.println("Your current hand's total is:" + p.getHandPoints());
        System.out.println("Your current game total is:" + p.getTotalPoints());
    }

    public void ignoreCardPrompt(ArrayList<Card> cards)
    {
        for(Card c: cards)
        {
            System.out.println(c.getRank() + " of " + c.getSuit() + " have been ignored!");
        }
    }

    public void newNumDonePrompt()
    {
        System.out.println("Call \"tunk\" or \"done\"?");
    }

    public void matchNumDonePrompt()
    {
        System.out.println("\"match\" or \"done\"?");
    }

    public void gameOverPrompt()
    {
        System.out.println("Game Over!");
    }

    public void gameExitPrompt()
    {
        System.out.println("GAME EXIT");
    }

    public void winningPlayerPrompt(Player p)
    {
        System.out.println(p.getName() + " Has won with " + p.getTotalPoints() + " points!");
    }

    public void manyWinnersPrompt(ArrayList<Player> winningPlayers)
    {
        String retString = "";
        for(int i = 0; i < winningPlayers.size(); i++)
        {
            if(i < winningPlayers.size()-1)
            {
                retString = retString + winningPlayers.get(i).getName() + ", ";
            }
            else
            {
                retString = retString + winningPlayers.get(i).getName();
            }

            System.out.println(retString + " Have won with " + winningPlayers.get(0).getTotalPoints() + " points!");

        }
    }
}