package a1_2001140061;
import java.util.Arrays;
public class CoffeeTinGame {
    public static final char[] BeansBag = new char[999];
    private static final char GREEN = 'G';
    private static final char BLUE = 'B';
    private static final char REMOVED = '-';
    private static final char NULL = '\u0000';

    public static void main(String[] args) {
        char[] tin = {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN};

        int greens = 0;
        for (char bean: tin) {
            if (bean == GREEN) {
                greens++;
            }
        }

        final char last = (greens % 2 == 1) ? GREEN : BLUE;

        System.out.printf("tin before: %s %n", Arrays.toString(tin));

        char lastBean = tinGame(tin);

        System.out.printf("tin after: %s %n", Arrays.toString(tin));

        if (lastBean == last) {
            System.out.printf("last bean: %c ", lastBean);
        } else {
            System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
        }
    }

    // change to public
    public static char tinGame(char[] tin) {
        while(hasAtLeastTwoBeans(tin)) {
            // take 2 bean from tin
            char[] takeTwoBean = takeTwo(tin);
            char beanFirst = takeTwoBean[0];
            char beanSecond = takeTwoBean[1];

            // process update tin
            if (beanFirst == BLUE && beanSecond == BLUE) {
                putIn(tin, BLUE);
            } else {
                if (beanFirst == GREEN && beanSecond == GREEN) {
                    putIn(tin, BLUE);
                } else {
                    putIn(tin, GREEN);
                }
            }
        }
        return anyBean(tin);
    }

    private static boolean hasAtLeastTwoBeans(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if (bean != REMOVED) {
                count++;
            }

            // enough bean
            if (count >= 2) {
                return true;
            }
        }

        // not enough bean
        return false;
    }

    private static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);
        char[] res = {first, second};
        return res;
    }

    public static char takeOne(char[] tin) {
        for (int i = 0; i < tin.length; i++) {
            char bean = tin[i];
            if (bean != REMOVED) {
                tin[i] = REMOVED;
                return bean;
            }
        }
        return NULL;
    }

    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == REMOVED) {
                tin[i] = bean;
                break;
            }
        }
    }

    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != REMOVED) {
                return bean;
            }
        }
        return NULL;
    }



}
