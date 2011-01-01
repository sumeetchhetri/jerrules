package com.rules;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RulesEngine 
{
	
	public static Rules initialize( String rulesFile)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("brules", Rules.class);
		xstream.alias("rules", Rule[].class);
		xstream.alias("rule", Rule.class);
		xstream.alias("objs", obj[].class);
		xstream.alias("obj", obj.class);
		xstream.processAnnotations(obj.class);
		xstream.processAnnotations(Rule.class);
		xstream.processAnnotations(Rules.class);
		Rules rules = null;
		try {
			rules = (Rules)xstream.fromXML(new FileInputStream(rulesFile));
			RTRGenerator.initialize(rules);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rules;
	}
	
	public static Rules initialize(InputStream rulesFileIs)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("brules", Rules.class);
		xstream.alias("rules", Rule[].class);
		xstream.alias("rule", Rule.class);
		xstream.alias("objs", obj[].class);
		xstream.alias("obj", obj.class);
		xstream.processAnnotations(obj.class);
		xstream.processAnnotations(Rule.class);
		xstream.processAnnotations(Rules.class);
		Rules rules = (Rules)xstream.fromXML(rulesFileIs);
		RTRGenerator.initialize(rules);
		return rules;
	}
	
	public static void executeRTC(Rules rules, List objects) 
	{
		RTRGenerator.execute(rules.getName(), objects.toArray(new Object[objects.size()]));
	}
	
	public static void execute(Rules rules, List objects) 
	{
		Map context = new HashMap();
		for (Object object : objects) 
		{
			for (obj obj : rules.getObjs()) 
			{
				if(object.getClass().getCanonicalName().equals(obj.getClas()))
				{
					context.put(obj.getName(), object);
				}
			}			
		}
		for (Rule rule : rules.getRules()) 
		{			
			rule.setWhen(rule.getWhen().trim());
			try
			{
				if(evaluateWhen(context, rule.getWhen()))
				{
					for (String then : rule.getThenArr())
					{
						if(!then.trim().equals(""))
							executeThen(context,then.trim());
					}
					if(rules.getType().equalsIgnoreCase("if"))
						break;
				}
			}
			catch(Exception  e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void executeThen(Map context, String then) 
	{
		Object parths = null;
		if(then.indexOf(".")==-1)
			return;
		String[] array = then.split("\\.");
		Object obj = context.get(array[0]);
		for (int j = 1; j < array.length; j++) 
		{			
			String field = array[j];
			Method meth = null;
			try 
			{
				String[] vars = new String[]{};
				List objvars = new ArrayList();
				if(field.indexOf("(")!=-1)
				{
					String temp = field.substring(field.indexOf("(")+1,field.lastIndexOf(")"));
					if(!temp.equals(""))vars = temp.split(",");
					List<String> temps = new ArrayList<String>();
					temp = "";
					if(vars.length==1)
						temps.add(vars[0]);
					else
					{	
						for (String string : vars) 
						{
							if(string.indexOf("\"")!=-1
									&& string.indexOf("\"")==string.lastIndexOf("\""))
							{
								if(temp.equals(""))
									temp = string;
								else
									temp += "," + string;
							}
							else
								temp = string;
							if(temp.indexOf("\"")!=-1
									&& temp.indexOf("\"")!=temp.lastIndexOf("\""))
							{
								temps.add(temp);
								temp = "";
							}
						}
					}
					if(temps.size()>0)
						vars = (String[])temps.toArray(new String[temps.size()]);
					field = field.substring(0,field.indexOf("("));				
				}
				for (String var : vars) 
				{
					objvars.add(getObj(var, context));
				}
				Method[] meths = obj.getClass().getMethods();
				for (Method method : meths) 
				{
					Class[] atypes = method.getParameterTypes();
					if(method.getName().equals(field) && atypes.length==objvars.size())
					{
						Object[] objvs = new Object[atypes.length];
						for (int i = 0; i < atypes.length; i++)
						{
							objvs[i] = cast(atypes[i],objvars.get(i));
						}
						obj = method.invoke(obj, objvs);
						break;
					}
				}
			} 
			catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static Object cast(Class claz,Object val)
	{
		if(claz.getCanonicalName().equals("java.lang.Double")
				|| claz.getCanonicalName().equals("double"))
		{
			if(val instanceof Long)
				return ((Long)val).doubleValue();
			else if(val instanceof Integer)
				return ((Integer)val).doubleValue();
			else if(val instanceof Float)
				return ((Float)val).doubleValue();
		}
		else if(claz.getCanonicalName().equals("java.lang.Float")
				|| claz.getCanonicalName().equals("float"))
		{
			if(val instanceof Long)
				return ((Long)val).floatValue();
			else if(val instanceof Integer)
				return ((Integer)val).floatValue();
			else if(val instanceof Float)
				return ((Float)val).floatValue();
			else if(val instanceof Double)
				return ((Double)val).floatValue();
		}
		else if(claz.getCanonicalName().equals("java.lang.Integer")
				|| claz.getCanonicalName().equals("int"))
		{
			if(val instanceof Long)
				return ((Long)val).intValue();
			else if(val instanceof Integer)
				return ((Integer)val).intValue();
			else if(val instanceof Float)
				return ((Float)val).intValue();
			else if(val instanceof Double)
				return ((Double)val).intValue();
		}
		else if(claz.getCanonicalName().equals("java.lang.Long")
				|| claz.getCanonicalName().equals("long"))
		{
			if(val instanceof Long)
				return ((Long)val).longValue();
			else if(val instanceof Integer)
				return ((Integer)val).longValue();
			else if(val instanceof Float)
				return ((Float)val).longValue();
			else if(val instanceof Double)
				return ((Double)val).longValue();
		}
		return claz.cast(val);
	}

	private static String getOper(String cond) throws Exception
	{
		if(cond.indexOf("==")!=-1)
			return "==";
		else if(cond.indexOf("<")!=-1)
			return "<";
		else if(cond.indexOf("<=")!=-1)
			return "<=";
		else if(cond.indexOf(">=")!=-1)
			return ">=";
		else if(cond.indexOf(">")!=-1)
			return ">";		
		else if(cond.indexOf("!=")!=-1)
			return "!=";
		else throw new Exception("Invalid operator in when");
	}
	
	private static boolean evaluateWhen(Map context,String condition) throws Exception
	{
		boolean flag = true;
		boolean change = false;
		String[] conds = condition.split(" and ");
		for (String cond : conds) 
		{
			change = true;
			cond = cond.trim(); 
			String oper = getOper(cond);
			String[] parts = cond.split(oper);
			boolean lfo=false,rfo=false;
			Object lhs = getConstant(parts[0], null, context);
			if(lhs==null)
			{
				lhs = getPart(context, parts[0]);
				lfo = true;
			}
			String tyep = null;
			if(lfo)
				tyep = lhs.getClass().getCanonicalName();
			Object rhs = getConstant(parts[1], tyep, context);
			if(rhs==null)
			{
				rhs = getPart(context, parts[1]);
				rfo = true;
			}
			if(!lfo && rfo)
				lhs = getConstant(parts[0], rhs.getClass().getCanonicalName(), context);
			flag &= operate(lhs,rhs,oper);
		}
		return flag && change;
	}
	
	private static boolean operate(Object lhs, Object rhs, String oper) throws Exception
	{
		if(oper.indexOf("==")!=-1)
			return lhs.equals(rhs);
		else if(oper.indexOf("<=")!=-1)
		{
			if(lhs instanceof Double
					|| lhs instanceof Float)
				return (Double)lhs <= (Double)rhs;
			else if(lhs instanceof String)
				return ((String)lhs).compareTo((String)rhs)<=0;
			else 
				return (Long)lhs <= (Long)rhs;
		}
		else if(oper.indexOf("<")!=-1)
		{
			if(lhs instanceof Double
					|| lhs instanceof Float)
				return (Double)lhs < (Double)rhs;
			else if(lhs instanceof String)
				return ((String)lhs).compareTo((String)rhs)<0;
			else 
				return (Long)lhs < (Long)rhs;
		}		
		else if(oper.indexOf(">=")!=-1)
		{
			if(lhs instanceof Double
					|| lhs instanceof Float)
				return (Double)lhs >= (Double)rhs;
			else if(lhs instanceof String)
				return ((String)lhs).compareTo((String)rhs)>=0;
			else 
				return (Long)lhs >= (Long)rhs;
		}
		else if(oper.indexOf(">")!=-1)
		{
			if(lhs instanceof Double
					|| lhs instanceof Float)
				return (Double)lhs > (Double)rhs;
			else if(lhs instanceof String)
				return ((String)lhs).compareTo((String)rhs)>0;
			else 
				return (Long)lhs > (Long)rhs;
		}		
		else if(oper.indexOf("!=")!=-1)
			return !lhs.equals(rhs);
		else 
			throw new Exception("Invalid operator in when");
	}
	
	private static Object getObj(String part,Map context)
	{
		Object obj = getConstant(part, null, context);
		if(obj==null)
			obj = getPart(context, part);
		return obj;
	}
	
	private static Object getConstant(String part,String type,Map context)
	{
		if(part.length()>=2 && (part.charAt(0)=='\'' && part.charAt(part.length()-1)=='\'')
				|| (part.charAt(0)=='"' && part.charAt(part.length()-1)=='"'))
			return part.substring(1,part.length()-1);
		else if(part.indexOf("*")!=-1)
		{
			String[] subparts = part.split("\\*");
			Object sub1 = getObj(subparts[0], context);
			Object sub2 = getObj(subparts[1], context);
			if((sub1.getClass().getCanonicalName().equals("java.lang.Double")
					|| sub1.getClass().getCanonicalName().equals("double"))
				|| 
				(sub2.getClass().getCanonicalName().equals("java.lang.Double")
						|| sub2.getClass().getCanonicalName().equals("double")))
			{
				return (Double)sub1 * (Double)sub2;
			}
			else if((sub1.getClass().getCanonicalName().equals("java.lang.Float")
					|| sub1.getClass().getCanonicalName().equals("float"))
					|| 
					(sub2.getClass().getCanonicalName().equals("java.lang.Float")
							|| sub2.getClass().getCanonicalName().equals("float")))
			{
				return (Float)sub1 * (Float)sub2;
			}
			else
			{
				return (Long)sub1 * (Long)sub2;
			}
		}
		else if(part.indexOf("/")!=-1)
		{
			String[] subparts = part.split("\\*");
			Object sub1 = getObj(subparts[0], context);
			Object sub2 = getObj(subparts[1], context);
			if((sub1.getClass().getCanonicalName().equals("java.lang.Double")
					|| sub1.getClass().getCanonicalName().equals("double"))
				|| 
				(sub2.getClass().getCanonicalName().equals("java.lang.Double")
						|| sub2.getClass().getCanonicalName().equals("double")))
			{
				return (Double)sub1 / (Double)sub2;
			}
			else if((sub1.getClass().getCanonicalName().equals("java.lang.Float")
					|| sub1.getClass().getCanonicalName().equals("float"))
					|| 
					(sub2.getClass().getCanonicalName().equals("java.lang.Float")
							|| sub2.getClass().getCanonicalName().equals("float")))
			{
				return (Float)sub1 / (Float)sub2;
			}
			else
			{
				return (Long)sub1 / (Long)sub2;
			}
		}
		else if(part.indexOf("+")!=-1)
		{
			String[] subparts = part.split("\\*");
			Object sub1 = getObj(subparts[0], context);
			Object sub2 = getObj(subparts[1], context);
			if((sub1.getClass().getCanonicalName().equals("java.lang.Double")
					|| sub1.getClass().getCanonicalName().equals("double"))
				|| 
				(sub2.getClass().getCanonicalName().equals("java.lang.Double")
						|| sub2.getClass().getCanonicalName().equals("double")))
			{
				return (Double)sub1 + (Double)sub2;
			}
			else if((sub1.getClass().getCanonicalName().equals("java.lang.Float")
					|| sub1.getClass().getCanonicalName().equals("float"))
					|| 
					(sub2.getClass().getCanonicalName().equals("java.lang.Float")
							|| sub2.getClass().getCanonicalName().equals("float")))
			{
				return (Float)sub1 + (Float)sub2;
			}
			else
			{
				return (Long)sub1 + (Long)sub2;
			}
		}
		else if(part.indexOf("-")!=-1)
		{
			String[] subparts = part.split("\\*");
			Object sub1 = getObj(subparts[0], context);
			Object sub2 = getObj(subparts[1], context);
			if((sub1.getClass().getCanonicalName().equals("java.lang.Double")
					|| sub1.getClass().getCanonicalName().equals("double"))
				|| 
				(sub2.getClass().getCanonicalName().equals("java.lang.Double")
						|| sub2.getClass().getCanonicalName().equals("double")))
			{
				return (Double)sub1 - (Double)sub2;
			}
			else if((sub1.getClass().getCanonicalName().equals("java.lang.Float")
					|| sub1.getClass().getCanonicalName().equals("float"))
					|| 
					(sub2.getClass().getCanonicalName().equals("java.lang.Float")
							|| sub2.getClass().getCanonicalName().equals("float")))
			{
				return (Float)sub1 - (Float)sub2;
			}
			else
			{
				return (Long)sub1 - (Long)sub2;
			}
		}
		else if(part.indexOf("%")!=-1)
		{
			String[] subparts = part.split("\\*");
			Object sub1 = getObj(subparts[0], context);
			Object sub2 = getObj(subparts[1], context);
			if((sub1.getClass().getCanonicalName().equals("java.lang.Double")
					|| sub1.getClass().getCanonicalName().equals("double"))
				|| 
				(sub2.getClass().getCanonicalName().equals("java.lang.Double")
						|| sub2.getClass().getCanonicalName().equals("double")))
			{
				return (Double)sub1 % (Double)sub2;
			}
			else if((sub1.getClass().getCanonicalName().equals("java.lang.Float")
					|| sub1.getClass().getCanonicalName().equals("float"))
					|| 
					(sub2.getClass().getCanonicalName().equals("java.lang.Float")
							|| sub2.getClass().getCanonicalName().equals("float")))
			{
				return (Float)sub1 % (Float)sub2;
			}
			else
			{
				return (Long)sub1 % (Long)sub2;
			}
		}
		else
		{
			try 
			{
				Object doub = null;
				if(part.indexOf(".")!=-1)
				{
					if(type==null)
						doub = Double.parseDouble(part);
					else if(type.equals("java.lang.Float"))
						doub = Float.parseFloat(part);
					else if(type.equals("java.lang.Double"))
						doub = Double.parseDouble(part); 
				}
				else
				{
					if(type==null)
						doub = Long.parseLong(part);
					else if(type.equals("java.lang.Integer"))
						doub = Integer.parseInt(part);
					else if(type.equals("java.lang.Long"))
						doub = Long.parseLong(part);
					else if(type.equals("java.lang.Short"))
						doub = Short.parseShort(part); 
				}
				return doub;
			} 
			catch (Exception e) 
			{
				
			}
		}
		return null;
	}
	
	private static Object getPart(Map context,String part)
	{
		String[] array = part.split("\\.");
		Object obj = context.get(array[0]);
		for (int j = 1; j < array.length; j++) 
		{			
			String field = array[j];
			Method meth = null;
			try 
			{
				String[] vars = new String[]{};
				List objvars = new ArrayList();
				if(field.indexOf("(")!=-1)
				{
					String temp = field.substring(field.indexOf("(")+1,field.lastIndexOf(")"));
					if(!temp.equals(""))vars = temp.split(",");
					List<String> temps = new ArrayList<String>();
					temp = "";
					if(vars.length==1)
						temps.add(vars[0]);
					else
					{
						for (String string : vars) 
						{
							if(string.indexOf("\"")!=-1
									&& string.indexOf("\"")==string.lastIndexOf("\""))
							{
								if(temp.equals(""))
									temp = string;
								else
									temp += "," + string;
							}
							else
								temp = string;
							if(temp.indexOf("\"")!=-1
									&& temp.indexOf("\"")!=temp.lastIndexOf("\""))
							{
								temps.add(temp);
								temp = "";
							}
						}
					}
					if(temps.size()>0)
						vars = (String[])temps.toArray(new String[temps.size()]);
					field = field.substring(0,field.indexOf("("));				
				}
				for (String var : vars) 
				{
					objvars.add(getObj(var, context));
				}
				Method[] meths = obj.getClass().getMethods();
				for (Method method : meths) 
				{
					Class[] atypes = method.getParameterTypes();
					String getfield = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
					if((method.getName().equals(field) || method.getName().equals(getfield)) 
							&& atypes.length==objvars.size())
					{
						Object[] objvs = new Object[atypes.length];
						for (int i = 0; i < atypes.length; i++)
						{
							objvs[i] = cast(atypes[i],objvars.get(i));
						}
						obj = method.invoke(obj, objvs);
						break;
					}
				}
			} 
			catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}
	
}
