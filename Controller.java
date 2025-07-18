import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
public class Controller
{
    private Model model;
    private View view;
    private boolean exit;

    //Custom comparator added to utilize built-in sort functionality for checkStrait method
    private CardComparator cardComparator;
    //Controller constructor.  Should comparator be moved to Model?
    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
        this.cardComparator = new CardComparator();
        this.exit = false;
    }
    /*
    purpose: initializes a game of Tunk
    input:n/a
    output:n/a (but perhaps this might change after the beginning of iteration 5)
    assumptions: knowing the rules of Tunk would definitely help
     */
    public void runGame()
    {
        view.startGamePrompt();
        this.instanceGame();

        while (this.gameActive() && !this.exit)
        {
            model.setNextTurn();
            view.startTurnPrompt(model.getTurn());
            for (Player p : model.getPlayers())
            {
                if (p.getPlaying() && !this.exit)
                {
                    if(p.getIsHuman()) {
                        playerTurn(p);
                    } else {
                        computerTurn(p);
                    }
                }
            }
        }
        this.findWinner();
    }

    private void findWinner()
    {
        if(!this.exit)
        {
            int numToBeat = model.getPlayers().get(0).getTotalPoints();
            ArrayList<Player> winningPlayers = new ArrayList<>();
            for (int i = 0; i < model.getPlayers().size(); i++)
            {
                Player p = model.getPlayers().get(i);
                if (p.getTotalPoints() == numToBeat && p.getHasQuit())
                {
                    winningPlayers.add(p);
                } else if (p.getTotalPoints() < numToBeat && p.getHasQuit())
                {
                    winningPlayers.clear();
                    winningPlayers.add(p);
                    numToBeat = p.getTotalPoints();
                }
            }
            if (winningPlayers.size() > 1)
            {
                view.manyWinnersPrompt(winningPlayers);
            } else
            {
                view.winningPlayerPrompt(winningPlayers.get(0));
            }
        }
        else
        {
            view.gameExitPrompt();
        }
        //<<---------------------------------------------------------------------------------- INSERT SQL HERE
    }
    /*
    purpose: keeps track of AI score
    input: card from top of deck to hand
    output: card from hand to discard pile
    assumptions: computer will always draw from top of deck and discard card 0
     */
    private void computerTurn(Player p)
    {
        view.playerStartPrompt(p);
        view.printHandCardsPrompt(p);
        view.printDiscardPile(model);
        p.getHand().add(drawCard());
        //Computer draws from deck
        view.printHandCardsPrompt(p);
        //Computer will keep playing
        this.handValue(p);
        view.handPointTotalPrompt(p);
        if (this.playerCanContinue(p))
        {
            Card c = p.getHand().remove(0);
            model.getDeck().getDiscardPile().add(c);
            view.discardCardPrompt(c);
        }
        else
        {
            p.setPlaying(false);
            view.gameOverPrompt();
        }
    }
    private void playerTurn(Player p)
    {
        view.playerStartPrompt(p);
        view.printHandCardsPrompt(p);
        if(!this.exit)
        {
            this.gamePlayerDraw(p);
        }
        if(!this.exit)
        {
            view.printHandCardsPrompt(p);
        }
        boolean calledTunk = false;
        if(!this.exit)
        {
            calledTunk = this.gameSelectTunkTerminalPrompt(p);
        }
        if(!this.exit)
        {
            this.handValue(p);
        }
        if(!this.exit)
        {
            view.handPointTotalPrompt(p);
        }
        if (this.playerCanContinue(p) && !this.exit)
        {
            if(!calledTunk)
            {
                this.playerDiscard(p);
            }
            if (!this.continueGameTerminalPrompt() && !this.exit)
            {
                view.gameOverPrompt();
                p.setHasQuit(true);
                p.setPlaying(false);
            }
        }
        else
        {
            view.gameOverPrompt();
            p.setPlaying(false);
        }
    }
    private void playerDiscard(Player p)
    {
        Scanner s = new Scanner(System.in);
        boolean booly = true;
        while(booly)
        {
            view.printHandCardsPrompt(p);
            view.pickDiscardCardPrompt();
            int index = s.nextInt();
            if(index >= 0 && index < p.getHand().size())
            {
                Card c = p.getHand().remove(index);
                model.getDeck().getDiscardPile().add(c);
                booly = false;
                view.discardCardPrompt(c);
            } else
            {
                view.invalidInputPrompt();
            }
        }
    }
    private void initPlayers()
    {
        // <<<<<< THIS METHOD CAN EASILY BE BROUGHT OVER TO TERMINAL.... It is A ton of work. This is just a proof of concept
    }
    private void gamePlayerDraw(Player p)
    {
        if(model.getDeck().getDiscardPile().size() < 1)
        {
            Card tempAdd = model.getDeck().getDeckArray().remove(0);
            model.getDeck().getDiscardPile().add(tempAdd);
        }
        view.printDiscardPile(model);
        view.drawCardOrDrawDiscardPrompt();

        boolean booly = true;
        Scanner s = new Scanner(System.in);
        while (booly)
        {
            String stringy = s.nextLine();
            switch(stringy)
            {
                case "draw":
                    p.getHand().add(drawCard());
                    booly = false;
                    break;
                case "pick":
                    int index = model.getDeck().getDiscardPile().size();
                    Card c = model.getDeck().getDiscardPile().remove(index - 1);
                    p.getHand().add(c);
                    booly = false;
                    break;
                case "exit": this.exit = true; booly = false; break;
                default: view.invalidInputPrompt();
            }
        }
    }
    /*
    purpose: calculate the value of points in a player's hand
    input: Player object
    output: integer representative of the addend to augend gameValue
    assumptions:n/a
     */
    private void handValue(Player p)
    {
        int pointTotal = 0;
        for (int i = 0; i < p.getHand().size(); i++)
        {
            if (!p.getIgnoreList().contains(p.getHand().get(i)))
            {
                pointTotal = pointTotal + p.getHand().get(i).getPointValue();
            }
        }
        view.ignoreCardPrompt(p.getIgnoreList());
        p.setPoints(pointTotal);
        p.getIgnoreList().clear();
    }
    /*
    purpose: detects if all card objects share a suit
    input: array list of card objects
    output: bool
    assumptions: according to rules, size of list should not exceed 4, no logic currently in place to restrict list size
     */
    private boolean checkMatch(ArrayList<Card> tempList)
    {
        if (tempList.size() != 3 && tempList.size() != 4) /////!!!! FIX LOGIC L8r
        {
            return false;
        }
        else
        {
            for (int j=0; j<tempList.size(); j++)
            {
                String stringy = tempList.get(0).getRank();
                if (Objects.equals(stringy, tempList.get(j).getRank()) || stringy.equals("Two"))
                {

                } else
                    return false;
            }
            return true;
        }
    }
    /*
    purpose: sorts the card list and checks that each card element is exactly 1 rank higher than previous card element
    input: an ArrayList of type <Card>
    output: bool
    assumptions:  are all these cards the same suit?
    */
    private boolean checkStrait(ArrayList<Card> tempList)
    {
        if (tempList.size() != 3 && tempList.size() != 4) /////!!!! FIX LOGIC L8r
        {
            return false;
        }
        else
        {
            Collections.sort(tempList, cardComparator);
        }
        int k = 1;
        for (int j=0; j < tempList.size() - 1 ; j++)
        {
            int lowCard = cardComparator.rankConversion(tempList.get(j).getRank());
            int highCard = cardComparator.rankConversion(tempList.get(k).getRank());
            if (lowCard+1 == highCard || tempList.get(j).getRank().equals("Two") || tempList.get(k).equals("Two"))
            {
                k++;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    /*
    purpose: asks the player if they wish to continue playing
    input:  string "y" or string "n", as defined by user
    output:  bool
    assumptions: only 2 valid inputs
     */
    private boolean continueGameTerminalPrompt()
    {
        boolean booly = true;
        Scanner s = new Scanner(System.in);
        boolean returnBooly = false;
        view.continuePlayingPrompt();
        while (booly)
        {
            String stringy = s.nextLine();
            if (stringy.equals("y"))
            {
                returnBooly = true;
                booly = false;
            }
            else if (stringy.equals("n"))
            {
                view.soundsGoodGameOverPrompt();
                booly = false;
                returnBooly = false;
            }
            else if (stringy.equals("exit"))
            {
                this.exit = true;
                booly = false;
                returnBooly = false;
            }
            else
            {
                view.wrongInputPrompt();
            }
        }
        return returnBooly;
    }
    /*
    purpose: displays the user's cards and prompts the user to match cards in sets
    output: an ArrayList of <Card> representing player's hand.  Moves Card object from hand to ignoreList, after validation
    input:  numbers, to be scanned
    assumption: strings, not ints
      */
    public boolean gameSelectTunkTerminalPrompt(Player p)
    {
        Scanner s = new Scanner(System.in);
        boolean returnBool = false;
        boolean b = true;

        while(b)
        {
            view.newNumDonePrompt();
            String line = s.nextLine();

            switch(line)
            {
                case "tunk": selectTunkCardsTerminalPrompt(p); b = false; returnBool = true; break;
                case "done": b = false; break;
                case "exit": b = false; this.exit = true; break;
                default: view.invalidInputPrompt();

            }
        }
        return returnBool;
    }
    public boolean gameSelectTunkModeTerminalPrompt(Player p)
    {
        Scanner s = new Scanner(System.in);
        boolean returnBool = false;
        boolean b = true;

        while(b)
        {
            view.matchNumDonePrompt();
            String line = s.nextLine();

            switch(line)
            {
                case "match": selectAdditionPileTerminalPrompt(p); b = false; returnBool = true; break;
                case "done": b = false; break;
                case "exit": b = false; this.exit = true; break;
                default: view.invalidInputPrompt();

            }
        }
        return returnBool;
    }
    /*
    logic for adding a card into existing matched set while game is in "tunk mode"
     */
    private void selectAdditionPileTerminalPrompt(Player p)
    {
        Scanner s = new Scanner(System.in);
        boolean b = true;
        while(b)
        {
            view.printMatchSetcardsPrompt(model);

            ArrayList<Integer> selectedCardsIndex = new ArrayList<>();
            view.displayCardsPrompt();
            String line = s.nextLine();
            Scanner indexChoices = new Scanner(line);

            if (line.equals("done"))
            {
                view.okPrompt();
                b = false;
            }
            else if (line.equals("exit"))
            {
                b = false;
                this.exit =true;
            }
            else
            {
                while (indexChoices.hasNextInt())
                {
                    selectedCardsIndex.add(indexChoices.nextInt());
                }
                if(selectedCardsIndex.size() > 1)
                {
                    int matchSetIndex = selectedCardsIndex.get(0);
                    int addCardIndex = selectedCardsIndex.get(1);
                    Card c = p.getHand().get(addCardIndex);
                    ArrayList<Card> tempCheck = new ArrayList<>();
                    for(int i = 0; i < model.getMatchSets().get(matchSetIndex).size(); i++)
                    {
                        tempCheck.add(model.getMatchSets().get(matchSetIndex).get(i));
                    }
                    tempCheck.add(c);
                    if (checkStrait(tempCheck) || checkMatch(tempCheck))
                    {
                        model.getMatchSets().remove(matchSetIndex);
                        model.getMatchSets().add(tempCheck);
                        p.getIgnoreList().add(c);
                        p.getHand().remove(c);
                        view.cardGroupingWorkedPrompt();
                        view.printHandCardsPrompt(p);
                        b = false;
                    }
                    else
                    {
                        view.invalidInputPrompt();
                    }
                }
                else
                {
                    view.invalidInputPrompt();
                }
            }
        }
    }
    private void selectTunkCardsTerminalPrompt(Player p)
    {
        Scanner s = new Scanner(System.in);
        boolean b = true;
        while(b)
        {
            ArrayList<Integer> selectedCardsIndex = new ArrayList<>();
            view.displayCardsPrompt();
            String line = s.nextLine();
            Scanner indexChoices = new Scanner(line);
            if (line.equals("done"))
            {
                view.okPrompt();
                b = false;
            }
            else if (line.equals("exit"))
            {
                b = false;
                this.exit =true;
            }
            else
            {
                while (indexChoices.hasNextInt())
                {
                    selectedCardsIndex.add(indexChoices.nextInt());
                }
                ArrayList<Card> checkCards = new ArrayList<>();
                for (int i=0; i < selectedCardsIndex.size(); i++)
                    checkCards.add(p.getHand().get(selectedCardsIndex.get(i)));
                if (checkStrait(checkCards) || checkMatch(checkCards))
                {
                    model.getMatchSets().add(checkCards);
                    for (Card c : checkCards)
                    {
                        p.getIgnoreList().add(c);
                        p.getHand().remove(c);
                    }
                    view.cardGroupingWorkedPrompt();
                    view.printHandCardsPrompt(p);
                    this.callTunk(p);
                    b = false;
                }
                else
                {
                    view.invalidInputPrompt();
                }
            }
        }
    }
    /*
    purpose: puts the game in "tunk" mode
    input: the input "tunk"
    output: altered game state?
    assumptions: Computer player cannot initialize "tunk" mode.
     */
    public void callTunk (Player orgPlayer)
    {
        view.callTunkPrompt();
        for(int i = 0; i < model.getPlayers().size(); i++)
        {
            Player p = model.getPlayers().get(i);
            if(p.getPlaying() && p != orgPlayer)
            {
                if (p.getIsHuman())
                {
                    view.playerStartPrompt(p);
                    view.startTunkTurnPrompt();
                    view.printHandCardsPrompt(p);
                    if(!this.exit)
                    {
                        this.gamePlayerDraw(p);
                    }
                    view.printHandCardsPrompt(p);
                    if(!this.exit)
                    {
                        this.gameSelectTunkModeTerminalPrompt(p);
                    }
                    if(!this.exit)
                    {
                        this.playerDiscard(p);
                    }
                }
                else
                {
                    view.playerStartPrompt(p);
                    view.startTunkTurnPrompt();
                    p.getHand().add(drawCard());
                    Card c = p.getHand().remove(0);
                    model.getDeck().getDiscardPile().add(c);
                    view.discardCardPrompt(c);
                }
            }
        }
    }
/*
purpose: randomly shuffles an existing deck object
input: n/a
output: n/a
assumptions:  n/a
 */
    private void shuffleDeck()
    {
        Collections.shuffle(this.model.getDeck().getDeckArray());
    }
    /*
    purpose:  sets the pre-required conditions for a new game of Tunk
    input: n/a
    output: n/a
    assumptions: n/a
     */
    private void instanceGame()
    {
        for (int i = 0; i < model.getNumPlayers(); i++)
        {
            Player p = new Player();
            if(i == 0){
                p.setHuman(true);
                p.setName("P1");
            } else {
                p.setName("C" + i);
            }
            model.getPlayers().add(p);
        }
        this.initialDraw();
        model.getDeck().initalizeDiscardPile();
    }
    /*
    purpose:  deals a player 7 cards
    input: n/a
    output: n/a
    assumptions: n/a
     */
    private void initialDraw()
    {
        this.shuffleDeck();
        for (Player p : model.getPlayers())
        {
            for(int  i = 0; i < model.getHandSize(); i++)
            {
                p.getHand().add(this.drawCard());
            }
        }
    }
    /*
    purpose: Checks how many players have a point total less than 100
    input: n/a
    output: bool
    assumptions:    return true if there is more than 1 active player utilizing new 'playing' flag from each player.
     */
    private boolean gameActive()
    {
        int j = 0;
        for (int i=0;  i < model.getPlayers().size(); i++)
        {
            if (model.getPlayers().get(i).getPlaying())
            {
                j++;
            }
        }
        if (j < 2)
        {
            return false;
        }
        return true;
    }
    /*
    purpose: determines if a player has or has not lost
    input: player object
    output: bool
    assumptions: n/a
     */
    private boolean playerCanContinue(Player p)
    {
        if (p.getTotalPoints() >= 100)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    /*
    purpose: adds a Card object to the player's hand
    input: n/a
    output: Card
    assumptions: n/a
     */
    private Card drawCard ()
    {
        return this.model.getDeck().getDeckArray().remove(0);
    }
}