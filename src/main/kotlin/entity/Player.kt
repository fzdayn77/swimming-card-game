package entity

/**
 * A class that defines every existing player with a name, a score
 * and a hand deck
 *
 * @property name the player´s name
 * @property score the score of the player
 * @property initialHandDeck a list of cards from [Card] which defines the
 *           hand deck of the player
 */

class Player(
    val name: String,
    private val score: Double,
    private val initialHandDeck: MutableList<Card>,
) {

    /**
     * getter and setter functions for the name of the player
     */
    var playerName: String = ""
        get() = this.name.lowercase()
        set(value) {
            field = if (value.length in 1..30) value else throw IllegalArgumentException()
        }

    /**
     * getter function for the score of the player
     */
    var playerScore: Double = 0.0
        get() = this.score

    /**
     * getter function for the hand deck
     */
    var playerHandDeck = mutableListOf<Card>()
        get() = this.initialHandDeck

    /**
     * A function that describes every player with its name and score
     */
    override fun toString(): String =
        "$name: (Score) : $score "


}