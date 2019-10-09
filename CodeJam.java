import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CodeJam {
    private static int getInt(String time) {
        String[] cs = time.split(":");
        return Integer.parseInt(cs[0]) * 60 + Integer.parseInt(cs[1]);
    }

    private static int[] solve(int[][] schedules) {
        int[] ret = new int[2];
        Arrays.sort(schedules, (a, b) -> Integer.compare(a[0], b[0]) == 0 ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
        PriorityQueue<int[]>[] availableTrain = new PriorityQueue[2];
        availableTrain[0] = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        availableTrain[1] = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        for (int i = 0; i < schedules.length; i++) {
            int[] schedule = schedules[i];
            int curStation = schedule[2];
            int otherStation = curStation ^ 1;
            ret[curStation]++;
            availableTrain[otherStation].offer(schedule);
            if (!availableTrain[curStation].isEmpty() && availableTrain[curStation].peek()[1] <= schedule[0]) {
                ret[curStation]--;
                availableTrain[curStation].poll();
            }
        }
        return ret;
    }

    private static void sort(String fileName) throws Exception {
        String outputFileName = fileName.substring(0, fileName.length() - 2) + "out";
        Scanner scanner = new Scanner(new File(fileName));
        PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
        int numCases = scanner.nextInt();
        for (int i = 1; i <= numCases; i++) {
            int turnAround = scanner.nextInt();
            int na = scanner.nextInt(), nb = scanner.nextInt();
            scanner.nextLine();
            int[][] schedules = new int[na + nb][];
            for (int j = 0; j < na; j++) {
                String[] cs = scanner.nextLine().split(" ");
                schedules[j] = new int[]{getInt(cs[0]), getInt(cs[1]) + turnAround, 0};
            }
            for (int j = 0; j < nb; j++) {
                String[] cs = scanner.nextLine().split(" ");
                schedules[na + j] = new int[]{getInt(cs[0]), getInt(cs[1]) + turnAround, 1};
            }
            int[] ret = solve(schedules);
            System.out.println(String.format("Case #%d: %d %d", i, ret[0], ret[1]));
            writer.println(String.format("Case #%d: %d %d", i, ret[0], ret[1]));
        }
        writer.close();
    }

    private static int[] solve2(int[] aCount, int[] bCount) {
        int[] ret = new int[2];
        int aCur = 0, bCur = 0;
        for (int i = 0; i < 24 * 60; i++) {
            aCur += aCount[i];
            if (aCur > 0) {
                ret[0] += aCur;
                aCur = 0;
            }
            bCur += bCount[i];
            if (bCur > 0) {
                ret[1] += bCur;
                bCur = 0;
            }
        }
        return ret;
    }

    private static void constant(String fileName) throws Exception {
        String outputFileName = fileName.substring(0, fileName.length() - 2) + "out2";
        Scanner scanner = new Scanner(new File(fileName));
        PrintWriter writer = new PrintWriter(outputFileName, "UTF-8");
        int numCases = scanner.nextInt();
        for (int i = 1; i <= numCases; i++) {
            int turnAround = scanner.nextInt();
            int na = scanner.nextInt(), nb = scanner.nextInt();
            scanner.nextLine();
            int[] aCount = new int[24 * 60 + 60];
            int[] bCount = new int[24 * 60 + 60];
            for (int j = 0; j < na; j++) {
                String[] cs = scanner.nextLine().split(" ");
                aCount[getInt(cs[0])]++;
                bCount[getInt(cs[1]) + turnAround]--;
            }
            for (int j = 0; j < nb; j++) {
                String[] cs = scanner.nextLine().split(" ");
                bCount[getInt(cs[0])]++;
                aCount[getInt(cs[1]) + turnAround]--;
            }
            int[] ret = solve2(aCount, bCount);
            System.out.println(String.format("Case #%d: %d %d", i, ret[0], ret[1]));
            writer.println(String.format("Case #%d: %d %d", i, ret[0], ret[1]));
        }
        writer.close();
    }


    public static void main(String[] args) throws Exception {
        String fileName = "B-large-practice.in";
        sort(fileName);
        constant(fileName);
    }
}
