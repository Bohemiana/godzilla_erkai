import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello world!");
//
//
//
//    }
    private static Random rand = new Random();

    public static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for (int i = length; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    public static void main(String[] args) {
        String[] arr = { "码仙1", "码仙2", "码仙3", "码仙4", "码仙5", "码仙6", "码仙7", "码仙8" };
        shuffle(arr);
        System.out.println(arr[1]);

        System.out.println(Arrays.toString(arr));
    }
}