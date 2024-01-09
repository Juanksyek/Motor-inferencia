import java.util.Scanner;
public class PruebaInferencia
{
   public static void main(String args[])
   {
      Scanner leer = new Scanner(System.in);
      Metodos met = new Metodos();
      int opc=0; //[x]Guarda el numero de reglas
      char aux[];
      String BH = "", MT="";//Base de hechos inicial (BH) y Obtetivo meta(MT)   
      //Ingreso del Sistema de producci�n
      met.entraData();    
     //Ingreso de la base de hechos incial
      System.out.println("Ingrese la base de hechos inicial [Ejemplo: AB = {A,B}]");
      BH = leer.nextLine();   
     //Ingreso del objetivo Meta
      System.out.println("Ingrese el objetivo meta");
      MT = leer.nextLine();     
      do{
         System.out.println("\n Menu Motor de Inferencia \n");
         System.out.println("Ingrese una opcion");
         System.out.println("[1] Encadenamiento hacia adelante");
         System.out.println("[2] Encadenamiento hacia atras");    
         System.out.println("[3] Editar sistema");
         System.out.println("[4] Salir");  
         System.out.print("Que desea hacer?: ");
         opc = leer.nextInt();
         leer.nextLine();
         switch(opc){
            case 1:
               System.out.println("Encadenamiento hacia adelante\n");      
               met.adelante(BH,MT);
               break;            
            case 2:
               System.out.println("\nEncadenamiento hacia atras\n");      
               met.atras(BH,MT);
               break;           
            case 3:
               System.out.println("\nEdicion de sistema\n");
               System.out.println("Ingrese una opcion");
               System.out.println("[1] Cambiar todo el sistema de produccion");
               System.out.println("[2] Cambiar la base de hechos iniciales");    
               System.out.println("[3] Cambiar el objetivo meta");
               System.out.print("Que desea hacer?: ");
               opc = leer.nextInt();
               leer.nextLine();
               switch(opc){
                  case 1:
                     System.out.println("\nSistema de produccion actual");
                     met.mostrarSist();
                     /////////////////////
                     System.out.println("Ingrese el nuevo sistema de produccion \n");
                     met.entraData();
                     /////////////////////
                     System.out.println("\nBase de hechos iniciales actual");
                     System.out.println(BH);
                     System.out.println("Ingrese la nueva base de hechos");
                     BH = leer.nextLine();
                     /////////////////////
                     System.out.println("\nObjetivo meta actual");
                     System.out.println(MT);
                     System.out.println("Ingrese el nuevo Objetivo meta");
                     MT = leer.nextLine();
                     break;                      
                  case 2:
                     System.out.println("\nBase de hechos iniciales actual");
                     System.out.println(BH);
                     System.out.println("Ingrese la nueva base de hechos");
                     BH = leer.nextLine();
                     break;                       
                  case 3:
                     System.out.println("\nObjetivo Meta Actual");
                     System.out.println(MT);
                     System.out.println("Ingrese el nuevo Objetivo Meta");
                     MT = leer.nextLine();
                     break;    
               }
               break;           
            case 4:
               System.out.println("Adios... :)");
               break;
         }
      } while(opc != 4);
   } 
}