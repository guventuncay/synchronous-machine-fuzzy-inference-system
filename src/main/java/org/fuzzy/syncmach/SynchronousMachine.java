package org.fuzzy.syncmach;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class SynchronousMachine {
    private static long gId = 0L;
    private final long id;

    private final FIS fis;
    private FunctionBlock fb;

    private double actualI_f;

    public SynchronousMachine(String filename, double I_y, double PF, double e_PF, double d_if, double actualI_f) {
        id = ++gId;
        // Load fuzzy controller from file
        fis = FIS.load(filename, true);
        initFunctionBlock(I_y, PF, e_PF, d_if);
        this.actualI_f = actualI_f;
    }

    private void initFunctionBlock(double I_y, double PF, double e_PF, double d_if) {
        // Get default function block
        fb = fis.getFunctionBlock(null);

        // Set inputs
        setI_y(I_y);
        setPF(PF);
        setE_PF(e_PF);
        setD_if(d_if);
    }

    public void evaluate() {
        fb.evaluate();
//        double i_f = getI_f();
//        System.out.println("I_f: " + i_f);

        // Show variable's chart
//        showCharts();
    }

    public void showCharts() {
        JFuzzyChart.get().chart(fb);
    }

    public double getSuccess() {
        return 100 - (100 * (Math.abs(getActualI_f() - getI_f()) / getActualI_f()));
    }

    public Long getId() {
        return id;
    }

    public double getActualI_f() {
        return actualI_f;
    }

    public double getI_f() {
        return fb.getVariable("I_f").getValue();
    }

    public double getI_y() {
        return fb.getVariable("I_y").getValue();
    }

    public double getPF() {
        return fb.getVariable("PF").getValue();
    }

    public double getE_PF() {
        return fb.getVariable("e_PF").getValue();
    }

    public double getD_if() {
        return fb.getVariable("d_if").getValue();
    }

    public void setI_y(double I_y) {
        fb.setVariable("I_y", I_y);
    }

    public void setPF(double PF) {
        fb.setVariable("PF", PF);
    }

    public void setE_PF(double e_PF) {
        fb.setVariable("e_PF", e_PF);
    }

    public void setD_if(double d_if) {
        fb.setVariable("d_if", d_if);
    }

    public void setActualI_f(double actualI_f) {
        this.actualI_f = actualI_f;
    }

    @Override
    public String toString() {
        return "SynchronousMachine{" +
                "id=" + getId() +
                ", I_y=" + getI_y() +
                ", PF=" + getPF() +
                ", e_PF=" + getE_PF() +
                ", d_if=" + getD_if() +
                ", actual I_f=" + getActualI_f() +
                ", calculated I_f=" + getI_f() +
                ", success=%" + getSuccess() +
                '}' + "\n";
    }

}
