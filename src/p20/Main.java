package p20;


import p20.ui.MillsUI;
import p20.ui.T3UI;

import java.util.Scanner;

/**
 * Created by David Donges on 01.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;
        boolean isRunning = true;

        while(isRunning) {
            System.out.println("\n");
            System.out.println("P20 - Projekt");
            System.out.println("-------------");
            System.out.println("Bitte wählen Sie aus den folgenden Optionen durch Eingabe der vorangestellten Zahl + <ENTER>.");
            System.out.println("[1] TicTacToe starten");
            System.out.println("[2] Mühle starten");
            System.out.println("[3] Beenden");

            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
            } else {
                input = 0;
                scanner.next();
            }

            switch(input) {
                case 1:
                    new T3UI().run();
                    break;
                case 2:
                    new MillsUI().run();
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Das habe ich leider nicht vertanden. Bitte überprüfen Sie Ihre Eingabe.");
            }
        }
    }
}
