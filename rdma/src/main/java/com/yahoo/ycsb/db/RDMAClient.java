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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * YCSB binding for <a href="http://redis.io/">Redis</a>.
 *
 * See {@code redis/README.md} for details.
 */
public class RDMAClient extends DB {

  //use fifo for Redis-cli
  //private JedisCommands jedis;

  public static final String FIFO1 = "~/work/fifo1";
  public static final String FIFO2 = "~/work/fifo2";

  private FileReader reader = null;
  private FileWriter writer = null;

  public void init() throws DBException {
    File fifo1 = new File(FIFO1);
    File fifo2 = new File(FIFO2);
    writer = new FileWriter(fifo1);
    reader = new FileReader(fifo2);
  }

  public void cleanup() throws DBException {
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
  public Status read(String table, String key, Set<String> fields,
      Map<String, ByteIterator> result) {
	char[] str = new char[50];
    writer.write("PING");
    reader.read(str);
    if ((String)str.equal("PONG")) {
      return Status.OK;
    } else {
      return Status.ERROR;
    }
  }

  @Override
  public Status insert(String table, String key,
      Map<String, ByteIterator> values) {
	char[] str = new char[50];
    writer.write("PING");
    reader.read(str);
    if ((String)str.equal("PONG")) {
      return Status.OK;
    } else {
      return Status.ERROR;
    }
  }

  @Override
  public Status delete(String table, String key) {
    return Status.OK;
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
