package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EndPageController {

    @FXML
    private TableView<PlayerScore> rankingTable;

    @FXML
    private TableColumn<PlayerScore, String> nameColumn;

    @FXML
    private TableColumn<PlayerScore, Integer> scoreColumn;

    private final File file = new File("records.txt");

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        loadAndDisplayScores();
    }

    private void loadAndDisplayScores() {
        List<PlayerScore> scores = loadScoresFromFile();
        ObservableList<PlayerScore> data = FXCollections.observableArrayList(scores);
        rankingTable.setItems(data);
    }

    private List<PlayerScore> loadScoresFromFile() {
        List<PlayerScore> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    list.add(new PlayerScore(name, score));
                }
            }
        } catch (Exception e) {
            // فایل ممکنه وجود نداشته باشه در اولین اجرا، مشکلی نیست
        }
        return list;
    }

    // این متد توسط MainPageController برای ثبت بازیکن جدید صدا زده میشه
    public void setPlayerData(String name, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(name + ":" + score);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // پس از اضافه کردن رکورد جدید، جدول را به‌روز کن
        loadAndDisplayScores();
    }

    // کلاس داخلی برای نگهداری رکورد
    public static class PlayerScore {
        private final String name;
        private final int score;

        public PlayerScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
