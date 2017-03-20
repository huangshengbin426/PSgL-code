package nju.pasa;

import scala.Array;
import scala.Int;

import java.io.*;
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
        String serial = String.valueOf(0);
        String query = "q" + serial;
        String edgeorientation = "eo"+ serial;

        Scanner scan =new Scanner(System.in);
        System.out.println("query graph:");
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
        String queryGraphPath = graphName + "/querygraph/" + query;
        try {
            FileOutputStream fis = new FileOutputStream(queryGraphPath);
            DataOutputStream dis = new DataOutputStream(fis);
            int line = edgeList.size();
            System.out.println("line:" + line);
            int count = 1;
            for(Map.Entry<Integer, ArrayList<Integer>> ele : edgeList.entrySet()){
                StringBuilder sb = new StringBuilder();
                sb.append(ele.getKey().toString());
                sb.append("\t" + "1");
                for (int i : ele.getValue()){
                    sb.append("\t" + i);
                }
                if (count != line)
                sb.append("\n");
                count = count + 1;
                dis.writeBytes(sb.toString());
            }
            dis.flush();
            System.out.println("query graph write success");
        }catch(IOException e){
            e.printStackTrace();
        }

        /* edge orientation */
        String edgeOrientationPath = graphName + "/querygraph/" + edgeorientation;
        try{
            FileOutputStream fis = new FileOutputStream(edgeOrientationPath);
            DataOutputStream dis = new DataOutputStream(fis);
            int edgeOrientationSize = 1;
            int size = edgeList.size();
            dis.writeInt(edgeOrientationSize);

            // write vertexIdIndexMap
            for (int i = 0; i < size; i++){
                dis.writeInt(i);
                dis.writeInt(i);
            }

            // automorphism info
            System.out.println("automorphism info:");
            String[] automorphismInfo = scan.nextLine().split(" ");

            // write edgeSequenceMatrix
            int[][] edgeSequenceMatrix = new int[size][size];
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                   edgeSequenceMatrix[i][j] = Integer.valueOf(automorphismInfo[size * i + j]);
                   dis.writeByte(edgeSequenceMatrix[i][j]);
                }
            }
            dis.flush();
            System.out.println("edge orientation write success");
            // output edgeSequenceMatrix
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    System.out.print(edgeSequenceMatrix[i][j] + " ");
                }
                System.out.println();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        }
    }
