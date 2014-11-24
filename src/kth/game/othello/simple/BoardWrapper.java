package kth.game.othello.simple;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * //TODO
 */
public class BoardWrapper implements Board {

	private List<NodeWrapper> nodeWrappers;

	public BoardWrapper(List<NodeWrapper> nodeWrappers) {
		// TODO Order list
	}

	/**
	 * Returns the node with the given x- and y-coordinate
	 * 
	 * @param x
	 *            the x-coordinate of the node
	 * @param y
	 *            the y-coordinate of the node
	 * @return the node with given x- and y-coordinate
	 * @throws IllegalArgumentException
	 *             if there is no {@link kth.game.othello.board.Node} having the
	 *             specific x- and y-coordinate
	 */
	@Override
	public Node getNode(int x, int y) {
		return null;
	}

	/**
	 * Returns an ordered list of rows using the natural order in x- and then
	 * y-coordinate of the nodes.
	 * 
	 * @return the nodes of the board
	 */
	@Override
	public List<Node> getNodes() {
		return null;
	}

	/**
	 * TODO return null if node with given Id does not exist on board;
	 * 
	 * @param id
	 * @return
	 */
	public Node getNodeById(String id) {
		// TODO implement;
		return null;
	}

}