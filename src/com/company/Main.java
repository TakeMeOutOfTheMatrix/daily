package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

                    //НЕМЦЕВ НИКИТА АНДРЕЕВИЧ БАСО-02-18
                    //ЕЖЕДНЕВНИК
                    //ИЗНАЧАЛЬНО Я ХОТЕЛ СДЕЛАТЬ, ЧТОБЫ ИМЯ ЗАМЕТКИ

public class Main {

    public static <string> void main(String[] args) throws IOException {
        int c;
        do {
            System.out.println("Выберете действие");
            System.out.println("1. Просмотр всех имеющихся заметок");
            System.out.println("2. Запись новой заметки");
            System.out.println("3. Удаление имеющейся заметки");
            System.out.println("4. Чтение имеющейся заметки");
            System.out.println("5. Дозапись в имеющуюся заметку");
            System.out.println("6. Редактирование имеющихся заметок");
            System.out.println("0. Выход");
            Scanner sc = new Scanner(System.in);
            c = sc.nextInt();
            switch (c) {
                case  1:
                    look();
                    break;
                case 2:
                    create();
                    break;

                case 3:
                    delete();
                    break;

                case 4:
                    read();
                    break;

                case 5:
                    write();
                    break;

                case 6:
                    edit();
                    break;

                case 0:
                    System.out.println("Выход");
                    break;
            }
        }while (c > 0) ;

    }


    //ЗАПИСЬ НОВОЙ ЗАМЕТКИ

    private static void create() {
        Date date = new Date();
        String dat = String.valueOf(date);

        //НЕ МОГ РЕШИТЬ, В КАКОМ ФОРМАТЕ ЛУЧШЕ ЗАПИСЫВАТЬ ИМЯ ФАЙЛА: В ВИДЕ СЕГОДНЯШНЕЙ ДАТЫ, ЛИБО ПРОИЗВОЛЬНО
        //ЕСЛИ В ФОРМАТЕ СЕГОДНЯШНЕЙ ДАТЫ, ТО ОТПАДАЕТ ВОЗМОЖНОСТЬ ЗАПИСЫВАТЬ ЗАМЕТКИ НА ДРУГИЕ ДАТЫ

        /* String d = new SimpleDateFormat("EEE, MMM d, ''yy").format(date);
        System.out.println(d); */

        System.out.println("Введите имя файла в формате даты");
        Scanner scn = new Scanner(System.in);
        String name = String.valueOf(scn.nextLine());

        try(FileWriter writer = new FileWriter(name, false))
        {
            System.out.println("Введите текст");
            while (true)
            {
                Scanner scan = new Scanner(System.in);
                String t = String.valueOf(scan.nextLine());
                String text = t;
                if (text.equals("stop")) {
                    writer.write(dat + "\r\n");
                    break;
                }
                else {
                    writer.write(text + "\r\n");
                    writer.flush();
                }
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


    // ПРОСМОТР ВСЕХ ЗАМЕТОК

    private  static  void look(){
        File dir = new File("C:\\Users\\Dell\\IdeaProjects\\daily");
        if(dir.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : dir.listFiles()){

                if(item.isFile())
                    System.out.println(item.getName() + "\t file");
            }
        }
    }


    //УДАЛЕНИЕ ЗАМЕТКИ

    private static void delete(){
        System.out.println("Введите имя файла");
        Scanner scn1 = new Scanner(System.in);
        String name1 = String.valueOf(scn1.nextLine());

        File file = new File("C:\\Users\\Dell\\IdeaProjects\\daily\\" + name1);
        if(file.delete()){
            System.out.println("C:\\Users\\Dell\\IdeaProjects\\daily\\" + name1 +  " файл удален");
        }else System.out.println("C:\\Users\\Dell\\IdeaProjects\\daily\\" + name1 + " файл не был обнаружен");
    }


    //ЧТЕНИЕ ЗАМЕТКИ

    private static void read(){
        System.out.println("Введите имя файла");
        Scanner scn = new Scanner(System.in);
        String name = String.valueOf(scn.nextLine());

        try(FileReader reader = new FileReader(name))
        {
            int c;
            while((c=reader.read())!=-1){

                System.out.print((char)c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


    //ДОЗАПИСЬ В ЗАМЕТКУ

    private static void write() throws IOException {
        Date date = new Date();
        String dat = String.valueOf(date);
        System.out.println("Введите имя файла");
        Scanner scn1 = new Scanner(System.in);
        String name1 = String.valueOf(scn1.nextLine());
        System.out.println("Введите текст для дозаписи");
        Scanner scn = new Scanner(System.in);
        String text = String.valueOf(scn.nextLine());
        String t = "\r\n";
        try {
            Files.write(Paths.get(name1), text.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(name1), t.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(name1), dat.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(name1), t.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }


    //РЕДАКТИРОВАНИЕ ЗАМЕТКИ

    private static void edit() throws IOException {
        String separator = File.separator;
        System.out.println("Введите имя заметки");
        Scanner scn = new Scanner(System.in);
        String name = String.valueOf(scn.nextLine());

        System.out.println("Введите имя для новой изменённой заметки");
        Scanner scn1 = new Scanner(System.in);
        String name1 = String.valueOf(scn1.nextLine());

        Date date = new Date();
        String dat = String.valueOf(date);
        String t = "\r\n";

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Dell\\IdeaProjects\\daily\\" + name)));
        PrintWriter ed = new PrintWriter(new FileWriter("C:\\Users\\Dell\\IdeaProjects\\daily\\" + name1), true);
        String newStr = "";

        System.out.println("Введите ту часть, которую хотите заменить:");
        Scanner scan = new Scanner(System.in);
        String text = String.valueOf(scan.nextLine());

        System.out.println("Введите, на что заменять");
        Scanner scan1 = new Scanner(System.in);
        String text1 = String.valueOf(scan1.nextLine());

        while ((newStr = in.readLine()) != null) {
            try {
                if (newStr.contains(text)) {
                    newStr = newStr.replace(text, text1);
                }
                ed.println(newStr);
            } catch (Exception e) {

            }
        }
        Files.write(Paths.get(name1), t.getBytes(), StandardOpenOption.APPEND);
        Files.write(Paths.get(name1), dat.getBytes(), StandardOpenOption.APPEND);
        Files.write(Paths.get(name1), t.getBytes(), StandardOpenOption.APPEND);
    }
}