package a1_2001140061;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @overview A program that performs the coffee tin game on a
 *           tin of beans and display result on the standard output.
 *
 */
public class CoffeeTinGame {
    /*
     * constant value for the green bean.
     */
    private static final char GREEN = 'G';
    /*
     * constant value for the blue bean.
     */
    private static final char BLUE = 'B';
    /*
     *  constant for removed beans.
     */
    private static final char REMOVED = '-';
    /*
     * the null character.
     */
    private static final char NULL = '\u0000';

    // the length of bag bean.
    private static final int LENGTH = 30;
    // the bag of available beans.
    public static final char[] BeansBag = new char[LENGTH];

    /**
     * the main procedure
     *
     * @effects
     *          initialise a coffee tin
     *          print the tin content
     *          {@link @tinGame(char[])}: perform the coffee tin game on tin
     *          print the tin content again
     *          if last bean is correct
     *          print its colour
     *          else
     *          print an error message
     */
    public static void main(String[] args) {
        // create bean in the BeansBag
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

        // shuffle the bean in the BeansBag.
        Random rd = new Random();
        for (int i = 1; i < LENGTH; i++) {
            int pos = rd.nextInt(i);
            char temp = BeansBag[pos];
            BeansBag[pos] = BeansBag[i];
            BeansBag[i] = temp;
        }

        // initialise some beans.
        char[] tin = {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN};


        int greens = 0;
        for (char bean: tin) {
            if (bean == GREEN) {
                greens++;
            }
        }

        // p0 = green parity /\
        // (p0=1 -> last=GREEN) /\ (p0=0 -> last=BLUE)
        final char last = (greens % 2 == 1) ? GREEN : BLUE;

        // print the content of tin before the game.
        System.out.printf("tin before: %s %n", Arrays.toString(tin));

        // perform the game.
        char lastBean = tinGame(tin);

        // print the content of tin and last bean.
        System.out.printf("tin after: %s %n", Arrays.toString(tin));

        // check if last bean as expected and print.
        if (lastBean == last) {
            System.out.printf("last bean: %c ", lastBean);
        } else {
            System.out.printf("Oops, wrong last bean: %c (expected: %c)%n", lastBean, last);
        }

    }

    /**
     * Performs the coffee tin game to determine the colour of the last bean
     *
     * @requires tin is not null /\ tin.length > 0
     * @modifies tin
     * @effects
     *
     *          <pre>
     *   take out two beans from tin
     *   if same colour
     *     throw both away, put one blue bean back
     *   else
     *     put green bean back
     *   let p0 = initial number of green beans
     *   if p0 = 1
     *     result = `G'
     *   else
     *     result = `B'
     *          </pre>
     *   change to public
     */
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

    /**
     * @effects
     *          if tin has at least two beans
     *          return true
     *          else
     *          return false
     */
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

    /**
     * @requires tin has at least 2 beans left
     * @modifies tin
     * @effects
     *          remove any two beans from tin and return them
     */
    private static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);
        return new char[] {first, second};
    }

    /**
     * @requires tin has at least one bean
     * @modifies tin
     * @effects
     *          remove any bean from tin and return it
     */
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

    /**
     * @requires tin has vacant positions for new beans
     * @modifies tin
     * @effects
     *          place bean into any vacant position in tin
     */
    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == REMOVED) {
                tin[i] = bean;
                break;
            }
        }
    }

    /**
     * @effects
     *          if there are beans in tin
     *          return any such bean
     *          else
     *          return '\u0000' (null character)
     */
    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != REMOVED) {
                return bean;
            }
        }

        // no have bean
        return NULL;
    }

    /**
     * @requires n is a positive integer
     * @effects
     *          return a random integer from 0 to n (n is excluded)
     */
    public static int randInt(int n) {
        return ThreadLocalRandom.current().nextInt(0, n);
    }

    /**
     * @requires tin != null /\ beanType is in {BLUE,GREEN}
     * @modifies tin
     * @effects
     *          get a random bean from tin
     *          bean must be the same type as beanType
     */
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

    /**
     * @requires tin != null /\ bean1 is in {BLUE,GREEN} /\ bean2 is in {BLUE,GREEN}
     * @modifies tin, BeansBag
     * @effects
     *          if bean1 and bean2 is the same color
     *              throw both away
     *              put a blue bean from BeansBag in
     *          else
     *              throw both away
     *              put a green bean from BeansBag in
     */
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
