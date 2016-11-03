package io.pivotal.benchmarks.listener;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

@SuppressWarnings("rawtypes")
public class DurableQueueListener extends CacheListenerAdapter {

	@Override
	public void afterCreate(EntryEvent event) {
		System.out.println(event.getKey());
	}

}
