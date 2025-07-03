import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Greedy {


    //ESTA SOLUCIÓN CREA EL CONJUNTO SOLUCIÓN TOMANDO LA MÁQUINA DE MAYOR CAPACIDAD EN CADA ITERACIÓN (SIN QUE SE PASE DE LA CANTIDAD DE PIEZAS A FABRICAR)
    //HASTA LLEGAR A LA CANTIDAD REQUERIDA DE PIEZAS.
    //-LOS CANDIDATOS ESTÁN COMPUESTOS POR TODAS LAS MÁQUINAS DISPONIBLES.
    //-PARA LA SELECCIÓN DEL MEJOR CANDIDATO BUSCAMOS LA MÁQUINA QUE TENGA MAYOR PRODUCCIÓN DE PIEZAS Y QUE NO EXCEDA LA CANTIDAD A FABRICAR.
    // EN CASO DE QUE NO HAYA NINGUNA, ELEGIMOS LA QUE MENOR DESPERDICIO NOS DEJE.

    private int totalPieces;
    private LinkedHashMap<String, Integer> machines;
    private ArrayList<String> solution;
    private int candidates;
    private ArrayList<String> machinesList;

    public Greedy(){
        this.totalPieces = 0;
        this.machines = new LinkedHashMap<>();
        this.solution = new ArrayList<>();
        this.machinesList = new ArrayList<>();
    }

    public int getCandidates() { return this.candidates; }

    public ArrayList<String> getSolution() { return this.solution; }

    private void clear() {
        this.machines.clear();
        this.candidates = 0;
        this.totalPieces = 0;
    }

    public void solve(LinkedHashMap<String, Integer> machines, int totalPieces) {


        this.clear();
        this.sort(machines);
        this.machines.putAll(machines);
        this.totalPieces = totalPieces;

        for(String m : this.machines.keySet()){
            machinesList.add(m);
        }

        this.solve();
        this.print();

    }

    private void solve() {

        int m = -1;
        int total = 0;

        //MIENTRAS QUE LA CANTIDAD DE PIEZAS ACUMULADAS NO SUPERE LA CANTIDAD DE PIEZAS A FABRICAR...
        while (total < this.totalPieces && !machinesList.isEmpty()) {

            //SE SELECCIONA LA POSICIÓN DE MEJOR MÁQUINA DISPONIBLE, ENVIANDO COMO PARÁMETRO LA CANTIDAD DE PIEZAS QUE QUEDAN POR FABRICAR
            m = this.select(totalPieces - total);
            //SI NO ES -1, SIGNIFICA QUE LA MÁQUNIA SELECCIONADA NO EXCEDE EL LÍMITE DE PRODUCCIÓN...
            if(m != -1){
                //SE AGREGA A LA SOLUCIÓN
                this.solution.add(machinesList.get(m));
                //Y SE SUMA AL TOTAL DE PIEZAS
                total+= machines.get(machinesList.get(m));
            }
        }
    }

    private int select(int diff) {

        this.candidates++;

        if (machines.get(machinesList.getFirst()) > diff) {
            //SI LA PRIMER MÁQUINA SE PASA DE LAS PIEZAS A PRODUCIR, SE BORRA
            machines.remove(machinesList.getFirst());
            machinesList.removeFirst();
            //Y RETORNA -1
            return -1;
        }

        return 0;

    }

    private void sort(LinkedHashMap<String, Integer> machines){
        //ORDENAMOS LAS MAQUINAS POR SU CAPACIDAD DE FABRICACIÓN PARA FACILITAR LA BUSQUEDA DE LA MEJOR...
        Map<String, Integer> sorted = machines.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));

        this.machines.clear();
        this.machines.putAll(sorted);


    }

    private void print() {

        System.out.println();

        if (this.machines.isEmpty()) {
            System.out.println("GREEDY SOLUTION:");
            System.out.println("NO SOLUTION FOUND");
        }
        else {
            System.out.println();
            System.out.println("GREEDY SOLUTION:");
            System.out.println("PIECES PRODUCED: " + this.totalPieces);
            System.out.print("BEST SOLUTION FOUND: ");

            for (String m : solution) {
                System.out.print(m + " ");
            }

            System.out.println();
            System.out.println("MACHINES RUNNING: " + this.solution.size());
            System.out.println("MACHINES CONSIDERED: " + this.candidates);
        }
    }

}
