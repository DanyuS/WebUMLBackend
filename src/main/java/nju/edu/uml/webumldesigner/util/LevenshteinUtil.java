package nju.edu.uml.webumldesigner.util;

public class LevenshteinUtil {
    private static int distance(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        int matrix[][] = initMatrix(length1, length2);
        for (int i = 0; i < length2; i++) {
            String s1 = str2.substring(i, i + 1);
            for (int j = 0; j < length1; j++) {
                String s2 = str1.substring(j, j + 1);
                int cost = 1;
                if (s1.equals(s2)) {
                    cost = 0;
                }
                int left = matrix[i + 1][j] + 1;
                int up = matrix[i][j + 1] + 1;
                int leftUp = matrix[i][j] + cost;
                int min = findMin(left, up, leftUp, s1, s2, cost);
                matrix[i + 1][j + 1] = min;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            String str = "";
            for (int j = 0; j < matrix[i].length; j++) {
                str = str + matrix[i][j] + "\t";
            }
            System.out.println(str);
        }

        return matrix[length2][length1];
    }

    private static int[][] initMatrix(int length1, int length2) {
        int matrix[][] = new int[length2 + 1][length1 + 1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }
        return matrix;
    }

    private static int findMin(int left, int up, int leftUp, String s1, String s2, int cost) {
        int min;
        int temp;
        if (left <= up) {
            temp = left;
        } else {
            temp = up;
        }
        if (temp <= leftUp) {
            min = temp;
        } else {
            min = leftUp;
            if (cost == 1) {
                System.out.println("1---------" + s1 + "----------" + s2);
            }
        }
        return min;
    }

    private static int findMax(String str1, String str2) {
        int max;
        int len1 = str1.length();
        int len2 = str2.length();
        if (len1 > len2) {
            max = len1;
        } else {
            max = len2;
        }

        return max;
    }

    public static void main(String args[]) {
        String str1 = "hello";
        String str2 = "hello";
        int distance = distance(str1, str2);
        int max = findMax(str1, str2);
        double percent = 1.0 - distance * 1.0 / max;
        System.out.println(percent);
    }
}
