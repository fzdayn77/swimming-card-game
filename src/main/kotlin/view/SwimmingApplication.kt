package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * This is a class of the Swimming Application that extends the [BoardGameApplication]
 * and allows accessing other MenuScenes
 */
class SwimmingApplication : BoardGameApplication("Swimming_fadi"), Refreshable {

    /**
     * This defines the root service that provides access to all
     * other classes and holds the current game
     */
    private val rootService = RootService()

    //--------------------SCENES------------------------//

    /**
     * This represents the [StartMenuScene] which is the first scene
     * of the game
     */
    private val startMenuScene: StartMenuScene = StartMenuScene(rootService).apply {
        playButton.onMouseClicked = {
            this@SwimmingApplication.hideMenuScene()
            this@SwimmingApplication.showMenuScene(playerConfigMenuScene)
        }

        exitButton.onMouseClicked = {
            exit() // this exits the current game
        }
    }

    /**
     * This represents the [PlayerConfigMenuScene]
     */
    private val playerConfigMenuScene: PlayerConfigMenuScene = PlayerConfigMenuScene(rootService).apply {
        startGameButton.onMouseClicked = {
            val game = rootService.gameService  // this holds the game service

            // starts the game with just two players
            if (field3.text.isBlank()) {
                game.startGame(
                    listOf(field1.text.trim(), field2.text.trim()).toMutableList()
                )
            }

            // starts the game with just three players
            if (field3.text.isNotBlank() && field4.text.isBlank()) {
                game.startGame(
                    listOf(field1.text.trim(), field2.text.trim(), field3.text.trim()).toMutableList()
                )
            }

            // starts the game with four players as expected
            if (field3.text.isNotBlank() && field4.text.isNotBlank()) {
                game.startGame(
                    listOf(
                        field1.text.trim(), field2.text.trim(), field3.text.trim(),
                        field4.text.trim()
                    ).toMutableList()
                )
            }

            this@SwimmingApplication.hideMenuScene()
            this@SwimmingApplication.showGameScene(swimmingGameScene)
        }

        backButton.onMouseClicked = {
            this@SwimmingApplication.showMenuScene(startMenuScene)
        }

    }

    /**
     * This represents the [SwimmingGameScene]
     */
    private val swimmingGameScene: SwimmingGameScene = SwimmingGameScene(rootService).apply {
        pauseButton.onMouseClicked = {
            this@SwimmingApplication.hideMenuScene()
            this@SwimmingApplication.showMenuScene(pauseMenuScene)
        }
    }

    /**
     * This represents the [PauseMenuScene] that pauses, restarts or quits the game
     */
    private val pauseMenuScene: PauseMenuScene = PauseMenuScene().apply {
        quitButton.onMouseClicked = {
            this@SwimmingApplication.hideMenuScene()
            this@SwimmingApplication.showMenuScene(startMenuScene)
        }

        resumeButton.onMouseClicked = {
            this@SwimmingApplication.hideMenuScene()
        }

        restartButton.onMouseClicked = {
            val newGame = rootService.currentGame // a variable that holds the new game
            val newList = mutableListOf<String>() // a new list of players´names
            checkNotNull(newGame) { "ERROR: No game is running !!" }

            newGame.players.forEach {
                newList.add(it.name)
            }

            rootService.gameService.startGame(newList) // start a new game with the same players

            this@SwimmingApplication.hideMenuScene()
        }
    }

    /**
     * This represents the [ScoreBoardMenuScene] that indicates the end of
     * the game and shows the scores of the players
     */
    private val scoreBoardMenuScene: ScoreBoardMenuScene = ScoreBoardMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            this@SwimmingApplication.hideMenuScene()
            this@SwimmingApplication.showMenuScene(startMenuScene)
        }
        restartButton.onMouseClicked = {
            val newGame = rootService.currentGame // a variable that holds the new game
            val newList = mutableListOf<String>() // a new list of players´names
            checkNotNull(newGame)

            newGame.players.forEach {
                newList.add(it.name)
            }

            rootService.gameService.startGame(newList) // starts a new game with the same players
            this@SwimmingApplication.hideMenuScene()
        }
    }

    /**
     * This function shows the start menu scene
     */
    override fun refreshOnStartGame() {
        this@SwimmingApplication.showGameScene(swimmingGameScene)
    }

    /**
     * This functions shows the score menu scene after the game is finished
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        if (hasGameEnded) {
            this@SwimmingApplication.showMenuScene(scoreBoardMenuScene)
        }
    }

    /**
     * This function shows the moves remaining counter when it is called
     */
    override fun refreshOnKnock() {
        swimmingGameScene.knockButton.apply { isDisabled = true }
        swimmingGameScene.movesRemainingCounter.text = "Moves Remaining: ${rootService.currentGame!!.moves}"
        swimmingGameScene.movesRemainingCounter.apply {
            if (!isVisible) {
                isVisible = true
            }
        }
    }

    /**
     * Initialization of the SwimmingApplication
     */
    init {
        rootService.addRefreshables(
            startMenuScene, this, playerConfigMenuScene, swimmingGameScene,
            pauseMenuScene, scoreBoardMenuScene
        )
        this.showMenuScene(startMenuScene)
    }

}