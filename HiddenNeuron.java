import java.util.*;
import java.lang.Math;

public class HiddenNeuron implements NeuronInterface {
    
    private List<Double> weights ; //Hidden layer neurons have 2 inputs so 3 weights,considering polarity.
    private Double[] derivatives;
    private List<Double> inputs;
    private final Double polarity = 1.0;
    private  Double learningRate = 0.1;
    private  Double delta = 0.0;
    private int canFireFlag;
    
    //All hiddens layers neurons have tanh as activation function.

    public HiddenNeuron(int inputNum){
        initializeNeuron( inputNum);
    }

    public int getInpSize() {
    	return this.inputs.size();
    }
    
    public Double[] getDerivatives(){
    	return this.derivatives;
    }
    
    public void initializeNeuron(int inputNum){
        Random random = new Random();
        this.canFireFlag = 1;
        this.weights = new ArrayList<Double>();
        this.inputs = new ArrayList<Double>();
        this.derivatives = new Double[inputNum];
        //Initialize derivatives.
        for(int input=0; input<inputNum; input++) {
        	
     	   this.derivatives[input]=0.0;
        }

        //Initialize random weights.
        for(int input=0; input<inputNum; input++) {
     	   this.weights.add(-1 + (1 - (-1)) * random.nextDouble());
        }//Last weight is for bias.
    }
    
    public Double neuronOutput(){
        if(this.canFireFlag == 1){
            
            Double sum = 0.0;
            for(int i=0; i<this.inputs.size(); i++){
            // System.out.println("\nInput: "+this.inputs.get(i)+"\tWeight: "+ this.weights.get(i));
                sum = sum + this.inputs.get(i) * this.weights.get(i);
            }
            sum = sum + this.polarity;
            return Math.tanh(sum);
        }
        else{
            return 0.0; //TODO change this 'cause we dont know what the neuron returns if it cant fire.
        }
    }

    public void addInput(Double[] x){
        this.inputs.clear();
        for(Double xi : x) {
        	this.inputs.add(xi);
        }
    }
    
    public void updateWeight(){
       List<Double> newWeights = new ArrayList<Double>();
       

       for(int i=0; i<this.weights.size(); i++) {
    	   //System.out.println("Size "+ this.weights.size());
    	   //System.out.println("Size der "+ this.derivatives.length );
       	newWeights.add(this.weights.get(i) - ( this.learningRate * this.derivatives[i]));
       }
       this.weights.clear();
       for(Double newW : newWeights ) {
       	this.weights.add(newW);
       }
   }

	@Override
	public List<Double> getWeights() {
		return this.weights;
	}

	@Override
	public void addDerivative(Double[]  newDerivatives) {
	
		//System.out.println("new "+newDerivatives.length);
		//System.out.println("old "+this.derivatives.length);
		for(int i=0; i<this.derivatives.length; i++) {
			
			this.derivatives[i] =this.derivatives[i] +  newDerivatives[i];
			}
	}

	public void clearDerivatives() {
    	int d = this.derivatives.length;
    	this.derivatives = new Double[d];
    	for(int i=0; i <d; i++) {
    		this.derivatives[i] = 0.0;
    	}
    }

	@Override
	public Double getDelta() {
		return this.delta;
	}

	@Override
	public void setDelta(Double delta) {
		this.delta = delta;		
	}

	@Override
	public void setLeanringRate(Double learningRate) {
		this.learningRate = learningRate;
	}
	
}
