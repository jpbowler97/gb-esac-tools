package gb.esac.tools;

import java.text.DecimalFormat;
import java.util.Vector;

import hep.aida.ref.histogram.FixedAxis;
import hep.aida.ref.histogram.Histogram1D;
import hep.aida.ref.histogram.Histogram2D;
import hep.aida.ref.histogram.VariableAxis;
import nom.tam.util.ArrayFuncs;

/**
 *
 *  @version    June 2015 (last modified)
 *  @author 	Guillaume Belanger (ESAC, Spain)
 *
 **/

public final class Converter {

    public static double[] days2sec(double[] times) {
	double[] t = new double[times.length];
	for ( int i=0; i < times.length; i++ ) {
	    t[i] = times[i]*86400.;
	}
	return t;
    }

    public static double[] sec2ms(double[] times) {
	double[] t = new double[times.length];
	for ( int i=0; i < times.length; i++ ) {
	    t[i] = times[i]*1000;
	}
	return t;
    }

    public static double[] lin2logSpace(double[] data) {
	double[] logData = new double[data.length];
	for ( int i=0; i < data.length; i++ ) {
	    logData[i] = Math.log10(data[i]);
	}
	return logData;
    }

    public static double[] vector2doubleArray(Vector dataVector) {
	double[] data = new double[dataVector.size()];
	for ( int i=0; i < dataVector.size(); i++ ) {
	    data[i] = ((Double) dataVector.elementAt(i)).doubleValue();
	}
	return data;
    }
    
    public static double[][] float2double(float[][] floatData) {
	////  Determine the size of the floatData double array
	ArrayFuncs arrayFuncs = new ArrayFuncs();
	int[] dataDims = arrayFuncs.getDimensions(floatData);
	//  dataDims[0] = number of col (xaxis length)
	//  dataDims[1] = number of rows (yaxis length)
	////  convert floats to doubles
	double[][] doubleData = new double[dataDims[0]][dataDims[1]];
	for ( int row=0; row < dataDims[1]; row++ ) {
	    for ( int col=0; col < dataDims[0]; col++ ) {
		doubleData[col][row] = (new Double(floatData[col][row])).doubleValue();
	    }
	}
	return doubleData;	
    }
    
    public static double[] float2double(float[] floatData) {
	double[] doubleData = new double[floatData.length];
	for ( int i=0; i < floatData.length; i++ ) {
	    doubleData[i] = (new Double(floatData[i])).doubleValue();
	}
	return doubleData;
    }

    public static double[] long2double(long[] longData) {
	double[] doubleData = new double[longData.length];
	for ( int i=0; i < longData.length; i++ ) {
	    doubleData[i] = (new Long(longData[i])).doubleValue();
	}
	return doubleData;
    }

    public static double[] short2double(short[] shortData) {
	double[] doubleData = new double[shortData.length];
	for ( int i=0; i < shortData.length; i++ ) {
	    doubleData[i] = (new Short(shortData[i])).doubleValue();
	}
	return doubleData;
    }

    public static int[] short2int(short[] shortData) {
	int[] intData = new int[shortData.length];
	for ( int i=0; i < shortData.length; i++ ) {
	    intData[i] = (new Short(shortData[i])).intValue();
	}
	return intData;
    }

    public static double[] int2double(int[] intData) {
	double[] doubleData = new double[intData.length];
	for ( int i=0; i < intData.length; i++ ) {
	    doubleData[i] = (new Integer(intData[i])).doubleValue();
	}
	return doubleData;
    }
    
    public static float[][] double2float(double[][] doubleData) {
	ArrayFuncs arrayFuncs = new ArrayFuncs();
	int[] dataDims = arrayFuncs.getDimensions(doubleData);
	//  dataDims[0] = number of col (xaxis length)
	//  dataDims[1] = number of rows (yaxis length)
	////  convert floats to doubles
	float[][] floatData = new float[dataDims[0]][dataDims[1]];
	for ( int row=0; row < dataDims[1]; row++ ) {
	    for ( int col=0; col < dataDims[0]; col++ ) {
		floatData[col][row] = (new Double(doubleData[col][row])).floatValue();
	    }
	}
	return floatData;
    }
    
    public static float[] double2float(double[] doubleData) {
	float[]  floatData = new float[doubleData.length];
	for ( int i=0; i < doubleData.length; i++ ) {
	    floatData[i] = (new Double(doubleData[i])).floatValue();
	}
	return floatData;
    }

    public static float[][] int2float(int[][] intData) {
	ArrayFuncs arrayFuncs = new ArrayFuncs();
	int[] dataDims = arrayFuncs.getDimensions(intData);
	//  dataDims[0] = number of col (xaxis length)
	//  dataDims[1] = number of rows (yaxis length)
	float[][] floatData = new float[dataDims[0]][dataDims[1]];
	for ( int row=0; row < dataDims[1]; row++ ) {
	    for ( int col=0; col < dataDims[0]; col++ ) {
		floatData[col][row] = (new Integer(intData[col][row])).floatValue();
	    }
	}
	return floatData;
    }

    public static Histogram1D array2histo(double xmin, double binWidth, double[] data) {
	return array2histo("histo", xmin, binWidth, data);
    }

    public static Histogram1D array2histo(String title, double xmin, double binWidth, double[] data) {
	//  Define histogram axis
	int dataLength = data.length; 
 	double xmax = xmin + dataLength*binWidth;
 	FixedAxis xaxis = new FixedAxis(dataLength, xmin, xmax);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[] and errors[]
	double[] heights = new double[nbins];
	double[] errors = new double[nbins];
	for ( int i=1; i <= xaxis.bins(); i++ ) {
	    heights[i] = data[i-1];
	    errors[i] = 0; //Math.sqrt(heights[i]);
	}
	//  Set contents of histogram
	Histogram1D histo = new Histogram1D("histo", title, xaxis);
	int[] entries = null;
	double[] meanXs = null;
	double[] rmsXs = null;
	histo.setContents(heights, errors, entries, meanXs, rmsXs);
	return histo;
    }

    public static Histogram1D array2histo(String title, double xmin, double binwidth, double[] data, double[] error) {
	//  Define histogram edges and axis
	int dataLength = data.length; 
 	double[] binEdgesX = new double[dataLength+1];
	for ( int i=0; i < binEdgesX.length; i++ )  binEdgesX[i] = xmin + i*(binwidth);
 	VariableAxis xaxis = new VariableAxis(binEdgesX);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[][] and errors[][]
	double[] heights = new double[nbins];
	double[] errors = new double[nbins];
	for ( int i=1; i <= xaxis.bins(); i++ ) {
	    heights[i] = data[i-1];
	    errors[i] = error[i-1];
	}
	//  Set contents of histogram
	Histogram1D histo = new Histogram1D("histo", title, xaxis);
	int[] entries = null;
	double[] meanXs = null;
	double[] rmsXs = null;
	histo.setContents(heights, errors, entries, meanXs, rmsXs);
	return histo;
    }

    public static Histogram1D array2histo(String title, double xmin, double[] binEdges, double[] data) {
	//  Define histogram edges and variable bin size axis
	//  **  IMPORTANT  **  Plotter cannot display bins of different sizes
 	VariableAxis xaxis = new VariableAxis(binEdges);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[][] and errors[][]
	double[] heights = new double[nbins];
	double[] errors = new double[nbins];
	for ( int i=1; i <= xaxis.bins(); i++ ) {
	    heights[i] = data[i-1];
	    errors[i] = 0; //Math.sqrt(heights[i]);
	}
	//  Set contents of histogram and return it
	Histogram1D histo = new Histogram1D("histo", title, xaxis);
	int[] entries = null;
	double[] meanXs = null;
	double[] rmsXs = null;
	histo.setContents(heights, errors, entries, meanXs, rmsXs);
	return histo;
    }

    public static Histogram1D array2histo(String title, double xmin, double[] binEdges, double[] data, double[] error) {
	//  Define histogram edges and variable bin size axis
	//  **   BUG   **  Plotter cannot display bins of different sizes
 	VariableAxis xaxis = new VariableAxis(binEdges);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[][] and errors[][]
	double[] heights = new double[nbins];
	double[] errors = new double[nbins];
	for ( int i=1; i <= xaxis.bins(); i++ ) {
	    heights[i] = data[i-1];
	    errors[i] = error[i-1];
	}
	//  Set contents of histogram and return it
	Histogram1D histo = new Histogram1D("histo", title, xaxis);
	int[] entries = null;
	double[] meanXs = null;
	double[] rmsXs = null;
	histo.setContents(heights, errors, entries, meanXs, rmsXs);
	return histo;
    }

    public static Histogram2D image2histo(float[][] imaData, float[][] varData) {
	//  Get dimensions of input data array
	int[] dims = ArrayFuncs.getDimensions(imaData);
	int xsize = dims[0];  // number of cols
	int ysize = dims[1];  // number of rows
	//  Define histogram edges and axes
 	double[] binEdgesX = new double[xsize+1];
	double[] binEdgesY = new double[ysize+1];
	for ( int i=0; i < binEdgesX.length; i++ ) binEdgesX[i] = i;
	for ( int i=0; i < binEdgesY.length; i++ ) binEdgesY[i] = i;
 	VariableAxis xaxis = new VariableAxis(binEdgesX);
 	VariableAxis yaxis = new VariableAxis(binEdgesY);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[][] and errors[][]
	double[][] heights = new double[nbins][nbins];
	double[][] errors = new double[nbins][nbins];
	int i = -1,  j = -1;
	for ( int y = 0; y < ysize; y++ ) {
	    while ( j < xaxis.bins()-2 ) {
		for ( int x = 0; x < xsize; x++ ) {
		    heights[j+2][i+2] = (new Double(imaData[y][x])).doubleValue();
		    errors[j+2][i+2] = Math.sqrt(varData[y][x]);
		    j++;
		}
		i++;
	    }
	    j=-1;
	}
	//  Set contents of histogram
	Histogram2D histo2d = new Histogram2D("histo2d ", "image2histo ", xaxis, yaxis);
	int[][] entries = null;
	double[][] meanXs = null;
	double[][] rmsXs = null;
	double[][] meanYs = null;
	double[][] rmsYs = null;
	histo2d.setContents(heights, errors, entries, meanXs, rmsXs, meanYs, rmsYs);
	return histo2d;
    }

    public static Histogram2D image2histo(float[][] imaData, float[][] varData, int xfirst, int xlast, int yfirst, int ylast) {
	//  Get size of sub-image
	int xsize = xlast - xfirst;
	int ysize = ylast - yfirst;
	//  Define histogram edges and axes
 	double[] binEdgesX = new double[xsize+1];
	double[] binEdgesY = new double[ysize+1];
	for ( int i=0; i < binEdgesX.length; i++ ) binEdgesX[i] = i;
	for ( int i=0; i < binEdgesY.length; i++ ) binEdgesY[i] = i;
 	VariableAxis xaxis = new VariableAxis(binEdgesX);
 	VariableAxis yaxis = new VariableAxis(binEdgesY);
	int nbins = xaxis.bins() + 2;
	//  Fill mandatory array for histogram i.e. heights[][] and errors[][]
	double[][] heights = new double[nbins][nbins];
	double[][] errors = new double[nbins][nbins];
	int i = -1,  j = -1;
	for ( int y = yfirst; y < ylast; y++ ) {
	    while ( j < xaxis.bins()-2 ) {
		for ( int x = xfirst; x < xlast; x++ ) {
		    heights[j+2][i+2] = (new Double(imaData[y][x])).doubleValue();
		    errors[j+2][i+2] = Math.sqrt(varData[y][x]);
		    j++;
		}
		i++;
	    }
	    j=-1;
	}
	//  Set contents of histogram
	Histogram2D histo2d = new Histogram2D("histo2d ", "image2histo ", xaxis, yaxis);
	int[][] entries = null;
	double[][] meanXs = null;
	double[][] rmsXs = null;
	double[][] meanYs = null;
	double[][] rmsYs = null;
	histo2d.setContents(heights, errors, entries, meanXs, rmsXs, meanYs, rmsYs);
	return histo2d;
    }

    public static double[][] fluxDensityToEquivCountRate(double[] flux, double[] error, double[] binWidths) {
	DecimalFormat number = new DecimalFormat("0.000");
	//  Get means from input flux density 
	double wMeanFlux = BasicStats.getWMean(flux, error);
	double aveErrFlux = BasicStats.getMean(error);
	double aveBinWidth = BasicStats.getMean(binWidths);
	//System.out.println("Log  : Weighted mean flux density = "+number.formatwMeanFlux));
	//System.out.println("Log  : Average error per bin = "+number.format(aveErrFlux));
	//System.out.println("Log  : Average bin width = "+number.format(aveBinWidth));
	//  Convert each flux density to equivalent count rate
	int nbins = flux.length;
	double meanEquivCounRate = Math.pow(wMeanFlux/aveErrFlux, 2);
	double scalingFactor = meanEquivCounRate/wMeanFlux;
	double[] equivCountRate = new double[nbins];
	double[] equivCountRateError = new double[nbins];
	double[] signifRate = new double[nbins];
	for ( int i=0; i < nbins; i++ ) {
	    equivCountRate[i] = scalingFactor*flux[i];
	    equivCountRateError[i] = scalingFactor*error[i];
	    signifRate[i] = equivCountRate[i]/equivCountRateError[i];
	}
	//  Get weighted mean count rate
	//System.out.println("Log  : Scaling factor = "+scalingFactor);
	double wMeanRate = BasicStats.getWMean(equivCountRate, equivCountRateError);
	//System.out.println("Log  : Weighted mean equivalent count rate = "+wMeanRate+" cts/s");
	//System.out.println("Log  : Average signficance per bin = "+number.format(BasicStats.getMean(signifRate)));
	double[][] equivRateAndError = new double[][] {equivCountRate, equivCountRateError};
	return equivRateAndError;
    }

}