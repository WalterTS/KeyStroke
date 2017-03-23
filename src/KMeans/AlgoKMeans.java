package KMeans;
/* This file is copyright (c) 2008-2013 Philippe Fournier-Viger
* 
* This file is part of the SPMF DATA MINING SOFTWARE
* (http://www.philippe-fournier-viger.com/spmf).
* 
* SPMF is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with
* SPMF. If not, see <http://www.gnu.org/licenses/>.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import keystroke.OpenClose;

import tools.Cluster;
import tools.DoubleArray;
import tools.MemoryLogger;

/**
 * An implementation of the K-means algorithm (J. MacQueen, 1967). 
 * <br/><br/>
 * 
 * The K-means  algorithm steps are (text from Wikipedia) : 1) Choose the number of clusters, k.
 * * 2) Randomly generate k clusters and determine the cluster centers, or directly
 * generate k random points as cluster centers. 3) Assign each point to the
 * nearest cluster center. 4) Recompute the new cluster centers. 5) Repeat the two
 * previous steps until some convergence criterion is met (usually that the
 * assignment hasn't changed).
 * 
 * @author Philippe Fournier-Viger
 */

public class AlgoKMeans {
    double checar=0;
    int contador=0,contador2=0;
    String nombre="";
    int pos=0;
    int p=0;
    //double Distancia;
    double distancias[]=new double[3];

	// The list of clusters generated
	private List<Cluster> clusters = null;
	
	// A random number generator because K-Means is a randomized algorithm
	private final static Random random = new Random(System.currentTimeMillis());
	
	// For statistics
	private long startTimestamp; // the start time of the latest execution
	private long endTimestamp;  // the end time of the latest execution
	private long iterationCount; // the number of iterations that was performed

	/**
	 * Default constructor
	 */
	public AlgoKMeans() { 
		
	}
	
	/**
	 * Run the K-Means algorithm
	 * @param inputFile an input file path containing a list of vectors of double values
	 * @param k the parameter k
	 * @return a list of clusters (some of them may be empty)
	 * @throws IOException exception if an error while writing the file occurs
	 */
	public List<Cluster> runAlgorithm(String inputFile, int k, String name) throws NumberFormatException, IOException {
		// record the start time
            nombre=name;
		startTimestamp =  System.currentTimeMillis();
		// reset the number of iterations
		iterationCount =0;
		
		// Structure to store the vectors from the file
		List<DoubleArray> vectors = new ArrayList<DoubleArray>();
		
		// variables to store the minimum and maximum values in vectors
		double minValue = Integer.MAX_VALUE;
		double maxValue = 0;
		
		// read the vectors from the input file
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line;
		// for each line until the end of the file
		while (((line = reader.readLine()) != null)) {
			// if the line is  a comment, is  empty or is a
			// kind of metadata
			if (line.isEmpty() == true ||
					line.charAt(0) == '#' || line.charAt(0) == '%'
							|| line.charAt(0) == '@') {
				continue;
			}
			// split the line by spaces
			String[] lineSplited = line.split(",");
			// create a vector of double
			double [] vector = new double[lineSplited.length];
			// for each value of the current line
			for (int i=0; i< lineSplited.length; i++) { 
				// convert to double
				double value = Double.parseDouble(lineSplited[i]);
				// check if it is the min or max
				if(value < minValue){
					minValue = value;
				}
				if(value > maxValue){
					maxValue = value;
				}
				// add the value to the current vector
				vector[i] = value;
			}
			// add the vector to the list of vectors
			vectors.add(new DoubleArray(vector));
		}
		// close the file
		reader.close();

		// Create a list of clusters
		clusters = new ArrayList<Cluster>();
		
		// Get the size of vectors
		int vectorsSize = vectors.get(0).data.length;
		
		// SPECIAL CASE: If only one vector
		if (vectors.size() == 1) {
			// Create a single cluster and return it 
			DoubleArray vector = vectors.get(0);
			Cluster cluster = new Cluster(vectorsSize);
			cluster.addVector(vector);
			clusters.add(cluster);
                        return clusters;
		}
		
		// (1) Randomly generate k empty clusters with a random mean (cluster
		// center)
		for(int i=0; i< k; i++){
			DoubleArray meanVector = generateRandomVector(minValue, maxValue, vectorsSize);
			Cluster cluster = new Cluster(vectorsSize);
			cluster.setMean(meanVector);
                        clusters.add(cluster);
                        
                        
		}

		// (2) Repeat the two next steps until the assignment hasn't changed
		boolean changed;
		while(true) {
			iterationCount++;
			changed = false;
			// (2.1) Assign each point to the nearest cluster center.

			// / for each vector
			for (DoubleArray vector : vectors) {
				// find the nearest cluster and the cluster containing the item
				Cluster nearestCluster = null;
				Cluster containingCluster = null;
				double distanceToNearestCluster = Double.MAX_VALUE;

				// for each cluster
				for (Cluster cluster : clusters) {
					// calculate the distance of the cluster mean to the vector
					double distance = euclideanDistance(cluster.getmean(), vector);
					// if it is the smallest distance until now, record this cluster
					// and the distance
					if (distance < distanceToNearestCluster) {
						nearestCluster = cluster;
						distanceToNearestCluster = distance;
                                                
                                                
					}
                                        
					// if the cluster contain the vector already,
					// remember that too!
					if (cluster.contains(vector)) {
						containingCluster = cluster;
                                                
                                                
					}
				}
                                

				// if the nearest cluster is not the cluster containing
				// the vector
				if (containingCluster != nearestCluster) {
					// remove the vector from the containing cluster
					if (containingCluster != null) {
                                            containingCluster.remove(vector);
					}
					// add the vector to the nearest cluster
					nearestCluster.addVector(vector);
                                        changed = true;
				}
			}

			// check the memory usage
			MemoryLogger.getInstance().checkMemory();
			
			if(!changed){     // exit condition for main loop
				break;
			}
			
			// (2.2) Recompute the new cluster means
			for (Cluster cluster : clusters) {
				cluster.recomputeClusterMean();
                                cluster.centroide(nombre);
                                //System.out.println(cluster); //------------------------------------------------
			}
		} 

		// check memory usage
		MemoryLogger.getInstance().checkMemory();
		
		// record end time
		endTimestamp =  System.currentTimeMillis();
		
		// return the clusters
		return clusters;
	}
        
        
        public void Match(String name,boolean nuevo) throws FileNotFoundException, IOException{
            
            
        String original= name.substring(3);
        
            
        String ruta1= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Centroides/"+name+"";
        File archivo = new File(ruta1);
        BufferedReader Flee= new BufferedReader(new FileReader(archivo));  
        String Slinea,Slinea2,key1="",key2="";  
        
        while((Slinea=Flee.readLine())!=null) {  
        key1=Slinea;        
        }
        Flee.close();
        
        
        if(nuevo==false){
            
        //String ruta2= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Centroides/"+name+"";
            String ruta2= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Centroides/"+name+"";
        File archivo2 = new File(ruta2);
        BufferedReader Flee2= new BufferedReader(new FileReader(archivo2)); 
        while((Slinea2=Flee2.readLine())!=null) { 
        key2=Slinea2;
        }
        Flee2.close();
        }
        
        
        System.out.println("KEY 1: "+key1);
        System.out.println("KEY 2: "+key2);
        System.out.println("==========================================================================================");
        System.out.println("Nota: Recordar eliminar el archivo generado en la carpeta comparar,cuando valide el acceso");
        System.out.println("==========================================================================================");
        
        }

	/**
	 * Generate a random vector.
	 * @param minValue  the minimum value allowed
	 * @param maxValue  the maximum value allowed
	 * @param vectorsSize the desired vector size
	 * @return the random vector
	 */
	private DoubleArray generateRandomVector(double minValue, double maxValue,
			int vectorsSize) {
		// create a new vector
		double[] vector = new double[vectorsSize];
		// for each position generate a random number
		for(int i=0; i < vectorsSize; i++){
			vector[i] = (random.nextDouble() * (maxValue - minValue)) + minValue;
		}
		// return the vector
		return new DoubleArray(vector);
	}

	/**
	 * Calculate the eucledian distance between two vectors of doubles.
	 * @param vector1 the first vector
	 * @param vector2 the second vector
	 * @return the distance
	 */
	private double euclideanDistance(DoubleArray vector1, DoubleArray vector2) {
		double sum =0;	
                
                pos++;
		for(int i=0; i< vector1.data.length; i++){
			sum += Math.pow(vector1.data[i] - vector2.data[i], 2);
                        
		}
                if(pos>3){
                distancias[p]=Math.sqrt(sum);
                p++;
                }
                
                System.out.println("--> "+Math.sqrt(sum)+" <-- posicion: "+pos); 
		return Math.sqrt(sum);
	}
	
	/**
	 * Print statistics of the latest execution to System.out.
	 */
	public void printStatistics(String name) {
            
		System.out.println("Cluster 1: "+contador2);
                System.out.println("Cluster 2: "+contador);
                Escribir(name);
                
                
               
	}

        public void Escribir(String nombre){
String ruta= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Archivos/distancias/"+nombre+"";
File f;
FileWriter w;
BufferedWriter bw;
PrintWriter wr;

    try {
f= new File(nombre);    
w= new FileWriter(ruta);
bw= new BufferedWriter(w);
wr= new PrintWriter(bw);



//cadena=Persona+cadena+Name;
for(int i=0;i<distancias.length;i++){
wr.write(distancias[i]+"\r\n");
}



wr.close();
bw.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error en: "+e);
    }
JOptionPane.showMessageDialog(null, "done!");
System.exit(0);
}
        
        
	/**
	 * Save the clusters to an output file
	 * @param output the output file path
	 * @throws IOException exception if there is some writing error.
	 */
	public void saveToFile(String output) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		// for each cluster
		for(int i=0; i< clusters.size(); i++){
			// if the cluster is not empty
			if(clusters.get(i).getVectors().size() >= 1){
				// write the cluster
				writer.write(clusters.get(i).toString()+"#");
                                contador=clusters.get(i).getVectors().size();
				// if not the last cluster, add a line return
				if(i < clusters.size()-1){
					writer.newLine();
                                        contador2=clusters.get(i).getVectors().size();
				}
			}
		}
		// close the file
		writer.close();
               
	}
        
}
