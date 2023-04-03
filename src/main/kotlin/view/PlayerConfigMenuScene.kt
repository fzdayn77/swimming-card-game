package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * This class represents the Config-Menu-Scene that allows the input
 * of the players´names and helps to start the game using the
 * Start-Game [Button]
 */
class PlayerConfigMenuScene(private val rootService: RootService) : MenuScene(1920, 1040), Refreshable {

    //-----------------------First Player-----------------------//

    /**
     * the first player´s [Label]
     */
    private val labelPlayer1 = Label(
        width = 300, height = 50,
        posX = 250, posY = 250,
        text = "Player 1:",
        font = Font(size = 58, color = Color.WHITE)
    )

    /**
     * the first player´s name text-field
     */
    val field1 = TextField(
        posX = 530, posY = 260,
        width = 300, height = 60,
        font = Font(size = 28, Color.BLACK)
    )

    //-----------------------Second Player-----------------------//

    /**
     * the second player´s [Label]
     */
    private val labelPlayer2 = Label(
        width = 300, height = 50,
        posX = 950, posY = 250,
        text = "Player 2:",
        font = Font(size = 58, color = Color.WHITE)
    )

    /**
     * the second player´s name text-field
     */
    val field2 = TextField(
        posX = 1230, posY = 260,
        width = 300, height = 60,
        font = Font(size = 28, Color.BLACK)
    )

    //-----------------------Third Player-----------------------//

    /**
     * the third player [Label]
     */
    private val labelPlayer3 = Label(
        width = 300, height = 50,
        posX = 250, posY = 500,
        text = "Player 3:",
        font = Font(size = 58, color = Color.WHITE)
    ).apply {
        isVisible = (rootService.gameService.numberOfPlayers == 3 || rootService.gameService.numberOfPlayers == 4)
    }

    /**
     * the third player´s name text-field
     */
    val field3 = TextField(
        posX = 530, posY = 510,
        width = 300, height = 60,
        font = Font(size = 28, Color.BLACK)
    ).apply {
        isVisible = (rootService.gameService.numberOfPlayers == 3 || rootService.gameService.numberOfPlayers == 4)
    }

    //-----------------------Fourth Player-----------------------//

    /**
     * the fourth player´s [Label]
     */
    private val labelPlayer4 = Label(
        width = 300, height = 50,
        posX = 950, posY = 500,
        text = "Player 4:",
        font = Font(size = 58, color = Color.WHITE)
    ).apply { isVisible = (rootService.gameService.numberOfPlayers == 4) }

    /**
     * the fourth player´s name text-field
     */
    val field4 = TextField(
        posX = 1230, posY = 510,
        width = 300, height = 60,
        font = Font(size = 28, Color.BLACK)
    ).apply { isVisible = (rootService.gameService.numberOfPlayers == 4) }

    /**
     * [Button] that represents the start-game-button.
     *
     * Starts the game on click and takes us to the [SwimmingGameScene]
     */
    val startGameButton = Button(
        width = 300, height = 70,
        posX = 810, posY = 800,
        text = "Start Game",
        font = Font(size = 30, color = Color.WHITE)
    ).apply {
        isVisible = true
        visual = ColorVisual(92, 46, 138)
    }

    /**
     * [Button] that represents the back-button.
     *
     * returns on click to the [StartMenuScene]
     */
    val backButton = Button(
        width = 200, height = 60,
        posX = 860, posY = 900,
        text = "Back",
        font = Font(size = 30, color = Color.WHITE)
    ).apply {
        isVisible = true
        visual = ColorVisual(255, 0, 63)
    }

    /**
     * Initialization of the PlayerConfigMenuScene
     */
    init {
        opacity = 1.0
        background = ImageVisual("GameBackground.jpg")
        addComponents(
            labelPlayer1, field1,
            labelPlayer2, field2,
            labelPlayer3, field3,
            labelPlayer4, field4,
            startGameButton, backButton
        )
    }

}