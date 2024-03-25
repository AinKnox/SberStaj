import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;

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

    // Добавляем методы доступа для приватных полей
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public int getPopulation() {
        return population;
    }
}

// Класс, реализующий компаратор для сортировки по федеральному округу и наименованию города
class RegionCityComparator implements Comparator<City> {
    @Override
    public int compare(City city1, City city2) {
        // Сначала сравниваем по федеральному округу
        int compareRegion = city1.getRegion().compareTo(city2.getRegion());
        if (compareRegion != 0) {
            return compareRegion;
        }
        // Если федеральные округа равны, сравниваем по наименованию города с учетом регистра
        return city2.getName().compareTo(city1.getName());
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
                    continue;
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

        // Сортировка списка городов по федеральному округу и наименованию города в алфавитном порядке по убыванию с учетом регистра
        cities.sort(new RegionCityComparator());

        // Вывод списка городов в консоль
        for (City city : cities) {
            System.out.println(city.toString().replace(", ", ";"));
        }

        // Поиск города с наибольшим количеством жителей
        int maxPopulation = 0;
        int maxPopulationIndex = -1;
        for (int i = 0; i < cities.size(); i++) {
            City currentCity = cities.get(i);
            if (currentCity.getPopulation() > maxPopulation) {
                maxPopulation = currentCity.getPopulation();
                maxPopulationIndex = i;
            }
        }

        if (maxPopulationIndex != -1) {
            System.out.println("[" + maxPopulationIndex + "] = " + maxPopulation);
        } else {
            System.out.println("Нет данных о городах.");
        }

        // Поиск количества городов в разрезе регионов
        Map<String, Integer> regionCityCount = new HashMap<>();
        for (City city : cities) {
            String region = city.getRegion();
            if (regionCityCount.containsKey(region)) {
                regionCityCount.put(region, regionCityCount.get(region) + 1);
            } else {
                regionCityCount.put(region, 1);
            }
        }

        // Вывод количества городов в разрезе регионов
        for (Map.Entry<String, Integer> entry : regionCityCount.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}