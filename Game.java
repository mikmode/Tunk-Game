public class Game
{
    private Controller controller;
    private View view;
    private Model model;
    //constructor/game start method, seems straightforward
    public Game()
    {
        this.model = new Model();
        this.view = new View();
        this.controller = new Controller(this.model, this.view);
    }
    public void startGame()
    {
        this.controller.runGame();
    }
}