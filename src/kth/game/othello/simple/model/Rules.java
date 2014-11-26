package kth.game.othello.simple.model;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.simple.model.ImmutableBoard.Direction;

//import kth.game.othello.board.Node;

/**
 * Represents the rules of simple Othello.
 * 
 * @author Ragnhild Karlsson
 * 
 */

public class Rules {

	/**
	 * Creates an object with the responsibility of telling the rules of
	 * Othello.
	 */
	public Rules() {

	}

	/**
	 * Returns true iff the nodeCoordinates exist on board and the corresponding
	 * node (A) is not occupied and there exist a node(B) on the board such that
	 * B is occupied with the given playerID and there exist at least one
	 * straight (horizontal, vertical, or diagonal) line between A and B where
	 * all nodes are occupied by other players.
	 * 
	 * @param nodeCordinates
	 *            the coordinates of the node where the player wants to play.
	 * @param PlayerID
	 *            the id of the player making the move.
	 * @param board
	 *            the board where the move would be made.
	 * @return true iff move is valid.
	 */
	public boolean validMove(ImmutableBoard board, Coordinates nodeCordinates, String playerId) {
		// check if nodes exist on board. else return false
		if (!board.hasCoordinates(nodeCordinates)) {
			return false;
		}
		// check if any nodes are swapped by move
		Set<ImmutableNode> swappedNodes = getNodesToSwap(board, nodeCordinates, playerId);
		if (swappedNodes.size() > 0) {
			return true;
		}
		// else return false
		return false;
	}

	/**
	 * Returns true iff any node (A) is not occupied and there exist a node(B)
	 * on the board such that B is occupied with the given playerID and there
	 * exist at least one straight (horizontal, vertical, or diagonal) line
	 * between A and B where all nodes are occupied by the other players.
	 * 
	 * @param PlayerID
	 *            the id of the player making the move.
	 * @param board
	 *            the board in which to look for a valid move.
	 * @return true iff a valid move exists for the given player.
	 */
	public boolean hasValidMove(ImmutableBoard board, String playerId) {
		Set<ImmutableNode> nodesOnBoard = board.getNodes();
		for (ImmutableNode node : nodesOnBoard) {
			if (validMove(board, node.getCoordinates(), playerId)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns false iff any of the players on the board can make a valid move.
	 * 
	 * @board the board to be checked for game over.
	 * @return Returns false iff any of the players on the board can make a
	 *         valid move.
	 */
	public boolean isGameOver(ImmutableBoard board) {
		Set<String> playerIDs = board.getPlayerIDs();
		for (String playerID : playerIDs) {
			if (hasValidMove(board, playerID))
				return false;
		}
		return true;
	}

	/**
	 * Returns a list with the nodes that will be swapped for the given move,
	 * excluding the node that is placed by the player.
	 * 
	 * @param board
	 *            the board where the move would be made.
	 * @param playerId
	 *            the id of the player making the move.
	 * @param node
	 *            a node on the board where the player wants to play.
	 * @return the list of nodes that will be swapped for the given move,
	 *         excluding the node that is placed by the player.
	 */
	public Set<ImmutableNode> getNodesToSwap(ImmutableBoard board, Coordinates nodeCoordinates, String playerId) {
		Set<ImmutableNode> result = new HashSet<ImmutableNode>();
		// check if node exist on board
		if (!board.hasCoordinates(nodeCoordinates)) {
			return result;
		}

		// check if node is occupied
		ImmutableNode node = board.getNodeAtCoordinates(nodeCoordinates);

		if (node.isMarked()) {
			result.clear();
			return result;
		}
		// Add the nodes swapped in each direction
		Set<ImmutableNode> nodesInDirection;
		for (Direction dir : Direction.values()) {
			nodesInDirection = getSwappableNodesInDirection(board, playerId, node, dir);
			result.addAll(nodesInDirection);
		}

		return result;

	}

	/*
	 * Returns the nodes in the given direction (in relation to the given node)
	 * that would be swapped if the player with the given playerId would play at
	 * the given node.
	 */
	private Set<ImmutableNode> getSwappableNodesInDirection(ImmutableBoard board, String playerId,
			ImmutableNode originNode, Direction direction) {

		// check that the following nodes in the given direction belongs to the
		// opponent until we reach a node marked by the given player. If this
		// never happens, return an empty list.
		Set<ImmutableNode> result = new HashSet<ImmutableNode>();
		while (true) {
			ImmutableNode nextNodeInDirection = board.getNextNodeInDirection(originNode, direction);
			if (nextNodeInDirection == null || nextNodeInDirection.getOccupantPlayerId() == null) {
				// Reached edge or unmarked node - return empty list.
				result.clear();
				return result;
			}

			if (nextNodeInDirection.getOccupantPlayerId().equals(playerId)) {
				// Found own node. Return nodes in between.
				return result;
			}

			result.add(nextNodeInDirection);
			originNode = nextNodeInDirection;
		}
	}
}
