import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final String LETTERS = "RLRFR";
    public static final int ROUTE_LENGTH = 100;
    public static final int THREADS = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREADS; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS, ROUTE_LENGTH);
                int count = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey()
        + " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
        route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
