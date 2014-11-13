package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.board.Node;
import kth.game.othello.simple.SimpleBoard.Direction;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleRulesTest {

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 * x o *
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  could player 1 play on the non occupied node
	 * </pre>
	 */
	@Test
	public void testValidMoveOnBoardEdgeShouldBeValid() {
		String player1Id = "1";
		String player2Id = "2";
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		// add node 1
		Node boardNode1 = getMockedNode(player1Id);
		nodesOnBoard.add(boardNode1);
		Node boardNode2 = getMockedNode(player2Id);
		nodesOnBoard.add(boardNode2);
		Node boardNode3 = getMockedNode(null);
		nodesOnBoard.add(boardNode3);
		// set up behavior for getNextNodeInDirection;
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.EAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.WEST)).thenReturn(boardNode2);

		Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, Direction.WEST)).thenReturn(boardNode1);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, Direction.WEST)).thenReturn(null);
		SimpleRules rules = new SimpleRules();
		assertEquals(true, rules.validMove(mockBoard, boardNode3, player1Id));

	}

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 *   0 1 2 3 4 5  
	 * 0   	 *   o	
	 * 1 x o o * o x
	 *  
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  will getNodesToSwap return a list with the following nodes (with coordinates) (1,1) (2,1) (4,1)
	 *  if player 1 plays on (3,1).
	 * </pre>
	 */
	@Test
	public void testValidMoveShouldGiveCorrectNumberOfSwapedNodes() {
		String player1Id = "1";
		String player2Id = "2";
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		// add node 1
		Node boardNode20 = getMockedNode(null);
		Node boardNode40 = getMockedNode(player2Id);
		Node boardNode01 = getMockedNode(player1Id);
		Node boardNode11 = getMockedNode(player2Id);
		Node boardNode21 = getMockedNode(player2Id);
		Node playAtNode = getMockedNode(null);
		Node boardNode41 = getMockedNode(player2Id);
		Node boardNode51 = getMockedNode(player1Id);

		// set up behavior for getNextNodeInDirection;

		for (Direction dir : Direction.values()) {
			switch (dir) {
			case NORTHEAST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode40);
				break;
			case EAST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode41);
				break;
			case WEST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode21);
				break;
			case NORTHWEST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode20);
				break;
			default:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(null);
				break;

			}
		}

		Mockito.when(mockBoard.getNextNodeInDirection(boardNode40, Direction.NORTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode41, Direction.EAST)).thenReturn(boardNode51);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode21, Direction.WEST)).thenReturn(boardNode11);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode11, Direction.WEST)).thenReturn(boardNode01);

		// Check that the right nodes (and no other nodes) where listed as nodes
		// to swap
		SimpleRules rules = new SimpleRules();
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, player1Id).contains(boardNode41));
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, player1Id).contains(boardNode21));
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, player1Id).contains(boardNode11));
		assertEquals(3, rules.getNodesToSwap(mockBoard, playAtNode, player1Id).size());

		// Verify that all calls to getNextNodeInDirection() was made
		for (Direction dir : Direction.values()) {
			Mockito.verify(mockBoard, Mockito.times(4)).getNextNodeInDirection(playAtNode, dir);
		}
		Mockito.verify(mockBoard, Mockito.times(4)).getNextNodeInDirection(boardNode40, Direction.NORTHEAST);
		Mockito.verify(mockBoard, Mockito.times(4)).getNextNodeInDirection(boardNode41, Direction.EAST);
		Mockito.verify(mockBoard, Mockito.times(4)).getNextNodeInDirection(boardNode21, Direction.WEST);
		Mockito.verify(mockBoard, Mockito.times(4)).getNextNodeInDirection(boardNode11, Direction.WEST);

	}

	/**
	 * Test that given a board with an occupied node it's an invalid move for
	 * any player to play at this node.
	 */
	@Test
	public void testMoveOnOccupiedNodeShouldBeInvalid() {
		String player1Id = "1";
		String player2Id = "2";
		Node boardNode = getMockedNode(player1Id);
		SimpleRules rules = new SimpleRules();
		// By giving null as the argument board, this test will fail if the
		// method validMove() tries to calculate if this move is valid
		assertEquals(false, rules.validMove(null, boardNode, player1Id));
		assertEquals(false, rules.validMove(null, boardNode, player2Id));
	}

	/**
	 * Test that given a board with an occupied node getNodesToSwap will return
	 * an empty list if any player plays on this node
	 */
	@Test
	public void testMoveOnOccupiedNodeShouldSwapNoNodes() {
		String player1Id = "1";
		Node boardNode = getMockedNode(player1Id);
		SimpleRules rules = new SimpleRules();
		// By giving null as the argument board, this test will fail if the
		// method getNodesToSwap() tries to calculate the nodes to swap
		assertEquals(true, rules.getNodesToSwap(null, boardNode, null).isEmpty());
		assertEquals(true, rules.getNodesToSwap(null, boardNode, player1Id).isEmpty());
	}

	/*
	 * Return a mocked node, if playerId is not null isMarked() will return true
	 * and getOccupantPlayerId() return the given playerId. Otherwise false and
	 * null, respectively, will be returned for these method calls.
	 */
	private Node getMockedNode(String playerId) {
		Node mockNode = Mockito.mock(Node.class);

		boolean isOccupied = true;
		if (playerId == null) {
			isOccupied = false;
		}
		Mockito.when(mockNode.isMarked()).thenReturn(isOccupied);
		Mockito.when(mockNode.getOccupantPlayerId()).thenReturn(playerId);
		return mockNode;
	}

}
