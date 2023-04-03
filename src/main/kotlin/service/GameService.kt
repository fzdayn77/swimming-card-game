package service

import entity.*

/**
 * A service layer class that provides the logic for actions
 * that are not directly related to a single player
 */
class GameService(private val rootService: RootService) : AbstractRefreshableService() {

    /**
     * defines the number of the existing players
     */
    var numberOfPlayers: Int = 4

    /**
     * A function that creates all the players for our game (minimum 2 players and maximum 4 players).
     *
     * @param playerNames a list of the players´names
     * @param allCards defines the card stack that will be created
     */
    fun createPlayers(playerNames: MutableList<String>, allCards: CardPile): MutableList<Player> {

        // defines a list of players with the same size as the list of names
        val listOfPlayers = mutableListOf<Player>()

        if (playerNames.size !in 2..4)
            println("ERROR: Number of Players must be 2, 3 or 4 !!")
        else {
            for (name in playerNames) {
                listOfPlayers.add(Player(name, 0.0, allCards.drawThree()))
            }
        }
        return listOfPlayers
    }

    /**
     * creates the card pile with 32 shuffled cards to start the game with
     */
    private fun createAllCards(): CardPile {
        val cards = CardPile()
        cards.cardsOnPile = defaultRandomCardList()
        cards.shuffleCards()
        return cards
    }

    /**
     * Starts a new game (overwriting an active game, if it exists)
     *
     * @param playerNames a list of the different players´names
     *
     */
    fun startGame(playerNames: MutableList<String>) {

        // defines the card pile
        val allCards: CardPile = createAllCards()

        // defines the middle deck
        val middleDeck2 = allCards.drawThree()

        // define the list of players
        val playersList = createPlayers(playerNames, allCards)

        // initialize the game
        rootService.currentGame = Swimming(playersList, middleDeck2, allCards)
        rootService.currentGame!!.passCounter = 0
        rootService.currentGame!!.middleDeck = middleDeck2
        rootService.currentGame!!.drawPile = allCards
        rootService.currentGame!!.discardPile = CardPile()
        rootService.currentGame!!.players = playersList
        rootService.currentGame!!.moves = playersList.size

        onAllRefreshable { refreshOnStartGame() }
    }

    /**
     * This function stops the active game and takes us back to the start
     * menu (this will start a new game)
     */
    fun stopGame() {

        if (rootService.currentGame == null)
            println("ERROR: There is no existing game to be stopped !!")
        else
            rootService.currentGame = null

        onAllRefreshable { refreshOnStopGame() }
    }

    /**
     * a function that helps to return the value of a card as a Double number
     */
    private fun cardScore(c: Card): Double {
        return when (c.getValue) {
            CardValue.SEVEN -> 7.0
            CardValue.EIGHT -> 8.0
            CardValue.NINE -> 9.0
            CardValue.TEN, CardValue.JACK, CardValue.QUEEN, CardValue.KING -> 10.0
            CardValue.ACE -> 11.0
            else -> 0.0
        }
    }

    /**
     * calculates the score of every single player
     */
    fun calculatePlayerScore(playerHandDeck: MutableList<Card>): Double {

        // c1, c2 and c3 define the three cards in the hand of the player
        val c1: Card = playerHandDeck[0]
        val c2: Card = playerHandDeck[1]
        val c3: Card = playerHandDeck[2]

        // a set of a possible three cards (king or queen or jack)
        val theSet = setOf(CardValue.KING, CardValue.QUEEN, CardValue.JACK)

        // case1: the same suit (the ACE card is equals to 11 points)
        return if (c1.getSuit == c2.getSuit && c1.getSuit == c3.getSuit) {
            cardScore(c1) + cardScore(c2) + cardScore(c3)
        }
        // case2: three cards with th same number/value
        else if (c1.getValue == c2.getValue && c1.getValue == c3.getValue) {
            30.5
        }
        // case3: return 10 points
        else if (c1.getValue in theSet && c2.getValue in theSet && c3.getValue in theSet) {
            10.0
        }
        // case4: the normal case
        else {
            cardScore(c1) + cardScore(c2) + cardScore(c3) - 10.0
        }


    }

    /**
     * Creates a shuffled 32 cards list of all four suits and cards
     * from 7 to Ace
     */
    private fun defaultRandomCardList() = MutableList(32) { index ->
        Card(
            CardValue.values()[(index % 8) + 5],
            CardSuit.values()[index / 8]
        )
    }


}