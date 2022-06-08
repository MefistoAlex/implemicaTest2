package test2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Test2 {
    public static void main(String[] args) {
        calculate();
    }

    private static void calculate() {

        Scanner scanner = new Scanner(System.in);
        int s;//number of tests
        int cityCount;//number of cityList
        int r;//number of path to find
        List<City> cityList = new ArrayList<>();
        List<Path> pathList = new ArrayList<>();
        List<CityPair> cityPairList = new ArrayList<>();
        System.out.println("Want to download pre set from test task?\n 1. Yes 2. No");
        if (scanner.hasNextInt()) {
            switch (scanner.nextInt()) {
                case 1 -> loadFromFile(cityList, pathList, cityPairList);
            }
        } else {
            //enter number of tests
            while (true) {
                System.out.println("Enter s [the number of tests <= 10]");
                if (scanner.hasNextInt()) {
                    s = scanner.nextInt();
                    if (s <= 10 && s > 0) {
                        scanner.nextLine();//clear scanner
                        break;
                    } else {
                        System.out.printf("s must be >0 and <=10");
                        scanner.nextLine();//clear scanner
                    }
                } else {
                    System.out.println("s must be int");
                    scanner.nextLine();//clear scanner
                }
            }
            //enter city count
            while (true) {
                System.out.println("Enter cityCount [the number of cityList <= 10000]");
                if (scanner.hasNextInt()) {
                    cityCount = scanner.nextInt();
                    if (s <= 10000 && s > 0) {
                        scanner.nextLine();//clear scanner
                        break;
                    } else {
                        System.out.printf("s must be >0 and <=10");
                        scanner.nextLine();//clear scanner
                    }
                } else {
                    System.out.println("s must be int");
                    scanner.nextLine();//clear scanner
                }
            }
            //enter city names
            for (int id = 1; id <= cityCount; id++) {
                String name;//name of current city
                int p;//the number of neighbors of city NAME

                while (true) {
                    System.out.println("Enter NAME [city name]");
                    name = scanner.nextLine();
                    try {
                        checkName(name);
                        City city = new City(id, name);
                        cityList.add(city);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                //enter city neighbors count
                while (true) {
                    System.out.println("Enter p [the number of neighbors of city NAME]");
                    if (scanner.hasNextInt()) {
                        p = scanner.nextInt();
                        if (p <= cityCount && p > 0) {
                            scanner.nextLine();//clear scanner
                            break;
                        } else {
                            System.out.printf("p must be >0 and <=" + cityCount);
                            scanner.nextLine();//clear scanner
                        }
                    } else {
                        System.out.println("p must be int");
                        scanner.nextLine();//clear scanner
                    }
                }
                //enter transfer cost for each neighbor
                for (int i = 1; i <= p; i++) {
                    while (true) {
                        String inputString;
                        System.out.printf("Enter n_id cost for %d neighbor \n" + "[n_id - index of a city connected to %s]\n" + "[cost - the transportation cost]\n", i, name);
                        inputString = scanner.nextLine();
                        try {
                            Path path = checkPath(inputString);
                            path.setIdFrom(id);
                            //checking if the ID city-neighbor is out of the number of cities
                            if (path.getIdTo() <= 0 || path.getIdTo() > cityCount) {
                                throw new IOException("ID must be <=cityCount");
                            }
                            pathList.add(path);
                            break;
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            //enter number of path to find
            while (true) {
                System.out.println("Enter r [the number of paths to find <= 100]");
                if (scanner.hasNextInt()) {
                    r = scanner.nextInt();
                    if (r <= 100 && r > 0) {
                        scanner.nextLine();//clear scanner
                        break;
                    } else {
                        System.out.printf("r must be >0 and <=100");
                        scanner.nextLine();//clear scanner
                    }
                } else {
                    System.out.println("r must be int");
                    scanner.nextLine();//clear scanner
                }
            }
            //Enter cityList names
            for (int i = 0; i < r; i++) {
                while (true) {
                    try {
                        System.out.printf("Enter %d pair of cities\nNAME1 NAME2 [NAME1 - source, NAME2 - destination]\n", i + 1);
                        String inputString = scanner.nextLine();
                        CityPair cityPair = checkCities(inputString, cityList);
                        cityPairList.add(cityPair);
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

//        saveToFile(cityList, pathList, cityPairList);
        //calculating shortest ways
        for (CityPair cityPair : cityPairList) {
            System.out.println(cityPair.toString() + " " + findShortestPath(cityPair, cityList, pathList));
        }

    }

    public static void checkName(String name) throws IOException {
        //name checking for non Latin symbols
        if (name.matches("\\W")) {
            throw new IOException("Name must be contain only Latin");
        }
        //name checking for numbers
        if (name.matches("\\d+")) {
            throw new IOException("Name must be without numbers");
        }
        //checking name length
        if (name.length() > 10) {
            throw new IOException("Name must be lower 10 symbols length");
        }
    }

    public static Path checkPath(String inputString) throws IOException {
        Path path = new Path();
        //inputString checking for symbols
        if (inputString.matches("[a-zA-Z]+")) {
            throw new IOException("Path must be contain only numbers");
        }
        //parsing values from inputString
        Pattern patternDigits = Pattern.compile("\\d+");
        Matcher matcherDigits = patternDigits.matcher(inputString);
        if (matcherDigits.find()) {
            String str = inputString.substring(matcherDigits.start(), matcherDigits.end());
            path.setIdTo(Integer.parseInt(str));
        } else throw new IOException("Path must n_id cost");
        if (matcherDigits.find()) {
            String str = inputString.substring(matcherDigits.start(), matcherDigits.end());
            path.setCost(Integer.parseInt(str));
        } else throw new IOException("Path must n_id cost");
        return path;
    }

    public static CityPair checkCities(String inputString, List<City> cities) throws IOException {
        String nameFrom;
        String nameTo;
        CityPair cityPair = new CityPair();
        //inputString checking for numbers
        if (inputString.matches("\\d+")) {
            throw new IOException("Cities must be in form NAME1 NAME2, without numbers");
        }
        //parsing values from inputString
        Pattern patternLatin = Pattern.compile("\\w+\\S");
        Matcher matcherLatin = patternLatin.matcher(inputString);
        if (matcherLatin.find()) {
            nameFrom = inputString.substring(matcherLatin.start(), matcherLatin.end());
        } else throw new IOException("Cities must be in form NAME1 NAME2");
        if (matcherLatin.find()) {
            nameTo = inputString.substring(matcherLatin.start(), matcherLatin.end());
        } else throw new IOException("Cities must be in form NAME1 NAME2");
        //searching city from
        List<City> result = cities.stream().filter(a -> Objects.equals(a.getName(), nameFrom)).collect(Collectors.toList());
        if (result.size() != 0) {
            cityPair.setCityFromId(result.get(0));
        } else throw new IOException("Have no city with name " + nameFrom);
        //searching city to
        result = cities.stream().filter(a -> Objects.equals(a.getName(), nameTo)).collect(Collectors.toList());
        if (result.size() != 0) {
            cityPair.setCityToId(result.get(0));
        } else throw new IOException("Have no city with name " + nameTo);
        return cityPair;
    }

    public static int findShortestPath(CityPair cityPair, List<City> cityList, List<Path> pathList) {
        //I used Dijkstra’s algorithm
        cityPair.getCityFromId().setMark(0);//started mark =0;
        //move cityFrom to the beginning of list
        cityList = cityList.stream().sorted().collect(Collectors.toList());
        //step find paths from start city
        for (City city : cityList) {
            //step find paths from start city
            List<Path> startList = pathList.stream().filter(a -> a.getIdFrom() == city.getId()).collect(Collectors.toList());
            for (Path path : startList) {
                //searching cityTo
                City searchedCity = cityList.stream().filter(a -> a.getId() == path.getIdTo()).collect(Collectors.toList()).get(0);
                int mark = city.getMark() + path.getCost();
                if (searchedCity.getMark() > mark) {
                    searchedCity.setMark(mark);
                }
            }
        }
        return cityPair.getCityToId().getMark();
    }

    public static void saveToFile(List<City> cityList, List<Path> pathList, List<CityPair> cityPairList) {
        //create 2 streams to serialize the object and save it to a file
        try {
            FileOutputStream outputStream = new FileOutputStream("save");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            // save to file
            objectOutputStream.writeObject(cityList);
            objectOutputStream.writeObject(pathList);
            objectOutputStream.writeObject(cityPairList);
            //close the stream and release resources
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadFromFile(List<City> cityList, List<Path> pathList, List<CityPair> cityPairList) {
        //create 2 streams to read the objects from file
        try {
            FileInputStream inputStream = new FileInputStream("save");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            // loading from file
            cityList.addAll((List<City>) objectInputStream.readObject());
            pathList.addAll((List<Path>) objectInputStream.readObject());
            cityPairList.addAll((List<CityPair>) objectInputStream.readObject());

            //close the stream and release resources
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


