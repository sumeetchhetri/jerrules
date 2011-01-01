<brules type="if" name="UserStatusRules">
	<objs>
		<obj name="cust" clas="com.model.Customer"/>
	</objs>
	<rules>
		<rule>		
			<when>
				cust.getUsage()&gt;=cust.getCreditLimit()
			</when>
			<then>
				cust.getUser().setUserStatus(5)
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.95*cust.getCreditLimit()
			</when>
			<then>
				cust.getUser().setUserStatus(4)
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.9*cust.getCreditLimit()
			</when>
			<then>
				cust.getUser().setUserStatus(3)
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;=cust.getBalance()
			</when>
			<then>
				cust.getUser().setUserStatus(2)
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.95*cust.getBalance()
			</when>
			<then>
				cust.getUser().setUserStatus(1)
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.9*cust.getBalance()
			</when>
			<then>
				cust.getUser().setUserStatus(0)
			</then>
		</rule>		
	</rules>
</brules>