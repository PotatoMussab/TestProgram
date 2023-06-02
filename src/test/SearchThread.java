package test;
import java.util.Iterator;
import java.util.ArrayList;

public class SearchThread extends Thread{
    private static Iterator<String>[] iter; //Iterator for each query
    private static ArrayList<String>[] searchLists; //Multiple lists of random strings for each query for the purpose of different iterators
    private static ArrayList<String> searchList;//The list to search through
    private static boolean[] resultFound;// Is the result found for each query?
    private static int[] valsTaken,resultIndex; //Counter to keep track of how many values have been taken so far | Index of the found result (-1 if not found)
    private static int numOfQueries; //Number of queries to be searched for
    private int localIndex,queryID;
    private static String[] queries;
    private static long start,end;
    private GUI listener;
    
    public SearchThread(int queryID, GUI window){
        super();
        this.queryID=queryID;
        listener=window;
    }
    public static void initialize(ArrayList<String> newSearchList, String[] newQueries){
        searchList=newSearchList;
        SearchThread.queries=newQueries;
        numOfQueries=newQueries.length;
        iter=(Iterator<String>[]) new Iterator[numOfQueries];
        searchLists=(ArrayList<String>[]) new ArrayList[numOfQueries];
        resultFound=new boolean[numOfQueries];
        valsTaken=new int[numOfQueries];
        resultIndex=new int[numOfQueries];
        for(int i=0;i<numOfQueries;i++){
            searchLists[i]=searchList;
            iter[i]=searchLists[i].iterator();
            resultFound[i]=false;
            valsTaken[i]=0;
            resultIndex[i]=-1;
        }
        start=System.currentTimeMillis();
        end=start;
        
    }
    public static void searchFor(String[] newQueries){
        SearchThread.queries=newQueries;
        numOfQueries=newQueries.length;
        iter=(Iterator<String>[]) new Iterator[numOfQueries];
        searchLists=(ArrayList<String>[]) new ArrayList[numOfQueries];
        resultFound=new boolean[numOfQueries];
        valsTaken=new int[numOfQueries];
        resultIndex=new int[numOfQueries];
        for(int i=0;i<numOfQueries;i++){
            searchLists[i]=searchList;
            iter[i]=searchLists[i].iterator();
            resultFound[i]=false;
            valsTaken[i]=0;
            resultIndex[i]=-1;
        }
        start=System.currentTimeMillis();
        end=start;
    }
    public void run(){
        localIndex=0;
        while(!resultFound[queryID]&&iter[queryID].hasNext()){
           String s;
           synchronized(SearchThread.class){
               /*
               This 'if' statement is necessary because sometimes a different
               thread will take the next value after the while loop but before
               the current thread takes iter.next();
               */
                if(iter[queryID].hasNext())
                    s=iter[queryID].next();
                else
                    return;
                valsTaken[queryID]++;
                localIndex=valsTaken[queryID]-1;
           }
           if(s.equalsIgnoreCase(queries[queryID])){
               resultFound[queryID]=true;
               resultIndex[queryID]=localIndex;
               end=System.currentTimeMillis();
               listener.showResult(queries[queryID]+" was found at index: "+resultIndex[queryID]+"\n");
               listener.notifySearchEnd();
               return;
           }
        }
    }
    public static int getResultIndex(int queryID){
        return resultIndex[queryID];
    }
    public static boolean isResultFound(int queryID){
        return resultFound[queryID];
    }
    public static long getExecTime(){
        return end-start;
    }
}
