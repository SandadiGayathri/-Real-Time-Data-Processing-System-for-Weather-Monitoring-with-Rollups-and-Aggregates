import java.util.Map;

public class RuleEngineService {
    
    // Function to create an AST from a rule string
    public ASTNode createRule(String ruleString) {
        // Parse ruleString and build the AST
        // Example: "age > 30 AND department = 'Sales'"
        // For simplicity, assume this function handles simple AND/OR conditions.
        // You can expand this using a parser.
        // Parse it recursively and return the ASTNode root.
        
        // Example parsing:
        ASTNode ageCondition = new ASTNode("operand", "age > 30", null, null);
        ASTNode departmentCondition = new ASTNode("operand", "department = 'Sales'", null, null);
        return new ASTNode("operator", "AND", ageCondition, departmentCondition);
    }

    // Function to combine multiple ASTs
    public ASTNode combineRules(ASTNode[] rules) {
        // Combine the rules, e.g., using AND/OR operators
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
            return evaluateCondition(ast.getValue(), data); // Evaluate individual condition
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
        // Parse the condition and check against the data, e.g., "age > 30"
        String[] parts = condition.split(" ");
        String attribute = parts[0]; // e.g., "age"
        String operator = parts[1]; // e.g., ">"
        String value = parts[2]; // e.g., "30"

        if (data.containsKey(attribute)) {
            Object attrValue = data.get(attribute);
            // Compare the value based on the operator
            // This can be expanded to handle all operators
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
