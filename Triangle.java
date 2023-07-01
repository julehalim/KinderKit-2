import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Jule Valendo Halim
 * @studentID: 1425567
 * @universityEmail : julevalendoh@student.unimelb.edu.au
 */

public class Triangle {
    //instance variables
    private int sideLength;
    private char printingChar;
    private int rotationDegree;

    //default constructor
    public Triangle() {
        setSideLength(5);
        setPrintingChar('*');
        setRotationDegree(0);
    }

    //constructor
    public Triangle(int sideLength, char printingChar) {
        setSideLength(sideLength);
        setPrintingChar(printingChar);
        setRotationDegree(0);
    }

    //copy constructor
    public Triangle(Triangle triangle) {
        setPrintingChar(triangle.printingChar);
        setSideLength(triangle.sideLength);
        setRotationDegree(triangle.rotationDegree);
    }

    //public methods
    //accessors
    //get side length
    public int getSideLength() {
        return sideLength;
    }

    //get printing char
    public char getPrintingChar() {
        return printingChar;
    }

    //get rotation degree
    public int getRotationDegree() {
        return rotationDegree;
    }

    //mutators
    //change side length
    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    //change printing char
    public void setPrintingChar(char printingChar) {
        this.printingChar = printingChar;
    }

    //change rotation degree
    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    //other public methods
    //zooming in
    public void setSideZoomOut(Triangle triangle) {
        triangle.setSideLength(triangle.getSideLength() - 1);
    }

    //zooming out
    public void setSideZoomIn(Triangle triangle) {
        triangle.setSideLength(triangle.getSideLength() + 1);
    }

    //iterate over drawing canvas array
    public void triangleToArray(Triangle triangle, char[][] canvasArray, int x, int y) {
        //no rotation printing to canvas
        if (triangle.getRotationDegree() == 0) {
            int n = 0;
            for (int i = y; i < y + triangle.getSideLength(); i++) {
                for (int j = x; j < x + triangle.getSideLength() - n; j++) {
                    canvasArray[i][j] = triangle.getPrintingChar();
                }
                n++;
            }
        }

        //array print orientation where rotation for 1 right rotation or 3 left rotations
        if (triangle.getRotationDegree() == 3 || triangle.getRotationDegree() == -1) {
            int n = 0;
            for (int i = y; i < y + triangle.getSideLength(); i++) {
                for (int j = x + n; j < x + triangle.getSideLength(); j++) {
                    canvasArray[i][j] = triangle.getPrintingChar();
                }
                n++;
            }
        }

        //array print orientation where rotation for 2 right rotations or 2 left rotations
        if (triangle.getRotationDegree() == 2 || triangle.getRotationDegree() == -2) {
            int n = triangle.getSideLength() - 1;
            for (int i = y; i < y + triangle.getSideLength(); i++) {
                for (int j = x + n; j < x + triangle.getSideLength(); j++) {
                    canvasArray[i][j] = triangle.getPrintingChar();
                }
                n--;
            }
        }

        //array print orientation where rotation for 3 right rotations or 1 left rotation
        if (triangle.getRotationDegree() == 1 || triangle.getRotationDegree() == -3) {
            int n = triangle.getSideLength() - 1;
            for (int i = y; i < y + triangle.getSideLength(); i++) {
                for (int j = x; j < x + triangle.getSideLength() - n; j++) {
                    canvasArray[i][j] = triangle.getPrintingChar();
                }
                n--;
            }
        }
    }
}
