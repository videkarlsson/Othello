package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.player.movestrategy.SimpleStrategy;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleStrategyTest {
	/**
	 * Test that the Strategy returns a valid move when it exist a valid move
	 * for the given player;
	 */
	@Test
	public void testMoveReturnValidMoveWhenValidMoveExist() {
		Node mockNode = Mockito.mock(Node.class);
		String nodeId = "id";
		Mockito.when(mockNode.getId()).thenReturn(nodeId);
		BoardAdapter mockBoard = Mockito.mock(BoardAdapter.class);
		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		nodesOnBoard.add(mockNode);
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		Othello mockOthello = Mockito.mock(Othello.class);
		String playerId = "player";
		Mockito.when(mockOthello.hasValidMove(playerId)).thenReturn(true);
		Mockito.when(mockOthello.getBoard()).thenReturn(mockBoard);
		Mockito.when(mockOthello.isMoveValid(playerId, nodeId)).thenReturn(true);

		SimpleStrategy strategy = new SimpleStrategy();
		assertEquals(mockNode, strategy.move(playerId, mockOthello));
	}

	/**
	 * This test that the Strategy returns a null when it does not exist any
	 * valid move for the given player.
	 */
	@Test
	public void testMoveReturnNullWhenNoValidMoveExist() {
		Othello mockOthello = Mockito.mock(Othello.class);
		String playerId = "player";
		Mockito.when(mockOthello.hasValidMove(playerId)).thenReturn(false);
		SimpleStrategy strategy = new SimpleStrategy();
		assertEquals(null, strategy.move(playerId, mockOthello));
	}

}