package miniplc0java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import miniplc0java.analyser.Analyser;
import miniplc0java.error.CompileError;
import miniplc0java.tokenizer.StringIter;
import miniplc0java.tokenizer.Token;
import miniplc0java.tokenizer.TokenType;
import miniplc0java.tokenizer.Tokenizer;

public class App {

    public static void main(String[] args) throws CompileError, IOException {
        System.out.println( (( 1  & 0xFF )));
        InputStream input;
        PrintStream output;
        DataOutputStream out;

        input = new FileInputStream(new File("C:\\Users\\鸡蛋酱\\IdeaProjects\\miniplc0-java\\src\\main\\java\\miniplc0java\\input.txt"));
        output = new PrintStream(new FileOutputStream(new File("C:\\Users\\鸡蛋酱\\IdeaProjects\\miniplc0-java\\src\\main\\java\\miniplc0java\\output.txt")));
        out=new DataOutputStream(new FileOutputStream(new File("C:\\Users\\鸡蛋酱\\IdeaProjects\\miniplc0-java\\src\\main\\java\\miniplc0java\\output.txt")));
        Scanner scanner;
        scanner = new Scanner(input);
        var iter = new StringIter(scanner);
        var tokenizer = tokenize(iter);
        String s=args[0];
        if (s.equals("t")) {
            // tokenize
            var tokens = new ArrayList<Token>();
            try {
                while (true) {
                    var token = tokenizer.nextToken();
                    if (token.getTokenType().equals(TokenType.EOF)) {
                        break;
                    }
                    tokens.add(token);
                }
            } catch (Exception e) {
                // 遇到错误不输出，直接退出
                System.err.println(e);
                System.exit(0);
                return;
            }
            for (Token token : tokens) {
                output.println(token.toString());
            }
        } else if (s.equals("l")) {
            // analyze
            var analyzer = new Analyser(tokenizer);
            Table table;
            List<FunctionTable> functionTables;
            List<Token> global;
            table=analyzer.analyse();
            table.generate();

            global=table.getGlobal();
            functionTables=table.getFunctionTables();
            for(Token token:global){
                output.println(token);
            }
            for (FunctionTable function:functionTables){
                output.println(function.getName()+" pos:"+function.getPos()+" params:"+function.getParamSoltNum()+" var:"+function.getVarSoltNmum()+" -> "+function.getReturnSoltNmum());
                output.println(function.getInstructions());
            }
        } else if(s.equals("tb")){
            // analyze
            var analyzer = new Analyser(tokenizer);
            Table table;
            List<FunctionTable> functionTables;
            List<Token> global;
            table=analyzer.analyse();
            table.generate();

            OutPutBinary outPutBinary=new OutPutBinary(table);
            List<Byte> bs=outPutBinary.generate();
            output.println(bs);
        }else if(s.equals("b")){
            // analyze
            var analyzer = new Analyser(tokenizer);
            Table table;
            List<FunctionTable> functionTables;
            List<Token> global;
            table=analyzer.analyse();
            table.generate();

            OutPutBinary outPutBinary=new OutPutBinary(table);
            List<Byte> bs=outPutBinary.generate();
            byte[] temp=new byte[bs.size()];
            for(int i=0;i<bs.size();i++)
                temp[i]=bs.get(i);
            out.write(temp);
        }
        else{
                System.err.println("Please specify either '--analyse' or '--tokenize'.");
                System.exit(3);
            }
    }



    private static Tokenizer tokenize(StringIter iter) {
        var tokenizer = new Tokenizer(iter);
        return tokenizer;
    }
}
