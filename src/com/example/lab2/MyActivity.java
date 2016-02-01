package com.example.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyActivity extends Activity {
    // declare size of nxn puzzle
    static int n = 3;
    static String modelKey = "Model";
    static String aboutItem = "About";
    static String saveFileName = "savedData";
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    LightsModel model;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new LightsView(this, new LightsModel(n)));
        setContentView(R.layout.main);
        System.out.println("Create");
    }

    @Override
    public void onStart() {
        super.onStart();
        loadGame();
        System.out.println("Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGame();
        System.out.println("Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
        System.out.println("Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveGame();
        System.out.println("Stop");
    }

    public void saveGame() {
        try {
            fileOutputStream = openFileOutput(saveFileName, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(getModel());
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try {
            fileInputStream = openFileInput(saveFileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            model = (LightsModel) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putSerializable(modelKey, getModel());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        model = (LightsModel) savedInstanceState.getSerializable(modelKey);
    }

    public LightsModel getModel() {
        if (model == null) {
            model = new LightsModel(n);
        }
        return model;
    }

    public void resetModel(View view) {
        LightsView lightsView = (LightsView) findViewById(R.id.lightsView);
        //lightsView.reset();
        model.reset();
        lightsView.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(aboutItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(aboutItem)) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("I see you're trying to leave.");
            alertDialog.setMessage("Are you sure?");

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    "No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing dialog will dismiss
                        }
                    });
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
