package nju.pasa;

import scala.Array;
import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Huangsb on 2017/3/20.
 */
public class QueryGraphProcess {
    public static void main(String[] args) {
        String graphName = "sample";
        Scanner scan =new Scanner(System.in);
        String[] strInput = scan.nextLine().split(" ");
        System.out.println(strInput.length);
        HashMap<Integer, ArrayList<Integer>> edgeList = new HashMap();
        for(int i = 0; i < strInput.length; i = i + 2){
            int a = Integer.valueOf(strInput[i]);
            int b = Integer.valueOf(strInput[i + 1]);
            if(edgeList.containsKey(a)){
                edgeList.get(a).add(b);
            } else {
                ArrayList<Integer> al = new ArrayList();
                al.add(b);
                edgeList.put(a, al);
            }
            if(edgeList.containsKey(b)){
                edgeList.get(b).add(a);
            } else {
                ArrayList<Integer> al = new ArrayList();
                al.add(a);
                edgeList.put(b, al);
            }
        }
        //
        /* query graph */
        String query = "q0";
        String queryGraphPath = graphName + "/querygraph" + query;
        try {

        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }
