package test;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class Main{
    public static void main(String[] args){
        createStringFile("strings.txt");
        ArrayList<String> list=loadStrings(new File("strings.txt"));
        GUI window=new GUI(list);
        try{
        FileWriter f=new FileWriter("Strings after randomization.txt");
        for(String s:list)
            f.write(s);
        f.close();
        }catch(IOException e){
            System.out.println("Failed to write randomized strings");
        }
    }
    
    //This function creates the file contiaining strings from AAAA to ZZZZ
    private static void createStringFile(String name){
        try{
            File file=new File(name);
            FileWriter printer=new FileWriter(file);
            char[] strData={'A','A','A','A'};
            while(!(strData[0]=='Z'&&strData[1]=='Z'&&strData[2]=='Z'&&strData[3]=='Z')){
                printer.write(strData);
                printer.write('\n');
                strData[3]++;
                for(int i=3;i>0&&strData[i]>'Z';i--){
                    strData[i]='A';
                    strData[i-1]++;

                }
            }
            printer.write(strData);
            printer.close();
        }catch(IOException e){
            System.out.println("Failed to create string-containing file.");
        }
    }
    public static void resultAchieved(){
        System.out.println("Search completed in " +SearchThread.getExecTime());
    }
    private static ArrayList<String> loadStrings(File strFile){
        ArrayList<String> randStrings=new ArrayList<String>();
        try{
        Scanner reader=new Scanner(strFile);
        Random rand=new Random();
        ArrayList<String> readStrings=new ArrayList<String>();
        while(reader.hasNextLine())
            readStrings.add(reader.nextLine());
        while(!readStrings.isEmpty()){
            int i=rand.nextInt(readStrings.size());
            randStrings.add(readStrings.remove(i));
        }
        
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return randStrings;
    }
}
/*
char[] strData={'A','A','A','A'};
        ArrayList<String> list=new ArrayList<>();
            while(!(strData[0]=='Z'&&strData[1]=='Z'&&strData[2]=='Z'&&strData[3]=='Z')){
                list.add(new String(strData));
                strData[3]++;
                for(int i=3;i>0&&strData[i]>'Z';i--){
                    strData[i]='A';
                    strData[i-1]++;

                }
            }
            list.add("ZZZZ");
*/