import java.math.BigDecimal;
import java.util.*;

public class OutputNeuron implements NeuronInterface {

    //Fields.
    private List<Double> weights ; //1st hidden layer neurons have 2 inputs so 3 weights,considering polarity.2nd hidden layer neurons have 4 inputs,so 5 weights.
    private Double[] derivatives;
    private List<Double> inputs;
    private final Double bias = 1.0;
    private  Double learningRate = 0.1;
    private  Double delta = 0.0;
    private int canFireFlag;
    
    public Double[] getDerivatives(){
    	return this.derivatives;
    }
       
    public OutputNeuron(int inputNum){
        initializeNeuron( inputNum);
    }

    public void initializeNeuron(int inputNum){
        Random random = new Random();
        this.canFireFlag = 1;
        this.derivatives = new Double[inputNum];
        this.weights = new ArrayList<Double>();
        this.inputs = new ArrayList<Double>();
        
        //Initialize derivatives.
       for(int input=0; input<inputNum; input++) {
    	   this.derivatives[input]=0.0;
       }

        //Initialize random weights.
       for(int input=0; input<inputNum; input++) {
    	   this.weights.add(-1 + (1 - (-1)) * random.nextDouble());
       }//Last weight is for bias.
       
    }
    
    public Double getDelta() {
    	return this.delta;
    }
    
    public Double neuronOutput(){
        if(this.canFireFlag == 1){
            
            Double sum = 0.0;
            for(int i=0; i<this.inputs.size(); i++){
            	//System.out.println("\nInput "+""+this.inputs.get(i)+"\tWeight: "+ this.weights.get(i));
                sum = sum + this.inputs.get(i) * this.weights.get(i);
            }
            sum = sum + this.bias*this.weights.get(2);
        	//System.out.println(BigDecimal.valueOf( logisticFunction(sum)).toPlainString());

            return logisticFunction(sum);
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
        	newWeights.add(this.weights.get(i) - ( this.learningRate * this.derivatives[i]));
        }
        this.weights.clear();
        for(Double newW : newWeights ) {
        	this.weights.add(newW);
        }
    }

    private Double logisticFunction(Double x){
        Double p = 1.0 + Math.exp(-x);
        return 1.0 / p;
    }
    
    public List<Double> getWeights(){
    	return this.weights;
    }

    public void clearDerivatives() {
    	int d = this.derivatives.length;
    	this.derivatives = new Double[d];
    	for(int i=0; i <d; i++) {
    		this.derivatives[i] = 0.0;
    	}
    }
    
	@Override
	public void addDerivative(Double[]  newDerivatives) {
		for(int i=0; i<this.derivatives.length; i++) {
			this.derivatives[i] = this.derivatives[i] +   newDerivatives[i];
		}
	}

	@Override
	public void setDelta(Double delta) {
		this.delta = delta;
	}

	@Override
	public void setLeanringRate(Double learningRate) {
		this.learningRate = learningRate;
	}
    
}//end of class
