package fakeNewsDetectorModel;

import weka.classifiers.Classifier;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import genDataNOapplication.RV.genPublicationsDV;

public class TextClassifier {

    private Classifier classifier;

    public TextClassifier(String modelPath) throws Exception {
        // Load the pre-trained model
        classifier = (Classifier) SerializationHelper.read(modelPath);
    }

    public String classifyText(String inputText) throws Exception {
        // Create an instance of Attribute for the input text
        Attribute textAttribute = new Attribute("text", (FastVector) null);

        // Create an instance of Attribute for the class (fake o real)
        FastVector classValues = new FastVector();
        classValues.addElement("fake");
        classValues.addElement("real");
        Attribute classAttribute = new Attribute("class", classValues);

        // Create an object Instances with the defined attributes
        FastVector attributes = new FastVector();
        attributes.addElement(textAttribute);
        attributes.addElement(classAttribute);
        Instances dataset = new Instances("TestInstances", attributes, 1);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Create a new instance and establish the value of the text attribute
        Instance instance = new DenseInstance(2);
        instance.setValue(textAttribute, inputText);
        instance.setDataset(dataset);

        // Classify the instance
        double prediction = classifier.classifyInstance(instance);

        // Obtain the assigned class
        String predictedClass = dataset.classAttribute().value((int) prediction);

        return predictedClass;
    }
    //Just to test that it works
    public static void main(String[] args) {
        try {
            // Route of the pre-trained model
            String modelPath = "./resources/Default_files/fakenewsmodel.model";

            // Input text to classify
            String inputText = "DAPL Protesters Proven Right as Largest Gas Pipeline in U.S. Experiences Massive and Deadly Explosion";
            // Create an object TextClassifier and load the model
            TextClassifier classifier = new TextClassifier(modelPath);

            // Classify the input text
            String predictedClass = classifier.classifyText(inputText);

            System.out.println("The news statement is: " + predictedClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
