package com.ruleengine;

import java.util.Map;

public class RuleEngineService {

    // Function to create an AST from a rule string
    public ASTNode createRule(String ruleString) {
        // Parse ruleString and build the AST
        ASTNode ageCondition = new ASTNode("operand", "age > 30", null, null);
        ASTNode departmentCondition = new ASTNode("operand", "department = 'Sales'", null, null);
        return new ASTNode("operator", "AND", ageCondition, departmentCondition);
    }

    // Function to combine multiple ASTs
    public ASTNode combineRules(ASTNode[] rules) {
        if (rules.length == 0) return null;
        ASTNode combined = rules[0];

        for (int i = 1; i < rules.length; i++) {
            combined = new ASTNode("operator", "AND", combined, rules[i]);
        }
        return combined;
    }

    // Function to evaluate the rule against provided data
    public boolean evaluateRule(ASTNode ast, Map<String, Object> data) {
        if (ast == null) return false;

        if ("operand".equals(ast.getType())) {
            return evaluateCondition(ast.getValue(), data);
        } else if ("operator".equals(ast.getType())) {
            boolean leftEval = evaluateRule(ast.getLeft(), data);
            boolean rightEval = evaluateRule(ast.getRight(), data);

            if ("AND".equals(ast.getValue())) {
                return leftEval && rightEval;
            } else if ("OR".equals(ast.getValue())) {
                return leftEval || rightEval;
            }
        }
        return false;
    }

    // Helper function to evaluate a single condition like "age > 30"
    private boolean evaluateCondition(String condition, Map<String, Object> data) {
        String[] parts = condition.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2];

        if (data.containsKey(attribute)) {
            Object attrValue = data.get(attribute);
            if (operator.equals(">") && attrValue instanceof Integer) {
                return (Integer) attrValue > Integer.parseInt(value);
            }
            if (operator.equals("=") && attrValue instanceof String) {
                return attrValue.equals(value);
            }
        }
        return false;
    }
}
