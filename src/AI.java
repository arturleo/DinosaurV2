import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class AI {
    //    private final int fly = -1;
    //    private final double down = .15;
    private final double range = 240;
    private final int ini = 70;//initial position

    private final int bdth = 60;//breadth

    private double k = -1;//no other trees and can choose freely
    private final double sp = 40.0 / 6;
    private double dis = 0;
    private Random ran = new Random();

    private z mai = new z();

    AI() {
        k = -1;
    }

    public void choose() {
        if (k > 5) {
            int z = ran.nextInt(20);
            if (z == 0) {
                k = -1;
                Runner.flag = true;
            } else k--;
            return;
        } else if (k >= 0 && k <= 10) {
            k = -1;
            Runner.flag = true;
        }

//        int flag = 200, j = 200;
        double nextt = -1, nexttm = -1;

        for (int i = 0; i < 5; i++) {
            if (Runner.tree[1][i].getLayoutX() > Runner.near) {
                if (Runner.tree[1][i].getLayoutX() < nextt) nexttm = 1;
                nextt = Math.min(Runner.tree[1][i].getLayoutX(), nextt);
            }

            if (Runner.tree[2][i].getLayoutX() > 0) {
                if (Runner.tree[2][i].getLayoutX() < nextt) nexttm = 2;
                nextt = Math.max(Runner.tree[2][i].getLayoutX(), nextt);
            }


            if (Runner.tree[3][i].getLayoutX() > 0) {
                if (Runner.tree[3][i].getLayoutX() < nextt) nexttm = 3;
                nextt = Math.max(Runner.tree[3][i].getLayoutX(), nextt);
            }


            if (Runner.berds[i].getLayoutX() < Runner.neart && Runner.berds[i].getLayoutX() > 0) {
                nexttm = Runner.berds[i].getLayoutY();
                nextt = Math.max(Runner.berds[i].getLayoutX(), nextt);
            }
        }
        //dis=nextt-Runner.neart;

        if (((Runner.neartm > 5 && Runner.neart < 7) || Runner.neartm < 5)&&Runner.flag2){
            Runner.flag = false;
            Runner.hero.relocate(Runner.hero.getLayoutX(), 275 );

            double x = Runner.hero.getLayoutX(), y = Runner.hero.getLayoutY();
            Runner.dungeon.getChildren().remove(Runner.hero);
            Runner.heroImage = new Image(Runner.HERO1_IMAGE_LOC, 70, 70, true, true);
            Runner.hero = new ImageView(Runner.heroImage);
            Runner.hero.relocate(x, y);
            Runner.dungeon.getChildren().add(Runner.hero);

            Runner.flag2 = false;
        }
        if (!Runner.flag2 && Runner.neartm > 5
                && Runner.neart < ran.nextInt(200) + 200
                &&Runner.neart >= 7
                &&!Runner.flag) {
            Runner.hero.relocate(Runner.hero.getLayoutX(), 275+40);
            double x = Runner.hero.getLayoutX(), y = Runner.hero.getLayoutY();
            Runner.dungeon.getChildren().remove(Runner.hero);
            Runner.heroImage = new Image(Runner.HERO4_IMAGE_LOC, 70, 70, true, true);
            Runner.hero = new ImageView(Runner.heroImage);
            Runner.hero.relocate(x, y);
            Runner.dungeon.getChildren().add(Runner.hero);

            Runner.flag2 = true;

        } else if (Runner.neartm > 0 && Runner.neartm <= 5 && Runner.neart > 70) {
            double h1 = countd(Runner.tree[(int) Runner.neartm][0].getBoundsInLocal().getHeight());
            double w1 = Runner.tree[(int) Runner.neartm][0].getBoundsInLocal().getWidth();
            if (range - h1 > Runner.neart+w1 - 70) {
                if (nexttm > 5) {
                    double h2 = countd(nexttm);
                    k = (Math.min(nextt -70- range + h2-bdth, Runner.neart -70- h1-bdth)) / sp;
                    k = Math.max(1, k);
                } else if (nexttm > 0) {
                    double w2 = Runner.tree[(int) nexttm][0].getBoundsInLocal().getWidth();
                    if (range - h1 > Runner.neart - 70) {
                        double h2 = countd(Runner.tree[(int) Runner.neartm][0].getBoundsInLocal().getHeight());
                        k = (Math.min(nextt -70-bdth- range - h2, Runner.neart -70- h1-bdth)) / sp;
                        k = Math.max(0, k);
                    } else k = -1;
                } else {
                    k = (Runner.neart -70- h1-bdth) / sp;
                }
            }
        }

        if (k > 5) {
            int z = ran.nextInt(20);
            if (z == 0) {
                k = -1;
                Runner.flag = true;
            } else k--;
        } else if (k >= 0 && k <= 5) {
            k = -1;
            Runner.flag = true;
        } else return;
    }

    private double countd(double h) {//distance between the bottom of the parabola and the safe height
        double u = Math.sqrt(2 * h / mai.G);//time to fall
        double v = u / mai.speed;
        double e = mai.jumpt / mai.speed - v + .01;
        return e;
    }
}
