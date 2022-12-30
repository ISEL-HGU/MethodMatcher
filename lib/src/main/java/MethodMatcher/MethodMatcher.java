package MethodMatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MethodMatcher {
    private ArrayList<Method> sourceList;
    private ArrayList<Method> classList;

    public void readSource(String path){
        ArrayList<Method> list = new ArrayList<>();
        BufferedReader r = null;
        try {
            String l;
            r = new BufferedReader(new FileReader(path));
            Method m = new Method();
            while ((l = r.readLine()) != null) {
                if(l.equals("")) continue;
                if(l.startsWith("$$METHOD#")){
                    int hashNum = Integer.parseInt(l.replace("$$METHOD#", "").trim());
                    m.setMethodHash(hashNum);
                }
                if(l.startsWith("$$METHOD_SIGNATURE:")){
                    String[] fileNameAndMethodSignature = l.replace("$$METHOD_SIGNATURE:", "")
                                                            .split(":");
                    m.setFileName(fileNameAndMethodSignature[0].split("\\.j")[0]);
                    m.setMethodSignature(fileNameAndMethodSignature[1]);
                    list.add(m);
                    m = new Method();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null)
                    r.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.sourceList = list;
    }

    public void readClass(String path){
        ArrayList<Method> list = new ArrayList<>();
        BufferedReader r = null;
        try {
            String l;
            r = new BufferedReader(new FileReader(path));
            Method m = new Method();
            while ((l = r.readLine()) != null) {
                if(l.equals("")) continue;
                if(l.startsWith("$$METHOD#")){
                    int hashNum = Integer.parseInt(l.replace("$$METHOD#", "").trim());
                    m.setMethodHash(hashNum);
                }
                if(l.startsWith("$$METHOD_SIGNATURE:")){
                    String[] fileNameAndMethodSignature = l.replace("$$METHOD_SIGNATURE:", "")
                            .split(":");
                    if(fileNameAndMethodSignature[0].contains("$")){
                        m.setFileName(fileNameAndMethodSignature[0].split("\\$")[0]);
                    } else {
                        m.setFileName(fileNameAndMethodSignature[0].split("\\.t")[0]);
                    }
                    if (!fileNameAndMethodSignature[1].startsWith("\"")) {
                        m.setMethodSignature(fileNameAndMethodSignature[1]);
                        list.add(m);
                    }
                    m = new Method();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null)
                    r.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.classList = list;
    }

    public void match(String outputPath){
        int numClassMethod = classList.size();
        int numSourceMethod = sourceList.size();
        Iterator<Method> classIterator = classList.iterator();
        MatchedWriter mw = new MatchedWriter();
        int cnt = 0;
        for(Method sm : sourceList){
            while(classIterator.hasNext()){
                Method cm = classIterator.next();
                //matched
                if(sm.getFileName().equals(cm.getFileName())
                    && sm.getMethodSignature().equals(cm.getMethodSignature())){
                    cnt++;
                    mw.writeMatched(sm, cm, cnt, outputPath);
                    classIterator.remove();
                    break;
                }
            }
            classIterator = classList.iterator();
        }
        System.out.println("Number of Src methods: " + numSourceMethod);
        System.out.println("Number of Class methods: " + numClassMethod);
    }
}
