import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Random;


/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */

@SuppressWarnings("restriction")
public class Runner extends Application {
    static final double W = 1000, H = 400;
    private Text outputText,gameovertext;
    //move left 6 pixels

    public static final String HERO1_IMAGE_LOC = "file:.\\png\\dinorun0000.png",
            HERO2_IMAGE_LOC = "file:.\\png\\dinorun0001.png",
            HERO3_IMAGE_LOC = "file:.\\png\\dinoJump0000.png",
            HERO4_IMAGE_LOC = "file:.\\png\\dinoduck0000.png",
            HERO5_IMAGE_LOC = "file:.\\png\\dinoduck0001.png",
            TREE1_IMAGE_LOC = "file:.\\png\\cactusBig0000.png",
            TREE2_IMAGE_LOC = "file:.\\png\\cactusSmall0000.png",
            TREE3_IMAGE_LOC = "file:.\\png\\cactusSmallMany0000.png",
            CLOUD_IMAGE_LOC = "file:.\\png\\cloud.png",
            FLYER_IMAGE_LOC = "file:.\\png\\berd.png",
            FLYER2_IMAGE_LOC = "file:.\\png\\berd2.png",
            GG_IMAGE_LOC = "file:.\\png\\gameover.png",
            GROUND_IMAGE_LOC = "file:.\\png\\earth.png",
            START_IMAGE_LOC = "file:.\\png\\start.png",
            AI_IMAGE_LOC = "file:.\\png\\AI.png",
            BOTTON1_IMAGE_LOC = "file:.\\png\\Button1.png",
            BOTTON2_IMAGE_LOC = "file:.\\png\\Button2.png",
            PRESSAI_IMAGE_LOC = "file:.\\png\\PressAI.png",
            ICON_IMAGE_LOC="file:.\\png\\dinoJump0000.png";

    //    public double t = 0;
    public static Image heroImage, ggImage, earthimage, startImage, AIImage, Button1, Button2, PressAI;
    public static Image[] tree1image = new Image[5],
            tree2image = new Image[5],
            tree3image = new Image[5],
            cloudimage = new Image[5],
            berdimage = new Image[5];
    public static Node hero, gg, start2, AIS, button1, button2, pressAI;
    public static Node[] cloud = new Node[5], berds = new Node[5], ground = new Node[2];
    public static Node[][] tree = new Node[5][5];// 1 to num
    static Group dungeon;

    public static boolean flag = false, flag2 = false, AIflag = false, life = true, start = false;
    private static double[] speed = new double[5];
    private static int high = 0, nexttree = -1, nextcloud = -1;
    private static long last = 0, score = 0;

    public static double near = -1, neart = 1000, neartm = -1;
    public static int nearm = -1;


    planttree pt = new planttree();
    findcloud fc = new findcloud();
    AI ai = new AI();

    @Override
    public void start(Stage primaryStage) {

        heroImage = new Image(HERO1_IMAGE_LOC, 60, 70, true, true);
        hero = new ImageView(heroImage);

        ggImage = new Image(GG_IMAGE_LOC, 385, 32, true, true);
        gg = new ImageView(ggImage);

        gg.relocate(-1000, 200);

        earthimage = new Image(GROUND_IMAGE_LOC, 2441, 29, true, true);
        ground[0] = new ImageView(earthimage);
        ground[1] = new ImageView(earthimage);

        ground[0].relocate(0, 320);
        ground[1].relocate(ground[0].getLayoutX() + earthimage.getWidth() - 40, 320);

        startImage = new Image(START_IMAGE_LOC, 200, 40, true, true);
        start2 = new ImageView(startImage);
        start2.relocate(400, 180);

        AIImage = new Image(AI_IMAGE_LOC, 100, 20, true, true);
        AIS = new ImageView(AIImage);
        AIS.relocate(470, 220);

        PressAI = new Image(PRESSAI_IMAGE_LOC, 120, 20, true, true);
        pressAI = new ImageView(PressAI);
        pressAI.relocate(440, 280);

        Button1 = new Image(BOTTON1_IMAGE_LOC, 20, 20, true, true);
        Button2 = new Image(BOTTON2_IMAGE_LOC, 20, 20, true, true);
        button1 = new ImageView(Button1);
        button2 = new ImageView(Button2);
        button1.relocate(450, 220);


        dungeon = new Group(hero);

        dungeon.getChildren().add(gg);
        dungeon.getChildren().add(ground[0]);
        dungeon.getChildren().add(ground[1]);
        dungeon.getChildren().add(start2);
        dungeon.getChildren().add(AIS);
        dungeon.getChildren().add(button1);
        dungeon.getChildren().add(button2);
        dungeon.getChildren().add(pressAI);

        for (int i = 0; i < 5; i++) {
            tree1image[i] = new Image(TREE1_IMAGE_LOC, 35, 70, true, true);
            tree2image[i] = new Image(TREE2_IMAGE_LOC, 30, 60, true, true);
            tree3image[i] = new Image(TREE3_IMAGE_LOC, 100, 50, true, true);
            cloudimage[i] = new Image(CLOUD_IMAGE_LOC, 100, 70, true, true);
            berdimage[i] = new Image(FLYER_IMAGE_LOC, 69, 60, true, true);

            tree[1][i] = new ImageView(tree1image[i]);
            tree[2][i] = new ImageView(tree2image[i]);
            tree[3][i] = new ImageView(tree3image[i]);
            cloud[i] = new ImageView(cloudimage[i]);
            berds[i] = new ImageView(berdimage[i]);

            tree[1][i].relocate(-300, 270);
            tree[2][i].relocate(-300, 280);
            tree[3][i].relocate(-300, 290);
            cloud[i].relocate(-300, 70);
            berds[i].relocate(-300, 100);
            cloud[i].toBack();

            dungeon.getChildren().add(tree[1][i]);
            dungeon.getChildren().add(tree[2][i]);
            dungeon.getChildren().add(tree[3][i]);
            dungeon.getChildren().add(cloud[i]);
            dungeon.getChildren().add(berds[i]);

        }


        Font font2 = Font.font("Verdana", FontWeight.BOLD, 17);
        outputText = new Text(900, 20, "000000");
        outputText.setFont(font2);
        dungeon.getChildren().add(outputText);

        Font font3 = Font.font("Verdana", FontPosture.ITALIC, 12);
        gameovertext = new Text(400, 250, "Press ENTER to restart,ESC to return.");
        gameovertext.setFont(font3);
        dungeon.getChildren().add(gameovertext);

        Scene scene = new Scene(dungeon, W, H, Color.WHITE);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    start = true;
                    last = 0;
                    life = true;
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    start = false;
                    last = 0;
                    life = true;
                }
                if (event.getCode() == KeyCode.A) {//AI mode
//                    flag = false;
//                    flag2 = false;
                    if(AIflag)AIflag=false;
                    else AIflag = true;
                }
                if (event.getCode() == KeyCode.UP) {//jump and only jump multiple times in up process
                    flag = true;
                    flag2 = false;
                    AIflag = false;
                    if (high <= 18)
                        high = 0;
                }
                if (event.getCode() == KeyCode.DOWN) {
                    flag = false;
                    //hero.relocate(hero.getLayoutX(),275);
                    AIflag = false;
                    if (!flag2) {
                        high=0;
                        flag2 = true;
                        hero.relocate(hero.getLayoutX(), 315);//hero.getLayoutY() + 40);
                        double x = hero.getLayoutX(), y = hero.getLayoutY();
                        dungeon.getChildren().remove(hero);
                        heroImage = new Image(HERO4_IMAGE_LOC, 70, 70, true, true);
                        hero = new ImageView(heroImage);
                        hero.relocate(x, y);
                        dungeon.getChildren().add(hero);
                    }
                }
                //                if (event.getCode() == KeyCode.SHIFT) {
//                    if (high <= 19) high++;
//                    else high = 0;
//                }

            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN) {
                    if (flag2) {
                        hero.relocate(hero.getLayoutX(), hero.getLayoutY() - 40);
                        double x = hero.getLayoutX(), y = hero.getLayoutY();
                        dungeon.getChildren().remove(hero);
                        heroImage = new Image(HERO1_IMAGE_LOC, 70, 70, true, true);
                        hero = new ImageView(heroImage);
                        hero.relocate(x, y);
                        dungeon.getChildren().add(hero);
                    }
                    flag2 = false;
                }

            }
        });

//        Line line = new Line();
//        line.setStartX(0.0f);
//        line.setStartY(330.0f);
//        line.setEndX(1000.0f);
//        line.setEndY(330.0f);
//        dungeon.getChildren().add(line);
//

        Random ran = new Random();

        AnimationTimer timer = new AnimationTimer() {
            int Dhigh[] = {
                    300, 277, 256, 236, 217, 200,
                    184, 169, 156, 144, 134, 125,
                    117, 111, 106, 103, 100, 99,
                    98,
                    99, 100, 103, 106, 111, 117,
                    125, 134, 144, 156, 169, 184,
                    200, 217, 236, 256, 277, 300
            };

            @Override
            public void handle(long now) {
                //if (flag==true) moveHeroTo(100,200);
                //System.out.println(now);
                if (last == 0) {
                    hero.relocate(-300, -300);

                    for (int i = 0; i < 5; i++) {
                        tree[1][i].relocate(-300, 270);
                        tree[2][i].relocate(-300, 280);
                        tree[3][i].relocate(-300, 290);
                        cloud[i].relocate(-300, 70);
                        berds[i].relocate(-300, 100);
                    }

                    ground[0].relocate(-3000,200);
                    ground[1].relocate(-3000,200);

                    gg.relocate(-1000,200);

                    start2.relocate(400, 180);
                    AIS.relocate(470, 220);
                    pressAI.relocate(440, 280);
                    outputText.relocate(-900,30);
                    gameovertext.relocate(-900,90);

                    button1.relocate(-400, 10);
                    button2.relocate(-400, 10);
                    if (AIflag) {
                        button2.relocate(450, 220);
                    } else button1.relocate(450, 220);
                }

                if (start) {
                    if (last == 0) {
                        moveHeroTo(100, 300);

                        start2.relocate(-400, -180);
                        button1.relocate(-400, 10);
                        button2.relocate(-400, 10);
                        if (!AIflag)
                            button1.relocate(10, 10);
                        else button2.relocate(10, 10);
                        AIS.relocate(30, 10);
                        pressAI.relocate(-400, -400);

                        outputText.relocate(900,20);

                        int l = ran.nextInt(3);
                        int r = 0;
                        for (int i = 0; i < l; i++) {
                            r = ran.nextInt(3) + 1;
                            tree[r][i].relocate(ran.nextInt(600 / l) + 400 + 600.0 / l * i, tree[r][i].getLayoutY());
                        }
                        nearm = r;

                        l = ran.nextInt(3);
                        for (int i = 0; i < l; i++) {
                            cloud[i].relocate(ran.nextInt(1000 / l) + 1000.0 / l * i, ran.nextInt(250) + 10);
                            speed[i] = (ran.nextInt(60) + 0.1) / 60 + 2;
                        }

                        ground[0].relocate(0, 320);
                        ground[1].relocate(ground[0].getLayoutX() + earthimage.getWidth() - 40, 320);

                    }
                    if (life) {
                        last++;
                        if (last % 3 == 0) {
                            score = last;
                            DecimalFormat df = new DecimalFormat("000000");
                            outputText.setText(df.format(score));
                        }
                        button1.relocate(-400, 10);
                        button2.relocate(-400, 10);
                        if (AIflag) {
                            ai.choose();
                            button2.relocate(10, 10);
                        } else button1.relocate(10, 10);


                        //Animate
                        if (flag && high == 0) {
//                          changeImg(hero,HERO3_IMAGE_LOC);
                            double x = hero.getLayoutX(), y = hero.getLayoutY();
                            dungeon.getChildren().remove(hero);
                            heroImage = new Image(HERO3_IMAGE_LOC, 60, 62.5, true, true);
                            hero = new ImageView(heroImage);
                            hero.relocate(x, y);
                            dungeon.getChildren().add(hero);
                        }

                        if (last % 16 == 0 && last % 32 != 0 && !flag) {
//                    changeImg(hero,HERO2_IMAGE_LOC);
                            double x = hero.getLayoutX(), y = hero.getLayoutY();
                            dungeon.getChildren().remove(hero);
                            heroImage = new Image(HERO2_IMAGE_LOC, 60, 70, true, true);
                            hero = new ImageView(heroImage);
                            hero.relocate(x, y);
                            dungeon.getChildren().add(hero);
                        }

                        if (last % 32 == 0 && !flag) {
//                    changeImg(hero,HERO1_IMAGE_LOC);
                            double x = hero.getLayoutX(), y = hero.getLayoutY();
                            dungeon.getChildren().remove(hero);
                            heroImage = new Image(HERO1_IMAGE_LOC, 60, 70, true, true);
                            hero = new ImageView(heroImage);
                            hero.relocate(x, y);
                            dungeon.getChildren().add(hero);
                        }
                        if (flag2) {

                            if (last % 16 == 0 && last % 32 != 0) {
//                    changeImg(hero,HERO2_IMAGE_LOC);
                                double x = hero.getLayoutX(), y = hero.getLayoutY();
                                dungeon.getChildren().remove(hero);
                                heroImage = new Image(HERO4_IMAGE_LOC, 70, 35, true, true);
                                hero = new ImageView(heroImage);
                                hero.relocate(x, y);
                                dungeon.getChildren().add(hero);
                            }

                            if (last % 32 == 0) {
//                    changeImg(hero,HERO1_IMAGE_LOC);
                                double x = hero.getLayoutX(), y = hero.getLayoutY();
                                dungeon.getChildren().remove(hero);
                                heroImage = new Image(HERO5_IMAGE_LOC, 70, 35, true, true);
                                hero = new ImageView(heroImage);
                                hero.relocate(x, y);
                                dungeon.getChildren().add(hero);
                            }
                        }
                        if (last % 14 == 0 && last % 28 != 0) {
                            //                    changeImg(berds[i],FlYER2_IMAGE_LOC);
                            for (int i = 0; i < 5; i++) {
                                double x = berds[i].getLayoutX(), y = berds[i].getLayoutY();
                                dungeon.getChildren().remove(berds[i]);
                                berdimage[i] = new Image(FLYER2_IMAGE_LOC, 69, 60, true, true);
                                berds[i] = new ImageView(berdimage[i]);
                                berds[i].relocate(x, y);
                                dungeon.getChildren().add(berds[i]);
                            }
                        }
                        if (last % 28 == 0) {
                            //                    changeImg(berds[i],FlYER_IMAGE_LOC);
                            for (int i = 0; i < 5; i++) {
                                double x = berds[i].getLayoutX(), y = berds[i].getLayoutY();
                                dungeon.getChildren().remove(berds[i]);
                                berdimage[i] = new Image(FLYER_IMAGE_LOC, 69, 60, true, true);
                                berds[i] = new ImageView(berdimage[i]);
                                berds[i].relocate(x, y);
                                dungeon.getChildren().add(berds[i]);
                            }
                        }
                        //Animate


                        if (flag) {
                            hero.relocate(70, hero.getLayoutY() + (high >= 37 && 275 - hero.getLayoutY() > 4 ?
                                    25 : Dhigh[high] - (high == 0 ? 300 : Dhigh[high - 1])));
                            high++;
                            //moveHeroTo(100, Dhigh[high++]-300+hero.getBoundsInLocal().getHeight() / 2+k );
                            if (high >= 37 && 275 - hero.getLayoutY() < 8) {
                                hero.relocate(70, 275);
                                high = 0;
                                flag = false;
                                double x = hero.getLayoutX(), y = hero.getLayoutY();
                                dungeon.getChildren().remove(hero);
                                heroImage = new Image(HERO1_IMAGE_LOC, 70, 70, true, true);
                                hero = new ImageView(heroImage);
                                hero.relocate(x, y);
                                dungeon.getChildren().add(hero);
                                //System.out.println(hero.getLayoutY());
                            }
                            //System.out.println(high);
                        }
                        if (!flag && !flag2) moveHeroTo(100, 310);

//                if (flag2 = true) {
//
//                }

                        if ((nexttree = pt.newh()) != 0) {
                            int tr = pt.istree();
                            if (tr == 1) {
                                for (int i = 0; i < 5; i++) {
                                    if (tree[nexttree][i].getLayoutX() + tree[nexttree][i].getBoundsInLocal().getWidth() <= 0) {
                                        tree[nexttree][i].relocate(1000, tree[nexttree][i].getLayoutY());
                                        break;
                                    }
                                }
                            } else {
                                for (int i = 0; i < 5; i++) {
                                    if (berds[i].getLayoutX() + berds[i].getBoundsInLocal().getWidth() <= 0) {
                                        berds[i].relocate(1000, tr);
                                        break;
                                    }
                                }
                            }
                        }
                        //System.out.println(nexttree);
                        if ((nextcloud = fc.newc()) != 0) {
                            for (int i = 0; i < 5; i++) {
                                if (cloud[i].getLayoutX() + cloud[i].getBoundsInLocal().getWidth() <= 0) {
                                    cloud[i].relocate(1000, nextcloud);
                                    speed[i] = (ran.nextInt(60) + 0.1) / 60 + 2;
                                    break;
                                }
                            }
                        }

                        near = 0;//nearc=-1;
                        neart = 1000;
                        for (int i = 0; i < 5; i++) {
                            tree[1][i].relocate(tree[1][i].getLayoutX() - 6.6666, tree[1][i].getLayoutY());
                            near = Math.max(tree[1][i].getLayoutX(), near);
                            if (tree[1][i].getLayoutX() > 0) {
                                if (tree[1][i].getLayoutX() < neart) neartm = 1;
                                neart = Math.min(tree[1][i].getLayoutX(), neart);
                            }

                            tree[2][i].relocate(tree[2][i].getLayoutX() - 6.6666, tree[2][i].getLayoutY());
                            near = Math.max(tree[2][i].getLayoutX(), near);
                            if (tree[2][i].getLayoutX() > 0) {
                                if (tree[2][i].getLayoutX() < neart) neartm = 2;
                                neart = Math.min(tree[2][i].getLayoutX(), neart);
                            }

                            tree[3][i].relocate(tree[3][i].getLayoutX() - 6.6666, tree[3][i].getLayoutY());
                            near = Math.max(tree[3][i].getLayoutX(), near);
                            if (tree[3][i].getLayoutX() > 0) {
                                if (tree[3][i].getLayoutX() < neart) neartm = 3;
                                neart = Math.min(tree[3][i].getLayoutX(), neart);
                            }

                            berds[i].relocate(berds[i].getLayoutX() - 6.6666, berds[i].getLayoutY());
                            near = Math.max(berds[i].getLayoutX(), near);
                            if (berds[i].getLayoutX() < neart && berds[i].getLayoutX() > 0) {
                                neartm = berds[i].getLayoutY();
                                neart = Math.min(berds[i].getLayoutX(), neart);
                            }


                            cloud[i].relocate(cloud[i].getLayoutX() - speed[i], cloud[i].getLayoutY());
                            //nearc=Math.max(cloud[i].getLayoutX(),near);
                        }

                        ground[0].relocate(ground[0].getLayoutX() - 6.6666, 320);
                        ground[1].relocate(ground[1].getLayoutX() - 6.6666, 320);
                        if (ground[0].getLayoutX() + earthimage.getWidth() <= W)
                            ground[1].relocate(ground[0].getLayoutX() + earthimage.getWidth() - 40, 320);
                        if (ground[1].getLayoutX() + earthimage.getWidth() <= W)
                            ground[0].relocate(ground[1].getLayoutX() + earthimage.getWidth() - 40, 320);
                        collision();
                    } else {
                        gg.relocate(500 - gg.getBoundsInLocal().getWidth() / 2, 200 - gg.getBoundsInLocal().getHeight() / 2);
                        gameovertext.relocate(380,230);
                    }
                }
            }
        };
        timer.start();
        primaryStage.setTitle("Little Dinosaur");
        primaryStage.getIcons().add(new Image(ICON_IMAGE_LOC));
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void changeImg(Node a, String b) {
        Image c;
        double x = a.getLayoutX(), y = a.getLayoutY(), w = a.getBoundsInLocal().getWidth(), h = a.getBoundsInLocal().getHeight();
        dungeon.getChildren().remove(a);
        c = new Image(b, w, h, true, true);
        a = new ImageView(c);
        a.relocate(x, y);
        dungeon.getChildren().add(a);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= W &&
                y - cy >= 0 &&
                y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    //    private void collision(int i) {
//        int xl = tree[i][I].getLayoutX();
//        int xr;
//
//        if (i == 1) {
//            xr = xl + 35;
//            if ((((xl <= 170) && (xl >= 100)) || ((xr >= 100) && (xr <= 170))) && (Dhigh[high] >= 160)) life = false;
//        }
//        if (i == 2) {
//            xr = xl + 30;
//            if ((((xl <= 170) && (xl >= 100)) || ((xr >= 100) && (xr <= 170))) && (Dhigh[high] >= 170)) life = false;
//        }
//        if (i == 2) {
//            xr = xl + 100;
//            if ((((xl <= 170) && (xl >= 100)) || ((xr >= 100) && (xr <= 170))) && (Dhigh[high] >= 180)) life = false;
//        }
//    }
    private void collision() {
        double yh = hero.getLayoutY() + hero.getBoundsInLocal().getHeight(),
                yr = hero.getLayoutX() + hero.getBoundsInLocal().getWidth(),
                yl = hero.getLayoutX();

        for (int z = 1; z <= 3; z++) {
            for (int j = 0; j < 5; j++) {
                double xl = tree[z][j].getLayoutX();
                double xr;
                double yt = tree[z][j].getLayoutY();

                xr = xl + tree[z][j].getBoundsInLocal().getWidth();
                if ((((xl <= yr) && (xl >= yl)) || ((xr >= yl) && (xr <= yr))) && (yh >= yt)) {
                    life = false;
                    return;
                }

            }
        }

        for (int v = 0; v < 5; v++) {
            double xb = berds[v].getLayoutX();
            double yb = berds[v].getLayoutY();
            double wb = berds[v].getBoundsInLocal().getWidth();
            double hb = berds[v].getBoundsInLocal().getHeight();
            double xbr = xb + wb;
            double ybl = yb + hb;
            double yhd = yh - hero.getBoundsInLocal().getHeight();

            if ((((xb <= yr) && (xbr >= yl)) ||
                    ((xb >= yl) && (xbr <= yr)))
                    && (((yh >= yb) && (yh <= ybl))
                    || ((yhd >= yb) && (yhd <= ybl)))) {
                life = false;
                return;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}


