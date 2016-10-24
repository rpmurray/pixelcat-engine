package info.masterfrog.pixelcat.engine.common.util;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.io.PrintStream;
import java.util.AbstractList;

public class ListBuilder<L extends AbstractList, V> {
    private L l;

    public ListBuilder(Class<L> l) throws TransientGameException {
        try {
            this.l = l.newInstance();
        } catch(Exception e) {
            throw new TransientGameException(GameEngineErrorCode.INTERNAL_ERROR, e);
        }
    }

    public ListBuilder(Class<L> l, PrintStream ps, boolean die) {
        try {
            this.l = l.newInstance();
        } catch (Exception e) {
            ps.println("Error: " + e);
            if (die) {
                System.exit(1);
            }
        }
    }

    public ListBuilder<L, V> add(V v) {
        l.add(v);

        return this;
    }

    public ListBuilder<L, V> add(int i, V v) {
        l.add(i, v);

        return this;
    }

    public L get() {
        return l;
    }
}
