package service

import entity.*

/**
 * This class defines all the actions/moves that each player
 * can do (knock, pass, change one card or change all the cards)
 */
class PlayerActionService(private val rootService: RootService) : AbstractRefreshableService() {

    /**
     * increments the pass counter by 1 and executes the endTurn function
     */
    fun pass() {
        rootService.currentGame!!.passCounter++
        endTurn()
    }

    /**
     * This function checks if the active player has knocked or not, change
     * the state of the remaining moves and change the pass counter, which is 0.
     *
     */
    fun knock() {
        if (!rootService.currentGame!!.isKnocked) {
            rootService.currentGame!!.isKnocked = true
            rootService.currentGame!!.moves = rootService.currentGame!!.players.size
        }

        onAllRefreshable { refreshOnKnock() }
        endTurn()
    }

    /**
     * A function that swaps only ONE card from the middle deck
     * and the hand of the active player
     *
     * @param playerCardIndex defines the index of the card that will be changed
     * @param middleCardIndex defines the index of the card on the table
     */
    fun changeOneCard(playerCardIndex: Int, middleCardIndex: Int) {

        // reset the pass counter
        rootService.currentGame!!.passCounter = 0

        // save the player`s card
        val savePlayerCard: Card =
            this.rootService.currentGame!!.players[rootService.currentGame!!.activePlayerID].playerHandDeck[playerCardIndex]

        // save the middle deck´s card
        val saveMiddleCard: Card = this.rootService.currentGame!!.middleDeck[middleCardIndex]

        // change the player´s card with the card in the middle
        this.rootService.currentGame!!.players[rootService.currentGame!!.activePlayerID].playerHandDeck.removeAt(
            playerCardIndex
        )
        this.rootService.currentGame!!.players[rootService.currentGame!!.activePlayerID].playerHandDeck.add(
            playerCardIndex,
            saveMiddleCard
        )

        // change the middle card with the player´s hand-card (the new variable)
        this.rootService.currentGame!!.middleDeck.removeAt(middleCardIndex)
        this.rootService.currentGame!!.middleDeck.add(middleCardIndex, savePlayerCard)

        onAllRefreshable { refreshOnChangeOneCard(playerCardIndex, middleCardIndex) }
        endTurn()
    }

    /**
     * A function that swaps all three cards in the middle with all three cards from the player´s hand
     */
    fun changeAllCards() {

        // saves the active player index
        val activePlayerIndex = rootService.currentGame!!.activePlayerID

        // saves the hand deck with its three cards in a new variable
        val saveHandDeck = mutableListOf<Card>()
        saveHandDeck.addAll(rootService.currentGame!!.players[activePlayerIndex].playerHandDeck)

        // saves the middle deck in a new variable
        val saveMiddleDeck = mutableListOf<Card>()
        saveMiddleDeck.addAll(rootService.currentGame!!.middleDeck)

        // changes the middle deck with the player´s hand deck
        rootService.currentGame!!.middleDeck = saveHandDeck

        // changes the player´s hand deck with the new variable (which is the middle deck)
        for (i in 0..2) {
            rootService.currentGame!!.players[activePlayerIndex].playerHandDeck.removeAt(i)
            rootService.currentGame!!.players[activePlayerIndex].playerHandDeck.add(i, saveMiddleDeck[i])
        }

        // resets the pass counter
        rootService.currentGame!!.passCounter = 0

        onAllRefreshable { refreshOnChangeAllCards() }
        endTurn()

    }

    /**
     * A function that controls the logic of the entire game and the players´actions
     */
    private fun endTurn() {

        if (rootService.currentGame!!.isKnocked) {
            rootService.currentGame!!.moves--
            onAllRefreshable { refreshOnKnock() }
        }

        if (rootService.currentGame!!.moves <= 0) {
            onAllRefreshable { refreshOnEndTurn(true) }
            return // ends the game
        }


        if (rootService.currentGame!!.passCounter == rootService.currentGame!!.players.size) {

            if (rootService.currentGame!!.drawPile.cardsOnPile.size >= 3) {

                // discard the middle deck
                rootService.currentGame!!.discardPile.cardsOnPile.add(rootService.currentGame!!.middleDeck[0])
                rootService.currentGame!!.discardPile.cardsOnPile.add(rootService.currentGame!!.middleDeck[1])
                rootService.currentGame!!.discardPile.cardsOnPile.add(rootService.currentGame!!.middleDeck[2])

                // fill the middle deck with three new cards
                this.rootService.currentGame!!.middleDeck = rootService.currentGame!!.drawPile.drawThree()

                // reset the pass counter
                this.rootService.currentGame!!.passCounter = 0

                onAllRefreshable { refreshOnPass() }

            } else {
                onAllRefreshable { refreshOnEndTurn(true) }
                return // ends the game
            }
        }

        rootService.currentGame!!.changeActivePlayer()
        onAllRefreshable { refreshOnEndTurn(false) } // game continues

    }
}