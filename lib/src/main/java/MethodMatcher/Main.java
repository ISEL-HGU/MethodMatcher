/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package MethodMatcher;

public class Main {
    //input: 1) tokenized methods in source files, 2) tokenized methods in class files, 3) output path
    //output: a csv file (Header: file path, method signature, source hash, class hash, new hash
    //                      Body: file path, method signature, source hash number, class hash number, new hash number)
    //(example) ./RxJava/UTFWithoutComments.txt ./RXJava_OPCodes.txt ./RxJava
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        if(args.length != 3){
            System.err.println("//input: 1) tokenized methods in source files, 2) tokenized methods in class files, 3) output path\n" +
                    "    //output: a csv file (Header: file path, method signature, source hash, class hash, new hash\n" +
                    "    //                      Body: file path, method signature, source hash number, class hash number, new hash number)\n" +
                    "    //(example) ./RxJava/UTFWithoutComments.txt ./RXJava_OPCodes.txt ./RxJava");
            System.exit(-1);
        }

        String sourcePath = args[0];
        String classPath = args[1];
        String outputPath = args[2];

        MethodMatcher mm = new MethodMatcher();
        mm.readSource(sourcePath);
        mm.readClass(classPath);
        mm.match(outputPath);
        System.out.println("Running Time (sec): " + ((System.currentTimeMillis() - start) / 1000));
    }
}
