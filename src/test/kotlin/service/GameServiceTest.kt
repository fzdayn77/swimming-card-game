package service

import entity.*
import kotlin.test.*

/**
 * This class tests all the functions in the [GameService] class
 */
class GameServiceTest {

    /**
     * defines a pile of cards from the class [CardPile]
     */
    private val cardStack = CardPile()

    /**
     *  defines a list of players´names
     */
    private var names2 : MutableList<String> = ArrayList()

    /**
     * defines the root
     */
    private val root = RootService()


    /**
     * checks if the function accepts only 2-4 players
     */
    @Test
    fun testCreatePlayers() {
        println(names2.size)
        GameService(root).createPlayers(names2, cardStack)

        names2.add("mikasa")
        println(names2.size)
        GameService(root).createPlayers(names2, cardStack)

        names2.add("levi")
        names2.add("hulk")
        names2.add("spiderman")
        println(names2.size)

        names2.add("boubou")
        println(names2.size)
        GameService(root).createPlayers(names2, cardStack)
    }

    /**
     * checks if the startGame method works
     */
    @Test
    fun testStartGame() {

        names2.add("tom")
        names2.add("mia")
        names2.add("ronaldo")
        GameService(root).startGame(names2)

        assertEquals(false, root.currentGame!!.isKnocked)
        assertEquals(0, root.currentGame!!.activePlayerID)
        assertEquals(0, root.currentGame!!.passCounter)
    }

    /**
     * checks if the function returns the correct answer using the rules of the game
     */
    @Test
    fun testCalculatePlayerScore() {
        // first hand deck
        val h1 = mutableListOf<Card>()
        h1.add(Card(CardValue.NINE, CardSuit.DIAMONDS))
        h1.add(Card(CardValue.JACK, CardSuit.DIAMONDS))
        h1.add(Card(CardValue.ACE, CardSuit.DIAMONDS))

        // second hand deck
        val h2 = mutableListOf<Card>()
        h2.add(Card(CardValue.SEVEN, CardSuit.HEARTS))
        h2.add(Card(CardValue.TEN, CardSuit.HEARTS))
        h2.add(Card(CardValue.JACK, CardSuit.HEARTS))

        // third hand deck
        val h3 = mutableListOf<Card>()
        h3.add(Card(CardValue.ACE, CardSuit.SPADES))
        h3.add(Card(CardValue.SEVEN, CardSuit.SPADES))
        h3.add(Card(CardValue.QUEEN, CardSuit.CLUBS))

        // fourth hand deck
        val h4 = mutableListOf<Card>()
        h4.add(Card(CardValue.JACK, CardSuit.HEARTS))
        h4.add(Card(CardValue.QUEEN, CardSuit.DIAMONDS))
        h4.add(Card(CardValue.KING, CardSuit.CLUBS))

        // fifth hand deck
        val h5 = mutableListOf<Card>()
        h5.add(Card(CardValue.EIGHT, CardSuit.CLUBS))
        h5.add(Card(CardValue.EIGHT, CardSuit.DIAMONDS))
        h5.add(Card(CardValue.EIGHT, CardSuit.SPADES))

        // tests
        assertEquals(30.0, GameService(root).calculatePlayerScore(h1))
        assertEquals(27.0, GameService(root).calculatePlayerScore(h2))
        assertEquals(18.0, GameService(root).calculatePlayerScore(h3))
        assertEquals(10.0, GameService(root).calculatePlayerScore(h4))
        assertEquals(30.5, GameService(root).calculatePlayerScore(h5))

    }

    /**
     * checks if the stopGame function works
     */
    @Test
    fun testStopGame() {
        // start the game
        names2.add("tom")
        names2.add("mia")
        names2.add("ronaldo")
        GameService(root).startGame(names2)

        if(root.currentGame != null) { GameService(root).stopGame() }
        assertEquals(null, root.currentGame)
    }

}