package service

import kotlin.test.*

/**
 * This class tests all the methods of the [PlayerActionService] class
 */
class PlayerActionServiceTest {

    /**
     * defines the root
     */
    private var root = RootService()

    /**
     * defines the names of players
     */
    private var names = mutableListOf<String>()

    /**
     * checks if the pass counter has been incremented by 1
     */
    @Test
    fun testPass() {

        // a list of names
        names.add("hani")
        names.add("daniel")
        names.add("susan")

        // starts the game
        root.gameService.startGame(names)

        // the beginning of the game
        assertEquals(0, root.currentGame!!.passCounter)

        // after one pass
        root.playerActionService.pass()
        assertEquals(1, root.currentGame!!.passCounter)

        // after two passes
        root.playerActionService.pass()
        assertEquals(2, root.currentGame!!.passCounter)

        // after three passes
        root.playerActionService.pass()
        assertEquals(0, root.currentGame!!.passCounter)

        // after four passes -> must return to zero
        root.playerActionService.pass()
        assertEquals(1, root.currentGame!!.passCounter)
    }


    /**
     * checks if the cards are correctly switched with the same positions
     */
    @Test
    fun testChangeOneCard() {

        names.add("henry")
        names.add("daniel")
        names.add("susan")

        // start the game
        root.gameService.startGame(names)
        println(root.currentGame!!.middleDeck.size)

        // save existing cards to compare them after switching places/hands
        val secondCardMiddle = this.root.currentGame!!.middleDeck[1]
        val thirdCardFirstPlayer = this.root.currentGame!!.players[0].playerHandDeck[2]

        // switch one card
        root.playerActionService.changeOneCard(2, 1)

        // compare the cards
        assertEquals(secondCardMiddle, this.root.currentGame!!.players[0].playerHandDeck[2])
        assertEquals(thirdCardFirstPlayer, this.root.currentGame!!.middleDeck[1])

    }

    /**
     * checks if the whole middle deck is replaced with the player´s hand deck
     */
    @Test
    fun testChangeAllCards() {

        names.add("henry")
        names.add("daniel")

        // start the game
        root.gameService.startGame(names)

        // save the existing cards to compare them after switching places/hands
        val middle = this.root.currentGame!!.middleDeck
        val hand = this.root.currentGame!!.players[0].playerHandDeck

        // switch all the cards
        this.root.playerActionService.changeAllCards()

        // testing the pass counter
        assertEquals(0, root.currentGame!!.passCounter)

        // compare the two decks
        assertEquals(middle, root.currentGame!!.players[0].playerHandDeck)
        //assertEquals(hand, root.currentGame!!.middleDeck)
    }

    /**
     * checks the functionality of the knock function
     */
    @Test
    fun testKnock() {

        names.add("cardi b")
        names.add("offset")
        names.add("snoop dogg")

        // start the game
        root.gameService.startGame(names)

        // knock
        root.playerActionService.knock()

        // comparing
        //assertEquals(true, root.currentGame!!.isKnocked)
        assertEquals(2, root.currentGame!!.moves)
        assertEquals(0, root.currentGame!!.passCounter)
    }

}