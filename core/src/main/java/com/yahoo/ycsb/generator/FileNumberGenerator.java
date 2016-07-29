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

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * A number generator, whose sequence is the lines of a file.
 */
public class FileNumberGenerator extends NumberGenerator
{
    private final String filename;
    private Number current;
    private BufferedReader reader;
    private NumberFormat numberFormat;

    /**
     * Create a FileNumberGenerator with the given file.
     * @param _filename The file to read lines from.
     */
    public FileNumberGenerator(String _filename)
    {
      filename = _filename;
      numberFormat = NumberFormat.getInstance();
      reloadFile();
    }

    /**
     * Return the next string of the sequence, ie the next line of the file.
     */
    @Override
    public synchronized Number nextValue()
    {
        try {
            return current = numberFormat.parse(reader.readLine());
        } catch (ParseException pe) { 
            throw new RuntimeException(pe);
        } catch (NumberFormatException nfe) {
            throw new RuntimeException(nfe);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the previous read line.
     */
    @Override
    public Number lastValue()
    {
        return current;
    }

    /**
     * Reopen the file to reuse values.
     */
    public synchronized void reloadFile() {
      try (Reader r = reader) {
            System.err.println("Reload " + filename);
            reader = new BufferedReader(new FileReader(filename));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Return the expected value (mean) of the values this generator will return.
     */
    @Override
    public double mean()
    {
        return 0.0;
    }
}
