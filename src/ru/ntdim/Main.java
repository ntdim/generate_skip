package ru.ntdim;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.printf("Не указан входящий файл!");
            return;
        }
        String fileIn = args[0];
        try (Stream<String> stream = Files.lines(Paths.get(fileIn))) {
            List<String> outList = stream
                    .map(s -> s.substring(1, s.indexOf(";")))
                    .map(s -> s.replaceAll("\"", ""))
                    .collect(Collectors.toList());

            try (PrintStream out = new PrintStream(new FileOutputStream("skip.txt"))) {
                out.print(outList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
