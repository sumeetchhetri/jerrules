package com.rules;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class obj 
{
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String clas;
	@Override
	public String toString() {
		return "obj [clas=" + clas + ", name=" + name + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clas == null) ? 0 : clas.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		obj other = (obj) obj;
		if (clas == null) {
			if (other.clas != null)
				return false;
		} else if (!clas.equals(other.clas))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClas() {
		return clas;
	}
	public void setClas(String clas) {
		this.clas = clas;
	}
	
}
