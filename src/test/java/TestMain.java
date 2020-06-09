import java.util.Date;
import java.util.ListIterator;
import java.util.Vector;

import mchhui.customnpcsfix.controllers.data.Waypoint;
import mchhui.customnpcsfix.util.NBTJsonUtil;
import net.minecraft.nbt.NBTTagCompound;

public class TestMain {
    static long time = new Date().getTime();
    public static void main(String[] args) {
        for (int i = 0; i < 10000;) {
            i += 1;
        }
        System.out.println((new Date().getTime() - time));
        time = new Date().getTime();
        new Thread(()->{
            for (int i = 0; i < 1000000000;) {
                String str=""+"";
                i += 1;
            }
        }).start(); ;
        new Thread(()->{
            for (int i = 0; i < 10000;) {
                i += 1;
            }
            System.out.println((new Date().getTime() - time));
        }).start(); ;
    }

    static void a(int[] arr) {
        arr[0] = 1;
    }

    public static String merge(String... strs) {
        String result = "";
        for (String str : strs) {
            result += str;
        }
        return result;
    }

    public void test5() {
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < 20; i++) {
            vector.add(Integer.valueOf(i));
        }

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ListIterator<Integer> iterator = vector.listIterator();
                while (iterator.hasNext()) {
                    System.out.println("thread1 " + iterator.next().intValue());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ListIterator<Integer> iterator = vector.listIterator();
                synchronized (vector) {
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        System.out.println("thread2 " + integer.intValue());
                        if (integer.intValue() == 5) {
                            iterator.remove();
                        }
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
