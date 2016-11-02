package io.pivotal.benchmarks.functions;

import java.util.Properties;

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

public class GetTransactionCountForProductTypeFunction extends FunctionAdapter implements Declarable {

	private static final long serialVersionUID = 1L;

    public static final String ID = "GetTransactionCountForProductTypeFunction";

    private GemFireCache cache;
    private LogWriter logger;

    public GetTransactionCountForProductTypeFunction() {
        this.cache = CacheFactory.getAnyInstance();
        this.logger = cache.getLogger();
    }

    @SuppressWarnings("rawtypes")
	public void execute(FunctionContext fc) {

    	if (!(fc instanceof RegionFunctionContext)) {
    		throw new FunctionException("This is a data aware function, and has "
    				+ "to be called using FunctionService.onRegion.");
    	}

    	RegionFunctionContext context = (RegionFunctionContext)fc;
    	String productType = (String) context.getArguments();

    	String queryString = "SELECT count(*) FROM /Transaction t WHERE t.productId = "
    			+ "'" + productType + "'";
    	logger.info("Query: " + queryString);


	   	 QueryService queryService = cache.getQueryService();
	   	 Query query = queryService.newQuery(queryString);
	   	 SelectResults results = null;

			try {
				logger.info("Executing the query");
				results = (SelectResults)query.execute();
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

			context.getResultSender().lastResult(results);

    }

    public String getId() {
        return ID;
    }

    public void init(Properties properties) {
    }

}
