// 
// Decompiled by Procyon v0.5.36
// 

package features;

import math.ActiveTransportMSDLineFitModified;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import traJ.TrajectoryModified;

public class ActiveTransportParametersFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private double timelag;
    
    public ActiveTransportParametersFeatureModified(final TrajectoryModified t, final double timelag) {
        this.t = t;
        this.timelag = timelag;
    }
    
    @Override
    public double[] evaluate() {
        final MeanSquaredDisplacmentFeatureModified msdevaluator = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
        msdevaluator.setTrajectory(this.t);
        final ArrayList<Double> xDataList = new ArrayList<Double>();
        final ArrayList<Double> yDataList = new ArrayList<Double>();
        for (int i = 1; i < this.t.size() / 3; ++i) {
            msdevaluator.setTimelag(i);
            final double[] res = msdevaluator.evaluate();
            final double msdhelp = res[0];
            for (int N = (int)res[2], j = 0; j < N; ++j) {
                xDataList.add(i * this.timelag);
                yDataList.add(msdhelp);
            }
        }
        final double[] xdata = ArrayUtils.toPrimitive((Double[])xDataList.<Double>toArray(new Double[0]));
        final double[] ydata = ArrayUtils.toPrimitive((Double[])yDataList.<Double>toArray(new Double[0]));
        final ActiveTransportMSDLineFitModified afit = new ActiveTransportMSDLineFitModified();
        afit.doFit(xdata, ydata);
        return this.result = new double[] { afit.getDiffusionCoefficient(), afit.getVelocity(), afit.getFitGoodness() };
    }
    
    @Override
    public String getName() {
        return "Active transport parameters";
    }
    
    @Override
    public String getShortName() {
        return "ACTPARAM";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
    }
}
