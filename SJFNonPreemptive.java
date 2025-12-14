import java.util.ArrayList;
import java.util.List;

class Process {
    String name;
    int waitingTime, turnAroundTime,arrivalTime, completionTime, burstTime;  
    boolean isCompleted; 

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.isCompleted = false;
    }
}

public class SJFNonPreemptive {
    public static void main(String[] args) {
        
        List<Process> processList = new ArrayList<>();
        processList.add(new Process("P1", 0, 7));
        processList.add(new Process("P2", 2, 4));
        processList.add(new Process("p3", 4, 1));
        processList.add(new Process("P4", 5, 4));

        int totalProcesses = processList.size();
        int currentTime = 0;
        int completedCount = 0;

        while (completedCount < totalProcesses) {
            int minIndex = -1;
            int minBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < totalProcesses; i++) {
                Process currentProcess = processList.get(i);
                
                if (currentProcess.arrivalTime <= currentTime && !currentProcess.isCompleted) {
                    
                    if (currentProcess.burstTime < minBurstTime) {
                        minBurstTime = currentProcess.burstTime;
                        minIndex = i;
                    }
                    else if (currentProcess.burstTime == minBurstTime) {
                        if (currentProcess.arrivalTime < processList.get(minIndex).arrivalTime) {
                            minIndex = i;
                        }
                    }
                }
            }

            if (minIndex != -1) {
                Process selectedProcess = processList.get(minIndex);
                currentTime += selectedProcess.burstTime;
                selectedProcess.completionTime = currentTime;
                selectedProcess.turnAroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
                selectedProcess.waitingTime = selectedProcess.turnAroundTime - selectedProcess.burstTime;
                
                selectedProcess.isCompleted = true;
                completedCount++;
            } else {
                currentTime++;
            }
        }
        printTable(processList);
    }

    static void printTable(List<Process> processList) {
        System.out.printf(" %-10s  %-12s  %-10s  %-16s  %-12s \n", 
                "P", "varış", "Burst", "Turnaround Time", "Bekleme");
        System.out.println("---------------------------------------------------");

        double totalWaitingTime = 0;
        double totalTurnAroundTime = 0;

        for (Process p : processList) {
            System.out.printf(" %-10d  %-12d  %-10d  %-16d  %-12d \n", 
                    p.name, p.arrivalTime, p.burstTime, p.turnAroundTime, p.waitingTime);
            
            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;
        }

        
        System.out.printf("Ortalama Turnaround Time: %.2f\n", (totalTurnAroundTime / processList.size()));
        System.out.printf("Ortalama Waiting Time   : %.2f\n", (totalWaitingTime / processList.size()));
        
    }
}