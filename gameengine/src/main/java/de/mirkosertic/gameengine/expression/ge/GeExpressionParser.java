package de.mirkosertic.gameengine.expression.ge;

import de.mirkosertic.gameengine.core.ExpressionParser;
import de.mirkosertic.gameengine.event.PropertyAware;
import de.mirkosertic.gameengine.type.DefaultValueProvider;
import de.mirkosertic.gameengine.type.ValueProvider;

import java.util.HashMap;
import java.util.Map;

public class GeExpressionParser implements ExpressionParser, VariableResolver {

    private final Map<String, ValueProvider> variables;
    private final Token[] rpnTokens;
    private final FunctionRegistry functionRegistry;

    GeExpressionParser(Token[] aRPNTokens, FunctionRegistry aFunctionRegistry) {
        rpnTokens = aRPNTokens;
        variables = new HashMap<String, ValueProvider>();
        functionRegistry = aFunctionRegistry;
    }

    @Override
    public void registerVariable(String aVariableName, PropertyAware aPropertyAware) {
        registerVariable(aVariableName, new DefaultValueProvider(aPropertyAware));
    }

    @Override
    public <T> void registerVariable(String aVariableName, ValueProvider<T> aValueProvider) {
        variables.put(aVariableName, aValueProvider);
    }

    @Override
    public String evaluateToString() {
        Object theResult = evaluateToObject();
        return theResult.toString();
    }

    @Override
    public Object evaluateToObject() {
        RPNEvaluator theEvaluator = new RPNEvaluator(this, functionRegistry);
        return theEvaluator.evaluate(rpnTokens);
    }

    @Override
    public Object resolveVariable(String aVariableName) {
        ValueProvider theValueProvider = variables.get(aVariableName);
        if (theValueProvider != null) {
            return theValueProvider.get();
        }
        return null;
    }
}