# `ExampleRules.rl` #
```
<jerrules type="if" name="ExampleRules">
        <objs>
                <obj name="objA" clas="com.test.ObjA"/>
                <obj name="objB" clas="com.test.ObjB"/>
        </objs>
        <rules>
                <rule>          
                        <when>
                                objA.getSomeBooleanProperty()==true and objA.someBooleanMethod()
                        </when>
                        <then>
                                objB.setSomeTargetProperty("Hello World")
                                System.out.println("Laugh out loud")
                        </then>
                </rule>
                <rule>          
                        <when>
                                (objA.getSomeIntProperty()==12345 or objA.someOtherBooleanMethod()) and objA.getSomeStringProperty().equals("This is Easy")
                        </when>
                        <then>
                                objB.setSomeOtherTargetProperty(1)
                                com.test.ObjC objC = new com.test.ObjC()
                                objB.setSomeObject(objC)
                                System.out.println("Laugh out louder")
                        </then>
                </rule>
        </rules>
</jerrules>
```