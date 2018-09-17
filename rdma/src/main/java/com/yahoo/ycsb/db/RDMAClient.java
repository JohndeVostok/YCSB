/**
 * Copyright (c) 2012 YCSB contributors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

/**
 * Redis client binding for YCSB.
 *
 * All YCSB records are mapped to a Redis *hash field*.  For scanning
 * operations, all keys are saved (by an arbitrary hash) in a sorted set.
 */

package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.Status;

import java.util.Properties;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * YCSB binding for <a href="http://redis.io/">Redis</a>.
 *
 * See {@code redis/README.md} for details.
 */
public class RDMAClient extends DB {
	public static final String TYPE_PROPERTY = "type";
	public static final String PATH_PROPERTY = "path";
	private FileWriter fileWriter = null;
	private BufferedWriter writer = null;
	private String type = null;
	private String path = null;

	public void init() throws DBException {
    	Properties props = getProperties();
		String type = props.getProperty(TYPE_PROPERTY);
		String path = props.getProperty(PATH_PROPERTY);
		System.out.println(type);
		System.out.println(path);
		try {
			File file = new File(path);
			fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			type = null;
			e.printStackTrace();
		}
	}
	
	public void cleanup() throws DBException {
		try {
			writer.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	@Override
	public Status read(String table, String key, Set<String> fields, Map<String, ByteIterator> result) {
		try {
			writer.write("GET " + key + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Status.OK;
	}

	@Override
	public Status insert(String table, String key, Map<String, ByteIterator> values) {
		Iterator iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object v = entry.getValue();
			try {
				writer.write("SET " + key + " " + v + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Status.OK;
	}

  @Override
  public Status delete(String table, String key) {
    return Status.OK;
  }

	@Override
	public Status update(String table, String key, Map<String, ByteIterator> values) {
		Iterator iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object v = entry.getValue();
			try {
				writer.write("SET " + key + " " + v + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Status.OK;
	}

	@Override
	public Status scan(String table, String startkey, int recordcount, Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {
		return Status.OK;
	}
}
