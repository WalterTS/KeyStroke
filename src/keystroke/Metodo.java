/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keystroke;

/**
 *
 * @author Walter
 */
import KMeans.MainTestKMeans_saveToFile;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Metodo {
    
    


ArrayList list = new ArrayList();
String Pegar[]= new String[100];
int contador=0;
String Slinea2,elemento;
String ruta="";
int usuario=1;
int veces=0;
boolean nuevo=false;
boolean third=false;
boolean second=false;

public void Tercero(String Persona,String nombre, String cadena,String Name,boolean exist) throws IOException{
third=true;
second=false;
String p1=Persona;
String n1=nombre;
String cad=cadena;
String n2=Name;
boolean sirol=exist;
Editar(p1,n1,cad,n2,sirol);
}

public void Segundo(String Persona,String nombre, String cadena,String Name,boolean exist) throws IOException{
second=true;
String p1=Persona;
String n1=nombre;
String cad=cadena;
String n2=Name;
boolean sirol=exist;
Editar(p1,n1,cad,n2,sirol);
}



    
public void Editar(String Persona,String nombre, String cadena,String Name,boolean exist) throws IOException{

veces++;
/*veces(Name);    
int check=Integer.parseInt(veces);

if(check<3){
ActualizarVeces(Name);    
}*/
    
/*if(exist){

//MainTestKMeans_saveToFile.main(Pegar);
nombre="NEW"+nombre;  
nuevo=false;
ruta= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Comparar/"+nombre+"";
}else{*/
ruta= "C:/Users/Walter/Documents/NetBeansProjects/KeyStroke/Usuarios/"+nombre+"";
nuevo=false;
//}

File archivo = new File(ruta);
        BufferedWriter bw;
        
        if(archivo.exists()) {
                              
          BufferedReader Flee= new BufferedReader(new FileReader(archivo));  
           String Slinea;  
           System.out.println("**********Leyendo Fichero***********");  
           
           while((Slinea=Flee.readLine())!=null) {  
               
           System.out.println(Slinea);  
           
           
           Slinea2=Slinea;
           if(Slinea2==null){
 
           }else{
           Pegar[contador]=Slinea2;
           
           contador++;    
           }
           
           }  
           System.out.println("*********Fin Leer Fichero**********");  
            
           Flee.close();
    
               
                
              
           //Slinea2=Slinea2+" | "+cadena;
           System.out.println("Valor del contador SIN aumento: "+contador);
           contador++;
           System.out.println("Valor del contador CON aumento: "+contador);
           //Slinea2=Persona+cadena+Name; 
           Slinea2=cadena;
           Pegar[contador]=Slinea2;    
             
     /* for(int i=0;i<Pegar.length;i++){
                System.out.println(Pegar[i]);                
            }*/
                
         
            
           bw = new BufferedWriter(new FileWriter(archivo));
          
         
           
           	
           
           for(int i = 0; i<Pegar.length;i++){
               if(Pegar[i]==null){
                   
               }else{
bw.write(Pegar[i]+"\r\n");                   
               }

} 
           
           //bw.write(Slinea2);
           // System.out.println("Contenido final: "+Slinea2);

            bw.close(); 
            if(third==false){
            OpenClose op2= new OpenClose();
            op2.openOne();
            
            }
            
            
            
            
        } else {
            
            Escribir(Persona,nombre, cadena,Name,ruta);
            
        }

     
       if(third==true){
       //MainTestKMeans_saveToFile km= new MainTestKMeans_saveToFile(nombre,nuevo);
       Lectura lectura= new Lectura();
       lectura.leer(nombre);
       //System.out.println("Nombre: "+nombre);
       
       }
    
    
}    
    
    
    
public void Escribir(String Persona,String nombre, String cadena,String Name,String ruta){

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
wr.write(cadena);


wr.close();
bw.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error en: "+e);
    }
    
   OpenClose oc= new OpenClose();
   oc.opentwo();
   
    
    //JOptionPane.showMessageDialog(null, "Vuelva a escribir sus datos porfavor");
    
    
    
    //KeyStroke.main(Pegar);
    
 
    
}
}    
    
    


