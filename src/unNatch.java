import javax.swing.*;
import java.util.Scanner;

public class unNatch {
    public static double Lrate = 0.5;
    public static double mubiao;
    public static double[] input = new double[1];
    public static double[] hidden = new double[80];
    public static double[] hidden2 = new double[80];
    public static double[] output = new double[3];
    public static double[][] Win = new double[1][80];
    public static double[][] wout = new double[80][3];
    public static double[][] hiddenxx = new double[80][80];
    public static boolean sb = false;
    public static boolean yuce = false;

    public static void main(String[] args) {
        JFrame main = new JFrame();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize();
        start();

    }

    public static void start() {
        Scanner IN = new Scanner(System.in);

        System.out.println("输入input");
        input[0] = (Double.parseDouble(IN.next()));

        System.out.println(input[0]);
        System.out.println("目标");
        mubiao = GetSigma(Double.parseDouble(IN.next()));
        System.out.println(mubiao);
        for (int i = 0; i < 80; i++) {
            Win[0][i] = getDoubleRandom();
        }

        for (int i = 0; i < 80; i++) {
            for (int s = 0; s < 3; s++) {
                wout[i][s] = getDoubleRandom();
            }
        }
        System.out.println("权重初始化完成");
        System.out.println("unNatch开始第一次训练");
        train();
    }


    public static void train() {
        for (int i = 0; i < 80; i++) {
            hidden[i] = GetSigma(input[0] * Win[0][i]);
        }
        for (int sb = 0; sb < 3; sb++) {
            output[sb] = 0;
            for (int i = 0; i < 80; i++) {
                output[sb] = GetSigma(hidden[i] * wout[i][sb]);
            }
        }
        System.out.println("训练结束");
        System.out.println(output[1] + " " + output[2] + " " + output[0]);
        double E1;
        double E2;
        double E3;

        if (!yuce) {
            E1 = (mubiao - output[0]);
            E2 = (mubiao - output[1]);
            E3 = (mubiao - output[2]);
            diedai(E1, E2, E3);

        } else {
            double yuce1 = GetInverseSigma(output[0]);
            double yuce2 = GetInverseSigma(output[1]);
            double yuce3 = GetInverseSigma(output[2]);
            System.out.println(yuce1 + " " +yuce2 + " " +yuce3);

        }
    }


    public static void diedai(double E1, double E2, double E3) {
        Scanner yesd = new Scanner(System.in);
        System.out.println("unNatch开始更新权重");
        for (int i = 0; i < 80; i++) {
            wout[i][0] += Lrate * E1* (wout[i][0] / qiuhe(80, 0, wout));
            wout[i][1] += Lrate * E1*(wout[i][1] / qiuhe(80, 1, wout));
            wout[i][2] += Lrate * E1*(wout[i][2] / qiuhe(80, 2, wout));
        }
        for (int i = 0; i < 80; i++) {
            Win[0][i] = qiuhe2(3,i,wout);
        }

        if (!sb) {
            System.out.println("权重更新完成，要再次训练吗 yes or no");

            if (yesd.next().equals("y")) {
                for (int i = 0; i < 100000; i++) {
                    sb = true;
                    train();
                }
                sb = false;
                train();
            } else {
                System.out.println("更换训练实例？yes or no");
                if (yesd.next().equals("y")) {
                    System.out.println("输入input");
                    input[0] = GetSigma( Double.parseDouble(yesd.next()));
                    System.out.println("目标");
                    mubiao = GetSigma(Double.parseDouble(yesd.next()));
                    train();
                } else {
                    System.out.println("预测?");
                    if (yesd.next().equals("y")) {
                        System.out.println("输入input");
                        input[0] = GetSigma(Double.parseDouble(yesd.next()));
                        yuce = true;
                        train();
                    } else {
                       System.out.println("Lrate");
                       Lrate = yesd.nextInt();
                    }
                }
            }
        }
    }


    public static double getDoubleRandom() {

        double done = 0;
        while (done == 0) {
            done = Math.random();
        }

        return done;
    }

    public static double GetSigma(double input) {
        return 1f / (1f + Math.pow(Math.E, -1 * input));
    }

    public static double GetInverseSigma(double sigma) {
        return -Math.log(1f / sigma - 1f);
    }
    public static double qiuhe(int d, int x, double[][] sd) {
        int done = 0;
        for (int i = 0; i < d; i++) {
            done += sd[i][x];
        }
     return done;

    }
    public static double qiuhe2(int d, int x, double[][] sd) {
        int done = 0;
        for (int i = 0; i < d; i++) {
            done += sd[x][i];
        }
        return done;

    }


}