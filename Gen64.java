import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Gen64 {
	String mutated;
	public Gen64(){
		System.out.println(System.getProperty("user.dir"));
		int pop = 100;          //the number of bit strings
		int bitLength = 64;     //length of bit strings
		Random rand = new Random();    //create new random variable
		String[] population = new String[pop];   //array of our population
		for (int i=0;i<pop;i++){				//the population creating loop
			String dude = "";
			for (int j=0; j<bitLength;j++){			//the individual creating loop
				int bit = rand.nextInt(2);
				String bitStr = Integer.toString(bit);   
				dude+=bitStr;
			}
			population[i]=dude;      //the individual is added to the population
		}
		
		life(population);			//the population is thrown into life!
	}
	
	public void life(String[] population){
		Random rand = new Random();
		String gLog = "";					//this gets written out to
		String bestDude = "";
		int bestFit=0;
		int[] popFit = new int[population.length];     //the fitness of the population using a parallel array
		int totalFit = 0;   			//total fitness, used for finding the average
		int avgFit = 0;						//average fitness, used for deciding who stays and goes
		int gens = 100;								//total number of generations
		int fitness = 0;
		try{
			// Create file 
			FileWriter fstream = new FileWriter("GenerationLog.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0; i<population.length;i++){
				fitness = 0;
				for (int j=0; j<population[i].length();j++){
					fitness+=Integer.parseInt(Character.toString(population[i].charAt(j)));
				}
				popFit[i]=fitness;
			}
			int i=0;
			while(bestFit!=population[0].length()){	//for each generation
				bestFit = 0;								//resetting bestFit here so the while loop can make sure bestFit isn't dead.
				gLog+= "\n---------------------Generation "+i+"----------------------Generation "+i+"----------------------Generation "+i+"--------------------";
				totalFit = 0;  				//total fitness is reset
				avgFit = 0;					//average fitness is reset
				for (int j=0; j<population.length;j++){     //if their fitness is not already calculated, then do it.
					fitness = 0;
					if (popFit[j]==0){
						for (int k=0; k<population[j].length();k++){	
							fitness+=Integer.parseInt(Character.toString(population[j].charAt(k)));
						}
						popFit[j]=fitness;
					}
					totalFit+=popFit[j];					//we add to totalFit, which is our numerator for the average fitness
				}	//fitness is done
				avgFit = (totalFit/population.length)+1;			//average fitness is calculated
				for (int j=0; j<population.length;j++){			//go through the population to delete under-performers
					String space = "";							//formatting for write out
					if (popFit[j]>=bestFit){
						bestDude = population[j];
						bestFit = popFit[j];
					}
					int xvd;
					if (popFit[j]<avgFit){					//if we need to delete a dude
						population[j]=null;					//we null his/her bit string
						popFit[j]=0;						//we send his/her fitness to 0
						boolean parFound=false;					//parFound is true if one parent has been found
						String par1 = null;						//parent one
						String par2 = null;						//parent two
						while (par1==null || par2==null){		//while both parents aren't chosen
							int parent = rand.nextInt(popFit.length);  		//we choose a random space
							if (popFit[parent]>=avgFit){					//if their fitness is >= to average fitness we fill a parent
								if (parFound){							//if we already have par1, we fill par2	
									par2=population[parent];			
								}
								else{
									par1=population[parent];			//we fill parent one and parFound==true;
									parFound=true;
								}
							}
						}
						population[j] = sex(par1,par2,rand);		//population gets a new dude
						if (mutated==""){
							mutated=":;:;:;:";					//this means no mutation
						}
						for (int k=0; k<population[j].length();k++){	
							fitness+=Integer.parseInt(Character.toString(population[j].charAt(k)));  //we get the fitness here
						}
						popFit[j]=fitness;									//replace the fitness
						if (j<10){										//
							space+=" ";
						}
						gLog+="\nIndividual #"+j+": "+space+population[j]+" || Fitness: "+popFit[j] + " || Mutations: "+mutated;
						fitness=0;
					}
					else{
						if (j<10){
							space+=" ";
						}
						gLog+="\nIndividual #"+j+": "+space+population[j]+" || Fitness: "+popFit[j] + " || Mutations: :;:;:;:;:;";
					}
				}
				gLog+="\n                                              Average Fitness: " +avgFit+
						"\n\n                                     !!!!!!!!!!!!!!Best Individual!!!!!!!!!!!!!!!!" +
						"\n                      ----------------------------BitString----------------------------\n " +
						"                      "+bestDude+
						"\n                      -----------------------------Fitness-----------------------------\n" +
						"                                                     "+bestFit+"\n\n\n";
				out.write(gLog);
				gLog="";
				i++;
			} 
			out.close();
		}
		catch (Exception e){
			  System.err.println("This will never happen");
		}
	}
	public String sex(String par1, String par2, Random rand){
		String behbeh = "";
		mutated = "";
		String[] bed = {par1, par2};
		int mut = rand.nextInt(100);
		int mutChromo = rand.nextInt(par1.length());
		boolean mutation=false;
		if (mut<=1){
			mutation=true;
		}
		for (int i=0;i<par1.length();i++){
			int onTop = rand.nextInt(2);
			String chromo= Character.toString(bed[onTop].charAt(i));
			if (mutation && mutChromo==i){
				if (chromo=="0"){
					mut=1;
				}
				else{
					mut=0;
				}
				behbeh+=mut;
				mutated+= "Bit #"+i+": "+chromo+"->"+mut;
				mutation=false;
			}
			else{
				behbeh+=chromo;
			}
		}
		return behbeh;
	}
	public static void main(String[] args){
		Gen64 g = new Gen64();
	}
}



