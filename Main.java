import java.util.List;

public class Main {

	public static void main(String[] args) {
		//Create MLP with 2 hidden layers.
		Double learningRate = 0.2;
		MLP  mlp  = new MLP(learningRate);
        mlp.createDataSets();
        
    	//Create MLP with 3 hidden layers.
        MLP2 mlp2 = new MLP2(learningRate);
        //mlp2.createDataSets();
        
        
        //Create input layer.
        InputNeuron inputNeuron0 = new InputNeuron();
        InputNeuron inputNeuron1 = new InputNeuron();
        mlp.addToInputNeuronList(inputNeuron0); mlp.addToInputNeuronList(inputNeuron1);
       
        //Create 1st hidden layer.
        HiddenNeuron HiddenNeuron0 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron1 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron2 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron3 = new HiddenNeuron(3);
        mlp.addToH1(HiddenNeuron0); mlp.addToH1(HiddenNeuron1); 
        mlp.addToH1(HiddenNeuron3); mlp.addToH1(HiddenNeuron2); 

        //Create 2nd hidden layer.
        HiddenNeuron HiddenNeuron4 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron5 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron6 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron7 = new HiddenNeuron(5);
        mlp.addToH2(HiddenNeuron4); mlp.addToH2(HiddenNeuron5); 
        mlp.addToH2(HiddenNeuron6); mlp.addToH2(HiddenNeuron7); 

        //Create output layer.
        OutputNeuron OutputNeuron1 = new OutputNeuron(5);
        OutputNeuron OutputNeuron2 = new OutputNeuron(5);
        OutputNeuron OutputNeuron3 = new OutputNeuron(5);
        OutputNeuron OutputNeuron4 = new OutputNeuron(5);
        mlp.addToOutputNeuronList(OutputNeuron1);mlp.addToOutputNeuronList(OutputNeuron2);
        mlp.addToOutputNeuronList(OutputNeuron3);mlp.addToOutputNeuronList(OutputNeuron4);
        
	

        //Create input layer.
        InputNeuron inputNeuron2 = new InputNeuron();
        InputNeuron inputNeuron3 = new InputNeuron();
        mlp2.addToInputNeuronList(inputNeuron2); mlp2.addToInputNeuronList(inputNeuron3);
        
        //Create 1st hidden layer.
        HiddenNeuron HiddenNeuron8 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron9 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron10 = new HiddenNeuron(3);
        HiddenNeuron HiddenNeuron11 = new HiddenNeuron(3);
        mlp2.addToH1(HiddenNeuron8); mlp2.addToH1(HiddenNeuron9); 
        mlp2.addToH1(HiddenNeuron10); mlp2.addToH1(HiddenNeuron11); 

        //Create 2nd hidden layer.
        HiddenNeuron HiddenNeuron12 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron13 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron14 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron15 = new HiddenNeuron(5);
        mlp2.addToH2(HiddenNeuron12); mlp2.addToH2(HiddenNeuron13); 
        mlp2.addToH2(HiddenNeuron14); mlp2.addToH2(HiddenNeuron15); 
        
      //Create 3rd hidden layer.
        HiddenNeuron HiddenNeuron16 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron17 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron18 = new HiddenNeuron(5);
        HiddenNeuron HiddenNeuron19 = new HiddenNeuron(5);
        mlp2.addToH3(HiddenNeuron16); mlp2.addToH3(HiddenNeuron17); 
        mlp2.addToH3(HiddenNeuron18); mlp2.addToH3(HiddenNeuron19); 
        
      //Create output layer.
        OutputNeuron OutputNeuron5 = new OutputNeuron(5);
        OutputNeuron OutputNeuron6 = new OutputNeuron(5);
        OutputNeuron OutputNeuron7 = new OutputNeuron(5);
        OutputNeuron OutputNeuron8 = new OutputNeuron(5);
        mlp2.addToOutputNeuronList(OutputNeuron5);mlp2.addToOutputNeuronList(OutputNeuron6);
        mlp2.addToOutputNeuronList(OutputNeuron7);mlp2.addToOutputNeuronList(OutputNeuron8);
        
        
        mlp.trainWithGradientDescent();//Train my network.
        mlp.runTest();//Run my test DataSet.
        
        
        System.out.print("----------MLP2----------");
        
        mlp2.setTrainSet(mlp.getTrainSet());
        mlp2.setTestSet(mlp.getTestSet());
        
        mlp2.trainWithGradientDescent();
        mlp2.runTest();
        
	}	
}
