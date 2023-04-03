package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

/**
 * This is a class of the pause-menu-scene that extends the [MenuScene]
 */
class PauseMenuScene : MenuScene(1920, 1040), Refreshable {

    /**
     * represents the menu label
     */
    private val menu = Label(
        height = 200, width = 200,
        posX = 860, posY = 35,
        text = "Menu",
        font = Font(size = 40, fontWeight = Font.FontWeight.BOLD, color = Color.WHITE)
    )

    /**
     * [Button] that resumes the game with no changes
     */
    val resumeButton = Button(
        height = 85, width = 200,
        posX = 860, posY = 230,
        text = "Resume",
        font = Font(size = 30, color = Color.WHITE),
        visual = ColorVisual(110, 55, 164)
    )

    /**
     * [Button] that restarts a new game with the same players and
     * a new stack of cards
     */
    val restartButton = Button(
        height = 85, width = 200,
        posX = 860, posY = 360,
        text = "Restart",
        font = Font(size = 30, color = Color.WHITE),
        visual = ColorVisual(110, 55, 164)
    )

    /**
     * [Button] that quits the game and returns to the [StartMenuScene]
     */
    val quitButton: Button = Button(
        height = 85, width = 200,
        posX = 860, posY = 490,
        text = "Quit",
        font = Font(size = 30, color = Color.WHITE),
        visual = ColorVisual(110, 55, 164)
    )


    /**
     * Initialization of the PauseMenuScene
     */
    init {
        opacity = 1.0
        background = ImageVisual("GameBackground.jpg")
        addComponents(menu, restartButton, resumeButton, quitButton)
    }

}