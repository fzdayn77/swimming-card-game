package service

import entity.*
import view.Refreshable

/**
 * This class defines the main class of the whole service layer for the Swimming game.
 * It provides access to all other service classes and holds the state of the [currentGame].
 */
class RootService {

    /**
     * holds the game service
     */
    var gameService = GameService(this)

    /**
     * holds the player action service
     */
    val playerActionService = PlayerActionService(this)

    /**
     * The current game can be `null´ if it is not started yet
     */
    var currentGame: Swimming? = null

    /**
     * Thi function adds the provided [newRefreshable] to all services connected
     * to the root service class
     */
    private fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * This function adds for each of the provided newRefreshables to all
     * services connected to the root service class
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }
}