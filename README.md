# GemfireBenchmarks

Spring Data Gemfire(SDG) clients to Benchmark Pivotal Gemfire features like Distributed Function, Continuous Queries, ACID Transaction support.

This demo was created to simulate MarketPlace transactions where Gemfire cluster will host Product and Transaction data in respective regions.

## Instructions

1) Use the following server configuration file to bootstrap Gemfire Server. This creates Transaction and Product Regions.

> GemfireBenchmarks/GemfireBenchmarksDataLoader/grid/config/serverCache.xml

2) GemfireBenchmarksServerSide contains server side code which needs to be deployed on the GemfireCluster. Following jar `GemfireBenchmarksServerSide-0.0.1-SNAPSHOT.jar` should be present on Gemfire server classpath. This jar deploys two functions onto the Cluster,

`GetTransactionCountForProductTypeFunction` - Function to return the no. of transactions based on product type. Function is a data aware function which is executed on region.

`size-function` - Sample distributed Global(data unaware) function to demonstrate the capability of running bussiness logic across all the nodes. Function is executed on servers.

3) GemfireBenchmarksDataLoader is used to load mock data into the Gemfire Cluster. Load data into the cluster using following command

```
java -jar GemfireBenchmarksDataLoader-0.0.1-SNAPSHOT.jar

```

### Output

```
*************************************************************
********************DATA LOADING SUMMARY*********************
*************************************************************
Products: Records Read: 1000 Records Added To GemFire: 1000
Transactions: Records Read: 10000 Records Added To GemFire: 10000
Total Loading Time: 4 seconds
*************************************************************
********************ERROR LOGS*******************************
*************************************************************
No errors were recorded
*************************************************************
**********Loading Completed**********
**********Press Enter To Continue**********
```


4) GemfireBenchmarksCQClient registers a Continuous Query on Transaction region. For Every transaction having `retail price is greater than $500`, an event will be sent to the client and will be displayed on the console.

```
java -jar GemfireBenchmarksCQClient-0.0.1-SNAPSHOT.jar
```

### Output

```
2016-10-31 11:14:08.703  INFO 37883 --- [enerContainer-2] i.p.benchmarks.cq.listener.CQListener    : Received a CQ event CqEvent [CqName=GfCq1; base operation=CREATE; cq operation=CREATE; key=1477926848696#776; value=Transaction [customerId=123, transactionDate=Mon Oct 31 11:14:08 EDT 2016, productId=776, quantity=1, retailPrice=510.0, orderStatus=OPEN, id=1477926848696#776, markUp=0.12]]
2016-10-31 11:14:08.703  INFO 37883 --- [enerContainer-1] i.p.benchmarks.cq.listener.CQListener    : Received a CQ event CqEvent [CqName=GfCq1; base operation=CREATE; cq operation=CREATE; key=1477926848611#955; value=Transaction [customerId=123, transactionDate=Mon Oct 31 11:14:08 EDT 2016, productId=955, quantity=1, retailPrice=510.0, orderStatus=OPEN, id=1477926848611#955, markUp=0.12]]
```

5) GemfireBenchmarksClientApp provides a transaction player which is used to simulate buy order being placed. Also, it is used to invoke functions deployed on the cluster.

```
java -jar GemfireBenchmarksCQClient-0.0.1-SNAPSHOT.jar
```

### Output

```
**********Actions**********
1. Transaction
2. Execute Functions
3. Exit
1
Enter the Transaction Count
10

**********Transaction execution completed**********
Total time in MiliSeconds(ms): 121
**********Press Enter To Continue**********
```
### Function Execution
This executes `GetTransactionCountForProductTypeFunction`

```
**********Actions**********
1. Transaction
2. Execute Functions
3. Exit
2
**********Starting Function Execution**********
Transaction Count :8
**********Function execution completed**********
Total time in MiliSeconds(ms): 84
**********Press Enter To Continue**********
```


Note: These instructions assume that demo jars are present on the node where one of the locator is running.


