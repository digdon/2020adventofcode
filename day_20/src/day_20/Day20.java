package day_20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day20 {

    public static void main(String[] args) {
        List<String> tileInputList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = null;
        
        try {
            while ((inputLine = reader.readLine()) != null) {
                tileInputList.add(inputLine);
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
        
        // Build the tiles
        Map<Integer, char[][]> tileMap = new HashMap<>();
        int tileNumber = 0;
        char[][] tileGrid = null;
        int tileRow = 0;
        
        for (String tileData : tileInputList) {
            if (tileData.startsWith("Tile")) {
                tileNumber = Integer.valueOf(tileData.substring(tileData.indexOf(' ') + 1, tileData.length() - 1));
                tileGrid = null;
            } else if (tileData.length() == 0) {
                tileMap.put(tileNumber, tileGrid);
            } else {
                if (tileGrid == null) {
                    tileGrid = new char[tileData.length()][tileData.length()];
                    tileRow = 0;
                }
                
                for (int i = 0; i < tileData.length(); i++) {
                    tileGrid[tileRow][i] = tileData.charAt(i);
                }
                
                tileRow++;
            }
        }

        if (tileGrid != null) {
            tileMap.put(tileNumber, tileGrid);
        }

        partOne(tileMap);
    }

    private static void partOne(Map<Integer, char[][]> tileMap) {
        List<Integer> cornerTiles = new ArrayList<>();
        
        for (Integer tileNumber : tileMap.keySet()) {
            List<Integer> adjoiningTiles = findAdjoiningTiles(tileNumber, tileMap);
            
            if (adjoiningTiles.size() == 2) {
                cornerTiles.add(tileNumber);
            }
        }
        
        long value = 1;
        
        for (int tile : cornerTiles) {
            value *= tile;
        }
        
        System.out.println(cornerTiles);
        System.out.println("Part one: " + value);
    }

    private static List<Integer> findAdjoiningTiles(int tileNumber, Map<Integer, char[][]> tileMap) {
        System.out.println("Looking for adjoining tiles for " + tileNumber);
        char[][] firstTile = tileMap.get(tileNumber);
        List<Integer> adjoiningList = new ArrayList<>();
        
        for (Entry<Integer, char[][]> entry : tileMap.entrySet()) {
            if (entry.getKey() == tileNumber) {
                // A tile cannot adjoin to itself - skip it
                continue;
            }
            
            char[][] secondTile = entry.getValue();
            boolean matchedToFirst = false;

            for (int flips = 0; flips < 2; flips++) {
                for (int rotations = 0; rotations < 4; rotations++) {
                    matchedToFirst = checkAllEdges(firstTile, secondTile);
    
                    if (matchedToFirst) {
                        tileMap.put(entry.getKey(), secondTile);
                        break;
                    } else {
                        System.out.println("Rotating tile " + entry.getKey());
                        secondTile = rotateTileCW(secondTile);
                    }
                }
                
                if (matchedToFirst) {
                    break;
                }

                secondTile = rotateTileCW(secondTile);
                System.out.println("Flipping tile " + entry.getKey());
                secondTile = flipTileHorizontal(secondTile);
            }
            
            if (matchedToFirst) {
                adjoiningList.add(entry.getKey());
            }
        }
        
        return adjoiningList;
    }

    private static boolean checkAllEdges(char[][] first, char[][] second) {
        // Does top of first match bottom of second?
        boolean edgeMatches = true;
        
        for (int col = 0; col < first.length; col++) {
            if (first[0][col] != second[second.length - 1][col]) {
                edgeMatches = false;
                break;
            }
        }

        if (edgeMatches) {
            System.out.println("top of first matches bottom of second");
            return true;
        }
        
        // Does right of first match of left of second?
        edgeMatches = true;
        
        for (int row = 0; row < first.length; row++) {
            if (first[row][first.length - 1] != second[row][0]) {
                edgeMatches = false;
                break;
            }
        }
        

        if (edgeMatches) {
            System.out.println("right of first matches left of second");
            return true;
        }
        
        // Does bottom of first match top of second?
        edgeMatches = true;
        
        for (int col = 0; col < first.length; col++) {
            if (first[first.length - 1][col] != second[0][col]) {
                edgeMatches = false;
                break;
            }
        }

        if (edgeMatches) {
            System.out.println("bottom of first matches top of second");
            return true;
        }
        
        // Does left of first match right of second?
        edgeMatches = true;
        
        for (int row = 0; row < first.length; row++) {
            if (first[row][0] != second[row][second.length - 1]) {
                edgeMatches = false;
                break;
            }
        }
        

        if (edgeMatches) {
            System.out.println("left of first matches right of second");
            return true;
        }
        
        return false;
    }
    
    private static char[][] rotateTileCW(char[][] inputGrid) {
        int gridSize = inputGrid.length;
        char[][] outputGrid = new char[gridSize][gridSize];
        
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                outputGrid[col][gridSize - row - 1] = inputGrid[row][col];
            }
        }
        
        return outputGrid;
    }
    
    private static char[][] flipTileHorizontal(char[][] inputGrid) {
        int gridSize = inputGrid.length;
        char[][] outputGrid = new char[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                outputGrid[row][gridSize - col - 1] = inputGrid[row][col];
            }
        }

        return outputGrid;
    }
    
    private static char[][] flipTileVertical(char[][] inputGrid) {
        int gridSize = inputGrid.length;
        char[][] outputGrid = new char[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                outputGrid[gridSize - row - 1][col] = inputGrid[row][col];
            }
        }

        return outputGrid;
    }
    
    private static void displayTile(char[][] tile) {
        for (int row = 0; row < tile.length; row++) {
            for (int col = 0; col < tile[row].length; col++) {
                System.out.print(tile[row][col]);
            }
            
            System.out.println();
        }
        
        System.out.println();
    }
}
