<brules type="if" name="ItemRules">
	<objs>
		<obj name="cdr" clas="com.model.CDR"/>
		<obj name="order" clas="com.model.Order"/>
	</objs>
	<rules>
		<rule>		
			<when>
				cdr.getStatus()==1 and cdr.getMnp()==0
			</when>
			<then>
				order.setItem_id(101)
			</then>
		</rule>
		<rule>		
			<when>
				cdr.getStatus()==1 and cdr.getMnp()==1
			</when>
			<then>
				order.setItem_id(100)
			</then>
		</rule>
		<rule>		
			<when>
				cdr.getStatus()==2 and cdr.getMnp()==0
			</when>
			<then>
				order.setItem_id(103)
			</then>
		</rule>
		<rule>		
			<when>
				cdr.getStatus()==2 and cdr.getMnp()==1
			</when>
			<then>
				order.setItem_id(102)
			</then>
		</rule>
		<rule>		
			<when>
				cdr.getStatus()==3
			</when>
			<then>
				order.setItem_id(104)
			</then>
		</rule>
	</rules>
</brules>