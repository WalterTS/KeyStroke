/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keystroke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Walter
 */
public class Lectura {
String Slinea; 
String cadenas;
String uno[],dos[],tres[];
ArrayList cent = new ArrayList();

public void leer(String nombre) throws FileNotFoundException, IOException{
    String ruta="C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Usuarios/"+nombre;
    String codigo;
    ArrayList numeros = new ArrayList();
    File archivo = new File(ruta);
        BufferedWriter bw;
        
        if(archivo.exists()) {                              
            BufferedReader Flee= new BufferedReader(new FileReader(archivo));                                   
                while((Slinea=Flee.readLine())!=null) {                        
                    numeros.add(Slinea);                      
                }                   
        }       
       
      String primero= (String) numeros.get(0);
      String segundo= (String) numeros.get(1);
      String tercero= (String) numeros.get(2);
      
      uno = primero.split(",");
      dos = segundo.split(",");
      tres = tercero.split(",");
       for(int x=0;x<uno.length;x++){
       
       }
       
       Distancias();
    
}

public void Distancias(){
    
    float v1,d1,dist1=0;
        for(int j=0;j<uno.length;j++){                                    
    v1= Float.parseFloat(dos[j]) - Float.parseFloat(uno[j]);                
    d1= (float) Math.pow(v1, 2);                
    dist1=d1+dist1;
                
        }                       
            //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            //-* ================== Segunda linea =========================== -*
            //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    
float v2,d2,dist2=0;
        for(int j=0;j<dos.length;j++){                    
    v2= Float.parseFloat(tres[j]) - Float.parseFloat(dos[j]);                
    d2= (float) Math.pow(v2, 2);                
    dist2=d2+dist2;                
        }

            //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            //-* ================== Tercera linea =========================== -*
            //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*    
    
    
    float v3,d3,dist3=0;
        for(int j=0;j<tres.length;j++){                                    
    v3= Float.parseFloat(tres[j]) - Float.parseFloat(uno[j]);                
    d3= (float) Math.pow(v3, 2);                
    dist3=d3+dist3;                
        }
        
    dist1=(float) Math.sqrt(dist1);
    dist2=(float) Math.sqrt(dist2);
    dist3=(float) Math.sqrt(dist3);
    System.out.println("\n\n\n");
    System.out.println("P1 -> : "+dist1); //2 y 1
    System.out.println("P2 -> : "+dist2); //3 y 2
    System.out.println("P3 -> : "+dist3);   //3 y 1
      

    Centroide();
}


public void Centroide(){
                    
                //Llenado de las posiciones del centroide
float v1,d1,dist1=0;
        for(int j=0;j<uno.length;j++){               
    v1= Float.parseFloat(uno[j]) + Float.parseFloat(dos[j]) + Float.parseFloat(tres[j]);
    v1=v1/3;
    cent.add(v1);    
        }    
        
        
                //Creación del valor del centroide
float val1,val2,sum,potencia,dist2=0;
        for(int j=0;j<uno.length;j++){                                  
    val1= Float.parseFloat(dos[j]);
    val2=(float) cent.get(j);
    sum=val1-val2;               
    potencia= (float) Math.pow(sum, 2);                
    dist2=potencia+dist2;                
        }
        
        float Raizz= (float) Math.sqrt(dist2);        
        
        
    
            //Imprimir centroide        
System.out.println("\n Centroide: ");
 for(int k=0;k<cent.size();k++){
 System.out.print(cent.get(k)+",");
 }
 System.out.println("\n\n Valor del Centroide:  "+Raizz);
 
 JOptionPane.showMessageDialog(null, "Done!");
 System.exit(0);
}


}




