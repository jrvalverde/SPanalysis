// 
// Decompiled by Procyon v0.5.36
// 

package math;

import ij.measure.CurveFitter;

public class ConfinedDiffusionMSDCurveFitModified
{
    private double a;
    private double b;
    private double c;
    private double D;
    private double goodness;
    private double initA;
    private double initB;
    private double initC;
    private double initD;
    
    public ConfinedDiffusionMSDCurveFitModified() {
        this.initA = Double.NaN;
        this.initB = Double.NaN;
        this.initC = Double.NaN;
        this.initD = Double.NaN;
    }
    
    public void doFit(final double[] xdata, final double[] ydata, final boolean reduced) {
        final CurveFitter fitter = new CurveFitter(xdata, ydata);
        if (!reduced) {
            final double ia = Double.isNaN(this.initA) ? 0.0 : this.initA;
            final double ib = Double.isNaN(this.initB) ? 0.0 : this.initB;
            final double ic = Double.isNaN(this.initC) ? 0.0 : this.initC;
            final double id = Double.isNaN(this.initD) ? 0.0 : this.initD;
            final double[] initialParams = { ia, ib, ic, id };
            fitter.setInitialParameters(initialParams);
            fitter.doCustomFit("y=sqrt(a*a)*(1-sqrt(b*b)*exp(-4*sqrt(c*c)*sqrt(d*d)*x/sqrt(a*a)))", initialParams, false);
            final double[] params = fitter.getParams();
            this.a = Math.abs(params[0]);
            this.b = Math.abs(params[1]);
            this.c = Math.abs(params[2]);
            this.D = Math.abs(params[3]);
            this.goodness = fitter.getFitGoodness();
        }
        else {
            final double ia = Double.isNaN(this.initA) ? 0.0 : this.initA;
            final double id2 = Double.isNaN(this.initD) ? 0.0 : this.initD;
            final double[] initialParams2 = { ia, id2 };
            fitter.setInitialParameters(initialParams2);
            fitter.doCustomFit("y=sqrt(a*a)*(1-exp(-4*sqrt(b*b)*x/sqrt(a*a)))", initialParams2, false);
            final double[] params2 = fitter.getParams();
            this.a = Math.abs(params2[0]);
            this.D = Math.abs(params2[1]);
            this.goodness = fitter.getFitGoodness();
        }
    }
    
    public void setInitParameters(final double[] p) {
        this.initA = p[0];
        this.initB = p[1];
        this.initC = p[2];
        this.initD = p[3];
    }
    
    public double getA() {
        return this.a;
    }
    
    public double getB() {
        return this.b;
    }
    
    public double getC() {
        return this.c;
    }
    
    public double getD() {
        return this.D;
    }
    
    public double getGoodness() {
        return this.goodness;
    }
}
