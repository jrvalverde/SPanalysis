package features;

import java.util.ArrayList;
import math.PowerLawCurveFitModified;
import org.apache.commons.lang3.ArrayUtils;
import traJ.TrajectoryModified;

public class PowerLawFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private int minlag;
   private int maxlag;
   private AbstractMeanSquaredDisplacmentEvaluatorModified msdeval;
   private int evaluateIndex = 0;
   private boolean useInitialGuess;
   private double initalDiffusionCoefficient;
   private double initalAlpha;
   private double fps;
   private double timelag;

   public PowerLawFeatureModified(TrajectoryModified t, double fps, int minlag, int maxlag) {
      this.t = t;
      this.minlag = minlag;
      this.maxlag = maxlag;
      this.fps = fps;
      this.timelag = 1.0D / fps;
      this.msdeval = new MeanSquaredDisplacmentFeatureModified((TrajectoryModified)null, 0);
      ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
      this.evaluateIndex = 0;
      this.useInitialGuess = false;
   }

   public PowerLawFeatureModified(TrajectoryModified t, double fps, int minlag, int maxlag, double initalAlpha, double initialDiffusionCoefficient) {
      this.t = t;
      this.minlag = minlag;
      this.maxlag = maxlag;
      this.fps = fps;
      this.timelag = 1.0D / fps;
      this.msdeval = new MeanSquaredDisplacmentFeatureModified((TrajectoryModified)null, 0);
      ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
      this.evaluateIndex = 0;
      this.useInitialGuess = true;
      this.initalAlpha = initalAlpha;
      this.initalDiffusionCoefficient = initialDiffusionCoefficient;
   }

   public double[] evaluate() {
      ArrayList<Double> xDataList = new ArrayList();
      ArrayList<Double> yDataList = new ArrayList();
      this.msdeval.setTrajectory(this.t);
      double[][] data = new double[this.maxlag - this.minlag + 1][3];

      int i;
      double[] yData;
      for(i = this.minlag; i <= this.maxlag; ++i) {
         this.msdeval.setTimelag(i);
         data[i - this.minlag][0] = (double)i * this.timelag;
         yData = this.msdeval.evaluate();
         data[i - this.minlag][1] = yData[this.evaluateIndex];
         data[i - this.minlag][2] = (double)((int)yData[2]);
      }

      for(i = 0; i < this.maxlag - this.minlag + 1; ++i) {
         double x = data[i][0];
         double y = data[i][1];
         int np = (int)data[i][2];

         for(int j = 0; j < np; ++j) {
            xDataList.add(x);
            yDataList.add(y);
         }
      }

      double[] xData = ArrayUtils.toPrimitive((Double[])xDataList.toArray(new Double[0]));
      yData = ArrayUtils.toPrimitive((Double[])yDataList.toArray(new Double[0]));
      PowerLawCurveFitModified pwFit = new PowerLawCurveFitModified();
      if (this.useInitialGuess) {
         pwFit.doFit(xData, yData, this.initalAlpha, this.initalDiffusionCoefficient);
      } else {
         pwFit.doFit(xData, yData);
      }

      this.result = new double[]{pwFit.getAlpha(), pwFit.getDiffusionCoefficient(), pwFit.getGoodness()};
      return this.result;
   }

   public void setEvaluateIndex(int evaluateIndex) {
      this.evaluateIndex = evaluateIndex;
   }

   public void setMeanSquaredDisplacmentEvaluator(AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
      this.msdeval = msdeval;
   }

   public String getName() {
      return "Power-Law-Feature";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }

   public String getShortName() {
      return "POWER";
   }
}
