package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class GUI extends JFrame implements ActionListener{
    private JTextArea resultArea;
    private JTextField execTimeField,searchField,threadField;
    private ArrayList<String> list;
    private int searchesEnded;
    private String[] queries;
    public GUI(ArrayList<String> list){
        searchesEnded=0;
        this.list=list;
        this.queries=new String[]{"Test","word"};
        SearchThread.initialize(this.list,queries);
        
        setSize(600,600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Word Search");
        
        resultArea=new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultPane=new JScrollPane(resultArea);
        JLabel resultLabel=new JLabel("Search Results:");
        resultLabel.setPreferredSize(new Dimension(100,20));
        resultLabel.setAlignmentX(this.CENTER_ALIGNMENT);
        resultPane.setPreferredSize(new Dimension(500,25));
        
        JPanel settingPanel=new JPanel();
        settingPanel.setLayout(new GridLayout(0,2));
        settingPanel.setSize(580,80);
        JLabel searchLabel,threadLabel,execTimeLabel;
        searchLabel=new JLabel("Search for: ");
        threadLabel=new JLabel("Number of threads:");
        execTimeLabel=new JLabel("Execution time:");
        searchField=new JTextField();
        threadField=new JTextField();
        execTimeField=new JTextField();
        execTimeField.setEditable(false);
        execTimeField.setSize(50,25);
        threadField.setSize(50,25);
        settingPanel.add(searchLabel);
        settingPanel.add(searchField);
        settingPanel.add(threadLabel);
        settingPanel.add(threadField);
        settingPanel.add(execTimeLabel);
        settingPanel.add(execTimeField);
        settingPanel.setBorder(BorderFactory.createEmptyBorder(25,10,50,0));
        
        JButton searchButton=new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setSize(300,40);
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        
        Container pane=getContentPane();
        pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));
        pane.add(resultLabel);
        pane.add(resultPane);
        pane.add(settingPanel);
        pane.add(searchButton);
        pack();
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        resultArea.setText("");
        String queryString=searchField.getText();
        queries=getQueries(queryString);
        try{
            int numOfThreads=Integer.parseInt(threadField.getText());
        SearchThread.searchFor(queries);
            SearchThread[] threads=new SearchThread[numOfThreads];
            for(int i=0;i<threads.length;i++)
                threads[i]=new SearchThread(i%queries.length,this);
            for(int i=0;i<threads.length;i++)
                threads[i].start();
        }catch(Exception ex){
            threadField.setText("Please specify a valid amount");
        }
    }
    public void showResult(String result){
        resultArea.append(result);
    }
    public void notifySearchEnd(){
        searchesEnded++;
        if(searchesEnded==queries.length)
            execTimeField.setText(SearchThread.getExecTime()+"ms");
    }
    private String[] getQueries(String queryString){
        ArrayList<String> queries=new ArrayList<>();
        int start=0,end=0;
        for(end=0;end<queryString.length();end++){
            if(queryString.charAt(end)==' '){
                queries.add(queryString.substring(start,end));
                start=end+1;
            }
        }
        if(queryString.charAt(queryString.length()-1)!=' ')
            queries.add(queryString.substring(start));
        String[] queryArray=queries.toArray(new String[queries.size()]);
        return queryArray;
    }
    
}
