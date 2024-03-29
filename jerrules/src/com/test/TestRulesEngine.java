package com.test;

import com.rules.Rules;
import com.rules.RulesEngine;
import java.util.List;
import java.util.ArrayList;

public class TestRulesEngine
{
    public static void main(String[] args)
    {
        List objs = new ArrayList();

        //application specific code
        com.test.ObjA objA = new com.test.ObjA();
        com.test.ObjB objB = new com.test.ObjB();   

        //the order of the arguments in the list is important
        //the list contains the bound objects as defined in the rules                   
        objs.add(objA);
        objs.add(objB);

        //now create the Rules object required by the Rules Engine
        //in this example a classpath resource file is considered 
        Rules rules = RulesEngine.initialize("rules/ExampleRules.rl");

        //now execute the rules
        RulesEngine.executeRTC(rules,objs);
    }
}
