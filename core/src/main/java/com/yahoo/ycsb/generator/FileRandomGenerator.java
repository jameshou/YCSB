/**
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

package com.yahoo.ycsb.generator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A generator, whose sequence is the lines of a file.
 */
public class FileRandomGenerator extends Generator<String>
{
    private final String filename;
    private String current;
    private List<String> keys = new ArrayList<String>();
    private Random randomGenerator = new Random();

    /**
     * Create a FileRandomGenerator with the given file.
     * @param _filename The file to read lines from.
     */
    public FileRandomGenerator(String _filename)
    {
      filename = _filename;
      loadFile();
    }

    /**
     * Return the next string of the sequence, ie the next line of the file.
     */
    @Override
  public synchronized String nextValue()
    {
        return keys.get(randomGenerator.nextInt(keys.size()));
    }

    /**
     * Return the previous read line.
     */
    @Override
  public String lastValue()
    {
        return current;
    }

    /**
     * Open the file for keys.
     */
    public synchronized void loadFile() {

        System.err.println("Load " + filename);
        Path path = Paths.get(filename);
        Charset charset = StandardCharsets.UTF_8;
        try {
            keys = Files.readAllLines(path, charset);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        System.err.println("Loaded " + keys.length() + " records");

    }
}
