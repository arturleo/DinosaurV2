import java.util.Random;

public class cloud {
}


class findcloud {
    final private int pof = 1200;//you can change the value to control possibility
    final double itv = 250;//interval of two clouds

    private int j = 2;


    int newc() {//generalize new cloud

        Random ran = new Random();
        int newr = ran.nextInt(pof);
        if (newr > j) newr = 0;
        else {
            newr = ran.nextInt(250) + 10;//1 to tree number

        }

        return newr;
    }


}

