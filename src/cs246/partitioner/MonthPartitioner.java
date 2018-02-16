package cs246.partitioner;

import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Partitioner;

public class MonthPartitioner<K2, V2> extends Partitioner<Text, Text> implements Configurable {

  private Configuration configuration;
  HashMap<String, Integer> months = new HashMap<String, Integer>();

  /**
   * Set up the months hash map in the setConf method.
   */
  @Override
  public void setConf(Configuration configuration) {
	String [] month_names = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	for(int i=0;i<month_names.length;i++) {
      months.put(month_names[i], i);
	}
  }

  /**
   * Implement the getConf method for the Configurable interface.
   */
  @Override
  public Configuration getConf() {
    return configuration;
  }

  /**
   * You must implement the getPartition method for a partitioner class.
   * This method receives the three-letter abbreviation for the month
   * as its value. (It is the output value from the mapper.)
   * It should return an integer representation of the month.
   * Note that January is represented as 0 rather than 1.
   * 
   * For this partitioner to work, the job configuration must have been
   * set so that there are exactly 12 reducers.
   */
  @Override
  public int getPartition(Text key, Text value, int numReduceTasks) {
	 int month_id = months.get(value.toString());
     return month_id;
  }
}
