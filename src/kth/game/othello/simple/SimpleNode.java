package kth.game.othello.simple;

import kth.game.othello.board.Node;

/**
* A simple implementation of the {@link kth.game.othello.board.Node}
* interface, SimpleNode is an immutable class.
*
* @author mikael
*/
public final class SimpleNode implements Node {

	private final String nodeId;
	private final String occupantPlayerId;
	private final int xCoordinate;
	private final int yCoordinate;

	/**
	 * Construct an immutable node given its coordinates and an optional player ID.
	 * 
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param playerId
	 *            optional, set to null if the node is unmarked.
	 */
	protected SimpleNode(int xCoordinate, int yCoordinate, String playerId) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.occupantPlayerId = playerId;
		int idCode = xCoordinate << 16;
		idCode = idCode | yCoordinate;
		this.nodeId = Integer.toString(idCode);
	}

	/**
	 * The unique identifier of a node. A node should be identified uniquely
	 * given the x- and y-coordinate
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.nodeId;
	}

	/**
	 * To get the player id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	@Override
	public String getOccupantPlayerId() {
		return this.occupantPlayerId;
	}

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
		return this.xCoordinate;
	}

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
		return this.yCoordinate;
	}

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	@Override
	public boolean isMarked() {
		if (occupantPlayerId != null) {
			return true;
		}
		return false;
	}
}
