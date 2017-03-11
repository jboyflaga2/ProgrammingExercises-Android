package com.example.flowmortarexample;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;

/**
 * Created by MyndDev on 10/26/2016.
 */
@Layout(R.layout.my_view)
public class MyScreen implements Blueprint {

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(injects = MyView.class)
    public class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<MyView> {
        private final SomeAsyncService service;

        private SomeResult lastResult;

        @Inject
        public Presenter(SomeAsyncService service) {
            this.service = service;
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            if (lastResult == null && savedInstanceState != null) {
                lastResult = savedInstanceState.getParcelable("last");
            }
            updateView();
        }

        @Override
        public void onSave(Bundle outState) {
            if (lastResult != null) outState.putParcelable("last", lastResult);
        }

        public void someButtonClicked() {
//            service.doSomethingAsync(getView().getSomeText(),
//                    new AsyncCallback<SomeResult>() {
//                        public void onResult(SomeResult result) {
//                            lastResult = result;
//                            if (getView() != null) updateView();
//                        }
//                    });
//

            service.doSomething(getView().getSomeText());

            lastResult = new SomeResult(getView().getSomeText());
            updateView();
        }

        private void updateView() {
            if(lastResult != null) {
                getView().showResult(lastResult);
            }
        }
    }
}