package a1_2001140061;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class CoffeeTinGame {

    private static final char GREEN = 'G';
    private static final char BLUE = 'B';
    private static final char REMOVED = '-';
    private static final char NULL = '\u0000';


    private static final int LENGTH = 30;
    public static final char[] BeansBag = new char[LENGTH];

    public static void main(String[] args) {
        for (int i = 0; i < LENGTH; i++) {
            if(i < LENGTH/3) {
                BeansBag[i] = BLUE;
            } else {
                if (i >= 2 * LENGTH / 3) {
                    BeansBag[i] = REMOVED;
                } else {
                    BeansBag[i] = GREEN;
                }
            }
        }
        Random rd = new Random();
        for (int i = 1; i < LENGTH; i++) {
            int pos = rd.nextInt(i);
            char temp = BeansBag[pos];
            BeansBag[pos] = BeansBag[i];
            BeansBag[i] = temp;
        }

        char[] tin = {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN, BLUE, BLUE, GREEN, GREEN};

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
            //System.out.printf("tin : %s %n", Arrays.toString(tin));
            // process update tin
            updateTin(tin, beanFirst, beanSecond);
            //System.out.printf("tin : %s %n", Arrays.toString(tin));
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
        return new char[] {first, second};
    }

    public static char takeOne(char[] tin) {
        int beanNumber = 0;
        for (int i = 0; i < tin.length; i++) {
            char bean = tin[i];
            if (bean != REMOVED) {
                beanNumber++;
            }
        }
        int random = randInt(beanNumber);
        int posBean = 0;

        for(int i = 0; i < random;) {
            if(tin[posBean] == REMOVED) {
                posBean++;
            } else {
                posBean++;
                i++;
            }
        }


        char bean = tin[posBean];
        while (bean == REMOVED) {
            bean = tin[++posBean];
        }
        tin[posBean] = REMOVED;
        //System.out.printf("tin : %s %n", Arrays.toString(tin));
        return bean;
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

    public static int randInt(int n) {

        return ThreadLocalRandom.current().nextInt(0, n);
    }

    public static char getBean(char[] Bag, char b) {
        int beanNumber = 0;
        for (int i = 0; i < Bag.length; i++) {
            char bean = Bag[i];
            if (bean == b) {
                beanNumber++;
            }
        }
        if (beanNumber == 0) {
            return NULL;
        }
        int random = randInt(beanNumber);
        int posBean = 0;
        for (int i = 0; i < random;) {
            if (Bag[posBean] == b) {
                i++;
            }
            posBean++;
        }
        Bag[posBean] = REMOVED;
        return b;
    }

    public static void updateTin(char[] tin, char beanFirst, char beanSecond) {
        char bean;
        if (beanFirst == BLUE && beanSecond == BLUE) {
            bean = getBean(BeansBag, BLUE);
            putIn(tin, bean);
        } else {
            if (beanFirst == GREEN && beanSecond == GREEN) {
                bean = getBean(BeansBag, BLUE);
                putIn(tin, bean);
            } else {
                bean = getBean(BeansBag, GREEN);
                putIn(tin, bean);
            }
        }
    }

}
