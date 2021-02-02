package com.example.notesbyme;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int priority;
    private int color;
    private String createdAt;
    private String lastUpdated;

    public Note(String title, String description, int priority, int color, String createdAt, String lastUpdated) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.color = color;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getColor() {
        return color;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
