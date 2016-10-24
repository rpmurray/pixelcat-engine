package info.masterfrog.pixelcat.engine.common.util;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.io.PrintStream;
import java.util.AbstractSet;

public class SetBuilder<S extends AbstractSet, V> {
    private S s;

    public SetBuilder(Class<S> s) throws TransientGameException {
        try {
            this.s = s.newInstance();
        } catch(Exception e) {
            throw new TransientGameException(GameEngineErrorCode.INTERNAL_ERROR, e);
        }
    }

    public SetBuilder(Class<S> s, PrintStream ps, boolean die) {
        try {
            this.s = s.newInstance();
        } catch (Exception e) {
            ps.println("Error: " + e);
            if (die) {
                System.exit(1);
            }
        }
    }

    public SetBuilder<S, V> add(V v) {
        s.add(v);

        return this;
    }

    public S get() {
        return s;
    }
}
