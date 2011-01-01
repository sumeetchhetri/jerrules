<brules type="if" name="AlertRules">
	<objs>
		<obj name="msg" clas="javax.mail.internet.MimeMessage"/>
		<obj name="cust" clas="com.model.Customer"/>
	</objs>
	<rules>
		<rule>		
			<when>
				cust.getUsage()==cust.getCreditLimit()
			</when>
			<then>
				msg.setSubject("Service suspended")
				msg.setText("Your service has been suspended due to insufficient funds, Please recharge immediately for resumption of Services")
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.95*cust.getCreditLimit()
			</when>
			<then>
				msg.setSubject("5% Credit Limit Alert")
				msg.setText("Your credit limit has dropped down to 5%")
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.9*cust.getCreditLimit()
			</when>
			<then>
				msg.setSubject("10% Credit Limit Alert")
				msg.setText("Your credit limit has dropped down to 10%")
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()==cust.getBalance()
			</when>
			<then>
				msg.setSubject("Balance exhausted")
				msg.setText("Your balance has exhausted, Please recharge immediately for interrupted service")
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.95*cust.getBalance()
			</when>
			<then>
				msg.setSubject("5% Balance Alert")
				msg.setText("Your balance has dropped down to 5%")
			</then>
		</rule>
		<rule>		
			<when>
				cust.getUsage()&gt;0.9*cust.getBalance()
			</when>
			<then>
				msg.setSubject("10% Balance Alert")
				msg.setText("Your balance has dropped down to 10%")
			</then>
		</rule>		
	</rules>
</brules>