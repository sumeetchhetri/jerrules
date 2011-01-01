package com.rules;

import java.util.Arrays;

public class Rule 
{
	@Override
	public String toString() {
		return "Rule [then=" + then
				+ ", when=" + when + "]";
	}
	
	public String getWhen() {
		return when;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((then == null) ? 0 : then.hashCode());
		result = prime * result + ((when == null) ? 0 : when.hashCode());
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
		Rule other = (Rule) obj;
		if (then == null) {
			if (other.then != null)
				return false;
		} else if (!then.equals(other.then))
			return false;
		if (when == null) {
			if (other.when != null)
				return false;
		} else if (!when.equals(other.when))
			return false;
		return true;
	}

	public void setWhen(String when) {
		this.when = when;
	}
	public String getThen() {
		return then;
	}
	public String[] getThenArr() {
		return then.split("\n");
	}
	public void setThen(String then) {
		this.then = then;
	}
	private String when;
	private String then;
}
