package view

import entity.*
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color.WHITE
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * This class defines the game scene that extends the [BoardGameScene]
 */
class SwimmingGameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {

    // defines the index of the active player´s clicked card
    private var clickedPlayerCardIndex: Int = -1

    // defines the index of the middle deck´s clicked card
    private var clickedMiddleCardIndex: Int = -1

    /**
     * This is a [Label] that stores and shows number of the remaining moves in each round
     */
    val movesRemainingCounter = Label(
        posX = -100, posY = 60,
        width = 600, height = 70,
        font = Font(size = 26, color = WHITE)
    ).apply {
        isVisible = false
    }

    //-----------------------------------Other Players-----------------------------------//

    /**
     * represents the cards of the second player as a [LinearLayout]
     */
    private val secondPlayerCards = LinearLayout<CardView>(
        posX = 770, posY = 60,
        width = 400, height = 100,
        spacing = 3,
        alignment = Alignment.CENTER
    ).apply {
        isVisible = true
    }

    /**
     * represents the cards of the third player as a [LinearLayout], if the player exists
     */
    private val thirdPlayerCards = LinearLayout<CardView>(
        posX = -60, posY = 500,
        width = 400, height = 100,
        spacing = 3
    ).apply {
        rotation = 90.0
        isVisible = (rootService.gameService.numberOfPlayers == 3 || rootService.gameService.numberOfPlayers == 4)
    }

    /**
     * represents the cards of the fourth player as a [LinearLayout], if the player exists
     */
    private val fourthPlayerCards = LinearLayout<CardView>(
        posX = 1620, posY = 500,
        width = 400, height = 100,
        spacing = 3
    ).apply {
        rotation = 90.0
        isVisible = (rootService.gameService.numberOfPlayers == 4)
    }

    /**
     * This is a function that shows other inactive players´hand-decks (just the back side)
     */
    private fun otherPlayerCardsInitialize(
        otherPlayerDeck: MutableList<Card>, cardImageLoader: CardImageLoader,
        otherPlayerCards: LinearLayout<CardView>,
    ) {
        otherPlayerCards.clear()

        otherPlayerDeck.forEach { card ->
            val cardView = CardView(
                height = 130, width = 90,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                showBack()
            }

            otherPlayerCards.add(cardView)
        }

    }

    //-----------------------------------Active Player-----------------------------------//

    /**
     * This [Label] shows the active player name
     */
    private val activePlayerName = Label(
        posX = 700, posY = 970,
        width = 500, height = 70,
        alignment = Alignment.CENTER,
        font = Font(size = 32, WHITE),
        text = "player name"
    )

    /**
     * A list of [String]s that stores every cardView with its name.
     *
     * This helps to determine the index of the clicked card.
     */
    private val cardViewList = mutableListOf<CardView>()

    /**
     * represents the active player´s cards as [LinearLayout]
     */
    private val activePlayerCards = LinearLayout<CardView>(
        posX = 760, posY = 700,
        width = 400, height = 200,
        spacing = 4
    ).apply {
        isVisible = true
        onMouseClicked = {

            // adds every cardView to the list and shows the front side
            forEach { cardView ->
                cardView.showFront()
                cardViewList.add(cardView)
            }

            // defines the index of the player´s clicked card
            defineIndex()
        }
    }

    /**
     * This function determines the index of the clicked card in the player´s hand deck
     */
    private fun defineIndex() {
        cardViewList.forEach { cardView ->
            cardView.apply {
                onMouseClicked = {
                    if (cardView == cardViewList[0]) {
                        clickedPlayerCardIndex = 0
                    }

                    if (cardView == cardViewList[1]) {
                        clickedPlayerCardIndex = 1
                    }

                    if (cardView == cardViewList[2]) {
                        clickedPlayerCardIndex = 2
                    }
                }
            }
        }

    }

    /**
     * A function that gives to every card in the playerHandDeck its front and back view
     * and determines the index of the clicked card
     */
    private fun activePlayerCardsInitialize(
        playerCards: MutableList<Card>, cardImageLoader: CardImageLoader,
        playerLinearLayout: LinearLayout<CardView>,
    ) {
        playerLinearLayout.clear()  // clears the hand deck of the active player

        playerCards.forEach { card ->
            val cardView = CardView(
                height = 220, width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                showFront()
            }

            playerLinearLayout.add(cardView)  // adds the current cardView to the linear layout of the player
        }
    }


    //-----------------------------------Middle Deck-----------------------------------//

    /**
     * represents the middle deck cards as a [LinearLayout]
     */
    private val middleDeckCards = LinearLayout<CardView>(
        posX = 665, posY = 400,
        width = 600, height = 200,
        spacing = 3,
        alignment = Alignment.CENTER
    ).apply {
        isVisible = true
    }

    /**
     * A function that gives to every card in the [middleDeck] its front and back view
     * and determines the index of the clicked card
     */
    private fun middleDeckCardsInitialize(
        middleDeck: MutableList<Card>, cardImageLoader: CardImageLoader,
        middleLinearLayout: LinearLayout<CardView>,
    ) {

        middleLinearLayout.clear()  // clears the middle deck

        // this will assign for every card in the middle deck layout a front and back [cardImageLoader]
        middleDeck.forEach { card ->
            val cardView = CardView(
                height = 150, width = 100,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {

                showFront()
                onMouseClicked = {

                    if (card == middleDeck[0]) {
                        clickedMiddleCardIndex = 0
                    }

                    if (card == middleDeck[1]) {
                        clickedMiddleCardIndex = 1
                    }

                    if (card == middleDeck[2]) {
                        clickedMiddleCardIndex = 2
                    }

                }
            }

            middleLinearLayout.add(cardView) // this adds the current cardView to the middle deck´s linear layout
        }

    }


    //-----------------------------------Draw Pile-----------------------------------//

    /**
     * represents the draw pile
     */
    private val drawPile = CardStack<CardView>(
        posX = 1150, posY = 250,
        width = 200, height = 200
    ).apply {
        isVisible = true
        rotation = -45.0
    }

    /**
     * A function that gives to every card in the [drawPile] its front and back view
     */
    private fun drawPileInitialize(
        drawPile: CardPile, cardImageLoader: CardImageLoader,
        stack: CardStack<CardView>,
    ) {
        stack.clear()  // clears the draw pile

        drawPile.cardsOnPile.forEach { card ->
            val cardView = CardView(
                height = 120, width = 90,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply {
                showBack()
            }

            stack.add(cardView) // this adds the current cardView to the draw pile´s linear layout
        }

    }


    //-----------------------------------Discard Pile-----------------------------------//

    /**
     * represents the discard pile
     */
    private val discardPile = CardStack<CardView>(
        posX = 570, posY = 250,
        width = 200, height = 200
    ).apply {
        isVisible = true
        rotation = 45.0
    }

    /**
     * A function that gives to every card in the [discardPile] its back view
     */
    private fun discardPileInitialize(
        discardPile: CardPile, cardImageLoader: CardImageLoader,
        stack2: CardStack<CardView>,
    ) {

        // this clears the discard pile
        stack2.clear()

        discardPile.cardsOnPile.forEach { card ->
            val cardView2 = CardView(
                height = 120, width = 90,
                back = ImageVisual(cardImageLoader.backImage),
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value))
            ).apply {
                showBack()
            }

            stack2.add(cardView2) // this adds the current cardView to the discard pile´s linear layout
        }

    }


    //-----------------------------------Action Buttons-----------------------------------//

    /**
     * This represents a [Button] that  pauses the game and opens the [PauseMenuScene] on click.
     */
    val pauseButton = Button(
        posX = 1800, posY = 20,
        width = 90, height = 90,
        visual = ColorVisual(102, 51, 153),
        text = "| |",
        alignment = Alignment.CENTER,
        font = Font(size = 40, fontWeight = Font.FontWeight.BOLD, color = WHITE)
    ).apply {
        isVisible = true
    }

    /**
     * represents the pass button
     */
    val passButton = Button(
        posX = 410, posY = 850,
        width = 200, height = 70,
        visual = ColorVisual(102, 51, 153),
        text = "Pass",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = WHITE)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    /**
     * represents the knock button
     */
    val knockButton = Button(
        posX = 1310, posY = 850,
        width = 200, height = 70,
        visual = ColorVisual(102, 51, 153),
        text = "Knock",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = WHITE)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.playerActionService.knock()
        }
    }

    /**
     * represents the change-one-card button that changes only
     * one card from the hand of the active player with a card
     * from the middle deck.
     */
    val changeOneCardButton = Button(
        posX = 410, posY = 750,
        width = 200, height = 70,
        visual = ColorVisual(102, 51, 153),
        text = "switch 1",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = WHITE)
    ).apply {
        isVisible = true
        onMouseClicked = {
            try {
                rootService.playerActionService.changeOneCard(clickedPlayerCardIndex, clickedMiddleCardIndex)
            } catch (e: Exception) {
                println("error 202")
            }

        }
    }

    /**
     * represents the change-all-cards button that changes
     * all cards from the hand of the active player with all
     * the cards from the middle deck.
     */
    val changeAllCardsButton = Button(
        posX = 1310, posY = 750,
        width = 200, height = 70,
        visual = ColorVisual(102, 51, 153),
        text = "switch All",
        font = Font(size = 30, fontWeight = Font.FontWeight.BOLD, color = WHITE)
    ).apply {
        isVisible = true
        onMouseClicked = {
            rootService.playerActionService.changeAllCards()
        }
    }


    //-----------------------------------Overriding Methods-----------------------------------//

    /**
     * This function refreshes the middle deck with new cards
     *  from the draw pile after all players have passed
     */
    override fun refreshOnPass() {
        val game = rootService.currentGame
        checkNotNull(game)

        val cardImageLoader = CardImageLoader()

        middleDeckCardsInitialize(game.middleDeck, cardImageLoader, middleDeckCards)
    }

    /**
     * This function refreshes the middleDeck and the playerHandDeck when it is called
     */
    override fun refreshOnChangeAllCards() {
        val game = rootService.currentGame
        checkNotNull(game)

        val activePlayerIndex = game.activePlayerID
        val cardImageLoader = CardImageLoader()

        middleDeckCardsInitialize(game.middleDeck, cardImageLoader, middleDeckCards)
        activePlayerCardsInitialize(game.players[activePlayerIndex].playerHandDeck, cardImageLoader, activePlayerCards)
    }

    /**
     * This function refreshes the chosen middleDeck´card and the chosen playerHandDeck´card when it is called
     */
    override fun refreshOnChangeOneCard(playerCardIndex: Int, middleCardIndex: Int) {
        val game = rootService.currentGame
        checkNotNull(game)

        val activePlayerIndex = game.activePlayerID
        val cardImageLoader = CardImageLoader()

        activePlayerCardsInitialize(game.players[activePlayerIndex].playerHandDeck, cardImageLoader, activePlayerCards)
        middleDeckCardsInitialize(game.middleDeck, cardImageLoader, middleDeckCards)
    }

    /**
     * This function makes the necessary changes and refreshes to start the game
     */
    override fun refreshOnStartGame() {

        // clears every existing layout before starting the game and assigning new cards
        drawPile.clear()
        discardPile.clear()
        activePlayerCards.clear()
        secondPlayerCards.clear()
        middleDeckCards.clear()
        if (rootService.gameService.numberOfPlayers == 3) {
            thirdPlayerCards.clear()
        }
        if (rootService.gameService.numberOfPlayers == 4) {
            fourthPlayerCards.clear()
        }

        // knock button
        knockButton.apply { isDisabled = false }

        // hides the moves counter
        movesRemainingCounter.isVisible = false

        // initialize the current game
        val game = rootService.currentGame
        checkNotNull(game)

        val activePlayerIndex = game.activePlayerID
        val cardImageLoader = CardImageLoader()

        // shows the active player name
        activePlayerName.apply {
            isVisible = true
            text = game.players[activePlayerIndex].name
        }

        // in this part we call and initialize every layout that we need to start the game
        activePlayerCardsInitialize(game.players[activePlayerIndex].playerHandDeck, cardImageLoader, activePlayerCards)
        middleDeckCardsInitialize(game.middleDeck, cardImageLoader, middleDeckCards)
        discardPileInitialize(game.drawPile, cardImageLoader, discardPile)
        drawPileInitialize(game.drawPile, cardImageLoader, drawPile)

        // shows the second player´s deck
        otherPlayerCardsInitialize(game.players[1].playerHandDeck, cardImageLoader, secondPlayerCards)

        // shows the third player´s deck, if exists
        if (rootService.gameService.numberOfPlayers >= 3) {
            otherPlayerCardsInitialize(game.players[2].playerHandDeck, cardImageLoader, thirdPlayerCards)
        }

        // shows the fourth player´s deck, if exists
        if (rootService.gameService.numberOfPlayers == 4) {
            otherPlayerCardsInitialize(game.players[3].playerHandDeck, cardImageLoader, fourthPlayerCards)
        }

    }

    /**
     * This function refreshes the game when it is finished
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        // this holds the current game
        val game = rootService.currentGame
        checkNotNull(game)

        val activePlayerIndex = game.activePlayerID
        val cardImageLoader = CardImageLoader()

        // refreshes the clicked indexes
        clickedMiddleCardIndex = -1
        clickedPlayerCardIndex = -1

        if (!hasGameEnded) {
            activePlayerName.apply {
                text = game.players[activePlayerIndex].name
            }

            activePlayerCardsInitialize(
                game.players[activePlayerIndex].playerHandDeck,
                cardImageLoader,
                activePlayerCards
            )

        }
    }

    /**
     * Initialization of the swimming game scene
     */
    init {
        opacity = 1.0
        background = ImageVisual("GameBackground.jpg")
        addComponents(
            pauseButton, movesRemainingCounter, activePlayerName,
            middleDeckCards, drawPile, discardPile, passButton, knockButton,
            changeOneCardButton, changeAllCardsButton, secondPlayerCards, thirdPlayerCards,
            fourthPlayerCards, activePlayerCards
        )
    }

}