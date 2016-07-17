package info.masterfrog.pixelcat.interpreter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Interpreter {
    protected static Interpreter instance;

    public static Interpreter getInstance() {
        if (instance == null) {
            instance = new Interpreter();
        }

        return instance;
    }

    /**
     * protected constructor for singleton
     */
    protected Interpreter() {
        // do nothing
    }

    public List<Expression> run(String filePath) throws Exception {
        // setup
        List<Expression> result;

        // initialize parser
        InputStream fileStream = ClassLoader.getSystemResourceAsStream(filePath);
        Parser p = new Parser(new Scanner(new InputStreamReader(fileStream)));

        // execute parser
        result = (List<Expression>) p.parse().value;

        return result;
    }
}
