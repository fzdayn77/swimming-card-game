package entity

/**
 * This class holds the game´s data
 */
class Swimming(
    var players: List<Player>, var middleDeck: MutableList<Card>, var drawPile: CardPile,
) {

    /**
     * getter functions
     */
    var passCounter: Int = 0

    var moves: Int = -1

    var activePlayerID: Int = 0

    var isKnocked: Boolean = false

    /**
     * defines the discard pile which is empty at the beginning but
     * the recieves the discarded cards
     */
    var discardPile = CardPile()

    /**
     * A function that calculates the index of the active player
     */
    fun changeActivePlayer() {
        if (this.activePlayerID == players.size - 1) {
            activePlayerID = 0
        } else {
            activePlayerID++
        }
    }

}