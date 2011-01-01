/*
	Copyright 2010, Sumeet Chhetri 
  
    Licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 
    You may obtain a copy of the License at 
  
        http://www.apache.org/licenses/LICENSE-2.0 
  
    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.  
*/
package com.rules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class RTRGenerator 
{
	private static Map<String,Class[]> clsmp = new HashMap<String, Class[]>();
	
	public static void initialize(Rules rules)
	{
		String name = rules.getName();
		StringBuilder build = new StringBuilder();
		String args = "";
		List<Class> clss = new ArrayList<Class>();
		for (int i=0;i<rules.getObjs().length;i++) 
		{
			build.append("import "+rules.getObjs()[i].getClas()+";\n");
			args += rules.getObjs()[i].getClas()+ " " + rules.getObjs()[i].getName();
			try {
				clss.add(Class.forName(rules.getObjs()[i].getClas()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i!=rules.getObjs().length-1)
				args += ",";
		}
		clsmp.put(name, clss.toArray(new Class[clss.size()]));
		build.append("\n\npublic class "+name+"\n{\n");
		build.append("\tpublic void execute("+args+")\n\t{\n");
		boolean fl = false;
		for (Rule rule : rules.getRules()) 
		{			
			rule.setWhen(rule.getWhen().trim());
			String when = rule.getWhen();
			when = when.replaceAll(" and ", " && ");
			when = when.replaceAll(" or ", " || ");
			if(fl && rules.getType().equals("if"))
				build.append("\t\telse if("+when+")\n\t\t{\n");
			else
				build.append("\t\tif("+when+")\n\t\t{\n");			
			for (String then : rule.getThenArr())
			{
				if(!then.trim().equals(""))
					build.append("\t\t\t"+then.trim()+";\n");
			}
			build.append("\t\t}\n");
			fl = true;
		}
		build.append("\t}\n");
		build.append("}\n");
		System.out.println(build.toString());
		URL main = RTRGenerator.class.getResource("RTRGenerator.class");
		if (!"file".equalsIgnoreCase(main.getProtocol()))
		  throw new IllegalStateException("Main class is not stored in a file.");
		File path = new File(main.getPath());
		String path1 =main.getPath();
		path1 = path1.substring(0,path1.lastIndexOf("/"));
		path1 = path1.substring(0,path1.lastIndexOf("/"));
		path1 = path1.substring(0,path1.lastIndexOf("/"));
		System.out.println(path1);

		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(path1+"/"+name+".java"));
			bw.write(build.toString());
			bw.close();
			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			int compilationResult = compiler.run(null, null, null, path1+"/"+name+".java");
			if(compilationResult == 0){
				System.out.println("Compilation is successful");
			}else{
				System.out.println("Compilation Failed");
			}
			File file = new File(path1+"/"+name+".java");
			URL url = new URI("file://"+file.getAbsolutePath()).toURL();          // file:/c:/class/
	        URL[] urls = new URL[]{url};
	    
	        // Create a new class loader with the directory
	        ClassLoader loader = new URLClassLoader(urls);
	    
	        // Load in the class; Class.childclass should be located in
	        // the directory file:/c:/class/user/information
	        Class cls = loader.loadClass(name);
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void execute(String name,Object[] args)
	{
		try
		{
			Class clz = Class.forName(name);		
			Object ins = clz.newInstance();
			Method meth = clz.getMethod("execute", clsmp.get(name));
			meth.invoke(ins, args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg) throws Exception
	{
		Rules rules = RulesEngine.initialize(RTRGenerator.class.getResourceAsStream("/com/ItemRules.rl"));
	}
}
