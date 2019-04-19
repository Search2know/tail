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

    public void start(String[] args) throws IOException {
        CmdLineParser parse = new CmdLineParser(this);
        try {
            parse.parseArgument(args);
        } catch (CmdLineException exc) {
            System.err.println(exc.getMessage());
            System.err.println("java -jar tail.jar  -c|-n num -o basicOutputName inputFileName1 inputFileName2 ...");
            parse.printUsage(System.err);
        }
        if (input.size() == 1) {
            if (chars != -1)
                tail.splitterC(input, chars, output);
            else if (files != -1)
                tail.splitterN(input, files, output);

        }
    }

    public static void splitterC(ArrayList<String> inputter, int count, String output) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(output, true), "UTF-8"));
        for (int i = 0; i <= inputter.size() - 1; i++) {
        BufferedReader buf = new BufferedReader(new FileReader(new File(inputter.get(i))));
        int charser = 0;
        ArrayList<Character> list0fList = new ArrayList<Character>();
        do {
            charser = buf.read();
            list0fList.add((char) charser);
        }
        while (charser != -1);
        for (int j = list0fList.size() - 1 - count;j < list0fList.size() - 1; j++) {

         writer.write((char) charser);

        }

        }
        writer.close();
    }

    public static void splitterN(ArrayList<String> inputter, int count, String output) throws IOException {
        for (int i = 0; i <= inputter.size() - 1; i++) {
        BufferedReader buf = new BufferedReader(new FileReader(new File(inputter.get(i))));
        String liner;
        ArrayList<String> list0fList = new ArrayList<String>();
        do {
            liner = buf.readLine();
            list0fList.add(liner);
        }
        while (liner != null);
        OutputStream os = null;
        for (int j = list0fList.size() - 1 - count; j < list0fList.size() - 1; j++) {

            os = new FileOutputStream(new File(output), true);
            os.write(list0fList.get(j).getBytes(), 0, list0fList.get(j).length());
        }
            os.close();
    }
    }

}
