package com.rpm.pixelcat.kernel;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        configureBindings();
    }

    protected void configureBindings() {
        //bind(ResourceFileLoader.class).to(ResourceFileLoaderImpl.class).asEagerSingleton();
    }
}
