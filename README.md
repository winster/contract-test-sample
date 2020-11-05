### Spring Cloud Contract based AMQP application
This application is created as simple as possible. You may not find a simpler application anywhere else.

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
