package view

import service.GameService
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * A class for the Scoreboard menu scene which extends [MenuScene]
 */
class ScoreBoardMenuScene(private val rootService: RootService) : MenuScene(1000, 1080), Refreshable {

    /**
     * [Label] that displays the title of the [ScoreBoardMenuScene]
     */
    private val scoreboardLabel = Label(
        width = 500, height = 100,
        posX = 250, posY = 60,
        text = "Scoreboard",
        font = Font(size = 70, Color.WHITE)
    )

    /**
     * [Label] shows the score of the first player
     */
    private val score1 = Label(
        width = 600, height = 100,
        posX = 200, posY = 200,
        text = "score1",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        isVisible = true
        scale = 0.8
    }

    /**
     * [Label] shows the score of the second player
     */
    private val score2 = Label(
        width = 600, height = 100,
        posX = 200, posY = 300,
        text = "score2",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        isVisible = true
        scale = 0.8
    }

    /**
     * [Label] shows the score of the third player
     */
    private val score3 = Label(
        width = 600, height = 100,
        posX = 200, posY = 400,
        text = "score3",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        isVisible = false
        scale = 0.8
    }

    /**
     * [Label] shows the score of the fourth player
     */
    private val score4 = Label(
        width = 600, height = 100,
        posX = 200, posY = 500,
        text = "score4",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        isVisible = false
        scale = 0.8
    }


    /**
     * [Button] that the game with the same players but with a new stack of cards
     */
    val restartButton = Button(
        width = 200, height = 90,
        posX = 100, posY = 890,
        text = "Restart",
        font = Font(size = 40, color = Color.WHITE),
        visual = ColorVisual(150, 111, 214)
    ).apply {
        isVisible = true
    }

    /**
     * [Button] that quits the game and takes us to the start menu scene
     */
    val quitButton = Button(
        width = 200, height = 90,
        posX = 700, posY = 890,
        text = "Quit",
        font = Font(size = 40, color = Color.WHITE),
        visual = ColorVisual(150, 111, 214)
    ).apply {
        isVisible = true
    }


    /**
     * refreshes the scene when endTurn() is called
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {

        // defines the current game
        val game = rootService.currentGame
        checkNotNull(game)

        // variables that will be used to check the number of players
        var exist3 = false
        var exist4 = false

        // in case there are 3 players
        if (game.players.size == 3) {
            exist3 = true
            score3.isVisible = true
        }

        // in case there are 4 players
        if (game.players.size == 4) {
            exist3 = true
            score3.isVisible = true

            exist4 = true
            score4.isVisible = true
        }

        // variables that store the score of each player
        val player1Score = GameService(rootService).calculatePlayerScore(game.players[0].playerHandDeck)
        val player2Score = GameService(rootService).calculatePlayerScore(game.players[1].playerHandDeck)
        val player3Score =
            if (exist3) GameService(rootService).calculatePlayerScore(game.players[2].playerHandDeck) else 0.0
        val player4Score =
            if (exist4) GameService(rootService).calculatePlayerScore(game.players[3].playerHandDeck) else 0.0


        // defines the best score
        val bestScore = when (game.players.size) {
            3 -> maxOf(player1Score, player2Score, player3Score)
            4 -> maxOf(player1Score, player2Score, player3Score, player4Score)
            else -> maxOf(player1Score, player2Score)
        }

        // makes the winner label bigger
        when (bestScore) {
            player1Score -> {
                score1.scale = 1.2
            }
            player2Score -> {
                score2.scale = 1.2
            }
            player3Score -> {
                score3.scale = 1.2
            }
            player4Score -> {
                score4.scale = 1.2
            }
        }

        if (hasGameEnded) {
            // printing the texts
            score1.text = "${game.players[0].playerName} : $player1Score points"
            score2.text = "${game.players[1].playerName} : $player2Score points"
            score3.text = if (exist3) "${game.players[2].playerName} : $player3Score points" else ""
            score4.text = if (exist4) "${game.players[3].playerName} : $player4Score points" else ""
        }

    }


    /**
     * Initialization of the ScoreBoardMenuScene
     */
    init {
        opacity = .4
        background = ColorVisual(149, 10, 255)
        addComponents(
            scoreboardLabel,
            score1, score2, score3, score4,
            restartButton, quitButton
        )
    }
}