package kth.game.othello.simple.model;

import java.util.Optional;
import java.util.Set;

public class GameState {

	private TurnKeeper turnKeeper;
	private Rules rules;
	private ImmutableBoard board;
	private String playerInTurn;

	public GameState(ImmutableBoard startBoard, TurnKeeper turnKeeper, Rules rules, String startPlayer) {
		board = startBoard;
		this.turnKeeper = turnKeeper;
		this.rules = rules;
		if (rules.hasValidMove(startBoard, startPlayer)) {
			playerInTurn = startPlayer;
		} else {
			playerInTurn = turnKeeper.getPlayerInTurn(startPlayer, startBoard, rules);
		}
	}

	/**
	 * Return the board of the gameState
	 */
	public ImmutableBoard getBoard() {
		return board;
	}

	/**
	 * Get the id of the player in turn or null if no player can move.
	 * 
	 * @return the id of the player in turn
	 */
	public String getPlayerInTurn() {
		return playerInTurn;
	}

	/**
	 * Determines if the player with given id has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player with the given id has a valid move.
	 */
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(board, playerId);
	}

	/**
	 * Determines if the game is active or over.
	 * 
	 * @return false if the game is over.
	 */
	public boolean isGameOver() {
		return !rules.isGameOver(board);
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId
	 *            the id of the player making the move.
	 * @param nodeCoordinates
	 *            the coordinates of the node where the player wants to play.
	 * @return true if the move is valid.
	 */
	public boolean isMoveValid(String playerId, Coordinates nodeCoordinates) {
		return rules.validMove(board, board.getNodeAtCoordinates(nodeCoordinates), playerId);
	}

	/**
	 * 
	 * If move is valid an optional of the resulting gameState be returned
	 * otherwise an empty optional.
	 * 
	 * @param playerId
	 *            id of the player that do the move
	 * @param nodeCoordinates
	 *            The coordinates of the node where the player want to do the
	 *            move
	 * @return If move is valid an optional of the resulting gameState be
	 *         returned otherwise an empty optional.
	 */
	public Optional<GameState> tryMove(String playerId, Coordinates nodeCoordinates) {

		if (!rules.validMove(board, board.getNodeAtCoordinates(nodeCoordinates), playerId)) {
			return Optional.empty();
		}
		ImmutableNode playAtNode = board.getNodeAtCoordinates(nodeCoordinates);
		Set<ImmutableNode> nodesToSwap = rules.getNodesToSwap(board, playAtNode, playerId);
		ImmutableBoard newBoard = board.swapNodes(nodesToSwap, playerId);
		String nextPlayerInTurn = turnKeeper.getPlayerInTurn(playerId, newBoard, rules);
		GameState nextGameState = new GameState(newBoard, turnKeeper, rules, nextPlayerInTurn);
		return Optional.of(nextGameState);
	}

}
