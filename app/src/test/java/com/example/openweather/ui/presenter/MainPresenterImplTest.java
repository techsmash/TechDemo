package com.example.openweather.ui.presenter;

import com.example.openweather.RxTestBase;
import com.example.openweather.categories.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by kumar on 11/21/17.
 */

@Category(UnitTest.class)
public class MainPresenterImplTest extends RxTestBase {

    private MainPresenter presenter;

    @Mock
    MainView mainView;

    @Mock
    MainInteractor mainInteractor;

    @Before
    public void setup() {
        super.setUp();
        initMocks(this);
        presenter = new MainPresenterImpl(mainView, mainInteractor);
    }

    @Test
    public void testStartPresentingLoadFromFile() throws Exception {
        //1. Arrange
        presenter = spy(presenter);
        doReturn(false).when(mainView).isDatabaseExists();

        //2. Act
        presenter.startPresenting();

        //3. Assert
        verify(mainInteractor).loadFromFile();
    }

    @Test
    public void testStartPresentingNoLoadFromFile() throws Exception {
        //1. Arrange
        presenter = spy(presenter);
        doReturn(true).when(mainView).isDatabaseExists();

        //2. Act
        presenter.startPresenting();

        //3. Assert
        verifyNoMoreInteractions(mainInteractor);
    }
}
