import java.util.Scanner;
import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Jule Valendo Halim
 * @studentID: 1425567
 * @universityEmail : julevalendoh@student.unimelb.edu.au
 */

public class DrawingCanvas {
    //instance variables
    private char background;
    private int width;
    private int height;
    private char[][] canvasArray; //canvas represented as an array

    //default constructor
    public DrawingCanvas() {
        setBackground('-');
        setHeight(10);
        setWidth(6);
        canvasArray = new char[6][10];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvasArray[j][i] = background;
            }
        }
    }

    //constructor
    public DrawingCanvas(int width, int height, char background, DrawingTask drawingTask) {
        setBackground(background);
        setWidth(width);
        setHeight(height);
        canvasArray = new char[width][height];
        for (int k = 0; k < drawingTask.getTriangleArray().size(); k++) {
            drawingTask.getTriangle(k).triangleToArray(drawingTask.getTriangle(k), canvasArray, drawingTask.getTriangleX(k), drawingTask.getTriangleY(k));
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (canvasArray[i][j] == 0) {
                    canvasArray[i][j] = background;
                }
            }
        }
    }

    //copy constructor
    public DrawingCanvas(DrawingCanvas drawingCanvas) {
        background = drawingCanvas.background;
        width = drawingCanvas.width;
        height = drawingCanvas.height;
        canvasArray = drawingCanvas.canvasArray;
    }

    //public methods
    //setters
    //set canvas backgroundchar
    public void setBackground(char background) {
        this.background = background;
    }

    //set canvas width
    public void setWidth(int width) {
        this.width = width;
    }

    //set canvas height
    public void setHeight(int height) {
        this.height = height;
    }

    //getters
    //get background char
    public char getBackground() {
        return background;
    }

    //get canvas width
    public int getWidth() {
        return width;
    }

    //get canvas height
    public int getHeight() {
        return height;
    }

    //get canvas array
    public char[][] getArray() {
        return canvasArray;
    }

    //other public methods
    //printing saved drawing canvas as bitmap
    public void printCurrentDrawing(DrawingCanvas drawingCanvas) {
        System.out.println("This is the detail of your current drawing");
        System.out.printf("%d,%d,%c%n", drawingCanvas.getHeight(), drawingCanvas.getWidth(), drawingCanvas.getBackground());
        for (int i = 0; i < drawingCanvas.getHeight(); i++) {
            for (int j = 0; j < drawingCanvas.getWidth(); j++) {
                if (j == drawingCanvas.getWidth() - 1) {
                    System.out.println(drawingCanvas.getArray()[j][i]);
                } else {
                    System.out.printf("%c,", drawingCanvas.getArray()[j][i]);
                }
            }
        }
    }

    //replace current canvas with another canvas
    public void replaceCanvas(DrawingCanvas drawingCanvas) {
        this.width = drawingCanvas.getWidth();
        this.height = drawingCanvas.getHeight();
        this.background = drawingCanvas.getBackground();
        this.canvasArray = drawingCanvas.getArray();
    }

    //print current canvas
    public void printCanvas(DrawingCanvas drawingCanvas) {
        for (int i = 0; i < drawingCanvas.getHeight(); i++) {
            for (int j = 0; j < drawingCanvas.getWidth(); j++) {
                if (j == drawingCanvas.getWidth() - 1) {
                    System.out.println(drawingCanvas.getArray()[j][i]);
                } else if (j != (drawingCanvas.getWidth() - 1)) {
                    System.out.print(drawingCanvas.getArray()[j][i]);
                }
            }
        }
    }
}
