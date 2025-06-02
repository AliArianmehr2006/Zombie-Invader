package com.example.project;

public class PlayerData {
    private static String selectedPlayer = "player1.png"; // پیش‌فرض

    public static void setSelectedPlayer(String playerImageFileName) {
        selectedPlayer = playerImageFileName;
    }

    public static String getSelectedPlayer() {
        return selectedPlayer;
    }
}
