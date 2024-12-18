package net.firestarter03.cclivestats;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Path;

public class CardProcessor {
    private static final Path CARD_LOG_FILE = Path.of("cc_live_cards.txt");

    //Regex Patterns
    private static final Pattern NAME_PATTERN = Pattern.compile("^\\(.*?\\) \\[Karte\\]$");
    private static final Pattern LEVEL_PATTERN = Pattern.compile("^Stufe \\(\\d+\\)$");


    public void process(String hoverText) {
        String[] lines = hoverText.split("\n");

        if (lines.length >= 2) {
            Matcher nameMatcher = NAME_PATTERN.matcher(lines[0]);
            Matcher levelMatcher = LEVEL_PATTERN.matcher(lines[1]);


            if (nameMatcher.find() && levelMatcher.find()) {
                String name = nameMatcher.group(1);
                String level = levelMatcher.group(1);
                writeToFile("Karte: " + name + ", Level: " + level);
                System.out.println("Karte hinzugefügt: " + name + ", Level: " + level);
            }
        }
    }

    private void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(CARD_LOG_FILE.toFile(), true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}