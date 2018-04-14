 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.*;

public class ACO_main {

    public static int w[][]=new int [1000][100];
    public static int loop=0;
    public static Ant ACO(Matrix phmGraph, int p, double e, int b, int items, boolean BPP1) 
    {
    /*This function runs the ACO algorithm
     * Inputs:
     * Matrix phmGraph: a two dimensional matrix of size bins x items. 
     *           Represents the construction graph of the BPP problem. The value at
     *           [r+1][c+1] is the pheromone level attracting an ant to put item (c+1)
     *           in bin (r+1)
     *
     * int p-> the number of ants
     * double e->  the evaporation rate of the pheromone(taken 0.6 as the optimum evaporation rate in order to encourage exploration)
     * int b-> number of bins used(10)
     */
        
        //Initialize the arraylist population to hold Ant objects.
        ArrayList<Ant> population=new ArrayList<Ant>();        //Array of objects of class ant
        //public static int w[];
        //Counts the number of fitness evaluations
        int count=0;
        
        //Tracks the global optimum 
        double best=Integer.MAX_VALUE;
            
        //Algorithm begins here
        while(true) {   
            
            //For every ant
            for(int i=0; i<p; i++) {
                
                //Create the ant 
                Ant temp= new Ant(items);

                //Build the solution 
                //For every item, choose the bin it will go into
                for(int c=0; c<items; c++) {
            
                        //Select next edge to traverse (random, biased by pheromone levels 
                        //on the construction graph phmGraph). This is equivalent to selecting the bin 
                        //item c is put into
                    
                        int nextBin=selectNext(c, phmGraph);//****************************
                    
                        //Add the bin to the ant's path. It has just traversed node (nextBin, c) on the adjacency
                        //matrix. This is equivalent to adding item c to the bin nextBin
                        temp.addTo(nextBin, c);//****************************
                        
                
                }//for item
            
                //Evaluate fitness of the ant's and increase evaluation count by one. 
                temp.updateFitness(b, BPP1);
                count++;
                
            
                //Add the ant's solution to the population of solutions
                population.add(temp);
            
            }//for ant
            
            //At this point, p ants each have a solution to the BPP 
            
            //Update the pheromone on the graph
            
            //Check
            Iterator<Ant> pop_it=population.iterator();
            while(pop_it.hasNext()) {
            
                //Retreive the ant
                Ant temp=pop_it.next();
                
                //Calculate the pheromone update; the amount of pheromone it will deposit on
                //each edge it traversed
                double phmUpdate;
                phmUpdate= 100/(temp.getFitness());
            
                //Add phmUpdate to every edge traversed by iterating through the ant's path
                for(int c=0; c<items; c++) {
                    
                    phmGraph.set(temp.getBin(c), c, phmGraph.get(temp.getBin(c), c) + phmUpdate);//****************************
                
                }//for each node of ant's path
            
            }//for ant
            
            //Evaporate pheromone on the construction graph
            phmGraph=phmGraph.multiply(e);

            
        //Check if 10, 0 solutions reached. If yes, return best ant (of current population). 
        //Otherwise, continue
        if(count >= 100) {
            
            population.sort((Ant a1, Ant a2) -> a1.compareTo(a2));
            System.out.println("Global optimum was: " +best);
            return population.get(0);
            
        }
        
        //Keep track of global optimum
        population.sort((Ant a1, Ant a2) -> a1.compareTo(a2));
        
        if(population.get(0).getFitness() < best) {
            best=population.get(0).getFitness();
        }
        
        
        //Clear population; ants will generate all new solutions next iteration
        population.clear();

        }//end while; end of iteration

    }//end ACO function
    
    
    public static int selectNext(int curItem, Matrix m) {
        /*
         * Using a roulette wheel-type mechanism, this function selects the next edge to traverse
         * (i.e. bin in which to put the current item) with a probability proportional to the 
         * amount of pheromone on that edge.
         * Inputs:
         * int curItem: the current item being placed (represented by column number in the adjacency matrix)
         * Matrix m: the adjacency matrix representing the construction graph
         * 
         * Output: the row index in the adjacency matrix of the next node to be traversed (column index is curItem)
         */

        //Retrieve the number of bins 
        int bins=m.getM();
        
        //This array will hold the 'roulette' wheel where each bin is given a segment based on its fitness.
        //The 'segment' is really a portion of the range from 0 to 1 that doesn't overlap with any other bin.
        double[] fitnessArray=new double[bins];
        
        fitnessArray[0]=m.get(0, curItem);
        
        for (int i = 1; i < bins; i++) {
            fitnessArray[i] = fitnessArray[i - 1] + m.get(i, curItem);

         }
        
        //'Spin' wheel. Generate random number and multiply it by the sum of the fitnesses to get 
        //a number in the desired range
        double random = Math.random() * fitnessArray[bins - 1];
        
        
        //Match the random number to a segment of the range from 0 to 1. The bin corresponding to this segment
        //is the bin the item will go in. 
        int binNum = Arrays.binarySearch(fitnessArray, random);
        
        if (binNum < 0) {

            binNum = Math.abs(binNum + 1);

        }
    
        //Return the bin number (row index)
        
        return binNum;
    }
    
    
    public static void main(String[] args)throws IOException {
        
        //This program calls the Ant Colony Optimization algorithm to solve the Bin Packing Problem.
        //It prints the best solution ant population after the algorithm has been run       
        //Parameters to be tested
        //Population size (number of ants)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter no. of TestCases:");
        int t=Integer.parseInt(br.readLine());
        int k[]=new int[t];
        for(int loop=0;loop<t;loop++){
        System.out.println("Enter no of items:");
        k[loop]=Integer.parseInt(br.readLine());
        System.out.println("Enter bin capacity:");
        int bin_cap=Integer.parseInt(br.readLine());
                //Initialize number of items
        //System.out.println("Enter no of items available");
        //int k=Integer.parseInt(br.readLine());
        //w[]=new int [200];
       for(int i=0;i<k[loop];i++)
            w[loop][i]=Integer.parseInt(br.readLine());
        }
        long startTime=System.currentTimeMillis();
        
        for(int loop=0;loop<t;loop++){
        
        //Evaporation rate 
        double e=0.6;
        
        //This variable indicates whether BPP1 or BPP2 is being adressed. 
        //True if BPP1 is being solved
        boolean BPP1=true;
        
        //Number of bins 
   
        int b=30;
        //Initialize number of bins according to the problem being solved
        //Create construction graph
        //Note: Matrix is a helper class
        //Row index r+1 gives bin number, column index c+1 gives item number
        //Edges hold the pheromone value associated with inserting item c+1 into bin r+1
        Matrix conGraph=new Matrix(30,k[loop]);
        
        //Initialize construction graph with a random amount of pheromone (between 0 and 1)
        conGraph.randomPheromoneInitialization();
        
            
        //Call ACO

        Ant result=ACO(conGraph, b, e, b,k[loop], BPP1);
        
        //Print the output ant
        result.print();
        }
        long endTime=System.currentTimeMillis();
        
        System.out.println("TIME: "+(endTime-startTime));
        
    }
        
        

}
