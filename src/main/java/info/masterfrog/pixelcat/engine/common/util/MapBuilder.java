package info.masterfrog.pixelcat.engine.common.util;

import info.masterfrog.pixelcat.engine.exception.GameEngineErrorCode;
import info.masterfrog.pixelcat.engine.exception.TransientGameException;

import java.io.PrintStream;
import java.util.AbstractMap;

public class MapBuilder<M extends AbstractMap, K, V> {
    private M m;

    public MapBuilder(Class<M> m) throws TransientGameException {
        try {
            this.m = m.newInstance();
        } catch (Exception e) {
            throw new TransientGameException(GameEngineErrorCode.INTERNAL_ERROR, e);
        }
    }

    public MapBuilder(Class<M> m, PrintStream ps, boolean die) {
        try {
            this.m = m.newInstance();
        } catch (Exception e) {
            ps.println("Error: " + e);
            if (die) {
                System.exit(1);
            }
        }
    }

    public MapBuilder<M, K, V> add(K k, V v) {
        m.put(k, v);

        return this;
    }

    public M get() {
        return m;
    }
}
