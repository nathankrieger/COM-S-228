package edu.iastate.cs228.hw4;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * This class contains the main method responsible for interacting with the user.
 *
 * @author Nathan Krieger
 */
public class HW4 {

    public static void main(String[] args) {

        System.out.println("Please enter filename to decode: ");
        Scanner scnr = new Scanner(System.in);
        String fileName = scnr.nextLine();
        scnr.close();

        File file = new File(fileName);

        // There can be at most 3 lines
        String[] lines = new String[3];

        String encodingString, message;

        if (file.exists()) {
            try {

                scnr = new Scanner(file);
                int i = 0;
                while (scnr.hasNext()) {

                    lines[i] = scnr.nextLine();
                    i++;
                }

                // This means that there is 2 lines worth of encodingString.
                if (lines[2] != null) {
                    encodingString = lines[0] + "\n" + lines[1];
                    message = lines[2];
                } else {
                    encodingString = lines[0];
                    message = lines[1];
                }

                MsgTree tree = new MsgTree(encodingString);

                System.out.println("character\tcode \n" +
                        "-------------------------");

                tree.printCodes(tree, "");

                System.out.println();

                System.out.println("MESSAGE: ");
                tree.decode(tree, message);

            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
            
        } else {
            System.out.println("File doesn't exist.");
        }

    }

}
