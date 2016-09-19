package com.example.borg.xandzero;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Button> buttons;
    private static final int[] BUTTON_IDS = {
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };

    Button newGame;

    private String[][] board = new String[3][3];

    boolean gameActive = false;
    boolean firstRun = true;
    boolean playerOneTurn=false;
    boolean playerTwoTurn=false;
    int count = 0;

    String marker = "";
    String scoring = "";
    TextView announcer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        announcer = (TextView) findViewById(R.id.textView);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j] = "";
            }
        }


        newGame = (Button) findViewById(R.id.buttonNew);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameActive == false && firstRun) {
                    gameActive = true;
                    firstRun = false;
                    newGame.setText("Stop");
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(true);
                    }
                    playerOneTurn = true;
                    setAnnouncerMessage();
                    setMarker();
                } else if(gameActive && firstRun == false){
                    gameActive = false;
                    newGame.setText("Restart");
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(false);
                        buttons.get(i).setText("");
                    }
                    playerOneTurn = false;
                    playerTwoTurn = false;

                    setAnnouncerMessage();
                    setMarker();
                    cleanup();
                }
                else if(gameActive == false & firstRun == false){
                    newGame.setText("Stop");

                    gameActive = true;
                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(true);
                        buttons.get(i).setText("");
                    }
                    playerOneTurn = true;
                    setAnnouncerMessage();
                    setMarker();
                }

            }
        });

        buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for(int i=0; i<BUTTON_IDS.length; i++) {
            final Button button = (Button)findViewById(BUTTON_IDS[i]);
            button.setText("");
            button.setEnabled(false);
            button.setTag(Integer.valueOf(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setText(marker);
                    if (playerOneTurn) {
                        scoreSet(Integer.valueOf(button.getTag().toString()));
                        playerOneTurn = false;
                        playerTwoTurn = true;
                        button.setTextColor(Color.BLUE);
                        setAnnouncerMessage();
                        setMarker();

                    } else if (playerTwoTurn) {
                        scoreSet(Integer.valueOf(button.getTag().toString()));
                        playerOneTurn = true;
                        playerTwoTurn = false;
                        button.setTextColor(Color.MAGENTA);
                        setAnnouncerMessage();
                        setMarker();
                    }
                    count ++;
                    button.setEnabled(false);
                    checkBoardState();
                }
            });
            buttons.add(button);
        }



    }
    public void setMarker(){
        if(playerOneTurn){
            marker="X";
        }
        else if(playerTwoTurn){
            marker="O";
        }
        else{
            marker = "";
        }
    }

    public void scoreSet(int x){
        if(playerOneTurn){
            scoring = "X";
        }
        else if(playerTwoTurn){
            scoring = "O";
        }
        else {
            scoring = "";
        }

        switch(x){
            case 0:
                board[0][0] = scoring;
                break;
            case 1:
                board[0][1] = scoring;
                break;
            case 2:
                board[0][2] = scoring;
                break;
            case 3:
                board[1][0] = scoring;
                break;
            case 4:
                board[1][1] = scoring;
                break;
            case 5:
                board[1][2] = scoring;
                break;
            case 6:
                board[2][0] = scoring;
                break;
            case 7:
                board[2][1] = scoring;
                break;
            case 8:
                board[2][2] = scoring;
                break;
        }

        Log.d("BoardCheck", "[" + board[0][0] + "]" + "[" + board[0][1] + "]" + "[" + board[0][2] + "] \n" +
                            "[" + board[1][0] + "]" + "[" + board[1][1] + "]" + "[" + board[1][2] + "] \n" +
                            "[" + board[2][0] + "]" + "[" + board[2][1] + "]" + "[" + board[2][2] + "]");

    }


    public void setAnnouncerMessage(){
        if(playerOneTurn && playerTwoTurn==false){
            announcer.setText("Player One");
        }
        else if(playerOneTurn==false && playerTwoTurn){
            announcer.setText("Player Two");
        }
        else{
            announcer.setText("");
        }
    }

    public void cleanup(){

        marker = "";
        scoring = "";

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j] = "";
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setEnabled(false);
        }
        gameActive = false;
        count = 0;
    }

    public void checkBoardState(){

        if(count == 9){
            String temp = "<font color='#EE0000'>Nobody won</font>";
            announcer.setText(Html.fromHtml(temp));
            newGame.setText("Restart");
            cleanup();
        }

        //Quick game logic player one
        if(board[0][0] == "X" && board[0][1] == "X" && board[0][2] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[1][0] == "X" && board[1][1] == "X" && board[1][2] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[2][0] == "X" && board[2][1] == "X" && board[2][2] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }

        else if(board[0][0] == "X" && board[1][0] == "X" && board[2][0] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][1] == "X" && board[1][1] == "X" && board[2][1] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][2] == "X" && board[1][2] == "X" && board[2][2] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }

        else if(board[0][0] == "X" && board[1][1] == "X" && board[2][2] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][2] == "X" && board[1][1] == "X" && board[2][0] == "X"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#0000EE'>PLAYER ONE</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }

        //Quick game logic player two
        if(board[0][0] == "O" && board[0][1] == "O" && board[0][2] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[1][0] == "O" && board[1][1] == "O" && board[1][2] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[2][0] == "O" && board[2][1] == "O" && board[2][2] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }

        else if(board[0][0] == "O" && board[1][0] == "O" && board[2][0] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][1] == "O" && board[1][1] == "O" && board[2][1] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][2] == "O" && board[1][2] == "O" && board[2][2] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }

        else if(board[0][0] == "O" && board[1][1] == "O" && board[2][2] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }
        else if(board[0][2] == "O" && board[1][1] == "O" && board[2][0] == "O"){
            String temp1 = "Winner : ";
            String temp2 = "<font color='#EE00EE'>PLAYER TWO</font>";
            announcer.setText(Html.fromHtml(temp1 + temp2));
            newGame.setText("Restart");
            cleanup();
        }


    }


}
