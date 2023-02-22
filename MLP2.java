	import java.awt.Shape;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
	import java.lang.Math;
	import java.math.BigDecimal;
	import java.math.MathContext;
	import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

	public class MLP2 {
	    //Fields.
	    private List<Double[]> trainingDataSet ;
	    private List<Double[]> testDataSet ;
	    private List<Double[]> C1 ;
	    private List<Double[]> C2 ;
	    private List<Double[]> C3 ;
	    private List<Double[]> C4 ;

	    private List<HiddenNeuron> H1List;
	    private List<HiddenNeuron> H2List;
	    private List<HiddenNeuron> H3List;
	    private List<InputNeuron> inputNeuronsList;
	    private List<OutputNeuron> outputNeuronsList;

	    private final int d  = 2;
	    private Double learningRate ;
	    private final int k  = 4;
	    private final int H1 = 4;
	    private final int H2 = 4;
	    private final int B  = 4000;
	    private final String hiddenLayerActivationFunction = "tanh";//for hidden neurons.
	    private final String outputLayerActivationFunction = "logistic"; // for output layer neuron.

	    public MLP2(Double learningRate){
	    	this.learningRate = learningRate;
	    	
	        this.H1List = new ArrayList<HiddenNeuron>();
	        this.H2List = new ArrayList<HiddenNeuron>();
	        this.H3List = new ArrayList<HiddenNeuron>();
	        this.inputNeuronsList = new ArrayList<InputNeuron>();
	        this.outputNeuronsList = new ArrayList<OutputNeuron>();
	        
	    }
	    
	   public  void createDataSets(){
	        //Create Σ1.
	        this.trainingDataSet = new ArrayList<Double[]>();
	        this.testDataSet = new ArrayList<Double[]>();
	        this.C1 = new ArrayList<Double[]>();
	        this.C2 = new ArrayList<Double[]>();
	        this.C3 = new ArrayList<Double[]>();
	        this.C4 = new ArrayList<Double[]>();

	        Random random = new Random();

	        for(int i=0; i<4000; i++){//TODO CHANGE THIS!!!
	            Double list1[]={-1 + (1 - (-1) ) * random.nextDouble() ,-1 + (1 - (-1) ) * random.nextDouble()};
	            Double list2[]={-1 + (1 - (-1) ) * random.nextDouble() ,-1 + (1 - (-1) ) * random.nextDouble()};
	            
	            this.trainingDataSet.add( list1 );
	            this.testDataSet.add(list2 );
	        }
	        //Adding (x1,x2) to C1 , C2 , C3 , C4
	        for(Double[] x : trainingDataSet){
	            
	            if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[0] - 0.5 , 2)< 0.16){
	                this.C1.add(x);
	            }
	            else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){
	                this.C1.add(x);
	            }
	            else if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){
	                this.C2.add(x);
	            }
	            else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] - 0.5 , 2)< 0.16){
	                this.C2.add(x);
	            }
	            else if(( x[0] > 0 && x[1] > 0 ) || ( x[0] < 0 && x[1] < 0 ) ){ // 1st or 3rd quadrant.
	                this.C3.add(x);
	            }
	            
	            else if(( x[0] < 0 && x[1] > 0 ) || ( x[0] > 0 && x[1] < 0 ) ){ // 2nd or 4th quadrant.
	                this.C4.add(x);
	            }
	        }//end for.

	        for(Double[] x : testDataSet){
	            
	            if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[0] - 0.5 , 2)< 0.16){
	                this.C1.add(x);
	            }
	            else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){
	                this.C1.add(x);
	            }
	            else if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){
	                this.C2.add(x);
	            }
	            else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] - 0.5 , 2)< 0.16){
	                this.C2.add(x);
	            }
	            else if(( x[0] > 0 && x[1] > 0 ) || ( x[0] < 0 && x[1] < 0 ) ){ // 1st or 3rd quadrant.
	                this.C3.add(x);
	            }
	            
	            else if(( x[0] < 0 && x[1] > 0 ) || ( x[0] > 0 && x[1] < 0 ) ){ // 2nd or 4th quadrant.
	                this.C4.add(x);
	            }
	        }//end for.

	        //Add noise to our training dataSet.
	        for(Double[] x : this.trainingDataSet){
	            
	            int prob = random.nextInt(10);
	            //System.out.print("\nProbability:"+probability);
	            
	            if(prob == 1){
	                if(C1.contains(x)){
	                    C1.remove(x);
	                    int randList=random.nextInt(3);
	                    if(randList == 0){
	                        C2.add(x);
	                    }
	                    else if(randList == 1){
	                        C3.add(x);
	                    }
	                    else if(randList == 2){
	                        C4.add(x);
	                    }

	                }
	                else if(C2.contains(x)){
	                    C2.remove(x);
	                    int randList=random.nextInt(3);
	                    if(randList == 0){
	                        C1.add(x);
	                    }
	                    else if(randList == 1){
	                        C3.add(x);
	                    }
	                    else if(randList == 2){
	                        C4.add(x);
	                    }
	                }
	                else if(C3.contains(x)){
	                    C3.remove(x); 
	                    int randList=random.nextInt(3);
	                    if(randList == 0){
	                        C2.add(x);
	                    }
	                    else if(randList == 1){
	                        C1.add(x);
	                    }
	                    else if(randList == 2){
	                        C4.add(x);
	                    }
	                }
	                else if(C4.contains(x)){
	                    C4.remove(x);                }
	                    int randList=random.nextInt(3);
	                    if(randList == 0){
	                        C2.add(x);
	                    }
	                    else if(randList == 1){
	                        C3.add(x);
	                    }
	                    else if(randList == 2){
	                        C1.add(x);
	                    }
	                }
	        }




	    }//end of method

	   public void  setTrainSet( List<Double[]> newTrainSet) {
	 	    	this.trainingDataSet=newTrainSet;
	   }
	   
	   public void  setTestSet( List<Double[]> newTestSet) {
	    	this.testDataSet=newTestSet;
	   }
  
	   
	   public void setLeanringRate() {
		   for(HiddenNeuron h1n : this.H1List) {
	   		h1n.setLeanringRate(this.learningRate);
	   	}
	   	
	   	for(HiddenNeuron h2n : this.H2List) {
	   		h2n.setLeanringRate(this.learningRate);
	   	}
	   	
	   	for(OutputNeuron outn : this.outputNeuronsList) {
	   		outn.setLeanringRate(this.learningRate);
	   	}
	   }
	    
	    public void printDataSets(){
	        for(Double[] x : trainingDataSet){
	            System.out.println("TraningSet: x1= " + x[0] +"\tx2="+x[1]);
	        }

	        System.out.println("\nC1:\n");
	        for (Double[] x : C1) {
	            System.out.println("x0= " +x[0]+"\tx1="+x[1]);
	        }
	        
	        System.out.println("\nC2:\n");
	        for (Double[] x : C2) {
	            System.out.println("x0= "+x[0]+"\tx1="+x[1]);
	        }

	        
	        System.out.println("\nC3:\n");
	        for (Double[] x : C3) {
	            System.out.println("x0= "+x[0]+"\tx1="+x[1]);
	        }

	        
	        System.out.println("\nC4:\n");
	        for (Double[] x : C4) {
	            System.out.println("x0= "+x[0]+"\tx1="+x[1]);
	        }

	    }

	    private Double[] calcTn(Double[] x){
	        if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[0] - 0.5 , 2)< 0.16){//C1.
	        	Double[] tn = {1.0,0.0,0.0,0.0};
	            return tn;
	        }
	        else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){//C1.
	        	Double[] tn = {1.0,0.0,0.0,0.0};      
	        	return tn;
	        }
	        else if( Math.pow(x[0] - 0.5 , 2) + Math.pow(x[1] + 0.5 , 2)< 0.16){//C2.
	        	Double[] tn = {0.0,1.0,0.0,0.0};
	        	return tn; 
	        }
	        else if( Math.pow(x[0] + 0.5 , 2) + Math.pow(x[1] - 0.5 , 2)< 0.16){//C2.
	        	Double[] tn = {0.0,1.0,0.0,0.0};
	        	return tn;
	        }        
	        else if(  (x[0] >= 0 && x[1] >= 0 ) || ( x[0] <= 0 && x[1] <= 0 ) ){
	        	Double[] tn = {0.0 , 0.0 , 1.0 , 0.0};//C3.
	            return tn;
	        }
	        else if(( x[0] <= 0 && x[1] >= 0 ) || ( x[0] >= 0 && x[1] <= 0 ) ) {//C4.
	        	Double[] tn = {0.0 , 0.0 , 0.0 , 1.0};//C3.
	        	return tn;
	        }
	        else {
	        	return null;
	        }
	    }

	    public void addToH1(HiddenNeuron neuron){
	        this.H1List.add(neuron);
	    }

	    public void addToH2(HiddenNeuron neuron){
	        this.H2List.add(neuron);
	    }

	    public void addToH3(HiddenNeuron neuron){
	        this.H3List.add(neuron);
	    }    
	    
	    public void addToInputNeuronList(InputNeuron neuron){
	        this.inputNeuronsList.add(neuron);
	    }

	    public void addToOutputNeuronList(OutputNeuron neuron){
	        this.outputNeuronsList.add(neuron);
	    }
	    
	    public Double[] forwardPass(Double[] x ){//Returns the predicted outputs from our neural network. 

	        //Give inputs to input Layer neurons.
	        this.inputNeuronsList.get(0).addInput(x[0]);
	        this.inputNeuronsList.get(1).addInput(x[1]);
	        
	        //Take input layer neurons's output.
	        Double inp0=inputNeuronsList.get(0).neuronOutput();
	        Double inp1=inputNeuronsList.get(1).neuronOutput();
	        Double[] inputLayerOutputs = {inp0 , inp1};
	        
	        //Give inputs to 1st hidden layer neurons.
	        for(HiddenNeuron h1n : this.H1List){
	        	h1n.addInput(inputLayerOutputs);
	        }
	        
	        //Take 1st Hidden layer neuron's outputs.
	        Double[] firstHiddenLayerOutputs = new Double[this.H1List.size()];
	        for(int i=0; i<this.H1List.size(); i++){
	        	firstHiddenLayerOutputs[i] = this.H1List.get(i).neuronOutput();
	        }
	        
	        //Give inputs to 2nd hidden layer neurons.
	        for(HiddenNeuron h2n : this.H2List){
	        	h2n.addInput(firstHiddenLayerOutputs);
	        }
	           
	        //Take 2nd Hidden layer neuron's outputs.
	        Double[] secondHiddenLayerOutputs = new Double[this.H2List.size()];
	        for(int i=0; i<this.H2List.size(); i++){
	        	secondHiddenLayerOutputs[i] = this.H2List.get(i).neuronOutput();
	        }
	        
	        //Give inputs to output layer neurons.
	        for(OutputNeuron outn : this.outputNeuronsList){
	        	outn.addInput(secondHiddenLayerOutputs);
	        }
	        
	        //Take output layer neuron's outputs.
	        Double[] outputs = new Double[this.outputNeuronsList.size()];
	        for(int i=0; i<this.outputNeuronsList.size(); i++){
	        	outputs[i] = this.outputNeuronsList.get(i).neuronOutput();
	        }
	        
	        //System.out.println("Output: "+this.outputNeuronsList.get(0).neuronOutput() +"\t"+this.outputNeuronsList.get(1).neuronOutput()
	        //                        +"\t"+this.outputNeuronsList.get(2).neuronOutput()+"\t"+this.outputNeuronsList.get(3).neuronOutput());
	    
	      
	       
	       return outputs;
	    }//end of method.

	    public void  backProp(Double[] x , int d , Double[] tn , Double[] on ) {//Returns the partials derivatives of En with respect to wi.
	        MathContext m = new MathContext(4); // 4 precision
	    	
	    	//Predicted outputs of each neuron.
	    	Double o0 = on[0];
	    	Double o1 = on[1];
	    	Double o2 = on[2];
	    	Double o3 = on[3];
	    	
	    	//Errors for output neurons
	    	Double d0 = o0 * (1 - o0) *(o0 - tn[0]); 
	    	Double d1 = o2 * (1 - o1) *(o1 - tn[1]); 
	    	Double d2 = o2 * (1 - o2) *(o2 - tn[2]); 
	    	Double d3 = o3 * (1 - o3) *(o3 - tn[3]);
	    	Double[] dList = {d0,d1,d2,d3};
	    	
	    	//Add new derivatives.
	    	for(int n=0; n<this.outputNeuronsList.size(); n++) {
	    		this.outputNeuronsList.get(n).setDelta(dList[n]);
	    		Double[] newDerivatives = new Double[5];
	    		newDerivatives[0] = this.outputNeuronsList.get(n).getDelta() * this.H3List.get(0).neuronOutput() ;
	    		newDerivatives[1] = this.outputNeuronsList.get(n).getDelta() * this.H3List.get(1).neuronOutput() ;
	    		newDerivatives[2] = this.outputNeuronsList.get(n).getDelta() * this.H3List.get(2).neuronOutput() ;
	    		newDerivatives[3] = this.outputNeuronsList.get(n).getDelta() * this.H3List.get(3).neuronOutput() ;
	    		newDerivatives[4] = this.outputNeuronsList.get(n).getDelta();//for bias.
	    		this.outputNeuronsList.get(n).addDerivative(newDerivatives);
	    	}
	    	
	    	
	    	
	    	//Find deltas for 3rd hidden layer neurons.
	    	for(int n=0; n<this.H3List.size(); n++) {
	    		Double sum = 0.0;
	    		for(OutputNeuron outn : this.outputNeuronsList) {
	    			sum = sum + ( outn.getWeights().get(n) * outn.getDelta()) ;
	    		}
	    		Double delta = this.H3List.get(n).neuronOutput() * (1 - this.H3List.get(n).neuronOutput()) * sum;
	    		this.H3List.get(n).setDelta(delta);
	    	}
	    	
	    	//Add new derivatives.
	    	for(int n=0; n<this.H3List.size(); n++) {
	    		Double[] newDerivatives = new Double[5];
	    		newDerivatives[0] = this.H3List.get(n).getDelta()  * this.H2List.get(0).neuronOutput() ;
	    		newDerivatives[1] = this.H3List.get(n).getDelta()  * this.H2List.get(1).neuronOutput() ;
	    		newDerivatives[2] = this.H3List.get(n).getDelta()  * this.H2List.get(2).neuronOutput() ;
	    		newDerivatives[3] = this.H3List.get(n).getDelta()  * this.H2List.get(3).neuronOutput() ;
	    		newDerivatives[4] = this.H3List.get(n).getDelta() ;//bias.
	    		this.H3List.get(n).addDerivative(newDerivatives);
	    	}
	    	
	    	
	    	
	    	//Find deltas for 2nd hidden layer neurons.
	    	for(int n=0; n<this.H2List.size(); n++) {
	    		Double sum = 0.0;
	    		for(HiddenNeuron h3n : this.H3List) {
	    			sum = sum + ( h3n.getWeights().get(n) * h3n.getDelta()) ;
	    		}
	    		Double delta = this.H2List.get(n).neuronOutput() * (1 - this.H2List.get(n).neuronOutput()) * sum;
	    		this.H2List.get(n).setDelta(delta);//Update deltas in every neuron.
	    	}
	    	
	    	//Add new derivatives.
	    	for(int n=0; n<this.H2List.size(); n++) {
	    		Double[] newDerivatives = new Double[5];
	    		newDerivatives[0] = this.H2List.get(n).getDelta()  * this.H1List.get(0).neuronOutput() ;
	    		newDerivatives[1] = this.H2List.get(n).getDelta()  * this.H1List.get(1).neuronOutput() ;
	    		newDerivatives[2] = this.H2List.get(n).getDelta()  * this.H1List.get(2).neuronOutput() ;
	    		newDerivatives[3] = this.H2List.get(n).getDelta()  * this.H1List.get(3).neuronOutput() ;
	    		newDerivatives[4] = this.H2List.get(n).getDelta() ;//bias.
	    		this.H2List.get(n).addDerivative(newDerivatives);
	    	}
	    	
	    	
	    	
	    	//Find deltas for 1st hidden layer neurons.
	    	for(int n=0; n<this.H1List.size(); n++) {
	    		Double sum = 0.0;
	    		for(HiddenNeuron h2n : this.H2List) {
	    			sum = sum + ( h2n.getWeights().get(n) * h2n.getDelta()) ;
	    		}
	    		Double delta = this.H1List.get(n).neuronOutput() * (1 - this.H1List.get(n).neuronOutput()) * sum;
	    		this.H1List.get(n).setDelta(delta);//Update each neuron's delta.
	    	}
	    	
	    	//Add new derivatives.
	    	for(int n=0; n<this.H1List.size(); n++) {
	    		Double[] newDerivatives = new Double[3];
	    		newDerivatives[0] = this.H1List.get(n).getDelta()  * this.inputNeuronsList.get(0).neuronOutput() ;
	    		newDerivatives[1] = this.H1List.get(n).getDelta()  * this.inputNeuronsList.get(1).neuronOutput() ;
	    		newDerivatives[2] = this.H1List.get(n).getDelta() ;//bias.
	    		this.H1List.get(n).addDerivative(newDerivatives);
	    	}
	    	    }//end of method.   
	   
	    private BigDecimal calcEn(Double[] x , Double[] on , Double[] tn) { //Returns mean square error for given x1&x2.
	    	BigDecimal En = BigDecimal.ZERO;
	    	Double sum = 0.0;
	    	for(int i=0;i<on.length; i++) {
	    		Double a = tn[i] - on[i]; 
	    		Double b = Math.pow(a, 2);
	    		sum = sum +( b );
	    	}
	    	sum = sum * 1.0 / 4.0;
	    	En=BigDecimal.valueOf(sum);
	    	return En;
	    }
	    
	    public void trainWithGradientDescent() {
	    	for(int epoch = 0; epoch<1000; epoch++){
	    		
	    		//Set partial Derivatives to 0.
	    		for(OutputNeuron outn : this.outputNeuronsList) {
	    			outn.clearDerivatives();
	    		}
	    		
	    		for(HiddenNeuron n : this.H2List) {
	    			n.clearDerivatives();
	    		}
	    		
	    		for(HiddenNeuron n : this.H1List) {
	    			n.clearDerivatives();
	    		}
	    		
	    		BigDecimal  epochError = BigDecimal.ZERO;
	    		
	    		int N=0;
		    	for(Double[] x :this.trainingDataSet) {
		    		N+=1;
		    		Double[] tn = calcTn(x);//Calculate the proper result of neural network.
		    		Double[] on = forwardPass(x);//Calculate the predicted result of neural network.
		    		backProp(x , 2 ,tn , on);

		    		if(N == B) {
		    			//Update weights.
		    			//System.out.println("Update Weights, mini-batch.");
		    	    	for(HiddenNeuron h1n : this.H1List) {
		    	    		h1n.updateWeight();
		    	    	}
		    	    	
		    	    	for(HiddenNeuron h2n : this.H2List) {
		    	    		h2n.updateWeight();	    
		    	    	}
		    	    	
		    	    	for(OutputNeuron outn : this.outputNeuronsList) {
		    	    		outn.updateWeight();
		    	    	}
		    	    	N=0;
		    		}
		    		epochError = epochError.add(calcEn(x,on,tn));	
		    	
		    	}
	   	    	System.out.println("\nEpoch: "+epoch+" Error/N= "+  epochError.divide(BigDecimal.valueOf(this.trainingDataSet.size())).toPlainString());
		    	
		    	
		    }//end of epoch.
	    }//end of method.
	    
	    private void printWeights() {
	    	System.out.println("Output Neurons Weights:");
	    	for(OutputNeuron outn : this.outputNeuronsList) {
	    		for(Double w : outn.getWeights()) {
	    			System.out.println(w);
	    		}
	    	}
	    	
	    	System.out.println("2nd Hidden layer neurons weight");
	    	for(HiddenNeuron h2n : this.H2List) {
	    		for(Double w : h2n.getWeights()) {
	    			System.out.println(w);
	    		}   
	    	}
	    	
	    	
	    	System.out.println("1st Hidden layer neurons weight");
	    	for(HiddenNeuron h1n : this.H1List) {
	    		for(Double w : h1n.getWeights()) {
	    			System.out.println(w);
	    		}   
	    	}
	    }
	   
	    public void runTest() {
			int correct=0;
			int wrong=0;
			int N=this.testDataSet.size();
			List<Double[]> corrects = new ArrayList<Double[]>();
			List<Double[]> incorrects = new ArrayList<Double[]>();
			
	    	for(Double[] x :this.testDataSet) {
	    		Double[] tn = calcTn(x);//Calculate the proper result of neural network.
	    		Double[] on = forwardPass(x );//Calculate the predicted result of neural network.
	    		
	    		System.out.println("\nx0= "+BigDecimal.valueOf(x[0]).toPlainString()+"\t x1= "+BigDecimal.valueOf(x[1]).toPlainString());
	    		System.out.println("o0= "+BigDecimal.valueOf(on[0]).toPlainString()+"\t t0= "+BigDecimal.valueOf(tn[0]).toPlainString());
	    		System.out.println("o1= "+BigDecimal.valueOf(on[1]).toPlainString()+"\t t1= "+BigDecimal.valueOf(tn[1]).toPlainString());
	    		System.out.println("o2= "+BigDecimal.valueOf(on[2]).toPlainString()+"\t t2= "+BigDecimal.valueOf(tn[2]).toPlainString());
	    		System.out.println("o3= "+BigDecimal.valueOf(on[3]).toPlainString()+"\t t3= "+BigDecimal.valueOf(tn[3]).toPlainString());
	    		
	    		Double maxO=-100.0;
	    		int index=-1;
	    		
	    		for(int i=0; i<on.length; i++) {
	    			if(on[i] > maxO) {
	    				maxO=on[i];
	    				index=i;
	    			}
	    			else {
	    				continue;
	    			}
	    		}
	    		
	    		if(index >= 0 && tn[index] == 1.0  ) {
	    			correct = correct + 1;
	    			corrects.add(x);
	    			System.out.print("Correct");
	    		}
	    		else {
	    			incorrects.add(x);
	    			wrong = wrong + 1;
	    			System.out.print("Incorrect");

	    		}
	    		
	    	}
	    	createChart(corrects , incorrects);
	    	System.out.println("Total examples= "+ N +"Correct = "+ correct );	
	    }
	
	    public void createChart(List<Double[]> corrects , List<Double[]> incorrects) {
			Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
			Shape diamond =ShapeUtilities.createDiamond(5);
			XYSeriesCollection clusterDataset = new XYSeriesCollection();
			XYSeriesCollection centroidDataset = new XYSeriesCollection();
			
	        XYSeries clusterSeries = new XYSeries("corrects");
	        XYSeries centroidSeries = new XYSeries("incorrect");
	        JFreeChart correctsPlot = ChartFactory.createScatterPlot("Number of correct results", "X","Y",clusterDataset,PlotOrientation.VERTICAL, false,false,false);
	        JFreeChart incorrectsPlot = ChartFactory.createScatterPlot("Number of incorrect results", "X","Y",centroidDataset,PlotOrientation.VERTICAL, false,false,false);
	        
	    
	    	for(Double[] x : corrects) {
	    		clusterSeries.add(x[0],x[1]);
	    	}
	        
	        //Change the shape of the points.
	        XYPlot plot = (XYPlot) correctsPlot.getPlot();
	        XYItemRenderer renderer = plot.getRenderer();
	        renderer.setSeriesShape(0, cross);
	        clusterDataset.addSeries(clusterSeries);
	        
	        for(Double[] x : incorrects) {
	        	centroidSeries.add(x[0],x[1]);
			}        
	        
	        plot = (XYPlot) incorrectsPlot.getPlot();
	        renderer = plot.getRenderer();
	        renderer.setSeriesShape(0, diamond);
	        centroidDataset.addSeries(centroidSeries);
	        
	        String filename1 = "resources/correct(3HiddenLayers).png";
	        String filename2 = "resources/incorrect(3HiddenLayers).png";
	        
	        try {
				ChartUtilities.saveChartAsPNG(new File(filename1), correctsPlot, 600, 400);
				ChartUtilities.saveChartAsPNG(new File(filename2), incorrectsPlot, 600, 400);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}//end of method.
	}//end of class