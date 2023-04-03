package entity

import java.util.function.UnaryOperator

/**
 * This class define a stack of only three [Card]s
 */
class TripleDeck : UnaryOperator<Card> {

    /**
     * this is always a stack of 3 cards from [Card]
     */
    var cards = mutableListOf<Card>()

    /**
     * A function that describes every card in the deck with
     * its Suit from [CardSuit] and Value from [CardValue] in the pile
     */
    override fun toString(): String = cards.toString()
    override fun apply(t: Card): Card {
        TODO("Not yet implemented")
    }

}