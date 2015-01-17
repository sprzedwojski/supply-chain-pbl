package ga;

/**
 * Created by Marta on 2015-01-15.
 */
public class BitIntConversion {

    private static int INTERVALEND=-1;
    public static int binaryToInt(byte[] array) {
        int tempResult = 0;
        int j = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 1) {
                tempResult += Math.pow(2, j);
            }
            j++;
        }
        int result =(int) Math.round((tempResult*INTERVALEND)/(Math.pow(2, array.length)-1));
        return result;
    }

    public static int calculateNumOfBits(int intervalEnd) {

        INTERVALEND = intervalEnd;
        double temp = INTERVALEND  + 1;
        int numOfBits = (int) Math.ceil((Math.log(temp) / Math.log(2)));
        return numOfBits;
    }


}
