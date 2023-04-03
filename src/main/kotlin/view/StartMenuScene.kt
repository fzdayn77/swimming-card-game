package view

import service.*
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color.WHITE

/**
 * This class defines the start game menu scene which extends the [BoardGameScene] class
 */
class StartMenuScene(private val rootService: RootService) : MenuScene(1000, 1080), Refreshable {


    /**
     * This is a [Button] that allows us to start the game and shows
     * the [PlayerConfigMenuScene]
     */
    val playButton = Button(
        width = 300, height = 90,
        posX = 350, posY = 300,
        text = "Play",
        font = Font(size = 36, color = WHITE)
    ).apply {
        isVisible = true
        visual = ColorVisual(92, 46, 138)
    }


    /**
     * This [Button] defines the exit-button that quits the game
     */
    val exitButton = Button(
        width = 300, height = 90,
        posX = 350, posY = 550,
        text = "Exit",
        font = Font(size = 36, color = WHITE)
    ).apply {
        isVisible = true
        visual = ColorVisual(92, 46, 138)
    }


    /**
     * [Button] that stores the number of players as 2
     */
    private val twoPlayers = Button(
        width = 99, height = 50,
        posX = 350, posY = 395,
        text = "2",
        font = Font(color = WHITE, size = 28)
    ).apply {
        isVisible = true
        visual = ColorVisual(145, 91, 200)
        onMouseClicked = {
            visual = ColorVisual(194, 163, 224)

            // reset colors of other buttons
            threePlayers.visual = ColorVisual(145, 91, 200)
            fourPlayers.visual = ColorVisual(145, 91, 200)

            rootService.gameService.numberOfPlayers = 2
        }
    }


    /**
     * [Button] that stores the number of players as 3
     */
    private val threePlayers = Button(
        width = 99, height = 50,
        posX = 451, posY = 395,
        text = "3",
        font = Font(color = WHITE, size = 28)
    ).apply {
        isVisible = true
        visual = ColorVisual(145, 91, 200)
        onMouseClicked = {
            visual = ColorVisual(194, 163, 224)

            //resets the colors of other buttons
            reset3()

            rootService.gameService.numberOfPlayers = 3
        }
    }

    /**
     * This function resets the colors of the second-player-button and fourth-player-button
     */
    private fun reset3() {
        twoPlayers.visual = ColorVisual(145, 91, 200)
        fourPlayers.visual = ColorVisual(145, 91, 200)
    }

    /**
     * [Button] that stores the number of players as 4
     */
    private val fourPlayers = Button(
        width = 99, height = 50,
        posX = 553, posY = 395,
        text = "4",
        font = Font(color = WHITE, size = 28)
    ).apply {
        isVisible = true
        visual = ColorVisual(145, 91, 200)
        onMouseClicked = {
            visual = ColorVisual(194, 163, 224)

            //resets the colors of other buttons
            reset4()

            rootService.gameService.numberOfPlayers = 4
        }
    }

    /**
     * This function resets the colors of the second-player-button and third-player-button
     */
    private fun reset4() {
        twoPlayers.visual = ColorVisual(145, 91, 200)
        threePlayers.visual = ColorVisual(145, 91, 200)
    }

    /**
     * Initialization of the StartMenuScene
     */
    init {
        opacity = 1.0
        background = ImageVisual("background.jpg")
        addComponents(
            playButton, exitButton,
            twoPlayers, threePlayers, fourPlayers
        )
    }

}