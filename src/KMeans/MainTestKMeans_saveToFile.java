package KMeans;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import KMeans.AlgoKMeans;

/**
 *  Example of how to use the KMEans algorithm, in source code.
 */
public class MainTestKMeans_saveToFile {
    String Pegar[]= new String[100];
    static String name;
    static int contador=0;
    static boolean nuevo1;
    
    
    
    public MainTestKMeans_saveToFile(String nombre, boolean nuevo) throws IOException{
    name=nombre;
        
    nuevo1=nuevo;
    
    System.out.println("Nombre: "+name);
    
    MainTestKMeans_saveToFile.main(Pegar);
    
    
    }
	
	public static void main(String []args) throws NumberFormatException, IOException{
		System.out.println("ENTRÓ AL MAIN ");
                String input="";
                //name="walter3.txt";
                nuevo1=true;
		//String input = fileToPath("configKmeans.txt");
                //String input = "Usuarios/"+name;
                if(nuevo1==true){
                input = "Usuarios/"+name;
                }else{
                input = "Comparar/"+name;
                }
                
              
               String output = "Archivos/salida.txt";
               
		int k=1;
		
		// Apply the algorithm
		AlgoKMeans algoKMeans = new AlgoKMeans();  // we request 3 clusters
		algoKMeans.runAlgorithm(input, k, name);
		
		algoKMeans.saveToFile(output);
                algoKMeans.printStatistics(name);
                algoKMeans.Match(name,nuevo1);

	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestKMeans_saveToFile.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
	
	
}
