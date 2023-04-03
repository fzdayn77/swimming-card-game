package view

/**
 * This Interface provides a mechanism for the service layer to communicate
 * with the view layer, which update the UI according to the taken actions.
 */
interface Refreshable {

    /**
     * perform refreshes after a new gam started
     */
    fun refreshOnStartGame() {}

    /**
     * perform refreshes after the game is stopped
     */
    fun refreshOnStopGame() {}

    /**
     * refreshes the UI after all the cards are changed
     */
    fun refreshOnChangeAllCards() {}

    /**
     * refreshes the UI after one card is changed
     *
     * @param playerCardIndex indicates the player´s card that will be changed
     * @param middleCardIndex defines the middle-deck´s card that will be replaced
     */
    fun refreshOnChangeOneCard(playerCardIndex: Int, middleCardIndex: Int) {}

    /**
     * perform refreshes after one of the players has knocked
     */
    fun refreshOnKnock() {}

    /**
     * perform refreshes after one of the players has passed his turn
     */
    fun refreshOnPass() {}

    /**
     * makes the necessary refreshes after the call of the endTurn() function
     *
     * @param hasGameEnded defines if the game has ended (true) or not (false)
     */
    fun refreshOnEndTurn(hasGameEnded: Boolean) {}

}