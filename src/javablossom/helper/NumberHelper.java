package javablossom.helper;

/**
 * The {@code NumberHelper} class is used to provide some useful methods for
 * number manipulation.
 */
public class NumberHelper {

    /**
     * This method is used to convert a given number to a currency format.
     * 
     * @param money The number to be converted.
     * @return The currency format of the given number.
     */
    public static String toCurrency(Double money) {
        if (money == null || money == 0) {
            return "0,00";
        }
        StringBuilder moneyString = new StringBuilder(String.format("%.2f", money).replace(".", ","));
        int start = moneyString.indexOf(",") != -1 ? moneyString.indexOf(",") - 1 : moneyString.length();
        for (int i = start, j = 1; i >= 0; i--, j++) {
            if (j % 3 == 0) {
                moneyString.insert(i, " ");
            }
        }
        return moneyString.toString();
    }
}
