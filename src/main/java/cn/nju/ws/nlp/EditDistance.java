package cn.nju.ws.nlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ciferlv on 17-6-6.
 */
public class EditDistance {

    private static Logger logger = LoggerFactory.getLogger(EditDistance.class);

    public static double editDistance(String str1, String str2) {

        int result = 0;
        int n = str1.length();
        int m = str2.length();

        if (m != 0 && n != 0) {
            //n行m列的矩阵
            int[][] distance = new int[n + 1][m + 1];

            int cnt = 0;
            //将i行0列从0开始赋值到n
            for (int i = 0; i < n + 1; i++) {
                distance[i][0] = cnt++;
            }
            cnt = 0;
            //将0行j列从0开始赋值到m
            for (int j = 0; j < m + 1; j++) {
                distance[0][j] = cnt++;
            }

            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < m + 1; j++) {
                    int temp = 1;
                    if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                        temp = 0;
                    }
                    distance[i][j] = Math.min(distance[i][j - 1] + 1, Math.min(distance[i - 1][j] + 1, temp + distance[i - 1][j - 1]));
                }
            }
            result = distance[n][m];
        } else {

            return 0;
        }

        return (double) 1 - (double) result / (Math.max(n, m));
    }
}
