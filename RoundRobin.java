import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Process {
    int p;               
    int arrivalTime;       
    int burstTime;          
    int remainingBurstTime; 
    int completionTime;     
    int turnAroundTime;     
    int waitingTime;        

    public Process(int p, int arrivalTime, int burstTime) {
        this.p = p;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime; 
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(1, 0, 8));
        processList.add(new Process(2, 1, 4));
        processList.add(new Process(3, 2, 9));
        processList.add(new Process(4, 3, 5));

        int timeQuantum = 4; 

        Queue<Process> readyQueue = new LinkedList<>();
        
        int currentTime = 0;
        int completedProcesses = 0;
        int n = processList.size();
        int processIndex = 0;

        while (processIndex < n && processList.get(processIndex).arrivalTime <= currentTime) {
            readyQueue.add(processList.get(processIndex));
            processIndex++;
        }

        while (completedProcesses < n) {
            
            if (readyQueue.isEmpty()) {
                currentTime++;
                
                while (processIndex < n && processList.get(processIndex).arrivalTime <= currentTime) {
                    readyQueue.add(processList.get(processIndex));
                    processIndex++;
                }
            } else {
                Process currentProcess = readyQueue.poll();

                
                if (currentProcess.remainingBurstTime > timeQuantum) {
                    
                    currentTime += timeQuantum;
                    currentProcess.remainingBurstTime -= timeQuantum;
                    
                    while (processIndex < n && processList.get(processIndex).arrivalTime <= currentTime) {
                        readyQueue.add(processList.get(processIndex));
                        processIndex++;
                    }
                    
                    readyQueue.add(currentProcess);
                    
                } else {
                    
                    currentTime += currentProcess.remainingBurstTime;
                    currentProcess.remainingBurstTime = 0;
                    
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;
                    
                    completedProcesses++;

                    while (processIndex < n && processList.get(processIndex).arrivalTime <= currentTime) {
                        readyQueue.add(processList.get(processIndex));
                        processIndex++;
                    }
                }
            }
        }

        printTable(processList, timeQuantum);
    }

    static void printTable(List<Process> processList, int quantum) {
        System.out.println("Time Quantum: " + quantum);
        System.out.printf(" %-10s  %-12s  %-10s  %-16s  %-12s \n", 
                "P", "Varış", "Burst", "Turnaround Time", "Bekleme");
                System.out.println("------------------------------------------------------------------------");

        double totalWaitingTime = 0;
        double totalTurnAroundTime = 0;

        for (Process p : processList) {
            System.out.printf(" %-10d  %-12d  %-10d  %-16d  %-12d \n", 
                    p.p, p.arrivalTime, p.burstTime, p.turnAroundTime, p.waitingTime);
            
            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;
        }

        System.out.printf("Ortalama Turnaround Time: %.2f\n", (totalTurnAroundTime / processList.size()));
        System.out.printf("Ortalama Bekleme Süresi   : %.2f\n", (totalWaitingTime / processList.size()));
        
    }
}
