import java.util.Scanner;
public class Metodos
{
   Scanner leer = new Scanner(System.in);
   String R[],R1[], C1[], T2[];
   int x, toc=0; //[x]Guarda el numero de reglas
   int sm = 0; //Auxiliar del metodo seleccionar meta
   int axu[]; boolean nan=false; //Auxiliares para mostrar las reglas
   char aux[];
   String reglas=""; 
   public void entraData()
   {    
      System.out.println("Ingrese el numero de reglas que tendra su sistema de produccion");
      x = leer.nextInt();
      R= new  String [x];
      R1= new  String [x];
      T2= new  String [x];
      C1= new  String [x];
      axu = new int[x];
      leer.nextLine();
      System.out.println("Ingrese su sistema de produccion [Por ejemplo: A|B=C Sustituye a lo siguiente A y B -> C]");
      for(int i=0; i < x; i++)
      {
         R[i] = leer.nextLine();
         R1[i] = "";
         T2[i] = "";
         C1[i] = "";  
      }
      for(int i=0; i < x; i++)
      {
         int j=0;
         do{
            if(R[i].charAt(j) != '|')
            {
               R1[i] =R1[i]+ R[i].charAt(j);
               T2[i] =T2[i]+ R[i].charAt(j);
            }
            j++;
         }while(R[i].charAt(j) != '=');
         
         if(R[i].charAt(j) == '=')
         {
            j++;
            C1[i] ="" + R[i].charAt(j);
         }
      }
   }
   //Presentacion de datos---Sistema
   public void mostrarSist()
   {
      int cont = 1;
      for(int i=0; i < x; i++,cont++)
         System.out.println("R"+cont+": "+R[i]);
   } 
   public void tablaData(String BH, String Meta, int r2, String CC[])
   {
      int cont=r2+1;
      for(int i=1; i <= CC.length; i++)
         if(CC[i-1] != null)
         {
            int cont2=0;
            System.out.println("R"+i+" ");
            if(nan)
            {
               for(int j=0; j < x; j++)
                  if(axu[j] != i)
                     cont2++;
               if( cont2 == x)
                  reglas+="R"+i+", ";
               axu[i-1]=i;
            }
            else
               nan = true;
         }
      if(toc==0)
      {
         System.out.print("         "+"         "+Meta+"       "+"         "+BH);
         toc++;  
      }
      else
         System.out.print("        "+C1[r2]+"        "+Meta+"        "+"R"+cont+"        "+BH);
      System.out.print("\n----------------------------------------------------------");
      System.out.println();
   }
   //Encadenamiento Hacia Adelante
   public void adelante(String BH, String Meta)
   {
      System.out.println("Sistema de produccion");
      mostrarSist();
      System.out.println("\nHechos iniciales= "+BH);
      System.out.println("Objetivo Meta= "+Meta);
      String CC[]=pruebaCC(BH);
      String CC3[];
      String CC2[]=limpiarCC(CC);
      String r,NH=" ";
      int r2=0;
      System.out.print("CC       "+"NM       "+"Meta        "+"R        "+"BH       ");
      System.out.print("\n**********************************************************");
      System.out.println("");
      while(!noVacio(CC) && !noContenida(Meta,BH))
      {
         tablaData(BH,Meta,r2,CC);
         r = resolver(CC2);
         r2 = resolver2(r);
         CC3=CC;
         CC=eliminar(r,CC2);
         NH = aplicar(r2,BH);
         BH = NH;
         if(!noContenida(Meta,BH))
         {
            CC=pruebaCC(BH);
            CC2=limpiarCC(CC);
         }
         else if(noContenida(Meta,BH))
         {
            tablaData(BH,Meta,r2,CC3);
            System.out.println("\nSistema exito");
            System.out.println("Reglas Usadas: "+reglas);
            reglas="";
            axu= new int [x];
            for(int i=0; i < x; i++)
               R1[i]=T2[i];  
            toc=0;
         }
         else if(noVacio(CC))
         {
            System.out.println("\n Sistema con fracaso");
            reglas="";
            axu= new int [x];
            for(int i=0; i < x; i++)
               R1[i]=T2[i]; 
            toc=0;
         }
      }
   }
   //Encadenamiento Hacia Atras
   public void atras(String BH, String Meta)
   {
      System.out.println("Sistema de produccion");
      mostrarSist();
      System.out.println("\nHechos Iniciales= "+BH);
      System.out.println("Objetivo Meta= "+Meta);
      System.out.print("CC       "+"NM       "+"Meta        "+"R        "+"BH       ");
      System.out.print("\n**********************************************************");
      System.out.println("");
      if(verificar(BH, Meta,""))
      {
         System.out.println("Sistema exitoso");
         System.out.println("Reglas Usadas: "+reglas);
         reglas="";
         axu= new int [x];
         R1=T2;  
         toc=0;
      }
      else
      {
         System.out.println("Sistema con fracaso");
         System.out.println("Reglas utilizadas: "+reglas);
         reglas="";
         axu= new int [x];
         R1=T2;  
         toc=0;
      }
   }
   //Verificar Encadenamiento Hacia Atras
   public boolean verificar(String BH, String Meta, String NH)
   {
      boolean verificado = false;
      String r;
      int r2=0;
      if(noContenida(Meta, BH))
         return true;
      else
      {
         String CC[]=ccAtras(Meta);
         String CC3[];
         String CC2[]=limpiarCC(CC);
         while(!noVacio(CC) && !verificado)
         {
            tablaData(BH,Meta,r2,CC);
            r = resolver(CC2);
            r2 = resolver2(r);
            CC3=CC;
            CC=eliminar(r,CC2);
            NH = extraerAntecedentes(r,BH,NH);
            verificado= true;
            while(!noVacio2(NH) && verificado)
            {
               Meta = seleccionarMeta(NH,Meta);
               BH = actualizarBH(BH,NH,Meta);
               NH = eliminar2(Meta,NH);
               verificado=verificar(BH,Meta,NH);
               if(verificado)
                  BH = BH + Meta.charAt(0);
            }
         }
         return verificado;  
      }
   }
   //Actualizar BH
   public String actualizarBH(String BH, String NH, String Meta)
   {
      int i=0;
      String aux="";
      while(i < NH.length())
      {
         int j=0; boolean lon = false;
         while(j < Meta.length())
         {                                      
            if(NH.charAt(i) == Meta.charAt(j))
               lon=true;
            
            if(lon)
            {
               aux+=NH.charAt(i);
               j=Meta.length();
            }
            j++;
         }
         i++;
      }
      BH+= aux;
      return BH;
   }   
 //Obtener CC encaAtras
   public String [] ccAtras(String Meta)
   {
      String CX[] = new String[C1.length];
      
      for(int i=0; i < C1.length; i++)
         for(int j=0; j < Meta.length(); j++)
            if(C1[i].charAt(0) == Meta.charAt(j))
               CX[i] = R1[i];
      
      return CX;
   } 
   //Eliminar la meta actual de la variable NM. 
   public String eliminar2(String Meta, String NH)
   {
      int i=0;
      String aux="";
      while(i < NH.length())
      {
         int j=0, lon=0;
         while(j < Meta.length())
         {                                      
            if(NH.charAt(i) != Meta.charAt(j))
               lon++;
            
            if(lon == Meta.length())
               aux+=NH.charAt(i);
            j++;
         }
         i++;
      }
      NH = aux;
      return NH;
   }
   //Se asigna a la nueva meta contenida en NM a la variable "Meta"
   public String seleccionarMeta(String NM, String Meta)
   {
      Meta = Meta + NM.charAt(0);
      return Meta;
   }
   //Verificar si NM esta vacio
   public boolean noVacio2(String NM)
   {
      if(NM == "")
         return true;
      else
         return false;
   }
   //Extraer Antecedentes
   public String extraerAntecedentes(String r, String BH, String NH)
   {
      String aux=NH;
      boolean ban=false, ban2=false;
      for(int i=0; i < r.length(); i++)
      {
         for(int j=0; j < BH.length(); j++)
         {
            if(BH.charAt(j) != r.charAt(i))
               ban = true;
            else
            {
               ban = false;
               j=BH.length();
            }
         }
         if(ban)
            aux =aux + r.charAt(i);
      }
      return aux;
   }
   //Sacar el Nuevo Hecho
   public String aplicar(int r2, String BH)
   {
      BH = BH + C1[r2];
      return BH;
   }
   //Eliminar la regla elegida del Conjunto
   public String [] eliminar(String r, String CC[])
   {
      int i=0, aux=CC.length;
      while(i < aux && CC[i] != null) 
      {
         if(CC[i].equals(r))
            CC[i]=null;
         i++;
      }
      //Eliminar de R1
      i=0; aux=R1.length;
      while(i < aux) 
      {
         if(R1[i] != null)
         {
            if(R1[i].equals(r))
               R1[i]=null;
            i++;
         }
         
         else
            i++;
      }
      return CC;
   }
   //Resolver Conjunto problema
   public String resolver(String CC2[])
   {
      int n=CC2[0].length(), m=0;
      String regla=CC2[0];
      for(int i=0; i < CC2.length; i++)
      {
         if(CC2[i] != null && i>0)
         {
            m=CC2[i].length();
            if(n > m)
            {
               n=m;
               regla=CC2[i];
            }
         }
      }
      return regla;
   }
   //Resolver obtener Nueva meta
   public int resolver2(String r)
   {
      int aux=0;
      for(int i=0; i < R1.length; i++)
         if(R1[i] != null)
            if(R1[i].equals(r))
               aux = i;
      return aux;
   }
   //Ver si La meta ya esta en BH
   public boolean noContenida(String Meta, String BH)
   {
      boolean ban=false;
      for(int i=0; i < BH.length(); i++)
         if(BH.charAt(i) == Meta.charAt(0))
         {
            ban=true;
            return ban;
         }
      return ban;
   }
   //Ver si CC no esta vacia
   public boolean noVacio(String CC2[])
   {
      boolean vacio = true;
      for(int i=0; i < CC2.length; i++)
         if(CC2[i] != null)
            vacio = false;
      return vacio;
   }
   //Obtener CC EncaAdelante
   public String[] pruebaCC(String BH)
   {
      int aux, aux2, cont; 
      boolean listo=false, pruebaCC=false, fallo,salir, ban;
      String CC[] = new String [x];//Conjunto Conflicto
      for(int i=0; i < x; i++)
      {
         if(R1[i] != null)
         {
            cont=0; aux2=BH.length();// obtener la longitud de BH
            for(int j=0; j<R1[i].length(); j++)
            {
               aux=0;
               do{
                  salir=false;ban = false; listo = false; pruebaCC=false; fallo=false;
                  if(aux < aux2)
                     if(R1[i].charAt(j) == BH.charAt(aux))
                     {
                        pruebaCC=true;
                        ban = true;
                        cont++;
                     }
                     else
                        pruebaCC=false;
                  if(!pruebaCC)
                  {
                     fallo = true;
                     if(aux2-1 == aux)
                     {
                        salir = true;
                     }
                  }
                  else if(pruebaCC == true && cont == R1[i].length())
                     listo = true;
                  aux++;
               }while(listo != true && ban == false && salir == false);
            }
            if(listo && pruebaCC)
            {
               CC[i]= R1[i];
            }
         }    
      }
      return CC;
   }
   public String [] limpiarCC(String CC[])
   {
      int cont=0;
      String CC2[] = new String [CC.length];
      for(int i=0; i < CC.length; i++)
         if(CC[i] != null)
         {
            CC2[cont] = CC[i];
            cont++;
         }
      return CC2;
   }
}