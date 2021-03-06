package ru.ntdim;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileIn = System.getProperty("user.dir") + "/" + "current.csv";
        if (args.length == 0) {
            System.out.printf("Не указан входящий файл!");
        } else {
            fileIn = System.getProperty("user.dir") + "/" + args[0];
        }
        System.out.println("Ищем файл: " + fileIn);

        if (new File(fileIn).exists()) {
            List<String> inFile = Files.readAllLines(Paths.get(fileIn), StandardCharsets.ISO_8859_1);
            try {
                List<String> outFile = inFile.stream()
                        .map(Main::subStringLocal)
                        .map(Main::replaceAlLocal)
                        .collect(Collectors.toList());
                System.out.println("Загружено " + inFile.size() + " записей.");
                Files.write(Paths.get("skip.txt"), outFile, StandardOpenOption.CREATE);
                System.out.printf("Результат сохранен в файл skip.txt");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        } else {
            System.out.println("Указанный файл не существует.");
        }
    }

    private static String subStringLocal(String s) {
        String result;
        try {
            result = s.substring(1, s.indexOf(";"));
        } catch (Exception e) {
            System.out.println("При обработке строки " + s + "/n произошла ошибка: /n" + e.getMessage());
            return "0";
        }
        return result;
    }

    private static String replaceAlLocal(String s) {
        String result;
        try {
            result = s.replaceAll("\"", "").replaceAll("\'", "");
        } catch (Exception e) {
            System.out.println("При обработке строки " + s + "/n произошла ошибка: /n" + e.getMessage());
            return "0";
        }
        return result;
    }
}
