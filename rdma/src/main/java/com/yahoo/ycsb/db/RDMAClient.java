package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.Status;
import com.yahoo.ycsb.StringByteIterator;

import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.Protocol;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class RDMAClient extends DB {

	//use fifo for Redis-cli
	//private JedisCommands jedis;

	public static final String HOST_PROPERTY = "redis.host";
	public static final String PORT_PROPERTY = "redis.port";
	//public static final String PASSWORD_PROPERTY = "redis.password";
	//public static final String CLUSTER_PROPERTY = "redis.cluster";

	//public static final String INDEX_KEY = "_indices";

	public void init() throws DBException {
		Properties props = getProperties();
		String host = props.getProperty(HOST_PROPERTY);
		String portString = props.getProperty(PORT_PROPERTY);
		int port = Integer.parseInt(portString);
	}

	public void cleanup() throws DBException {
		//TODO cleanup
	}

	/*
	 * Calculate a hash for a key to store it in an index. The actual return value
	 * of this function is not interesting -- it primarily needs to be fast and
	 * scattered along the whole space of doubles. In a real world scenario one
	 * would probably use the ASCII values of the keys.
	 */
	private double hash(String key) {
		return key.hashCode();
	}

	// XXX jedis.select(int index) to switch to `table`

	@Override
	public Status read(String table, String key, Set<String> fields,
			Map<String, ByteIterator> result) {
		return Status.OK;
	}

	@Override
	public Status insert(String table, String key,
			Map<String, ByteIterator> values) {
		return Status.OK;
	}

	@Override
	public Status delete(String table, String key) {
		Status.OK;
	}

	@Override
	public Status update(String table, String key,
			Map<String, ByteIterator> values) {
			return Status.OK;
	}

	@Override
	public Status scan(String table, String startkey, int recordcount,
			Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {
		return Status.OK;
	}

}
