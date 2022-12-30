package MethodMatcher;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MatchedWriter {
    public void writeMatched(Method sourceMethod, Method classMethod, int newKey, String outputPath) {
        if(outputPath.endsWith("/")){
            outputPath = outputPath.substring(0, outputPath.length() -1);
        }
        File f = new File(outputPath + "_matched.csv");
        if(f.exists()){
            try (CSVPrinter printer = new CSVPrinter(new FileWriter(f, true), CSVFormat.DEFAULT)) {
                printer.printRecord(sourceMethod.getFileName(), sourceMethod.getMethodSignature(),
                        sourceMethod.getMethodHash(), classMethod.getMethodHash(), "" + newKey);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try (CSVPrinter printer = new CSVPrinter(new FileWriter(f, true), CSVFormat.DEFAULT
                    .withHeader("file path", "method signature", "source hash", "class hash", "new hash"))) {
                printer.printRecord(sourceMethod.getFileName(), sourceMethod.getMethodSignature(),
                        sourceMethod.getMethodHash(), classMethod.getMethodHash(), "" + newKey);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}