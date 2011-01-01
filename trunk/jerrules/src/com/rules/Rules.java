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

import java.util.Arrays;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Rules 
{
	private Rule[] rules;
	private obj[] objs;
	@XStreamAsAttribute
	private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XStreamAsAttribute
	private String name;
	@Override
	public String toString() {
		return "Rules [rules=" + Arrays.toString(rules) + ", objs="
				+ Arrays.toString(objs) + ", type=" + type + ", name=" + name
				+ "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(objs);
		result = prime * result + Arrays.hashCode(rules);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rules other = (Rules) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(objs, other.objs))
			return false;
		if (!Arrays.equals(rules, other.rules))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	public Rule[] getRules() {
		return rules;
	}
	public void setRules(Rule[] rules) {
		this.rules = rules;
	}
	public obj[] getObjs() {
		return objs;
	}
	public void setObjs(obj[] objs) {
		this.objs = objs;
	}
}
