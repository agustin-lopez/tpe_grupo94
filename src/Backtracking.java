import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Backtracking {

    private int totalPieces;
    private HashMap<String, Integer> machines;
    private HashMap<String, Integer> solution;
    private HashMap<String, Integer> bestSolution;
    private int states;

    public Backtracking() {
        this.totalPieces = 0;
        this.waste = 0;
        this.machines = new LinkedHashMap<>();
        this.bestSolution = new ArrayList<>();
    }

    //GETTERS

    public int getStates() { return this.states; }

    public int getWaste() { return this.waste; }

    public int getTotalPieces() { return this.waste; }

    public ArrayList<String> getBestSolution() { return this.bestSolution; }


    //MÉTODOS

    private void clear() {
        this.bestSolution.clear();
        this.machines.clear();
        this.waste = 0;
        this.states = 0;
        this.totalPieces = 0;
    }

    public void solve(LinkedHashMap<String, Integer> machines, int totalPieces) {

        this.clear();

        this.machines.putAll(machines);
        this.totalPieces = totalPieces;
        this.waste = totalPieces;

        //POR CADA MÁQUINA DE LA LISTA, SE LLAMA AL MÉTODO RECURSIVO
        for (String m : machines.keySet()) {
            solve(m, 0, new ArrayList<>());
        }

        this.print();

    }

    //ESTA SOLUCIÓN TOMA COMO PRIORIDAD QUE SE DESPERDICIE LA MENOR CANTIDAD DE PIEZAS POSIBLES.
    //CUANDO ENCUENTRA UNA NUEVA SOLUCIÓN VÁLIDA QUE TIENE EL MISMO DESPERDICIO DE PIEZAS QUE LE MEJOR SOLUCIÓN,
    //SE QUEDA CON LA QUE USE MENOS CANTIDAD DE MÁQUINAS
    private void solve(String currentMachine, int sum, ArrayList<String> solution) {

        //SE SUMA UN ESTADO AL CONTADOR
        this.states++;
        //SE AGREGA LA MÁQUINA ACTUAL A LA SOLUCIÓN
        solution.add(currentMachine);
        //SE SUMA SU CAPACIDAD DE FABRICACIÓN A LA SUMA TOTAL
        sum = sum + machines.get(currentMachine);

        System.out.println(solution + ", sum = " + sum);

        //CONDICIÓN DE CORTE: QUE LA CAPACIDAD TOTAL DE LAS MÁQUINAS EN LA SOLUCIÓN ACTUAL
        //SEA MAYOR O IGUAL A LA CANTIDAD DE PIEZAS A FABRICAR
        if (sum >= totalPieces) {
            //SE CALCULA CUÁNTAS PIEZAS SE DESPERDICIAN
            int currentWaste = sum - totalPieces;
            //SI EL DESPERDICIO ES MENOR AL DE LA MEJOR SOLUCIÓN ENCONTRADA HASTA EL MOMENTO...
            if (currentWaste < this.waste) {
                //SE TOMA COMO UNA NUEVA MEJOR SOLUCIÓN
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //SI NO ES MENOR, PREGUNTA SI EL DESPERDICIO ES IGUAL Y SI LA SOLUCIÓN ES DE MENOR TAMAÑO QUE LA MEJOR HASTA EL MOMENTO
            else if ((currentWaste == this.waste) && (solution.size() < this.bestSolution.size())) {
                //EN CASO DE CUMPLIR ESA CONDICIÓN, SIGNIFICA QUE ESTA SOLUCIÓN DESPERDICIA LA MISMA CANTIDAD DE PIEZAS
                //PERO USA MENOS CANTIDAD DE MÁQUINAS (O SEA, ES MEJOR)
                this.bestSolution.clear();
                this.bestSolution.addAll(solution);
                //Y SE ACTUALIZA EL DESPERDICIO
                this.waste = currentWaste;
            }
            //(SI NO CUMPLE NINGUNA DE LAS CONDICIONES ANTERIORES ES PORQUE EL DESPERDICIO ES MAYOR, Y SE DESCARTA ESA SOLUCIÓN)
        }
        //SI NO CUMPLE LA CONDICIÓN DE CORTE...
        else {
            //POR CADA MÁQUINA DE LA LISTA...
            for (String m : machines.keySet()) {
                //SE LLAMA RECURSIVAMENTE A ESTE MÉTODO
                this.solve(m, sum, solution);
                //Y CUANDO VUELVE, SE SACA ESA MÁQUINA DE LA SOLUCIÓN
                solution.removeLast();
            }

        }

    }

    public void print() {
        System.out.println();
        System.out.println("PIECES TO BE FABRICATED: " + this.totalPieces);
        System.out.print("BEST SOLUTION FOUND: ");

        for (String m : bestSolution) {
            System.out.print(m + " (" + this.machines.get(m) + ") ");
        }

        System.out.println();
        System.out.println("PIECES WASTED: " + this.getWaste());
        System.out.println("STATES GENERATED: " + this.getStates());
    }

}
