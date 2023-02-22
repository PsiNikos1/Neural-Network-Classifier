import java.util.*;
import java.util.function.DoubleToLongFunction;

public class InputNeuron implements NeuronInterface{
    
    private List<Double> weight ; //Input layer neurons have 1 input so 2 weights,considering bias.
    private Double input;
    private final Double bias = 1.0;
    private  Double learningRate = 0.1;
    private  Double delta = 0.0;

    public InputNeuron(){
        initializeNeuron(0);
    }

   
    
    public void initializeNeuron(int notUsed){
        Random random = new Random();
        this.weight = new ArrayList<Double>();
        //Initialize weights.
        this.weight.add(-1 + (1 - (-1)) * random.nextDouble());//Add random weight.
        this.weight.add(-1 + (1 - (-1)) * random.nextDouble());//Last weight is for bias.
    }
    
    public Double neuronOutput(){
    	double scale = Math.pow(10, 3);
        Double sum = ( this.input * this.weight.get(0) ) + this.bias ; 
        return sum;
    }

    public void addInput(Double x){
        this.input=x;
    }

	@Override
	public List<Double> getWeights() {
		return this.weight;
	}

	@Override
	public void updateWeight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDerivative(Double[] d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInput(Double[] x) {
		// TODO Auto-generated method stub
		
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
