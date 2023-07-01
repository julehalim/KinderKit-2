import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Jule Valendo Halim
 * @studentID: 1425567
 * @universityEmail : julevalendoh@student.unimelb.edu.au
 */

public class KinderKit {

    public static void main(String[] args) {
        //DON'T TOUCH LINES 8 to 59
        //Check program arguments
        if (args.length != 1) {
            System.out.println("This program takes exactly one argument!");
            return;
        }

        //Read sample drawing from file
        Scanner inputStream = null;
        char[][] bitmap = null;
        int rows = 0, cols = 0;
        char bgChar;

        try {
            inputStream = new Scanner(new FileInputStream(args[0]));
            String line;

            //Read the first line
            if (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                String[] tmps = line.split(",");
                if (tmps.length != 3) {
                    System.out.println("The given file is in wrong format!");
                    return;
                } else {
                    rows = Integer.parseInt(tmps[0]);
                    cols = Integer.parseInt(tmps[1]);
                    bgChar = tmps[2].charAt(0);
                    bitmap = new char[rows][cols];
                }
            } else {
                System.out.println("The given file seems empty!");
                return;
            }

            //Read the remaining lines
            int rowIndex = 0;
            while (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                String[] tmps = line.split(",");
                for (int i = 0; i < tmps.length; i++) {
                    bitmap[rowIndex][i] = tmps[i].charAt(0);
                }
                rowIndex++;
            }
            //Close the file input stream
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("The given file is not found!");
            return;
        }

        //initialize Input
        int inputInt = 0;

        //Welcome Message
        System.out.printf("----DIGITAL KINDER KIT: LET'S PLAY & LEARN----%n");

        //initialize scanner
        Scanner scanner = new Scanner(System.in);

        //main menu
        while (inputInt < 1 || inputInt > 3) {
            //get new clean array of triangles and triangle positions
            DrawingTask drawingTask = new DrawingTask();
            inputInt = drawingTask.mainMenu(scanner);
            //switch statement for chosen options
            switch (inputInt) {
                case 1: //predefined object mode
                    while (inputInt == 1) {
                        //get predefined canvas
                        DrawingCanvas drawingCanvas = new DrawingCanvas(cols, rows, bgChar, drawingTask);
                        int inputIntOne = drawingTask.predefinedMenu(scanner);

                        //preview sample
                        if (inputIntOne == 1) {
                            System.out.println("This is your task. Just try to draw the same object. Enjoy!");
                            drawingTask.printBitmap(bitmap, rows, cols);
                            inputInt = 1;
                        }

                        //start/edit current canvas
                        if (inputIntOne == 2) {
                            drawingCanvas.printCanvas(drawingCanvas);
                            int drawingInt = 0;
                            while (drawingInt == 0) {
                                drawingInt = drawingTask.triangleOptions(scanner);
                                while (drawingInt == 1) { //add a triangle
                                    drawingTask.getNewTriangle(drawingTask, drawingCanvas, scanner);
                                    drawingCanvas.printCanvas(drawingCanvas);
                                    drawingTask.editMenu(drawingTask, drawingTask.getTriangleArray().size() - 1, drawingCanvas, scanner);
                                    drawingInt = 0;
                                }
                                while (drawingInt == 2) {//edit triangle
                                    drawingTask.choosingEditTriangle(drawingTask, drawingCanvas, scanner);
                                    drawingInt = 0;
                                }
                                while (drawingInt == 3) {//delete triangle
                                    drawingTask.choosingDeleteTriangle(drawingTask, drawingCanvas, scanner);
                                    drawingInt = 0;
                                }
                                if (drawingInt == 4) { //exit menu
                                    inputInt = 1;
                                }
                            }
                        }

                        //compare drawing with task given
                        if (inputIntOne == 3) {
                            drawingTask.checkResult(bitmap, drawingCanvas, rows, cols, drawingTask);
                            inputIntOne = 0;
                        }

                        //back to main menu
                        if (inputIntOne == 4) {
                            inputInt = 0;
                        }
                    }

                case 2: //freestyle drawing mode
                    while (inputInt == 2) {
                        //get clean canvas
                        DrawingCanvas drawingCanvas = new DrawingCanvas();
                        //replace current canvas with user input settings
                        drawingCanvas.replaceCanvas(drawingTask.getCustomCanvas(drawingCanvas, drawingTask, scanner));
                        while (inputInt == 2) {
                            int inputIntTwo = drawingTask.freestyleMenu(scanner);//enter freestyleMenu
                            int printStatus = 0;
                            while (inputIntTwo == 1) {
                                if (printStatus == 0) {
                                    drawingCanvas.printCanvas(drawingCanvas);
                                    printStatus = 1;
                                }
                                int drawingInt = drawingTask.triangleOptions(scanner); //enter menu to edit triangles
                                if (drawingInt == 1) { //add a new triangle
                                    drawingTask.getNewTriangle(drawingTask, drawingCanvas, scanner);
                                    drawingCanvas.printCanvas(drawingCanvas);
                                    drawingTask.editMenu(drawingTask, drawingTask.getTriangleArray().size() - 1, drawingCanvas, scanner);
                                }
                                if (drawingInt == 2) { //edit an existing triangle
                                        drawingTask.choosingEditTriangle(drawingTask, drawingCanvas, scanner);
                                }
                                if (drawingInt == 3) { //delete a triangle
                                        drawingTask.choosingDeleteTriangle(drawingTask, drawingCanvas, scanner);
                                    drawingInt = 0;
                                }
                                if (drawingInt == 4) { //exit menu
                                    inputIntTwo = 0;
                                }
                            }
                            while (inputIntTwo == 2) { //print current canvas as bitmap
                                drawingCanvas.printCurrentDrawing(drawingCanvas);
                                inputIntTwo = 1;
                            }
                            if (inputIntTwo == 3) { //exit menu
                                inputInt = 0;
                            }
                        }
                    }

                case 3: //exit program
                    while (inputInt == 3) {
                        System.out.println("Goodbye! We hope you had fun :)");
                        break;
                    }
            }
        }
    }
}
