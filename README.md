# data-pipeline  数据流管道框架

使用：
```
<bean id="testPipeline" class="com.reliefzk.middleware.dp.pipeline.PipelineImpl">
    <property name="valves">
        <list>
            <bean class="com.xxxx.DataLoaderValve">
                <property name="loopDataListName" value="__loopDataList__" />
            </bean>
            <bean class="com.reliefzk.middleware.dp.pipeline.valve.DataListLoopValve">
                <property name="loopBody">
                    <bean class="com.reliefzk.middleware.dp.pipeline.PipelineImpl">
                        <property name="valves">
                            <list>
                                <bean class="com.xxx.DataProcessValve" init-method="init">
                                    <property name="loopDataName" value="__loopData__" />
                                </bean>
                            </list>
                        </property>
                    </bean>
                </property>
            </bean>
            <bean class="com.xxx.DataProcessResultValve" />
        </list>
    </property>
</bean>
```