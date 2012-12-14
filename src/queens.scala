class Board(val size: Int) {
  def leftDiagonal(file: Int, rank: Int): Int = {
    rank - file
  }
  def rightDiagonal(file: Int, rank: Int): Int = {
    rank + file
  }
  
  def isLegalAddition(queenRanks: Array[Int], lDiags: Array[Int], rDiags: Array[Int], file: Int, rank: Int):Boolean = {
    //println("file %d, rank %d".format(file, rank))
 //   for (i <- 1 to queens.length-1) {print(queens(i)); print(" ")}; println
    val rd = rightDiagonal(file, rank)
    val ld = leftDiagonal(file, rank)
    for (previousFile <- 1 to file-1) {
      val previousRank = queenRanks(previousFile)
      val prd = rDiags(previousFile)
      val pld = lDiags(previousFile)
      if (rank == previousRank || rd == prd || ld == pld) {
        return false
      }
    }
    true
  }
  
  def placeQueen(file: Int, queenRanks: Array[Int], lDiags: Array[Int], rDiags: Array[Int]) : Option[Array[Int]] = {
	  //    if (file == 0) {
	  //      val limit = size + 1 / 2;
	  //    } else {
	  //      val limit = size;
	  //    }
	  if (file > size) {
		  Some(queenRanks)
	  } else {
		  for (rank <- 1 to size) {
			  if (isLegalAddition(queenRanks, lDiags, rDiags, file, rank)) {
				  queenRanks(file) = rank
				  lDiags(file) = leftDiagonal(file, rank)
				  rDiags(file) = rightDiagonal(file, rank)
				  val maybeSolution = placeQueen(file+1, queenRanks, lDiags, rDiags)
				  if (maybeSolution != None) {
				    return maybeSolution
				  }
			  }
		  }
		  None
	  }
  }
  
  def solve() : Option[Array[Int]] = {
    val ranks = new Array[Int](size + 1)
    for (i <- 1 to size) { ranks(i) = 0; }
    val ldiags = ranks.clone;
    val rdiags = ranks.clone;
    placeQueen(1, ranks, ldiags, rdiags)
  }

  def printFileLabels() = {
	  for (i <- -2 to size) {
		  if (i<10) print(" ") else print(i/10);
	  }
	  println()
	  for (i <- -2 to size) {
		  if (i<1) print(" ") else print(i%10);
	  }
  }
  
  def printIt(solution: Option[Array[Int]]) {
	  solution match {
	  case None => println("No solution for size "+size+".");
	  case Some(queens) => {
		  printFileLabels()
		  for (i <- (1 to size).reverse ) {
			  // ranks
			  println()
			  if (i<10) print(" ")
			  print(i);
			  print(" ");
			  for (j <- (1 to size)) {
				  // files
				  if (queens(j) == i) print("Q") else print(".");
			  }
		  }
		  println()
		  printFileLabels()
	  println()
	  }
	  }
  }
}

object Queens extends App {
	println("Queens On A Chessboard.");
	var strQueens = ""
	if (args.length == 0) {
		println("Specify a small integer (0-99) defining the board size.")
		strQueens = readLine()
		println("Working....")
	} else {
		strQueens = args(0)
		println(strQueens + " queens.")
	}
	val nqueens = strQueens.toInt
	val startTime = java.lang.System.currentTimeMillis()
	val board = new Board(nqueens);
	val solution = board.solve;
	val endTime = java.lang.System.currentTimeMillis()
	val runTimeMillis = endTime - startTime
	println
	board.printIt(solution)
	println("Run time for " + nqueens + " queens: " + runTimeMillis + "ms")
}

// Production approach:
// one queen per file
// next try must be a previously unused rank
// next try checked to see if it's diagonal from another queen
//   "previously unused left-diagonal and right-diagonal"
// if all fail, go back to previous file and try another rank
// if all ranks in first file tried and yield failure, no result. (N=2,3).
// for diagonals: each left-diagonal and right-diagonal has 1 to N squares.
// A board has 2N-1 left-diagonals and 2N-1 right-diagonals.
// N=1: one Q in file 1, rank 1, left-diagonal 1, right-diagonal 1, done.
// N=2: one Q in file 1, rank 1, left-diagonal 2, right-diagonal 1, no place
//  for second Q: either (2, 1X, 3, 2) or (2, 2, 2X, 3).
// Also: first file can try only first half of ranks (symmetry).
