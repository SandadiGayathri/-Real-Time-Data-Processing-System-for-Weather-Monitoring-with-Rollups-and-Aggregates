import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleEngineController {
    
    private final RuleEngineService ruleEngineService;

    public RuleEngineController(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    // API to create a rule
    @PostMapping("/create")
    public ASTNode createRule(@RequestBody String ruleString) {
        return ruleEngineService.createRule(ruleString);
    }

    // API to combine multiple rules
    @PostMapping("/combine")
    public ASTNode combineRules(@RequestBody String[] rules) {
        ASTNode[] astRules = new ASTNode[rules.length];
        for (int i = 0; i < rules.length; i++) {
            astRules[i] = ruleEngineService.createRule(rules[i]);
        }
        return ruleEngineService.combineRules(astRules);
    }

    // API to evaluate a rule with data
    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody Map<String, Object> data, @RequestBody ASTNode ast) {
        return ruleEngineService.evaluateRule(ast, data);
    }
}
