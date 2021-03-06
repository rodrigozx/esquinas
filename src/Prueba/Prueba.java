/**
 * ***************************************************
 * Clase: showPartida
 *
 * @author Rodrigo Blanco - 151251 - Programación II
 * *************************************************
 */
package Prueba;

import Dominio.*;
import Interfaz.*;
import java.util.*;

public class Prueba {

    public static void main(String[] args) {

        //Se crea el sistema del juego
        Sistema miSistema = new Sistema();

        //Se crea el menu a mostrar
        showMenu elMenu = new showMenu(0, true);

        int dato = 0; //Por defecto se ejecuta el menú princiapl.

        //Carga del Menu Principal
        elMenu.showLogo();
        boolean salir = false;

        Jugador jugador1 = new Jugador("Rolo", "Rodrigo", 33);
        Jugador jugador2 = new Jugador("Viky", "Virginia", 29);
        miSistema.agregarJugador(jugador1);
        miSistema.agregarJugador(jugador2);

        do {
            dato = elMenu.showMenuPrincipal(4); //existen 4 opciones
            switch (dato) {

                //Registro de Jugador **********************************************************************
                case 1:
                    seleccionOpcionesRegistroJugador(miSistema, elMenu);
                    break;

                //Jugar 1 vs 1 **********************************************************************
                case 2:
                    iniciarPartida(2, miSistema, elMenu); //Son dos jugadores.
                    break;

                //Jugar 1 vs CPU **********************************************************************
                case 3:
                    iniciarPartida(1, miSistema, elMenu); //Es un sólo jugador.
                    break;

                //Muestra el Ranking de jugadores *****************************************************
                case 4:
                    elMenu.showMenuRanking(miSistema.getListaJugadores());
                    break;

                //Sale del programa **********************************************************************                    
                case 0:
                    elMenu.showSaludo();
                    salir = true;
                    break;

                default:
                //No hace nada
            }
        } while (salir != true);
    }

    public static void seleccionOpcionesRegistroJugador(Sistema miSistema, showMenu elMenu) {
        String cabezal = "OPCIONES DE REGISTRO DE JUGADOR";
        ArrayList<String> listaOpciones = new ArrayList();
        int opcSelect;

        listaOpciones.add("Volver");//0 
        listaOpciones.add("Registrar Nuevo Jugador"); //1
        listaOpciones.add("Eliminar Jugador");        //2

        //El último parámetro muestra o no la opción de volver.
        opcSelect = elMenu.menuOpciones(cabezal, listaOpciones, true);
        switch (opcSelect) {
            case 1:
                registroDeJugador(miSistema, elMenu);
                break;
            case 2:
                eliminarJugador(miSistema, elMenu);
                break;
            default: //no hace nada y vuelve al menu principal
        }
    }

    public static void registroDeJugador(Sistema miSistema, showMenu elMenu) {
        //Se ejecuta el metodo mostrar Cabecera de Menú.
        elMenu.showCabeceraMenu("Menú Registrar Jugador");

        //Se crea el objeto Jugador con los parametros.
        //Luego se agrega al ArrayList de Jugadores
        Jugador elJugador = new Jugador(elMenu.registrarJugadorAlias(miSistema.getListaJugadores()),
                elMenu.registrarJugadorNombre(), elMenu.registrarJugadorEdad());

        elMenu.mostrarMensaje("Jugador Registrado!", "");
        miSistema.agregarJugador((Jugador) elJugador);
        System.out.println("\n" + elJugador);
    }

    public static void eliminarJugador(Sistema miSistema, showMenu elMenu) {

        //Se muestra la lista de jugadores y se elimina el seleccionado;
        Jugador elJugador = elMenu.seleccionarJugador(miSistema.getListaJugadores());
        miSistema.eliminarJugador((Jugador) elJugador);
        elMenu.mostrarMensaje("Jugador  " + elJugador.getAlias() + "  eliminado!", "correcto");
    }

    public static void iniciarPartida(int cantJugadores, Sistema miSistema, showMenu elMenu) {

        //Creo una partida.
        boolean vsCpu;

        //Veo si es un jugaor sólo
        vsCpu = cantJugadores != 2;

        //Selección de jugadores y arranque Partida. ******************************************
        //si existe menos de un jugador registrado
        if (!miSistema.numJugadoresMinimos() && (vsCpu == false)) {
            elMenu.mostrarMensaje("Error! la cantidad de jugadores registrados", "error");
            if (!miSistema.numJugadoresMinimos() && (vsCpu == true)) {
                elMenu.mostrarMensaje("es de al menos uno para ésta partida", "error");
            } else {
                elMenu.mostrarMensaje("es de al menos dos para este tipo de partida", "error");
            }

        } else {
            if (miSistema.numJugadoresRegistrados() == 1 && (vsCpu == false)) {
                elMenu.mostrarMensaje("Error! la cantidad de jugadores registrados", "error");
                elMenu.mostrarMensaje("es de al menos dos para este tipo de partida", "error");
            }
            Jugador jugador1;
            Jugador jugador2;
                    
            if (vsCpu) {
            jugador1 = elMenu.seleccionarJugadoresPartida("Primer",
                    miSistema.getListaJugadores(), null);
            jugador2 = new Jugador ("CPU","CPU",1);
            
            }else{
                jugador1 = elMenu.seleccionarJugadoresPartida("Primer",
                    miSistema.getListaJugadores(), null);
                jugador2 = elMenu.seleccionarJugadoresPartida("Segundo",
                    miSistema.getListaJugadores(), jugador1);
            }
            Partida laPartida = new Partida(jugador1, jugador2, 1, vsCpu, 25);

            //Mostrar tablero y datos de los jugadores.
            do {
                //CICLO DE PARTIDA ##################################################
                //Dibujo del Tablero
                elMenu.mostrarMensaje("Tablero - Esquinas ", "");
                showTablero.show(laPartida.getTablero().getMatrizTablero());//muestro la estadística

                //Dibujo de status de la partida.
                showPartida.datos(laPartida);

                //Se ingresa la jugada.
                if (laPartida.getTurno()==2 && vsCpu){
                    laPartida.getTablero().jugadaCpu();
                }else {
                    pedirJugada(miSistema, laPartida, elMenu);
                }

                //Si un jugador quiere abandonar
                //Si nadie Abandona o no hay ganador vuelve al "do"
            } while ((laPartida.terminoPartida() == 0)); //mientra haya jugadores con fichas.

            //FIN PARTIDA #############################################################
            /*
             Asignamos el resultado al ranking.
             */
            if (laPartida.terminoPartida() == 1) {
                laPartida.getJugador1().setPartidas(1, 0);
                laPartida.getJugador2().setPartidas(0, 1);
                elMenu.showCabeceraMenu("EL GANADOR ES: " + (laPartida.getJugador1().getAlias()));
            }
            if (laPartida.terminoPartida() == 2) {
                laPartida.getJugador2().setPartidas(1, 0);
                laPartida.getJugador1().setPartidas(0, 1);
                System.out.println("\n");
                elMenu.showCabeceraMenu("EL GANADOR ES: " + (laPartida.getJugador2().getAlias()));
                System.out.println("\n");
            }

        }
        /*
         Fin Partida retorno menú principal
         */

    }

    public static void pedirJugada(Sistema miSistema, Partida laPartida, showMenu elMenu) {
        String jugada;
        int[] lasCoordenadas = new int[2];
        String entradaOk;
        boolean salir = false;

        jugada = elMenu.ingresarMovimiento();
        jugada = jugada.toUpperCase();
        entradaOk = validaEntradaJugada(jugada, laPartida);
        
        if (entradaOk.equals("Ok")){
            lasCoordenadas = laPartida.ingresarCoordenadas(jugada);
            if (laPartida.getEstado().equalsIgnoreCase("OK")){
                
                //se intenta realizar la jugada
                entradaOk = laPartida.getTablero().colocarCubo(lasCoordenadas[0],lasCoordenadas[1], laPartida.getTurno());
                laPartida.setEstado(entradaOk);
                if (laPartida.getEstado().equalsIgnoreCase("Ok")){
                    laPartida.cambioTurno();
                    laPartida.restaCubos(1, true);
                }else{
                    elMenu.mostrarMensaje(laPartida.getEstado(), "error");                    
                }
            }else{
                elMenu.mostrarMensaje(laPartida.getEstado(), "error");
            }
        }else{

            if(entradaOk.equals("ABANDONA")){
                salir = elMenu.confimaMensaje("¿Está seguro que desea abandonar?", "error");

                if(salir){
                    //Doy la partida ganada al otro jugador
                    laPartida.abandonar();
                    //Sale al menu Principal
                }
            }else{
                //error al ingresar Jugada
                elMenu.mostrarMensaje(entradaOk,"error");
            }
        }
    }

    public static String validaEntradaJugada(String jugada, Partida laPartida) {
        String entradaOk;

        if (jugada.trim().length() > 2 || jugada.trim().length() < 1) {
            entradaOk = "Largo de jugada inválido";
        } else {
            if (jugada.trim().equals("X")) {
                entradaOk = "ABANDONA";
            } else {
            
                entradaOk = "Ok";
            }
        }
        return entradaOk;
    }
}
