package day_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {

    public static void main(String[] argts) {
        List<Integer> inputList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = null;
        
        try {
            while ((inputLine = reader.readLine()) != null) {
                inputList.add(Integer.valueOf(inputLine));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        List<Integer> adapterList = new ArrayList<>(inputList);
        Collections.sort(adapterList);
        Map<Integer, Integer> diffMap = new HashMap<>();
        int currentJoltage = 0;
        
        for (int adapter : adapterList) {
            int diff = adapter - currentJoltage;
            
            Integer diffCount = diffMap.get(diff);
            
            if (diffCount == null) {
                diffCount = 0;
            }
            
            diffCount++;
            diffMap.put(diff, diffCount);
            currentJoltage = adapter;
        }
        
        diffMap.put(3, diffMap.get(3) + 1);
        System.out.println(diffMap);
        
        System.out.println("Part one: " + (diffMap.get(1) * diffMap.get(3)));
    }
}
