import java.util.List;

public interface NeuronInterface {
    
    public void initializeNeuron(int layer);
    
    public Double neuronOutput();

    public void addInput(Double[] x);

    public void updateWeight();
    
    public List<Double> getWeights();

    public void addDerivative(Double[] d);
    
    public Double getDelta();
    
    public void setLeanringRate(Double LearingRate);
    
    public void setDelta(Double delta);
}
