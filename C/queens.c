#include <stdio.h>
#include <stdlib.h>

int isLegalAddition(int *ranks, int *ldiags, int *rdiags, int file, int rank) {
  int ld = rank - file;
  int rd = rank + file;
  int i;
  for (i = 1; i < file; i++) {
    if (rank == ranks[i] || ld == ldiags[i] || rd == rdiags[i]) {
      return (1 == 0);
    }
  }
  return (1 == 1);
}

int *placeQueen(int size, int file, int *ranks, int *ldiags, int *rdiags) {
  if (file > size) {
    return ranks;
  } else {
    int rank;
    for (rank=1; rank<=size; rank++) {
      if (isLegalAddition(ranks, ldiags, rdiags, file, rank)) {
        ranks[file] = rank;
        ldiags[file] = rank - file;
        rdiags[file] = rank + file;
        int *maybeSolution = placeQueen(size, file+1, ranks, ldiags, rdiags);
        if (maybeSolution != NULL) {
          return maybeSolution;
        }
      } 
    }
    return NULL;
  }
}

void printFileLabels(int nFiles) {
  int i;
  if (nFiles > 9) {
    for (i=-2; i<=nFiles; i++) {
      if (i<10) { printf(" "); } else { printf("%d", i/10); }
    }
    printf("\n");
  }
  for (i=-2; i<=nFiles; i++) {
    if (i<1) { printf(" "); } else { printf("%d", i%10); }
  }
}

void printBoard(int *qRanks, int n) {
  int i, j;
  if (n<1 || qRanks == NULL) { /* || qRanks[1] == 0) { */
    printf("No solution for size %d.\n", n);
  } else {
    printFileLabels(n);
    for (i=n; i>=1; i--) {
      printf("\n");
      printf("%2d ", i); 
      for (j=1; j<=n; j++) {
        if (qRanks[j] == i) { printf("Q"); } else { printf("."); }
      }
    }
    printf("\n");
    printFileLabels(n);
    printf("\n");
  }
}

int main(int argc, char **argv) {

  char inputline[30];

  int nqueens = 0;
  int firstRank = 1;

  if (argc < 2) {
    printf("Specify a small integer [0-99] defining the board size.\n");
    nqueens = atoi(gets(inputline));
    printf("Optionally specify the rank of the queen on the first file [1-%d].\n", nqueens);
    int fr = atoi(gets(inputline));
    if (fr >= 1 && fr <= nqueens) {
      firstRank = fr;
    }
  } else {
    nqueens = atoi(argv[1]);
    if (argc > 2) {
      int fr = atoi(argv[2]);
      if (fr >= 1 && fr <= nqueens) {
        firstRank = fr;
      }
    }
  }
  printf("nqueens == %d\n", nqueens);
  int *qRanks = calloc(nqueens+1, sizeof(int));
  int *lDiags = calloc(nqueens+1, sizeof(int));
  int *rDiags = calloc(nqueens+1, sizeof(int));
  int *solution;
  if (firstRank <= 1) {
    solution = placeQueen(nqueens, 1, qRanks, lDiags, rDiags);
  } else {
    qRanks[1] = firstRank;
    lDiags[1] = firstRank - 1;
    rDiags[1] = firstRank + 1;
    solution = placeQueen(nqueens, 2, qRanks, lDiags, rDiags);
  }
  printBoard(solution, nqueens);
}
