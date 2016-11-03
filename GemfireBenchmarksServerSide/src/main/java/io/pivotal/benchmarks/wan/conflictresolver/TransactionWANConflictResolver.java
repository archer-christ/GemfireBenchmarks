package io.pivotal.benchmarks.wan.conflictresolver;

import java.io.Serializable;
import java.util.Properties;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.util.GatewayConflictHelper;
import com.gemstone.gemfire.cache.util.GatewayConflictResolver;
import com.gemstone.gemfire.cache.util.TimestampedEntryEvent;

public class TransactionWANConflictResolver implements GatewayConflictResolver, Serializable, Declarable {

	private static final long serialVersionUID = 1L;
	private LogWriter logger = null;

	public TransactionWANConflictResolver() {
		logger = CacheFactory.getAnyInstance().getLogger();
	}

	public void onEvent(TimestampedEntryEvent event, GatewayConflictHelper helper) {

		if (event.getOperation().isUpdate()) {

			logger.info("Old TimeStamp: " + event.getOldTimestamp() + "\t New TimeStamp: " + event.getNewTimestamp());
		}
	}

	public void init(Properties arg0) {


	}

}
