### Spring Cloud Contract based AMQP application
This application is created as simple as possible. It uses spring-cloud-contract to verify the message contract at publisher and consumer side
### What does the application contain
1. A Producer application to publish a message to amqp
2. A Consumer application to receive the message

### Spring cloud contract integration
#### On Producer side
1. Producer contains the contract definition (under test/resources/contracts)
2. BaseTest class for contract tests publish message to Amqp
3. ContractVerifierTest (auto generated) will verify the contract
#### On Consumer side
1. Consumer downloads the stubs from local repo
2. The only test will trigger the stub using label
3. Asserts the contract

### Issues found
1. If following dependency is added (by replacing `spring-boot-starter-amqp`),
```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bus-amqp</artifactId>
    </dependency>
```
`ContractVerifierTest` will fail with following error
```
2020-11-09 17:06:15.326 ERROR 3872 --- [           main] m.s.StreamPollableChannelMessageReceiver : Exception occurred while trying to read a message from  a channel with na
me [custom-event-exchange]

org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'custom-event-exchange' available
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:816) ~[spring-beans-5.2.10.RELEASE.jar:5.2.
10.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1288) ~[spring-beans-5.2.10.RELEASE.jar:5.2.10
.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:298) ~[spring-beans-5.2.10.RELEASE.jar:5.2.10.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:277) ~[spring-beans-5.2.10.RELEASE.jar:5.2.10.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207) ~[spring-beans-5.2.10.RELEASE.jar:5.2.10.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1115) ~[spring-context-5.2.10.RELEASE.jar:5.2.10.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.stream.StreamPollableChannelMessageReceiver.receive(StreamPollableChannelMessageReceiver.java:47) [spring-cl
oud-contract-verifier-2.2.4.RELEASE.jar:2.2.4.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.stream.StreamPollableChannelMessageReceiver.receive(StreamPollableChannelMessageReceiver.java:61) [spring-cl
oud-contract-verifier-2.2.4.RELEASE.jar:2.2.4.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.stream.StreamPollableChannelMessageReceiver.receive(StreamPollableChannelMessageReceiver.java:29) [spring-cl
oud-contract-verifier-2.2.4.RELEASE.jar:2.2.4.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.stream.StreamStubMessages.receive(StreamStubMessages.java:59) [spring-cloud-contract-verifier-2.2.4.RELEASE.
jar:2.2.4.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.stream.StreamStubMessages.receive(StreamStubMessages.java:30) [spring-cloud-contract-verifier-2.2.4.RELEASE.
jar:2.2.4.RELEASE]
        at org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging.receive(ContractVerifierMessaging.java:44) [spring-cloud-contract-verifie
r-2.2.4.RELEASE.jar:2.2.4.RELEASE]
        at com.example.producer.ContractVerifierTest.validate_custom_event(ContractVerifierTest.java:30) [test-classes/:na]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_231]
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_231]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_231]
        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_231]
        at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:686) [junit-platform-commons-1.6.3.jar:1.6.3]
        at org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60) [junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131) [junit-jupiter-engine-5.6.3.jar:5.
6.3]
        at org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149) [junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140) [junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84) [junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115) ~[junit-jupiter-engine-5.6.3.jar:
5.6.3]
        at org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106) ~[junit-jupiter-engine-5.6.3.jar:
5.6.3]
        at org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:212) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:208) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:137) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:71) ~[junit-jupiter-engine-5.6.3.jar:5.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:135) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at java.util.ArrayList.forEach(ArrayList.java:1257) ~[na:1.8.0_231]
        at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38) ~[junit-plat
form-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at java.util.ArrayList.forEach(ArrayList.java:1257) ~[na:1.8.0_231]
        at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38) ~[junit-plat
form-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32) ~[junit-platfor
m-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51) ~[junit-platform-engine-1.6.3.jar:1.6.3]
        at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:220) ~[junit-platform-launcher-1.3.1.jar:1.3.1]
        at org.junit.platform.launcher.core.DefaultLauncher.lambda$execute$6(DefaultLauncher.java:188) ~[junit-platform-launcher-1.3.1.jar:1.3.1]
        at org.junit.platform.launcher.core.DefaultLauncher.withInterceptedStreams(DefaultLauncher.java:202) ~[junit-platform-launcher-1.3.1.jar:1.3.1]
        at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:181) ~[junit-platform-launcher-1.3.1.jar:1.3.1]
        at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:128) ~[junit-platform-launcher-1.3.1.jar:1.3.1]
        at org.apache.maven.surefire.junitplatform.JUnitPlatformProvider.invokeAllTests(JUnitPlatformProvider.java:150) ~[surefire-junit-platform-2.22.2.jar:2.22.2]
        at org.apache.maven.surefire.junitplatform.JUnitPlatformProvider.invoke(JUnitPlatformProvider.java:124) ~[surefire-junit-platform-2.22.2.jar:2.22.2]
        at org.apache.maven.surefire.booter.ForkedBooter.invokeProviderInSameClassLoader(ForkedBooter.java:384) ~[surefire-booter-2.22.2.jar:2.22.2]
        at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:345) ~[surefire-booter-2.22.2.jar:2.22.2]
        at org.apache.maven.surefire.booter.ForkedBooter.execute(ForkedBooter.java:126) ~[surefire-booter-2.22.2.jar:2.22.2]
        at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:418) ~[surefire-booter-2.22.2.jar:2.22.2]

[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 30.393 s <<< FAILURE! - in com.example.producer.ContractVerifierTest
[ERROR] validate_custom_event  Time elapsed: 4.417 s  <<< ERROR!
java.lang.IllegalStateException: org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'custom-event-exchange' available
        at com.example.producer.ContractVerifierTest.validate_custom_event(ContractVerifierTest.java:30)
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'custom-event-exchange' available
        at com.example.producer.ContractVerifierTest.validate_custom_event(ContractVerifierTest.java:30)


```
