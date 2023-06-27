package fakeNewsDetectorModel;

import java.io.FileReader;
import java.util.Arrays;

import weka.classifiers.Evaluation;
import weka.core.Instances;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SGDText;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.SerializationHelper;

import fakeNewsDetectorModel.ARFFDataCreator;



public class TextClasssifierTester {
	
	
	 public static void main(String[] args) {
	 try {
		String modelPath = "./resources/Default_files/fakenewsmodel.model";
		Classifier model = (Classifier) SerializationHelper.read(modelPath);
		String trainingFilePath = "./resources/Default_files/data_test.arff";
		String csvFile = "./resources/Default_files/fakenews_test.csv";
		
		ARFFDataCreator dataCreator = new ARFFDataCreator();

        // Create a the training data doc in ARFF format
        dataCreator.createTrainingData(trainingFilePath, csvFile);
        
		Instances trueLabels = new Instances(new FileReader(trainingFilePath));
		trueLabels.setClassIndex(trueLabels.numAttributes() - 1);
			
		//Evaluate the model
		Evaluation evaluation = new Evaluation(trueLabels);
		evaluation.evaluateModel(model, trueLabels);
		//Print evaluation metrics
		System.out.println("Accuracy: " + evaluation.pctCorrect());
		System.out.println("Precision: " + evaluation.precision(0));
		System.out.println("Recall: " + evaluation.recall(0));
		System.out.println("F1-Score: " + evaluation.fMeasure(0));
		System.out.println("Confusion Matrix:\n"); 
		double mat[][] = evaluation.confusionMatrix();
		for (double[] row : mat)   System.out.println(Arrays.toString(row));
		
	 } catch (Exception e) {
         e.printStackTrace();
     }
	
		
	 }

}
