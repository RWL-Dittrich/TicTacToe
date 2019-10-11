package com.saxion.robindittrich.tictactoe.game;

import com.saxion.robindittrich.tictactoe.managers.HueManager;

import java.io.IOException;
import java.util.Arrays;

import nl.mesoplz.hue.models.HueLight;

import static com.saxion.robindittrich.tictactoe.managers.HueManager.bridge;

public class Game {
    //3x3 huelight grid
    public static HueLight[][] lights = new HueLight[3][3];

    private char[][] board = new char[3][3];

    private boolean redTurn = true;

    public Game() {
        resetBoard();
    }


    public int nextTurn(int xPos, int yPos) {
        if (xPos < 0 || xPos > 2 || yPos < 0 || yPos > 2) {
            return -1;
        }

        if (board[xPos][yPos] != '0') {
            return -1;
        }

        if (redTurn) {
            board[xPos][yPos] = 'R';
            try {
                HueLight l = lights[xPos][yPos];
                l.setPower(true);
                l.setRGB(255, 0, 0, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            board[xPos][yPos] = 'B';
            try {
                HueLight l = lights[xPos][yPos];
                l.setPower(true);
                l.setRGB(0, 100, 255, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String result = checkWinner();
        redTurn = !redTurn;

        if (result == null) {
            return 0;
        }
        switch(result) {
            case "R":
                for (HueLight light : bridge.getLights()) {
                    try {
                        light.setPower(true);
                        light.setRGB(255, 0, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return 1;
            case "B":
                for (HueLight light : bridge.getLights()) {
                    try {
                        light.setPower(true);
                        light.setRGB(0, 100, 255);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return 2;
            case "D":
                for (HueLight light : bridge.getLights()) {
                    try {
                        light.setPower(true);
                        light.setRGB(0, 255, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return 3;
            default:
                return -1;
        }

    }


    private String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = "" + board[0][0] + board[1][0] + board[2][0];
                    break;
                case 1:
                    line = "" + board[0][1] + board[1][1] + board[2][1];
                    break;
                case 2:
                    line = "" + board[0][2] + board[1][2] + board[2][2];
                    break;
                case 3:
                    line = "" + board[0][0] + board[0][1] + board[0][2];
                    break;
                case 4:
                    line = "" + board[1][0] + board[1][1] + board[1][2];
                    break;
                case 5:
                    line = "" + board[2][0] + board[2][1] + board[2][2];
                    break;
                case 6:
                    line = "" + board[0][0] + board[1][1] + board[2][2];
                    break;
                case 7:
                    line = "" + board[0][2] + board[1][1] + board[2][0];
                    break;
            }
            if (line.equals("RRR")) {
                return "R";
            } else if (line.equals("BBB")) {
                return "B";
            }
        }
        if (!Arrays.asList(board).toString().contains("0")) {
            return "D";
        }

        return null;
    }


    public void resetBoard() {
        redTurn = true;
        for (HueLight light : bridge.getLights()) {
            try {
                light.setRGB(255, 255, 255, 5);
                light.setBri(254);
                light.setPower(false, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int countX = 0, countY = 0;
        //fill the game's board
        for (int i = 0; i < 3 * 3; i++) {
            if (countX == 3) {
                countY++;
                countX = 0;
            }
            board[countX][countY] = '0';
            HueLight l = lights[countX][countY];
            try {
                l.setPower(true, 5);
                l.setRGB(255, 255, 255, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            countX++;
        }
    }


    public static void instantiateLights() {
        int countX = 0, countY = 0;
        //fill the game's HueLight[][]
        for (int i = 0; i < 3 * 3; i++) {
            if (countX == 3) {
                countY++;
                countX = 0;
            }
            int lightId = HueManager.lightIds[countX][countY];
            for (HueLight l : bridge.getLights()) {
                if (l.getId() == lightId) {
                    Game.lights[countX][countY] = l;
                }
            }
            countX++;
        }
    }


}
