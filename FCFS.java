import java.util.*;

class Process {
    String name;        
    int arrivalTime,waitingTime,turnaroundTime,bitis,burstTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFS {

    public static void main(String[] args) {
        
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 4));
        processes.add(new Process("P2", 1, 6));
        processes.add(new Process("P3", 2, 8));
        processes.add(new Process("P4", 3, 10));

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        float totalWaitTime = 0;
        float totalTurnaroundTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.bitis = currentTime + p.burstTime;
            p.turnaroundTime = p.bitis - p.arrivalTime;  
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime = p.bitis;
            totalWaitTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
        }
        printTable(processes);

        System.out.printf("\nOrtalama Bekleme Süresi: %.2f\n", (totalWaitTime / processes.size()));
        System.out.printf("Ortalama Turnaround Süresi: %.2f\n", (totalTurnaroundTime / processes.size()));
        
    }
    private static void printTable(List<Process> processes) {
        System.out.printf("%-5s %-10s %-10s %-10s %-15s %-10s\n", 
                "P", "Varış", "Burst", "Bitiş", "Turnaround", "Bekleme");
        System.out.println("------------------------------------------------------------------");

        for (Process p : processes) {
            System.out.printf("%-5s %-10d %-10d %-10d %-15d %-10d\n", 
              p.name, p.arrivalTime, p.burstTime, p.bitis, p.turnaroundTime, p.waitingTime);
        }
        
    }

    
}