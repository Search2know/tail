import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;


public class tail {
    @Option(name = "-c", usage = "last chars in file", forbids = {"-n"})
    private int chars = -1;

    @Option(name = "-n", usage = "last strings in file", forbids = {"-c"})
    private int files = -1;

    @Option(name = "-o", usage = "Output name")
    private String output;

    @Argument(usage = "Input name")
    private ArrayList<String> input;


    public static void main(String[] args) throws IOException {
        new tail().start(args);
    }

    private void start(String[] args) throws IOException {
        CmdLineParser parse = new CmdLineParser(this);
        try {
            parse.parseArgument(args);
        } catch (CmdLineException exc) {
            System.err.println(exc.getMessage());
            System.err.println("java -jar tail.jar  -c|-n num -o basicOutputName inputFileName1 inputFileName2 ...");
            parse.printUsage(System.err);
        }
        if (input.size() != 0) {
            if (chars != -1)
                splitterC(input, chars, output);
            else if (files != -1)
                splitterN(input, files, output);
            if (chars == -1 && files == -1) {
                files = 10;
                splitterN(input, files, output);
            }

        }

    }

    private static void splitterC(ArrayList<String> inputter, int count, String output) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(output, false)));
        for (String anInputter : inputter) {
            BufferedReader buf = new BufferedReader(new FileReader(new File(anInputter)));
            int charser;
            ArrayList<Character> list0fList = new ArrayList<Character>();
            do {
                charser = buf.read();
                list0fList.add((char) charser);
            }
            while (charser != -1);
            for (int j = list0fList.size() - 1 - count; j < list0fList.size() - 1; j++) {

                writer.write(list0fList.get(j));
            }
        }
        writer.close();
    }

    private static void splitterN(ArrayList<String> inputter, int count, String output) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(output, false)));
        for (String anInputter : inputter) {
            BufferedReader buf = new BufferedReader(new FileReader(new File(anInputter)));
            String liner;
            ArrayList<String> list0fList = new ArrayList<String>();
            do {
                liner = buf.readLine();
                list0fList.add(liner);
            }
            while (liner != null);
            for (int j = list0fList.size() - 1 - count; j < list0fList.size() - 1; j++) {

                writer.write(list0fList.get(j));
            }
        }
        writer.close();
    }

}
