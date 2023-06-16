package SmallProjects;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
// ФИО, дата рождения, номер телефона, пол. Форматы данных: фамилия, имя, отчество - строки
// дата рождения - строка формата dd.mm.yyyy
// номер телефона - целое беззнаковое число без форматирования
// пол - символ латиницей f или m.
// Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше 
// или больше данных, чем требуется.
// Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.  
// Можно использовать встроенные типы java или создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
// <Фамилия><Имя><Отчество><дата рождения> <номер телефона><пол>
// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
// Не забудьте закрыть соединение с файлом.
// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.

public class Project {
    public static Scanner scanner = new Scanner(System.in); 
    public static void main(String[] args){
        M();
    }
    public static void M(){
        try{
            String[] arr = Parsing(WriteInfo());
            Vork(arr);
            WriteInFile(arr);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            M();
        } catch (IOException e){
            System.out.println(e.getMessage() + e.getStackTrace());
            M();
        }
    }
    public static String WriteInfo(){
        System.out.println("Введите свои данные через пробел: ФИО, дата рождения, номер телефона, пол. Форматы данных:фамилия, имя, отчество - строки\n" 
                                + "Дата рождения - строка формата dd.mm.yyyy\n" 
                                    + "Номер телефона - целое беззнаковое число без форматирования\n"
                                        + "Пол - символ латиницей f или m\n"
                                            + "Пример: "
                                                + "'Maxim Maximov Maximovich 01.01.2000 89999999999 f'"); // Почему то не работает на русском языке, лучше писать на английском
        String str = scanner.nextLine();
        return str;
    }
    public static String[] Parsing(String str) {
        String[] strArray = str.split("\\s"); 
        return strArray; 
    }
    public static void WriteInFile(String[] str) throws IOException{
        String st = str[0] + ".txt";
        String filePath = "D:\\AllPrograming/ProjectsJava/SmallProjects/" + st;
        Path path = Paths.get(filePath);
        if (Files.notExists(path)){
            try(FileWriter fw = new FileWriter(st)){
                for (String each : str) {
                    fw.write("<" + each + ">");
                } 
            }  catch(IOException e)  {
                e.getMessage();
                System.out.println("Выпал exciption");
            }

        } else {
            try(FileWriter fw = new FileWriter(st)){
                for (String each : str) {
                    fw.write("\n"); 
                    fw.write("<" + each + ">");
                }  
            }  catch(IOException e){
                e.getMessage(); 
                System.out.println("Выпал exeption");
            } 
        }  
    }

    public static void Vork(String[] arr) throws RuntimeException{
        if (arr.length != 6) {throw new RuntimeException("\nНе хватает элементов, поторите ввод\n");}
        else {
            if (Bool(arr[0]) != false) throw new RuntimeException("\nНеправльно введено имя(Найдены цифры), повторите попытку\n");
            if (Bool(arr[1]) != false) throw new RuntimeException("\nНеправильно введена фамилия(Найдены цифры), повторите попытку\n");
            if(Bool(arr[2]) != false) throw new RuntimeException("\nНеправильно введено отчество(найдены цифры), повторите попытку\n");
            if(Date(arr[3]) != true ) throw new RuntimeException("\nНеправильно введена дата, повторите попытку\n");
            if(containsOnlyDigits(arr[4]) != true) throw new RuntimeException("\nНеправильно введен номер телефона(найдены символы), повторите попытку\n");
            //if(arr[5] != "f" || arr[5] != "m") throw new RuntimeException("\nНеправильно введен пол, повторите попытку\n");
            }
    }






    public static boolean Bool(String word){
        boolean hasDigits = false;
        for(int i = 0; i < word.length() && !hasDigits; i++) {
            if(Character.isDigit(word.charAt(i))) {
                hasDigits = true;
                return hasDigits;
            }
        }
        return hasDigits;
    }
    public static boolean containsOnlyDigits(String str) {
        if (str == null || str.length() == 0) return false;
        for (int i = 0; i < str.length(); i++) { if (!Character.isDigit(str.charAt(i))) return false;}
        return true;
    }
    public static boolean Date(String str){
        if(str == null || str.length() != 10) return false;
        char[] strToArray = str.toCharArray();
        char dot = '.';
        if (strToArray[2] != dot || strToArray[5] != dot) return false;
        return true;
    }
}