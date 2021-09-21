/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller0_araya_ibacache;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author aaraya_dibacache
 */
public class Taller0_Araya_Ibacache {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        //extras
        String [] nombresJornadas = new String[]{"Mañana","Tarde"};
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
        
        
        
        //Lectura
        int cantidadClientes = lecturaDatosClientes(nombresC,apellidosC,rutsC,clavesC,saldosC,estadosC);
        int cantidadPeliculas = lecturaDatosPeliculas(nombreAJornadas,nombresP,tiposP,recaudacionP,sala1,sala2,sala3);
        menuPrincipal(cantidadPeliculas,nombresC,apellidosC,rutsC,clavesC,saldosC,estadosC,cantidadClientes,nombreAJornadas,nombresP,tiposP,recaudacionP,sala1,sala2,sala3,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T);
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
            int pos = buscarRut(rutsC,rut);
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
    private static int buscarRut(String[] rutsC, String rut) {
        for (int i = 0; i < rutsC.length; i++) {
            if (rutsC[i].equals(rut)){
                return i;
            }
        }
        return -1;
    }
    /**
     * This method reads the file peliculas and fill the vectors related to movies and movie rooms
     * @param nombreAJornadas
     * @param nombresP
     * @param tiposP
     * @param recaudacionP
     * @param sala1
     * @param sala2
     * @param sala3
     * @throws FileNotFoundException 
     */
    private static int lecturaDatosPeliculas(String[] nombreAJornadas,String[] nombresP, String[] tiposP, int[] recaudacionP, int[][] sala1, int[][] sala2, int[][] sala3) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("peliculas.txt"));
        int i = 0;
        while(scan.hasNext()){
            String linea = scan.nextLine();
            String[] partes = linea.split(",");
            nombresP[i]=partes[0];
            tiposP[i]=partes[1];
            recaudacionP[i] = Integer.parseInt(partes[2]);
            for (int j = 3; j < partes.length; j+=2) {
                int sala = Integer.parseInt(partes[j]);
                String horario = partes[j+1];
                int pos = buscarJornada(nombreAJornadas,horario);
                if (sala == 1){
                    //sala1
                    sala1[i][pos] = 1;
                }
                else{
                    if (sala == 2){
                        //sala 2
                        sala2[i][pos] = 1;
                    }
                    else{
                        //sala 3
                        sala3[i][pos] = 1;
                    }
                }
                i++;
            }
        }
        return i;
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
    private static String[] login(String[] ruts, String[] claves){
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
            int pos = buscarRut(ruts, rutReal);
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

    private static void menuPrincipal(int cantidadPeliculas,String[] nombresC, String[] apellidosC, String[] rutsC, String[] clavesC, int[] saldosC, int[] estadosC, int cantidadClientes, String[] nombreAJornadas, String[] nombresP, String[] tiposP, int[] recaudacionP, int[][] sala1, int[][] sala2, int[][] sala3, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T) {
        System.out.println("Bienvenido!");
        Scanner scan = new Scanner(System.in);
        String[] credenciales = login(rutsC, clavesC);
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
            credenciales = login(rutsC, clavesC);
        }
        if (credenciales[0].equals("1")){
            String rutCliente = credenciales[1];
            int pos = buscarRut(rutsC, rutCliente);
            String[] peliculasDelCliente = new String[1000];
            String[] horarioDelCliente = new String[1000];
            String[] asientosDelCliente = new String[1000];
            int cantidadAsientosComprados = 0;
            menuUsuario(cantidadPeliculas,cantidadClientes,rutCliente,pos,nombresC,apellidosC,saldosC,estadosC,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados,nombreAJornadas, nombresP, tiposP, recaudacionP, sala1, sala2, sala3,sala1M,sala1T, sala2M,sala2T,sala3M, sala3T);
        }
        else{
            menuAdmin();
        }
    }

    private static void menuUsuario(int cantidadPeliculas,int cantidadClientes, String rutCliente, int pos, String[] nombresC, String[] apellidosC, int[] saldosC, int[] estadosC, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados,String[] nombreAJornadas, String[] nombresP, String[] tiposP, int[] recaudacionP, int[][] sala1, int[][] sala2, int[][] sala3, int[][] sala1M, int[][] sala1T, int[][] sala2M, int[][] sala2T, int[][] sala3M, int[][] sala3T) {
        Scanner scan = new Scanner(System.in);
        despliegueMenuUsuario();
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
                        int aPagar = cantEntradas*valorEntrada(posPelicula,tiposP);
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
                            desplegarAsientosDisponibles(sala,sala1,sala2,sala3);
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
                                System.out.println("¿Está seguro que desea realizar la compra? [Y/N]");
                                String comprar = scan.nextLine();
                                if (comprar.equals("Y")){
                                    realizarCompra(nombresP,cantidadAsientosComprados,letras,numeros,nombreAJornadas,jornada,posPelicula,sala,sala1M,sala1T,sala2M,sala2T,sala3M,sala3T,peliculasDelCliente,horarioDelCliente,asientosDelCliente, recaudacionP);
                                    cantidadAsientosComprados+=cantEntradas;
                                    saldosC[pos]-=aPagar;
                                }
                                else{
                                    System.out.println("Compra cancelada.");
                                }
                            }
                            else{
                                System.out.println("Alguno de los datos de su compra no ha sido ingresado correctamente.");
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
                //Entradas del usuario
                System.out.println("Entradas de: "+rutCliente);
                System.out.println("Usted posee "+cantidadAsientosComprados+" asientos.");
                desplegarEntradasUsuario(rutCliente,peliculasDelCliente,horarioDelCliente,asientosDelCliente,cantidadAsientosComprados);
                System.out.println("Ingrese la cantidad de asientos a reembolsar: ");
                int cantAsientos = Integer.parseInt(scan.nextLine());
                if (cantAsientos == cantidadAsientosComprados){
                    horarioDelCliente = new String[cantidadAsientosComprados];
                    asientosDelCliente = new String[cantidadAsientosComprados];
                    peliculasDelCliente = new String[cantidadAsientosComprados];
                    
                }
                else{
                    System.out.println("Cantidad de asientos no válida.");
                }
            }
            if (opcion.equals("4")){
                desplegarPeliculas(cantidadPeliculas, nombresP, tiposP, sala1, sala2, sala3, nombreAJornadas);
            }
            despliegueMenuUsuario();
        }
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
            if (nombresP.equals(pelicula)){
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
    private static void desplegarAsientosDisponibles(int sala, int[][] sala1, int[][] sala2, int[][] sala3) {
        String[] filas = new String[]{"A","B","C","D","E","F","G","H","I","J"};
        int[][] matriz = null;
        if (sala==1){
            matriz = sala1;
        }
        else{
            if (sala==2){
                matriz = sala2;
            }
            else{
                matriz = sala3;
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 30; j++) {
                if (matriz[i][j]==1){
                    System.out.println(filas[i]+""+(j+1)+": Ocupado");
                }
                if (matriz[i][j]==0){
                    System.out.println(filas[i]+""+(j+1)+": Disponible");
                }
                if (matriz[i][j]==-1){
                    System.out.println(filas[i]+""+(j+1)+": Restringido");
                }
            }
        }
    }
    /**
     * return the value of the ticket
     * @param posPelicula
     * @param tiposP
     * @return 
     */
    private static int valorEntrada(int posPelicula, String[] tiposP) {
        if (tiposP[posPelicula].equals("estreno")){
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
            horarioDelCliente[asientosCOmprados+i]=jornada;
            matriz[fila][numeros[i]-1] = 1;//ocupada
            if ((numeros[i]-1-1)<0){
                matriz[fila][numeros[i]-2]=-1;//no disponible
            }
            if ((numeros[i]-1+1)>29){
                matriz[fila][numeros[i]]=-1;//no disponible
            }
        }
    }

    private static void desplegarInfoUsuario(int pos, String rutCliente, String[] nombresC, String[] apellidosC, int[] saldosC, String[] peliculasDelCliente, String[] horarioDelCliente, String[] asientosDelCliente, int cantidadAsientosComprados) {
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
    
    

}

