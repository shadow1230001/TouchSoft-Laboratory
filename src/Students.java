import java.util.Arrays;
import java.util.function.Predicate;

public class Students {

    public static void main(String[] args) {
        test(calculate(new int[]{1, 2, 3, 4, 5, 3, 2, 1, 2, 6, 5, 4, 3, 3, 2, 1, 1, 3, 3, 3, 4, 2}), 47);
        test(calculate(new int[]{1, 1, 1, 2, 2, 2, 2}), 8);
        test(calculate(new int[]{1, 1, 1, 2, 2, 2, 1}), 9);
        test(calculate(new int[]{2, 2, 1}), 4);
        test(calculate(new int[]{-5, 2, 2}), 4);
        test(calculate(new int[]{2, 2, 2}), 3);
        test(calculate(new int[]{50, 2, 60}), 5);
        test(calculate(new int[]{5, 100, 2}), 4);
        test(calculate(new int[]{100}), 1);
        test(calculate(new int[]{-100}), 1);
        test(calculate(new int[]{1, 1}), 2);
        test(calculate(new int[]{-1, 1}), 3);
    }

    private static int calculate(int[] levels) {
        int length = levels.length;
        int[] grants = new int[length];
        for (int i = 0; i < length; i++) {
            if (isMin(i, levels)) {
                grants[i] = 1;
                int j = i + 1;
                while (j < length && levels[j] > levels[j - 1]) {
                    grants[j] = Math.max(grants[j], grants[j - 1] + 1);
                    j++;
                }
                j = i - 1;
                while (j >= 0 && levels[j] > levels[j + 1]) {
                    grants[j] = Math.max(grants[j], grants[j + 1] + 1);
                    j--;
                }
            }
        }
        return Arrays.stream(grants).sum();
    }

    private static void fillGrants(int startIndex, int direction, int[] levels, int[] grants, Predicate<Integer> underLimit) {
        int j = startIndex + direction;
        while (underLimit.test(j) && levels[j] > levels[j - direction]) {
            grants[j] = Math.max(grants[j], grants[j - direction] + 1);
            j += direction;
        }
    }

    private static boolean isMin(int i, int[] levels) {
        if (levels.length == 1) return true;
        if (i == 0) {
            return levels[i] <= levels[i + 1];
        } else if (i == levels.length - 1) {
            return levels[i] <= levels[i - 1];
        } else {
            return levels[i] <= levels[i - 1] && levels[i] <= levels[i + 1];
        }
    }

    private static <T> void test(T value, T expectedValue) {
        System.out.println(value == expectedValue ? "Passed" : "Not Passed!");
    }

}
