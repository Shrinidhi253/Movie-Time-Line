package movieTimeLine;

public class utilities {
    public static String to2DigitInt(int n) {
        if (n <= 9)
            return "0%d".formatted(n);
        return "%d".formatted(n);
    }
}