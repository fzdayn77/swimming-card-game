package entity

/**
 * This class defines a one card with its suit and value.
 */
data class Card(val value: CardValue, val suit: CardSuit) {

    /**
     * a suit getter
     */
    val getSuit: CardSuit
        get() = this.suit

    /**
     * a value getter
     */
    val getValue: CardValue
        get() = this.value

    /**
     * This function describes every existing card with
     * its Suit from [CardSuit] and Value from [CardValue]
     */
    override fun toString() = "$suit-$value"

    /**
     * This function compares two cards using the ordinal-Ordering of
     * their values in the class [CardValue]
     */
    operator fun compareTo(other: Card) = this.value.ordinal - other.value.ordinal
}