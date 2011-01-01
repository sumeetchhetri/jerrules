<brules type="if" name="PricingRules">
	<objs>
		<obj name="order" clas="com.model.Order"/>
		<obj name="item" clas="com.model.Item"/>
		<obj name="cust" clas="com.model.Customer"/>
	</objs>
	<rules>
		<rule>		
			<when>
				cust.getId()==1 and item.getId()==1
			</when>
			<then>
				order.setPrice(10)
			</then>
		</rule>
		<rule>
			<when>
				cust.getId()==2 and item.getId()==1
			</when>
			<then>
				order.setPrice(10)
			</then>
		</rule>
	</rules>
</brules>