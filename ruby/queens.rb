#!/usr/bin/env ruby
# queens.rb

def leftDiagonal(rank, file)
 return rank - file
end

def rightDiagonal(rank, file)
 return rank + file
end

def isLegalAddition(qRanks, lDiags, rDiags, file, rank)  
  ld = rank - file
  rd = rank + file
  for previousFile in 1 .. file-1 
    previousRank = qRanks[previousFile]
    prd = rDiags[previousFile]
    pld = lDiags[previousFile]
    if (rank == previousRank || rd == prd || ld == pld) 
      return false
    end
  end
  return true
end

def placeQueen(size, file, ranks, ldiags, rdiags)
  if (file > size) 
    return ranks
  else
    openRanks = (1..size).to_a - ranks.slice(1, file-1)
    openRanks.shuffle!
    for rank in openRanks
      if (isLegalAddition(ranks, ldiags, rdiags, file, rank)) 
        ranks[file] = rank
        ldiags[file] = rank - file
        rdiags[file] = rank + file
        maybeSolution = placeQueen(size, file+1, ranks, ldiags, rdiags)
        if (maybeSolution != nil)
          return maybeSolution
        end
      end
    end
    return nil
  end
end

def solve(nFiles)
  ranks  = Array.new(nFiles+1, 0)
  ldiags = Array.new(nFiles+1, 0)
  rdiags = Array.new(nFiles+1, 0)
  placeQueen(nFiles, 1, ranks, ldiags, rdiags)
end

def printFileLabels(n)
  if (n>9)
    for i in -2..n
      if (i<10) then putc ' '  else print "#{i/10}" end
    end
    puts
  end
  for i in -2..n
    if (i<1) then putc ' '  else print "#{i%10}" end
  end
end

def printBoard(qRanks, n)
  if (qRanks == nil) 
    puts "No solution for size #{n}"
  else
    printFileLabels(n)
    for i in (1..n).to_a.reverse
      puts
      if (i<10) then print ' ' end
      print "#{i}"
      print ' '
      for j in 1..n
        if (qRanks[j] == i) then print 'Q' else print '.' end
      end
    end
    puts
    printFileLabels(n)
    puts
  end
  puts
end

puts("Queens On A Chessboard.");
if ARGV.length == 0 then
  puts("Specify a small integer (0-99) defining the board size.");
  strQueens = readline();
  puts("Working...")
else
  strQueens = ARGV[0]
  puts "#{strQueens} queens."
end
nqueens = Integer(strQueens)
startTime = Time.new
solution = solve(nqueens)
endTime = Time.new
runTimeMillis = (1000000 * (endTime - startTime)).round / 1000.0
printBoard(solution, nqueens)
puts "Run time for #{nqueens} queens: #{runTimeMillis} msec"
