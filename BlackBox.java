/**
 * Created by Sripath Mishra on 9/8/2017.
 */

import java.util.Random;
import java.util.Scanner;

/**
 * Project 2- BlackBox
 * <p>
 * This program allows the user to place the BlackBox game, which is played with a square, n by n matrix.
 * There are three difficulty level to this game: Easy, Medium, Hard
 * The game board will have three hidden balls placed in location.
 *
 * @author SooKyung Lee, lee2689@purdue.edu
 * @version 02/16/2018
 */
public class BlackBox {
    public static char box[][]; // The matrix for the game.
    public static int size;       // to store the number of rows and columns.
    public static int numball = 3;
    public static int numlink;
    public static boolean end;
    public static int score;
    public static int high_score = -1;
    public static int hloc[][];// to store the location of the ball where the hit was occurred
    public static int hrow;
    public static int hcol;
    public static int rloc[][];//to store the location of the ball where the reflection was occurred
    public static int rrow;
    public static int rcol;
    public static int dloc[][];//to store the location of the ball where the deflection was occurred
    public static int drow;
    public static int dcol;
    static Scanner scan;


    /**
     * The default constructor which places default values to the class variables
     */
    public BlackBox() {
        this.box = new char[0][0];
        this.size = 0;
        this.numball = 0;
        this.numlink = 0;
        this.end = false;
        this.score = 0;
    }

    /**
     * The parameterized constructor which places values to the class variables
     */
    public BlackBox(int size, int numball, int numlink, boolean end, int score) {
        this.box = new char[size][size];
        this.size = size;
        this.numball = numball;
        this.numlink = numlink;
        this.end = end;
        this.score = score;
    }

    public BlackBox(int hrow, int hcol, int rrow, int rcol, int drow, int dcol) {
        //for a new constructor to call non-static methods in check method
        /**hloc=new int [2][1];
         rloc=new int [2][1];
         dloc=new int [2][1];*/
        this.hloc = new int[hrow][hcol];
        this.rloc = new int[rrow][rcol];
        this.dloc = new int[drow][dcol];
    }

    /**
     * The main function takes input for the difficulty level and call the functions initialize(int) and
     * playgame()
     */
    public static void main(String[] args) {

        scan = new Scanner(System.in);
        String input;//for the user input of choosing the difficulty of the game
        boolean quit = false;
        boolean error;

        //Todo:start the game print the welcome message and ask for correct difficulty level.
        do {

            numball = 3;
            numlink = 0;
            end = false;

            System.out.println("Welcome to BlackBox Game. Please choose the difficulty level: easy/medium/hard" +
                    " or quit to end the game");
            do {
                input = scan.nextLine();

                if (input.equalsIgnoreCase("easy")
                        || input.equalsIgnoreCase("medium") || input.equalsIgnoreCase("hard")
                        || input.equalsIgnoreCase("quit")) {
                    error = false;
                } else {
                    error = true;
                }
            } while (error == true);

            if (input.equalsIgnoreCase("Easy")) {
                size = 7;
            } else if (input.equalsIgnoreCase("Medium")) {
                size = 9;
            } else if (input.equalsIgnoreCase("Hard")) {
                size = 10;
            }

            //Todo: end the game if the user says quit.
            else if (input.equalsIgnoreCase("quit")) {
                quit = true;
                break;
            } else {
                end = true;
                return;
            }


            //end = false;
            //Todo:call the functions initialize and playgame()
            //while (end!=true) {
            BlackBox blackboxgame = new BlackBox(size, numball, numlink, end, score);
            blackboxgame.initialize();
            playgame();

            // Todo: take care of high score
            if (score != 0) {
                if (high_score == -1) {
                    high_score = score;
                } else {
                    if (score < high_score) {
                        high_score = score;
                    }
                }
            }
        } while (numball != 0);

        if (quit == true) {
            if (high_score != -1) {
                System.out.println("Highest score =>" + high_score);
            } else {
                System.out.println("Highest score => none");
            }
        }
    }

    /**
     * The initialize function
     */
    public void initialize() {
        //Todo:initialize the Box[][]
        setSize(size);

        //Todo: place the 'X' and the '#'
        //to place the '#' in first row, column, and last row, column
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                box[i][0] = '#';//initialize the first column
                box[this.size - 1][i] = '#';//initialize the last row of the array
                box[0][j] = '#';//initialize the fist row
                box[i][this.size - 1] = '#';//initialize the column
                //box=box[i][j] + "      ";
            }
        }

        //to place the 'X' in the corners of the board
        box[0][0] = 'X';
        box[0][this.size - 1] = 'X';
        box[this.size - 1][0] = 'X';
        box[this.size - 1][this.size - 1] = 'X';

        // Todo: place 3 '0''s randomly.

        Random random = new Random();
        //for (int balls = 0; balls < 3; balls++)
        boolean randomduplicate;
        do {
            randomduplicate = false;
            int randomRow1 = (random.nextInt(size - 3)) + 2;
            int randomColumn1 = (random.nextInt(size - 3)) + 2;
            int randomRow2 = (random.nextInt(size - 3)) + 2;
            int randomColumn2 = (random.nextInt(size - 3)) + 2;
            int randomRow3 = (random.nextInt(size - 3)) + 2;
            int randomColumn3 = (random.nextInt(size - 3)) + 2;
            if (randomRow1 == randomRow2 && randomColumn1 == randomColumn2) {
                randomduplicate = true;
            } else if (randomRow1 == randomRow3 && randomColumn1 == randomColumn3) {
                randomduplicate = true;
            } else if (randomRow2 == randomRow3 && randomColumn2 == randomColumn3) {
                randomduplicate = true;
            } else {
                box[randomRow1][randomColumn1] = '0';
                box[randomRow2][randomColumn2] = '0';
                box[randomRow3][randomColumn3] = '0';
            }
        } while (randomduplicate == true);


        //}
    }

    /**
     * The printbox function prints out the matrix in a particular format as given in the handout.
     */
    public static void printbox() {
        //Todo:print the box in the correct order
        // for  5*5 example
        /* 1  2  3  4  5  6  7
         ======================
        1|X |# |# |# |# |# |X |
        2|# |  |  |  |  |  |# |
        3|# |  |  |  |  |  |# |
        4|# |  |  |  |  |  |# |
        5|# |  |  |  |  |  |# |
        6|# |  |  |  |  |  |# |
        7|X |# |# |# |# |# |X |
         ======================*/
        //place the guesses as they come and print the balls when the player enter submit.


        int rowcolumnnumber;
        for (int i = 1; i < size + 1; i++) {
            if (size == 7 || size == 9) {
                rowcolumnnumber = i;
                if (i == 1) {
                    System.out.print("  " + rowcolumnnumber);
                } else {
                    System.out.print("  " + rowcolumnnumber);
                }
            } else {
                rowcolumnnumber = i;
                if (i == 1) {
                    System.out.print("   " + rowcolumnnumber);
                } else {
                    System.out.print("  " + rowcolumnnumber);
                }
            }
        }
        System.out.println("");
        if (size == 7) {
            System.out.println("=======================");
        } else if (size == 9) {
            System.out.println("==============================");
        } else {
            System.out.println("=================================");
        }

        for (int i = 0; i < size; i++) {
            if (size == 7 || size == 9) {
                rowcolumnnumber = i;
                System.out.print((rowcolumnnumber + 1));
            } else {
                rowcolumnnumber = i;
                if (rowcolumnnumber + 1 == 10) {
                    System.out.print(rowcolumnnumber + 1 + "");
                } else {
                    System.out.print(rowcolumnnumber + 1 + " ");
                }

            }
            for (int j = 0; j < size; j++) {
                if (j < size) {
                    if (numlink != 0) {
                        if (box[i][j] == '0') {
                            if (end == true) {
                                System.out.print("|0 ");
                            } else {
                                System.out.print("|  ");
                            }
                        } else if (box[i][j] == '-' || box[i][j] == '?') {
                            System.out.print("|* ");
                        } else if (box[i][j] != '0' && box[i][j] != '#' && box[i][j] != 'X' && box[i][j] != '-' &&
                                box[i][j] != '?' && box[i][j] != 'H' && box[i][j] != 'R' && box[i][j] != numlink &&
                                box[i][j] != '1' && box[i][j] != '2' && box[i][j] != '3' && box[i][j] != '4' && box[i][j] != '5'
                                && box[i][j] != '6' && box[i][j] != '7' && box[i][j] != '8' && box[i][j] != '9') {
                            System.out.print("|" + box[i][j] + "  ");// to add two spaces in blank array index
                        } else if (box[i][j] != '0' && box[i][j] != '-') {
                            System.out.print("|" + box[i][j] + " "); // to only add one space if the array has a character in it
                        }
                        if (j == size - 1) {
                            System.out.println("|");
                        }

                    } else {// if there is no deflection nor straightray found
                        if (box[i][j] == '0') {
                            if (end == true) {
                                System.out.print("|0 ");
                            } else {
                                System.out.print("|  ");
                            }
                        } else if (box[i][j] == '-' || box[i][j] == '?') {
                            System.out.print("|* ");
                        } else if (box[i][j] != '0' && box[i][j] != '#' && box[i][j] != 'X' && box[i][j] != '-' &&
                                box[i][j] != '?' && box[i][j] != 'H' && box[i][j] != 'R' && box[i][j] != 'n') {
                            System.out.print("|" + box[i][j] + "  ");
                        } else if (box[i][j] != '0' && box[i][j] != '-') {
                            System.out.print("|" + box[i][j] + " ");
                        }
                        if (j == size - 1) {
                            System.out.println("|");
                        }

                    }
                }
            }
        }
        if (size == 7) {
            System.out.println("=======================");
        } else if (size == 9) {
            System.out.println("==============================");
        } else {
            System.out.println("=================================");
        }
    }

    /**
     * The playgame funtion opens the first cell and is the main controller for the game. It calls various function when needed.
     */
    public static void playgame() {
        //Todo:Take input of a guess or hint from the user.
        printbox();
        while (end == false) {

            String guess;// the input by the user for the guess of the location

            int ballrow = 0;//the row number of the guess
            int ballcolumn = 0;// the column number of the guess
            int coordinatesep; //index of comma for integer pair
            int count = 0;
            boolean error = false;

            Scanner scan = new Scanner(System.in);
            //printbox();

            System.out.print("Choose the new coordinates (row,column) to play the next step or say submit/quit to end the game:");
            do {
                guess = scan.nextLine();
                if (guess.equalsIgnoreCase("") || guess.equalsIgnoreCase("submit")
                        || guess.equalsIgnoreCase("quit") || guess.indexOf(",") == 1 || guess.indexOf(",") == 2) {
                    error = false;
                } else {
                    error = true;
                }
            } while (error == true);
            if (guess.equalsIgnoreCase("submit")) {
                if (numball == 0) {
                    //if (numball == 3) {
                    //return a statement whether the player found all of the ball or not
                    end = true;
                    printbox();
                    //to display fail or success
                    for (int checkr = 1; checkr < size; checkr++) {// check if the user guessed all three balls correctly
                        for (int checkc = 1; checkc < size; checkc++) {
                            if (box[checkr][checkc] == '-') {
                                count++;
                                System.out.println();
                            }
                        }
                    }// end of for loop
                    if (count != 3) {
                        System.out.println("Failed");
                        score = 0;
                    } else {
                        System.out.println("Thank you for playing the game! Your score is: " + score);//return the score
                    }
                    numball = 3;
                    return;
                } else {
                    System.out.println("Please place all your guess-balls");
                    printbox();
                }
            } else if (guess.equalsIgnoreCase("quit")) {
                numball = -2;// to indicate that the game has completely ended
                if (high_score != -1) {
                    System.out.println("Highest score =>" + high_score);
                } else {
                    System.out.println("Highest score => none");
                }
                break;
            } else {
                coordinatesep = guess.indexOf(",");
                //the row number and column number ranges from 0 to size-1
                ballrow = Integer.parseInt(guess.substring(0, coordinatesep)) - 1;
                ballcolumn = Integer.parseInt(guess.substring(coordinatesep + 1, guess.length())) - 1;
            }


            //Todo:Check for valid input
            // unnecessary

            //Todo:call required functions
            check(ballrow, ballcolumn);
            printbox();
            //Todo:keep tab on score.
            score++;

        }//end of while
    }//end of playgame

    /**
     * The check function takes in the row and column in the matrix, checks for Hit (H), Reflection (R) or Divergence(#num)
     */
    public static void check(int i, int j) {
        BlackBox checknew = new BlackBox(1, 2, 1, 2, 1, 2);
        //Todo:place a guess when the input of i and j are valid
        //checking the input whether they are valid or not
        boolean invalid = true;
        boolean newguess = false;
        String guess;
        int coordinatesep;
        int c = 0;
        int r = 0;
        int count = 0;
        int checkcount = 0;

        while (invalid == true) {
            if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
                invalid = true;
                newguess = true;
            } else if (i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1) {//checking whether the input is within the array range
                //checking whether the inputs are the corners
                if (i == 0 && j == 0) {
                    invalid = true;
                    newguess = true;
                } else if (i == 0 && j == size - 1) {
                    invalid = true;
                    newguess = true;
                } else if (i == size - 1 && j == 0) {
                    invalid = true;
                    newguess = true;
                } else if (i == size - 1 && j == size - 1) {
                    invalid = true;
                    newguess = true;
                } else if (i == 0 || j == 0 || i == size - 1 || j == size - 1) {
                    //Todo:Check for a Hit

                    if (checknew.hitcheck(i, j) == true) {
                        checkcount++;
                    }
                    if (checknew.hitcheck(i, j) == true && checknew.reflectionCheck(i, j) != true && checknew.deflectionCheck(i, j) != true) {
                        box[i][j] = 'H';
                        break;
                    }
                    //Todo:Check for a reflection
                    if (checknew.reflectionCheck(i, j) == true) {
                        checkcount++;
                    }
                    if (checknew.reflectionCheck(i, j) == true && checknew.deflectionCheck(i, j) != true && checknew.hitcheck(i, j) != true) {
                        box[i][j] = 'R';
                        break;
                    }
                    //Todo:Check for a bounce
                    System.out.println();
                    if (checknew.deflectionCheck(i, j) == true) {
                        checkcount++;
                    }
                    if (checknew.deflectionCheck(i, j) == true && checknew.reflectionCheck(i, j) != true && checknew.hitcheck(i, j) != true) {
                        checknew.setNumlink(1);
                        char numlinkchar = (Integer.toString(numlink)).charAt(0);
                        checknew.getDloc();
                        if (j == 0 || j == size - 1) {
                            if (dloc[0][0] >= i) {
                                box[i][j] = numlinkchar;
                                if (j == 0) {
                                    box[0][dloc[0][1] - 1] = numlinkchar;
                                } else {
                                    box[0][dloc[0][1] + 1] = numlinkchar;
                                }
                            } else {
                                box[i][j] = numlinkchar;
                                if (j == 0) {
                                    box[size - 1][dloc[0][1] - 1] = numlinkchar;
                                } else {
                                    box[size - 1][dloc[0][1] + 1] = numlinkchar;
                                }
                            }
                            break;
                        } else {
                            if (dloc[0][1] >= j) {
                                box[i][j] = numlinkchar;
                                if (i == 0) {
                                    box[dloc[0][0] - 1][0] = numlinkchar;
                                } else {
                                    box[dloc[0][0] + 1][0] = numlinkchar;
                                }
                            } else {
                                box[i][j] = numlinkchar;
                                if (i == 0) {
                                    box[dloc[0][0] - 1][size - 1] = numlinkchar;
                                } else {
                                    box[dloc[0][0] + 1][size - 1] = numlinkchar;
                                }
                            }
                            break;
                        }
                    }
                    if (checknew.straightRay(i, j) == true && checknew.reflectionCheck(i, j) != true
                            && checknew.deflectionCheck(i, j) != true && checknew.hitcheck(i, j) != true) {
                        checknew.setNumlink(1);
                        char numlinkchar = (Integer.toString(numlink)).charAt(0);
                        if (j == 0 || j == size - 1) {
                            box[i][0] = numlinkchar;
                            box[i][size - 1] = numlinkchar;
                        } else {
                            box[0][j] = numlinkchar;
                            box[size - 1][j] = numlinkchar;
                        }
                        break;
                    }
                    if (checkcount >= 2) {
                        if (checknew.locationball(i, j) == "hitcheck") {
                            box[i][j] = 'H';
                            return;
                        } else if (checknew.locationball(i, j) == "reflectionCheck") {
                            box[i][j] = 'R';
                            break;
                        } else if (checknew.locationball(i, j) == "deflectionCheck") {
                            checknew.setNumlink(1);
                            char numlinkchar = (Integer.toString(numlink)).charAt(0);
                            checknew.getDloc();
                            if (j == 0 || j == size - 1) {
                                if (dloc[0][0] >= i) {
                                    box[i][j] = numlinkchar;
                                    if (j == 0) {
                                        box[0][dloc[0][1] - 1] = numlinkchar;
                                    } else {
                                        box[0][dloc[0][1] + 1] = numlinkchar;
                                    }
                                } else {
                                    box[i][j] = numlinkchar;
                                    if (j == 0) {
                                        box[size - 1][dloc[0][1] - 1] = numlinkchar;
                                    } else {
                                        box[size - 1][dloc[0][1] + 1] = numlinkchar;
                                    }
                                }
                                break;
                            } else {
                                if (dloc[0][1] >= j) {
                                    box[i][j] = numlinkchar;
                                    if (i == 0) {
                                        box[dloc[0][0] - 1][0] = numlinkchar;
                                    } else {
                                        box[dloc[0][0] + 1][0] = numlinkchar;
                                    }
                                } else {
                                    box[i][j] = numlinkchar;
                                    if (i == 0) {
                                        box[dloc[0][0] - 1][size - 1] = numlinkchar;
                                    } else {
                                        box[dloc[0][0] + 1][size - 1] = numlinkchar;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }// the input is within the array range, but not for a ball placement//end of if
                else if (checknew.placeball(i, j) == true && numball != 0) {
                    if (box[i][j] != '0') {
                        box[i][j] = '?';
                        invalid = false;
                        checknew.setNumball(1);
                    } else {
                        box[i][j] = '-';
                        invalid = false;
                        checknew.setNumball(1);
                    }
                } else {
                    //Todo:Print the statement telling the user they cannot place 4th or more ball.
                    System.out.println("You cannot place 4th ball.");
                    break;
                }//end of else
            }//end of checking whether the input is within the array range
            while (newguess == true) {
                System.out.println("Please enter a valid location of a ball");
                guess = scan.nextLine();
                coordinatesep = guess.indexOf(",");
                i = Integer.parseInt(guess.substring(0, coordinatesep)) - 1;
                j = Integer.parseInt(guess.substring(coordinatesep + 1, guess.length())) - 1;
                newguess = false;
            }//end of while of newguess
        }//end of while of invalid

    }//end of check

    /**
     * The placeball method checks if a guess ball can be placed at a given row and column.
     */
    public boolean placeball(int i, int j) {
        //return false if a ball is already there, else it returns true
        if (box[i][j] != '-' && box[i][j] != '?') {//checking if the input has already been placed
            return true;
        } else {
            return false;
        }
    }

    public String locationball(int i, int j) {
        boolean ballfound = false;
        String checkfirst = "";
        int closest = 0;

        if (hitcheck(i, j) == true && reflectionCheck(i, j) == true && deflectionCheck(i, j) != true) {
            if (j == 0) {
                if (hloc[0][1] <= rloc[0][1]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "reflectionCheck";
                }
            } else if (j == size - 1) {
                if (hloc[0][1] >= rloc[0][1]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "reflectionCheck";
                }
            } else if (i == 0) {
                if (hloc[0][0] >= rloc[0][0]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "reflectionCheck";
                }
            } else {
                if (hloc[0][0] >= rloc[0][0]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "reflectionCheck";
                }
            }
        } else if (reflectionCheck(i, j) == true && deflectionCheck(i, j) == true && hitcheck(i, j) != true) {
            if (j == 0) {
                if (rloc[0][1] <= dloc[0][1]) {
                    checkfirst = "reflectionCheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else if (j == size - 1) {
                if (rloc[0][1] >= dloc[0][1]) {
                    checkfirst = "reflectionCheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else if (i == 0) {
                if (rloc[0][0] <= dloc[0][0]) {
                    checkfirst = "reflectionCheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else {
                if (rloc[0][0] >= dloc[0][0]) {
                    checkfirst = "reflectionCheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            }
        } else if (deflectionCheck(i, j) == true && hitcheck(i, j) == true && reflectionCheck(i, j) != true) {
            if (j == 0) {
                if (hloc[0][1] <= dloc[0][1]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else if (j == size - 1) {
                if (hloc[0][1] >= dloc[0][1]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else if (i == 0) {
                if (hloc[0][0] <= dloc[0][0]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            } else {
                if (hloc[0][0] >= dloc[0][0]) {
                    checkfirst = "hitcheck";
                } else {
                    checkfirst = "deflectionCheck";
                }
            }
        } else if (deflectionCheck(i, j) == true && hitcheck(i, j) == true && reflectionCheck(i, j) == true) {
            if (j == 0 || j == size - 1) {
                if (j == 0) {
                    closest = Math.min(hloc[0][1], Math.min(dloc[0][1], rloc[0][1]));
                } else if (j == size - 1) {
                    closest = Math.max(hloc[0][1], Math.max(dloc[0][1], rloc[0][1]));
                }
                if (closest == hloc[0][1]) {
                    checkfirst = "hitcheck";
                } else if (closest == dloc[0][1] && closest != hloc[0][1]) {
                    checkfirst = "deflectionCheck";
                } else if (closest == rloc[0][1] && closest != hloc[0][1] && closest != dloc[0][1]) {
                    checkfirst = "reflectionCheck";
                }
            }
            if (i == 0 || i == size - 1) {
                if (i == 0) {
                    closest = Math.min(hloc[0][0], Math.min(dloc[0][0], rloc[0][0]));
                } else {
                    closest = Math.max(hloc[0][0], Math.max(dloc[0][0], rloc[0][0]));
                }

                if (closest == hloc[0][0]) {
                    checkfirst = "hitcheck";
                } else if (closest == dloc[0][0] && closest != hloc[0][0]) {
                    checkfirst = "deflectionCheck";
                } else if (closest == rloc[0][0] && closest != hloc[0][0] && closest != dloc[0][0]) {
                    checkfirst = "reflectionCheck";
                }
            }
        }
        return checkfirst;
    }//end of locationball method

    public boolean hitcheck(int i, int j) {
        //todo: check if the ray causes a HIT as defined in the handout
        //return true if the beam ends in a hit. Otherwise, return false

        if (j == 0) {//if the column is 1, checking horizontally
            for (int c = 1; c < size - 1; c++) {
                if (box[i][c] == '0' || box[i][c] == '-') {
                    hloc[0][0] = i;
                    hloc[0][1] = c;
                    return true;
                }
            }// end of checking if the column is 1
        } else if (j == size - 1) {//if the column is 7, checking horizontally
            for (int c = size - 2; c > 0; c--) {//check the previous row, current row, and the next row
                if (box[i][c] == '0' || box[i][c] == '-') {
                    hloc[0][0] = i;
                    hloc[0][1] = c;
                    return true;
                }
            }
        }//end of checking if the column is 7
        if (i == 0) {// if the row is 1, checking vertically
            for (int r = 1; r < size - 1; r++) {
                if (box[r][j] == '0' || box[r][j] == '-') {
                    hloc[0][0] = r;
                    hloc[0][1] = j;
                    return true;
                }
            }//end of checking if the row is 1
        } else if (i == size - 1) {//if the row is 7, checking vertically backwards
            for (int r = size - 2; r > 0; r--) {
                //check the previous row, current row, and the next row
                if (box[r][j] == '0' || box[r][j] == '-') {
                    hloc[0][0] = r;
                    hloc[0][1] = j;
                    return true;
                }
            }
        }
        return false;
    }//end of hitcheck


    /**
     * The check function takes in the row and column in the matrix, checks for Reflection (R)
     */
    public boolean reflectionCheck(int i, int j) {
        //todo: check if the ray causes a Reflection as defined in the handout
        //return true if the beam ends in a reflection. Otherwise, return false

        //when two balls are one space away from each other and either directly horizontal or vertical with respect
        //to the other, any beam that enters the space immediately before the gap will be returned to its entry port
        if (j == 0) {//if the column is 1, checking horizontally
            for (int c = 1; c < size - 2; c++) {
                if (c == 1) {
                    if (i == 1) {//if the row is 2, check only that row and the row below
                        if ((box[i][c] != '0' && box[i][c] != '-') && (box[i + 1][c] == '0' || box[i + 1][c] == '-')) {//check if there is no ball in front, but diagonally
                            rloc[0][0] = i;
                            rloc[0][1] = c - 1;
                            return true;
                        } else {
                            return false;
                        }
                    } else if (i == size - 2) {//if the row is last row, check only that row and the row above
                        if ((box[i - 1][c] == '0' || box[i - 1][c] == '-') && (box[i][c] != '0' && box[i][c] != '-')) {
                            rloc[0][0] = i;
                            rloc[0][1] = c - 1;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((box[i - 1][c] == '0' || box[i - 1][c] == '-') && (box[i + 1][c] == '0' || box[i + 1][c] == '-')) {//check if there is a ball in each side
                            rloc[0][0] = i;
                            rloc[0][1] = c;
                            return true;
                        }
                    }
                } else // if the row is not 2 nor the last row, check the row above and row below
                    if ((box[i - 1][c + 1] == '0' || box[i - 1][c + 1] == '-') && (box[i + 1][c + 1] == '0' || box[i + 1][c + 1] == '-')) {//check if there is a ball in each side
                        rloc[0][0] = i;
                        rloc[0][1] = c + 1;
                        return true;
                    }
            }
            // end of checking when the column is 1
        } else if (j == size - 1) {//if the column is last column, checking horizontally from the bottom
            for (int c = size - 2; c > 1; c--) {//check the previous row, current row, and the next row
                if (c == size - 2) {
                    if (i == 1) {// if the row is 2, check only that row and the row below
                        if ((box[i + 1][c] == '0' || box[i + 1][c] == '-') && (box[i][c] != '0' && box[i][c] != '-')) {//check if there is no ball front, but diagonally
                            rloc[0][0] = i;
                            rloc[0][1] = c + 1;
                            return true;
                        } else {
                            return false;
                        }
                    } else if (i == size - 2) {//if the row is the last row, check only that row and the row above
                        if ((box[i - 1][c] == '0' || box[i - 1][c] == '-') && (box[i][c] != '0' && box[i][c] != '-')) {//check if there is no ball in front, but diagonally
                            rloc[0][0] = i;
                            rloc[0][1] = c + 1;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((box[i - 1][c] == '0' || box[i - 1][c] == '-') || (box[i + 1][c] == '0' || box[i + 1][c] == '-')) {//check if there is a ball in each side
                            rloc[0][0] = i;
                            rloc[0][1] = c;
                            return true;
                        }
                    }
                } else {//if the row is not 2 and not the last row, check the row above and row below
                    if ((box[i - 1][c - 1] == '0' || box[i - 1][c - 1] == '-') && (box[i + 1][c - 1] == '0' || box[i + 1][c - 1] == '-')) {//check if there is a ball in each side
                        rloc[0][0] = i;
                        rloc[0][1] = c + 1;
                        return true;
                    }
                }
            }
        }// end of checking when the column is the last column
        else if (i == 0) {// if the row is 1, checking vertically
            for (int r = 1; r < size - 2; r++) {
                if (r == 1) {
                    if (j == 1) {//if the column is 2, check only that column and the column on the right
                        if ((box[r][j + 1] == '0' || box[r][j + 1] == '-') && (box[r][j] != '0' && box[r][j] != '-')) {//check if there is no ball in front, but diagonally
                            rloc[0][0] = r - 1;
                            rloc[0][1] = j;
                            return true;
                        } else {
                            return false;
                        }
                    } else if (j == size - 2) {//if the column is the last column, check only that column and the column on the left
                        if ((box[r][j - 1] == '0' || box[r][j - 1] == '-') && (box[r][j] != '0' && box[r][j] != '-')) {//check if there is no ball in front, but diagonally
                            rloc[0][0] = r - 1;
                            rloc[0][1] = j;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((box[r][j + 1] == '0' || box[r][j + 1] == '-') || (box[r][j - 1] == '0' || box[r][j - 1] == '0' || box[r][j - 1] == '0')) {
                            rloc[0][0] = r;
                            rloc[0][1] = j;
                            return true;
                        }
                    }
                } else {// if the column is not 2 and not the last column, check the column on the left and column on the right
                    if ((box[r + 1][j + 1] == '0' || box[r + 1][j + 1] == '-') && (box[r + 1][j - 1] == '0' || box[r + 1][j - 1] == '-')) {
                        rloc[0][0] = r + 1;
                        rloc[0][1] = j;
                        return true;
                    }
                }
            }
        } else if (i == size - 1) {//if the row is the last row, checking vertically backwards
            for (int r = size - 2; r > 1; r--) {
                //check the previous row, current row, and the next row
                if (r == size - 2) {
                    if (j == 2) {//if the column is 2, check only that column and the column on the right
                        if ((box[r][j] != '0' && box[r][j] != '-') && (box[r][j + 1] == '0' || box[r][j + 1] == '-')) {//check if there is no ball in front, but diagonally
                            rloc[0][0] = r + 1;
                            rloc[0][1] = j;
                            return true;
                        } else {
                            return false;
                        }
                    } else if (j == size - 2) {//if the column is the last row, check only that column and the column on the left
                        if ((box[r][j - 1] == '0' || box[r][j - 1] == '-') && (box[r][j] != '0' && box[r][j] != '-')) {
                            rloc[0][0] = r + 1;
                            rloc[0][1] = j;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((box[r][j - 1] == '0' || box[r][j - 1] == '-') || (box[r][j + 1] == '0' || box[r][j + 1] == '-')) {
                            rloc[0][0] = r;
                            rloc[0][1] = j;
                            return true;
                        }
                    }
                } else {//if the column is not 2, nor the last column, check the column on the left and column on the right
                    if ((box[r - 1][j - 1] == '0' || box[r - 1][j - 1] == '-') && (box[r - 1][j + 1] == '0' || box[r - 1][j + 1] == '=')) {
                        rloc[0][0] = r + 1;
                        rloc[0][1] = j;
                        return true;
                    }
                }
            }
        }
        return false;
    }//end of reflectionCheck

    /**
     * The check function takes in the row and column in the matrix, checks for Divergence(#num)
     */
    public boolean deflectionCheck(int i, int j) {
        //todo: check if the ray causes a Deflection as defined in the handout
        //return true if the beam results in a deflection. Otherwise, return false
        //
        if (j == 0) {//if the column is 1, checking horizontally
            for (int c = 1; c < size - 2; c++) {
                if (i == 1) {//if the row is 2, check the row below and the column to the right
                    if ((box[i][c + 1] != '0' && box[i][c + 1] != '-') && (box[i + 1][c + 1] == '0' || box[i + 1][c + 1] == '-')) {//check if there is no ball in front and only diagonally
                        dloc[0][0] = i + 1;
                        dloc[0][1] = c + 1;
                        return true;
                    }
                } else if (i == size - 2) {//if the row is the last row, check the row above and the column to the right
                    if ((box[i - 1][c + 1] == '0' || box[i - 1][c + 1] == '-') && (box[i][c + 1] != '0' && box[i][c + 1] != '-')) {//check if there is no ball in front and only diagonally
                        dloc[0][0] = i - 1;
                        dloc[0][1] = c + 1;
                        return true;
                    }
                } else {//if the row is neither second row nor the last row
                    if (box[i][c + 1] != '0' && box[i][c + 1] != '-') {//check if there is no ball in front
                        if ((box[i + 1][c + 1] != '0' && box[i + 1][c + 1] != '-') && (box[i - 1][c + 1] == '0' || box[i - 1][c + 1] == '-')) {//check if there is a ball only to the left
                            dloc[0][0] = i - 1;
                            dloc[0][1] = c + 1;
                            return true;
                        } else if ((box[i + 1][c + 1] == '0' || box[i + 1][c + 1] == '-') && (box[i - 1][c + 1] != '0' && box[i - 1][c + 1] != '-')) {//check if there is a ball only to the right
                            dloc[0][0] = i + 1;
                            dloc[0][1] = c + 1;
                            return true;
                        }
                    } else if (box[i][c + 1] == '0' || box[i][c + 1] == '-') {
                        return false;
                    }
                }
            }// end of checking when the column is 1
        } else if (j == size - 1) {//if the column is 7, checking horizontally
            for (int c = size - 2; c > 1; c--) {//check the previous row, current row, and the next row
                if (i == 1) {// if the row is 2, check the row below and column to the left
                    if ((box[i][c - 1] != '0' && box[i][c - 1] != '-') && (box[i + 1][c - 1] == '0' || box[i + 1][c - 1] == '-')) {//check if there is no ball in front
                        dloc[0][0] = i + 1;
                        dloc[0][1] = c - 1;
                        return true;
                    }
                } else if (i == size - 2) {//if the row is the last row, check if the row above and the column to the left
                    if ((box[i][c - 1] != '0' && box[i][c - 1] != '-') && (box[i - 1][c - 1] == '0' || box[i - 1][c - 1] == '-')) {//check if there is no ball in front and only diagonally
                        dloc[0][0] = i - 1;
                        dloc[0][1] = c - 1;
                        return true;
                    }
                } else {
                    if (box[i][c - 1] != '0' && box[i][c - 1] != '-') {//check if there is no ball in front
                        if ((box[i + 1][c - 1] != '0' || box[i + 1][c - 1] != '-') && (box[i - 1][c - 1] == '0' || box[i - 1][c - 1] == '-')) {//if there is a ball only to the left
                            dloc[0][0] = i - 1;
                            dloc[0][1] = c - 1;
                            return true;
                        }
                        if ((box[i + 1][c - 1] == '0' || box[i + 1][c - 1] == '-') && (box[i - 1][c - 1] != '0' && box[i - 1][c - 1] != '-')) {//if there is a ball only to the right
                            dloc[0][0] = i + 1;
                            dloc[0][1] = c - 1;
                            return true;
                        }
                    } else if (box[i][c - 1] == '0' || box[i][c - 1] == '-') {
                        return false;
                    }
                }
            }
        } else if (i == 0) {// if the row is 1, checking vertically
            for (int r = 1; r < size - 2; r++) {
                if (j == 1) {//if the column is 2
                    if ((box[r + 1][j] != '0' && box[r + 1][j] != '-') && (box[r + 1][j + 1] == '0' || box[r + 1][j + 1] == '-')) {//if the ball is on the left
                        dloc[0][0] = r + 1;
                        dloc[0][1] = j + 1;
                        return true;
                    }
                } else if (j == size - 2) {// if the column is the last column-1
                    if ((box[r + 1][j] != '0' && box[r + 1][j] != '-') && (box[r + 1][j - 1] == '0' || box[r + 1][j - 1] == '-')) {//if the ball is on the right
                        dloc[0][0] = r + 1;
                        dloc[0][1] = j - 1;
                        return true;
                    }
                } else {//if the column is neither the second column nor the last column
                    if (box[r + 1][j] != '0' && box[r + 1][j] != '-') {//if there is no ball in front
                        if ((box[r + 1][j] != '0' || box[r + 1][j] != '-') && (box[r + 1][j - 1] == '0' || box[r + 1][j - 1] == '-')) {//if the ball is on the left
                            dloc[0][0] = r + 1;
                            dloc[0][1] = j - 1;
                            return true;
                        } else if ((box[r + 1][j] != '0' && box[r + 1][j] != '-') && (box[r + 1][j + 1] == '0' || box[r + 1][j + 1] == '-')) {// if the ball is on the right
                            dloc[0][0] = r + 1;
                            dloc[0][1] = j + 1;
                            return true;
                        }
                    } else if (box[r + 1][j] == '0' || box[r + 1][j] == '-') {
                        return false;
                    }
                }
            }
        } else if (i == size - 1) {//if the row is 7, checking vertically backwards
            for (int r = size - 2; r > 1; r--) {
                //check the previous row, current row, and the next row
                if (j == 1) {//if the column is the second column
                    if ((box[r - 1][j] != '0' && box[r - 1][j] != '-') && (box[r - 1][j + 1] == '0' || box[r - 1][j + 1] == '-')) {//if the ball is on the right
                        dloc[0][0] = r - 1;
                        dloc[0][1] = j + 1;
                        return true;
                    }
                } else if (j == size - 2) {//if the column is the last column
                    if ((box[r - 1][j] != '0' && box[r - 1][j] != '-') && (box[r - 1][j - 1] == '0' || box[r - 1][j - 1] == '-')) {//if the ball is on the left
                        dloc[0][0] = r - 1;
                        dloc[0][1] = j - 1;
                        return true;
                    }
                } else {// if the column is neither the second column nor the last column
                    if (box[r - 1][j] != '0' && box[r - 1][j] != '-') {//there is no ball in front
                        if ((box[r - 1][j + 1] != '0' && box[r - 1][j + 1] != '-') && (box[r - 1][j - 1] == '0' || box[r - 1][j - 1] == '-')) {// if the ball is on left
                            dloc[0][0] = r - 1;
                            dloc[0][1] = j - 1;
                            return true;
                        } else if ((box[r - 1][j + 1] == '0' || box[r - 1][j + 1] == '-') && (box[r - 1][j - 1] != '0' && box[r - 1][j - 1] != '-')) {//if the ball is on the right
                            dloc[0][0] = r - 1;
                            dloc[0][1] = j + 1;
                            return true;
                        }
                    } else if (box[r - 1][j] == '0' || box[r - 1][j] == '-') {
                        return false;
                    }
                }
            }
        }
        return false;
    }//end of deflection check

    /**
     * The straightRay function takes in the row and column in the matrix, checks for Straight ray
     */
    public boolean straightRay(int i, int j) {
        //todo: check if the ray is a straight ray as defined in the handout
        //return true if the beam results in a straightRay. Otherwise, return false
        //beam that exists the game board after following a straight line from the original port

        if (j == 0 || j == size - 1) {//if the column is 1 or the column is 7, checking horizontally
            for (int c = 1; c < size - 1; c++) {
                if (box[i][c] == '0' || box[i][c] == '-') {
                    return false;
                } else if (c == size - 2) {
                    if (box[i][c] != '0' && box[i][c] != '-') {
                        return true;
                    } else {
                        return false;
                    }
                }
            }// end of checking if the column is 1
        } else if (i == 0 || i == size - 1) {//if the column is 7, checking horizontally
            for (int r = size - 2; r > 0; r--) {//check the previous row, current row, and the next row
                if (box[r][j] == '0' || box[r][j] == '-') {
                    return false;
                } else if (r == 1) {
                    if (box[r][j] != '0' && box[r][j] != '-') {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }//end of straight ray check

    /**
     * The following definitions are the getters and setter functions which have to be implemented
     */
    public char[][] getbox() {
        //returns box, an n-by-n array containing the '#'' and 'X' characters, the location of the balls,
        //and the location of the player guesses.
        return this.box;
    }

    public int getscore() {
        //returns the current score, the number of moves the player has made
        return this.score;
    }

    public int getNumball() {
        //returns numball, the number of balls remaining for the player to guess (initially 3)
        //numball=3;
        return numball;
    }

    public int getNumlink() {
        //returns numlink, the counter for the number of straight rays and deflections (initially 0)
        //numlink=0;
        return this.numlink;
    }

    public boolean getend() {
        //returns end, a variable signaling whether the current game has ended (user has submitted with three guesses)
        return end;
    }

    public int[][] getHloc() {
        return this.hloc;
    }

    public int[][] getRloc() {
        return this.rloc;
    }

    public int[][] getDloc() {
        return this.dloc;
    }

    public void setSize(int size) {
        //sets the size to the value given in the parameter
        /**if (size == 5) {
         this.size = 7;
         } else if (size == 7) {
         this.size = 9;
         } else if (size == 8) {
         this.size = 10;
         }*/
        //this.size=size+2;
        box = new char[this.size][this.size];
        setbox(box);
    }

    public void setbox(char box[][]) {
        //sets the box to the value given in the parameter
        this.box = new char[size][size];

    }

    public void setNumball(int Numball) {
        //sets the numball to the value given in the parameter
        if (numball > 0) {
            this.numball = this.numball - Numball;
        } else {
            this.numball = numball;
        }
    }

    public void setNumlink(int Numlink) {
        //sets the numlink to the value given in the parameter
        numlink = numlink + Numlink;

    }

    public void setEnd(boolean end) {
        //sets the end to the value given in the parameter
        this.end = end;

    }

    public void setScore(int score) {
        //sets the score to the value given in the parameter
        //score goes up for each of the inputs, not for the fourth ball
        this.score = this.score + score;

    }

    public void setHloc(int hloc[][]) {
        this.hloc = new int[hrow][hcol];
    }

    public void setRloc(int rloc[][]) {
        this.rloc = new int[rrow][rcol];
    }

    public void setDloc(int dloc[][]) {
        this.dloc = new int[drow][dcol];
    }
}
