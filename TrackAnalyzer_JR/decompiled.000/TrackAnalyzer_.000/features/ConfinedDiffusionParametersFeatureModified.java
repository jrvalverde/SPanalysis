package features;

import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import java.util.ArrayList;
import math.ConfinedDiffusionMSDCurveFitModified;
import org.apache.commons.lang3.ArrayUtils;
import traJ.TrajectoryModified;

public class ConfinedDiffusionParametersFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private double timelag;
   private AbstractDiffusionCoefficientEstimatorModified dcEst;
   private boolean useReducedModel;

   public ConfinedDiffusionParametersFeatureModified(TrajectoryModified t, double timelag, boolean useReducedModel) {
      this.t = t;
      this.timelag = timelag;
      this.dcEst = new RegressionDiffusionCoefficientEstimatorModified((TrajectoryModified)null, 1.0D / timelag, 1, 2);
      this.useReducedModel = useReducedModel;
   }

   public ConfinedDiffusionParametersFeatureModified(TrajectoryModified t, double timelag, boolean useReducedModel, AbstractDiffusionCoefficientEstimatorModified dcEst) {
      this.t = t;
      this.timelag = timelag;
      this.dcEst = dcEst;
      this.useReducedModel = useReducedModel;
   }

   public double[] evaluate() {
      MeanSquaredDisplacmentFeatureModified msd = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
      msd.setOverlap(false);
      ArrayList<Double> xDataList = new ArrayList();
      ArrayList<Double> yDataList = new ArrayList();

      double[] yData;
      for(int i = 1; i < this.t.size() / 3; ++i) {
         msd.setTimelag(i);
         yData = msd.evaluate();
         double msdvalue = yData[0];
         int N = (int)yData[2];

         for(int j = 0; j < N; ++j) {
            xDataList.add((double)i * this.timelag);
            yDataList.add(msdvalue);
         }
      }

      double[] xData = ArrayUtils.toPrimitive((Double[])xDataList.toArray(new Double[0]));
      yData = ArrayUtils.toPrimitive((Double[])yDataList.toArray(new Double[0]));
      MaxDistanceBetweenTwoPositionsFeatureModified maxdist = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
      double estdia = maxdist.evaluate()[0];
      double estDC = this.dcEst.getDiffusionCoefficient(this.t, 1.0D / this.timelag)[0];
      double[] initialParams = new double[]{estdia * estdia, 0.0D, 0.0D, estDC};
      ConfinedDiffusionMSDCurveFitModified cmsdfit = new ConfinedDiffusionMSDCurveFitModified();
      cmsdfit.setInitParameters(initialParams);
      cmsdfit.doFit(xData, yData, this.useReducedModel);
      if (this.useReducedModel) {
         this.result = new double[]{cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getGoodness()};
      } else {
         this.result = new double[]{cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getB(), cmsdfit.getC(), cmsdfit.getGoodness()};
      }

      return this.result;
   }

   public String getName() {
      return "Confinement Parameters";
   }

   public String getShortName() {
      return "CONFPARAM";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
