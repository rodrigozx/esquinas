/**
 * ***************************************************
 * Clase: showPartida
 *
 * @author Rodrigo Blanco - 151251 - Programación II
 * *************************************************
 */
package Interfaz;

import Dominio.Partida;
import java.util.HashMap;
import java.util.Scanner;

public class showPartida {

    Scanner teclado = new Scanner(System.in);

    //Defino Constructores
    //Constructor sin parametros
    public showPartida() {

    }

    //Recorro la matriz y dibujo
    public static void datos(Partida unaPartida) {
        showPartida.datosJugadores(unaPartida);

    }

    //Muestra Datos Jugadores
    private static void datosJugadores(Partida unaPartida) {
        System.out.print("\nJugador 1: " + unaPartida.getJugador1().getAlias() + " - Cubos: " + unaPartida.getTablero().getCantCubosJug1());
        System.out.print("\nJugador 2: " + unaPartida.getJugador2().getAlias() + " - Cubos: " + unaPartida.getTablero().getCantCubosJug2());

        System.out.print("\nJUEGA: ");
        if (unaPartida.getTurno() == 1) {
            System.out.println(unaPartida.getJugador1().getAlias());
        }
        if (unaPartida.getTurno() == 2) {
            System.out.println(unaPartida.getJugador2().getAlias());
        }
    }

}
