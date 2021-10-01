/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller0_araya_ibacache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author aaraya_dibacache
 */
public class Taller0_Araya_Ibacache {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        //extras
        String [] nombreAJornadas = new String[]{"M","T"};
        
        //clientes
        int clientes = 100;
        String [] nombresC = new String[clientes];
        String [] apellidosC = new String[clientes];
        String [] rutsC = new String[clientes];
        String [] clavesC = new String[clientes];
        int [] saldosC = new int[clientes];
        int [] estadosC = new int[clientes];
        
        //peliculas
        int peliculas = 6;
        String [] nombresP = new String[peliculas];
        String [] tiposP = new String[peliculas];
        int [] recaudacionP = new int[peliculas];
        int [] recaudacionPDiaria = new int[peliculas];
        
        //salas
        int [][] sala1 = new int[peliculas][2];
        int [][] sala2 = new int[peliculas][2];
        int [][] sala3 = new int[peliculas][2];
        
        //asientos
        int [][] sala1M = new int[10][30];
        int [][] sala1T = new int[10][30];
        int [][] sala2M = new int[10][30];
        int [][] sala2T = new int[10][30];
        int [][] sala3M = new int[10][30];
        int [][] sala3T = new int[10][30];
        rellenarMatriz(sala1M,sala1T,sala2M,sala2T,sala3M,sala3T);
        System.out.println(mostrarMatrizAsientos(sala2M, "2", "M"));
        
        //Lectura
        int cantidadClientes = lecturaDatosClientes(nombresC,apellidosC,rutsC,clavesC,saldosC,estadosC);
        //int cantidadPeliculas = lecturaDatosPeliculas(nombreAJornadas,nombresP,tiposP,recaudacionP,sala1,sala2,sala3);
        int cantidadPeliculas = leerDatosPeliculas(nombresP,recaudacionPDiaria, tiposP, recaudacionP, sala1,sala2, sala3);
        menuPrincipal(recaudacionP,cantidadPeliculas,nombresC,apellidosC,rutsC,clavesC,saldosC,estadosC,cantidadClientes,nombreAJornadas,nombresP,tiposP,recaudacionP,recaudacionPDiaria,sala1,sala2,sala3,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T);
    }
    
    private static String mostrarMatriz(int[][] matriz, String sala, String horario) {
        String text = "";
        text += "SALA "+sala+" - "+horario+"\n\n";
        for (int fila = 0; fila < matriz.length; fila++){
          for (int columna = 0; columna < matriz[fila].length; columna++){
                if (matriz[fila][columna] == 0 || matriz[fila][columna] == 1 ) {
                        text += matriz[fila][columna]+"  ";
                } else {
                        text += matriz[fila][columna]+" ";
                }
          }
          text += "\n";
        }

        return text;
    }
    
    private static String mostrarMatrizAsientos(int[][] matriz, String sala, String horario) {
        String[] filas = new String[]{"A","B","C","D","E","F","G","H","I","J"};
        String text = "";
        text += "SALA "+sala+" - "+horario+"\n\n";
        String text2="     ";
        for (int i = 0; i < 30; i++) {
            if (i<=8){
                text2+= (i+1)+"  ";
            }
            else{
                text2+= (i+1)+" ";
            }
        }
        text+=text2+"\n\n";
        text+=filas[0]+"    ";
        for (int fila = 0; fila < matriz.length; fila++){
            
          for (int columna = 0; columna < matriz[fila].length; columna++){
                if (matriz[fila][columna] == 0 || matriz[fila][columna] == 1 ) {
                        text += matriz[fila][columna]+"  ";
                } else {
                        text += matriz[fila][columna]+" ";
                }
          }
          if (fila==matriz.length-1){
              text += "\n ";
          }
          else{
              text += "\n"+filas[fila+1]+"    ";
          }
          
        }

        return text;
    }
    
    /**
     * This method fill the void position on the movie room with a -2.
     * @param sala1
     * @param sala2
     * @param sala3
     * @param sala4
     * @param sala5
     * @param sala6 
     */
    private static void rellenarMatriz(int[][] sala1,int[][] sala2,int[][] sala3,int[][] sala4,int[][] sala5,int[][] sala6) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 30; j++) {
                if ((i>=0 && i<=3) && ( (j>=0 && j<=4) || (j>=25 && j<=29)) ){
                    sala1[i][j] = -2;
                    sala2[i][j] = -2;
                    sala3[i][j] = -2;
                    sala4[i][j] = -2;
                    sala5[i][j] = -2;
                    sala6[i][j] = -2;
                }
            }
        }
    }
    /**
     * This method reads the files clientes and status for fill the initial vectors related to clients.
     * @param nombresC
     * @param apellidosC
     * @param rutsC
     * @param clavesC
     * @param saldosC
     * @param estadosC
     * @throws FileNotFoundException 
     */
    private static int lecturaDatosClientes(String[] nombresC, String[] apellidosC, String[] rutsC, String[] clavesC, int[] saldosC, int[] estadosC) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("clientes.txt"));
        int i = 0;
        while(scan.hasNext()){
            String linea = scan.nextLine();
            String [] partes = linea.split(",");
            nombresC[i]=partes[0];
            apellidosC[i]=partes[1];
            rutsC[i]=formatearRut(partes[2]);
            clavesC[i]=partes[3];
            saldosC[i]=Integer.parseInt(partes[4]);
            i++;
        }
        scan.close();
        Scanner scan1 = new Scanner(new File("status.txt"));
        while (scan1.hasNext()) {            
            String linea = scan1.nextLine();
            String [] partes = linea.split(",");
            String rut = partes[0];
            String rutFormateado = formatearRut(rut);
            int pos = buscarRut(rutsC,rutFormateado,i);
            if (pos != -1){
                if (partes[1].equals("HABILITADO")){
                    estadosC[pos]=1;
                }
            }
            else{
                System.out.println(rut+" no existe en el sistema.");
            }
        }
        scan1.close();
        return i;
    }
    /**
     * This function returns ruts index value on ruts vector
     * @param rutsC
     * @param rut
     * @return 
     */
    private static int buscarRut(String[] rutsC, String rut, int cantidadClientes) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (rutsC[i].equals(rut)){
                return i;
            }
        }
        return -1;
    }

    
    private static int leerDatosPeliculas(String[] nombreP,int [] recaudacionPDiaria, String[] tipoP, int[] recaudadoP, int[][] sala1, int[][] sala2, int[][] sala3) throws IOException{
        int contador = 0;
        int columna;
        File file = new File("peliculas.txt");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String linea = scan.nextLine();
            String [] partes = linea.split(",");
            nombreP[contador] = partes[0];
            tipoP[contador] = partes[1];
            recaudadoP[contador] = Integer.parseInt(partes[2]);
            for (int i = 3; i < partes.length; i+= 2) {
            	String horario = partes[i+1];
            	if (horario.equalsIgnoreCase("M")) {
                    //mañana
                    columna = 0;
                    }
            	else {
                    //tarde
                    columna = 1;
                }
            	int numSala = Integer.parseInt(partes[i]);
            	if (numSala == 1) {
                     //sala1
                    sala1[contador][columna] = 1;
                }else {
                    //sala2
                    if (numSala == 2) {
                            sala2[contador][columna] = 1;
                    } else {
                            //sala3
                            sala3[contador][columna] = 1;
                    }
                }
            }
            contador ++;
        }
        scan.close();
        System.out.println(mostrarMatriz(sala1, "1", "CARTELERA"));
        System.out.println(mostrarMatriz(sala2, "2", "CARTELERA"));
        System.out.println(mostrarMatriz(sala3, "3", "CARTELERA"));
        return contador;
    }
    
    /**
     * This method returns the schedule index value on schedule vector names 
     * @param nombreAJornadas
     * @param horario
     * @return 
     */
    private static int buscarJornada(String[] nombreAJornadas, String horario) {
        for (int i = 0; i < 2; i++) {
            if (nombreAJornadas[i].equals(horario)){
                return i;
            }
        }
        return -1;
    }
    /**
     * Register method
     * @param nombresC
     * @param apellidosC
     * @param rutsC
     * @param clavesC
     * @param saldosC
     * @param estadosC
     * @return new count of people
     */
    private static int registro(int cantidadClientes,String[] nombresC, String[] apellidosC, String[] rutsC, String[] clavesC, int[] saldosC, int[] estadosC){
        Scanner scan = new Scanner(System.in);
        System.out.println("--Registro--");
        System.out.println("Ingrese rut: ");
        String rut = scan.nextLine();
        String rutReal = formatearRut(rut);
        rutsC[cantidadClientes]=rutReal;
        System.out.println("Ingrese nombre: ");
        String nombre = scan.nextLine();
        nombresC[cantidadClientes]=nombre;
        System.out.println("Ingrese apellido: ");
        String apellido = scan.nextLine();
        apellidosC[cantidadClientes]=apellido;
        System.out.println("Ingrese clave: ");
        String clave = scan.nextLine();
        clavesC[cantidadClientes]=clave;
        System.out.println("Ingrese saldo: ");
        int saldo = Integer.parseInt(scan.nextLine());
        saldosC[cantidadClientes]=saldo;
        estadosC[cantidadClientes]=1;
        cantidadClientes++;
        System.out.println("Cliente "+rut+" registrado correctamente! Ahora debe loguear.");
        return cantidadClientes;
        
    }
    /**
     * This method breaks any format of the string passed by parameter
     * @param rut
     * @return 
     */
    private static String formatearRut(String rut) {
        String retorno = "";
        String[] partes = rut.split("");
        for (int i = 0; i < partes.length; i++) {
            if (partes[i].equals(".") || partes[i].equals("-")){
                retorno+="";
            }
            else{
                retorno+=partes[i];
            }
        }
        return retorno;
    }
    /**
     * Login of a client. Returns 1 if is a client. Returns 2 if is admin. Returns -1 if user doesn't exists. Returns -2 if the password is incorrect.
     * @param ruts
     * @param claves
     * @return 
     */
    private static String[] login(String[] ruts, String[] claves, int cantidadClientes){
       Scanner scan = new Scanner(System.in);
        System.out.println("--LogIn--");
        System.out.println("Ingrese rut: ");
        String rut = scan.nextLine();
        String rutReal = formatearRut(rut);
        System.out.println("Ingrese clave: ");
        String clave = scan.nextLine();
        if (rutReal.equals("ADMIN") && clave.equals("ADMIN")){
            return new String[]{"2"};
        }
        else{
            int pos = buscarRut(ruts, rutReal,cantidadClientes);
            if (pos==-1){
                //Usuario no existe
                //Dar opcion de registro, relogueo o de salir del sistema.
                return new String[]{"-1"};
            }
            else{
                if (claves[pos].equals(clave)){
                    //Usuario con credenciales correctas!
                    return new String[]{"1",rut};
                }
                else{
                    //Contraseña erronea, volver a logear
                    return new String[]{"-2"};
                }
            }
        }
    }

    private static void menuPrincipal(int[] recaP,int cantidadPeliculas,String[] nombresC, String[] apellidosC, String[] rutsC, String[] clavesC, int[] saldosC, int[] estadosC, int cantidadClientes, String[] nombreAJornadas, String[] nombresP, String[] tiposP, int[] recaudacionP, int [] recaudacionPDiaria, int[][] sala1, int[][] sala2, int[][] sala3, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T) throws IOException {
        System.out.println("Bienvenido!");
        Scanner scan = new Scanner(System.in);
        String[] peliculasDelCliente = new String[1000];
        String[] horarioDelCliente = new String[1000];
        String[] asientosDelCliente = new String[1000];
        int cantidadAsientosComprados = 0;
        String[] credenciales = login(rutsC, clavesC,cantidadClientes);
        while (!credenciales[0].equals("1") && !credenciales[0].equals("2")){
            if (credenciales[0].equals("-1")){
                System.out.println("El rut ingresado no existe en el sistema");
                System.out.println("¿Desea registrarse? [Y/N]");
                String opcion = scan.nextLine();
                if (opcion.equals("Y")){
                    cantidadClientes = registro(cantidadClientes, nombresC, apellidosC, rutsC, clavesC, saldosC, estadosC);
                }
            }
            else{
                System.out.println("Clave incorrecta");
                System.out.println("¿Desea reloguear o salir del sistema? [R/S]");
                String opcion = scan.nextLine();
                if (opcion.equals("S")){
                    System.out.println("Adios!");
                    System.exit(0);
                }
            }
            credenciales = login(rutsC, clavesC,cantidadClientes);
        }
        if (credenciales[0].equals("1")){
            String rutCliente = formatearRut(credenciales[1]);
            int pos = buscarRut(rutsC,rutCliente,cantidadClientes);
            
            
            menuUsuario(clavesC,recaP,rutsC,cantidadPeliculas,cantidadClientes,rutCliente,pos,nombresC,apellidosC,saldosC,estadosC,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados,nombreAJornadas, nombresP, tiposP, recaudacionPDiaria, sala1, sala2, sala3,sala1M,sala1T, sala2M,sala2T,sala3M, sala3T);
        }
        else{
            menuAdmin(clavesC,recaP,rutsC,cantidadPeliculas,nombresP,recaudacionP,recaudacionPDiaria,sala1,sala2,sala3,nombresC,apellidosC,cantidadClientes,saldosC,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados);
        }
    }

    private static void menuUsuario(String[] clavesC,int [] recaP,String[] rutsC,int cantidadPeliculas,int cantidadClientes, String rutCliente, int pos, String[] nombresC, String[] apellidosC, int[] saldosC, int[] estadosC, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados,String[] nombreAJornadas, String[] nombresP, String[] tiposP, int[] recaudacionP, int[][] sala1, int[][] sala2, int[][] sala3, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T) throws IOException {
        Scanner scan = new Scanner(System.in);
        despliegueMenuUsuario();
        System.out.println("Elija una opción: ");
        String opcion = scan.nextLine();
        while(!opcion.equals("5")){
            if (opcion.equals("1")){
                //Comprar entrada
                desplegarPeliculas(cantidadPeliculas,nombresP,tiposP,sala1,sala2,sala3,nombreAJornadas);
                System.out.println("Ingrese nombre de pelicula: ");
                String pelicula = scan.nextLine();
                int posPelicula = buscarPelicula(nombresP,pelicula,cantidadPeliculas);
                if (posPelicula!=-1){
                    System.out.println("Ingrese jornada de la funcion: ");
                    String jornada = scan.nextLine();
                    System.out.println("Ingrese sala de la funcion: [1/2/3]");
                    int sala = Integer.parseInt(scan.nextLine());
                    boolean exito = comprobarFuncion(posPelicula,sala,jornada,sala1,sala2,sala3,nombreAJornadas);
                    if (exito){
                        System.out.println("Ingrese cantidad de entradas: ");
                        int cantEntradas = Integer.parseInt(scan.nextLine());
                        int aPagar = (cantEntradas*valorEntrada(posPelicula,tiposP));
                        if (estadosC[pos]==1){
                            aPagar *= 0.85;
                        }
                        if(aPagar>saldosC[pos]){
                            System.out.println("No puede comprar esa cantidad de entradas en esta película.");
                            System.out.println("¿Desea agregar saldo? [Y/N]");
                            String respuesta = scan.nextLine();
                            if (respuesta.equals("Y")){
                                agregarSaldo(pos,saldosC);
                            }
                        }
                        else{
                            String[] letras = new String[cantEntradas];
                            int [] numeros = new int[cantEntradas];
                            desplegarAsientosDisponibles(sala,jornada,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T);
                            int exitos = 0;
                            for (int i = 0; i < cantEntradas; i++) {
                                System.out.println("Ingrese letra del asiento: ");
                                String letra = scan.nextLine();
                                System.out.println("Ingrese numero del asiento: ");
                                int numero = Integer.parseInt(scan.nextLine());
                                letras[exitos]=letra;
                                numeros[exitos]=numero;
                                exitos++;
                            }
                            if (comprarBoleto(letras,numeros,nombreAJornadas,jornada,posPelicula,sala,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T,peliculasDelCliente,horarioDelCliente,asientosDelCliente, recaudacionP)){
                                System.out.println("Su saldo es: $"+saldosC[pos]);
                                System.out.println("El precio por la compra será: $"+aPagar);
                                System.out.println("¿Está seguro que desea realizar la compra? [Y/N]");
                                String comprar = scan.nextLine();
                                if (comprar.equals("Y")){
                                    realizarCompra(nombresP,cantidadAsientosComprados,letras,numeros,nombreAJornadas,jornada,posPelicula,sala,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T,peliculasDelCliente,horarioDelCliente,asientosDelCliente, recaudacionP);
                                    cantidadAsientosComprados+=cantEntradas;
                                    recaudacionP[posPelicula]+=aPagar;
                                    saldosC[pos]-=aPagar;
                                }
                                else{
                                    System.out.println("Compra cancelada.");
                                }
                            }
                            else{
                                System.out.println("No se pudo comprar el boleto ya que el asiento no se encuentra disponible.");
                            }
                        }
                    }
                    else{
                        System.out.println("La función no existe.");
                    }
                }
                else{
                    System.out.println("La película no existe.");
                }
            }
            if (opcion.equals("2")){
                //Información de usuario
                desplegarInfoUsuario(pos,rutCliente,nombresC,apellidosC,saldosC,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados);
            }
            if (opcion.equals("3")){
                //Devolución de entradas
                int movilidad = estadosC[pos];
                System.out.println("Entradas de: "+rutCliente);
                System.out.println("Usted posee "+cantidadAsientosComprados+" asientos.");
                desplegarEntradasUsuario(rutCliente,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados);
                System.out.println("Ingrese la cantidad de asientos a reembolsar: ");
                int cantAsientos = Integer.parseInt(scan.nextLine());
                if (cantAsientos>cantidadAsientosComprados){
                    System.out.println("Usted no posee tantos asientos.");
                }
                else{
                    for (int i = 0; i < cantAsientos; i++) {
                        System.out.println("Ingrese el nombre de la película: ");
                        String pelicula = scan.nextLine();
                        System.out.println("Ingrese la jornada de la función: ");
                        String jornada = scan.nextLine();
                        System.out.println("Ingrese número de sala de la función: ");
                        int sala = Integer.parseInt(scan.nextLine()); 
                        System.out.println("Ingrese la letra del asiento a devolver: ");
                        String letra = scan.nextLine();
                        System.out.println("Ingrese el número del asiento a devolver: ");
                        int numero = Integer.parseInt(scan.nextLine());
                        int exito = devolverBoleto(pos,saldosC,cantidadPeliculas,pelicula,jornada,sala,letra,numero,peliculasDelCliente,asientosDelCliente,horarioDelCliente,nombresP,cantidadAsientosComprados,recaudacionP,tiposP,movilidad,sala1,sala2,sala3,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T,nombreAJornadas);
                        if (exito==-1){
                            System.out.println("Ocurrió un problema");
                        }
                        else{
                            cantidadAsientosComprados = exito;
                            System.out.println("Boleto "+letra+" "+numero+" reembolsado.");
                        }

                    }
                }
                
            }
            if (opcion.equals("4")){
                desplegarPeliculas(cantidadPeliculas, nombresP, tiposP, sala1, sala2, sala3, nombreAJornadas);
            }
            despliegueMenuUsuario();
            System.out.println("Elija una opción nuevamente: ");
            opcion = scan.nextLine();
        }
        salir(clavesC,cantidadClientes,nombresC, apellidosC,  rutCliente, rutsC, saldosC, estadosC,  cantidadPeliculas, tiposP,  recaudacionP,  nombresP, sala1,  sala2, sala3,  recaP);
    }
    /**
     * shows client menu
     */
    private static void despliegueMenuUsuario(){
        System.out.println("Menu Usuario");
        System.out.println("[1] Comprar entrada");
        System.out.println("[2] Información usuario");
        System.out.println("[3] Devolución entrada");
        System.out.println("[4] Cartelera");
        System.out.println("[5] Salir");
    }
    /**
     * show movies
     * @param cantidadPeliculas
     * @param nombresP
     * @param tiposP
     * @param sala1
     * @param sala2
     * @param sala3
     * @param nombreAJornadas 
     */
    private static void desplegarPeliculas(int cantidadPeliculas,String[] nombresP, String[] tiposP, int[][] sala1, int[][] sala2, int[][] sala3,String[] nombreAJornadas) {
        for (int i = 0; i < cantidadPeliculas; i++) {
            if (sala1[i][0]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[0]+" Sala 1");
            }
            if (sala2[i][0]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[0]+" Sala 2");
            }
            if (sala3[i][0]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[0]+" Sala 3");
            }
            if (sala1[i][1]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[1]+" Sala 1");
            }
            if (sala2[i][1]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[1]+" Sala 2");
            }
            if (sala3[i][1]==1){
                System.out.println("Funcion "+tiposP[i]+" "+nombresP[i]+", "+nombreAJornadas[1]+" Sala 3");
            }
        }
    }
    /**
     * look for a movie with that name
     * @param nombresP
     * @param pelicula
     * @param cantidadPeliculas
     * @return 
     */
    private static int buscarPelicula(String[] nombresP, String pelicula, int cantidadPeliculas) {
        for (int i = 0; i < cantidadPeliculas; i++) {
            if (nombresP[i].equals(pelicula)){
                return i;
            }
        }
        return -1;
    }
    /**
     * Check if show exists
     * @param posPelicula
     * @param sala
     * @param jornada
     * @param sala1
     * @param sala2
     * @param sala3
     * @param nombreAJornadas
     * @return 
     */
    private static boolean comprobarFuncion(int posPelicula, int sala, String jornada, int [][] sala1,int [][] sala2,int [][] sala3, String[] nombreAJornadas) {
        if (sala==1){
            if (sala1[posPelicula][buscarJornada(nombreAJornadas, jornada)]==1){
                return true;
            }
        }
        if (sala==2){
            if (sala2[posPelicula][buscarJornada(nombreAJornadas, jornada)]==1){
                return true;
            }
        }
        if (sala==3){
            if (sala3[posPelicula][buscarJornada(nombreAJornadas, jornada)]==1){
                return true;
            }
        }
        return false;
    }
    /**
     * show sits availables of a movie room
     * @param sala
     * @param sala1
     * @param sala2
     * @param sala3 
     */
    private static void desplegarAsientosDisponibles(int sala, String jornada, int[][] sala1M, int[][] sala1T , int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T) {
        String[] filas = new String[]{"A","B","C","D","E","F","G","H","I","J"};
        int[][] matriz = null;
        if (sala==1 && jornada.equals("M")){
            matriz = sala1M;
        }
        if (sala==1 && jornada.equals("T")){
            matriz = sala1T;
        }
        if (sala==2 && jornada.equals("M")){
            matriz = sala2M;
        }
        if (sala==2 && jornada.equals("T")){
            matriz = sala2T;
        }
        if (sala==3 && jornada.equals("M")){
            matriz = sala3M;
        }
        if (sala==3 && jornada.equals("T")){
            matriz = sala3T;
        }
        System.out.println(mostrarMatrizAsientos(matriz, Integer.toString(sala), jornada));
        
        
    }
    /**
     * return the value of the ticket
     * @param posPelicula
     * @param tiposP
     * @return 
     */
    private static int valorEntrada(int posPelicula, String[] tiposP) {
        if (tiposP[posPelicula].equals("Estreno")){
            return 5500;
        }
        else{
            return 4000;
        }
    }
    /**
     * Add money account
     * @param pos
     * @param saldosC 
     */
    private static void agregarSaldo(int pos, int[] saldosC) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese saldo a agregar: ");
        saldosC[pos] += Integer.parseInt(scan.nextLine());
        System.out.println("Saldo agregado satisfactoriamente | Nuevo saldo: "+saldosC[pos]);
    }
    /**
     * This function check if selected sits are availables and unrestricted
     * @param letras
     * @param numeros
     * @param nombreJornadas
     * @param jornada
     * @param posPelicula
     * @param sala
     * @param sala1M
     * @param sala1T
     * @param sala2M
     * @param sala2T
     * @param sala3M
     * @param sala3T
     * @param peliculasDelCliente
     * @param horarioDelCliente
     * @param asientosDelCliente
     * @param recaudacionP
     * @return 
     */
    private static boolean comprarBoleto(String[] letras, int[] numeros, String[] nombreJornadas,String jornada,int posPelicula, int sala, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int[] recaudacionP) {
        int[][] matriz = null;
        int jornad = buscarJornada(nombreJornadas, jornada);
        if (sala == 1){
            if (jornad==0){
                matriz = sala1M;
            }
            else{
                matriz = sala1T;
            }
        }
        else{
            if (sala == 2){
                if (jornad == 0){
                   matriz = sala2M;
                }
                else{
                    matriz = sala2T;
                }
            }
            else{
                if (jornad == 0){
                    matriz = sala3M;
                }
                else{
                    matriz = sala3T;
                }
            }
        }
        boolean exito = false;
        for (int i = 0; i < letras.length; i++) {
            int fila = buscarFila(letras[i]);
            int numero = numeros[i]-1;
            if (matriz[fila][numero]==0){
                exito = true;
            }
            else{
                exito=false;
            }
        }
        if (exito){
            return true;
        }
        return false;
    }
    /**
     * find index of a character
     * @param letra
     * @return 
     */
    private static int buscarFila(String letra) {
        String[] filas = new String[]{"A","B","C","D","E","F","G","H","I","J"};
        for (int i = 0; i < filas.length; i++) {
            if (filas[i].equals(letra)){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * do a sale
     * @param letras
     * @param numeros
     * @param nombreAJornadas
     * @param jornada
     * @param posPelicula
     * @param sala
     * @param sala1M
     * @param sala1T
     * @param sala2M
     * @param sala2T
     * @param sala3M
     * @param sala3T
     * @param peliculasDelCliente
     * @param horarioDelCliente
     * @param asientosDelCliente
     * @param recaudacionP 
     */
    private static void realizarCompra(String[] peliculas,int asientosCOmprados,String[] letras, int[] numeros, String[] nombreAJornadas, String jornada, int posPelicula, int sala, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int[] recaudacionP) {
        int[][] matriz = null;
        int jornad = buscarJornada(nombreAJornadas, jornada);
        if (sala == 1){
            if (jornad==0){
                matriz = sala1M;
            }
            else{
                matriz = sala1T;
            }
        }
        else{
            if (sala == 2){
                if (jornad == 0){
                   matriz = sala2M;
                }
                else{
                    matriz = sala2T;
                }
            }
            else{
                if (jornad == 0){
                    matriz = sala3M;
                }
                else{
                    matriz = sala3T;
                }
            }
        }
        for (int i = 0; i < letras.length; i++) {
            String asiento = letras[i]+"-"+numeros[i];
            int fila = buscarFila(letras[i]);
            asientosDelCliente[asientosCOmprados+i]=asiento;
            peliculasDelCliente[asientosCOmprados+i]=peliculas[posPelicula];
            horarioDelCliente[asientosCOmprados+i]="Sala "+sala+" "+jornada;
            int columnaAsiento = numeros[i]-1;
            if (matriz[fila][columnaAsiento] == 0){
                System.out.println("Compraste el asiento "+letras[i]+""+numeros[i]);
                matriz[fila][columnaAsiento] = 1;
                if (columnaAsiento-1>=0){
                    if (matriz[fila][columnaAsiento-1]!=-2){
                        matriz[fila][columnaAsiento-1] = -1;
                    }
                }
                if (columnaAsiento+1<=29){
                    if (matriz[fila][columnaAsiento+1]!=-2){
                        matriz[fila][columnaAsiento+1] = -1;
                    }
                }
            }
            else{
                if (matriz[fila][columnaAsiento]==-2){
                    System.out.println("No existe el asiento "+asiento);
                }
                if (matriz[fila][columnaAsiento]==-1){
                    System.out.println("El asiento "+asiento+" está restringido por distanciamiento");
                }
                if (matriz[fila][columnaAsiento]==1){
                    System.out.println("Asiento "+asiento+" ocupado");
                }
            }
            
        }
    }

    private static void desplegarInfoUsuario(int pos, String rutCliente, String[] nombresC, String[] apellidosC, int[] saldosC, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados) {
        if (pos==-1){
            System.out.println("No existe el usuario");
            return;
        }
        System.out.println("Info de usuario: "+rutCliente);
        System.out.println("Nombre: "+nombresC[pos]+" "+apellidosC[pos]);
        System.out.println("Saldo: "+saldosC[pos]);
        desplegarEntradasUsuario(rutCliente, peliculasDelCliente, horarioDelCliente, asientosDelCliente, cantidadAsientosComprados);
    }

    private static void desplegarEntradasUsuario(String rutCliente, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados) {
        if (cantidadAsientosComprados>0){
            System.out.println("Asientos comprados:");
            for (int i = 0; i < cantidadAsientosComprados; i++) {
               System.out.println(peliculasDelCliente[i]+" | Jornada:"+horarioDelCliente[i]+" | Asiento: "+asientosDelCliente[i]);
            }
        }
        else{
            System.out.println("No tiene asientos comprados.");
        }
    }

    private static int devolverBoleto(int pos, int[] saldoC,int cantidadPeliculas, String pelicula, String jornada, int sala, String letra, int numero, String[] peliculasDelCliente, String[] asientosDelCliente, String[] horarioDelCliente, String[] nombresP, int cantidadAsientosComprados, int[] recaudacionP, String[] tiposP, int movilidad, int[][] sala1, int[][] sala2, int[][] sala3, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T, String[] nombreAJornadas) {
        int indexPelicula = buscarPelicula(nombresP, pelicula, cantidadPeliculas);
        if (indexPelicula!=-1){
            int indexJornada = buscarJornada(nombreAJornadas, jornada);
            if (indexJornada!=-1){
                int[][] matriz = null;
                if (sala == 1){
                    if (indexJornada==0){
                        matriz = sala1M;
                    }
                    else{
                        matriz = sala1T;
                    }
                }
                else{
                    if (sala == 2){
                        if (indexJornada == 0){
                           matriz = sala2M;
                        }
                        else{
                            matriz = sala2T;
                        }
                    }
                    else{
                        if (indexJornada == 0){
                            matriz = sala3M;
                        }
                        else{
                            matriz = sala3T;
                        }
                    }
                }
                int fila = buscarFila(letra);
                int columna = numero-1;
                if (fila!=-1 && columna!=-1){
                    matriz[fila][columna]=0;
                    if (columna-1>=0){
                        if (columna-2>=0){
                            if (matriz[fila][columna-2]==1){
                            }
                            else{
                                if (matriz[fila][columna-1]!=-2){
                                    matriz[fila][columna-1] = 0;
                                }
                            }
                        }
                    }
                    if (columna+1<=29){
                        if (columna+2<=29){
                            if (matriz[fila][columna+2]==1){
                                
                            }
                            else{
                                if (matriz[fila][columna+1]!=-2){
                                    matriz[fila][columna+1] = 0;
                                }
                            }
                        }
                    }
                    if (movilidad==1){
                        double aDevolver = ((valorEntrada(indexPelicula,tiposP))*0.85)*0.8;
                        recaudacionP[indexPelicula] -= aDevolver;
                        saldoC[pos]+=aDevolver;
                        System.out.println("Reembolso: $"+aDevolver);
                        cantidadAsientosComprados = eliminarDeLista(letra,numero,cantidadAsientosComprados,peliculasDelCliente,asientosDelCliente,horarioDelCliente);
                    }    
                    else{
                        double aDevolver = (valorEntrada(indexPelicula,tiposP))*0.8;
                        recaudacionP[indexPelicula] -= aDevolver;
                        saldoC[pos]+=aDevolver;
                        System.out.println("Reembolso: $"+aDevolver);
                        cantidadAsientosComprados = eliminarDeLista(letra,numero,cantidadAsientosComprados,peliculasDelCliente,asientosDelCliente,horarioDelCliente);
                    }
                    return (cantidadAsientosComprados);
                }
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
        return -1;
    }
    
    public static int eliminarDeLista(String letraAEliminar, int numeroAEliminar, int cantidad,String[] peliculasDelCliente, String[] asientosDelCliente, String[] horarioDelCliente){
	int i; 
	for(i=0; i< cantidad; i++){
            String[] partes = asientosDelCliente[i].split("-");
            String letra = partes[0];
            int numero = Integer.parseInt(partes[1]);
            if (letra.equals(letraAEliminar) && numero==numeroAEliminar){
                break;
            }
	}
	if(i == cantidad){
            return -1;
	}
	else {
            if (cantidad==1){
                horarioDelCliente[0]=horarioDelCliente[1];
                asientosDelCliente[0]=asientosDelCliente[1];
                peliculasDelCliente[0]=peliculasDelCliente[1];
            }
            else{
                System.out.println("Cantidad 2 o más");
                for(int j=i; j<cantidad - 1;j++){
                    horarioDelCliente[j] = horarioDelCliente[j+1]; 
                    asientosDelCliente[j] = asientosDelCliente[j+1]; 
                    peliculasDelCliente[j] = peliculasDelCliente[j+1]; 
                }
            }
            cantidad--;
            return cantidad;
	}
}

    private static void menuAdmin(String[] clavesC,int[] recaP,String[] rutC,int cantidadPeliculas, String[] nombresP, int[] recaudacionP, int[] recaudacionPDiaria, int[][] sala1, int[][] sala2, int[][] sala3, String[] nombresC, String[] apellidosC, int cantidadClientes, int[] saldosC, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados) throws IOException {
        Scanner scan = new Scanner(System.in);
        despliegueMenuAdmin();
        System.out.println("Elija una opción: ");
        String opcion = scan.nextLine();
        while(!opcion.equals("3")){
            if (opcion.equals("1")){
                taquilla(cantidadPeliculas,nombresP,recaudacionP,recaudacionPDiaria,sala1,sala2,sala3);
            }
            if (opcion.equals("2")){
                //Información de usuario
                System.out.println("Ingrese el rut del cliente: ");
                String rut =  scan.nextLine();
                String rutCliente = formatearRut(rut);
                int pos = buscarRut(nombresC, rutCliente, cantidadClientes);
                desplegarInfoUsuario(pos,rutCliente,nombresC,apellidosC,saldosC,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados);
            }
            despliegueMenuAdmin();
            System.out.println("Elija una opción: ");
            opcion = scan.nextLine();
        }
        salir(rutC, cantidadClientes, nombresC, apellidosC, opcion, rutC, saldosC, saldosC, cantidadPeliculas, nombresP, recaudacionP, nombresP, sala1, sala2, sala3, recaP);
    }

    private static void despliegueMenuAdmin() {
        System.out.println("Menu Admin");
        System.out.println("[1] Taquilla");
        System.out.println("[2] Informacion cliente");
        System.out.println("[3] Salir");
    }

    private static void taquilla(int cantidadPeliculas, String[] nombresP, int[] recaudacionP, int[] recaudacionPDiaria, int[][] sala1, int[][] sala2, int[][] sala3) {
        int mañana = 0;
        int tarde = 0;
        for (int i = 0; i < cantidadPeliculas; i++) {
            System.out.println(nombresP[i]);
            System.out.println("-- Recaudación diaria: $"+recaudacionPDiaria[i]);
            System.out.println("-- Recaudación total: $"+(recaudacionP[i]+recaudacionPDiaria[i]));
            if (sala1[i][0]==1){
                mañana+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
            if (sala2[i][0]==1){
                mañana+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
            if (sala3[i][0]==1){
                mañana+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
            if (sala1[i][1]==1){
                tarde+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
            if (sala2[i][1]==1){
                tarde+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
            if (sala3[i][1]==1){
                tarde+=(recaudacionP[i]+recaudacionPDiaria[i]);
            }
        }
        System.out.println("Recaudación de la jornada Mañana: $"+mañana);
        System.out.println("Recaudación de la jornada Tarde: $"+tarde);
    }

    private static void salir(String[] pass,int cantC,String[] nombresC, String[] apellidosC, String rutCliente, String[] rutsC, int[] saldosC, int[] estadosC, int cantidadPeliculas, String[] tiposP, int[] recaudacionP, String[] nombresP, int[][] sala1, int[][] sala2, int[][] sala3, int[] recaP) throws IOException {
        String arch1 = "peliculas.txt";
        FileWriter file = new FileWriter(arch1);
        PrintWriter escritura = new PrintWriter(file);
        for (int i = 0; i < cantidadPeliculas; i++) {
            String text = nombresP[i]+","+tiposP[i]+","+pass[i]+","+(recaP[i]+recaudacionP[i]);
            if (sala1[i][0]==1){
                text+=",1,M";
            }
            if (sala2[i][0]==1){
                text+=",2,M";
            }
            if (sala3[i][0]==1){
                text+=",3,M";
            }
            if (sala1[i][1]==1){
                text+=",1,T";
            }
            if (sala2[i][1]==1){
                text+=",2,T";
            }
            if (sala3[i][1]==1){
                text+=",3,T";
            }
            escritura.println(text);
        }
        file.close();
        escritura.close();
        String arch2 = "clientes.txt";
        String arch3 = "status.txt";
        file = new FileWriter(arch2);
        escritura = new PrintWriter(file);
        FileWriter file2 = new FileWriter(arch3);
        PrintWriter escritura2 = new PrintWriter(file2);
        for (int i = 0; i < cantC; i++) {
            String text1 = "";
            String text2 = "";
            text1 = nombresC[i]+","+apellidosC[i]+","+rutsC[i]+","+saldosC[i];
            if (estadosC[i]==1){
                text2 = rutsC[i]+",HABILITADO";
            }
            else{
                text2 = rutsC[i]+",NO HABILITADO";
            }
            escritura.println(text1);
            escritura2.println(text2);
        }
        file.close();
        escritura.close();
        file2.close();
        escritura2.close();
    }
}