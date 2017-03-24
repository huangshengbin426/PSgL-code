package nju.pasa;

import org.apache.commons.io.IOExceptionWithCause;
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
        String serial = String.valueOf(10);
        String query = "q" + serial;
        String edgeorientation = "eo"+ serial;
        String edgeIndex = "edgeindex";

        Scanner scan =new Scanner(System.in);
        System.out.println("query graph: q" + serial);
        String[] strInput = scan.nextLine().split(" ");

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
            //System.out.println("line:" + line);
            int count = 1;
            System.out.println("query graph write info :");
            System.out.println(query + ":");
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
                System.out.println(sb.toString());
            }
            dis.flush();
            System.out.println("query graph write success");
        }catch(IOException e){
            e.printStackTrace();
        }

        /* edge orientation */
        System.out.println("automorphism info:");
        String edgeOrientationPath = graphName + "/querygraph/" + edgeorientation;
        String[] automorphismInfo = scan.nextLine().split(" ");
        System.out.println("edge orientation write info :");
        try{
            FileOutputStream fis = new FileOutputStream(edgeOrientationPath);
            DataOutputStream dis = new DataOutputStream(fis);
            int edgeOrientationSize = 1;
            int size = edgeList.size();
            dis.writeInt(edgeOrientationSize);
            System.out.println("edgeOrientationSize" + edgeOrientationSize);
            dis.writeInt(size);

            // write vertexIdIndexMap
            for (int i = 0; i < size; i++){
                dis.writeInt(i);
                System.out.println("key: " + i);
                dis.writeInt(i);
                System.out.println("label:" + i);
            }


            // write edgeSequenceMatrix
            int[][] edgeSequenceMatrix = new int[size][size];
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                   edgeSequenceMatrix[i][j] = Integer.valueOf(automorphismInfo[size * i + j]);
                   dis.writeByte(edgeSequenceMatrix[i][j]);
                    System.out.printf(edgeSequenceMatrix[i][j] + " ");
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

        /* edge index */
        System.out.println("edge index write info :");
        String edgeIndexPath = graphName + "/edgeindex/" + edgeIndex;
        int[] edgeindex = {7, 16, 17, 7, 11, 19, 13, 9};
        try{
            FileOutputStream fos = new FileOutputStream(edgeIndexPath);
            DataOutputStream dos = new DataOutputStream(fos);
            for (int i = 0; i < edgeindex.length; i++){
                if (i == 0 || i == 1){
                    dos.writeInt(edgeindex[i]);
                }else {
                    dos.writeByte(edgeindex[i]);
                }
                System.out.print(edgeindex[i] + " ");
            }
            dos.flush();
            System.out.println("\n edge index write success");
        }catch (IOException e){
            e.printStackTrace();
        }
        }

    }
/**
 *
 * write example
 * triangle: 0 1 2, 0 < 1 < 2
 * first input (query graph): 0 1 0 2 1 2
 * second input (automorphism): 0 1 1 2 0 1 2 2 0
 *
 * */