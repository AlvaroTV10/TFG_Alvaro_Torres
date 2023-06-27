package fakeNewsDetectorModel;

import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SGDText;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class TextClassifierTrainer {

    public void trainModel(String trainingFilePath, String modelFilePath) throws Exception {
        // Load the set of training data
        Instances trainingData = new Instances(new FileReader(trainingFilePath));
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        // Create an SGDText classifier
        Classifier classifier = new SGDText();

        //  Train the model using the set of training data
        classifier.buildClassifier(trainingData);

        // Save the trained model
        SerializationHelper.write(modelFilePath, classifier);

        // Evaluate the model using cross validation
        Evaluation evaluation = new Evaluation(trainingData);
        evaluation.crossValidateModel(classifier, trainingData, 10, new Random(1));
        System.out.println(evaluation.toSummaryString());
    }

    public static void main(String[] args) {
        try {
            // Route of the training data doc (in ARFF format)
            String trainingFilePath = "./resources/Default_files/data.arff";

            // Route to save the trained model
            String modelFilePath = "./resources/Default_files/fakenewsmodel.model";

            // Create an TextClassifierTrainer object
            TextClassifierTrainer trainer = new TextClassifierTrainer();

            // Train the model and save it
            System.out.println("Training the model...");
            trainer.trainModel(trainingFilePath, modelFilePath);

            System.out.println("The model has been trained and saved correctly");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
