import java.util.*;

class Process {
    String name;
    int arrivalTime, waitingTime, turnaroundTime, kalanSure, completionTime, burstTime;
    
    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.kalanSure = burstTime; 
    }
}

public class SRTFScheduler {

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 4));
        processes.add(new Process("P2", 1, 6));
        processes.add(new Process("P3", 2, 1)); 
        processes.add(new Process("P4", 3, 8));

        int n = processes.size();
        int currentTime = 0;
        int completedCount = 0;
        
        List<String> ganttChart = new ArrayList<>();

        while (completedCount < n) {
            Process shortest = null;
            int minRemainingTime = Integer.MAX_VALUE;

            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.kalanSure > 0) {
                    if (p.kalanSure < minRemainingTime) {
                        minRemainingTime = p.kalanSure;
                        shortest = p;
                    }
                }
            }

            if (shortest == null) {
                
                currentTime++;
                ganttChart.add("--");
            } else {
                
                shortest.kalanSure--;
                ganttChart.add(shortest.name);
                currentTime++;

                if (shortest.kalanSure == 0) {
                    completedCount++;
                    shortest.completionTime = currentTime;
                    
                    shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                    shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
                }
            }
        }
 
        printTable(processes);
        
        double totalWait = 0, totalTurnaround = 0;
        for(Process p : processes) {
            totalWait += p.waitingTime;
            totalTurnaround += p.turnaroundTime;
        }
        System.out.printf("\nOrtalama Bekleme Süresi: %.2f\n", (totalWait / n));
        System.out.printf("Ortalama Turnaround Süresi: %.2f\n", (totalTurnaround / n));
    }

    private static void printTable(List<Process> processes) {
        System.out.printf("%-5s %-10s %-10s %-10s %-15s %-10s\n", 
                "P", "Varış", "Burst", "Bitiş", "Turnaround", "Bekleme");
        System.out.println("------------------------------------------------------------");

        for (Process p : processes) {
            System.out.printf("%-5s %-10d %-10d %-10d %-15d %-10d\n", 
                    p.name, p.arrivalTime, p.burstTime, p.completionTime, p.turnaroundTime, p.waitingTime);
        }
        
    }
    
   
}
