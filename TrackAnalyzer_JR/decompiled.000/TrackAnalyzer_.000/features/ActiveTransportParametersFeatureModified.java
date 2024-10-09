package features;

import java.util.ArrayList;
import math.ActiveTransportMSDLineFitModified;
import org.apache.commons.lang3.ArrayUtils;
import traJ.TrajectoryModified;

public class ActiveTransportParametersFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private double timelag;

   public ActiveTransportParametersFeatureModified(TrajectoryModified t, double timelag) {
      this.t = t;
      this.timelag = timelag;
   }

   public double[] evaluate() {
      MeanSquaredDisplacmentFeatureModified msdevaluator = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
      msdevaluator.setTrajectory(this.t);
      ArrayList<Double> xDataList = new ArrayList();
      ArrayList<Double> yDataList = new ArrayList();

      double[] ydata;
      for(int i = 1; i < this.t.size() / 3; ++i) {
         msdevaluator.setTimelag(i);
         ydata = msdevaluator.evaluate();
         double msdhelp = ydata[0];
         int N = (int)ydata[2];

         for(int j = 0; j < N; ++j) {
            xDataList.add((double)i * this.timelag);
            yDataList.add(msdhelp);
         }
      }

      double[] xdata = ArrayUtils.toPrimitive((Double[])xDataList.toArray(new Double[0]));
      ydata = ArrayUtils.toPrimitive((Double[])yDataList.toArray(new Double[0]));
      ActiveTransportMSDLineFitModified afit = new ActiveTransportMSDLineFitModified();
      afit.doFit(xdata, ydata);
      this.result = new double[]{afit.getDiffusionCoefficient(), afit.getVelocity(), afit.getFitGoodness()};
      return this.result;
   }

   public String getName() {
      return "Active transport parameters";
   }

   public String getShortName() {
      return "ACTPARAM";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
   }
}
