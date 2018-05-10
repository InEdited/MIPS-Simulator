package Mips.Processor;

import javafx.beans.property.StringProperty;

public class PC {
    private static int pc;

    public static int getPc() {
        //System.out.println("Getting instruction at : " + pc);
        //pc = Integer.toBinaryString(Integer.parseInt(pc,2)+4);
        //System.out.println("Getting instruction at : " + String.valueOf(Integer.parseInt(pc,2)-4));
        return pc;
    }

    public int pcProperty() {
        return pc;
    }

    public static void setPc(int dis) {
        pc = dis;
    }
}
