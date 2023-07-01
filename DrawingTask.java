import java.util.Scanner;
import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Jule Valendo Halim
 * @studentID: 1425567
 * @universityEmail : julevalendoh@student.unimelb.edu.au
 */

public class DrawingTask {

    //instance variables
    private ArrayList < Triangle > triangleArray;
    private ArrayList < Integer > trianglePosition;

    //default constructor
    public DrawingTask() {
        triangleArray = new ArrayList < Triangle > ();
        trianglePosition = new ArrayList < Integer > ();
    }

    //public methods
    //getters
    //get triangle at position [i]
    public Triangle getTriangle(int i) {
        return triangleArray.get(i);
    }

    //get triangle X position from trianglePosition arraylist
    public int getTriangleX(int triangleStart) {
        return trianglePosition.get(2 * triangleStart);
    }

    //get triangle Y position from trianglePosition arraylist
    public int getTriangleY(int triangleStart) {
        return trianglePosition.get(2 * triangleStart + 1);
    }

    //get triangleArray
    public ArrayList < Triangle > getTriangleArray() {
        return triangleArray;
    }

    //get trianglePosition arraylist
    public ArrayList < Integer > getPositionArray() {
        return trianglePosition;
    }

    //get triangle Y for printing
    public int getTriangleYPrint(int triangleStart) {
        return trianglePosition.get(2 * triangleStart);
    }

    //get triangle x for printing
    public int getTriangleXPrint(int triangleStart) {
        return trianglePosition.get(2 * triangleStart + 1);
    }

    //setters
    //add new triangle
    public void addTriangle(Triangle triangle, int x, int y) {
        triangleArray.add(triangle);
        trianglePosition.add(x);
        trianglePosition.add(y);
    }

    //remove triangle from arraylist
    public void removeTriangle(int trianglePos, DrawingTask drawingTask) {
        triangleArray.remove(trianglePos);
        trianglePosition.remove(2 * trianglePos);
        trianglePosition.remove(2 * trianglePos);
    }

    //set triangle x by editing trianglePosition array
    public void setTriangleX(int oldPositionIndex, int newPosition) {
        trianglePosition.set(2 * oldPositionIndex, newPosition);
    }

    //set triangle y by editing trianglePosition array
    public void setTriangleY(int oldPositionIndex, int newPosition) {
        trianglePosition.set(2 * oldPositionIndex + 1, newPosition);
    }

    //other public methods
    //print list of triangle for editing/deleting
    private void printTriangleList(DrawingTask drawingTask) {
        for (int i = 0; i < drawingTask.getTriangleArray().size(); i++) {
            System.out.printf("Shape #%d - Triangle: xPos = %d, yPos = %d, tChar = %c%n", (i + 1), drawingTask.getTriangleXPrint(i), drawingTask.getTriangleYPrint(i), drawingTask.getTriangle(i).getPrintingChar());
        }
    }

    //access triangle to delete and delete triangle
    public void choosingDeleteTriangle(DrawingTask drawingTask, DrawingCanvas drawingCanvas, Scanner scanner) {
        int deletingTriangle;
        DrawingCanvas tempDrawingCanvas;
        if (drawingTask.getTriangleArray().isEmpty()) {
            drawingTask.emptyCanvas(drawingTask, drawingCanvas,0); //prints error message if empty canvas for delete (0)
        }
        else {
            drawingTask.printTriangleList(drawingTask);
            System.out.println("Index of the shape to remove:");
            deletingTriangle = scanner.nextInt() - 1;
            if (deletingTriangle + 1 > drawingTask.getTriangleArray().size()) {
                System.out.println("The shape index cannot be larger than the number of shapes!");
                drawingCanvas.printCanvas(drawingCanvas);
            } else {
                drawingTask.removeTriangle(deletingTriangle, drawingTask);
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
        }
    }

    //access triangle to edit and edit triangle
    public void choosingEditTriangle(DrawingTask drawingTask, DrawingCanvas drawingCanvas, Scanner scanner) {
        int editingTriangle;
        if (drawingTask.getTriangleArray().isEmpty()) {
            drawingTask.emptyCanvas(drawingTask,drawingCanvas,1);//prints error message if canvas empty for edit(1)
        } 
        else {
            drawingTask.printTriangleList(drawingTask);
            System.out.println("Index of the shape:");
            editingTriangle = scanner.nextInt() - 1;
            if (editingTriangle + 1 > drawingTask.getTriangleArray().size()) {
                System.out.println("The shape index cannot be larger than the number of shapes!");
                drawingCanvas.printCanvas(drawingCanvas);
            } else {
                drawingCanvas.printCanvas(drawingCanvas);
                drawingTask.editMenu(drawingTask, editingTriangle, drawingCanvas, scanner);
            }
        }
    }

    //get triangle information and add new triangle into arraylist
    public void getNewTriangle(DrawingTask drawingTask, DrawingCanvas drawingCanvas, Scanner scanner) {
        DrawingCanvas tempDrawingCanvas;
        Triangle triangle = new Triangle();
        int sideCheck = 0;
        while (sideCheck == 0) { //obtain parameters for triangle and error checking
            System.out.println("Side length:");
            int sideObtain = scanner.nextInt();
            if ((sideObtain > drawingCanvas.getHeight()) || (sideObtain > drawingCanvas.getWidth())) {
                System.out.printf("Error! The side length is too long (Current canvas size is %dx%d). Please try again.%n", drawingCanvas.getHeight(), drawingCanvas.getWidth());
            }
            else if ((sideObtain < 1)) {
                System.out.println("Error! The side length has to be more than 0!");
            }
            else {
                triangle.setSideLength(sideObtain);
                sideCheck = 1;
            }
        }
        System.out.println("Printing character:");
        triangle.setPrintingChar(scanner.next().charAt(0));
        drawingTask.addTriangle(triangle, 0, 0);
        tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
        drawingCanvas.replaceCanvas(tempDrawingCanvas);
    }

    //edit values of triangle in tringleArray arraylist depending on Z/M/R modes, with edit menu
    public void editMenu(DrawingTask drawingTask, int editingTriangle, DrawingCanvas drawingCanvas, Scanner scanner) {
        DrawingCanvas tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
        int menuInt = 0;
        while (menuInt == 0) {
            System.out.println("Type Z/M/R for zooming/moving/rotating. Use other keys to quit the Zooming/Moving/Rotating mode.");
            drawingCanvas.printCanvas(drawingCanvas);
            String editMode = scanner.next().toLowerCase();
            if (!(editMode.equals("z")) || editMode.equals("m") || editMode.equals("r")) {
                menuInt = 1;
            }
            if (editMode.equals("z")) { //zooming mode
                drawingTask.zooming(drawingTask, editingTriangle, drawingCanvas, tempDrawingCanvas, scanner);
                menuInt = 0;
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (editMode.equals("m")) { //moving mode
                drawingTask.moving(drawingTask, editingTriangle, drawingCanvas, tempDrawingCanvas, scanner);
                menuInt = 0;
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (editMode.equals("r")) { //rotating mode
                drawingTask.rotating(drawingTask, editingTriangle, drawingCanvas, tempDrawingCanvas, scanner);
                menuInt = 0;
                drawingCanvas.printCanvas(drawingCanvas);
            }
        }
    }

    //main menu
    public int mainMenu(Scanner scanner) {
        int inputInt;
        System.out.println("Please select an option. Type 3 to exit.");
        System.out.println("1. Draw a predefined object");
        System.out.println("2. Freestyle Drawing");
        System.out.println("3. Exit");
        //take initial input
        inputInt = scanner.nextInt();
        if (inputInt < 1 || inputInt > 3) {
            System.out.println("Unsupported option. Please try again!");
        }
        return inputInt;
    }

    //initial menu for predefined mode
    public int predefinedMenu(Scanner scanner) {
        System.out.println("Please select an option. Type 4 to go back to the main menu.");
        System.out.println("1. Preview the sample drawing");
        System.out.println("2. Start/edit the current canvas");
        System.out.println("3. Check result");
        System.out.println("4. Go back to the main menu");
        int inputIntOne = scanner.nextInt();
        if (inputIntOne < 1 || inputIntOne > 4) {
            System.out.println("Unsupported option. Please try again.");
        }
        return inputIntOne;
    }


    //obtain custom canvas settings for frestyle mode
    public DrawingCanvas getCustomCanvas(DrawingCanvas drawingCanvas, DrawingTask drawingTask, Scanner scanner) {
        System.out.print("Canvas width: ");
        int width = scanner.nextInt();
        System.out.print("Canvas height: ");
        int height = scanner.nextInt();
        System.out.print("Background character: ");
        char backgroundChar = scanner.next().charAt(0);
        if(height<=0||width<=0){
            System.out.println("Warning! Canvas dimension is 0 or less!");
        }
        DrawingCanvas redrawingCanvas = new DrawingCanvas(width, height, backgroundChar, drawingTask);
        return redrawingCanvas;
    }

    //print a bitmap given
    public void printBitmap(char[][] bitmap, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == (cols - 1)) {
                    System.out.println(bitmap[i][j]);
                }
                if (j != (cols - 1)) {
                    System.out.print(bitmap[i][j]);
                }
            }
        }
    }

    //menu for selecting options to work with triangles in triangleArray
    public int triangleOptions(Scanner scanner) {
        System.out.println("Please select an option. Type 4 to go back to the previous menu.");
        System.out.println("1. Add a new Triangle");
        System.out.println("2. Edit a triangle");
        System.out.println("3. Remove a triangle");
        System.out.println("4. Go back");
        int drawingInt = scanner.nextInt();
        if (drawingInt < 1 || drawingInt > 4) {
            System.out.println("Unsupported option. Please try again.");
        }
        return drawingInt;
    }

    //main menu for freestyle mode
    public int freestyleMenu(Scanner scanner) {
        System.out.println("Please select an option. Type 3 to go back to the main menu.");
        System.out.println("1. Start/edit your current canvas");
        System.out.println("2. Share your current drawing");
        System.out.println("3. Go back to the main menu");
        int inputIntTwo = scanner.nextInt();
        if (inputIntTwo < 1 || inputIntTwo > 3) {
            System.out.println("Unsupported option. Please try again.");
        }
        return inputIntTwo;
    }

    //check results of current drawing
    public void checkResult(char[][] bitmap, DrawingCanvas drawingCanvas, int rows, int cols, DrawingTask drawingTask) {
        boolean testResult = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (drawingCanvas.getArray()[j][i] != bitmap[i][j]) {
                    testResult = false;
                }
            }
        }
        if (testResult == true) {
            System.out.printf("You successfully complete the drawing task. Congratulations!!%nThis is the sample drawing:%n");
            drawingTask.printBitmap(bitmap, rows, cols);
            System.out.println("And this is your drawing:");
            drawingCanvas.printCanvas(drawingCanvas);
        }
        if (testResult == false) {
            System.out.printf("Not quite! Please edit your canvas and check the result again.%nThis is the sample drawing:%n");
            drawingTask.printBitmap(bitmap, rows, cols);
            System.out.println("And this is your drawing:");
            drawingCanvas.printCanvas(drawingCanvas);
        }
    }

    //private methods
    //edit sidelength of triangle in trianglearray
    private void zooming(DrawingTask drawingTask, int editingTriangle, DrawingCanvas drawingCanvas, DrawingCanvas tempDrawingCanvas, Scanner scanner) {
        int zoomInMode = 0;
        while (zoomInMode == 0) {
            System.out.println("Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving/Rotating menu.");
            String zoomMode = scanner.next().toLowerCase();
            if (zoomMode.equals("o")) { //zooming out
                if ((drawingTask.getTriangle(editingTriangle).getSideLength() - 1) < 1) {
                    System.out.println("This triangle reaches its limit. You cannot make it smaller!");
                } else if ((drawingTask.getTriangle(editingTriangle).getSideLength() - 1) >= 1) {
                    drawingTask.getTriangle(editingTriangle).setSideZoomOut(drawingTask.getTriangle(editingTriangle));
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (zoomMode.equals("i")) { //zooming in
                if ((drawingTask.getTriangleX(editingTriangle) + drawingTask.getTriangle(editingTriangle).getSideLength()) == drawingCanvas.getHeight() || (drawingTask.getTriangleY(editingTriangle) + drawingTask.getTriangle(editingTriangle).getSideLength()) == drawingCanvas.getWidth()) {
                    System.out.println("This triangle reaches its limit. You cannot make it bigger!");
                } else {
                    drawingTask.getTriangle(editingTriangle).setSideZoomIn(drawingTask.getTriangle(editingTriangle));
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (!(zoomMode.equals("o") || zoomMode.equals("i"))) {
                zoomInMode = 1;
            }
        }
    }

    //edit x/y coordinates in trianglePosition arraylist to move triangle
    private void moving(DrawingTask drawingTask, int editingTriangle, DrawingCanvas drawingCanvas, DrawingCanvas tempDrawingCanvas, Scanner scanner) {
        int moveMode = 0;
        while (moveMode == 0) {
            System.out.println("Type A/S/W/Z to move left/right/up/down. Use other keys to go back to the Zooming/Moving/Rotating menu.");
            String moveMenu = scanner.next().toLowerCase();
            if (!(moveMenu.equals("a") || moveMenu.equals("s") || moveMenu.equals("w") || moveMenu.equals("z"))) {
                moveMode = 1;
            }
            if (moveMenu.equals("w")) { //moving up
                if ((drawingTask.getTriangleX(editingTriangle) - 1) < 0) {
                    System.out.println("You cannot move this triangle outside of the drawing canvas!");
                } else {
                    drawingTask.setTriangleX(editingTriangle, drawingTask.getTriangleX(editingTriangle) - 1);
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (moveMenu.equals("z")) { //moving down
                if ((drawingTask.getTriangleX(editingTriangle) + drawingTask.getTriangle(editingTriangle).getSideLength()) > drawingCanvas.getHeight() - 1) {
                    System.out.println("You cannot move this triangle outside of the drawing canvas!");
                } else {
                    drawingTask.setTriangleX(editingTriangle, drawingTask.getTriangleX(editingTriangle) + 1);
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (moveMenu.equals("a")) { //moving left
                if ((drawingTask.getTriangleY(editingTriangle) - 1) < 0) {
                    System.out.println("You cannot move this triangle outside of the drawing canvas!");
                } else {
                    drawingTask.setTriangleY(editingTriangle, drawingTask.getTriangleY(editingTriangle) - 1);
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (moveMenu.equals("s")) { //moving right
                if ((drawingTask.getTriangleY(editingTriangle) + drawingTask.getTriangle(editingTriangle).getSideLength() + 1) > drawingCanvas.getWidth()) {
                    System.out.println("You cannot move this triangle outside of the drawing canvas!");
                } else {
                    drawingTask.setTriangleY(editingTriangle, drawingTask.getTriangleY(editingTriangle) + 1);
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
        }
    }

    //edit the rotation degree of triangles saved in triangleArray arraylist
    private void rotating(DrawingTask drawingTask, int editingTriangle, DrawingCanvas drawingCanvas, DrawingCanvas tempDrawingCanvas, Scanner scanner) {
        int rotateMode = 0;
        while (rotateMode == 0) {
            System.out.println("Type R/L to rotate clockwise/anti-clockwise. Use other keys to go back to the Zooming/Moving/Rotating menu.");
            String rotateMenu = scanner.next().toLowerCase();
            if (!(rotateMenu.equals("r") || rotateMenu.equals("l"))) {
                rotateMode = 1;
            }
            if (rotateMenu.equals("r")) { //right rotation
                if ((drawingTask.getTriangle(editingTriangle).getRotationDegree() + 1) > 3) { //reset rotation to 0 if full 360 has been done
                    drawingTask.getTriangle(editingTriangle).setRotationDegree(0);
                } else {
                    drawingTask.getTriangle(editingTriangle).setRotationDegree((drawingTask.getTriangle(editingTriangle).getRotationDegree() + 1));
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
            if (rotateMenu.equals("l")) {//left rotation
                if ((drawingTask.getTriangle(editingTriangle).getRotationDegree() - 1) < -3) {//reset rotation to 0 if full 360 has been done
                    drawingTask.getTriangle(editingTriangle).setRotationDegree(0);
                } else {
                    drawingTask.getTriangle(editingTriangle).setRotationDegree((drawingTask.getTriangle(editingTriangle).getRotationDegree() - 1));
                }
                tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
                drawingCanvas.replaceCanvas(tempDrawingCanvas);
                drawingCanvas.printCanvas(drawingCanvas);
            }
        }
    }
    
    //print out if canvas empty during edit or delete
    private void emptyCanvas(DrawingTask drawingTask, DrawingCanvas drawingCanvas,int editOrDelete) {
        DrawingCanvas tempDrawingCanvas;
        if(editOrDelete==1){
            System.out.println("The current canvas is clean; there are no shapes to edit!");
        }
        if(editOrDelete==0){
            System.out.println("The current canvas is clean; there are no shapes to remove!");
        }
        tempDrawingCanvas = new DrawingCanvas(drawingCanvas.getWidth(), drawingCanvas.getHeight(), drawingCanvas.getBackground(), drawingTask);
        drawingCanvas.replaceCanvas(tempDrawingCanvas);
        drawingCanvas.printCanvas(drawingCanvas);
    }
}

