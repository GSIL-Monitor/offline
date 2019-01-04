<dal name="trn-offline-dal">
	<databaseSets>
		<databaseSet name="ctrainppsdb" provider="mySqlProvider">
            <add name="ctrainppsdb" databaseType="Master" sharding="" connectionString="ctrainppsdb"/>   
		</databaseSet>
		<databaseSet name="ctrainfinancedb" provider="mySqlProvider">
            <add name="ctrainfinancedb" databaseType="Master" sharding="" connectionString="ctrainfinancedb"/>   
		</databaseSet>
		<databaseSet name="CtrainChat" provider="mySqlProvider">
            <add name="ctrainchatdb" databaseType="Master" sharding="" connectionString="ctrainchatdb"/>   
		</databaseSet>
	</databaseSets>
	<LogListener>
		<logger>com.ctrip.platform.dal.sql.logging.CtripDalLogger</logger>
	</LogListener>
	<ConnectionLocator>
		<settings>
			<dataSourceConfigureProvider>com.ctrip.datasource.titan.TitanProvider</dataSourceConfigureProvider>
		</settings>
	</ConnectionLocator>
	<TaskFactory>
		<factory>com.ctrip.platform.dal.dao.CtripTaskFactory</factory>
	</TaskFactory>
</dal>