package io.pivotal.benchmarks.cq.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gemstone.gemfire.cache.query.CqEvent;

public class CQListener {

	private static Log log = LogFactory.getLog(CQListener.class);

	public void handleEvent(CqEvent event) {
		log.info("Received a CQ event " + event);
	}

}
