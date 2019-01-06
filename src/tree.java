import java.util.ArrayList;
import java.util.Random;

public class tree {
}

class z {//store variables
    final double speed = 0.01, jumpt = .3;//1/speed in fact
    final double G = 40000.0 / 9;
}

class planttree {
    private final int pof = 1500;//you can change the value to control possibility
    private final z mai = new z();
    private final double itv = 160;//interval of two tree//use it to change difficulty
    private double dis;
    private int po = pof, magic = 0, j = 25;

    ArrayList<tree> trees = new ArrayList<>();//add new kind of tree

    planttree() {
        trees.add(new tree(0, 0, 400));//no tree
        trees.add(new tree(70, 35, countd(70)));
        trees.add(new tree(60, 30, countd(60)));
        trees.add(new tree(50, 100, countd(50)));
        Runner.nearm = 0;
        Runner.near = 1000;
        dis = 0;
    }

    public int istree() {
        Random fly = new Random();
        return fly.nextInt(5) == 4 ? (
                fly.nextInt(3) == 0 ?
                        fly.nextInt(20) + 230 : fly.nextInt(180) + 40) : 1;
    }

    int newh() {//generalize new tree
        dis = Runner.W - Runner.near;

//        if (po - magic * 50 > 100)//magic!!
//            po -= magic * 50;

        Random ran = new Random();
        int newr = ran.nextInt(po);
        if (newr > j) newr = 0;
        else {
            newr = ran.nextInt(trees.size() - 1) + 1;//1 to tree number
            while (trees.get(newr).danger + trees.get(Runner.nearm).width + trees.get(Runner.nearm).danger + itv > dis) {
                newr = ran.nextInt(po);
                if (newr > j) {//add the weight to produce more tree because loss of chance
                    newr = ran.nextInt(trees.size() * 2) - trees.size();//another chance
                    if (newr <= 0) {
                        newr = 0;
                        magic++;
                        break;
                    }
                } else newr = ran.nextInt(trees.size() - 2) + 1;
            }
        }
        if (newr != 0) {
            Runner.nearm = newr;
            magic = 0;
            po = pof;
        }
        return newr;
    }

    private double countd(int h) {//distance between the bottom of the parabola and the safe height
        double u = Math.sqrt((double) 2 * h / mai.G);//time to fall
        double v = u / mai.speed;
        double e = mai.jumpt / mai.speed - v + .01;
        return e;
    }


    private class tree {
        int height, width;
        double danger;

        tree(int h, int w, double d) {
            height = h;
            width = w;
            danger = d;
        }
    }
}

