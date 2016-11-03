package io.pivotal.benchmarks.clients;

import io.pivotal.benchmarks.domain.Product;
import io.pivotal.benchmarks.domain.Transaction;
import io.pivotal.benchmarks.repositories.ProductRepository;
import io.pivotal.benchmarks.repositories.TransactionRepository;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.gemstone.gemfire.cache.CacheTransactionManager;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;

@Component
public class GemfireTransaction {

	private static final int QUANTITY = 1;

	@Autowired
	ClientCache clientCache;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	TransactionRepository transactionRepo;

//	@Autowired
//	@Qualifier("productFunctionCaller")
//	ProductFunctionCaller productFunctionCaller;

	public String executeTransactionUsecase(int transactionCount) {

		Collection<Product> products = productRepo.findAllWithStock();
		int count = 0;

		StopWatch sw = new StopWatch();
		CacheTransactionManager txManager =
				clientCache.getCacheTransactionManager();

		for ( Product product : products) {

			Transaction transaction = createTransactionObject(product);
			product.setStockOnHand(product.getStockOnHand() - QUANTITY );

			sw.start();
			txManager.begin();
			Product currentProduct = productRepo.findOne(product.getId());
			if (currentProduct.getStockOnHand() - QUANTITY > 0) {
				transactionRepo.save(transaction);
				currentProduct.setStockOnHand(currentProduct.getStockOnHand() - QUANTITY );
				productRepo.save(currentProduct);
			} else {
				System.out.println("Transaction for following Product Id failed :" + currentProduct.getId());
			}
			txManager.commit();
			sw.stop();

			count++;
			if (count == transactionCount) {
				break;
			}
		}


		String result = "Total time in MiliSeconds(ms): " + sw.getTotalTimeMillis();
		return result;
	}

    public String executeSizeFunction() {

    	return null;
    }

    public String executeTransactionCountFunction(String productType) {

    	StopWatch sw = new StopWatch();

//    	Integer count = productFunctionCaller.getTransactionCountForProductTypeFunction(productType);

    	Region<?,?> region = clientCache.getRegion("Transaction");

    	Execution functionExecution = FunctionService.
    			onRegion(region).withArgs(productType);

    	sw.start();
		ResultCollector<?,?> results = functionExecution.
				execute("GetTransactionCountForProductTypeFunction");

		List<?> response = (List<?>)results.getResult();
		SelectResults<?> queryResult = (SelectResults<?>)response.get(0);
		Integer count = (Integer)queryResult.asList().get(0);
    	sw.stop();
    	System.out.println("Transaction Count :" + count);
    	String result = "Total time in MiliSeconds(ms): " + sw.getTotalTimeMillis();
    	return result;
    }

    public String executeTransactionInfoDisplayFunction(String transactionId) {

    	StopWatch sw = new StopWatch();

//    	Integer count = productFunctionCaller.getTransactionCountForProductTypeFunction(productType);

    	Region<?,?> region = clientCache.getRegion("Transaction");

    	@SuppressWarnings("serial")
		Set<String> filters = new HashSet<String>() {{
    		add(transactionId);
    	}};

    	Execution functionExecution = FunctionService.
    			onRegion(region).withFilter(filters);

    	sw.start();
		ResultCollector<?,?> resultCollector = functionExecution.
				execute("GetDisplayInfoForTransactionFunction");

		List<?> response = (List<?>)resultCollector.getResult();
		SelectResults<?> queryResult = (SelectResults<?>)response.get(0);
		Struct results = (Struct)queryResult.asList().get(0);
    	sw.stop();
    	System.out.println("Transaction Info :" + results);
    	String result = "Total time in MiliSeconds(ms): " + sw.getTotalTimeMillis();
    	return result;
    }

	private Transaction createTransactionObject( Product product) {

		Transaction transaction = new Transaction();
		transaction.setCustomerId("123");
		transaction.setTransactionDate(new Date(System.currentTimeMillis()));
        transaction.setMarkUp(new Double (0.12));
        transaction.setQuantity(1);
        transaction.setOrderStatus("OPEN");
        transaction.setProductId(product.getId());
        transaction.setRetailPrice(new Double (510.00));
        transaction.setId(System.currentTimeMillis() + "#" + product.getId());

		return transaction;
	}

}
