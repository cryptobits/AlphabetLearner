package com.example.asuper.alphabetlearner.Objects;

import com.example.asuper.alphabetlearner.Activities.MainActivity;
import com.example.asuper.alphabetlearner.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Super on 6/7/2017.
 */

// This class organizes the image and sound assets as they relate to each letter.
// It also serves to create the set of letters in the database when it is empty
// or gets the letters back from the database when it is populated.
public class AlphaSet {

    private int random0;
    private int random1;
    private int random2;
    private int randomLetter;
    private int randomColor;
    public int choice0;
    public int choice1;
    public int choice2;
    private final int[][] alphaImages = { {R.drawable.a, R.drawable.a1, R.drawable.a2},
            {R.drawable.b, R.drawable.b1, R.drawable.b2},
            {R.drawable.c, R.drawable.c1, R.drawable.c2},
            {R.drawable.d, R.drawable.d1, R.drawable.d2},
            {R.drawable.e, R.drawable.e1, R.drawable.e2},
            {R.drawable.f, R.drawable.f1, R.drawable.f2},
            {R.drawable.g, R.drawable.g1, R.drawable.g2},
            {R.drawable.h, R.drawable.h1, R.drawable.h2},
            {R.drawable.i, R.drawable.i1, R.drawable.i2},
            {R.drawable.j, R.drawable.j1, R.drawable.j2},
            {R.drawable.k, R.drawable.k1, R.drawable.k2},
            {R.drawable.l, R.drawable.l1, R.drawable.l2},
            {R.drawable.m, R.drawable.m1, R.drawable.m2},
            {R.drawable.n, R.drawable.n1, R.drawable.n2},
            {R.drawable.o, R.drawable.o1, R.drawable.o2},
            {R.drawable.p, R.drawable.p1, R.drawable.p2},
            {R.drawable.q, R.drawable.q1, R.drawable.q2},
            {R.drawable.r, R.drawable.r1, R.drawable.r2},
            {R.drawable.s, R.drawable.s1, R.drawable.s2},
            {R.drawable.t, R.drawable.t1, R.drawable.t2},
            {R.drawable.u, R.drawable.u1, R.drawable.u2},
            {R.drawable.v, R.drawable.v1, R.drawable.v2},
            {R.drawable.w, R.drawable.w1, R.drawable.w2},
            {R.drawable.x, R.drawable.x1, R.drawable.x2},
            {R.drawable.y, R.drawable.y1, R.drawable.y2},
            {R.drawable.z, R.drawable.z1, R.drawable.z2}
    };
    private final int[] sounds = {R.raw.a_audio, R.raw.b_audio, R.raw.c_audio, R.raw.d_audio,
            R.raw.e_audio, R.raw.f_audio, R.raw.g_audio, R.raw.h_audio, R.raw.i_audio,
            R.raw.j_audio, R.raw.k_audio, R.raw.l_audio, R.raw.m_audio, R.raw.n_audio,
            R.raw.o_audio, R.raw.p_audio, R.raw.q_audio, R.raw.r_audio, R.raw.s_audio,
            R.raw.t_audio, R.raw.u_audio, R.raw.v_audio, R.raw.w_audio, R.raw.x_audio,
            R.raw.y_audio, R.raw.z_audio};

    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static Letter[] letterSet = new Letter[26];

    public AlphaSet(){

        initializeLetters();

    }

    public void setRandomLetters() {
        random1 = -1;
        random2 = -1;

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            randomLetter = rand.nextInt(26);
            randomColor = rand.nextInt(3);
            switch (i) {
                case 0: choice0 = alphaImages[randomLetter][randomColor];
                    random0 = randomLetter;
                    break;
                case 1: while (randomLetter == random0) {
                    randomLetter = rand.nextInt(26);
                }
                    choice1 = alphaImages[randomLetter][randomColor];
                    random1 = randomLetter;
                    break;
                case 2: while (randomLetter == random1 || randomLetter == random0) {
                    randomLetter = rand.nextInt(26);
                }
                    choice2 = alphaImages[randomLetter][randomColor];
                    random2 = randomLetter;
                    break;
            }
        }
    }

    public int getSound(int letterId){
        int soundId = -1;
        if (letterId == choice0) {
            soundId = sounds[random0];
        } else if (letterId == choice1) {
            soundId = sounds[random1];
        } else if (letterId == choice2) {
            soundId = sounds[random2];
        }
        return soundId;
    }

    public Letter getLetter(int letterId) {
        Letter letter = null;
        if (letterId == choice0) {
            letter = letterSet[random0];
        } else if (letterId == choice1) {
            letter = letterSet[random1];
        } else if (letterId == choice2) {
            letter = letterSet[random2];
        }
        return letter;
    }

    private void initializeLetters() {
        List<Letter> letterList = MainActivity.dbMgr.getAllLetters();

        // populates the database if it is empty
        // else it gets all the letters and values from the database
        if (letterList.size() == 0) {
            for (int i = 0; i < letters.length; i++) {
                Letter temp = new Letter(letters[i], 0, 0);
                letterSet[i] = MainActivity.dbMgr.addLetter(temp);
            }
        } else {
            int i = 0;
            for (Letter l:letterList) {
                letterSet[i] = l;
                i++;
            }
        }
    }

    public Letter[] getLetterSet() {
        return letterSet;
    }

}
