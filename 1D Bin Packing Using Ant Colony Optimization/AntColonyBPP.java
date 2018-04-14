
import java.io.*;

public class AntColonyBPP
{
    double c = 1.0; //Original no. of trails at the startof the simulation
    double alpha = 1; //Pheromone importance factor
    double beta = 5; //Distance priority
    double evaporation = 0.5;//Rate of evaporation of pheromone
    boolean visited[]=new int[20]; //Check whether a particular bin is visited or not
    double probabilities[]=new int[20]; //Probability of going to a particular bin
    int cap;//Capacity of each bin
    int bins[]=new int [20];
    
    public void main(String[] args)throws IOException
    {
        BufferedReader br= new BufferedReader( new InputStreamReader (System.in));
        AntColonyBP obj=new obj();
        int n,bin, binN, counter;
        int pheromone[] = new int [100];
        int ant=100;///Considering 100 ants are a part of the colony
        
        System.out.println("Enter no. of objects:");
        n=Integer.parseInt(br.readLine());
        int w[] = new int [n];
        for(int i=0;i<n;i++)//Enter weight for each object
        {
            System.out.println("Enter weight for each object "+i+": ");
            w[i]=Integer.parseInt(br.readLine());
        }
        System.out.println("Enter capacity for each bin:");
        int cap=Integer.parseInt(br.readLine());
        int temp[]=new int [n];
        
        for(int i=0;i<100;i++)//Initiaize initial bin weight to 'cap' and pheromone content to 0
        {
            bins[i]=cap;
            pheromone[i]=0;
        }
    }   
    
        
    void AntBasedSolutionConstruction(int w[], int n, int binN )//This function performs the solution construction process where the artificial ants
    {   //move through adjacent states of a problem according to a transition rule, iteratively building solutions
        
        int i=(int)(Math.random()*n) ;
        int c;
        int totalwt=0;
        while(bins[binN]>w[i])//Solution for first bin by 1 ant
        {
            if(bins[binN]<w[i])
                break;
            bins[binN]=bins[binN]-w[i];
            totalwt+=w[i];
            w[i]=-1; 
            c++;
            
            do
            {
                i=(int)(Math.random()*n);
            }while(w[i]==-1);
        }
        PheromoneUpdate(c, totalwt, binN);
    }
    
  
    
    /*Calculating the length of the ant trail based on the pheromone content of adjacent bins
     * and logging them in a 2D array called graph
     */
        
    public static double trailLength(double graph[][], int pheromone[]) 
    {
        int trailSize = pheromone.length();
        double length = graph[pheromone[trailSize - 1]][pheromone[0]];
        for (int i = 0; i < trailSize - 1; i++) 
        {
            length += graph[pheromone[i]][pheromone[i + 1]];
        }
        return length;
    }
        
    
    

    void PheromoneUpdate(int c, int totalwt, int binN, int pheromone[])// It performs pheromone trail updates
    {
        double temp=0;
        temp=totalwt/(double)binN; //Calculating pheromone update for each ant
        if (temp>pheromone[binN])
        {    //Taking pheromone update from the best ant solution
            
            if (visited[binN]==true)
            {
                pheromone   += Math.pow(graph[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta); //Updating pheromone
            }
        }
    }


    void DaemonActions()
    {}

        
    
}
