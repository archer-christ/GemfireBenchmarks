package io.pivotal.benchmarks.client.functions;


import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region="Product", id="productFunctionCaller")
public interface ProductFunctionCaller {

	@FunctionId("GetTransactionCountForProductTypeFunction")
	public Integer getTransactionCountForProductTypeFunction(String productType);

}



