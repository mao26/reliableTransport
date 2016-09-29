import java.util.ArrayList;

public class GridGameNoRecur {

	public static void main(String[] args) {
		String[] grid = { ".X.X", "..X.", ".X..", "...." };

		GridGameNoRecur gg = new GridGameNoRecur();
		System.out.println(gg.winningMoves(grid));

	}

	public int winningMoves(String[] grid) { // store input into your grid //
		StringBuilder sGrid = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length(); j++) {
				sGrid.append(grid[i].charAt(j));
			}
		}
		ArrayList<TreeNode> prvLvl = new ArrayList<TreeNode>();
		ArrayList<TreeNode> currLvl = new ArrayList<TreeNode>();
		ArrayList<TreeNode> initial = new ArrayList<TreeNode>();
		ArrayList<TreeNode> leaves = new ArrayList<TreeNode>();
		int level = 0;
		while (true) {
			if (prvLvl.isEmpty()) {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length(); j++) {
						if (coordinateAvailable(sGrid, i, j, grid) == true) {
							TreeNode node = new TreeNode();
							node.col = j;
							node.level = level;
							node.row = i;
							prvLvl.add(node);

						}
					}
				}
				initial.addAll(prvLvl);
			}
			level++;
			for (TreeNode eachNode : prvLvl) {
				StringBuilder currGrid = new StringBuilder(sGrid);
				TreeNode tempNode = eachNode;
				while (tempNode != null) {
					fillIn(currGrid, tempNode.row, tempNode.col, grid);
					tempNode = tempNode.parent;
				}
				if (!moveAvailable(currGrid, grid)) {
					leaves.add(eachNode);
					// eachNode.isWinner = true;

				} else {
					for (int i = 0; i < grid.length; i++) {
						for (int j = 0; j < grid[i].length(); j++) {
							if (coordinateAvailable(currGrid, i, j, grid)) {
								TreeNode n = new TreeNode();
								n.parent = eachNode;
								n.row = i;
								n.col = j;
								n.level = level;
								eachNode.children.add(n);
								currLvl.add(n);
							}
						}
					}
				}
			}
			if (currLvl.isEmpty()) {
				break;
			}
			prvLvl = currLvl;
			currLvl = new ArrayList<TreeNode>();
		}
		int winners = 0;
		boolean quit = false;
		ArrayList<TreeNode> tempList = new ArrayList<TreeNode>();
		while (!quit) {
			quit = true;
			leaves.addAll(tempList);
			tempList.clear();
			for (TreeNode child : leaves) {
				TreeNode temp = child;
				boolean skip = false;
				while (temp != null) {
					if (temp.remove) {
						skip = true;
						break;
					} else {
						temp = temp.parent;
					}
				}
				if (skip) {
					child.remove = true;
					continue;

				}
				if (child.level != 0) {
					if (child.level % 2 == 0) {
						quit = false;
						TreeNode grandpa = child.parent.parent;
						grandpa.children.remove(child.parent); // removes child
																// from gpa
						child.remove = true;
						if (grandpa.children.size() == 0) {
							grandpa.level = grandpa.level - 2;
							tempList.add(grandpa);
						}
					} else {
						child.remove = true;
						child.parent.remove = true;
						if (child.parent.parent != null) {
							child.parent.parent.children.remove(child.parent);
						}
					}
				} else {
					winners++;
					child.remove = true;
				}
			}
		}
		return winners;
	}

	private void fillIn(StringBuilder sGrid, int row, int col, String[] grid) {
		int index = row * 4 + col;
		sGrid.setCharAt(index, 'X');
	}

	private char getCharIdx(StringBuilder sGrid, int row, int col, String[] grid) {
		int index = row * 4 + col;
		return sGrid.charAt(index);
	}

	private boolean coordinateAvailable(StringBuilder sGrid, int row, int col,
			String[] grid) {
		int columnCount = grid[0].length();
		int rowCount = grid.length;
		if (getCharIdx(sGrid, row, col, grid) == '.') {
			if (col + 1 < columnCount) {
				if (getCharIdx(sGrid, row, col + 1, grid) == 'X') {
					return false;
				}
			}
			if (col - 1 > 0) {
				if (getCharIdx(sGrid, row, col - 1, grid) == 'X') {
					return false;
				}
			}
			if (row + 1 < rowCount) {
				if (getCharIdx(sGrid, row + 1, col, grid) == 'X') {
					return false;
				}
			}
			if (row - 1 > 0) {
				if (getCharIdx(sGrid, row - 1, col, grid) == 'X') {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean moveAvailable(StringBuilder sGrid, String[] grid) {
		int columnCount = grid[0].length();
		int rowCount = grid.length;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (coordinateAvailable(sGrid, i, j, grid)) {
					return true;
				}
			}

		}
		return false;
	}

	public class TreeNode {
		TreeNode parent;
		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		int row;
		int col;
		int level;
		boolean isWinner;
		boolean remove;
	}

}
