package com.ruleengine;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleEngineController {
    
    private final RuleEngineService ruleEngineService;

    public RuleEngineController() {
        this.ruleEngineService = new RuleEngineService();
    }

    @PostMapping("/create")
    public ASTNode createRule(@RequestBody String ruleString) {
        return ruleEngineService.createRule(ruleString);
    }

    @PostMapping("/combine")
    public ASTNode combineRules(@RequestBody String[] rules) {
        ASTNode[] astRules = new ASTNode[rules.length];
        for (int i = 0; i < rules.length; i++) {
            astRules[i] = ruleEngineService.createRule(rules[i]);
        }
        return ruleEngineService.combineRules(astRules);
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody Map<String, Object> data, @RequestBody ASTNode ast) {
        return ruleEngineService.evaluateRule(ast, data);
    }
}
