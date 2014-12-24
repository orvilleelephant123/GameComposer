package de.mirkosertic.gameengine.core;

import de.mirkosertic.gameengine.event.GameEventManager;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameRuntimeTest {

    @Test
    public void testGetResourceCache() throws Exception {
        GameEventManager theEventManager = mock(GameEventManager.class);
        GameResourceLoader theResourceLoader = mock(GameResourceLoader.class);
        ExpressionParserFactory theExFactory = mock(ExpressionParserFactory.class);
        GameRuntime theRuntime = new GameRuntime(theEventManager, theResourceLoader, theExFactory);
        assertNotNull(theRuntime.getResourceCache());
    }

    @Test
    public void testGetEventManager() throws Exception {
        GameEventManager theEventManager = mock(GameEventManager.class);
        GameResourceLoader theResourceLoader = mock(GameResourceLoader.class);
        ExpressionParserFactory theExFactory = mock(ExpressionParserFactory.class);
        GameRuntime theRuntime = new GameRuntime(theEventManager, theResourceLoader, theExFactory);
        assertSame(theEventManager, theRuntime.getEventManager());
    }

    @Test
    public void testGetExpressionParserFactory() throws Exception {
        GameEventManager theEventManager = mock(GameEventManager.class);
        GameResourceLoader theResourceLoader = mock(GameResourceLoader.class);
        ExpressionParserFactory theExFactory = mock(ExpressionParserFactory.class);
        GameRuntime theRuntime = new GameRuntime(theEventManager, theResourceLoader, theExFactory);
        assertSame(theExFactory, theRuntime.getExpressionParserFactory());
    }

    @Test
    public void testAddSystem() throws Exception {
        GameEventManager theEventManager = mock(GameEventManager.class);
        GameResourceLoader theResourceLoader = mock(GameResourceLoader.class);
        ExpressionParserFactory theExFactory = mock(ExpressionParserFactory.class);
        GameRuntime theRuntime = new GameRuntime(theEventManager, theResourceLoader, theExFactory);
        assertNotNull(theRuntime.getSystems());
        assertEquals(0, theRuntime.getSystems().length);
        GameSystem theSystem = mock(GameSystem.class);
        theRuntime.addSystem(theSystem);
        assertEquals(1, theRuntime.getSystems().length);
        assertSame(theSystem, theRuntime.getSystems()[0]);
    }

    @Test
    public void testGetIORegistry() throws Exception {
        GameEventManager theEventManager = mock(GameEventManager.class);
        GameResourceLoader theResourceLoader = mock(GameResourceLoader.class);
        ExpressionParserFactory theExFactory = mock(ExpressionParserFactory.class);
        GameRuntime theRuntime = new GameRuntime(theEventManager, theResourceLoader, theExFactory);
        assertNotNull(theRuntime.getIORegistry());
    }
}