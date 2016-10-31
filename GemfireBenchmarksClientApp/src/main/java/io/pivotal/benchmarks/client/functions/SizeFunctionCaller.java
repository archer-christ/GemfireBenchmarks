package io.pivotal.benchmarks.client.functions;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;

@OnRegion(region="Transaction", id="sizeFunctionCaller")
public interface SizeFunctionCaller {

	@FunctionId("size-function")
	public Integer getRegionSize();
}
