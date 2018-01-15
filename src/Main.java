import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> inputParams =  new ArrayList<Integer>(
                Arrays.asList( 1, 2, 3, 4, 5, 3, 2, 1, 2, 6, 5, 4, 3, 3, 2, 1, 1, 3, 3, 3, 4, 2 ));
        List<Integer> outputParams = initOutputParams(inputParams);
        show("input", inputParams);
        List<Integer> res = calculateOutputParams(inputParams, outputParams);
        show("res", res);

        int vres = 0;
        for(int i = 0; i < res.size(); i++) {
            vres+=res.get(i);
        }
        System.out.println(vres);

    }

    private static List<Integer> initOutputParams(List<Integer> inputParams) {
        List<Integer> outputParams = new ArrayList<Integer>();
        for(int i = 0; i < inputParams.size(); i++) {
            outputParams.add(1);
        }
        return outputParams;
    }

    private static List<Integer> calculateOutputParams(List<Integer> inputParams, List<Integer> outputParams) {
        for(int i = 0; i < inputParams.size(); i++) {
            checkPrev(inputParams, outputParams, i);
        }
        normalize(outputParams);
        return normalizeLists(splitMaximums(outputParams));
    }

    private static void checkPrev(List<Integer> inputParams, List<Integer> outputParams, int index) {
        if (index == 0) {
            return;
        }

        int prev = inputParams.get(index - 1);
        int current = inputParams.get(index);

        if (prev > current) {
            outputParams.set(index, outputParams.get(index-1) - 1);
        } else if (prev < current) {
            outputParams.set(index, outputParams.get(index-1) + 1);
        }
    }

    private static void normalize(List<Integer> outputParams) {
        for(int i = 0; i < outputParams.size(); i++) {
            if (outputParams.get(i) < 1) {
                increment(outputParams, -outputParams.get(i) + 1);
            }
        }
    }

    private static void increment(List<Integer> outputParams, int val) {
        for(int i = 0; i < outputParams.size(); i++) {
            outputParams.set(i, outputParams.get(i) + val);
        }
    }

    private static void show(String mess, List<Integer> outputParams) {
        System.out.print(mess + ": ");
        for(Integer outputParam: outputParams) {
            System.out.print(outputParam.intValue() + ", ");
        }
        System.out.println();
    }

    private static List<List<Integer>> splitMaximums(List<Integer> outputParams) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        int start = 0;
        boolean a = outputParams.get(0) > outputParams.get(1);
        for(int i = 0; i < outputParams.size() - 1; i++) {
            if (outputParams.get(i) > outputParams.get(i+1) != a){
                a = !a;
                res.add(outputParams.subList(start, i));
                start = i;
            }
        }
        res.add(outputParams.subList(start, outputParams.size()));
        return res;
    }

    private static List<Integer> normalizeLists(List<List<Integer>> lists) {
        List<Integer> res = new ArrayList<Integer>();

        int f = 1;
        List<Integer> flist = lists.get(0);
        if (flist.get(0) < flist.get(flist.size()-1)) {
            f = 0;
        }

        for(int i = f; i < lists.size(); i += 2) {
            List<Integer> list = lists.get(i);
            int first = list.get(0);
            increment(list, -first + 1);
        }

        if (f == 1) {
            f = 0;
        } else {
            f = 1;
        }

        for(int i = f; i < lists.size(); i+=2) {
            int leftdif = 0;
            int rightdif = 0;
            List<Integer> list = lists.get(i);
            if (i != 0) {
                Integer prev = lists.get(i-1).get(lists.get(i-1).size()-1);
                leftdif = list.get(0) - prev - 1;
            }
            if (i < lists.size() - 1) {
                Integer next = lists.get(i+1).get(0);
                rightdif = list.get(list.size()-1) - next -1;
            }
            int dif = leftdif;
            if (leftdif > rightdif) {
                dif = rightdif;
            }
            increment(lists.get(i), -dif);

            if (leftdif < rightdif) {
                increment(list, leftdif - rightdif);
                list.set(0, list.get(0) + rightdif - leftdif);
            }

        }

        for(int i = 0; i < lists.size(); i++) {
            res.addAll(lists.get(i));

        }

        return res;
    }

}