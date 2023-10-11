import service.*;
import pojo.*;
import csvLoader.*;


import java.io.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args)  {
        MenuService menuService = new MenuService();
        try {
            menuService.displayIntroduction();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
}















}
