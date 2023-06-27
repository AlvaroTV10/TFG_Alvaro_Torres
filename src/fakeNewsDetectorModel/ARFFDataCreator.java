package fakeNewsDetectorModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import genDataNOapplication.RV.genPublicationsDV;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import weka.core.Instances;
import java.io.*;
import weka.classifiers.functions.SGDText;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;


public class ARFFDataCreator {

    public void createTrainingData(String filePath, String csvFile) throws IOException {
        // Route of the ARFF doc to create
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        // Write the headline of the ARFF doc
        writer.write("@relation TrainingData\n\n");

        // Define the attributes
        writer.write("@attribute text string\n");
        writer.write("@attribute class {FAKE, REAL}\n\n");

        // Write the data
        writer.write("@data\n");
        
        
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                Pattern pattern = Pattern.compile("\"(.*?)\"");
	        	Matcher matcher = pattern.matcher(row[0]);
	        	String text = matcher.replaceAll("$1");
                writer.write("\""+text+"\"");
                writer.write(", "+row[1]+"\n");
            }
        } catch (IOException e) {
                e.printStackTrace();
            }

        // Close the doc
        writer.close();

        System.out.println("The ARFF document has been created correctly at: " + filePath);
    }

    public static void main(String[] args) throws Exception {
        try {        	
            // Route of the ARFF doc to create
            String filePath = "./resources/Default_files/data.arff";
            String csvFile = "./resources/Default_files/fakenews_train.csv";

            // Create an ARFFDataCreator object
            ARFFDataCreator dataCreator = new ARFFDataCreator();

            // Create a the training data doc in ARFF format
            dataCreator.createTrainingData(filePath, csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
