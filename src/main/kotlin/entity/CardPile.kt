package entity

import kotlin.random.Random

/**
 * This class defines the card pile which is a list of maximum 32 cards
 */
class CardPile(private val random: Random = Random) {

    /**
     * cardsOnPile: initializes our card pile
     */
    var cardsOnPile: MutableList<Card> = ArrayList()

    /**
     * This function shuffles the cards randomly using the function [shuffle]
     *
     * @return a shuffled card pile
     */
    fun shuffleCards() = cardsOnPile.shuffle(random)

    /**
     * A function that draws the first on Top amount-cards from the stack
     * using the [removeFirst] function
     *
     * @param amount the number of cards that we want to draw
     * @return a list of card(s) with size = amount
     *
     */
    private fun draw(amount: Int): MutableList<Card> {
        require(amount in 0 until cardsOnPile.size) { "you can't draw $amount cards from $cardsOnPile" }
        return List(amount) { cardsOnPile.removeFirst() }.toMutableList()
    }

    /**
     * A function that draws the first 3 on top cards from the stack using the [draw] function
     */
    fun drawThree(): MutableList<Card> {
        check(cardsOnPile.size >= 3)
        return draw(3)
    }

    /**
     * A function that describes every card in the pile with
     * its Suit from [CardSuit] and Value from [CardValue] in the pile
     */
    override fun toString(): String = cardsOnPile.toString()

    /**
     * the card pile size must be min. 0 and max. 32
     */
    init {
        require(cardsOnPile.size in 0..32)
    }

}