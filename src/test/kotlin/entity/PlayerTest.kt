package entity

import kotlin.test.*


class PlayerTest {

    /**
     * two empty lists of cards for each player
     */
    private val h1 = mutableListOf<Card>()
    private val h2 = mutableListOf<Card>()

    /**
     * two different players with a name, a score and a list of cards
     */
    private val p1 = Player("hichem", 20.0, h1)
    private val p2 = Player("papi", 10.5, h2)

    /**
     * checks if toString produces for every player the correct form
     */
    @Test
    fun testToString() {
        assertEquals("hichem: (Score) : 20.0 ", p1.toString())
        assertEquals("papi: (Score) : 10.5 ", p2.toString())
    }

    /**
     * checks if the getters functions work fine
     */
    @Test
    fun testGetter() {
        assertEquals("hichem", p1.playerName)
        assertEquals("papi", p2.playerName)
        assertEquals(20.0, p1.playerScore)
        assertEquals(10.5, p2.playerScore)
        assertEquals(h1, p1.playerHandDeck)
        assertEquals(h2, p2.playerHandDeck)
    }

}


