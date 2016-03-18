/*
MLP neural network in Java
by Phil Brierley
www.philbrierley.com

This code may be freely used and modified at will

Tanh hidden neurons
Linear output neuron

To include an input bias create an
extra input in the training data
and set to 1

Routines included:

calcNet()
WeightChangesHO()
WeightChangesIH()
initWeights()
initData()
tanh(double x)
displayResults()
calcOverallError()

compiled and tested on
Symantec Cafe Lite

*/

import java.lang.Math;

public class NeuralNetwork
{// only one output.

 //user defineable variables   THOSE VALUE ARE default value, if user don't build it, then it won't change.
 public static int numEpochs; //number of training cycles
 public static int numHidden; //number of hidden units
 public static double LR_IH; //learning rate
 public static double LR_HO; //learning rate   
 public static int numPatterns; //number of training patterns
 public static int numInputs; //number of inputs - this includes the input bias
 
 //process variables
 public static int patNum;
 public static double errThisPat;
 public static double outPred;
 public static double RMSerror;

 //training data
 public static double[][] trainInputs  ;
 public static double[] trainOutput ;

 //the outputs of the hidden neurons
 public static double[] hiddenVal;

 //the weights
 public static double[][] weightsIH;
 public static double[] weightsHO;
 
//==============================================================
//****************** builder pattern ***************************
//==============================================================
public static class builder{
	//required parameters: 
	private double [][] trainInputs;     // ??? WHY THOSE VARIABLIES MUST BE INITIALIZED if I modified them with final
	private double [] trainOutput;
	private int numInputs;
	private int numPatterns;
	
	//Optional parameters: 	(have default value)
	private int numHidden = 4 ;
	private int numEpochs = 500;
	public double LR_IH = 0.7; 
	public double LR_HO = 0.07;
	
	 public double[] hiddenVal ; //=new double[numHidden] ;

	 //the weights
	 public double[][] weightsIH ;//= new double[numInputs][numHidden];      // this place cannot be initialized  won't work when instance class Neural
	 public double[] weightsHO; // = new double[numHidden];
	
	
	public builder(double [][] trainInput, double [] trainOutput){
		this.trainInputs= trainInput;
		this.trainOutput= trainOutput;
		this.numInputs = trainInput[0].length;
		this.numPatterns= trainInput.length;
		
		//the outputs of the hidden neurons
		this.hiddenVal  = new double[this.numHidden];

		//the weights
		this.weightsIH = new double[this.numInputs][this.numHidden];
		this.weightsHO = new double[this.numHidden];
		 
	}
	
	public builder numHidder(int value){
		this.numHidden= value;
		return this;
	}
	
	public builder numEpochs(int value){
		this.numEpochs= value;
		return this;
	}
	
	public builder LearningRateInputHidden( double value){   
		this.LR_IH= value;
		return this;
	}
	
	public builder LearningRateHiddenOutput(double value){
		this.LR_HO = value;
		return this;
	}
	
	public NeuralNetwork build(){
		return new NeuralNetwork(this);
	}
  
}

	private NeuralNetwork( builder b){
		numEpochs = b.numEpochs; //number of training cycles
		numInputs = b.numInputs; //number of inputs - this includes the input bias
		numHidden  = b.numHidden; //number of hidden units
		numPatterns= b.numPatterns ; //number of training patterns
		LR_IH = b.LR_IH; //learning rate
		LR_HO = b.LR_HO; //learning rate   
		trainInputs  = b.trainInputs;
		trainOutput = b.trainOutput;
		hiddenVal= b.hiddenVal;
		weightsIH = b.weightsIH;
		weightsHO = b.weightsHO;
	
	}
//==============================================================
//********** THE END OF BUILD PATTERN **************************
//==============================================================


//==============================================================
//********** THIS IS THE MAIN PROGRAM **************************
//==============================================================

 public static void mainNN()
 {

  //initiate the weights
	 NeuralNetwork.initWeights();               // ?? Method invocation problem ?

  //load in the data
 // initData();                   REPLACED BY BUILDER

  //train the network
    for(int j = 0;j <= numEpochs;j++)
    {

        for(int i = 0;i<numPatterns;i++)
        {

            //select a pattern at random
        	patNum = (int)((Math.random()*numPatterns)-0.001);

            //calculate the current network output
            //and error for this pattern
            NeuralNetwork.calcNet();

            //change network weights
            NeuralNetwork.WeightChangesHO();
            NeuralNetwork.WeightChangesIH();
        }

        //display the overall network error
        //after each epoch
        NeuralNetwork.calcOverallError();
        System.out.println("epoch = " + j + "  RMS Error = " + RMSerror);

    }

    //training has finished
    //display the results
    displayResults();

 }

//============================================================
//********** END OF THE MAIN PROGRAM **************************
//=============================================================






//************************************
public static void calcNet()
 {
    //calculate the outputs of the hidden neurons
    //the hidden neurons are tanh
    for(int i = 0;i<numHidden;i++)
    {
	hiddenVal[i] = 0.0;

        for(int j = 0;j<numInputs;j++)
        hiddenVal[i] = hiddenVal[i] + (trainInputs[patNum][j] * weightsIH[j][i]);

        hiddenVal[i] = tanh(hiddenVal[i]);
    }

   //calculate the output of the network
   //the output neuron is linear
   outPred = 0.0;

   for(int i = 0;i<numHidden;i++)
    outPred = outPred + hiddenVal[i] * weightsHO[i];

    //calculate the error
    errThisPat = outPred - trainOutput[patNum];
 }


//************************************
 public static void WeightChangesHO()
 //adjust the weights hidden-output
 {
   for(int k = 0;k<numHidden;k++)
   {
    double weightChange = LR_HO * errThisPat * hiddenVal[k];
    weightsHO[k] = weightsHO[k] - weightChange;

    //regularisation on the output weights
    if (weightsHO[k] < -5)
        weightsHO[k] = -5;
    else if (weightsHO[k] > 5)
        weightsHO[k] = 5;
   }
 }


//************************************
 public static void WeightChangesIH()
 //adjust the weights input-hidden
 {
  for(int i = 0;i<numHidden;i++)
  {
   for(int k = 0;k<numInputs;k++)
   {
    double x = 1 - (hiddenVal[i] * hiddenVal[i]);
    x = x * weightsHO[i] * errThisPat * LR_IH;
    x = x * trainInputs[patNum][k];
    double weightChange = x;
    weightsIH[k][i] = weightsIH[k][i] - weightChange;
   }
  }
 }


//************************************
 public static void initWeights()
 {

  for(int j = 0;j<numHidden;j++)
  {
    weightsHO[j] = (Math.random() - 0.5)/2;
    for(int i = 0;i<numInputs;i++)
    weightsIH[i][j] = (Math.random() - 0.5)/5;
  }

 }


//************************************
 public static double tanh(double x)
 {
    if (x > 20)
        return 1;
    else if (x < -20)
        return -1;
    else
        {
        double a = Math.exp(x);
        double b = Math.exp(-x);
        return (a-b)/(a+b);
        }
 }


//************************************
 public static void displayResults()
    {
     for(int i = 0;i<numPatterns;i++)
        {
        patNum = i;
        calcNet();
        System.out.println("pat = " + (patNum+1) + " actual = " + trainOutput[patNum] + " neural model = " + outPred);
        }
    }


//************************************
public static void calcOverallError()
    {
     RMSerror = 0.0;
     for(int i = 0;i<numPatterns;i++)
        {
        patNum = i;
        calcNet();
        RMSerror = RMSerror + (errThisPat * errThisPat);
        }
     RMSerror = RMSerror/numPatterns;
     RMSerror = java.lang.Math.sqrt(RMSerror);
    }

}
