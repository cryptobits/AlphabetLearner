package com.example.asuper.alphabetlearner.Activities;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.asuper.alphabetlearner.Objects.AlphaSet;
import com.example.asuper.alphabetlearner.Objects.Letter;
import com.example.asuper.alphabetlearner.R;

import java.util.Random;


public class GameActivity extends AppCompatActivity {

    // keeps track of the correct letter and associated sound
    private int randomCorrect;
    private int correctSoundID;

    // the set of all letters
    private AlphaSet alphaSet;

    // mediaplayers for various sounds
    private MediaPlayer prompt;
    private MediaPlayer letter;
    private MediaPlayer wrongAnswer;
    private MediaPlayer tryAgain;

    // id numbers for the three buttons with the letters on them
    private ImageButton rightBtn;
    private ImageButton centerBtn;
    private ImageButton leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Assign the three buttons for the game
        rightBtn = (ImageButton) findViewById(R.id.rightButton);
        centerBtn = (ImageButton) findViewById(R.id.centerButton);
        leftBtn = (ImageButton) findViewById(R.id.leftButton);

        // An AlphaSet generates 3 random letters to be used for the game
        alphaSet = new AlphaSet();
        initializeGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeGame();
    }

    @Override
    protected void onPause() {
        releaseTheSounds();
        super.onPause();

    }

    private void initializeGame() {

        alphaSet.setRandomLetters();

        // Assign the correct letter image to each button
        rightBtn.setImageResource(alphaSet.choice2);
        centerBtn.setImageResource(alphaSet.choice1);
        leftBtn.setImageResource(alphaSet.choice0);

        // animates the letters
        animateLetters();

        // Determine a random letter to be the correct letter to prompt
        Random rand = new Random();
        randomCorrect = rand.nextInt(3);
        switch (randomCorrect) {
            case 0:
                correctSoundID = alphaSet.getSound(alphaSet.choice0);
                break;
            case 1:
                correctSoundID = alphaSet.getSound(alphaSet.choice1);
                break;
            case 2:
                correctSoundID = alphaSet.getSound(alphaSet.choice2);
                break;
        }

        // Play the prompt with the correct letter after
        prompt = MediaPlayer.create(GameActivity.this, R.raw.prompt);
        prompt.start();
        prompt.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                letter = MediaPlayer.create(GameActivity.this, correctSoundID);
                letter.start();
            }
        });

    }

    // Finishes the game if left button is the correct letter, else plays a try again sound
    public void choiceButton0(View v) {
        if (prompt.isPlaying() || letter.isPlaying()) {
            // do nothing, must wait for the sound to finish
        } else if (wrongAnswer != null && wrongAnswer.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else if (tryAgain != null && tryAgain.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else {
            if (randomCorrect == 0) {
                Letter correct = alphaSet.getLetter(alphaSet.choice0);
                correct.incrementCorrect();
                MainActivity.dbMgr.updateLetter(correct);
                releaseTheSounds();
                starScreen();
                //initializeGame();
            } else {
                if (randomCorrect == 1) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice1);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                } else if (randomCorrect == 2) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice2);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                }
                if (wrongAnswer != null) {wrongAnswer.reset();}
                wrongAnswer = MediaPlayer.create(GameActivity.this, R.raw.that_letter);
                wrongAnswer.start();
                wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer player) {
                        if (letter != null) {letter.reset();}
                        letter = MediaPlayer.create(GameActivity.this, alphaSet.getSound(alphaSet.choice0));
                        letter.start();
                        letter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer player) {
                                if (tryAgain != null) {tryAgain.reset();}
                                tryAgain = MediaPlayer.create(GameActivity.this, R.raw.try_again);
                                tryAgain.start();
                            }
                        });
                    }
                });
            }
        }
    }

    // Finishes the game if center button is the correct letter, else plays a try again sound
    public void choiceButton1(View v) {
        if (prompt.isPlaying() || letter.isPlaying()) {
            // do nothing, must wait for the sound to finish
        } else if (wrongAnswer != null && wrongAnswer.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else if (tryAgain != null && tryAgain.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else {
            if (randomCorrect == 1) {
                Letter correct = alphaSet.getLetter(alphaSet.choice1);
                correct.incrementCorrect();
                MainActivity.dbMgr.updateLetter(correct);
                releaseTheSounds();
                starScreen();
                //initializeGame();
            } else {
                if (randomCorrect == 0) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice0);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                } else if (randomCorrect == 2) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice2);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                }
                if (wrongAnswer != null) {wrongAnswer.reset();}
                wrongAnswer = MediaPlayer.create(GameActivity.this, R.raw.that_letter);
                wrongAnswer.start();
                wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer player) {
                        if (letter != null) {letter.reset();}
                        letter = MediaPlayer.create(GameActivity.this, alphaSet.getSound(alphaSet.choice1));
                        letter.start();
                        letter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer player) {
                                if (tryAgain != null) {tryAgain.reset();}
                                tryAgain = MediaPlayer.create(GameActivity.this, R.raw.try_again);
                                tryAgain.start();
                            }
                        });
                    }
                });

            }
        }
    }

    // Finishes the game if right button is the correct letter, else plays a try again sound
    public void choiceButton2(View v) {
        if (prompt.isPlaying() || letter.isPlaying()) {
            // do nothing, must wait for the sound to finish
        } else if (wrongAnswer != null && wrongAnswer.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else if (tryAgain != null && tryAgain.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else {
            if (randomCorrect == 2) {
                Letter correct = alphaSet.getLetter(alphaSet.choice2);
                correct.incrementCorrect();
                MainActivity.dbMgr.updateLetter(correct);
                releaseTheSounds();
                starScreen();
                //initializeGame();
            } else {
                if (randomCorrect == 1) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice1);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                } else if (randomCorrect == 0) {
                    Letter incorrect = alphaSet.getLetter(alphaSet.choice0);
                    incorrect.incrementIncorrect();
                    MainActivity.dbMgr.updateLetter(incorrect);
                }
                if (wrongAnswer != null) {wrongAnswer.reset();}
                wrongAnswer = MediaPlayer.create(GameActivity.this, R.raw.that_letter);
                wrongAnswer.start();
                wrongAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer player) {
                        if (letter != null) {letter.reset();}
                        letter = MediaPlayer.create(GameActivity.this, alphaSet.getSound(alphaSet.choice2));
                        letter.start();
                        letter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer player) {
                                if (tryAgain != null) {tryAgain.reset();}
                                tryAgain = MediaPlayer.create(GameActivity.this, R.raw.try_again);
                                tryAgain.start();
                            }
                        });
                    }
                });
            }
        }
    }

    // Replays the prompt and correct letter
    public void repeatLetter(View v) {
        if (prompt.isPlaying() || letter.isPlaying()) {
            // do nothing, must wait for the sound to finish
        } else if (wrongAnswer != null && wrongAnswer.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else if (tryAgain != null && tryAgain.isPlaying()){
            // do nothing, must wait for the sound to finish
        } else {
            prompt.reset();
            prompt = MediaPlayer.create(GameActivity.this, R.raw.prompt);
            prompt.start();
            prompt.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer player) {
                    if (letter != null) {letter.reset();}
                    letter = MediaPlayer.create(GameActivity.this, correctSoundID);
                    letter.start();
                }
            });
        }
    }

    // resets the media players to free resources
    // if media players are not reset or released, they will eventually stop working
    private void releaseTheSounds() {
        if (prompt != null) {prompt.reset();}
        if (letter != null) {letter.reset();}
        if (tryAgain != null) {tryAgain.reset();}
        if (wrongAnswer != null) {wrongAnswer.reset();}
    }

    // adds a short animation when the letters load
    // the offset float values give it an unwinding appearance from right to left
    private void animateLetters() {
        ObjectAnimator objectAnimator0 = ObjectAnimator.ofFloat(
                rightBtn, "rotationX", 0.0F, 360.0F);
        objectAnimator0.setDuration(1000);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(
                centerBtn, "rotationX", 120.0F, 360.0F);
        objectAnimator1.setDuration(1000);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(
                leftBtn, "rotationX", 240.0F, 360.0F);
        objectAnimator2.setDuration(1000);
        objectAnimator0.start();
        objectAnimator1.start();
        objectAnimator2.start();
    }

    private void starScreen() {
        Intent intent = new Intent(this, StarActivity.class);
        startActivity(intent);
    }

}
