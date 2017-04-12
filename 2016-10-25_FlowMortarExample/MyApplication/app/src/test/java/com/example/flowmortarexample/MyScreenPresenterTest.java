package com.example.flowmortarexample;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mortar.MortarScope;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by MyndDev on 11/7/2016.
 */

public class MyScreenPresenterTest {

    private MyScreen.Presenter _presenter;

    @Mock private SomeAsyncService _mockSomeAsyncService;
    @Mock private MyView _mockView;

    @Mock Context _mockContext;
    @Mock MortarScope _mockMortarScope;

    @Before
    public void Init() {
        MockitoAnnotations.initMocks(this);

        given(_mockContext.getSystemService("mortar_scope")).willReturn(_mockMortarScope);
        given(_mockView.getContext()).willReturn(_mockContext);

        _presenter = new MyScreen.Presenter(_mockSomeAsyncService);
        _presenter.takeView(_mockView);
    }

    @Test
    public void whenButtonIsClicked_soSomethingMethodOfServiceIsCalled() {
        //arrange
        when(_mockView.getSomeText()).thenReturn("some text");

        //act
        _presenter.someButtonClicked();

        //assert
        verify(_mockSomeAsyncService).doSomething("some text");
    }

}
