package com.example.notesbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumPalette;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.notes.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.notes.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notes.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.notes.EXTRA_PRIORITY";
    public static final String EXTRA_COLOR = "com.example.notes.EXTRA_COLOR";
    public static final String EXTRA_CREATED_AT = "com.example.notes.EXTRA_CREATED_AT";
    public static final String EXTRA_LAST_UPDATED = "com.example.notes.EXTRA_LAST_UPDATED";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private TextView textViewCreatedAt;
    private TextView textViewLastUpdated;
    private SpectrumPalette palette;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.title_gradient));
        }

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewCreatedAt = findViewById(R.id.created_at);
        textViewLastUpdated = findViewById(R.id.last_updated);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        palette = findViewById(R.id.palette);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(5);
        numberPickerPriority.setWrapSelectorWheel(true);
        numberPickerPriority.setValue(3);
        numberPickerPriority.setDisplayedValues(new String[]{"Very High", "High", "Medium", "Low", "Very Low"});

        palette.setOnColorSelectedListener(
                clr -> {
                    color = clr;
                }
        );

        palette.setSelectedColor(getResources().getColor(R.color.white));
        color = getResources().getColor(R.color.white);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            textViewCreatedAt.setText("Created at: " + intent.getStringExtra(EXTRA_LAST_UPDATED));
            textViewLastUpdated.setText("Last updated: " +  intent.getStringExtra(EXTRA_LAST_UPDATED));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            palette.setSelectedColor(intent.getIntExtra(EXTRA_COLOR,1));
        } else {
            setTitle("Add note");
        }
    }

    private void saveNote() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate today = LocalDate.now();

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String createdAt = dtf.format(today);
        String lastUpdated = dtf.format(today);
        int priority = numberPickerPriority.getValue();
        int color = this.color;

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Enter note title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_COLOR, color);
        data.putExtra(EXTRA_CREATED_AT, createdAt);
        data.putExtra(EXTRA_LAST_UPDATED, lastUpdated);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_LAST_UPDATED, dtf.format(LocalDate.now()));
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
