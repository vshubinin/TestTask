package com.company;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Имя файла
    private static String fileName;
    private static int H;
    private static int W;
    //Конструктор
    public Main(String fileName) {
        this.fileName = fileName;
    }


    public static void main(String[] args) throws FileNotFoundException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
    }

    /**
     *Запуск приложения.
     */
    public Main() throws FileNotFoundException {

        initialize();

    }

    private void initialize() throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        //Указываем полный путь + имя файла и расширение
        System.out.println("\n" + "Input file name please:");
        fileName = in.nextLine();
        System.out.println("\n" + "Example of source data:");
        //Чтение файла
        String textFromFile = read(fileName);
        // Вывод содержимого файла на консоль
        System.out.println("\n" + textFromFile + "\n");

        String result = null;
        try {
            result = yesorno();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Вывод проверки результата на консоль
        System.out.println("Example of program output:" + "\n");
        System.out.print(result + "\n");
        //delete(fileName);
    }

    //Результат проверки
    private static String yesorno() throws IOException {
        String result;
        int totalsum = 0;
        //Алгоритм...
        ArrayList<String> list = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(fileName),
                Charset.defaultCharset());
        for (String line : lines)
        {
            String[] splitted = line.split(" ");
            int splitsize = splitted.length;
            if(splitsize==1&&!splitted[0].equals("")) {
                //Проверка на пробелы
                if(splitted[0].equals("")){
                    result = "no";
                    return result;
                }
                if(splitted[0].length()>=9) {
                    String strSub = "";
                    char simvol = '1';
                    for(int c= 0;c<splitted[0].length();c++){
                          if(splitted[0].charAt(c)==simvol){
                              strSub += simvol;
                              if (strSub.equals("111111111")) {
                                  result = "no";
                                  return result;
                              }
                          }else{
                              strSub = "";
                          }
                    }
                }
                list.add(splitted[0].substring(0, 1));
            }else if(splitsize==2) {
                //Проверка на пробелы
                if(splitted[0].equals("")||splitted[1].equals("")){
                    result = "no";
                    return result;
                }
                H = Integer.parseInt(splitted[0]);
                W = Integer.parseInt(splitted[1]);
            }else if(splitsize==3){
                 totalsum +=  Integer.parseInt(splitted[0])*Integer.parseInt(splitted[1])*Integer.parseInt(splitted[2]);
            }

        }
        String simvol = "1";
        String search = "";
        for(int c= 0;c<list.size();c++){
            if(list.get(c).equals(simvol)){
                search += simvol;
                if (search.equals("111111111")) {
                    result = "no";
                    return result;
                }
            }else{
                search = "";
            }
        }
        if(H==8&&list.size()>H){
            int resize = list.size()-H;
            list.remove(resize);
        }
        if(W==8&&list.size()>W){
            int resize = list.size()-W;
            list.remove(resize);
        }

        int size = (H*W);
        if(size>=totalsum){
            result = "yes";
        }else {
            result = "no";
        }
        return result;
    }

    // Проверка на несуществующий файл
    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }

    //Удаление файла
    private static void delete(String nameFile) throws FileNotFoundException {
        exists(nameFile);
        new File(nameFile).delete();
    }
    //Функция обработки файла с выводом его содержимого
    private static String read(String fileName) throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();
        // Проверка на несуществующий файл
        exists(fileName);

        try {
            File file = new File(fileName);
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                //Закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }
}