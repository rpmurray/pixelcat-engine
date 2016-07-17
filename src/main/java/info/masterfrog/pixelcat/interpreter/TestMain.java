package info.masterfrog.pixelcat.interpreter;

import info.masterfrog.pixelcat.engine.common.printer.Printer;
import org.apache.log4j.Level;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        // init debug
        Printer.setLevel(Level.ERROR);

        // fetch filename
        String fileName = args[0];
        String filePath = "scripts/" + fileName;

        // run interpreter
        List<Expression> interpretedResult = null;
        try {
            interpretedResult = Interpreter.getInstance().run(filePath);
        } catch (Exception e) {
            System.out.println("Interpreter aborting due to error!\nException:\n" + e);
            System.exit(1);
        }

        // print result
        System.out.println("Interpreter execution completed successfully!\nInterpreted result:\n" + interpretedResult);

        // run evaluator
        List<Numeric> evaluatedResult = null;
        try {
            evaluatedResult = Evaluator.getInstance().run(interpretedResult);
        } catch (Exception e) {
            System.out.println("Interpreter aborting due to error!\nException:\n" + e);
            System.exit(1);
        }

        System.out.println("Evaluator execution completed successfully!\nEvaluated result:\n" + evaluatedResult);
    }
}
