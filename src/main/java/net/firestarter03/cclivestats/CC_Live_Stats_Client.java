package net.firestarter03.cclivestats;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.HoverEvent;

public class CC_Live_Stats_Client implements ClientModInitializer {

    private static final Path HOVER_TEXT = Paths.get("hovertext.txt");

    @Override
    public void onInitializeClient() {
        System.out.println("TestMod gestartet!");

        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            System.out.println("Nachricht empfangen: " + message.getString());

            // Rekursiv durch die Nachricht und ihre Siblings gehen
            extractHoverText(message);

            return true; // Erlaubt die Anzeige der Nachricht
        });
    }

    private void extractHoverText(Text text) {
        // Prüfe den Hover-Event der aktuellen Komponente
        Style style = text.getStyle();
        if (style != null && style.getHoverEvent() != null) {
            HoverEvent hoverEvent = style.getHoverEvent();

            // Prüfe, ob der Hover-Event SHOW_TEXT ist und extrahiere ihn
            if (hoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                Text hoverText = (Text) hoverEvent.getValue(HoverEvent.Action.SHOW_TEXT);
                if (hoverText != null) {
                    String hoverTextString = hoverText.getString();
                    System.out.println("Hover Text gefunden: " + hoverTextString);
                    writeToFile(hoverTextString);
                }
            }
        }

        // Prüfe die Siblings der aktuellen Komponente
        for (Text sibling : text.getSiblings()) {
            extractHoverText(sibling);
        }
    }

    // Schreibe den Text in eine Datei
    private void writeToFile(String message) {
        try (FileWriter writer = new FileWriter(HOVER_TEXT.toFile(), true)) {
            writer.write(message + "\n");
            System.out.println("In Datei gespeichert: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
