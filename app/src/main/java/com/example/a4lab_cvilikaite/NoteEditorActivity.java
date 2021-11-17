package com.example.a4lab_cvilikaite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;
    EditText edNotes;
    EditText edTittles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        edNotes = findViewById(R.id.edNotes);
        edTittles= findViewById(R.id.edTittle);
        // Fetch data that is passed from MainActivity
        edNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesv2", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                HashSet<String> setNotes = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
                HashSet<String> setTittles = (HashSet<String>) sharedPreferences.getStringSet("tittles", null);
                if(setNotes != null) {
                    HashSet<String> setNotesNew = new HashSet(setNotes);
                    setNotesNew.add(charSequence.toString());
                    spEditor.putStringSet("notes", setNotes);
                }
                if(setTittles != null) {
                    HashSet<String> setTittlesNew = new HashSet(setTittles);
                    setTittlesNew.add(charSequence.toString());
                    spEditor.putStringSet("notes", setNotes);
                }
                spEditor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
        edTittles.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.tittles.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                //Creating Object of SharedPreferences to store data in the phone
                if(MainActivity.tittles != null) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPreferences.edit();
                    HashSet<String> setNotes = new HashSet(MainActivity.notes);
                    HashSet<String> setTittles = new HashSet(MainActivity.tittles);
                    spEditor.putStringSet("notes", setNotes);
                    spEditor.putStringSet("titles", setTittles);
                    spEditor.apply();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
    }

    public void onBtnAddClick(View view) {
        if(!edNotes.getText().toString().isEmpty()) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesv2", Context.MODE_PRIVATE);
            SharedPreferences.Editor spEditor = sharedPreferences.edit();
            HashSet<String> setNotes = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
            HashSet<String> setTittles = (HashSet<String>) sharedPreferences.getStringSet("tittles", null);
            HashSet<String> setNotesNew;
            HashSet<String> setTitlesNew;
            if (setNotes != null) {
                setNotesNew = new HashSet(setNotes);
            } else {
                setNotesNew = new HashSet();
            }
            setNotesNew.add(edNotes.getText().toString());
            spEditor.putStringSet("notes", setNotesNew);


            if (setTittles != null) {
                setTitlesNew = new HashSet(setTittles);
            } else {
                setTitlesNew = new HashSet();
            }
            setTitlesNew.add(edTittles.getText().toString());
            spEditor.putStringSet("tittles", setTitlesNew);

            spEditor.apply();
            finish();

        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter something...",Toast.LENGTH_LONG).show();
        }
    }
}
