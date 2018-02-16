package cs246.counter;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ImageCounter extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.printf("Usage: ImageCounter <input dir> <output dir>\n");
      return -1;
    }

    Job job = Job.getInstance(getConf());
    job.setJarByClass(ImageCounter.class);
    job.setJobName("Image Counter");

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(ImageCounterMapper.class);
    job.setNumReduceTasks(0);

    boolean success = job.waitForCompletion(true);
    if (success) {
      long jpg = job.getCounters().findCounter("ImageCounter", "jpg").getValue();
      long gif = job.getCounters().findCounter("ImageCounter", "gif").getValue();
      long other = job.getCounters().findCounter("ImageCounter", "other").getValue();
      System.out.println("JPG:" + jpg);
      System.out.println("GIF:" + gif);
      System.out.println("OTHER:" + other);
    }
    return success ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new Configuration(), new ImageCounter(), args);
    System.exit(exitCode);
  }
}