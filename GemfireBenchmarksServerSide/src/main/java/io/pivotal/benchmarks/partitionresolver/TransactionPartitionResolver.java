package io.pivotal.benchmarks.partitionresolver;

import java.io.Serializable;
import java.util.Properties;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

public class TransactionPartitionResolver implements PartitionResolver, Serializable, Declarable {

	private static final long serialVersionUID = 1L;

	public void close() {
		// TODO Auto-generated method stub

	}

	public void init(Properties arg0) {
		// TODO Auto-generated method stub

	}

	public String getName() {

		return this.getClass().getName();
	}

	public Object getRoutingObject(EntryOperation arg0) {

		String key = (String) arg0.getKey();
		String routingKey[] = key.split("#");

		if(routingKey[1] != null) {
			return routingKey[1];
		}
		return key;
	}

}
