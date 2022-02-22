
public class SudokuSolver {

	public SudokuSolver(){}
	
	static void printSudoku(int[][] sudoku) {
		
		for(int row = 0; row < sudoku.length; row++) {
			for(int column = 0; column < sudoku.length; column++) {
				System.out.print(" " + sudoku[row][column]);
			}
			System.out.print("\n");
		}
	}
	
	static void printCandidatos(int [][][] candidatos) {
		for(int row = 0; row < candidatos.length; row++) {
			for(int column = 0; column < candidatos.length; column++) {
				System.out.print("{");
				for(int i = 0; i < candidatos[row][column].length; i++) {
					System.out.print(candidatos[row][column][i]);
				}
				System.out.print("}");
			}
			System.out.print("\n");
		}
			
	}
	
	static boolean checksquare(int[][] sudoku, int row, int column) {
		for(int i = row; i < row+3; i++) {
			for(int j = column; j <column+3; j++) {
				for(int ii = row; ii < row+3; ii++) {
					for(int jj = column; jj <column+3; jj++) {
						  if(i == ii && j == jj) continue;
						  if(sudoku[i][j] == sudoku[ii][jj]) return false;
					}
				}
			}
		}
		return true;
	}
	
	static boolean checkRow(int[][] sudoku, int row) {
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				if(i == j) continue;
				if(sudoku[row][i] == sudoku[row][j]) return false;
			}
		}
		return true;
	}
	
	static boolean checkColumn(int[][] sudoku, int column) {
		for(int i = 0; i < sudoku.length; i++) {
			for(int j = 0; j < sudoku.length; j++) {
				if(i == j) continue;
				if(sudoku[i][column] == sudoku[j][column]) return false;
			}
		}
		return true;
	}
	
	static boolean isCorrect(int[][] sudoku) {
		for(int i = 0; i < sudoku.length; i++) if(!checkRow(sudoku, i)) return false;
		for(int i = 0; i < sudoku.length; i++) if(!checkColumn(sudoku, i)) return false;
		for(int i = 0; i <= 6; i+=3) {
			for(int j = 0; j <= 6; j+=3) {
				if(!checksquare(sudoku,i,j)) return false;
			}
		}
		return true;
	}
	
	static int squareIndex(int i) {
		if(i >= 0 && i <= 2) return 0;
		else if(i >= 3 && i <= 5) return 3;
		return 6;
	}
	
	static boolean canDiscardAllBoxesInSquare(int[][] sudoku, int row, int column, int numToDiscard ) {
		for(int i = squareIndex(row); i < squareIndex(row)+3; i++) {
			for(int j = squareIndex(column); j < squareIndex(column)+3; j++) {
			  if(sudoku[i][j] == 0) {
				  if(!thereIsANumInRowColumn(numToDiscard, sudoku, i, j)) {
					  return false;
				  }
				}
			}
		}
		return true;
	}
	
	
	static boolean thereIsANumInRowColumn(int num, int[][] sudoku, int row, int column) {
		for(int i = 0; i < 9; i++) {
			if(sudoku[row][i] == num) return true;
			if(sudoku[i][column] == num) return true;
		}
		return false;
	}
	
	static boolean thereIsANumInSquareRowColumn(int num, int[][] sudoku, int row, int column) {
		for(int i = 0; i < 9; i++) {
			if(sudoku[row][i] == num) return true;
			if(sudoku[i][column] == num) return true;
		}
		for(int i = squareIndex(row); i < squareIndex(row)+3; i++) {
			for(int j = squareIndex(column); j <squareIndex(column)+3; j++) {
				if(sudoku[i][j] == num) return true;
			}
		}
		return false;
	}
	
	static int[] add(int [] array, int num){
		int[] arrayCopy = new int[array.length + 1];
		for(int i = 0; i < array.length; i++) {
			arrayCopy[i] = array[i];
		}
		arrayCopy[array.length] = num;
		return arrayCopy;
	}
	
	static boolean exists(int[] array, int num) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == num) return true;
		}
		return false;
	}
	
	static int[] deleteElement(int[] array, int element) {
		int[] arrayCopy = new  int[array.length - 1];
		int ii = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == element) continue;
			arrayCopy[ii] = array[i];
			ii++;
		}
		return arrayCopy;
	}
	
	static boolean allElementsAreTheSame(int[] array) {
		if(array.length == 0 || array.length == 1) return false;
		for(int i = 1; i < array.length; i++) {
			if(array[0] != array[i]) return false;
		}
		return true;
	}
	
	static int[][][] posibleCandidates(int [][][] candidatos){
		for(int i = 0; i <= 6; i+=3) {
			for(int j = 0; j <= 6; j+=3) {
				for(int num = 1; num <= 9; num++) {
					int[] rows = new int[0];
					int[] columns = new int[0];
					for(int row = i; row < i+3; row++) {
						for(int column = j; column <j+3; column++) {
							if(exists(candidatos[row][column], num)) {
								rows = add(rows,row);
								columns = add(columns,column);
								
							}
						}
					}
					if(allElementsAreTheSame(rows)) {
						for(int column = 0; column < 9; column++) {
							if(exists(candidatos[rows[0]][column],num) && (column < j || column > j+2)) {
								candidatos[rows[0]][column] = deleteElement(candidatos[rows[0]][column],num);
							}
						}
					}
					if(allElementsAreTheSame(columns)) {
						for(int row = 0; row < 9; row++) {
							if(exists(candidatos[row][columns[0]],num) && (row < i || row > i+2)) {
								candidatos[row][columns[0]] = deleteElement(candidatos[row][columns[0]],num);
							}
						}
					}
				}
			}
		}
		return candidatos;
	}
	
	static int[][][] candidates(int[][] sudoku){
		int[][][] candidatos = new int[sudoku.length][sudoku.length][0];
		for(int row = 0; row < sudoku.length; row++) {
			for(int column = 0; column < sudoku.length; column++) {
				if(sudoku[row][column] == 0) {
					for(int num = 1; num <= 9; num++) {
					if(!thereIsANumInSquareRowColumn(num, sudoku, row, column)) {
						candidatos[row][column] = add(candidatos[row][column], num);
						}
					}
				}
			}
		}
		printCandidatos(candidatos);
		candidatos = posibleCandidates(candidatos);
		System.out.print("\n");
		printCandidatos(candidatos);
		
		return candidatos;
	}
	
	static void onlyPossibleNumber(int[][] sudoku) {
		int[][][] candidatos = candidates(sudoku);
		
		for(int row = 0; row < sudoku.length; row++) {
			for(int column = 0; column < sudoku.length; column++) {
				if(candidatos[row][column].length == 1) {
					sudoku[row][column] = candidatos[row][column][0];
				}
			}
		}
	}
	
	static void singlePositionTechnique(int [][] sudoku) {
		for(int row = 0; row < sudoku.length; row++) {
			for(int column = 0; column < sudoku.length; column++) {
				if(sudoku[row][column] == 0) {
					for(int num = 1; num <= 9; num++) {
						if(!thereIsANumInSquareRowColumn(num, sudoku, row, column)) {
							sudoku[row][column] = -1;
							if(canDiscardAllBoxesInSquare(sudoku, row, column, num)) {
								sudoku[row][column] = num;
								break;
							}
							sudoku[row][column] = 0;
						}
					}
				}
			}
		}
	}
	
	static int[][] solve(int[][] sudoku){
		
		while(!isCorrect(sudoku)) {
			onlyPossibleNumber(sudoku);
			printSudoku(sudoku);
			System.out.println("unica posicion Technique");
			singlePositionTechnique(sudoku);
			printSudoku(sudoku);
			System.out.println("single Position Technique");
			
		}
		return sudoku;
	}
	
	
	
}

