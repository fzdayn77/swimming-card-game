package entity

import kotlin.test.*

class SwimmingTest {

    private val playersList2 = mutableListOf<Player>()

    private val middleDeck2 = TripleDeck()

    private val drawPile2 = CardPile()

    /**
     *  Defines an instance of the class [Swimming]
     */
    private val swim = Swimming(  playersList2,middleDeck2.cards, drawPile2)

    /**
     * checks if the getter functions are working correctly
     */
    @Test
    fun testGetters() {
        assertEquals(0, swim.passCounter)
        assertEquals(-1, swim.moves)
        assertEquals(0, swim.activePlayerID)
    }

}