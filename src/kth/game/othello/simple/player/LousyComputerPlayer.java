package kth.game.othello.simple.player;

import kth.game.othello.board.Node;
import kth.game.othello.simple.SimpleRules;
import kth.game.othello.simple.board.ImmutableBoard;

/**
 * A lousy computer player implementing the {@link ComputerPlayer} interface.
 * 
 * @author Ragnhild Karlsson
 */
public class LousyComputerPlayer implements ComputerPlayer {

	private String id;
	private String name;

	protected LousyComputerPlayer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * The id is a unique identifier of this player in the context of all
	 * players
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * The name of the player
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * The {@link kth.game.othello.player.Player.Type} of the player
	 * 
	 * @return the type
	 */
	@Override
	public Type getType() {
		return Type.COMPUTER;
	}

	/**
	 * Returns a node representing a valid move on the board for the player. If
	 * no valid move exists returns null;
	 * 
	 * @param rules
	 *            The rules of the present game
	 * @param board
	 *            The board where the move should be made
	 * @return A node representing a valid move on the board for the player. If
	 *         no valid move exists returns null;
	 */
	public Node getMove(SimpleRules rules, ImmutableBoard board) {
		Node result = null;
		// Check if player have any valid move;
		for (Node move : board.getNodes()) {
			if (rules.validMove(board, move, getId())) {
				result = move;
				break;
			}
		}
		return result;
	}

    @Override
    public String toString() {
        return "LousyComputerPlayer{" +
            "ID='" + id + '\'' +
            ", Name='" + name + '\'' +
            '}';
    }
}