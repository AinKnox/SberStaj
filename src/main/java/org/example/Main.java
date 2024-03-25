import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class City {
    private String name;
    private String region;
    private String district;
    private int population;
    private String foundation;

    public City(String name, String region, String district, int population, String foundation) {
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.foundation = foundation;
    }

    @Override
    public String toString() {
        return "City{name='" + name + "', region='" + region + "', district='" + district + "', population=" + population + ", foundation='" + foundation + "'}";
    }
}

public class Main {
    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        try {
            File file = new File("cities.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");

// Проверяем, что строка содержит нужное количество частей
                if (parts.length != 6) {
                    System.out.println("Ошибка чтения строки: неверное количество значений");
                    continue; // Пропускаем эту строку и переходим к следующей
                }

                String name = parts[1];
                String region = parts[2];
                String district = parts[3];
                int population = Integer.parseInt(parts[4]);
                String foundation = parts[5];

                City city = new City(name, region, district, population, foundation);
                cities.add(city);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка преобразования числа: " + e.getMessage());
        }

        for (City city : cities) {
            System.out.println(city.toString().replace(", ", ";"));
        }
    }
}