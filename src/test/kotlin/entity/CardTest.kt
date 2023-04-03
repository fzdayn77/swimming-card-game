package entity

import kotlin.test.*

class CardTest {

    /**
     * three cards each with a value from [CardValue]
     * and a suit from [CardSuit]
     */
    private val c1 : Card = Card(CardValue.TWO, CardSuit.SPADES)
    private val c2 : Card = Card(CardValue.SEVEN, CardSuit.DIAMONDS)
    private val c3 : Card = Card(CardValue.THREE, CardSuit.CLUBS)

    /**
     * checks if toString produces for every [Card] the correct output
     */
    @Test
    fun testToString() {
        assertEquals("♠-2", c1.toString())
        assertEquals("♦-7", c2.toString())
        assertEquals("♣-3", c3.toString())
    }

    /**
     * checks if compareTo returns the correct value
     */
    @Test
    fun testCompareTo() {
        assertEquals(5, c2.compareTo(c1))
        assertEquals(4, c2.compareTo(c3))
        assertEquals(1, c3.compareTo(c1))
        assertEquals(0, c1.compareTo(c1))
    }


}