package io.pivotal.benchmarks.functions;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.FunctionException;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

public class GetDisplayInfoForTransactionFunction extends FunctionAdapter implements Declarable {

	private static final long serialVersionUID = 1L;

    public static final String ID = "GetDisplayInfoForTransactionFunction";

    private GemFireCache cache;
    private LogWriter logger;
    private transient QueryService queryService = null;

    public GetDisplayInfoForTransactionFunction() {
        this.cache = CacheFactory.getAnyInstance();
        this.logger = cache.getLogger();
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(FunctionContext fc) {

		if (!(fc instanceof RegionFunctionContext)) {
    		throw new FunctionException("This is a data aware function, and has "
    				+ "to be called using FunctionService.onRegion.");
    	}

		RegionFunctionContext rfc = (RegionFunctionContext)fc;
//    	String transactionId = (String) context.getArguments();

		Set<String> filters = (Set<String>) rfc.getFilter();
		String transactionId = filters.iterator().next();

    	String queryString = "SELECT t.id, t.customerId, t.transactionDate, p.brand, p.productType, t.orderStatus"
    			+ " FROM /Product p, /Transaction t WHERE t.id ="
    			+ "'" + transactionId + "' AND t.productId = p.id";

    	logger.info("Query: " + queryString);

	   	QueryService queryService = cache.getQueryService();
//    	this.queryService = CacheFactory.getAnyInstance().getQueryService();
	   	Query query = queryService.newQuery(queryString);
	   	SelectResults results = null;

	   	try {
			logger.info("Executing the query");
			results = (SelectResults)query.execute(rfc);
			logger.info("Quering Results :" + results.toString());
		} catch (FunctionDomainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryInvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   	rfc.getResultSender().lastResult(results);
	}

	@Override
	public String getId() {
		return ID;
	}

	public void init(Properties arg0) {

	}

}
