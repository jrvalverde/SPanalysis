package DiffusionCoefficientEstimator;

import features.AbstractMeanSquaredDisplacmentEvaluatorModified;
import features.AbstractTrajectoryFeatureModified;
import features.MeanSquaredDisplacmentFeatureModified;
import java.util.ArrayList;
import math.StraightLineFitModified;
import org.apache.commons.lang3.ArrayUtils;
import traJ.TrajectoryModified;

public class RegressionDiffusionCoefficientEstimatorModified extends AbstractTrajectoryFeatureModified implements AbstractDiffusionCoefficientEstimatorModified {
   private int lagMin;
   private int lagMax;
   private AbstractMeanSquaredDisplacmentEvaluatorModified msdevaluator;
   private TrajectoryModified t;
   private double fps;

   public RegressionDiffusionCoefficientEstimatorModified(int lagMin, int lagMax) {
      this.lagMin = lagMin;
      this.lagMax = lagMax;
      this.msdevaluator = new MeanSquaredDisplacmentFeatureModified((TrajectoryModified)null, lagMin);
   }

   public RegressionDiffusionCoefficientEstimatorModified(TrajectoryModified t, double fps, int lagMin, int lagMax) {
      this.lagMin = lagMin;
      this.lagMax = lagMax;
      this.msdevaluator = new MeanSquaredDisplacmentFeatureModified((TrajectoryModified)null, lagMin);
      this.t = t;
      this.fps = fps;
   }

   public double[] getDiffusionCoefficient(TrajectoryModified t, double fps) {
      if (t.size() == 1) {
         return null;
      } else {
         ArrayList<Double> xDataList = new ArrayList();
         ArrayList<Double> yDataList = new ArrayList();
         double msdhelp = 0.0D;
         if (this.lagMin == this.lagMax) {
            xDataList.add(0.0D);
            yDataList.add(0.0D);
         }

         this.msdevaluator.setTrajectory(t);
         this.msdevaluator.setTimelag(this.lagMin);

         double[] ydata;
         for(int i = this.lagMin; i < this.lagMax + 1; ++i) {
            this.msdevaluator.setTimelag(i);
            ydata = this.msdevaluator.evaluate();
            msdhelp = ydata[0];
            int N = (int)ydata[2];

            for(int j = 0; j < N; ++j) {
               xDataList.add((double)i * 1.0D / fps);
               yDataList.add(msdhelp);
            }
         }

         double[] xdata = ArrayUtils.toPrimitive((Double[])xDataList.toArray(new Double[0]));
         ydata = ArrayUtils.toPrimitive((Double[])yDataList.toArray(new Double[0]));
         StraightLineFitModified fdf = new StraightLineFitModified();
         fdf.doFit(xdata, ydata);
         this.result = new double[]{fdf.getB() / (2.0D * (double)t.getDimension()), fdf.getB() * 2.0D * (double)t.getDimension(), fdf.getA(), fdf.getGoodness()};
         return this.result;
      }
   }

   public void setTimelags(int lagMin, int lagMax) {
      this.lagMin = lagMin;
      this.lagMax = lagMax;
   }

   public void setMeanSquaredDisplacementEvaluator(AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
      this.msdevaluator = msdeval;
   }

   public double[] evaluate() {
      this.result = this.getDiffusionCoefficient(this.t, this.fps);
      return this.result;
   }

   public String getName() {
      return "Diffusion coefficient (Regression)";
   }

   public String getShortName() {
      return "DC-REG";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
   }
}
