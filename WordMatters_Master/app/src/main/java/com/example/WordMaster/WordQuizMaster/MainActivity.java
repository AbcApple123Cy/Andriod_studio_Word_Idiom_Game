package com.example.WordMaster.WordQuizMaster;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout; // automatically wrap

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int presCounter = 0;
    // input length limit
    private int maxPresCounter;
    private String[] keys;
    private String textAnswer;
    TextView textScore, textQuestion, textTitle;

    private ImageButton btnTips, btnSkip, btnDelete, btnhome;
    AlertDialog.Builder builder;



    private static final String TAG = "MyApp";
    private String[] questions;
    private String[] Answers;
    private int nowLevel = 0;
    private String newLevel;

    // Score of player
    private int nowScore = 0;
    private String showScore = "0";


    //keyboard
    private String[] keyboard;
    private String realkeyboard;
    private String[] realkeyboard1;
    int random = 0; //create a extra random value
    private String randomchar;
    private String[] randomchararray;
    // make some random text to confuse player
    private String[] confuse = {"a b c d e f", "g h i j k l", "m n o p q r", "s t u v w x y z"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //keyboard
        // create a extra random characters to trick user(*3 means 0-3 numbers chosen randomly)
        random = (int) (Math.random() * 3);
        // create a extra string for random characters to trick user(string)
        randomchar = confuse[random];

        // Resources is for getting the answer
        Resources string = getResources();
        // get string and assign the answers array(from string.xml) to "keyboard"
        keyboard = string.getStringArray(R.array.Answers);
        // for each question answer index
        int keyanswer = nowLevel;
        // add answer string and random string
        String realkeyboard = keyboard[keyanswer] + " " + randomchar;
        // separate the string into different string array
        realkeyboard1 = realkeyboard.split(" ");

        keys = realkeyboard1; // assign answers string value to keys.
        btnTips = findViewById(R.id.btnTips);
        btnTips.setOnClickListener(this);
        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(this);
        btnDelete=findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnhome=findViewById(R.id.btnHome);
        btnhome.setOnClickListener(this);

        keys = shuffleArray(keys); //  go line 125
        // add different button for keyboard View with Linear Layout
        for (String key : keys) {
            addView(((FlexboxLayout) findViewById(R.id.FlexboxLayout)), key, ((EditText) findViewById(R.id.editText)));
        }
        //end keyboard

        Log.d(TAG, "newLevel in MainActivity check point 1 = " + newLevel);

        if (newLevel != null) {
            updateNowLevel();
        }
        setQusetionsAndAnswers();

    }

    private void updateNowLevel() {
        nowLevel = Integer.parseInt(newLevel);
        Log.d(TAG, "New Level = " + nowLevel);
        setQusetionsAndAnswers();
    }
    // set questions and answers
    private void setQusetionsAndAnswers() {

        int i = nowLevel;
        Resources res = getResources();
        // pass
        questions = res.getStringArray(R.array.questions);
        Answers = res.getStringArray(R.array.Answers);
        textQuestion = findViewById(R.id.textGood);
        textQuestion.setText(questions[i]);

        Resources string = getResources();
        keyboard = string.getStringArray(R.array.Answers);

        maxPresCounter = Answers[i].length() - (int) Answers[i].length() / 2; // set maxPresCounter

        textAnswer = keyboard[i];
        textAnswer = textAnswer.replace(" ", ""); // deleted the blank


        Log.d(TAG, "Answer = " + textAnswer + ", Length = " + maxPresCounter);

        doValidate();
    }

    private String[] shuffleArray(String[] ar) {  // random select the characters
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    //add new Linear Layout / View
    // create keyboard
    private void addView(FlexboxLayout viewParent, final String text, final EditText editText) {
        // LayoutParams gets the layout parameters and is used to tell their parent object how to lay out, allowing us to adjust the size of the layout.
        FlexboxLayout.LayoutParams linearLayoutParams = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );

        linearLayoutParams.rightMargin = 25;


        //create a textView
        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        // keyboard's word colour
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        textQuestion = findViewById(R.id.textGood);
        textScore = findViewById(R.id.textScore);
        textTitle = findViewById(R.id.Answer);

        // Show initial score
        textScore.setText("Score: " + showScore);


        // set the animation
        final Animation animation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(400);

        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    textView.startAnimation(animation);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });

        // official add  View
        viewParent.addView(textView);


    }

    // onClick listener for opening dialog
    public void onClick(View v) {
        // if user clicks the start button
        // for tips, skip, delete and home function
        if (v.getId() == R.id.btnTips) {
            if (nowScore < 30) {
                noTipsDialog(); // to to this private method
            }
            else
                showTipsDialog();
        }
        if (v.getId() == R.id.btnSkip) {
            if (nowScore < 50) {
                noSkipDialog();
            }
            else {
                showSkipDialog();
            }

        }
        if (v.getId() == R.id.btnDelete) {
            DeleteCharacters();
            doValidate2();
            }

        if (v.getId() == R.id.btnHome) {
            // use intent to move back to MainMenu
            Intent homepage = new Intent(MainActivity.this, MainMenu.class);
            startActivity(homepage);
        }
    }



    // Delete the incorrect input
    private void DeleteCharacters (){
        EditText editText = findViewById(R.id.editText);
        int originiallength=editText.getText().length();
        // if editText length > 0
        if(originiallength > 0) {
        int length = originiallength - 1; // delete last character of input
        String deleted = editText.getText().toString(); // string deleted get string from EditText
        String afterdeleted=deleted.substring(0,length); // assess value to afterdelted from original string
       editText.setText(afterdeleted); // deleted string and assigned to EditText(show the input from user)
             }
    }

    // showing tips, each tips need 30 points to buy
    private void showTipsDialog() {
        int n = nowLevel;
        Resources res = getResources();
        Answers = res.getStringArray(R.array.Answers);
        Resources string = getResources();
        keyboard = string.getStringArray(R.array.Answers);
        textAnswer = keyboard[n];
        // take away the blank from answer
        textAnswer = textAnswer.replace(" ", "");
        int ansLength = textAnswer.length();
        int before = ansLength - 1;
        // get the second character in the answer
        String tips = textAnswer.substring(before ,ansLength);

        builder = new AlertDialog.Builder(this);
        builder.setMessage("The last character is " + tips);
        builder.setCancelable(false);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nowScore -= 30;
                showScore = String.valueOf(nowScore);
                textScore.setText("Score: " + nowScore);
                showScore = String.valueOf(nowScore);
                if (nowScore < 0) {
                    Intent i = new Intent(MainActivity.this, GameOver.class);
                    startActivity(i);
                }
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Tips");
        alert.show(); // show the dialog
    }

    // not showing tips, since player do not get enough score
    private void noTipsDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Hey!!! Your scores is below 30, use your brain!!!");
        builder.setCancelable(false);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing need to do, so set it to null
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("No Tips!!!");
        alert.show();
    }

    // allow user to skip one level
    private void showSkipDialog() {

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to skip this question?");
        builder.setCancelable(false);

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newLevel = String.valueOf(++nowLevel);
                updateNowLevel();
                nowScore -= 50;
                textScore.setText("Score: " + nowScore);
                showScore = String.valueOf(nowScore);
                if (nowScore < 0) {
                    Intent i = new Intent(MainActivity.this, GameOver.class);
                    startActivity(i);
                }
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing need to do
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Skip");
        alert.show();
    }

    // cannot skip one level, since player do not get enough score
    private void noSkipDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Hey!!! Your scores is below 50, use your brain!!!");
        builder.setCancelable(false);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing need to do, so set it to null
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("No Skip!!!");
        alert.show();
    }



    //dynamically views after finish the question。 and show again if the answer is wrong
    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        FlexboxLayout linearLayout = findViewById(R.id.FlexboxLayout); // get main.xml linearlayout



        // for checking answer, correct -> EncouragePage, wrong -> again

        if (editText.getText().toString().equals(textAnswer) && editText.length() == maxPresCounter) {
            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            // update level when it is correct
            newLevel = String.valueOf(++nowLevel);

            // if user answers wrong, it will add 10 score
            nowScore += 10;
            // check whether it is full score, if it is, then pass it to the end page
            if (newLevel == "349" || nowLevel == 350) {
                showScore = String.valueOf(nowScore);
                Log.d(TAG, "Answer Correct");

                Intent i = new Intent(MainActivity.this, End.class);
                i.putExtra("newLevel", newLevel);
                // move the score to another class, for displaying score
                showScore = String.valueOf(nowScore);
                i.putExtra("score", showScore);
                startActivity(i);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {                                      //要延遲執行update questions, 因為轉page速度太慢
                    @Override
                    //question轉左下個level都未轉到個page, 所以要delay佢
                    public void run() {
                        updateNowLevel();
                    }
                }, 1000);

                editText.setText("");


            }

            // not full score and not negative score
            else if (newLevel != "349" && nowScore >= 0) {
                Log.d(TAG, "Answer Correct");
                // move the EncouragePage
                Intent a = new Intent(MainActivity.this, EncouragePage.class);
                a.putExtra("newLevel", newLevel);
                // move the score to another class, for displaying score
                showScore = String.valueOf(nowScore);
                a.putExtra("score", showScore);
                startActivity(a);

                new Handler().postDelayed(new Runnable() {                                      //要延遲執行update questions, 因為轉page速度太慢
                    @Override
                    //question轉左下個level都未轉到個page, 所以要delay佢
                    public void run() {
                        updateNowLevel();
                    }
                }, 1000);

                editText.setText("");
            }

            int counter = 0;// counter of first highest marks
           if (counter == 0)
           {
               // set a value in globlehighest.java and AndroidManifest.xml
               globalhighest highest = (globalhighest) getApplicationContext();

                highest.setHighestmarks(nowScore); // the first nowScore is the highest mark
                 counter++;
           }
           if(counter >= 1){//new current highest marks
               globalhighest highest = (globalhighest) getApplicationContext();
               highest.setCurrentHighestmarks(nowScore); // set new current highest marks

           }

        } else if (editText.getText().toString().equals(textAnswer) == false && editText.length() == maxPresCounter) {
            Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
            // reset the value
            editText.setText("");
            nowScore -= 10;
            showScore = String.valueOf(nowScore);
            textScore.setText("Score: " + nowScore);
            // check whether it is negative score, if it is, then pass it to the game over page
            if (nowScore < 0) {
                Intent i = new Intent(MainActivity.this, GameOver.class);
                startActivity(i);
            }


        }




        // keyboard for after answering
        Resources string = getResources(); // Resources is for getting answer
        keyboard = string.getStringArray(R.array.Answers); // get string and assign the answers array(from string.xml) to "keyboard"
        int keyanswer = nowLevel ; // for each question answer index
        String realkeyboard = keyboard[keyanswer] + " " + randomchar; // add the string of answer and random character
        Log.d(TAG, realkeyboard);
        realkeyboard1= realkeyboard.split(" "); // Split each answer string into different character groups.
        keys= realkeyboard1; // assign answers string value to keys.

        // After the error, add different keyboard buttons again View
        keys = shuffleArray(keys);
        linearLayout.removeAllViews();

        for (String key : keys) {
            addView(linearLayout, key, editText);

        }

    }

    // for after deletion
    private void doValidate2() {
        EditText editText = findViewById(R.id.editText);
        presCounter= editText.getText().length();
        FlexboxLayout linearLayout = findViewById(R.id.FlexboxLayout); // get main.xml linearlayout


        // checking answer, correct -> EncouragePage, wrong -> again

        if (editText.getText().toString().equals(textAnswer) && editText.length() == maxPresCounter) {
            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            newLevel = String.valueOf(++nowLevel);

            // if user answers wrong, it will add 10 score
            nowScore += 10;
            // check whether it is full score, if it is, then pass it to the end page
            if (newLevel == "349") {
                showScore = String.valueOf(nowScore);
                Log.d(TAG, "Answer Correct");

                Intent i = new Intent(MainActivity.this, End.class);
                i.putExtra("newLevel", newLevel);
                // move the score to another class, for displaying score
                showScore = String.valueOf(nowScore);
                i.putExtra("score", showScore);
                startActivity(i);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {                                      //要延遲執行update questions, 因為轉page速度太慢
                    @Override
                    //question轉左下個level都未轉到個page, 所以要delay佢
                    public void run() {
                        updateNowLevel();
                    }
                }, 1000);

                editText.setText("");


            }

            // not full score and not negative score
            else if (newLevel != "349" && nowScore >= 0) {
                Log.d(TAG, "Answer Correct");

                Intent a = new Intent(MainActivity.this, EncouragePage.class);
                a.putExtra("newLevel", newLevel);
                // move the score to another class, for displaying score
                showScore = String.valueOf(nowScore);
                a.putExtra("score", showScore);
                startActivity(a);

                new Handler().postDelayed(new Runnable() {                                      //要延遲執行update questions, 因為轉page速度太慢
                    @Override
                    //question轉左下個level都未轉到個page, 所以要delay佢
                    public void run() {
                        updateNowLevel();
                    }
                }, 1000);

                editText.setText("");
            }

            int counter = 0; // counter of first highest marks
            if (counter == 0)
            {
                // set the highest score
                globalhighest highest = (globalhighest) getApplicationContext();
                 highest.setHighestmarks(nowScore);
                counter++;
            }
            if(counter >= 1){
                // set the current highest score
                globalhighest highest = (globalhighest) getApplicationContext();
                highest.setCurrentHighestmarks(nowScore);

            }

        } else if (editText.getText().toString().equals(textAnswer) == false && editText.length() == maxPresCounter) {
            Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
            nowScore -= 10;
            showScore = String.valueOf(nowScore);
            textScore.setText("Score: " + nowScore);
            // check whether it is negative score, if it is, then pass it to the game over page
            if (nowScore < 0) {
                Intent i = new Intent(MainActivity.this, GameOver.class);
                startActivity(i);
            }


        }

        // get the answer in string.xml
        Resources string = getResources();
        keyboard = string.getStringArray(R.array.Answers);
        // set the level value to the keyanswer
        int keyanswer = nowLevel;
        // add the string of answer and random character
        String realkeyboard = keyboard[keyanswer] + " " + randomchar;
        Log.d(TAG, realkeyboard);
        realkeyboard1= realkeyboard.split(" ");
        keys = realkeyboard1;
        // add different keyboard buttons in View
        keys = shuffleArray(keys);
        // Remove the function of the view
        linearLayout.removeAllViews();

        for (String key : keys) {
            // Added view in layout using for loop
            addView(linearLayout, key, editText);

        }

    }

}
