/*
* O algoritmo utiliza a representação do tabuleiro como um vetor de inteiros, onde o índice representa a coluna e o valor armazenado representa
* a linha onde a rainha está posicionada. O estado inicial é gerado aleatoriamente, atribuindo uma linha aleatória para cada coluna.
* Para gerar vizinhos, para cada rainha (coluna) são gerados novos estados alterando sua posição para cada linha possível (exceto a atual).
* A função de avaliação conta o número total de conflitos (ataques) entre as rainhas, considerando ataques na mesma linha e nas diagonais.
* O algoritmo para quando encontra um estado com 0 conflitos (solução) ou quando não há vizinho com avaliação inferior, indicando um ponto local.
* */
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 4; // Pode ser testado com 8, 20, 50, 100 ou qualquer n >= 4
        int[] state = generateInitialState(n);
        System.out.println("Estado inicial: " + Arrays.toString(state) + " | Conflitos: " + evaluate(state));
        printBoard(state);

        int steps = 0;
        while (true) {
            int currentConflicts = evaluate(state);
            if (currentConflicts == 0) {
                System.out.println("Solução encontrada:" + Arrays.toString(state));
                printBoard(state);
                break;
            }

            int[] nextState = null;
            int bestConflicts = currentConflicts;

            // Gera todos os vizinhos movendo cada rainha para outra linha na sua coluna
            for (int col = 0; col < n; col++) {
                int originalRow = state[col];
                for (int row = 0; row < n; row++) {
                    if (row == originalRow) continue;
                    int[] neighbor = state.clone();
                    neighbor[col] = row;
                    int neighborConflicts = evaluate(neighbor);
                    // Seleciona o vizinho com menor número de conflitos
                    if (neighborConflicts < bestConflicts) {
                        bestConflicts = neighborConflicts;
                        nextState = neighbor;
                    }
                }
            }

            if (nextState == null) {
                System.out.println("Ponto local atingido: " + Arrays.toString(state) + " | Conflitos: " + currentConflicts);
                printBoard(state);
                break;
            } else {
                state = nextState;
                steps++;
                System.out.println("Passo " + steps + ": " + Arrays.toString(state) + " | Conflitos: " + bestConflicts);
                printBoard(state);
            }
        }
    }

    // Gera o estado inicial aleatório para um tabuleiro n x n
    private static int[] generateInitialState(int n) {
        int[] state = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            state[i] = random.nextInt(n);
        }
        return state;
    }

    // Avalia o estado contando o número de conflitos entre as rainhas
    private static int evaluate(int[] state) {
        int conflicts = 0;
        int n = state.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Conflito na mesma linha
                if (state[i] == state[j])
                    conflicts++;
                // Conflito na diagonal
                if (Math.abs(state[i] - state[j]) == Math.abs(i - j))
                    conflicts++;
            }
        }
        return conflicts;
    }

    // Imprime o tabuleiro e as posições (coluna, linha) de cada rainha
    private static void printBoard(int[] state) {
        int n = state.length;
        System.out.println("Tabuleiro:");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (state[col] == row)
                    System.out.print(" Q ");
                else
                    System.out.print(" . ");
            }
            System.out.println();
        }
        System.out.println("Posições das rainhas:");
        for (int col = 0; col < n; col++) {
            System.out.println("Coluna " + col + " -> Linha " + state[col]);
        }
        System.out.println();
    }
}