package com.example.enriq.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int resultado;
    int posicionCorrecta = 0;
    int score = 0;
    int numerosTotales = 0;
    ArrayList<Integer> respuestas = new ArrayList<>();
    Button startButton;
    TextView scoreView;
    TextView timerTextView;
    TextView resultTextView;
    TextView sumTextView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button tryAgain;
    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTextView = findViewById(R.id.timerTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreView = findViewById(R.id.scoreTextView);
        sumTextView = findViewById(R.id.sumTextView);
        gridLayout = findViewById(R.id.gridLayout);
        startButton = findViewById(R.id.startButton);

    }

    public void generarOperacion(){
        Random random = new Random();
        int number1 = random.nextInt(21);
        int number2 = random.nextInt(21);
        resultado = number1 + number2;
        update(number1,number2);
    }

    public void update(int number1,int number2){

        sumTextView.setText(String.format("%s + %s",number1,number2 ));
    }

    public void generarNumeros(){
        respuestas.clear();
        Random random = new Random();
        posicionCorrecta = random.nextInt(4);
        for(int i=0;i<4;i++){
            if(i == posicionCorrecta){
                respuestas.add(resultado);
            }else{
                int respuestaRandom = random.nextInt(41);
                while (respuestaRandom == resultado){
                    respuestaRandom = random.nextInt(41);
                }
                respuestas.add(respuestaRandom);
            }
        }
    }

    public void pintarBotones(){
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setText(String.format("%s",respuestas.get(0)));
        button2.setText(String.format("%s",respuestas.get(1)));
        button3.setText(String.format("%s",respuestas.get(2)));
        button4.setText(String.format("%s",respuestas.get(3)));
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        tryAgain(findViewById(R.id.tryAgainButton));
        showAll();
    }

    public void elegirRespuesta(View view){

        if(view.getTag().toString().equals(Integer.toString(posicionCorrecta))){
            resultTextView.setText(R.string.correct_string);
            score++;
            pintarScore();
        }else{
            resultTextView.setText(R.string.incorrect_string);
        }
        resultTextView.setVisibility(View.VISIBLE);
        numerosTotales++;
        pintarScore();
        generarOperacion();
        generarNumeros();
        pintarBotones();
    }

    public void pintarScore(){

        scoreView.setText(String.format("%s + %s", score,numerosTotales));
    }

    public void tryAgain(View view){
        score = 0;
        numerosTotales = 0;
        resultTextView.setVisibility(View.INVISIBLE);
        scoreView.setText("0/0");
        tryAgain = findViewById(R.id.tryAgainButton);
        tryAgain.setVisibility(View.INVISIBLE);
        generarOperacion();
        generarNumeros();
        pintarBotones();

        new CountDownTimer(30100,1000){
            @Override
            public void onTick(long millisecondsUntilDone) {
                int segundos = (int)(long)millisecondsUntilDone/1000;
                timerTextView.setText(String.format("%ss",segundos));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText(String.format("Your score: %s / %s",score,numerosTotales));
                Button tryAgain = findViewById(R.id.tryAgainButton);
                tryAgain.setVisibility(View.VISIBLE);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
            }
        }.start();
    }

    public void showAll(){
        timerTextView.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
    }
}
